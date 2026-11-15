<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LibraryT</title>
<style>
    *{
        margin:0;
        padding:0;
        box-sizing:border-box;
    }

    body{
        font-family:Arial,sans-serif;
        background:#f5f5f5;
    }

    .container{
        width:90%;
        max-width:1200px;
        margin:20px auto;
    }

    .search-box{
        background:#fff;
        border:1px solid #ddd;
        padding:15px;
        margin-bottom:20px;
    }

    .search-box form{
        display:flex;
        gap:10px;
    }

    .search-box input{
        flex:1;
        padding:10px;
        border:1px solid #ccc;
    }

    .search-box select{
        width:200px;
        padding:10px;
        border:1px solid #ccc;
    }

    .search-box button{
        padding:10px 20px;
        border:none;
        background:#333;
        color:white;
        cursor:pointer;
    }

    .search-box button:hover{
        background:black;
    }

    .info-box{
        background:white;
        border:1px solid #ddd;
        padding:15px;
        margin-bottom:20px;
    }

    h2{
        margin-bottom:15px;
        border-left:5px solid #333;
        padding-left:10px;
    }

    .book-grid{
        display:grid;
        grid-template-columns:repeat(4,1fr);
        gap:15px;
        margin-bottom:30px;
    }

    .book-card{
        background:white;
        border:1px solid #ddd;
        padding:10px;
    }

    .book-card img{
        width:100%;
        height:260px;
        object-fit:cover;
        background:#eee;
    }

    .title{
        font-weight:bold;
        margin-top:10px;
        min-height:45px;
    }

    .author{
        color:#666;
        margin:8px 0;
    }

    .meta{
        display:flex;
        justify-content:space-between;
        margin-bottom:10px;
        font-size:14px;
    }

    .actions{
        display:flex;
        gap:8px;
    }

    .btn{
        flex:1;
        text-align:center;
        padding:8px;
        border:1px solid #ccc;
        background:white;
        text-decoration:none;
        color:black;
    }

    .btn-dark{
        background:#333;
        color:white;
        border:none;
    }

    .btn-dark:hover{
        background:black;
    }

    .divider{
        margin:25px 0;
        border:none;
        border-top:1px solid #ddd;
    }

    @media(max-width:992px){
        .book-grid{
            grid-template-columns:repeat(3,1fr);
        }
    }

    @media(max-width:768px){
        .book-grid{
            grid-template-columns:repeat(2,1fr);
        }
    }

</style>
</head>
<body>
<%@ include file="_navbar.jsp" %>
<div class="container">
<!-- Search -->
<div class="search-box">
    <form action="${pageContext.request.contextPath}/search" method="get">
        <input
                type="text"
                name="keyword"
                placeholder="Tìm theo tên sách hoặc tác giả">
        <select name="categoryId">
            <option value="0">
                Tất cả danh mục
            </option>
            <c:forEach items="${categories}" var="c">
                <option value="${c.id}">
                    ${c.name}
                </option>
            </c:forEach>
        </select>
        <button type="submit">
            Tìm kiếm
        </button>
    </form>
</div>
<!-- Tổng sách -->
<div class="info-box">
    <strong>Tổng số đầu sách:</strong>
    ${allBooks.size()}
</div>
<!-- Sách mới nhất -->
<h2>Sách mới nhất</h2>
<div class="book-grid">
    <c:forEach items="${newestBooks}" var="book">
        <div class="book-card">
            <img    src="${pageContext.request.contextPath}/resources/images/${book.image}"
                    alt="${book.title}"
                    onerror="this.src='';">
            <div class="title">
                ${book.title}
            </div>
            <div class="author">
                Tác giả: ${book.author}
            </div>
            <div class="meta">
                <span>${book.categoryName}</span>
                <span>
                    Còn ${book.availableQuantity}
                </span>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/book/detail/${book.id}" class="btn">
                    Chi tiết
                </a>
                <form
                        action="${pageContext.request.contextPath}/cart/add"
                        method="post"
                        style="flex:1;">
                    <input
                            type="hidden"
                            name="bookId"
                            value="${book.id}">
                    <button
                            type="submit"
                            class="btn btn-dark"
                            style="width:100%;">                        + Giỏ
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<hr class="divider">

<!-- TẤT CẢ SÁCH -->

<h2>Tất cả sách</h2>

<div class="book-grid">

    <c:forEach items="${allBooks}" var="book">

        <div class="book-card">

            <img
                    src="${pageContext.request.contextPath}/resources/images/${book.image}"
                    alt="${book.title}"
                    onerror="this.src='';">

            <div class="title">
                ${book.title}
            </div>

            <div class="author">
                Tác giả: ${book.author}
            </div>

            <div class="meta">

                <span>${book.categoryName}</span>

                <span>
                    Còn ${book.availableQuantity}
                </span>

            </div>

            <div class="actions">

                <a
                        href="${pageContext.request.contextPath}/book/detail/${book.id}"
                        class="btn">

                    Chi tiết

                </a>

                <form
                        action="${pageContext.request.contextPath}/cart/add"
                        method="post"
                        style="flex:1;">

                    <input
                            type="hidden"
                            name="bookId"
                            value="${book.id}">

                    <button
                            type="submit"
                            class="btn btn-dark"
                            style="width:100%;">

                        + Giỏ

                    </button>

                </form>

            </div>

        </div>

    </c:forEach>

</div>


</div>

</body>
</html>
