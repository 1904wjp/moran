/**
 * 上传资源
 */
function uploadSource(){
    uploadFile($('#source_file'),"/example/source/uploadSource",$("#display_img_resource"),"source_url");
}
//保存资源
function saveSource(){
    console.log("url:"+$("#source_url").val());
    var data={
        id:$("#id").val(),
        url: $("#source_url").val(),
        sourceName: $("#sourceName").val(),
        sort: $("#sort").val(),
    };
    if (confirm("确认提交数据?")==false){
        return false;
    }
    $.ajax({
        url: '/example/source/saveSource',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs){
        }
        alert(data.msg);
    }).fail(function () {

    });
}
