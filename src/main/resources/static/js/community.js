function post() {
    var id = $("#question_id").val();
    var content = $("#question_comment").val();
    comment(id, 1, content);

}


function comment(parentId, type, content) {
    var data = {
        "parentId": parentId,
        "type": type,
        "content": content
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (json) {
            if (json.code == 200) {
                window.location.reload();
                $("#comment_section").hide();
            } else {
                if (json.code == 2003) {
                    var isAccept = confirm(json.message);
                    if (isAccept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.17b170297832cc61&redirect_uri=http://localhost:9999/callback&scope=user&state=11");
                        window.localStorage.setItem("closeable", "true")
                    }
                } else {
                    alert(json.message);
                }
            }

        }

    });
}

function reply(e) {
    var id = e.getAttribute("data-reply-id");

    var content = $("#reply" + id).val();

    comment(id, 2, content);
}


function showSecondComment(e) {
    var id = e.getAttribute("data-id")
    var comments = $("#comment-" + id);
    var collapse = e.getAttribute("data-collapse")
    if (collapse) {

        comments.removeClass("in")
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
        $('#comment-'+id).empty()
    } else {

        $.getJSON("/comment/" + id, function (json) {

                for (var i = 0; i < json.data.length; i++) {
                    var div = '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 content-section"\n' +
                        '                                 >\n' +
                        '                                <div class="media">\n' +
                        '                                    <div class="media-left">\n' +
                        '                                        <a href="#">\n' +
                        '                                            <img class="media-object img-circle" src=":avatar:">\n' +
                        '                                        </a>\n' +
                        '                                    </div>\n' +
                        '                                    <div class="media-body">\n' +
                        '                                        <h5 class="media-heading"><span>:username:</span></h5>\n' +
                        '                                        <div >:content:</div>\n' +
                        '                                        <div class="menu">\n' +
                        '                                            <span class="pull-right"\n' +
                        '                                                  >:time:</span>\n' +
                        '                                        </div>\n' +
                        '                                    </div>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>'
                    div = div.replace(':id:', 'comment-' + id);
                    div = div.replace(':avatar:', json.data[i].user.avatar);
                    div = div.replace(':username:', json.data[i].user.name);
                    div = div.replace(':content:', json.data[i].content);
                    var time = getMyDate(json.data[i].gmtModified);
                    div = div.replace(':time:', time);

                    $("#comment-"+id).prepend(div)


                }
                var input = '<div class="subs-comment"><input type="text" class="form-control col-lg-12 col-md-12 col-sm-12 col-xs-12 input-reply" id=":id:" data-reply-id=":data-id:" placeholder="评论一下">' +
                    '<button type="button" class="btn btn-success pull-right"   data-reply-id=":data-reply-id:"   onclick="reply(this)" >评论</button></div>'
                input = input.replace(':id:', 'reply' + id);
                input = input.replace(':data-id:', 'data-id' + id);
                input = input.replace(':data-reply-id:', id);
                $("subs-comment").empty();
                $("#comment-" + id).append(input)

            }
        );


        comments.addClass("in")
        e.setAttribute("data-collapse", "in")
        e.classList.add("active")
    }


}


function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}