import redis
import json
import base64
import io
import numpy as np
import librosa
import whisper
import difflib
from sentence_transformers import SentenceTransformer, util
import time
import logging
from pydub import AudioSegment
import tempfile
import os

# Setup logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class WhisperWorker:
    def __init__(self):
        # Kết nối Redis
        self.redis_client = redis.Redis(host='localhost', port=6379, db=0)
        
        # Load models một lần
        logger.info("Loading Whisper model...")
        self.whisper_model = whisper.load_model("small")
        logger.info("Whisper model loaded!")
        
        logger.info("Loading semantic model...")
        self.semantic_model = SentenceTransformer('sentence-transformers/LaBSE')
        logger.info("Semantic model loaded!")
        
        # Queue keys
        self.queue_key = "whisper_queue"
        self.job_prefix = "job:"
        self.result_prefix = "result:"
    
    def calc_similarity(self, ans, pred):
        """Tính độ tương đồng bằng SequenceMatcher"""
        return round(difflib.SequenceMatcher(None, ans, pred).ratio() * 100, 2)
    
    def is_match(self, user_text, reference_text, threshold=0.2):
        """Tính độ tương đồng semantic giữa hai văn bản"""
        try:
            emb1 = self.semantic_model.encode(user_text, convert_to_tensor=True)
            emb2 = self.semantic_model.encode(reference_text, convert_to_tensor=True)
            similarity = util.cos_sim(emb1, emb2).item()
            print(similarity)
            return {
                "match": similarity >= threshold,
                "similarity_score": round(similarity, 4),
                "user_answer": user_text,
                "expected_answer": reference_text,
                "status": "COMPLETED"
            }
        except Exception as e:
            logger.error(f"Error in semantic matching: {str(e)}")
            return {
                "status": "FAILED",
                "error": f"Semantic matching error: {str(e)}"
            }
    
    def audio_bytes_to_numpy(self, audio_bytes):
        """Chuyển audio bytes thành numpy array"""
        try:
            # Tạo temp file để pydub xử lý
            with tempfile.NamedTemporaryFile(delete=False, suffix='.webm') as temp_file:
                temp_file.write(audio_bytes)
                temp_file_path = temp_file.name
            
            try:
                # Sử dụng pydub để load audio (hỗ trợ nhiều format hơn)
                audio_segment = AudioSegment.from_file(temp_file_path)
                
                # Convert to WAV format để librosa xử lý
                with tempfile.NamedTemporaryFile(delete=False, suffix='.wav') as wav_file:
                    wav_file_path = wav_file.name
                
                audio_segment.export(wav_file_path, format="wav")
                
                # Load bằng librosa
                audio_array, sample_rate = librosa.load(
                    wav_file_path,
                    sr=16000,  # Whisper yêu cầu sample rate 16kHz
                    mono=True  # Chuyển về mono
                )
                
                # Cleanup temp files
                os.unlink(temp_file_path)
                os.unlink(wav_file_path)
                
                return audio_array
                
            except Exception as inner_e:
                # Cleanup on error
                if os.path.exists(temp_file_path):
                    os.unlink(temp_file_path)
                raise inner_e
                
        except Exception as e:
            raise Exception(f"Error converting audio to numpy: {str(e)}")
    
    def process_job(self, job_data):
        """Xử lý một job"""
        try:
            job_id = job_data['jobId']
            audio_b64 = job_data['audioData']
            expected_text = job_data['expectedText']
            
            logger.info(f"Processing job {job_id}")
            
            # Decode audio từ base64
            audio_bytes = base64.b64decode(audio_b64)
            
            # Chuyển thành numpy array
            audio_array = self.audio_bytes_to_numpy(audio_bytes)
            
            # Transcribe audio
            result = self.whisper_model.transcribe(audio_array, language="ja")
            recognized_text = result["text"].strip()
            
            # Đánh giá semantic similarity
            evaluation_result = self.is_match(recognized_text, expected_text)
            
            # Lưu kết quả vào Redis
            self.redis_client.setex(
                f"{self.result_prefix}{job_id}", 
                3600,  # TTL 1 hour
                json.dumps(evaluation_result)
            )
            
            # Xóa job data
            self.redis_client.delete(f"{self.job_prefix}{job_id}")
            
            logger.info(f"Job {job_id} completed successfully")
            return True
            
        except Exception as e:
            logger.error(f"Error processing job {job_data.get('jobId', 'unknown')}: {str(e)}")
            
            # Lưu lỗi vào Redis
            error_result = {
                "status": "FAILED",
                "error": str(e)
            }
            
            if 'jobId' in job_data:
                self.redis_client.setex(
                    f"{self.result_prefix}{job_data['jobId']}", 
                    3600,
                    json.dumps(error_result)
                )
            
            return False
    
    def run(self):
        """Chạy worker liên tục"""
        logger.info("Worker started, waiting for jobs...")
        
        while True:
            try:
                # Lấy job từ queue (blocking với timeout 5s)
                job_data = self.redis_client.brpop(self.queue_key, timeout=5)
                
                if job_data:
                    # job_data là tuple (queue_name, job_id)
                    raw_job_id = job_data[1].decode('utf-8')
                    
                    # FIX: Remove quotes if present
                    job_id = raw_job_id.strip('"')
                    logger.info(f"Processing job: {job_id} (raw: {raw_job_id})")
                    
                    # Lấy job detail từ Redis
                    job_detail = self.redis_client.get(f"{self.job_prefix}{job_id}")
                    
                    if job_detail:
                        # FIX: Handle both object and JSON string
                        try:
                            if isinstance(job_detail, bytes):
                                job_detail = job_detail.decode('utf-8')
                            
                            if isinstance(job_detail, str):
                                job_json = json.loads(job_detail)
                            else:
                                job_json = job_detail  # Already an object
                                
                            self.process_job(job_json)
                        except json.JSONDecodeError as e:
                            logger.error(f"Failed to parse job detail for {job_id}: {e}")
                    else:
                        logger.warning(f"Job detail not found for job_id: {job_id}")
                
            except KeyboardInterrupt:
                logger.info("Worker stopped by user")
                break
            except Exception as e:
                logger.error(f"Worker error: {str(e)}")
                time.sleep(1)

if __name__ == "__main__":
    worker = WhisperWorker()
    worker.run()