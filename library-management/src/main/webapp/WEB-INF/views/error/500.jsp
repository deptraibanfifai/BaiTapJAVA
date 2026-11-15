<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>500 — Lỗi máy chủ</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; }
        body { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: #f5f5f5; }
        .box { text-align: center; }
        .box h1 { font-size: 72px; color: #ccc; margin-bottom: 10px; }
        .box p { color: #888; margin-bottom: 20px; }
        .box a { color: #333; text-decoration: none; border: 1px solid #ccc; padding: 8px 16px; }
        .box a:hover { background: #f0f0f0; }
    </style>
</head>
<body>
<div class="box">
    <h1>500</h1>
    <p>Máy chủ đang gặp sự cố.</p>
    <a href="${pageContext.request.contextPath}/">Về trang chủ</a>
</div>
</body>
</html>