$(document).ready(function (){
    getUserTabler();
    /*  $("#fakeloader").fakeLoader({
          timeToHide:1200, //关闭的时间
          zIndex:999, // Default zIndex
          spinner:"spinner1",//加载主题（内置7种动画）: 'spinner1', 'spinner2', 'spinner3', 'spinner4', 'spinner5', 'spinner6', 'spinner7'
          bgColor:"#2ecc71", //Hex, RGB or RGBA colors
          imagePath:"/static/img/userImg/file0default0name.png"
      });*/
});

function getUserTabler() {
    var obj=$('#userTable');
    var url='/example/user/userListData';
    var queryObj=$('#query-form');
    var columns =[{
        checkbox: true
    },{
        title:'序号',
        formatter:function (value,row,index){
            return index+1;
        }
    },{
        field:'username',
        title: '用户名',
        sortables:true
    },{
        field: 'email',
        title: '邮箱',
        sortables: true
    },{
        field: 'phone',
        title: '手机号码',
        sortables: true
    },{
        field: 'status',
        title: '账号状态',
        sortables: true,
        formatter: function (value, row, index){
            if (row.status==1){
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>已激活</button>";
            }else if (row.status==10000){
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>冻结</button>";
            } else{
                return"<button class='btn-secondary'disabled='disabled'style='padding:3px; border:10px;'>未激活</button>";
            }
        }
    }, {
        field: 'createTime',
        title: '创建时间',
        sortables: true
    },{
        field: 'createBy',
        title: '创建者',
        sortables: true
    },{
        title: '操作',
        align: 'center',
        formatter:userOpFormatter//格式化输出
    }];
    tables(obj,url,queryObj,columns);
}

//操作按钮
function userOpFormatter(value,row,index){
    var actions=[];
    actions.push('<a class="btn btn-success btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="queryUserFuc(\''+row.id+'\')"><i class="fa fa-adn"></i> 查看</a> ');
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editUserFuc(\''+row.id+'\')"><i class="fa fa-edit"></i> 编辑</a> ');
    actions.push('<a class="btn btn-danger btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteModal"  onclick="deleteIdFuc(\''+row.id+'\')"><i class="fa fa-remove"></i> 删除</a> ');
    /*actions.push('<a class="btn btn-warning btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteAuthorityModal"  onclick="deleteAuthority(\''+row.id+'\')"><i class="fa fa-remove"></i> 删除权限</a> ')*/
    if (row.status==10000){
        actions.push('<a class="btn btn-info btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="recoverUserFuc(\''+row.id+'\')"><i class="fa fa-adn"></i> 恢复</a> ');
    }else {
        actions.push('<a class="btn btn-default btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="freezeUserFuc(\''+row.id+'\')"><i class="fa fa-adn"></i> 冻结</a> ');
    }
    return actions.join('');
}

//删除id为id的数据
function deleteIdFuc(id){
    var data = {"ids":id};
    if (!confirm("确认删除id为"+id+"的数据")){
        alert("操作取消");
        return false;
    }
    $.ajax({
        url: '/example/user/deleteUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toList('/example/user/userList');
        }
        alert(data.msg);
    }).fail(function () {
    });
}

//冻结id为id的数据
function freezeUserFuc(id){
    var data = {"ids":id};
    if (!confirm("确认冻结id为"+id+"的数据")){
        alert("操作取消");
        return false;
    }
    $.ajax({
        url: '/example/user/freezeUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toList('/example/user/userList');
        }
        alert(data.msg);
    }).fail(function () {

    });
}

//恢复id为id的数据
function  recoverUserFuc(id){
    var data = {"ids":id};
    if (!confirm("确认恢复id为"+id+"的数据")){
        alert("操作取消");
        return false;
    }
    $.ajax({
        url: '/example/user/recoverUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toList('/example/user/userList');
        }
        alert(data.msg);
    }).fail(function () {

    });
}

