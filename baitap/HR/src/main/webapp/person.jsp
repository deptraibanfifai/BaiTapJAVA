<!DOCTYPE html>
<html>
<head>
<title>Person Form</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
body {
    background: linear-gradient(135deg,#ff9a9e,#fad0c4);
    font-family: Arial;
}
.box {
    width: 400px;
    margin: 50px auto;
    background: white;
    padding: 20px;
    border-radius: 12px;
}
input, textarea {
    width: 100%;
    padding: 10px;
    margin: 8px 0;
}
button {
    background: #ff6a88;
    color: white;
    padding: 10px;
    border: none;
    width: 100%;
}
#help {
    background: #eee;
    padding: 10px;
    margin-top: 10px;
}
</style>

<script>
function validate(){
    let name = $("input[name='firstName']").val();
    if(name === "" || /\d/.test(name)){
        alert("First name invalid!");
        return false;
    }
    return true;
}

function loadHelp(){
    $("#help").load("help.jsp");
}
</script>

</head>

<body>

<div class="box">
<h2>Add Person</h2>

<form action="AddPersonServlet" method="post" onsubmit="return validate()">
<input name="firstName" placeholder="First Name">
<input name="lastName" placeholder="Last Name">
<input name="phone" placeholder="Phone">
<input name="email" placeholder="Email">
<input name="hobbies" placeholder="Hobbies">
<textarea name="description" placeholder="Description"></textarea>
<button>Add</button>
</form>

<br>
<button onclick="loadHelp()">Help to register</button>

<div id="help"></div>

<br>
<a href="search.jsp">Go to Search</a>

</div>

</body>
</html>