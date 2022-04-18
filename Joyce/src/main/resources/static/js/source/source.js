

$().ready(function () {
    $('#video').hide();
    $("#uploadPicSourceBtn").css("background-color", "red");
});
/**
 * 上传资源
 */
$("#slice").change(function (event) {
    var file = $("#slice")[0].files[0];
    uploadVideoFile(file,0,guid(),"/example/source/uploadVideoSource","/example/source/mergeVideoSource");
});

/**
 * 选择上传类型
 * @param type
 */
function selectSource(type){
    if (type==1){
        $('#video').hide();
        $('#picture').show();
        $('#type').val(1);
        $("#uploadPicSourceBtn").css("background-color", "red");
        $('#src_video').hide();
        $('#display_img_resource').show();
        $("#uploadVidSourceBtn").css("background-color", "yellow");
        $('#sourceStatus').show();
    }
    if (type==2){
        $('#picture').hide();
        $('#video').show();
        $('#sourceStatus').hide();
        $('#type').val(2);
        $('#display_img_resource').hide();
        $('#src_video').show()
        $("#uploadPicSourceBtn").css("background-color", "blue");
        $("#uploadVidSourceBtn").css("background-color", "red");
    }
}
/**
 * 上传资源
 */
function uploadSource(){
    uploadFile($('#pic_source_file'),"/example/source/uploadSource",$("#display_img_resource"),"source_url");
}

//保存资源
function saveSource(){
    var data={
        id:$("#id").val(),
        type:$("#type").val(),
        url: $("#source_url").val(),
        sourceName: $("#sourceName").val(),
        sort: $("#sort").val(),
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
