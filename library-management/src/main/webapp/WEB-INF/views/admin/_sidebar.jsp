<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .admin-layout {
        display: flex;
        min-height: 100vh;
    }

    .sidebar {
        width: 220px;
        background: #222;
        flex-shrink: 0;
    }

    .sidebar .brand {
        padding: 16px;
        color: #fff;
        font-weight: bold;
        font-size: 16px;
        border-bottom: 1px solid #444;
    }

    .sidebar .brand span {
        color: #aaa;
        font-size: 12px;
        font-weight: normal;
        display: block;
        margin-top: 4px;
    }

    .sidebar nav a {
        display: block;
        padding: 12px 16px;
        color: #ccc;
        text-decoration: none;
        border-left: 4px solid transparent;
        transition: 0.2s;
    }

    .sidebar nav a:hover {
        background: #333;
        color: white;
    }

    .sidebar nav a.active {
        background: #333;
        color: white;
        border-left-color: #4CAF50;
        font-weight: bold;
    }

    .sidebar nav hr {
        border: none;
        border-top: 1px solid #444;
        margin: 8px 0;
    }

    .sidebar nav a.danger {
        color: #ff8b8b;
    }

    .sidebar nav a.danger:hover {
        background: #4d1f1f;
    }

    .admin-content {
        flex: 1;
        padding: 24px;
        background: #f5f5f5;
    }
</style>

<div class="sidebar">

    <div class="brand">
        LibraryMS
        <span>Admin Panel</span>
    </div>

    <nav>

        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="${currentPage == 'dashboard' ? 'active' : ''}">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/admin/books"
           class="${currentPage == 'books' ? 'active' : ''}">
            Quản lý sách
        </a>

        <a href="${pageContext.request.contextPath}/admin/users"
           class="${currentPage == 'users' ? 'active' : ''}">
            Quản lý User
        </a>

        <a href="${pageContext.request.contextPath}/admin/rentals"
           class="${currentPage == 'rentals' ? 'active' : ''}">
            Quản lý thuê
        </a>

        <a href="${pageContext.request.contextPath}/admin/payments"
           class="${currentPage == 'payments' ? 'active' : ''}">
            Quản lý doanh thu
        </a>

        <hr>

        <a href="${pageContext.request.contextPath}/logout"
           class="danger">
            Đăng xuất
        </a>

    </nav>

</div>