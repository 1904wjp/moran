$(function () {

    $.ajax({
        url: '/aboutUsData',
        type: 'GET',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        console.log("sss:",data.rs);
        if (data.rs){
            outputQRCod(data.data, 100, 100);
        }else {
            tips(data.rs,data.msg);
        }
    }).fail(function () {
        tips(false,"获取资源失败");
    });
});