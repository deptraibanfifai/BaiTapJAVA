<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<style>
body {
    background: linear-gradient(135deg, #ff758c, #ff7eb3);
    font-family: Arial;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}
.box {
    background: white;
    padding: 30px;
    border-radius: 12px;
    width: 300px;
    text-align: center;
}
input {
    width: 90%;
    padding: 10px;
    margin: 10px 0;
}
button {
    background: #ff758c;
    color: white;
    border: none;
    padding: 10px;
    width: 100%;
    border-radius: 6px;
}
</style>
</head>

<body>
<div class="box">
<h2>Register</h2>

<form action="RegisterServlet" method="post">
<input name="username" placeholder="Username"><br>
<input type="password" name="password" placeholder="Password"><br>
<input name="email" placeholder="Email"><br>
<button>Register</button>
</form>

<a href="login.jsp">Back</a>
</div>
</body>
</html>