$(document).ready(function(){
    stopFuc();
    enterFuc();
});
var startIndex=0;
/**
 * 停止
 */
function stopFuc(){
        //  监听 ctrl + c事件
        $('#in').unbind('keydown').bind('keydown', function(e){
            if(e.ctrlKey && e.keyCode  == 67) {
                $('.stop').val(0);
                $('#in').val("Terminate batch operation(y/n):");
                // 返回false, 防止重复触发copy事件
                return false;
            }
        });
}

    /**
     * 回车事件
     */
function enterFuc(){
    $("#in").keyup(function(event){
        if(event.keyCode ==13){
            var data = {
                "code":$("#in").val()
            }
            $.ajax({
                type: "POST",
                url: "/doMainInit",
                data: data,
                success: function(data){
                    var str =data.data;
                    optionCmdFuc(startIndex,str);
                }
            });
        }
    });
    $("#in")[0].focus();
}


function optionCmdFuc(startIndex,data){
    for (let i = startIndex; i < data.length; i++) {
        if (notBlank($("#in").val())){
            $(".cmd").append("<li class='cmd_li'>"+$("#in").val()+"</li>"); //将输入的输出到界面
        }
        $("#in").val("");
        if (data[i]!="########********###"){
            (function (i) {
                setTimeout(function () {
                    if ($('.stop').val()==2){
                        //获取返回值并输出
                        $(".cmd").append("<li class='cmd_li'>"+data[i]+"</li>");
                        if (i==data.length-1){
                            $.ajax({
                                type: "POST",
                                url: "/changeStatus",
                                data: data,
                                success: function(data){
                                }
                            });
                        }
                    }else if ($('.stop').val()==1){
                        $('.stop').val(2);
                    }else if($('.stop').val()==0){
                        startIndex=i;
                    }
                    toBut($('#text')[0]);
                }, 100*Math.ceil(Math.random()*10)*i);
            })(i);
        }else if (data[i]=="########********###::y"){
            $('.stop').val(2);
            optionCmdFuc(startIndex,str);
        }else if (data[i]=="########********###::n"){
            $('.stop').val(1);
        }else {
            $('.cmd_li').remove();
        }

    }
    $("#in").val(""); //清空输入框
    $("#text").scrollTop($("#text").scrollTop()+32);//滚动条拉到最下面，显示出输入框
}
/**
 * 获得创建主页图片
 */
function createStructImageFuc(){
    /* var img = $('#imgTest');
     var canvas = $('#canBoard')[0];
     context = canvas.getContext('2d');
     img.on('load',function(){
         context.drawImage(this, 0, 0);
     });
     $.ajax({
         url: '/doMain',
         type: 'POST',
         dataType: 'json',

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
     });*/

    /**
     * 停止事件
     */
}
