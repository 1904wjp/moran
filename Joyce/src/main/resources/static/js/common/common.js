/**
 * 密码框触发器
 * @param id
 */
function password_change(id) {
    var value = document.getElementById(id).value;
    if (value != null && value != '') {
        document.getElementById(id).value = password_md5(value);
    }
}

/**
 * 跳转指定url
 * @param url
 */
function toList(url) {
    setTimeout(function () {
        $(location).attr('href', url);
    }, 1000);
}


/**
 * 跳转主页
 */
function toMain() {
    toList("/main");
}

/**
 * MD5加密
 * @param str
 * @returns {*}
 */
function password_md5(str) {
    /* str = MD5(str)+'558dsda4';*/
    return str;
}

/**
 * 背景url设置
 * @param url
 * @param ap
 */
function backGroundSetting(url,ap) {
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.position = "center center";
    document.body.style.backgroundAttachment = "fixed";
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundImage = "URL(" + url + ")";
     if(null!=ap){
         document.body.style.opacity = 0.4;
        /* document.body.style.opacity = 0.4;
         document.body.style.filter = 'alpha(opacity=' + 80 + ')';*/
     }
}

/**
 * 数据列表通用方法
 * @param tabObj
 * @param url
 * @param queryObj
 * @param columns
 */
function tables(tabObj, url, queryObj, columns) {
    tabObj.css("background", "white");
    tabObj.bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: url, // 服务器数据的加载地址
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10, 25, 50, 100, 500], //可供选择的每页的行数（*）
        strictSearch: true,
        minimumCountColumns: 2, //最少允许的列数
        clickToSelect: true, //是否启用点击选中行
        uniqueId: "ID", //每一行的唯一标识，一般为主键列
        cardView: false,
        detailView: false, //是否显示详细视图
        smartDisplay: false,
        /*search: true, //开启搜索框*/
        searchOnEnterKey: false, //回车后执行搜索
        /* trimOnSearch: true, //完全匹配*/
        trimOnSearch: true, //去除关键词空格
        searchAlign: "right",
        /*searchText:"苹果",*/ //搜索关键字
        /*  customSearch:customSearch, //自定义搜索*/
        queryParams: function queryParams(params) {
            var param = {
                offset: params.offset + 0,// SQL语句起始索引
                pageNumber: params.limit  // 每页显示数量
            };
            queryObj.find('[name]').each(function () {
                var value = $(this).val();
                if (value !== '') {
                    param[$(this).attr('name')] = value;
                }
            });
            return param;
        }, iconSize: 'outline',
        toolbar: '#sliderToolbar',
        icons: {
            refresh: 'glyphicon-repeat',
            toggle: 'glyphicon-list-alt',
            columns: 'glyphicon-list'
        },
        columns: columns
    });
}

/**
 * 获取选中多个选项的id集合
 * @param obj
 * @returns {string}
 */
function getIds(obj) {
    var ids = "";
    var rows = obj.bootstrapTable('getSelections');
    if (rows.length == 0) {
        /* alert("至少选中一项");*/
        toastr.warning('至少选中一项');
    } else {
        $(rows).each(function () {
            ids = ids + this.id + ",";
        });
        ids = ids.substring(0, ids.length - 1);
    }
    return ids;
}

/**
 * 上传单个文件
 * @param file
 * @param restUrl
 * @param img
 * @param objUrl
 * @param size
 */
// 处理上传
function uploadFile(file, restUrl, img, objUrl, size) {

    if (file.val() === '') {
        tips("", "图片不能为空");
        return;
    }
    console.log("图片", file[0].files[0]);
    var formData = new FormData();
    formData.append('file', file[0].files[0]);
    //设置图片类型
    if (!/.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(file.val())) {
        tips("", "图片类型不符合要求");
        return;
    }
    if (isBlank(size)) {
        size = 20;
    }
    //设置图片大小
    if (file[0].size.toFixed(2) >= (size * 1024 * 1024)) {
        tips(false, "上传图片小于" + size + "MB");
        return;
    }
    addLoadingModal("请稍后...正在上传资源");
    $.ajax({
        url: restUrl,
        type: "POST",
        //上传格式为formData
        data: formData,
        contentType: false,
        // jQuery不要去处理发送的数据
        processData: false,
        success: function (data) {
            tips(data.rs, data.msg);
            if (data.rs){
                img.attr("src",data.data);
                objUrl.val(data.data);
                //var pic = document.getElementsByName("picFlag");
                //pic.value=data.data;
               // console.log("---->",pic.value);
            }
        }
    });
    loading(false);
}

/**
 * 上传多个文件
 * @param files
 * @param restUrl
 * @param obj
 */
