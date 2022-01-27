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
      data: data,
   }).done(function (data) {
      loading(false);
      if (data.rs){
         $.each(data.data,function (key,value){
            $('.intro__title').attrs("text",key.sourceName);
            $('.intro__description__content').attrs("text",key.descContent);
            $('.intro__image').attrs("src",key.url);
            $(value).each(function (){
               $('.items-wrap').append("<a href=\"#\" class=\"item\">\n" +
                   "            <input type='hidden' value='"+this.id+"'/>"+
                   "            <img class=\"item__image\" src=\""+this.url+"\" alt=\"item01\"/>\n" +
                   "            <h2 class=\"item__title\">this.sourceName</h2>\n" +
                   "        </a>");
            });
         });
      }else {
         tpis(data.rs,data.msg);
      };
   }).fail(function (){
      tpis(false,data.msg)
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