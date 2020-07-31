function post(){
    var id = $("#question_id").val();
    var content1 = $("#question_comment").val();
    var type = 1;
    var data = 'parentId='+id+'&'+'content='+content1+'&'+'type='+type;
    console.log(data);
    $.ajax({
        type: "POST",
        url: "/comment",
        data: data,
        success: function (json){

        },
        dataType: "json"
    });
}