<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="rentals"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý thuê — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .page-title { font-size: 20px; font-weight: bold; margin-bottom: 20px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 16px; }
        table { width: 100%; border-collapse: collapse; }
        th { background: #f0f0f0; padding: 8px 10px; text-align: left; border-bottom: 2px solid #ddd; font-size: 13px; }
        td { padding: 8px 10px; border-bottom: 1px solid #eee; vertical-align: middle; }
        tr:hover td { background: #fafafa; }
        .badge { padding: 3px 8px; font-size: 12px; border: 1px solid #ccc; }
        .badge-pending  { background: #fffbe6; border-color: #f0c000; color: #856404; }
        .badge-renting  { background: #e8f4ff; border-color: #90c8f0; color: #084298; }
        .badge-returned { background: #e8f8e8; border-color: #90d090; color: #1a5c1a; }
        .badge-overdue  { background: #fff0f0; border-color: #f5c6cb; color: #c00; }
        .btn { padding: 4px 10px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; font-size: 13px; display: inline-block; }
        .btn:hover { background: #f0f0f0; }
        .btn-primary { background: #e8f4ff; border-color: #90c8f0; color: #084298; }
        .btn-success { background: #e8f8e8; border-color: #90d090; color: #1a5c1a; }
        .msg-success { background: #f0fff0; border: 1px solid #b2dfdb; padding: 8px; margin-bottom: 12px; color: #2e7d32; }
    </style>
</head>
<body>
<div class="admin-layout">
    <%@ include file="_sidebar.jsp" %>
    <div class="admin-content">
        <div class="page-title">Quản lý đơn thuê</div>
        <c:if test="${not empty success}">
            <div class="msg-success">${success}</div>
        </c:if>
        <div class="card">
            <table>
                <thead>
                <tr><th>#</th><th>Người thuê</th><th>Ngày thuê</th><th>Hạn trả</th><th>Ngày trả</th><th>Trạng thái</th><th>Thao tác</th></tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${rentals}">
                    <tr>
                        <td>${r.id}</td>
                        <td>${r.username}</td>
                        <td>${r.rentalDate}</td>
                        <td>${r.dueDate}</td>
                        <td>${not empty r.returnDate ? r.returnDate : '—'}</td>
                        <td>
                            <c:choose>
                                <c:when test="${r.status=='PENDING'}"><span class="badge badge-pending">Chờ xác nhận</span></c:when>
                                <c:when test="${r.status=='RENTING'}"><span class="badge badge-renting">Đang thuê</span></c:when>
                                <c:when test="${r.status=='RETURNED'}"><span class="badge badge-returned">Đã trả</span></c:when>
                                <c:otherwise><span class="badge badge-overdue">Quá hạn</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${r.status=='PENDING'}">
                                <a href="${pageContext.request.contextPath}/admin/rentals/confirm/${r.id}"
                                   class="btn btn-primary"
                                   onclick="return confirm('Xác nhận đơn #${r.id}?')">Xác nhận</a>
                            </c:if>
                            <c:if test="${r.status=='RENTING'}">
                                <a href="${pageContext.request.contextPath}/admin/rentals/return/${r.id}"
                                   class="btn btn-success"
                                   onclick="return confirm('Xác nhận trả sách đơn #${r.id}?')">Trả sách</a>
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