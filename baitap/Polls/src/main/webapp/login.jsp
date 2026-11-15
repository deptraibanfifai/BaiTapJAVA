<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<style>
body {
    background: linear-gradient(135deg, #667eea, #764ba2);
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
    background: #667eea;
    color: white;
    border: none;
    padding: 10px;
    width: 100%;
    border-radius: 6px;
}
a { color: #667eea; }
</style>
</head>

<body>
<div class="box">
<h2>Login</h2>

<form action="LoginServlet" method="post">
<input type="text" name="username" placeholder="Username"><br>
<input type="password" name="password" placeholder="Password"><br>
<button>Login</button>
</form>

<p><a href="register.jsp">Create account</a></p>
</div>
</body>
</html>