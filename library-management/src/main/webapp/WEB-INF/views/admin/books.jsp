<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="books"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sách — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .page-title { font-size: 20px; font-weight: bold; margin-bottom: 20px; }
        .header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 16px; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #f0f0f0; padding: 8px 10px; text-align: left; border-bottom: 2px solid #ddd; font-size: 13px; }
        td { padding: 8px 10px; border-bottom: 1px solid #eee; vertical-align: middle; }
        tr:hover td { background: #fafafa; }
        .book-thumb { width: 36px; height: 48px; object-fit: cover; border: 1px solid #eee; background: #eee; display: block; }
        .badge { padding: 2px 7px; font-size: 12px; background: #f0f0f0; border: 1px solid #ddd; }
        .badge-ok { background: #e8f8e8; border-color: #90d090; color: #1a5c1a; }
        .badge-no { background: #fff0f0; border-color: #f5c6cb; color: #c00; }
        .btn { padding: 4px 10px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; font-size: 13px; display: inline-block; }
        .btn:hover { background: #f0f0f0; }
        .btn-add { background: #333; color: #fff; border-color: #333; padding: 7px 14px; }
        .btn-add:hover { background: #000; }
        .btn-danger { color: #c00; border-color: #f5c6cb; }
        .btn-danger:hover { background: #fff0f0; }
        .msg-success { background: #f0fff0; border: 1px solid #b2dfdb; padding: 8px; margin-bottom: 12px; color: #2e7d32; }
    </style>
</head>
<body>
<div class="admin-layout">
    <%@ include file="_sidebar.jsp" %>
    <div class="admin-content">
        <div class="header-row">
            <div class="page-title">Quản lý sách</div>
            <a href="${pageContext.request.contextPath}/admin/books/add" class="btn btn-add">+ Thêm sách mới</a>
        </div>
        <c:if test="${not empty success}">
            <div class="msg-success">${success}</div>
        </c:if>
        <div class="card">
            <table>
                <thead>
                <tr><th>#</th><th>Ảnh</th><th>Tên sách</th><th>Tác giả</th><th>Thể loại</th><th>Giá</th><th>SL</th><th>Còn</th><th>Thao tác</th></tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id}</td>
                        <td><img class="book-thumb"
                                 src="${pageContext.request.contextPath}/resources/images/${book.image}"
                                 onerror="this.style.background='#e0e0e0';this.src=''"></td>
                        <td><strong>${book.title}</strong></td>
                        <td>${book.author}</td>
                        <td><span class="badge">${book.categoryName}</span></td>
                        <td>${book.price} đ</td>
                        <td>${book.quantity}</td>
                        <td>
                            <c:choose>
                                <c:when test="${book.availableQuantity > 0}">
                                    <span class="badge badge-ok">${book.availableQuantity}</span>
                                </c:when>
                                <c:otherwise><span class="badge badge-no">Hết</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/books/edit/${book.id}" class="btn">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/books/delete/${book.id}"
                               class="btn btn-danger"
                               onclick="return confirm('Xóa')">Xóa</a>
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