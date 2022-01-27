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

        }).done(function (data) {
            if (data.rs) {
               tpis(rs,data.msg)
            } else {
                tpis(data.rs,data.msg)
            }
        }).fail(function () {
            tpis(false,data.msg)
        });
        });
    }
}
