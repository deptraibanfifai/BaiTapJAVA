<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${book.title} — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .container { max-width: 900px; margin: 0 auto; padding: 20px; }
        .breadcrumb { margin-bottom: 15px; color: #888; }
        .breadcrumb a { color: #555; text-decoration: none; }
        .breadcrumb a:hover { text-decoration: underline; }
        .detail-box { background: #fff; border: 1px solid #ddd; padding: 20px; display: flex; gap: 24px; }
        .detail-box img { width: 200px; height: 270px; object-fit: cover; border: 1px solid #eee; flex-shrink: 0; background: #eee; }
        .info { flex: 1; }
        .info h1 { font-size: 20px; margin-bottom: 6px; }
        .info .author { color: #555; margin-bottom: 14px; }
        table.meta { border-collapse: collapse; margin-bottom: 14px; }
        table.meta td { padding: 5px 12px 5px 0; color: #555; }
        table.meta td:first-child { font-weight: bold; color: #333; white-space: nowrap; }
        .price { font-size: 20px; font-weight: bold; margin-bottom: 14px; }
        .stock-ok { color: green; }
        .stock-no { color: red; }
        .msg-error { background: #fff0f0; border: 1px solid #f5c6cb; padding: 8px; margin-bottom: 12px; color: #c00; }
        .msg-success { background: #f0fff0; border: 1px solid #b2dfdb; padding: 8px; margin-bottom: 12px; color: #2e7d32; }
        .actions { display: flex; gap: 8px; margin-bottom: 14px; }
        .btn { padding: 7px 14px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; display: inline-block; font-size: 14px; }
        .btn:hover { background: #f0f0f0; }
        .btn-dark { background: #333; color: #fff; border-color: #333; }
        .btn-dark:hover { background: #000; }
        .description { margin-top: 14px; padding-top: 14px; border-top: 1px solid #eee; color: #444; line-height: 1.6; }
        @media(max-width: 600px) { .detail-box { flex-direction: column; } .detail-box img { width: 100%; height: auto; } }
    </style>
</head>
<body>
<%@ include file="../_navbar.jsp" %>
<div class="container">
    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/">Trang chủ</a> &rsaquo;
        <a href="${pageContext.request.contextPath}/search">Sách</a> &rsaquo;
        ${book.title}
    </div>

    <c:if test="${not empty success}">
        <div class="msg-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="msg-error">${error}</div>
    </c:if>

    <div class="detail-box">
        <img src="${pageContext.request.contextPath}/resources/images/${book.image}"
             alt="${book.title}"
             onerror="this.style.background='#e0e0e0';this.src=''">
        <div class="info">
            <h1>${book.title}</h1>
            <div class="author">Tác giả: ${book.author}</div>
            <table class="meta">
                <tr><td>Thể loại</td><td>${book.categoryName}</td></tr>
                <tr><td>Năm XB</td><td>${book.publishYear}</td></tr>
                <tr><td>Số lượng</td><td>${book.quantity} cuốn</td></tr>
                <tr><td>Còn lại</td>
                    <td>
                        <c:choose>
                            <c:when test="${book.availableQuantity > 0}">
                                <span class="stock-ok">${book.availableQuantity} cuốn có sẵn</span>
                            </c:when>
                            <c:otherwise><span class="stock-no">Hết sách</span></c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
            <div class="price">${book.price} đ</div>
            <div class="actions">
                <c:choose>
                    <c:when test="${book.availableQuantity > 0}">
                        <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin:0">
                            <input type="hidden" name="bookId" value="${book.id}">
                            <button class="btn btn-dark" type="submit">+ Thêm vào giỏ</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="btn" disabled>Hết sách</button>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/search" class="btn">← Quay lại</a>
            </div>
            <div class="description">${book.description}</div>
        </div>
    </div>
</div>
</body>
</html>