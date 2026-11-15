<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .form-box { background: #fff; border: 1px solid #ddd; padding: 30px; width: 360px; }
        h2 { font-size: 18px; margin-bottom: 20px; text-align: center; }
        label { display: block; margin-bottom: 4px; color: #555; }
        input { width: 100%; padding: 8px; border: 1px solid #ccc; margin-bottom: 14px; }
        input:focus { outline: none; border-color: #888; }
        button { width: 100%; padding: 9px; background: #333; color: #fff; border: none; cursor: pointer; }
        button:hover { background: #000; }
        .msg-error { background: #fff0f0; border: 1px solid #f5c6cb; padding: 8px; margin-bottom: 14px; color: #c00; }
        .footer-link { text-align: center; margin-top: 14px; color: #555; }
        .footer-link a { color: #333; }
    </style>
</head>
<body>
<div class="form-box">
    <h2>Đăng ký tài khoản</h2>
    <c:if test="${not empty error}">
        <div class="msg-error">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <label>Họ và tên</label>
        <input type="text" name="fullName" placeholder="Nguyễn Văn A" required>
        <label>Tên đăng nhập</label>
        <input type="text" name="username" placeholder="username" required>
        <label>Email</label>
        <input type="email" name="email" placeholder="email@example.com" required>
        <label>Mật khẩu</label>
        <input type="password" name="password" placeholder="Ít nhất 6 ký tự" minlength="6" required>
        <button type="submit">Đăng ký</button>
    </form>
    <div class="footer-link">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></div>
</div>
</body>
</html>