function uploadFiles(files,restUrl,obj){
    console.log(files,"--",restUrl,"--",obj)
    var formData = new FormData();
    for (let i = 0; i < files.length ; i++) {
        formData.append("files",files[i]);
    }
    console.log("--",formData);
    addLoadingModal("请稍后...正在上传资源");
    $.ajax({
        url: restUrl,
        type: "POST",
        //上传格式为formData
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            tips(data.rs, data.msg);
            if (data.rs){
                obj.val(data.data);
                console.log("---------+---->",obj.val());
            }
        }
    });
    loading(false);
    return flag;
}

/**
 * 添加视频组件
 * @param divObject
 * @param videoId
 * @param src
 */
function appendVideo(divObject, videoId, src) {
    var video = $("<video id=\"" + videoId + "\" class='append_video' autoplay=\"autoplay\" loop=\"loop\" muted=\"muted\"><source src=\"" + src + "\"></video>");
    divObject.append(video);
    /*divObject[0].play();*/
}

/**
 * 设置背景图片
 */
/**
 * 设置成rgb格式
 * @param x
 * @param y
 * @param z
 * @returns {string}
 */
function getRbg(x, y, z) {
    if (x.trim() == '' || x == null) {
        x = '0';
    }
    if (y.trim() == '' || y == null) {
        y = '0';
    }
    if (z.trim() == '' || z == null) {
        z = '0';
    }
    return x + "," + y + "," + z;
}

/**
 * rbg转换hex格式
 * @param rgb(格式“x,y,z”)
 * @returns {string}
 */
function rgb2hex(rgb) {
    var reg = /(\d{1,3}),(\d{1,3}),(\d{1,3})/;
    var arr = reg.exec(rgb);
    var _hex = "#" + hex(arr[1]) + hex(arr[2]) + hex(arr[3]);
    return _hex.toUpperCase();
}

function hex(x) {
    return ("0" + parseInt(x).toString(16)).slice(-2);
}

/**
 * 背景设置颜色
 * @param obj
 * @param color
 */
function setBgColor(obj, color) {
    obj.css("background", color);
}

/**
 * rbg设置
 * @param x_obj
 * @param y_obj
 * @param z_obj
 * @param bgObj
 */
function getRBG(x_obj, y_obj, z_obj, bgObj) {
    x_obj.on('input propertychange', function () {
        x = x_obj.val();
        var color = rgb2hex(getRbg(x, y, z));
        setBgColor(bgObj, color);
        console.log(getRbg(x, y, z));
    });
    y_obj.on('input propertychange', function () {
        y = y_obj.val();
        var color = rgb2hex(getRbg(x, y, z));
        setBgColor(bgObj, color);
        console.log(getRbg(x, y, z));
    });
    z_obj.on('input propertychange', function () {
        z = z_obj.val();
        var color = rgb2hex(getRbg(x, y, z));
        setBgColor(bgObj, color);
        console.log(getRbg(x, y, z));
    });
}

/**
 * echarts的xy遍历list中的属性
 * @param data ajax成功的data
 * @param nameValue 想要遍历的字符串名称字符串数组
 * @param type 类型切换
 * @returns {*[]}
 */
function echarts_x_y_value_list(data, nameValue, type) {
    var datas = [];
    var items = [];
    var exs = new Array();
    var eys = new Array();
    if (type == "pie") {
        for (var i in data.rows) {
            var ex = "";
            var ey = "";
            for (var key in data.rows[i]) {
                if (key == nameValue[0]) {
                    var row = data.rows[i];
                    ex = row[key];
                }
                if (key == nameValue[1]) {
                    var row = data.rows[i];
                    ey = row[key];
                }
            }
            var item = {
                value: ey,
                name: ex
            };
            items.push(item);
        }

    } else {
        for (var i in data.rows) {
            for (var key in data.rows[i]) {
                if (key == nameValue[0]) {
                    var row = data.rows[i];
                    exs.push(row[key]);
                }
                if (key == nameValue[1]) {
                    var row = data.rows[i];
                    eys.push(row[key]);
                }
            }
        }
    }
    datas.push(exs);
    if (type == "pie") {
        datas.push(items);
    } else {
        datas.push(eys);
    }
    datas.push(type);
    return datas;
}

/**
 * 非空
 * @param str
 * @returns {boolean}
 */
function notBlank(str) {
    str = str.replace(/\s/g, "");
    if (null != str && str !== "" && str !== undefined) {
        return true;
    }
    return false;
}

/**
 * 非空
 * @param obj
 * @returns {boolean}
 */
function notNull(obj) {
    if (null != obj && obj !=='') {
        return true;
    }
    return false;
}

/**
 * echarts的xy遍历object中的属性
 * @param data ajax成功的data
 * @param nameValue 想要遍历的字符串名称
 * @returns {*[]}
 */
