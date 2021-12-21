$(document).ready(function (){
    loadBackGround();
})
//变换页面
function loadBackGround(){
    var Img = "../img/error/404bg.png";
    var head500Img = "../img/error/head500.png";
    var txtbg500Img = "../img/error/txtbg.png";
//需要加载的背景图片
    if(document.readyState === "loading" ||document.readyState === "interactive" ||document.readyState === "complete")
    {
        document.body.style.backgroundRepeat = "no-repeat";
        document.body.style.position = "center center" ;
        document.body.style.backgroundAttachment = "fixed";
        document.body.style.backgroundSize = "cover";
        document.body.style.backgroundImage = "URL("+Img+")";
        $('.head500').css("background-image","url("+head500Img+")");
        $('.head500').css("background-repeat", "no-repeat");
        $('.txtbg500').css("background-image","url("+txtbg500Img+")");
        $('.txtbg500').css("background-repeat", "no-repeat");
    }

}