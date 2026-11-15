<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentPage" value="dashboard"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Dashboard — LibraryMS</title>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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

        .page-title{
            font-size:22px;
            font-weight:bold;
            margin-bottom:20px;
        }

        .stats{
            display:flex;
            flex-wrap:wrap;
            gap:15px;
            margin-bottom:25px;
        }

        .stat-box{
            background:#fff;
            border:1px solid #ddd;
            border-radius:6px;
            padding:18px;
            flex:1;
            min-width:180px;
        }

        .stat-box .num{
            font-size:30px;
            font-weight:bold;
            margin-bottom:5px;
        }

        .stat-box .lbl{
            color:#666;
        }

        .card{
            background:#fff;
            border:1px solid #ddd;
            border-radius:6px;
            padding:16px;
            margin-bottom:20px;
        }

        .card-title{
            font-size:16px;
            font-weight:bold;
            margin-bottom:15px;
        }

        .header-row{
            display:flex;
            justify-content:space-between;
            align-items:center;
            margin-bottom:12px;
        }

        table{
            width:100%;
            border-collapse:collapse;
        }

        th{
            background:#f0f0f0;
            padding:10px;
            text-align:left;
        }

        td{
            padding:10px;
            border-bottom:1px solid #eee;
        }

        tr:hover td{
            background:#fafafa;
        }

        .badge{
            padding:4px 8px;
            font-size:12px;
            border-radius:4px;
        }

        .badge-pending{
            background:#fff3cd;
        }

        .badge-renting{
            background:#d1ecf1;
        }

        .badge-returned{
            background:#d4edda;
        }

        .badge-overdue{
            background:#f8d7da;
        }

        .btn{
            padding:5px 10px;
            border:1px solid #ccc;
            text-decoration:none;
            color:#333;
            background:#fff;
        }

        .btn:hover{
            background:#eee;
        }

        .btn-sm-primary{
            background:#e8f4ff;
        }

        .btn-sm-success{
            background:#d4edda;
        }

        #revenueChart{
            height:120px !important;
        }

    </style>
</head>

<body>

<div class="admin-layout">

    <%@ include file="_sidebar.jsp" %>

    <div class="admin-content">

        <div class="page-title">
            Dashboard
        </div>

        <!-- THỐNG KÊ -->

        <div class="stats">

            <div class="stat-box">
                <div class="num">${totalBooks}</div>
                <div class="lbl">Đầu sách</div>
            </div>

            <div class="stat-box">
                <div class="num">${totalUsers}</div>
                <div class="lbl">Thành viên</div>
            </div>

            <div class="stat-box">
                <div class="num">${totalRentals}</div>
                <div class="lbl">Tổng đơn thuê</div>
            </div>

            <div class="stat-box">
                <div class="num">${availableBooks}</div>
                <div class="lbl">Sách còn sẵn</div>
            </div>

            <div class="stat-box">
                <div class="num">${rentingBooks}</div>
                <div class="lbl">Sách đang thuê</div>
            </div>

            <div class="stat-box">
                <div class="num">${overdueRentals}</div>
                <div class="lbl">Đơn quá hạn</div>
            </div>

            <div class="stat-box">
                <div class="num">${monthlyRevenue}</div>
                <div class="lbl">Doanh thu tháng</div>
            </div>

        </div>

        <!-- BIỂU ĐỒ -->

        <div class="card">

            <div class="card-title">
                Biểu đồ doanh thu theo tháng
            </div>

            <canvas id="revenueChart"></canvas>

        </div>

        <!-- ĐƠN THUÊ -->

        <div class="card">

            <div class="header-row">

                <div class="card-title">
                    Đơn thuê gần đây
                </div>

                <a href="${pageContext.request.contextPath}/admin/rentals"
                   class="btn">
                    Xem tất cả
                </a>

            </div>

            <table>

                <thead>
                <tr>
                    <th>#</th>
                    <th>Người thuê</th>
                    <th>Ngày thuê</th>
                    <th>Hạn trả</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="r" items="${recentRentals}">

                    <tr>

                        <td>${r.id}</td>
                        <td>${r.username}</td>
                        <td>${r.rentalDate}</td>
                        <td>${r.dueDate}</td>

                        <td>

                            <c:choose>

                                <c:when test="${r.status=='PENDING'}">
                                    <span class="badge badge-pending">
                                        Chờ xác nhận
                                    </span>
                                </c:when>

                                <c:when test="${r.status=='RENTING'}">
                                    <span class="badge badge-renting">
                                        Đang thuê
                                    </span>
                                </c:when>

                                <c:when test="${r.status=='RETURNED'}">
                                    <span class="badge badge-returned">
                                        Đã trả
                                    </span>
                                </c:when>

                                <c:otherwise>
                                    <span class="badge badge-overdue">
                                        Quá hạn
                                    </span>
                                </c:otherwise>

                            </c:choose>

                        </td>

                        <td>

                            <c:if test="${r.status=='PENDING'}">

                                <a href="${pageContext.request.contextPath}/admin/rentals/confirm/${r.id}"
                                   class="btn btn-sm-primary">
                                    Xác nhận
                                </a>

                            </c:if>

                            <c:if test="${r.status=='RENTING'}">

                                <a href="${pageContext.request.contextPath}/admin/rentals/return/${r.id}"
                                   class="btn btn-sm-success">
                                    Trả sách
                                </a>

                            </c:if>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </div>

</div>

<script>

new Chart(
    document.getElementById('revenueChart'),
    {
        type: 'bar',
        data: {
            labels: [
                'T1','T2','T3','T4',
                'T5','T6','T7','T8',
                'T9','T10','T11','T12'
            ],
            datasets: [{
                label: 'Doanh thu',
                data: [
                    ${revenueData[0]},
                    ${revenueData[1]},
                    ${revenueData[2]},
                    ${revenueData[3]},
                    ${revenueData[4]},
                    ${revenueData[5]},
                    ${revenueData[6]},
                    ${revenueData[7]},
                    ${revenueData[8]},
                    ${revenueData[9]},
                    ${revenueData[10]},
                    ${revenueData[11]}
                ]
            }]
        }
    }
);

</script>

</body>
</html>