function echarts_x_y_value_obj(data, nameValue) {
    var datas = new Array()
    for (var key in data.data) {
        if (key == nameValue) {
            var row = data.rows[i];
            datas.push(row[key]);
        }
    }
    return datas;
}

/**
 * 填写验证
 * @param data
 */
function vailDate(data, filter) {
    var flag = false;
    if (filter != null) {
        $.each(data, function (index, itemi) {
            $.each(filter, function (index, itemj) {
                if (!isBlank(itemi) && itemi != itemj) {
                    flag = true;
                }
            });
        });
    } else {
        $.each(data, function (index, item) {
            if (!isBlank(item)) {
                flag = true;
            }
        });
    }

    if (flag == false) {
        toastr.warning("数据未填写完整");
    }
    return flag;
}

/**
 * 当前值为空
 * @param value
 * @returns {boolean}
 */
function isBlank(value) {
    if (value == null || value == '' || value == undefined) {
        return true;
    }
    return false;
}




/**
 * 时间格式转化
 * @param date
 * @returns {string}
 */
var formatDate = function (date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? '0' + m : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
};

/**
 * 返回上一页
 */
function back() {
    history.back(-1);
}

/**
 * 返回上一页展示新页面
 */
function backNew() {
    history.go(-1);
}

/**
 * 获取当前登录人id
 * @returns {*|jQuery}
 */
function getSessionUserId() {
    return $('#session_user_id').val();
}

/**
 * 回到目标底部
 * @param obj
 */
function toBut(obj) {
    if (obj.scrollHeight > obj.clientHeight) {
        obj.scrollTop = obj.scrollHeight + 10;
    }
}

/**
 * 添加弹窗
 * @param obj
 * @param msg
 */
function addLoadingModal(msg) {
    $('body').append('<div id="loading"></div>');
    if (isBlank(msg)) {
        msg = "正在加载...请稍等！"
    }
    $('#loadingModal').remove();
    $("#loading").append(
        "<div class=\"modal fade\" id=\"loadingModal\">\n" +
        "<div style=\"width: 200px;height:20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%;margin-left:-100px;margin-top:-10px\">\n" +
        "<div class=\"progress progress-striped active\" style=\"margin-bottom: 0;\">\n" +
        "<div class=\"progress-bar\" style=\"width: 100%;\"></div>\n" +
        "</div>\n" +
        "<h5 style=\"color:black\"> <strong>" + msg + "</strong> </h5>\n" +
        "</div>\n" +
        "</div>");
    loading(true);
}

/**
 * 弹窗开启和关闭
 * @param bool
 */
function loading(bool) {
    if (bool) {
        $("#loadingModal").modal('show');
    } else {
        $("#loadingModal").modal('hide');
    }
}

class Loading{
     msg(msg){
         addLoadingModal(msg);
     }
     close(){
         loading(false);
     }
}
/**
 * 转换中文字符串
 * @param str
 * @returns {string}
 */
function toUtf8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}

/**
 * 生成二维码
 * @param txt
 * @param width
 * @param height
 */
function outputQRCod(txt, width, height) {
    //先清空
    $(".qrcode").empty();
    //中文格式转换
    var str = toUtf8(txt);
    //生成二维码
    $(".qrcode").qrcode({
        render: "canvas",//canvas和table两种渲染方式
        width: width,
        height: height,
        text: str
    });
}

/**
 * 生成二维码
 * @param array
 * @param width
 * @param height
 */
function outputQRCod2(array, width, height) {
    var a = ["联系我们", "微信打赏", "支付宝打赏"]
    //Jquery 循环map的用法
    for (var i = 0; i < array.length; i++) {
        var key = "qrcode" + i;
        $("#qrcode").append(
            "<div class='card col-md-3' style=''>" +
            "<label style='font-size: 50px'>" + a[i] + "</label>" +
            "<div class='" + key + "  card-body'></div>" +
            "</div>");
        //先清空
        var obj = "." + key;
        $(obj).empty();
        //中文格式转换
        var str = toUtf8(array[i]);
        //生成二维码
        $(obj).qrcode({
            render: "canvas",//canvas和table两种渲染方式
            width: width,
            height: height,
            text: str
        });
    }
}

/**
 * 正则表达
 * @param reg
 * @param msg
 */
function checkFill(str, reg, msg) {
    if (!reg.test(str)) {
        tips(false, msg);
        return;
    }
}

function getInitL2D() {
    $(body).append("<div class=\"waifu\" id=\"waifu\">\n" +
        "    <div class=\"waifu-tips\" style=\"opacity: 1;\"></div>\n" +
        "    <canvas id=\"live2d\" width=\"280\" height=\"250\" class=\"live2d\"></canvas>\n" +
        "    <div class=\"waifu-tool\">\n" +
        "        <span class=\"fui-home\"></span>\n" +
        "        <span class=\"fui-chat\"></span>\n" +
        "        <span class=\"fui-eye\"></span>\n" +
        "        <span class=\"fui-user\"></span>\n" +
        "        <span class=\"fui-photo\"></span>\n" +
        "        <span class=\"fui-info-circle\"></span>\n" +
        "        <span class=\"fui-cross\"></span>\n" +
        "    </div>\n" +
        "</div>");
}

