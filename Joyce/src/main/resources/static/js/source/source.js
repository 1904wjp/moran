

$().ready(function () {
    $("#uploadPicSourceBtn").css("background-color", "red");
});
/**

/**
 * 选择上传类型
 * @param type
 */
function selectSource(type){
    if (type==1){
        $('#type').val(1);
        $("#uploadPicSourceBtn").css("background-color", "red");
        $("#uploadVidSourceBtn").css("background-color", "yellow");
        $('#sourceStatus').show();
    }
    if (type==2){
        $('#sourceStatus').hide();
        $('#type').val(3);
        $("#uploadPicSourceBtn").css("background-color", "blue");
        $("#uploadVidSourceBtn").css("background-color", "red");
    }
}
/**
 * 上传资源
 */
function uploadSource(){
     var def = $('#type').val();

    if(def==='1'){
        uploadFile($('#pic_source_file'),"/example/source/uploadSource",$("#display_img_resource"),"source_url");
    }
    if (def==='3'){
        var file = $("#pic_source_file")[0].files[0];
        uploadVideoFile(file,0,guid(),"/example/source/uploadVideoSource","/example/source/mergeVideoSource", $("#display_img_resource"));
    }
}

//保存资源
function saveSource(){
    var data={
        id:$("#id").val(),
        type:$("#type").val(),
        url: $("#source_url").val(),
        sourceName: $("#sourceName").val(),
        sort: $("#sort").val(),
        vUrl: $("#vUrl").val(),
        descContent: $('#descContent').val(),
        applyStatus: $("#applyStatus option:selected").val()
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
            tips(data.rs,data.msg);
            if (data.rs){
                $("#id").val('');
                $("#type").val('');
                $("#source_url").val('');
                $("#sourceName").val('');
                $("#sort").val('');
                $('#descContent').val('');
                $("#applyStatus option:selected").val('');
                $("#src_video").empty();
            }
        }).fail(function () {
            tips(false,ajaxFailMsg);
        });
        });
    }
}
