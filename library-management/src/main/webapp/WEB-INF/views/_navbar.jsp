<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
    nav { background: #fff; border-bottom: 1px solid #ddd; padding: 10px 20px; display: flex; align-items: center; gap: 20px; }
    nav a { text-decoration: none; color: #333; }
    nav a:hover { color: #000; text-decoration: underline; }
    .nav-brand { font-weight: bold; font-size: 16px; }
    .nav-links { display: flex; gap: 15px; flex: 1; }
    .nav-right { display: flex; gap: 15px; align-items: center; }
    .badge { background: red; color: white; border-radius: 50%; padding: 1px 5px; font-size: 11px; }
    .dropdown { position: relative; }
    .dropdown-menu { display: none; position: absolute; right: 0; top: 100%; background: #fff; border: 1px solid #ddd; min-width: 150px; z-index: 999; }
    .dropdown-menu a { display: block; padding: 8px 12px; color: #333; }
    .dropdown-menu a:hover { background: #f5f5f5; }
    .dropdown:hover .dropdown-menu { display: block; }
    .dropdown-toggle { cursor: pointer; }
    hr.divider { border: none; border-top: 1px solid #eee; margin: 4px 0; }
</style>
<nav>
    <a class="nav-brand">Library</a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Trang chủ</a>
        <%-- Tạm ẩn phần Thể loại và Tìm sách để tránh lặp lại --%>
        <%--
        <div class="dropdown">
            <span class="dropdown-toggle">Thể loại ▾</span>
            <div class="dropdown-menu">
                <c:forEach var="cat" items="${categories}">
                    <a href="${pageContext.request.contextPath}/search?categoryId=${cat.id}">${cat.name}</a>
                </c:forEach>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/search">Tìm sách</a>
        --%>
    </div>
    <div class="nav-right">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <a href="${pageContext.request.contextPath}/cart">
                    Giỏ hàng
                    <c:if test="${cartCount > 0}">
                        <span class="badge">${cartCount}</span>
                    </c:if>
                </a>
                <div class="dropdown">
                    <span class="dropdown-toggle">${sessionScope.user.username} ▾</span>
                    <div class="dropdown-menu">
                        <a href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a>
                        <a href="${pageContext.request.contextPath}/user/history">Lịch sử thuê</a>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <hr class="divider">
                            <a href="${pageContext.request.contextPath}/admin/dashboard">Trang Admin</a>
                        </c:if>
                        <hr class="divider">
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>