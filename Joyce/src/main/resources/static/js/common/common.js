
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
    setTimeout(function (){
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
 */
function backGroundSetting(url) {
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.position = "center center";
    document.body.style.backgroundAttachment = "fixed";
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundImage = "URL(" + url + ")";
  /*  document.body.style.opacity = 0.4;
    document.body.style.filter = 'alpha(opacity=' + 80 + ')';*/
}

/**
 * 数据列表通用方法
 * @param tabObj
 * @param url
 * @param queryObj
 * @param columns
 */
function tables(tabObj, url, queryObj, columns) {
    tabObj.css("background","white");
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
        pageList: [10, 25, 50, 100, 500,100000], //可供选择的每页的行数（*）
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
                if (value != '') {
                    param[$(this).attr('name')] = value;
                }
            });
            return param;
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
 * @param requestUrl
 * @param img
 * @param objUrl
 */
// 处理上传
function uploadFile(file, restUrl, img, objUrl, size) {
    if (file.val() == '') {
        return;
    }
    var formData = new FormData();
    formData.append('file', file[0].files[0]);
    //设置图片类型
    if (!/.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(file.val())) {
        tips(false,"图片类型不符合要求");
        return;
    }
    if (isBlank(size)){
        size=20;
    }
    //设置图片大小
    if (file[0].size.toFixed(2) >= (size  * 1024 * 1024)) {
        tips(false,"上传图片小于" + size + "MB");
        return;
    }
    $.ajax({
        url: restUrl,
        type: "POST",
        //上传格式为formData
        data: formData,
        contentType: false,
        // jQuery不要去处理发送的数据
        processData: false,
        success: function (data) {
            tips(data.rs,data.msg);
            if (data.rs) {
                img.attr("src", data.data);
                $("input[name=" + objUrl + "]").val(data.data);
            }


        }
    });
}


/**
 * 添加视频组件
 * @param divObject
 * @param videoId
 * @param src
 */
function appendVideo(divObject, videoId, src) {
    var video = $("<video id=\"" + videoId + "\" autoplay=\"autoplay\" loop=\"loop\" muted=\"muted\"><source src=\"" + src + "\"></video>")
    divObject.append(video);
    divObject[0].play();
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
    if (null != str && str != "" && str != undefined) {
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
    if (null != obj && obj != undefined) {
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


//生成对应的类
function inputColumns() {
    var data = {
        "tableName": $('#tableName').val(),
        "dbName": $('#dbName').val()
    }

    if(vailDate(data)){
        Ewin.confirm({message: "确认要提交数据？"}).on(function (e) {
            if (!e) {
                return;
            }
            addLoadingModal("正在创建数据...请稍后")
            $.ajax({
                url: '/example/columns/getColumns',
                type: 'POST',
                dataType: 'json',
                data: data,
                //传过来的data需要.data才可以获取当前对象。因为data是封装过的
            }).done(function (data) {
                loading(false);
                if (data.rs) {
                    $('#download').show();
                    toastr.success(data.msg);
                }else {
                    toastr.error(data.msg);
                }
            }).fail(function () {
                toastr.error(ajaxFailMsg);
            });
        });
    }

}

/**
 * 下载文件
 */
function downloadWebFile() {
    toList("/example/columns/downloadWebFile");
    $('#download').hide();
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
function back(){
    history.back(-1);
}
/**
 * 返回上一页展示新页面
 */
function backNew(){
    history.go(-1);
}

/**
 * 获取当前登录人id
 * @returns {*|jQuery}
 */
function getSessionUserId(){
    return  $('#session_user_id').val();
}

/**
 * 回到目标底部
 * @param obj
 */
function toBut(obj){
    if(obj.scrollHeight > obj.clientHeight) {
        obj.scrollTop = obj.scrollHeight;
    }
}

/**
 * 添加弹窗
 * @param obj
 * @param msg
 */
function addLoadingModal(msg){
    if (isBlank(msg)){
        msg="正在加载...请稍等！"
    }
    $('#loadingModal').remove();
    $("#loading").append(
        "<div class=\"modal fade\" id=\"loadingModal\">\n" +
        "<div style=\"width: 200px;height:20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%;margin-left:-100px;margin-top:-10px\">\n" +
        "<div class=\"progress progress-striped active\" style=\"margin-bottom: 0;\">\n" +
        "<div class=\"progress-bar\" style=\"width: 100%;\"></div>\n" +
        "</div>\n" +
        "<h5 style=\"color:black\"> <strong>"+msg+"</strong> </h5>\n" +
        "</div>\n" +
        "</div>"
    )
   loading(true);
}

/**
 * 弹窗开启和关闭
 * @param bool
 */
function loading(bool){
    if (bool){
        $("#loadingModal").modal('show');
    }else {
        $("#loadingModal").modal('hide');
    }
}

//转换中文字符串
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

//生成二维码
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
//生成二维码
function outputQRCod2(array, width, height) {
     var a = ["联系我们","微信打赏","支付宝打赏"]
    //Jquery 循环map的用法
       for (var i=0;i<array.length;i++){
           var key = "qrcode"+i;
           $("#qrcode").append(
               "<div class='card col-md-3' style=''>" +
               "<label style='font-size: 50px'>"+a[i]+"</label>" +
               "<div class='"+key+"  card-body'></div>"+
           "</div>");
           //先清空
           var obj = "."+key;
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
 * 正则表达谁
 * @param reg
 * @param msg
 */
function checkFill(str,reg,msg){
    if(!reg.test(str)){
        tips(false,msg);
        return;
    }

}