<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng — LibraryMS</title>
<style>
    *{
        box-sizing:border-box;
        margin:0;
        padding:0;
        font-family:Arial,sans-serif;
        font-size:14px;
    }

    body{
        background:#f5f5f5;
    }

    .container{
        max-width:1000px;
        margin:0 auto;
        padding:20px;
    }

    h2{
        font-size:18px;
        margin-bottom:16px;
    }

    .msg-error{
        background:#fff0f0;
        border:1px solid #f5c6cb;
        padding:8px;
        margin-bottom:12px;
        color:#c00;
    }

    .msg-success{
        background:#f0fff0;
        border:1px solid #b2dfdb;
        padding:8px;
        margin-bottom:12px;
        color:#2e7d32;
    }

    .empty{
        text-align:center;
        padding:50px;
        color:#888;
    }

    .layout{
        display:flex;
        gap:20px;
    }

    .cart-list{
        flex:1;
    }

    .cart-item{
        background:#fff;
        border:1px solid #ddd;
        padding:12px;
        display:flex;
        gap:12px;
        align-items:center;
        margin-bottom:8px;
    }

    .cart-item img{
        width:55px;
        height:70px;
        object-fit:cover;
        border:1px solid #eee;
        background:#eee;
    }

    .item-info{
        flex:1;
    }

    .item-title{
        font-weight:bold;
        margin-bottom:3px;
    }

    .item-author{
        color:#666;
        font-size:12px;
    }

    .item-price{
        margin-top:4px;
    }

    .remove a{
        color:#c00;
        text-decoration:none;
    }

    .summary{
        width:260px;
    }

    .summary-box{
        background:#fff;
        border:1px solid #ddd;
        padding:15px;
    }

    .summary-box h3{
        margin-bottom:12px;
    }

    .summary-row{
        display:flex;
        justify-content:space-between;
        margin-bottom:8px;
    }

    .btn{
        padding:8px 12px;
        border:1px solid #ccc;
        background:#fff;
        cursor:pointer;
        width:100%;
        text-align:center;
        text-decoration:none;
        display:block;
        margin-bottom:8px;
    }

    .btn-dark{
        background:#333;
        color:#fff;
        border-color:#333;
    }

    .checkbox{
        transform:scale(1.2);
    }

    @media(max-width:700px){
        .layout{
            flex-direction:column;
        }

        .summary{
            width:100%;
        }
    }
</style>
</head>
<body>

<%@ include file="../_navbar.jsp" %>

<div class="container">

<h2>Giỏ hàng</h2>

<c:if test="${not empty success}">
    <div class="msg-success">${success}</div>
</c:if>

<c:if test="${not empty error}">
    <div class="msg-error">${error}</div>
</c:if>

<c:choose>

    <c:when test="${empty cartItems}">
        <div class="empty">
            <p>Giỏ hàng trống</p>
            <br>
            <a href="${pageContext.request.contextPath}/search">
                Khám phá sách
            </a>
        </div>
    </c:when>

<c:otherwise>

<div class="layout">

