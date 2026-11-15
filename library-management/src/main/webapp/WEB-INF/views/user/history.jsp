<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử thuê — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .container { max-width: 900px; margin: 0 auto; padding: 20px; }
        h2 { font-size: 18px; margin-bottom: 16px; }
        .empty { text-align: center; padding: 50px; color: #888; }
        .rental-card { background: #fff; border: 1px solid #ddd; margin-bottom: 12px; }
        .rental-header { padding: 10px 14px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; background: #fafafa; }
        .rental-header .order-id { font-weight: bold; }
        .rental-header .dates { color: #666; font-size: 13px; }
        .status { padding: 3px 8px; font-size: 12px; border: 1px solid #ccc; }
        .status-PENDING  { background: #fffbe6; border-color: #f0c000; color: #856404; }
        .status-RENTING  { background: #e8f4ff; border-color: #90c8f0; color: #084298; }
        .status-RETURNED { background: #e8f8e8; border-color: #90d090; color: #1a5c1a; }
        .status-OVERDUE  { background: #fff0f0; border-color: #f5c6cb; color: #c00; }
        .rental-body { padding: 10px 14px; }
        .book-row { display: flex; align-items: center; gap: 10px; padding: 6px 0; border-bottom: 1px solid #f5f5f5; }
        .book-row:last-child { border-bottom: none; }
        .book-row img { width: 40px; height: 52px; object-fit: cover; border: 1px solid #eee; background: #eee; flex-shrink: 0; }
        .book-row .book-title { font-weight: bold; font-size: 13px; }
        .book-row .book-author { color: #888; font-size: 12px; }
    </style>
</head>
<body>
<%@ include file="../_navbar.jsp" %>
<div class="container">
    <h2>Lịch sử thuê sách</h2>
    <c:choose>
        <c:when test="${empty rentals}">
            <div class="empty">Bạn chưa có lịch sử thuê sách.</div>
        </c:when>
        <c:otherwise>
            <c:forEach var="rental" items="${rentals}">
                <div class="rental-card">
                    <div class="rental-header">
                        <div>
                            <span class="order-id">Đơn #${rental.id}</span>
                            <span class="dates"> — Ngày thuê: ${rental.rentalDate} | Hạn trả: ${rental.dueDate}</span>
                        </div>
                        <span class="status status-${rental.status}">
                            <c:choose>
                                <c:when test="${rental.status == 'PENDING'}">Chờ xác nhận</c:when>
                                <c:when test="${rental.status == 'RENTING'}">Đang thuê</c:when>
                                <c:when test="${rental.status == 'RETURNED'}">Đã trả</c:when>
                                <c:when test="${rental.status == 'OVERDUE'}">Quá hạn</c:when>
                            </c:choose>
                        </span>
                    </div>
                    <div class="rental-body">
                        <c:forEach var="detail" items="${requestScope['details_'.concat(rental.id)]}">
                            <div class="book-row">
                                <img src="${pageContext.request.contextPath}/resources/images/${detail.bookImage}"
                                     alt="${detail.bookTitle}"
                                     onerror="this.style.background='#e0e0e0';this.src=''">
                                <div>
                                    <div class="book-title">${detail.bookTitle}</div>
                                    <div class="book-author">${detail.bookAuthor}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>