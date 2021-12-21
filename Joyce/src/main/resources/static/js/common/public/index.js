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
        url: '/example/sysMenu/getIndexVideo',
        type: 'GET',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
          appendVideo($("#index_video"),"index_v",data.data.menuUrl);
        }else {
            alert(data.msg);
        }
    }).fail(function () {
      /*  alert("获取资源失败");*/
    });
}

