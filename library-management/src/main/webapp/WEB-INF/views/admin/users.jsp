<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="users"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý user — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .page-title { font-size: 20px; font-weight: bold; margin-bottom: 20px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 16px; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #f0f0f0; padding: 8px 10px; text-align: left; border-bottom: 2px solid #ddd; font-size: 13px; }
        td { padding: 8px 10px; border-bottom: 1px solid #eee; vertical-align: middle; }
        tr:hover td { background: #fafafa; }
        .badge { padding: 2px 7px; font-size: 12px; background: #f0f0f0; border: 1px solid #ddd; }


        .badge-locked  { background: #f0f0f0; border-color: #ccc; color: #666; }
        .btn { padding: 4px 10px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; font-size: 13px; display: inline-block; }

        .btn-danger { color: #c00; border-color: #f5c6cb; }
        .btn-danger:hover { background: #fff0f0; }
        .msg-success { background: #f0fff0; border: 1px solid #b2dfdb; padding: 8px; margin-bottom: 12px; color: #2e7d32; }
    </style>
</head>
<body>
<div class="admin-layout">
    <%@ include file="_sidebar.jsp" %>
    <div class="admin-content">
        <div class="page-title">Quản lý người dùng</div>
        <c:if test="${not empty success}">
            <div class="msg-success">${success}</div>
        </c:if>
        <div class="card">
            <table>
                <thead>
                <tr><th>#</th><th>Username</th><th>Họ tên</th><th>Email</th><th>SĐT</th><th>Role</th><th>Trạng thái</th><th>Ngày tạo</th><th>Thao tác</th></tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.id}</td>
                        <td><strong>@${u.username}</strong></td>
                        <td>${not empty u.fullName ? u.fullName : '—'}</td>
                        <td>${u.email}</td>
                        <td>${not empty u.phone ? u.phone : '—'}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.role == 'ADMIN'}"><span class="badge badge-admin">Admin</span></c:when>
                                <c:otherwise><span class="badge">User</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.status == 'ACTIVE'}"><span class="badge badge-active">Active</span></c:when>
                                <c:otherwise><span class="badge badge-locked">Locked</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${u.createdAt}</td>
                        <td>
                            <c:if test="${u.role != 'ADMIN'}">
                                <c:choose>
                                    <c:when test="${u.status == 'ACTIVE'}">
                                        <a href="${pageContext.request.contextPath}/admin/users/lock/${u.id}"
                                           class="btn"
                                           onclick="return confirm('Khóa @${u.username}?')">Khóa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/admin/users/unlock/${u.id}" class="btn">Mở khóa</a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${pageContext.request.contextPath}/admin/users/delete/${u.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Xóa @${u.username}?')">Xóa</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>