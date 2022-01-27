$(document).ready(function(){
   addLoadingModal();
   initMainPictures();
});
/**
 * 初始化图片
 */
function initMainPictures(){
   $.ajax({
      url: '/getSourceImage',
      type: 'GET',
      dataType: 'json',
   }).done(function (data) {
      loading(false);
      if (data.rs){
            $('.intro__title').text(data.data.source.sourceName);
            $('.intro__description__content').text(data.data.source.descContent);
            $('.intro__image').attr("src",data.data.source.url);
         for (let i = 0; i < data.data.list; i++) {
            console.log(i);
            $('.items-wrap').append("<a href=\"#\" class=\"item\">\n" +
                "            <input type='hidden' value='"+data.data.list[i].id+"'/>"+
                "            <img class=\"item__image\" src=\""+data.data.list[i].url+"\" alt=\"item01\"/>\n" +
                "            <h2 class=\"item__title\">data.data.list[i].sourceName</h2>\n" +
                "        </a>");
         }
      }else {
         tpis(data.rs,data.msg);
      };
   }).fail(function (){
      tpis(false,ajaxFailMsg)
   });
}


$(".items-wrap").on('click', '.item', function (){
   var data = {
      "id":$(this).find("input").val(),
      "status": 1
   };
   Ewin.confirm({message: "确认设置成主页壁纸？"}).on(function (e) {
      if (!e){
         return;
      }
      addLoadingModal();
   $.ajax({
      url: '/example/source/setMainSource',
      type: 'GET',
      dataType: 'json',
      data: data,
   }).done(function (data) {
      if(data.rs){
         $('.intro__title').attrs("text",data.data.sourceName);
         $('.intro__description__content').attrs("text",data.data.descContent);
         $('.intro__image').attrs("src",data.data.url);
      }
      loading(false);
      tips(data.rs,data.msg);
   }).fail(function (){
      loading(false);
      tips(false,ajaxFailMsg);
   });
  });
});