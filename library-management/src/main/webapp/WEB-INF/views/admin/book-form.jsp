<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="books"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${not empty book ? 'Sửa' : 'Thêm'} sách — LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 14px; }
        body { background: #f5f5f5; }
        .page-title { font-size: 20px; font-weight: bold; margin-bottom: 20px; }
        .card { background: #fff; border: 1px solid #ddd; padding: 20px; max-width: 650px; }
        .form-row { display: flex; gap: 16px; }
        .form-group { margin-bottom: 14px; flex: 1; }
        label { display: block; margin-bottom: 4px; color: #555; }
        input, select, textarea { width: 100%; padding: 7px; border: 1px solid #ccc; }
        input:focus, select:focus, textarea:focus { outline: none; border-color: #888; }
        textarea { resize: vertical; min-height: 80px; }
        .actions { display: flex; gap: 8px; margin-top: 6px; }
        .btn { padding: 7px 16px; border: 1px solid #ccc; cursor: pointer; text-decoration: none; color: #333; background: #fff; font-size: 14px; display: inline-block; }
        .btn:hover { background: #f0f0f0; }
        .btn-dark { background: #333; color: #fff; border-color: #333; }
        .btn-dark:hover { background: #000; }
        .back-link { margin-bottom: 14px; }
        .back-link a { color: #555; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="admin-layout">
    <%@ include file="_sidebar.jsp" %>
    <div class="admin-content">
        <div class="back-link"><a href="${pageContext.request.contextPath}/admin/books">← Quay lại danh sách</a></div>
        <div class="page-title">${not empty book ? 'Chỉnh sửa sách' : 'Thêm sách mới'}</div>
        <div class="card">
            <form action="${pageContext.request.contextPath}/admin/books/save" method="post">
                <input type="hidden" name="bookId" value="${book.id}">
                <div class="form-row">
                    <div class="form-group">
                        <label>Tên sách </label>
                        <input type="text" name="title" value="${book.title}" required>
                    </div>
                    <div class="form-group">
                        <label>Năm xuất bản </label>
                        <input type="number" name="publishYear" value="${book.publishYear}" min="1900" max="2099" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Tác giả</label>
                        <input type="text" name="author" value="${book.author}" required>
                    </div>
                    <div class="form-group">
                        <label>Thể loại</label>
                        <select name="categoryId" required>
                            <option value="">Chọn</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${book.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Giá thuê (đ)</label>
                        <input type="number" name="price" value="${book.price}" min="0" required>
                    </div>
                    <div class="form-group">
                        <label>Số lượng</label>
                        <input type="number" name="quantity" value="${book.quantity}" min="1" required>
                    </div>
                    <div class="form-group">
                        <label>File ảnh</label>
                        <input type="text" name="image" value="${not empty book.image ? book.image : 'default-book.jpg'}">
                    </div>
                </div>
                <div class="form-group">
                    <label>Mô tả</label>
                    <textarea name="description">${book.description}</textarea>
                </div>
                <div class="actions">
                    <button class="btn btn-dark" type="submit">${not empty book ? 'Cập nhật' : 'Thêm sách'}</button>
                    <a href="${pageContext.request.contextPath}/admin/books" class="btn">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>