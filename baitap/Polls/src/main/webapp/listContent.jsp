<%@ page import="java.util.*, model.Content" %>

<%
List<Content> list = (List<Content>) request.getAttribute("list");
%>

<!DOCTYPE html>
<html>
<head>
<style>
body {
    font-family: Arial;
    background: #f8fafc;
}
table {
    width: 80%;
    margin: auto;
    border-collapse: collapse;
}
th {
    background: #667eea;
    color: white;
}
td, th {
    padding: 10px;
    border: 1px solid #ddd;
}
h2 {
    text-align: center;
}
</style>
</head>

<body>

<h2>Content List</h2>

<table>
<tr>
    <th>Title</th>
    <th>Brief</th>
</tr>

<%
if(list != null){
    for(Content c : list){
%>
<tr>
    <td><%= c.getTitle() %></td>
    <td><%= c.getBrief() %></td>
</tr>
<%
    }
}
%>

</table>

<div style="text-align:center; margin-top:20px;">
<a href="index.jsp">Back</a>
</div>

</body>
</html>