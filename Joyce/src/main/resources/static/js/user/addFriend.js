
/**
 * 获取申请列表
 */
function getApplicationFriendFuc(){
    $.ajax({
        url: '/example/uu/applyFriendList',
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        console.log(data.data)
        if (data.rs) {
            var str ="";
            if (data.data.length!=0){
                for (let i = 0; i < data.data.length; i++) {
                    str = str + "<li class=\"list-group-item\">"+
                        "<span><img  style='width: 10%' src="+data.data[i].userFileUrlA+"/></span>" +
                        " <span>"+data.data[i].usernameA +
                        "</span> <span>"+data.data[i].createTime+"</span>";
                    if (data.data[i].resultStr==='1'){
                        str = str+
                            "<a><input class='pull-right'  type=\"button\" value='不同意' onclick='disagree("+data.data[i].userAId+")' ></a>" +
                            "<a> <input class='pull-right' type=\"button\" value='同意' onclick='agree("+data.data[i].userAId+")'/></a>"
                    }
                    if (data.data[i].resultStr=='0'){
                        str = str+
                            "<span class='pull-right'>同意</span>" ;
                    }
                    if (data.data[i].resultStr=='2'){
                        str = str+
                            "<span class='pull-right'>不同意</span>" ;
                    }
                    str = str+"</li>";
                }
            }else {
                str = "暂无信息";
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
        userAId: getSessionUserId(),
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
        tips(false,ajaxFailMsg);
    });
}

/**
 * 同意
 * @param id
 */
function agree(id){
    var data ={
        "id":id,
        "type":0
    }
    $.ajax({
        url: '/example/uu/agreeFriend',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        tips(data.rs,data.msg);
    }).fail(function () {
        tips(false,ajaxFailMsg)
    });
}

/**
 * 不同意
 * @param id
 */
function disagree(id){
    var data ={
        "id":id,
        "type":2
    }
    $.ajax({
        url: '/example/uu/agreeFriend',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        tips(data.rs,data.msg);
    }).fail(function () {
        tips(false,ajaxFailMsg)
    });
}
