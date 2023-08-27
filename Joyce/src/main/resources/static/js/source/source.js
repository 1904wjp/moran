

$().ready(function () {
    $("#uploadPicSourceBtn").css("background-color", "red");
    getSourceTables();
    /*Modal.hide($('#video_modal'));*/
    $('#video_modal').hide();
   // $('#pic_source_file2').hide();
    $("#pic_source_file2").hide();
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
                        sourceSearch();
                      /*  toList('/example/source/sourceListPage');*/
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
    $("#display_img_resource").show();
    $("#pic_source_file2").hide();
    $("#pic_source_file").show();
    $("#display_img_resource").val('');
    $("#src_video").val('');
    $("#pic_source_file").val('');
    $("#pic_source_file2").val('');
    $(".paer").show();
    if (type===1){
        $('#type').val(0);
        $("#uploadPicSourceBtn").css("background-color", "red");
        $("#uploadVidSourceBtn").css("background-color", "yellow");
        $("#uploadVidPageSourceBtn").css("background-color", "green");
        $('#sourceStatus').show();
        //$('#pic_source_file2').hide();
        //$('#pic_source_file').show();
    }
    if (type===2){
        $('#sourceStatus').hide();
        $('#type').val(3);
        $("#uploadPicSourceBtn").css("background-color", "blue");
        $("#uploadVidSourceBtn").css("background-color", "red");
        $("#uploadVidPageSourceBtn").css("background-color", "green");
        //$('#pic_source_file2').show();
        //$('#pic_source_file').hide();
    }
    if (type===3){
        $(".paer").hide();
        $('#sourceStatus').hide();
        $("#display_img_resource").hide();
        $("#pic_source_file2").show();
        $("#pic_source_file").hide();
        // $('#type').val(3);
        $("#uploadPicSourceBtn").css("background-color", "blue");
        $("#uploadVidSourceBtn").css("background-color", "yellow");
        $("#uploadVidPageSourceBtn").css("background-color", "red");

        //$('#pic_source_file2').show();
        //$('#pic_source_file').hide();
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
        lable:$("#lable").val(),
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
                $("#vUrl").val('');
                $("#lable").val('');
                $('#descContent').val('');
                $('#display_img_resource').attr("src","");
                $('#pic_source_file2').val('');
               /* $("#applyStatus option:selected").val('');*/
                $("#src_video").empty();
            }
        }).fail(function () {
            tips(false,ajaxFailMsg);
        });
        });
    }
}


function uploadSourceD2(e){
    var files = e.target.files;
    // var  relativePath = files[0].webkitRelativePath;
    // var folderName  = relativePath.split('/')[0];
    addLoadingModal("请稍后...正在上传");
    for (let i = 0; i < files.length; i++) {
        var fileType = files[i].name.substr(files[i].name.indexOf("\.")).toLowerCase();
        if (fileType != ".mp4") {
          continue;
        }
        uploadVideoFile2(files[i],0,guid(),"/example/source/uploadVideoSource","/example/source/mergeVideoSource");
    }
    loading(false);
}


/**
 * 执行分片上传
 * @param file
 * @param i
 * @param uuid
 */
