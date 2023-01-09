

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
//复位
function resetSearch() {
    $('#query-form').find('[name]').each(function () {
        $(this).val('');
    });
}


//删除id为ids的数据集合
function deleteSourceFuc() {
    var ids = getIds($("#sourceTable"));
    if (ids != '' && ids != null) {
        var data = {"ids": ids};
        Ewin.confirm(
            {message: "确认要删除选择的数据吗？"
            }).on(
            function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    url: '/example/source/deleteSource',
                    type: 'POST',
                    dataType: 'json',
                    data: data,
                }).done(function (data) {
                    if (data.rs) {
                        toastr.success(data.msg);
                        toList('/example/source/sourceListPage');
                    }else {
                        toastr.error(data.msg);
                    }
                }).fail(function () {
                    tips(false,data.msg)

                });
            });
    }
}
/**
/**
 * 选择上传类型
 * @param type
 */
function selectSource(type){
    if (type===1){
        $('#type').val(0);
        $("#uploadPicSourceBtn").css("background-color", "red");
        $("#uploadVidSourceBtn").css("background-color", "yellow");
        $('#sourceStatus').show();
    }
    if (type===2){
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
        uploadFile($('#pic_source_file'),"/example/source/uploadSource",$("#display_img_resource"),$('#source_url'));
    }

}

//保存资源
function saveSource(){
    var type = $("#type").val();
    if (type == null){
        type === 1;
    }
    console.log("----------->",type);
    var data={
        id:$("#id").val(),
        type:type,
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
             image ='<div>' ;
                 if(row.type==='3'){
                    image+='<a href="/example/source/playSourcePage/'+row.id+'/'+row.sourceName+'">' ;
                 }
                image+= '<img style="width: 420px;height: 220px;margin: auto 12%" src="'+ row.url +'"/>';
                 if (row.type==='3'){
                     image+='</a>';
                 }
                image+='<span   class="glyphicon glyphicon-play-circle" style="color: white; margin-left: -40%;">' +
                    '</span>' +
                    '</div>';
            }
            return image;
        },
        sortables: true
    }, {
        field: 'createTime',
        title: '资源类型',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.type == '0') {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>图片</button>";
            } else if (row.type == '3') {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>视频</button>";
            }
        }
    }, {
        field: 'createTime',
        title: '应用类型',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.applyStatus == 0) {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>普通</button>";
            } else if (row.applyStatus == 1) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>主页</button>";
            } else if (row.applyStatus == 2) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>主页选项</button>";
            }else if (row.applyStatus == 3) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>备用</button>";
            }else if (row.applyStatus == 4) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>相册</button>";
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
 * 转资源列表
 */
function sourceList() {
    toList('/example/source/sourceListPage');
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
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="downloadFile(\'' + row.id + '\')"><i class="fa fa-edit"></i> 下载</a> ');
    return  actions.join('');
}

/**
 * 下载
 * @param id
 */
function downloadFile(id){
    toList("/example/source/downloadFile/"+id);
}
