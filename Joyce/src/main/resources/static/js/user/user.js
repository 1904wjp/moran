$(document).ready(function () {
    getUserTables();
});

/**
 * 获取用户
 */
function getUserTables() {
    var obj = $('#userTable');
    var url = '/example/user/userListData';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'username',
        title: '用户名',
        sortables: true
    }, {
        field: 'email',
        title: '邮箱',
        sortables: true
    }, {
        field: 'phone',
        title: '手机号码',
        sortables: true
    }, {
        field: 'status',
        title: '账号状态',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.status == 1) {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>已激活</button>";
            } else if (row.status == 10000) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>冻结</button>";
            } else {
                return "<button class='btn-secondary'disabled='disabled'style='padding:3px; border:10px;'>未激活</button>";
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
        formatter: userOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
}

//操作按钮
function userOpFormatter(value, row, index) {
    var actions = [];
    actions.push('<a class="btn btn-success btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="queryUserFuc(\'' + row.id + '\')"><i class="fa fa-adn"></i> 查看</a> ');
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editUserFuc(\'' + row.id + '\')"><i class="fa fa-edit"></i> 编辑</a> ');
    actions.push('<a class="btn btn-danger btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteModal"  onclick="deleteIdFuc(\'' + row.id + '\')"><i class="fa fa-remove"></i> 删除</a> ');
    /*actions.push('<a class="btn btn-warning btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteAuthorityModal"  onclick="deleteAuthority(\''+row.id+'\')"><i class="fa fa-remove"></i> 删除权限</a> ')*/
    if (row.status == 10000) {
        actions.push('<a class="btn btn-info btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="recoverUserFuc(\'' + row.id + '\')"><i class="fa fa-adn"></i> 恢复</a> ');
    } else {
        actions.push('<a class="btn btn-default btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="freezeUserFuc(\'' + row.id + '\')"><i class="fa fa-adn"></i> 冻结</a> ');
    }
    return actions.join('');
}

//删除id为id的数据
function deleteIdFuc(id) {
    var data = {"ids": id};
    Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/user/deleteUser',
            type: 'POST',
            dataType: 'json',
            data: data,
            
        }).done(function (data) {
            if (data.rs) {
               tips(data.rs,data.msg)
                toList('/example/user/userList');
            }else {
                tips(data.rs,data.msg)
            }
        }).fail(function () {
            tips(false,data.msg)
        });
    });
}

//冻结id为id的数据
function freezeUserFuc(id) {
    var data = {"ids": id};
    Ewin.confirm({message: "确认要冻结选择的数据吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/user/freezeUser',
            type: 'POST',
            dataType: 'json',
            data: data,
            
        }).done(function (data) {
            if (data.rs) {
                toastr.success(data.msg)
                toList('/example/user/userList');
            }else {
                toastr.error(data.msg)
            }
        }).fail(function () {
            tips(false,data.msg)
        });
    });    
}

//恢复id为id的数据
function recoverUserFuc(id) {
    var data = {"ids": id};
    Ewin.confirm({message: "确认要恢复选择的数据吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/user/recoverUser',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            if (data.rs) {
               tips(data.rs,data.msg)
                toList('/example/user/userList');
            }else {
                toastr.error(data.msg)
            }
        }).fail(function () {
            tips(false,data.msg)
        });
    });    
}

//删除id为ids的数据集合
function deleteIdsFuc() {
    var ids = getIds($("#userTable"));
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
                url: '/example/user/deleteUser',
                type: 'POST',
                dataType: 'json',
                data: data,
            }).done(function (data) {
                if (data.rs) {
                    toastr.success(data.msg)
                    toList('/example/source/userList');
                }else {
                    toastr.error(data.msg)
                }
            }).fail(function () {
            tips(false,data.msg)

            });
        });
    }
}

//跳转到修改页面
function editUserFuc(id) {
    var data = {
        "id": id
    };
        $.ajax({
            url: '/example/user/doQueryUser',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
           tips(data.rs,data.msg)
            if (data.rs) {
                toList('/example/user/editUser/' + id);
            }
        }).fail(function () {
            tips(false,data.msg)
        });
}

//跳转到查看页面
function queryUserFuc(id) {
    var data = {
        "id": id
    };
    $.ajax({
        url: '/example/user/doQueryUser',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        tips(data.rs,data.msg);
        if (data.rs) {
            toList('/example/user/queryUser/' + id);
        }
    }).fail(function () {
            tips(false,data.msg)
    });
}

