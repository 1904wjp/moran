$(document).ready(function (){
    loadBackGround();
})
//变换页面
function loadBackGround(){
    var Img = "../img/error/404bg.png";
    var head404Img = "../img/error/head404.png";
    var txtbg404Img = "../img/error/txtbg.png";
//需要加载的背景图片
    if(document.readyState === "loading" ||document.readyState === "interactive" ||document.readyState === "complete")
    {
        document.body.style.backgroundRepeat = "no-repeat";
        document.body.style.position = "center center" ;
        document.body.style.backgroundAttachment = "fixed";
        document.body.style.backgroundSize = "cover";
        document.body.style.backgroundImage = "URL("+Img+")";
        $('.head404').css("background-image","url("+head404Img+")");
        $('.head404').css("background-repeat", "no-repeat");
        $('.txtbg404').css("background-image","url("+txtbg404Img+")");
        $('.txtbg404').css("background-repeat", "no-repeat");
    }

}