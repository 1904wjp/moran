$().ready(function (){
   initListFuc();
   initFuc();
});

function initListFuc(){
    let data = {
        nickname: $('#searchfield').val()
    };
    $.ajax({
        url: '/example/user/sessionUsers',
        type: 'GET',
        dataType: 'json',
        data:data,
    }).done(function (data) {
        console.log("....",data.data);
        let html='';
            for (let i = 0; i < data.data.length; i++) {
                html =html+  "<div  class=\"friend\">" +
                    "<input type='hidden' value=\""+data.data[i].id+"\"/>"+
                    "<img class='friend_list_url' style='width: 9%' src=\""+data.data[i].fileUrl+"\">" +
                    "<p>" +
                    "<strong class=\"nickname\">"+data.data[i].nickname+"</strong>" +
                    "<br/>"+
                    "<span>"+data.data[i].email+"</span>"+
                    "</p>";
                    if (data.data[i].chartStatus===0){
                      html = html+"<div class=\"status available\"></div></div>";
                    }
                    if (data.data[i].chartStatus===1) {
                        html = html+"<div class=\"status inactive\"></div></div>";
                    }
            }
        $('#friends').append(html);
    }).fail(function () {
        tips(false,ajaxFailMsg);
    });
}

function initFuc(){

    var preloadbg = document.createElement('img');
    preloadbg.src = '/static/img/websocket/timeline1.png';
    $('.friend').each(function () {
        $(this).click(function () {
            var childOffset = $(this).offset();
            var parentOffset = $(this).parent().parent().offset();
            var childTop = childOffset.top - parentOffset.top;
            var clone = $(this).find('img').eq(0).clone();
            var top = childTop + 12 + 'px';
            $(clone).css({ 'top': top }).addClass('floatingImg').appendTo('#chatbox');
            setTimeout(function () {
                $('#profile p').addClass('animate');
                $('#profile').addClass('animate');
            }, 100);
            setTimeout(function () {
                $('#chat-messages').addClass('animate');
                $('.cx, .cy').addClass('s1');
                setTimeout(function () {
                    $('.cx, .cy').addClass('s2');
                }, 100);
                setTimeout(function () {
                    $('.cx, .cy').addClass('s3');
                }, 200);
            }, 150);
            $('.floatingImg').animate({
                'width': '68px',
                'left': '108px',
                'top': '20px'
            }, 200);
            var name = $(this).find('p strong').html();
            var email = $(this).find('p span').html();
            $('#profile p').html(name);
            $('#profile span').html(email);
            $('.message').not('.right').find('img').attr('src', $(clone).attr('src'));
            $('#friendslist').fadeOut();
            $('#chatview').fadeIn();
            $('#close').unbind('click').click(function () {
                $('#chat-messages, #profile, #profile p').removeClass('animate');
                $('.cx, .cy').removeClass('s1 s2 s3');
                $('.floatingImg').animate({
                    'width': '40px',
                    'top': top,
                    'left': '12px'
                }, 200, function () {
                    $('.floatingImg').remove();
                });
                setTimeout(function () {
                    $('#chatview').fadeOut();
                    $('#friendslist').fadeIn();
                }, 50);
            });
        });
    });
}

/**
 * 查询好友
 */
$('#search').keydown(function (e){
   if (e.keyCode===13){
       $('.friend').remove();
       initListFuc();
   }
});

$("#friends").on('click', '.friend', sendInfo);

/**
 * 发送消息
 */
function sendInfo(){
    $('#to').val($(this).find("input").val());
    let data = {
        id:$(this).find("input").val()
    }
    let data2 = {
        userBId:$(this).find("input").val()
    }

    $.ajax({
        url: '/example/user/doQueryUser',
        type: 'GET',
        dataType: 'json',
        data:data,
    }).done(function (data) {

        let html =
            '<div class="close">' +
            '<div class="cy"></div>' +
            '<div class="cx"></div>' +
            '</div>' +
            '<img style="width: 20%;height: 15%" src='+data.data.fileUrl+'/>' +
            '<p>'+data.data.nickname+'</p>' +
            '<span>'+data.data.email+'</span>';
        $("#chatview").show();
        $('#profile p').show();
        $("#friends").hide();
        $('#profile').append(html);
        $('#chat-messages').show();
        $('#to').val(data.data.id);
    });


    $.ajax({
        url: '/example/chatRecord/getAllRecord',
        type: 'GET',
        dataType: 'json',
        data:data2,
    }).done(function (data) {
        console.log(JSON.stringify(data.data))
        let chatHtml ="";
        for (let i = 0; i < data.data.length; i++) {
            let date =  "<label>"+data.data[i].createTimeValue+"</label>" ;
            if ((new Date()).getTime()-data.data[i].createTimeValue.getTime>3*60*1000){
                date  = "";
            }
            chatHtml = chatHtml + date;

            if ($('#session_user_id').val()==data.data[i].userAId){
                chatHtml = chatHtml+ "<div class=\"message right\">"+
                    "<img src=\""+data.data[i].afileUrl+"\" />" ;
            }else {
                chatHtml = chatHtml+ "<div class=\"message\">"+
                    "<img src=\""+data.data[i].afileUrl+"\" />" ;
            }
            chatHtml = chatHtml +
                "<div class=\"bubble\">" +
                data.data[i].content +
                "</div></div>";
            $('#tosrc').val(data.data[0].bfileUrl);
        }
        $('#chat-messages').append(chatHtml);

    }).fail(function (){
        $('#chat-messages').append("<div>暂无信息</div>");
    });
}

/**
 * 保存信息
 * @param to
 * @param message
 */
function saveInfo(to,message){
    var data ={
       "id":to,
        "msg":message
    }

    $.ajax({
        url: '/example/user/sendTo',
        type: 'POST',
        dataType: 'json',
        data:data,
    }).done(function (data) {
        tips(data.rs,data.msg);
    }).fail(function (){
        tips(false,ajaxFailMsg);
    });
}

/**
 * 限制字数
 */
$("#messageText").keyup(function(){
    var max  =30;
    var this_ = $(this).val();
    var len = this_.length;
    $(this).attr({maxlength:""+max});
    if (len>=max){
        tips("","输入文字不可多余"+max);
    }
});

