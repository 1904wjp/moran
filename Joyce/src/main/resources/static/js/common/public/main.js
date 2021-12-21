$(function() {
    /*$('.headerBar').load("headerBar.html");*/
    createStructImageFuc();
});

/**
 * 获得创建主页图片
 */
function createStructImageFuc(){
    var img = $('#imgTest');
    var canvas = $('#canBoard')[0];
    context = canvas.getContext('2d');
    img.on('load',function(){
        context.drawImage(this, 0, 0);
    });
    $.ajax({
        url: '/doMain',
        type: 'POST',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs){
            img.attr('src',data.data);
        }else {
            img.attr('src','static/img/userImg/file0default0name.png');
        }
    }).fail(function () {
    });

    var imgW = img.width();
    var imgH = img.height();
    canvas.width = imgW;
    canvas.height = imgH;
    $('#btnConvert').click(function(){
        var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
        var pxls = imgData.data;
        var black = 0;
        for (var i = 0, n = pxls.length; i < n; i += 4)
        {
            black = (pxls[i] + pxls[i+1] + pxls[i+2]) / 3;
            pxls[i] = black;
            pxls[i+1] = black;
            pxls[i+2] = black;
        }
        context.putImageData(imgData, 0, 0);
        //音乐
        var m_m = $('#main_music');
        m_m.hide();
    });
}
