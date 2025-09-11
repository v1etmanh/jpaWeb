import sys
from rembg import remove
from PIL import Image


def hex_to_rgb(hex_color):
    """Chuyển mã HEX sang RGB."""
    hex_color = hex_color.lstrip('#')
    return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))


def main(input_path, output_path, bg_color="#FFFFFF"):
    # Đọc ảnh
    input_image = Image.open(input_path)

    # Xóa nền
    output_image = remove(input_image)

    # Chuyển màu hex sang RGB
    rgb_color = hex_to_rgb(bg_color)

    # Tạo nền mới với màu đã chọn
    bg = Image.new("RGBA", output_image.size, rgb_color + (255,))

    # Dán ảnh đã xóa nền lên
    bg.paste(output_image, (0, 0), output_image)

    # Lưu kết quả
    bg.convert('RGB').save(output_path)

    print(f"Đã lưu ảnh với nền {bg_color} → {output_path}")


if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Cách dùng: python a.py input.png output.png [màu_hex]")
        print("Ví dụ: python a.py input.png output.png #00FFFF")
        sys.exit(1)

    input_file = sys.argv[1]
    output_file = sys.argv[2]
    bg_color = sys.argv[3] if len(sys.argv) >= 4 else "#FFFFFF"  # Mặc định trắng

    main(input_file, output_file, bg_color)
