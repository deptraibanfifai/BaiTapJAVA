<%@ page import="java.util.*, model.Person" %>

<!DOCTYPE html>
<html>
<head>
<style>
body {
    background: linear-gradient(135deg,#a1c4fd,#c2e9fb);
    font-family: Arial;
}
table {
    width: 80%;
    margin: auto;
    border-collapse: collapse;
}
th {
    background: #4facfe;
    color: white;
}
td, th {
    padding: 10px;
    border: 1px solid #ddd;
}
</style>
</head>

<body>

<h2 style="text-align:center;">Search Person</h2>

<form action="SearchServlet" style="text-align:center;">
<input name="name">
<button>Search</button>
</form>

<br>

<table>
<tr>
<th>First Name</th>
<th>Last Name</th>
<th>Action</th>
</tr>

<%
List<Person> list = (List<Person>) request.getAttribute("list");

if(list != null){
for(Person p : list){
%>
<tr>
<td>
<a href="EditServlet?id=<%=p.getId()%>">
<%=p.getFirstName()%>
</a>
</td>
<td><%=p.getLastName()%></td>
<td>
<a href="DeleteServlet?id=<%=p.getId()%>">Delete</a>
</td>
</tr>
<% }} %>

</table>

</body>
</html>