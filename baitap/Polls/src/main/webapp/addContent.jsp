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
    background: #eef2ff;
}
.box {
    width: 400px;
    margin: 50px auto;
    background: white;
    padding: 20px;
    border-radius: 10px;
}
input, textarea {
    width: 100%;
    margin: 10px 0;
    padding: 10px;
}
button {
    background: #4CAF50;
    color: white;
    padding: 10px;
    border: none;
    width: 100%;
}
</style>
</head>

<body>

<div class="box">
<h2>Add Content</h2>

<form action="AddContentServlet" method="post">
<input name="title" placeholder="Title">
<input name="brief" placeholder="Brief">
<textarea name="content" placeholder="Content"></textarea>
<button>Add</button>
</form>

<a href="index.jsp">Back</a>
</div>

</body>
</html>