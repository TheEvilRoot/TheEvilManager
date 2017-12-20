function handleQuery() {
    var input = $("#query_data_field").val();
    $.ajax({
        url: "http://192.168.0.199/MySQL/getProfile.php",
        data: {data: input},
        success: function (data) {
            var ret = JSON.parse(data)["response"];
            if(ret["error"] !== undefined) {
                $("#root").html("Error. " + ret["error"]);
                return;
            }
            for(var i = 0; i < ret["users"].length; i++) {
                console.log(ret["uesrs"][i]["name"]);
            }
        }
    });
}