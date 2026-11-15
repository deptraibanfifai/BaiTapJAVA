<%@ page import="model.Member" %>
<%
Member user = (Member) session.getAttribute("user");
if(user == null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<style>
body {
    font-family: Arial;
    background: #f4f6f9;
}
.nav {
    background: #667eea;
    color: white;
    padding: 15px;
}
a {
    display: inline-block;
    margin: 10px;
    padding: 10px;
    background: #667eea;
    color: white;
    text-decoration: none;
    border-radius: 6px;
}
</style>
</head>

<body>

<div class="nav">
Welcome <%= user.getUsername() %>
</div>

<div style="padding:20px">
<a href="addContent.jsp">Add Content</a>
<a href="ListContentServlet">View Content</a>
<a href="LogoutServlet">Logout</a>
</div>

</body>
</html>