/**
 * 执行分片上传
 * @param file
 * @param i
 * @param uuid
 */
function uploadVideoFile(file, i, uuid, url, mergeUrl, img) {
    console.log('视频上传中')
    var fileType = file.name.substr(file.name.indexOf("\.")).toLowerCase();
    if (fileType != ".mp4") {
        tips("", "格式必须为mp4");
        return;
    }
    addLoadingModal("请稍后...正在上传");
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
            uploadVideoFile(file, i, uuid, url, mergeUrl, img);
            tips(null, data.msg);
        } else if (data.code === 500) {
            count++;
            form = '';
            /* 失败后，每2秒继续传一次分片文件 */
            var setIntervalFuc = setInterval(function () {
                uploadVideoFile(file, i, uuid, url, mergeUrl, img);
            }, 2000);
            //达到一定错误数量停止
            if (count === 10) {
                clearInterval(setIntervalFuc);
                loading(false);
                tips(data.rs, data.msg);
                var setIntervalFuc2 = setInterval(function () {
                    location.reload();
                }, 2000);
                clearInterval(setIntervalFuc2);
            }
        } else if (data.code === 200) {
            tips(data.rs, data.msg);
            mergeVideo(uuid, name, mergeUrl, img);
        }
    });

}

/**
 * 视频解析
 * @param uuid
 * @param fileName
 */
function mergeVideo(uuid, fileName, mergeurl, img) {
    $.ajax({
        url: mergeurl,
        type: "GET",
        data: {uuid: uuid, newFileName: fileName},
//timeout:"10000", //超时10秒
        async: true, //异步
        dataType: "json",
        success: function (data) {
            loading(false);
            if (data.rs) {
                $("#source_url").val(data.data.videoPicturePath);
                $("#vUrl").val(data.data.videoAccessPath);
                console.log("src", data.data.videoPicturePath);
                img.attr("src", data.data.videoPicturePath);
                //清楚上一个添加的
                /* $("#src_video").empty();
                 appendVideo($("#src_video"),"index_v",data.data);*/
            }
            tips(data.rs, data.msg)
        }
    })
}


/**
 * 获取uuid
 * @returns {string}
 */
function guid() {
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/**
 * 获取随机
 * @param max
 * @param min
 * @returns {number}
 */
function getRandom(max, min) {
    return Math.random() * (max - min + 1) + min;
}

class Modal {
    static show(obj) {
        obj.modal('show');
    }

    static hide(obj) {
        obj.modal('hide');
    }
}


/**
 * 删除
 * @param tableObj
 * @param delUrl
 * @param delUrl
 * @param toUrl
 */
function deleteByIdsT(tableObj,delUrl,toUrl) {

//删除id为ids的数据集合
    var ids = getIds(tableObj);
    if (ids != '' && ids != null) {
        var data = {"ids": ids};
        Ewin.confirm(
            {
                message: "确认要删除选择的数据吗？"
            }).on(
            function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    url: delUrl,
                    type: 'POST',
                    dataType: 'json',
                    data: data,
                }).done(function (data) {
                    if (data.rs) {
                        tips(data.msg);
                        toList(toUrl);
                    } else {
                        tips(data.msg)
                    }
                }).fail(function () {
                    tips(false, data.msg)
                });
            });
    }
}
var HttpRequest = function (options) {
    var defaults = {
        type: 'get',
        headers: {},
        data: {},
        dataType: 'json',
        async: true,
        cache: false,
        beforeSend: null,
        success: null,
        complete: null
    };
    var o = $.extend({}, defaults, options);
    $.ajax({
        url: o.url,
        type: o.type,
        headers: {
            'Content-Type': o.contentType,
            'access_token': o.token
        },
        data: o.data,
        dataType: o.dataType,
        async: o.async,
        beforeSend: function () {
            o.beforeSend && o.beforeSend();
        },
        success: function (res) {
            o.success && o.success(res);
        },
        complete: function () {
            o.complete && o.complete();
        }
    });
};

var ajax = function (options) {
    // 登入页无需携带token
    // 后台如果要求 Content-Type
    if (options.type == 'post') {
        options.contentType = 'application/x-www-form-urlencoded';
    }
    HttpRequest(options);
}
var ajaxHttp = function (options) {
    if (options.type == 'post') {
        options.contentType = 'application/x-www-form-urlencoded';
    }
    // 每次请求携带token
    options.token = localStorage.getItem('access_token');
    HttpRequest(options);
}