//打开更改密码弹窗
function openModelFuc() {
    $("#updatePasswordModal").modal('show');
    $("#org_password").val('');
    $("#new_password1").val('');
    $("#new_password2").val('');
}

//打开忘记密码弹窗
function openForgetPasswordModelFuc() {
    $("#forgetPasswordModal").modal('show');
    $("#forget_email").val('');
    $("#email_code").val('');
    $("#new_password").val('');
}

//获取邮件验证码
function getEmailCodeFuc() {
    var email = $('#forget_email').val();
    if (email == null || email == '' || email == undefined) {
        toastr.info("请先输入您的邮寄");
        return;
    }
    var data = {
        "email": email
    };
    if (vailDate(data)){
            $.ajax({
                url: '/example/user/getEmailCode',
                type: 'POST',
                dataType: 'json',
                data: data,
            }).done(function (data) {
                tips(data.rs,data.msg);
            }).fail(function () {
                tips(false,ajaxFailMsg);
            });
    }

}

//忘记密码
function forget_password_update_verifyFuc() {
    var data = {
        "emailCode": $("#email_code").val(),
        "email": $("#forget_email").val(),
        "newPassword": $("#new_password").val()
    };

    if (vailDate(data)){
        Ewin.confirm({message: "确认提交数据？"}).on(function (e) {
            if (!e) {
                return;
            }
            addLoadingModal('正在提交数据...请稍后');
            $("#forgetPasswordModal").modal('hide');
            $.ajax({
                url: '/example/user/forgetPassword',
                type: 'POST',
                dataType: 'json',
                data: data,
            }).done(function (data) {
                loading(false)
                $("#forgetPasswordModal").modal('show');
                tips(data.rs,data.msg);
                if (data.rs) {
                    $("#updatePasswordModal").modal('hide');
                    toList("/example/user/login");
                }
            }).fail(function () {
                tips(false,data.msg)
            });
        });
    }
}

//密码修改
function password_update_verify() {
    var pwd1 = $("#new_password1").val();
    var pwd2 = $("#new_password2").val();
    if (pwd1 != pwd2) {
        toastr.error('两次输入不一致');
        return false;
    }
    var data = {
        "userId": $("#update_user_id").val(),
        "password": $("#org_password").val(),
        "newPassword": $("#new_password2").val()
    };
    if (vailDate(data)){
        Ewin.confirm({message: "确认提交数据？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '/example/user/updatePassword',
                type: 'POST',
                dataType: 'json',
                data: data,
            }).done(function (data) {
                tips(data.rs,data.msg);
                if (data.rs) {
                    $("#updatePasswordModal").modal('hide');
                   tips(data.rs,data.msg)
                    toList("/example/user/login");
                }
            }).fail(function () {
                tips(false,data.msg)
            });
        });
    }
}

//刷新
function customSearch() {
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
function uploadUserPhoto() {
    uploadFile($('#photo_file'), "/example/user/upload", $("#display_img"), $('#fileUrl'));
}

//用户修改
function updateUserFuc() {
    var data = {
        id: $("#id").val(),
        username: $("#username").val(),
        password: $("#password").val(),
        phone: $("#phone").val(),
        fileUrl: $("input[name='fileUrl']").val(),
        email: $("#email").val(),
        status: $('#status').val(),
        nickname: $('#nickname').val()
    };
    var fdata = {
        username: $("#username").val(),
        password: $("#password").val(),
        phone: $("#phone").val(),
        fileUrl: $("input[name='fileUrl']").val(),
        email: $("#email").val()
    };
    if (vailDate(fdata)){
        Ewin.confirm({message: "确认要提交数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '/example/user/doSaveUser',
                type: 'POST',
                dataType: 'json',
                data: data,
            }).done(function (data) {
                tips(data.rs,data.msg);
                if (data.rs) {
                    toMain();
                }
            }).fail(function () {
                tips(false,data.msg)
            });
        });
    }
}

//退出登录
function rmUser() {
    Ewin.confirm({message: "确认要退出吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/user/toRemoveUser',
            type: 'GET',
            dataType: 'json',
        }).done(function (data) {
            if (data.rs) {
                toList('/example/user/login');
            }else {
                tips(data.rs, data.msg);
            }
        }).fail(function () {
            tips(false, ajaxFailMsg)
        });
    });
}
//查找好友
function searchFriendFuc() {

    var data = {
        "username": $('#username').val()
    };
    $.ajax({
        url: '/example/user/searchUserByUsername',
        type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs){
            toList('/example/user/queryUser/'+data.data.id);
        }
        tips(data.rs,data.msg);
    }).fail(function () {
        tips(false,data.msg)
    });
}

