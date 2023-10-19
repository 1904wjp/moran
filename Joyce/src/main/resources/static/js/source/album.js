/**
 * 获取资源
 */
function getSource(id) {
    var obj = $('.cube');
    $.ajax({
        url: '/example/source/getAlbum/' + id,
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        if (data.rs) {
          //  console.log(data.data);
            $('#pic').remove();
            $('#in_pic').remove();
            res = data.data;
            //console.log(notNull(res.backGround));
            if (notNull(res.backGround)){
                backGroundSetting(res.backGround);
            }
            $.each(res.map, function (key, value) {
                if (key.includes("out")) {
                    var html = " <div class=\"" + key + "\">\n" +
                               " <img src=\"" + value.url + "\" class=\"pic\"/>\n" +
                               " </div>\n";
                    obj.append(html);
                }
            });

            $.each(res.map, function (key, value) {
                if (key.includes("in")) {
                    var html = " <span class=\"" + key + "\">\n" +
                               " <img src=\"" + value.url + "\" class=\"in_pic\"/>\n" +
                               " </span>\n";
                    obj.append(html);
                }
            });
        }
    }).fail(function () {
        tips(false, ajaxFailMsg);
    });
}


/**
 * 搜索
 */
function albumSearch(){
    $('#albumTable').bootstrapTable("refresh");
}

//资源列表
function getAlbumTables() {
    var obj = $('#albumTable');
    var url = '/example/source/getAlbumList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'name',
        title: '相册名称',
        sortables: true
    }, {
        field: 'total',
        title: '张数',

        sortables: true
    }, {
        field: 'type',
        title: '类型',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.type === '0') {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>内外盒子</button>";
            }
           /* else if (row.status === '2') {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>备用</button>";
            } else {
                return "<button class='btn-secondary'disabled='disabled'style='padding:3px; border:10px;'>未知</button>";
            }*/
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
        formatter: albumOpFormatter//格式化输出
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
function albumOpFormatter(value,row,index){
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="toList(\'/example/source/albumPage/' + row.id + '\')"><i class="fa fa-edit"></i> 查看</a> ');
    return  actions.join('');
}


var imgs = [] ;
var rsLen = 0;
/**
 * 页面上传图片
 * @param size
 * @param lenth
 */
function uploadAlbum(size,lenth){
    var img = []; //创建一个空对象用来保存传入的图片
    var AllowImgFileSize = 101376; //1兆
    $("#inputs").change(function () {
        var fil = this.files;
        //console.logog(fil.length);
        for (var i = 0; i < fil.length; i++) {
            var curr = $("#inputs")[i].files[0].size;
            if (curr > AllowImgFileSize * size) {
                //当图片大于1兆提示
                layer.msg("图片文件大小超过限制 请上传小于99M的文件");
            } else {
                reads(fil[i]);
                img.push($("#inputs")[i].files[0]); //将传入的图片push到空对象中
            }
        }
        if (img.length >= lenth) {
            //判断图片的数量，数量不能超过3张
            $(".uploadDIv").hide();
        } else {
            $(".uploadDIv").show();
        }
        imgs = img;
        rsLen = lenth;
        //console.logog("图片",imgs);
});
}



function reads(fil) {
    var reader = new FileReader();
    reader.readAsDataURL(fil);
    var number = Math.random();
    reader.onload = function () {
        document.getElementById("uploadBox").innerHTML +=
            "<div class='divImg' id='"+number+"'>"+
            "<img src='" +
            reader.result +
            "' class='imgBiMG'></div>";
    };
}



function saveAlbum(){
   if (imgs.length === rsLen){
       saveAndUpload();
   }else {
       tips(false,"图片必须时"+rsLen+"张");
   }

}

function saveAndUpload(){
    var formData = new FormData();
    for (let i = 0; i < imgs.length ; i++) {
        formData.append("files",imgs[i]);
    }
    //console.logog("--",formData);
    addLoadingModal("请稍后...正在上传资源");
    $.ajax({
        url: "/example/source/files",
        type: "POST",
        //上传格式为formData
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            tips(data.rs, data.msg);
            if (data.rs){
                loading(false);
                var obj = $('#albumPath');
                obj.val(data.data);
                if (notNull(obj.val())){
                    $('#add_article_info').modal('hide');
                    var urls = $('#albumPath').val().split(",");
                    //console.logog(urls,"::::",urls.length);
                    var album = {
                        name:$('#in_title').val()==null?('#in_title').val()==null:"album"+getRandom(0,20000),
                        total:imgs.length,
                        type:0,
                        sourceConfig:{},
                        sources:[],
                        albumDesc: $('#albumDesc').val(),
                        userId: 0,
                        map:{},
                        sourceUrls:urls
                    };
                    //console.logog(album);
                    Ewin.confirm({message: "确认提交数据源？"}).on(function (e) {
                        if (!e) {
                            return;
                        }
                        $.ajax({
                            url: '/example/source/saveAlbum',
                            type: 'POST',
                            dataType: 'json',
                            data: album,
                        }).done(function (data) {
                            tips(data.rs, data.msg);
                            toList("/example/source/albumListPage");
                        }).fail(function () {
                            tips(false, data.msg)
                        });
                    });
                }
            }
        }
    });
    loading(false);
}

function albumSave(){
    $("#add_article_info").modal('show');
}

