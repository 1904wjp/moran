/**
 * 上传资源
 */
function uploadSource(){
    uploadFile($('#source_file'),"/example/source/uploadSource",$("#display_img_resource"),"source_url");
}
//保存资源
function saveSource(){
    var data={
        id:$("#id").val(),
        url: $("#source_url").val(),
        sourceName: $("#sourceName").val(),
        sort: $("#sort").val(),
    };
    let fData={
        url: $("#source_url").val(),
        sourceName: $("#sourceName").val(),
        sort: $("#sort").val(),
    }
    if (vailDate(fData)) {
        Ewin.confirm({message: "确认要提交数据？"}).on(function (e) {
            if (!e){
                return;
            }
        $.ajax({
            url: '/example/source/saveSource',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            if (data.rs) {
                toastr.success(data.msg);
            } else {
                toastr.error(data.msg);
            }
        }).fail(function () {
            toastr.error(ajaxFailMsg);
        });
        });
    }
}
