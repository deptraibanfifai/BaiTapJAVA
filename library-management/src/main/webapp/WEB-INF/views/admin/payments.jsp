<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPage" value="payments"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý doanh thu</title>

    <style>

        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
            font-family:Arial,sans-serif;
        }

        body{
            background:#f5f5f5;
        }

        .page-title{
            font-size:24px;
            margin-bottom:20px;
            color:#000;
        }

        .stats{
            display:flex;
            gap:20px;
            margin-bottom:25px;
        }

        .stat-box{
            flex:1;
            background:white;
            border:1px solid #ddd;
            padding:20px;
            border-radius:6px;
        }

        .stat-title{
            color:#555;
            margin-bottom:10px;
        }

        .stat-value{
            font-size:30px;

            color:#000;
        }

        .card{
            background:white;
            border:1px solid #ddd;
            border-radius:6px;
            padding:20px;
        }

        table{
            width:100%;
            border-collapse:collapse;
        }

        th{
            background:#f2f2f2;
            padding:10px;
            text-align:left;
        }

        td{
            padding:10px;
            border-top:1px solid #eee;
        }

        tr:hover{
            background:#fafafa;
        }

        .money{
            color:#000;
            font-weight:bold;
        }

    </style>
</head>
<body>

<div class="admin-layout">

    <%@ include file="_sidebar.jsp" %>

    <div class="admin-content">

        <div class="page-title">
            Quản lý doanh thu
        </div>

        <div class="stats">

            <div class="stat-box">
                <div class="stat-title">
                    Doanh thu tháng hiện tại
                </div>

                <div class="stat-value">
                    ${monthlyRevenue} VNĐ
                </div>
            </div>

            <div class="stat-box">
                <div class="stat-title">
                    Doanh thu năm hiện tại
                </div>

                <div class="stat-value">
                    ${yearlyRevenue} VNĐ
                </div>
            </div>

        </div>

        <div class="card">

            <h3 style="margin-bottom:15px">
                Danh sách giao dịch thuê sách
            </h3>

            <table>

                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Người thuê</th>
                    <th>Ngày thuê</th>
                    <th>Phí thuê</th>
                    <th>Phí trễ</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${rentals}" var="r">

                    <tr>

                        <td>${r.id}</td>

                        <td>${r.username}</td>

                        <td>${r.rentalDate}</td>

                        <td class="money">
                            ${r.rentalFee}
                        </td>

                        <td class="money">
                            ${r.lateFee}
                        </td>

                        <td class="money">
                            ${r.totalAmount}
                        </td>

                        <td>
                            ${r.status}
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