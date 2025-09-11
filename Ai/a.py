import requests
from bs4 import BeautifulSoup
import pandas as pd
import time
import os
from urllib.parse import urljoin, urlparse

def check_requirements():
    """Kiểm tra các thư viện cần thiết"""
    try:
        import requests
        import bs4
        import pandas as pd
        print("✅ Tất cả thư viện đã được cài đặt")
        return True
    except ImportError as e:
        print(f"❌ Thiếu thư viện: {e}")
        print("Vui lòng chạy: pip install requests beautifulsoup4 pandas lxml")
        return False

def explore_cooky_structure():
    """Khám phá cấu trúc website cooky.vn"""
    
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }
    
    # Thử các URL khác nhau
    test_urls = [
        "https://www.cooky.vn/",
        "https://www.cooky.vn/dish",
        "https://www.cooky.vn/dishes",
        "https://www.cooky.vn/recipe",
        "https://www.cooky.vn/recipes",
        "https://cooky.vn/",
        "https://cooky.vn/dish",
        "https://cooky.vn/recipes"
    ]
    
    print("🔍 Đang khám phá cấu trúc website...")
    
    for url in test_urls:
        try:
            print(f"\n🌐 Kiểm tra: {url}")
            response = requests.get(url, headers=headers, timeout=10)
            print(f"   Status: {response.status_code}")
            
            if response.status_code == 200:
                soup = BeautifulSoup(response.text, 'html.parser')
                
                # Tìm các link món ăn
                dish_links = soup.find_all('a', href=True)
                recipe_links = [link for link in dish_links if 'recipe' in link.get('href', '').lower() or 'dish' in link.get('href', '').lower()]
                
                if recipe_links:
                    print(f"   ✅ Tìm thấy {len(recipe_links)} link món ăn")
                    print(f"   📋 Ví dụ: {recipe_links[0].get('href')}")
                    return url, soup
                else:
                    # Tìm pagination hoặc danh sách món ăn
                    pagination = soup.find_all(['a', 'div'], class_=['page', 'pagination', 'dish', 'recipe'])
                    if pagination:
                        print(f"   🔗 Tìm thấy {len(pagination)} element liên quan")
                        
        except Exception as e:
            print(f"   ❌ Lỗi: {e}")
    
    return None, None

def find_recipe_structure(base_url, soup):
    """Tìm cấu trúc của trang recipe"""
    
    print(f"\n🔍 Phân tích cấu trúc của {base_url}")
    
    # Tìm các selector có thể chứa danh sách món ăn
    possible_selectors = [
        '.dish-card',
        '.recipe-card', 
        '.food-card',
        '.item-card',
        '.card',
        '[class*="dish"]',
        '[class*="recipe"]',
        '[class*="food"]'
    ]
    
    for selector in possible_selectors:
        elements = soup.select(selector)
        if elements:
            print(f"   ✅ Tìm thấy {len(elements)} element với selector: {selector}")
            
            # Tìm link trong element đầu tiên
            first_element = elements[0]
            links = first_element.find_all('a', href=True)
            if links:
                href = links[0].get('href')
                print(f"   🔗 Link mẫu: {href}")
                return selector, href
    
    return None, None

def crawl_with_discovered_structure():
    """Crawl với cấu trúc được phát hiện"""
    
    if not check_requirements():
        return
    
    # Khám phá cấu trúc
    base_url, soup = explore_cooky_structure()
    
    if not base_url:
        print("❌ Không tìm thấy cấu trúc phù hợp của cooky.vn")
        print("🔄 Thử crawl với các website khác:")
        alternative_crawl()
        return
    
    print(f"\n🎯 Sử dụng base URL: {base_url}")
    
    # Tìm cấu trúc selector
    selector, sample_href = find_recipe_structure(base_url, soup)
    
    if not selector:
        print("❌ Không tìm thấy cấu trúc món ăn phù hợp")
        return
    
    # Crawl dữ liệu
    crawl_cooky_dishes_new(base_url, selector)

def alternative_crawl():
    """Crawl từ các website khác"""
    
    print("\n🔄 Thử crawl từ các website khác:")
    
    # Thử website khác
    alternative_sites = [
        {
            'name': 'Món Ngon Mỗi Ngày',
            'url': 'https://www.monngonmoingay.com/',
            'selector': '.recipe-item'
        },
        {
            'name': 'Foody',
            'url': 'https://www.foody.vn/',
            'selector': '.recipe-card'
        }
    ]
    
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
    }
    
    for site in alternative_sites:
        try:
            print(f"\n🌐 Thử {site['name']}: {site['url']}")
            response = requests.get(site['url'], headers=headers, timeout=10)
            
            if response.status_code == 200:
                print(f"   ✅ Kết nối thành công!")
                soup = BeautifulSoup(response.text, 'html.parser')
                
                # Tìm các link món ăn
                links = soup.find_all('a', href=True)
                recipe_links = [link for link in links if any(keyword in link.get('href', '').lower() for keyword in ['recipe', 'dish', 'mon-an', 'cong-thuc'])]
                
                if recipe_links:
                    print(f"   📋 Tìm thấy {len(recipe_links)} link món ăn")
                    break
                    
        except Exception as e:
            print(f"   ❌ Lỗi: {e}")

def crawl_cooky_dishes_new(base_url, selector):
    """Crawl với cấu trúc mới được phát hiện"""
    
    dishes = []
    ingredients_set = set()
    dish_ingredient_map = []
    
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
    }
    
    try:
        # Crawl trang chủ
        response = requests.get(base_url, headers=headers, timeout=10)
        soup = BeautifulSoup(response.text, 'html.parser')
        
        # Tìm các món ăn
        dish_elements = soup.select(selector)[:800]  # Giới hạn 20 món đầu tiên
        
        print(f"📋 Tìm thấy {len(dish_elements)} món ăn")
        
        for i, element in enumerate(dish_elements, 1):
            try:
                # Tìm tên món ăn
                dish_name = element.get_text(strip=True)
                if not dish_name:
                    continue
                
                # Tìm link chi tiết
                link = element.find('a', href=True)
                if link:
                    href = link.get('href')
                    full_url = urljoin(base_url, href)
                else:
                    full_url = base_url
                
                print(f"  {i}. {dish_name[:50]}...")
                
                # Thêm vào danh sách
                dishes.append({
                    "dish_id": i,
                    "name": dish_name,
                    "url_method": full_url
                })
                
                time.sleep(0.5)
                
            except Exception as e:
                print(f"    ❌ Lỗi: {e}")
                continue
        
        # Lưu dữ liệu
        if dishes:
            save_simple_data(dishes)
        else:
            print("❌ Không có dữ liệu nào được crawl")
            
    except Exception as e:
        print(f"❌ Lỗi crawl: {e}")

def save_simple_data(dishes):
    """Lưu dữ liệu đơn giản"""
    
    output_dir = "crawled_data"
    os.makedirs(output_dir, exist_ok=True)
    
    dish_df = pd.DataFrame(dishes)
    dish_df.to_csv(f"{output_dir}/dishes.csv", index=False, encoding='utf-8')
    
    print(f"\n✅ Đã lưu {len(dishes)} món ăn vào {output_dir}/dishes.csv")

if __name__ == "__main__":
    print("🚀 Bắt đầu khám phá và crawl dữ liệu...")
    crawl_with_discovered_structure()