function uploadVideoFile2(file, i, uuid, url, mergeUrl) {
    console.log('视频上传中')
    var fileType = file.name.substr(file.name.indexOf("\.")).toLowerCase();
    if (fileType != ".mp4") {
        tips("", "格式必须为mp4");
        return;
    }
    //addLoadingModal("请稍后...正在上传");
    var count = 0;
    var name = file.name, //文件名
        size = file.size, //总大小shardSize = 2 * 1024 * 1024,
        shardSize = 10 * 1024 * 1024, //以2MB为一个分片,每个分片的大小
        shardCount = Math.ceil(size / shardSize); //总片数
    if (i >= shardCount) {
        return;
    }

    //文件总大小，第一次，分片大小//
    var start = i * shardSize;
    var end = start + shardSize;
    var packet = file.slice(start, end); //将文件进行切片
    /* 构建form表单进行提交 */
    var form = new FormData();
    form.append("uuid", uuid);// 前端生成uuid作为标识符传个后台每个文件都是一个uuid防止文件串了
    form.append("data", packet); //slice方法用于切出文件的一部分
    form.append("name", name);
    form.append("totalSize", size);
    form.append("total", shardCount); //总片数
    form.append("index", i + 1); //当前是第几片
    $.ajax({
        url: url,
        type: "POST",
        data: form,
//timeout:"10000", //超时10秒
        async: true, //异步
        dataType: "json",
        processData: false, //很重要，告诉jquery不要对form进行处理
        contentType: false, //很重要，指定为false才能形成正确的Content-Ty
    }).done(function (data) {
        /* 表示上一块文件上传成功，继续下一次 */
        if (data.code === 201) {
            form = '';
            i++;
            uploadVideoFile2(file, i, uuid, url, mergeUrl);
            // tips(null, data.msg);
        } else if (data.code === 500) {
            count++;
            form = '';
            /* 失败后，每2秒继续传一次分片文件 */
            var setIntervalFuc = setInterval(function () {
                uploadVideoFile2(file, i, uuid, url, mergeUrl);
            }, 2000);
            //达到一定错误数量停止
            if (count === 10) {
                clearInterval(setIntervalFuc);
               // loading(false);
                tips(data.rs, data.msg);
                var setIntervalFuc2 = setInterval(function () {
                    location.reload();
                }, 2000);
                clearInterval(setIntervalFuc2);
            }
        } else if (data.code === 200) {
            // tips(data.rs, data.msg);
            mergeVideo2(uuid, name, mergeUrl);
        }
    });

}
/**
 * 视频解析
 * @param uuid
 * @param fileName
 */
function mergeVideo2(uuid, fileName, mergeurl) {
    $.ajax({
        url: mergeurl,
        type: "GET",
        data: {uuid: uuid, newFileName: fileName},
//timeout:"10000", //超时10秒
        async: true, //异步
        dataType: "json",
        success: function (data) {
           // loading(false);
            if (data.rs) {
                // $("#source_url").val(data.data.videoPicturePath);
                // $("#vUrl").val(data.data.videoAccessPath);
                var data2={
                    id:$("#id").val(),
                    type:'3',
                    url: data.data.videoPicturePath,
                    sourceName: guid()+"打包上传",
                    sort: $("#sort").val(),
                    vUrl: data.data.videoAccessPath,
                    descContent: $('#descContent').val(),
                    applyStatus: $("#applyStatus option:selected").val()
                };
                $.ajax({
                    url: '/example/source/saveSource',
                    type: 'POST',
                    dataType: 'json',
                    data: data2,
                }).done(function (data) {
                    tips(data.rs,data.msg);
                }).fail(function () {
                    tips(false,ajaxFailMsg);
                });
                // console.log("src", data.data.videoPicturePath);
                //img.attr("src", data.data.videoPicturePath);
                //清楚上一个添加的
                /* $("#src_video").empty();
                 appendVideo($("#src_video"),"index_v",data.data);*/
            }
            tips(data.rs, data.msg)
        }
    })
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
        field: 'lable',
        title: '标签',
        sortables: true
    }, {
        field: 'url',
        title: '封面',
        formatter:function (value, row, index) {
            var image =  '';
            if (row.vid==''||row.vid==null||row.vid==undefined){
               image='<div>' +
                     '<img style="width: 90%;height: 220px;margin: auto 12%" src="'+ row.url + '"/>' +
                     '</div>';
            }else {
             image ='<div>' ;
                 if(row.type==='3'){
                    var searchWord =  $('#searchWord').val();
                     var type =  $('#type').val();
                     var lable =  $('#lable').val();
                    image+='<a href="/example/source/playSourcePage/'+row.id+'/'+row.sourceName+'/'+type+'/'+lable+'/'+searchWord+'">' ;
                 }
                image+= '<img style="width: 90%;height: 220px;margin: auto 12%" src="'+ row.url +'"/>';
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
    if (row.powLevel==1){
        actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" ' +
            'data-target="#editModal" onclick="downloadFile(\'' + row.id + '\')"><i class="fa fa-edit"></i> 下载</a> ');
    }
    return  actions.join('');
}

/**
 * 下载
 * @param id
 */
function downloadFile(id){
    toList("/example/source/downloadFile/"+id);
}
