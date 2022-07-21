

$().ready(function () {
    $("#uploadPicSourceBtn").css("background-color", "red");
    getSourceTables();
    /*Modal.hide($('#video_modal'));*/
    $('#video_modal').hide();
});

/**
 * 搜索
 */
function sourceSearch(){
    $('#sourceTable').bootstrapTable("refresh");
}
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
    if (def==='3'){
        var file = $("#pic_source_file")[0].files[0];
        uploadVideoFile(file,0,guid(),"/example/source/uploadVideoSource","/example/source/mergeVideoSource", $("#display_img_resource"));
    }else {
        uploadFile($('#pic_source_file'),"/example/source/uploadSource",$("#display_img_resource"),"source_url");
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
               /* $("#applyStatus option:selected").val('');*/
                $("#src_video").empty();
            }
        }).fail(function () {
            tips(false,ajaxFailMsg);
        });
        });
    }
}

//获取图片


//进入视频

//资源列表
function getSourceTables() {
    var obj = $('#sourceTable');
    var url = '/example/source/getList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'sourceName',
        title: '资源名',
        sortables: true
    }, {
        field: 'url',
        title: '封面',
        formatter:function (value, row, index) {
            var image =  '';
            if (row.vid==''||row.vid==null||row.vid==undefined){
               image='<div>' +
                     '<img style="width: 420px;height: 220px;margin: auto 12%" src="'+ row.url + '"/>' +
                     '</div>';
            }else {
             image ='<div>' +
                    '<img style="width: 420px;height: 220px;margin: auto 12%" src="'+ row.url + '" onclick="'+seeVideo(row)+'"/>' +
                    '<span   class="glyphicon glyphicon-play-circle" style="color: white; margin-left: -40%;">' +
                    '</span>' +
                    '</div>';
            }
            return image;
        },
        sortables: true
    }, {
        field: 'status',
        title: '账号状态',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.status === '1') {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>应用</button>";
            } else if (row.status === '2') {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>备用</button>";
            } else {
                return "<button class='btn-secondary'disabled='disabled'style='padding:3px; border:10px;'>未知</button>";
            }
        }
    }, {
        field: 'createTime',
        title: '创建时间',
        sortables: true
    }, {
        field: 'createBy',
        title: '创建者',
        sortables: true
    }, {
        title: '操作',
        align: 'center',
        formatter: sourceOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
}

/**
 * 按钮
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function sourceOpFormatter(value,row,index){
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editUserFuc(\'' + row.id + '\')"><i class="fa fa-edit"></i> 应用</a> ');
    return  actions.join('');
}

/**
 * 打开弹窗播放视频
 * @param row
 */
function seeVideo(row){
    Modal.show($('#video_modal'));
  /*  Modal.hide($('#video_modal'));*/
    $('#v_source').attr("src",row.vurl);
}