$(function () {
    $.ajax({
        url: '/example/aboutUs/aboutUsData',
        type: 'GET',
        dataType: 'json',

    }).done(function (data) {
        if (data.rs){
            outputQRCod2(data.data, 300, 300);
        }else {
            tips(data.rs,data.msg);
        }
    }).fail(function () {
        tips(false,"获取资源失败");
    });
});