<form action="${pageContext.request.contextPath}/cart/checkout"
      method="post"
      style="display:flex;width:100%;gap:20px;">

    <div class="cart-list">

        <c:forEach var="item" items="${cartItems}">

            <div class="cart-item">

                <input type="checkbox"
                       class="checkbox"
                       name="cartIds"
                       value="${item.id}"
                       checked>

                <img src="${pageContext.request.contextPath}/resources/images/${item.bookImage}"
                     alt="${item.bookTitle}"
                     onerror="this.style.background='#e0e0e0';this.src=''">

                <div class="item-info">
                    <div class="item-title">${item.bookTitle}</div>
                    <div class="item-author">${item.bookAuthor}</div>
                    <div class="item-price">${item.bookPrice} đ</div>
                </div>

                <div class="remove">
                    <a href="${pageContext.request.contextPath}/cart/remove/${item.id}"
                       onclick="return confirm('Xóa sách này?')">
                        Xóa
                    </a>
                </div>

            </div>

        </c:forEach>

    </div>

    <div class="summary">

        <div class="summary-box">

            <h3>Tóm tắt đơn</h3>

            <div class="summary-row">
                <span>Tổng sách:</span>
                <strong>${cartItems.size()} cuốn</strong>
            </div>

            <div class="summary-row">
                <span>Tổng tiền sách:</span>
                <strong id="totalPrice">${totalPrice} đ</strong>
            </div>

            <div class="summary-row">
                <span>Số ngày thuê:</span>
                <strong id="rentDays">0</strong>
            </div>

            <div class="summary-row">
                <span>Tiền thuê:</span>
                <strong id="rentalFee">0 đ</strong>
            </div>
            <input
                type="hidden"
                id="rentalFeeInput"
                name="rentalFee"
                value="0">

            <hr style="margin:10px 0;">

            <div style="margin-bottom:10px;">
                <label>Ngày thuê</label>

                <input
                        type="date"
                        id="rentalDate"
                        name="rentalDate"
                        required
                        value="<%= java.time.LocalDate.now() %>"
                        style="width:100%;padding:8px;">
            </div>

            <div style="margin-bottom:10px;">
                <label>Ngày trả dự kiến</label>

                <input
                        type="date"
                        id="dueDate"
                        name="dueDate"
                        required
                        style="width:100%;padding:8px;">
            </div>



            <!-- <hr style="margin:10px 0;"> -->

            <h3>Thanh toán trước</h3>

            <div style="text-align:center;">

                <img
                        id="vietqr"
                        src="https://img.vietqr.io/image/MB-0898407761-compact2.png"
                        width="220">

                <p style="margin-top:10px;">
                    Ngân hàng MB Bank
                    <br>
                    STK: 0898407761
                    <br>
                    Chủ TK: NGUYỄN MINH HIẾU
                </p>

                <p style="color:red;">
                    Vui lòng chuyển khoản trước khi thuê sách
                </p>

            </div>

            <button
                    class="btn btn-dark"
                    type="submit"
                    onclick="return confirm('Tôi đã thanh toán và muốn gửi yêu cầu thuê sách?')">

                Tôi đã thanh toán

            </button>

            <a href="${pageContext.request.contextPath}/search"
               class="btn">
                Thêm sách
            </a>
                </div> <!-- summary-box -->

            </div> <!-- summary -->

        </form>

        </div> <!-- layout -->

        </c:otherwise>
        </c:choose>

        </div> <!-- container -->

        <script>

        const totalBookPrice = ${empty totalPrice ? 0 : totalPrice};

        function calculateRentalFee() {

            const rentalDate =
                document.getElementById("rentalDate").value;

            const dueDate =
                document.getElementById("dueDate").value;

            if (!rentalDate || !dueDate) {
                return;
            }

            const start = new Date(rentalDate);
            const end = new Date(dueDate);

            const diff = end - start;

            const days =
                Math.ceil(diff / (1000 * 60 * 60 * 24));

            if (days <= 0) {

                document.getElementById("rentDays")
                    .innerText = "0";

                document.getElementById("rentalFee")
                    .innerText = "0 đ";

                document.getElementById("rentalFeeInput")
                    .value = 0;

                document.getElementById("vietqr").src =
                    "https://img.vietqr.io/image/MB-0898407761-compact2.png";

                return;
            }

            const rentalFee =
                totalBookPrice * 0.10 * days;

            document.getElementById("rentDays")
                .innerText = days;

            document.getElementById("rentalFee")
                .innerText =
                rentalFee.toLocaleString("vi-VN") + " đ";

            document.getElementById("rentalFeeInput")
                .value = Math.round(rentalFee);

            document.getElementById("vietqr").src =
                "https://img.vietqr.io/image/MB-0898407761-compact2.png"
                + "?amount=" + Math.round(rentalFee)
                + "&addInfo=RENTAL_${sessionScope.user.id}";
        }

        window.onload = function () {

            document.getElementById("rentalDate")
                .addEventListener("change", calculateRentalFee);

            document.getElementById("dueDate")
                .addEventListener("change", calculateRentalFee);

            calculateRentalFee();
        };

        </script>

        </body>
        </html>
