<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bài Lab Java 4 - PolyOE</title>
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f2f2f2;
            font-family: "Segoe UI", Arial, sans-serif;
        }
        .card {
            max-width: 600px;
            margin: 80px auto;
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }
        .title {
            color: #212121;
            font-weight: 700;
        }
        .btn-dark-gray {
            background-color: #333;
            color: white;
            font-weight: 600;
            font-size: 18px;
            border-radius: 10px;
            padding: 15px 0;
            width: 100%;
            transition: 0.3s;
        }
        .btn-dark-gray:hover {
            background-color: #555;
            color: #fff;
        }
        .note {
            font-size: 14px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="card p-4 text-center bg-white">
        <h2 class="title mb-3">Bài Lab Java 4 - PolyOE</h2>
        <p class="text-muted mb-4">Chọn một chức năng để xem:</p>

        <div class="d-grid gap-4">
            <div>
                <a href="${pageContext.request.contextPath}/favorite-user" class="btn btn-dark-gray">
                    Bài 3: Trang Video yêu thích
                </a>
                <div class="note mt-1">
                    (Hiển thị video anh "Nguyễn Văn Tèo" đã thích)
                </div>
            </div>

            <div>
                <a href="${pageContext.request.contextPath}/favorite-report" class="btn btn-dark-gray">
                    Bài 4: Thống kê Video
                </a>
                <div class="note mt-1">
                    (Hiển thị danh sách tất cả video đã được yêu thích)
                </div>
            </div>
        </div>
    </div>
</body>
</html>
