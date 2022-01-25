$(document).ready(function(){
   initMainPictures();
});
/**
 * 初始化图片
 */
function initMainPictures(){
   $.ajax({
      url: '/example/columns/getAllTables',
      type: 'POST',
      dataType: 'json',
      data: data,
   }).done(function (data) {
      loading(false);
      if (data.rs){
         $('#tableName').append('<option value="">请选择表</option>');
         for (let i = 0; i < data.data.length; i++) {
            $('#tableName').append("<option value=\""+data.data[i].tableName+"\">"+data.data[i].tableName+"("+data.data[i].tableComment+")"+"</option>");
         }
      }else {
         toastr.error(data.msg);
      };
   }).fail(function (){
      toastr.error(ajaxFailMsg);
   });
}