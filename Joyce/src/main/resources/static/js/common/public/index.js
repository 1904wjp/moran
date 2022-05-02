$().ready(function (){
    indexLoadBackGround();
});

/**
 * 设置index背景
 */
function indexLoadBackGround(){
    $("#index_content").hide();
    $("#index_content").fadeIn(5000);
    $.ajax({
        url: '/example/source/getIndexVideo',
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        if (data.rs){
        //  appendVideo($("#index_video"),"index_v",data.data.url);
        }else {
            tips(data.rs,data.msg);
        }
    }).fail(function () {
      /*  alert("获取资源失败");*/
    });
}

