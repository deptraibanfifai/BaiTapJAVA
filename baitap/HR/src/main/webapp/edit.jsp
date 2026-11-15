<%@ page import="model.Person" %>
<%
Person p = (Person) request.getAttribute("p");
%>

<!DOCTYPE html>
<html>
<head>
<style>
body {
    background: linear-gradient(135deg,#fbc2eb,#a6c1ee);
    font-family: Arial;
}
.box {
    width: 400px;
    margin: 50px auto;
    background: white;
    padding: 20px;
    border-radius: 12px;
}
</style>
</head>

<body>

<div class="box">
<h2>Edit Person</h2>

<form action="EditServlet" method="post">
<input type="hidden" name="id" value="<%=p.getId()%>">

<input name="firstName" value="<%=p.getFirstName()%>">
<input name="lastName" value="<%=p.getLastName()%>">

<button>Save</button>
</form>

<br>
<a href="search.jsp">Cancel</a>

</div>

</body>
</html>