var info={};
//邮件正则
 info.emailReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
//返回信息
 info.returnEmailMsg = "输入的邮件号有误，请规范书写";
//手机正则
 info.phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
 info.returnPhoneMsg = "输入的手机号格式有误，请规范书写";
 info.errorMsg = "输入信息有误";
$(document).ready(function () {
    loadBackGround();
});

/**
 * 变换页面
 */
function loadBackGround() {
    $.ajax({
        url: '/example/source/getList'+"?applyStatus=0",
        type: 'GET',
        dataType: 'json',
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
    if ($('.check_info').val() !=='0'){
        tips(false,info.errorMsg);
    } else if (vailDate(data)) {
        $.ajax({
            url: '/example/user/doSaveUser',
            type: 'POST',
            dataType: 'json',
            data: data,
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

// function phoneRegFuc(){
//     var phone= $('#phone').val();
//     checkFill(phone,phoneReg,returnPhoneMsg);
// }
//
// function emailRegFuc(){
//     var email= $('#email').val();
//     checkFill(email,emailReg,returnEmailMsg);
// }