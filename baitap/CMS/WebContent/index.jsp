<h2>Welcome to CMS</h2>
<input type="text" id="search">
<div id="result"></div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$("#search").keyup(function(){
    let key = $(this).val();
    $.ajax({
        url: "ContentServlet",
        data: {search: key},
        success: function(data){
            $("#result").html(data);
        }
    });
});
</script>