//删除id为ids的数据集合
function deleteIdsFuc(){
    var ids =  getIds($("#userTable"));
    if (ids!=''&&ids!=null){
        var data = {"ids":ids};
        if (!confirm("确认删除id为:"+ids+"的数据?")){
            alert("操作取消");
            return false;
        }
        $.ajax({
            url: '/example/user/deleteUser',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            if (data.code==200){
                toList('/example/user/userList');
            }else {
                alert(data.msg);
            };
        }).fail(function () {

        });
    }
}

//跳转到修改页面
function editUserFuc(id,type){
    var data ={
        "id":id
    };
    if (type=='2'){
        if (confirm("确认修改id为:"+id+"的数据?")==false){
            alert("操作取消");
            return false;
        }
    }
    $.ajax({
        url: '/example/user/doQueryUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            if (type=='1'){
                toList('/example/user/editUser/login');
            }
            toList('/example/user/editUser/'+id);
        }else {
            alert(data.msg);
        };
    }).fail(function () {

    });
}

//跳转到查看页面
function queryUserFuc(id){
    var data ={
        "id":id
    };
    $.ajax({
        url: '/example/user/doQueryUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toList('/example/user/queryUser/'+id);
        }else {
            alert(data.msg);
        }
    }).fail(function () {

    });
}

//打开更改密码弹窗
function openModelFuc(){
    $("#updatePasswordModal").modal('show');
    $("#org_password").val('');
    $("#new_password1").val('');
    $("#new_password2").val('');
}

//打开忘记密码弹窗
function openForgetPasswordModelFuc(){
    $("#forgetPasswordModal").modal('show');
    $("#forget_email").val('');
    $("#email_code").val('');
    $("#new_password").val('');
}

//获取邮件验证码
function getEmailCodeFuc(){
    var email = $('#forget_email').val();
    if (email==null||email==''||email==undefined){
        alert("请先输入您的邮寄");
        return;
    }
    var data ={
        "email":email
    };
    $.ajax({
        url: '/example/user/getEmailCode',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        alert(data.msg);
    }).fail(function () {

    });
}

//忘记密码
function forget_password_update_verifyFuc(){
    var data ={
        "emailCode":$("#email_code").val(),
        "email":$("#forget_email").val(),
        "newPassword":$("#new_password").val()
    };
    $.ajax({
        url: '/example/user/forgetPassword',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            $("#updatePasswordModal").modal('hide');
            toList("/example/user/login");
        }else {
            alert(data.msg);
        };
    }).fail(function () {

    });
}

//密码修改
function password_update_verify(){
    var pwd1 =  $("#new_password1").val();
    var pwd2 =  $("#new_password2").val();
    if (pwd1!=pwd2){
        alert('两次输入不一致');
        return false;
    }
    var data ={
        "userId":$("#update_user_id").val(),
        "password":$("#org_password").val(),
        "newPassword":$("#new_password2").val()
    };
    $.ajax({
        url: '/example/user/updatePassword',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            $("#updatePasswordModal").modal('hide');
            toList("/example/user/login");
        }else {
            alert(data.msg);
        };
    }).fail(function () {

    });
}

//刷新
function customSearch(text) {
    //刷新Table，Bootstrap Table 会自动执行重新查询
    $('#userTable').bootstrapTable('refresh');
}
//复位
function resetSearch() {
    $('#query-form').find('[name]').each(function () {
        $(this).val('');
    });
}

//上传用户图片
function uploadUserPhoto(){
    uploadFile($('#photo_file'),"/example/user/upload",$("#display_img"),"fileUrl");
}

//用户修改
function updateUserFuc(){
    var data={
        id:$("#id").val(),
        username: $("#username").val(),
        password: $("#password").val(),
        phone: $("#phone").val(),
        fileUrl: $("input[name='fileUrl']").val(),
        email: $("#email").val()
    };
    if (confirm("确认提交数据?")==false){
        return false;
    }
    $.ajax({
        url: '/example/user/doSaveUser',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toMain();
        }
        alert(data.msg);
    }).fail(function () {

    });
}

//退出登录
function rmUser(){

    if (confirm("确认退出登录?")==false){
        alert("操作取消");
        return false;
    }
    $.ajax({
        url: '/example/user/toRemoveUser',
        type: 'GET',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            toList('/example/user/editUser/login');
        }
    }).fail(function () {

    });
}