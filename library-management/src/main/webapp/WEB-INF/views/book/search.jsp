<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tìm sách — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .container { max-width: 1100px; margin: 0 auto; padding: 20px; display: flex; gap: 20px; }
        .sidebar { width: 180px; flex-shrink: 0; }
        .sidebar h3 { font-size: 13px; font-weight: bold; margin-bottom: 10px; text-transform: uppercase; color: #555; }
        .sidebar a { display: block; padding: 6px 8px; color: #333; text-decoration: none; border: 1px solid transparent; margin-bottom: 3px; }
        .sidebar a:hover, .sidebar a.active { background: #f0f0f0; border-color: #ddd; }
        .main { flex: 1; }
        .search-bar { display: flex; gap: 8px; margin-bottom: 15px; }
        .search-bar input { flex: 1; padding: 7px 10px; border: 1px solid #ccc; }
        .search-bar button { padding: 7px 14px; background: #333; color: #fff; border: none; cursor: pointer; }
        .result-info { margin-bottom: 12px; color: #555; }
        .book-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 12px; }
        .book-card { background: #fff; border: 1px solid #ddd; padding: 10px; }
        .book-card img { width: 100%; height: 150px; object-fit: cover; background: #eee; display: block; }
        .book-card .title { font-weight: bold; font-size: 13px; margin: 8px 0 3px; }
        .book-card .author { color: #666; font-size: 12px; margin-bottom: 5px; }
        .book-card .meta { display: flex; justify-content: space-between; font-size: 12px; margin-bottom: 8px; }
        .book-card .actions { display: flex; gap: 6px; }
        .btn { padding: 5px 10px; font-size: 12px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; display: inline-block; }
        .btn:hover { background: #f0f0f0; }
        .btn-dark { background: #333; color: #fff; border-color: #333; }
        .btn-dark:hover { background: #000; }
        .empty { text-align: center; padding: 40px; color: #888; }
        @media(max-width: 768px) { .container { flex-direction: column; } .sidebar { width: 100%; } .book-grid { grid-template-columns: repeat(2, 1fr); } }
    </style>
</head>
<body>
<%@ include file="../_navbar.jsp" %>
<div class="container">
    <div class="sidebar">
        <h3>Thể loại</h3>
        <a href="${pageContext.request.contextPath}/search"
           class="${categoryId == 0 ? 'active' : ''}">Tất cả</a>
        <c:forEach var="cat" items="${categories}">
            <a href="${pageContext.request.contextPath}/search?categoryId=${cat.id}"
               class="${categoryId == cat.id ? 'active' : ''}">${cat.name}</a>
        </c:forEach>
    </div>
    <div class="main">
        <form class="search-bar" action="${pageContext.request.contextPath}/search" method="get">
            <input type="text" name="keyword" value="${keyword}" placeholder="Tìm tên sách, tác giả...">
            <button type="submit">Tìm</button>
        </form>
        <div class="result-info">
            <c:choose>
                <c:when test="${not empty keyword}">Kết quả cho: "<b>${keyword}</b>" —</c:when>
                <c:otherwise>Tất cả sách —</c:otherwise>
            </c:choose>
            ${books.size()} cuốn
        </div>
        <c:if test="${empty books}">
            <div class="empty">Không tìm thấy sách nào.</div>
        </c:if>
        <div class="book-grid">
            <c:forEach var="book" items="${books}">
                <div class="book-card">
                    <img src="${pageContext.request.contextPath}/resources/images/${book.image}"
                         alt="${book.title}"
                         onerror="this.style.background='#e0e0e0';this.src=''">
                    <div class="title">${book.title}</div>
                    <div class="author">${book.author}</div>
                    <div class="meta">
                        <span>${book.price} đ</span>
                        <span>Còn ${book.availableQuantity}</span>
                    </div>
                    <div class="actions">
                        <a href="${pageContext.request.contextPath}/book/detail/${book.id}" class="btn">Chi tiết</a>
                        <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin:0">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button class="btn btn-dark" type="submit">+ Giỏ</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>