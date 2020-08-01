function post(){
    var id = $("#question_id").val();
    var content = $("#question_comment").val();
    var type = 1;
    var data = {
        "parentId":id,
        "type":1,
        "content":content
    }
    console.log(data);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (json){
            if(json.code == 200){
                $("#comment_section").hide();
            }else{
                if(json.code == 2003){
                    var isAccept = confirm(json.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.17b170297832cc61&redirect_uri=http://localhost:9999/callback&scope=user&state=11");
                        window.localStorage.setItem("closeable","true")
                    }
                }else{
                    alert(json.message);
                }



            }

        }

    });
}