$(document).ready(function () {
    loadBackGround();
});

/**
 * 变换页面
 */
function loadBackGround() {
    $.ajax({
        url: '/example/source/getList',
        type: 'GET',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        var img =  Math.floor(Math.random()* (data.rows.length-1)) ;
        var Img = data.rows;
        if (Img.length!=0){
            if (document.readyState === "loading" || document.readyState === "interactive" || document.readyState === "complete") {
                backGroundSetting(Img[img].url);
            }
        }
    }).fail(function () {

    });

}

var ajaxFailMsg = "系统开了个小差，请稍后再试！！！";

/**
 * 用户登录
 */
function loginFuc() {
    var data = {
        //获取输入框用户名
        "username": $("#username").val(),
        //获取输入框密码
        "password": $("#password").val()
    };
    console.log(vailDate(data));
    if (vailDate(data)) {
        $.ajax({
            url: '/example/user/doLogin',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            if (data.rs) {
                toList("/main");
            }
                tips(data.rs,data.msg);
        }).fail(function () {
            tips(false,ajaxFailMsg);
        });
    }
}

/**
 * 用户注册
 */
function registFuc() {
    var data = {
        username: $("#username").val(),
        password: $("#password").val(),
        phone: $("#phone").val(),
        email: $("#email").val(),
        nickname: $('#nickname').val()
    };
    if (vailDate(data)) {
        $.ajax({
            url: '/example/user/doSaveUser',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            tips(data.rs,data.msg);
            if (data.rs) {
                toList('/example/user/statusResult');
            }
        }).fail(function () {
            tips(false,ajaxFailMsg);
        });
    }
}
