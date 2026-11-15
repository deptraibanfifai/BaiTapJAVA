<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hồ sơ — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        h2 { font-size: 18px; margin-bottom: 16px; }
        .layout { display: flex; gap: 20px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 20px; margin-bottom: 16px; }
        .card h3 { font-size: 15px; margin-bottom: 14px; border-bottom: 1px solid #eee; padding-bottom: 8px; }
        label { display: block; margin-bottom: 4px; color: #555; }
        input { width: 100%; padding: 7px; border: 1px solid #ccc; margin-bottom: 12px; }
        input:focus { outline: none; border-color: #888; }
        button { padding: 7px 16px; background: #333; color: #fff; border: none; cursor: pointer; }
        button:hover { background: #000; }
        .msg-error { background: #fff0f0; border: 1px solid #f5c6cb; padding: 8px; margin-bottom: 12px; color: #c00; }
        .msg-success { background: #f0fff0; border: 1px solid #b2dfdb; padding: 8px; margin-bottom: 12px; color: #2e7d32; }
        .info-box { width: 200px; flex-shrink: 0; }
        .info-box .card { text-align: center; }
        .avatar { font-size: 48px; margin-bottom: 10px; }
        .info-box .name { font-weight: bold; margin-bottom: 4px; }
        .info-box .uname { color: #888; font-size: 13px; margin-bottom: 10px; }
        .info-box .meta { color: #666; font-size: 13px; margin-bottom: 6px; text-align: left; }
        .info-box a { display: block; margin-top: 10px; color: #333; text-decoration: none; padding: 6px; border: 1px solid #ddd; text-align: center; }
        .info-box a:hover { background: #f5f5f5; }
        .forms { flex: 1; }
        @media(max-width: 600px) { .layout { flex-direction: column; } .info-box { width: 100%; } }
    </style>
</head>
<body>
<%@ include file="../_navbar.jsp" %>
<div class="container">
    <h2>Hồ sơ cá nhân</h2>

    <c:if test="${not empty success}">
        <div class="msg-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="msg-error">${error}</div>
    </c:if>

    <div class="layout">
        <div class="info-box">
            <div class="card">
                <div class="avatar"></div>
                <div class="name">${user.fullName}</div>
                <div class="uname">@${user.username}</div>
                <div class="meta">${user.email}</div>
                <div class="meta"> ${not empty user.phone ? user.phone : 'Chưa cập nhật'}</div>
                <a href="${pageContext.request.contextPath}/user/history">Lịch sử thuê sách</a>
            </div>
        </div>
        <div class="forms">
            <div class="card">
                <h3>Cập nhật thông tin</h3>
                <form action="${pageContext.request.contextPath}/user/profile/update" method="post">
                    <label>Họ và tên</label>
                    <input type="text" name="fullName" value="${user.fullName}" required>
                    <label>Email</label>
                    <input type="email" name="email" value="${user.email}" required>
                    <label>Số điện thoại</label>
                    <input type="text" name="phone" value="${user.phone}">
                    <button type="submit">Lưu thay đổi</button>
                </form>
            </div>
            <div class="card">
                <h3>Đổi mật khẩu</h3>
                <form action="${pageContext.request.contextPath}/user/change-password" method="post">
                    <label>Mật khẩu hiện tại</label>
                    <input type="password" name="oldPassword" required>
                    <label>Mật khẩu mới</label>
                    <input type="password" name="newPassword" minlength="6" required>
                    <button type="submit">Đổi mật khẩu</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>