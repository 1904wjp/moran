
/**
 * 获取申请列表
 */
function getApplicationFriendFuc(){
    $.ajax({
        url: '/example/uu/applyFriendList',
        type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs) {
            var str ="";
            for (let i = 0; i < data.data.length; i++) {
                str = str + "<li class=\"list-group-item\">"+data.data[i].userAName+"</li>";
            }
            $('#applyFriendList').append(str);
        }else {
            tips(data.rs,data.msg);
        }
    }).fail(function () {
        tips(false,ajaxFailMsg)
    });
}

/**
 * 申请添加好友
 */
function addFriendFuc(){
    var data = {
        userAid:getSessionUserId(),
        userBId: $('#update_password_id').val()
    };
    $.ajax({
        url: '/example/uu/addFriend',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
       alert(data.msg);
       back();
    }).fail(function () {
        tips(false,ajaxFailMsg)
    });
}


