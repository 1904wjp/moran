/**
 * 添加数据源
 */
function addDataSource() {
    $("#id").val("");
    $("#dataSourceName").val("");
    $("#url").val("");
    $("#username").val("");
    $("#password").val("");
    $("#driverName").val("");
    $("#tempCode").val("");
    $("#databaseType").val("");
    $(".addDb").show();
    $(".dbs").hide();
}

function cancelDb() {
    $('.addDb').hide();
    $('.dbs').show();
}


function cancelPackage() {
    $('.addPg').hide();
    $('.pgs').show();
}

/**
 * 保存数据源信息
 */
function saveDataSource() {

    var data = {
        "id": $("#id").val(),
        "dataSourceName": $("#dataSourceName").val(),
        "url": $("#url").val(),
        "username": $("#username").val(),
        "password": $("#password").val(),
        "driverName": $("#driverName").val(),
        "tempCode": $("#tempCode").val(),
        "databaseType": $("#databaseType").val()
    };
    var filter = {
        "id": $("#id").val()
    };
    if (vailDate(data, filter)) {
        Ewin.confirm({message: "确认提交数据？"}).on(function (e) {
            if (!e) {
                return;
            }
            $(".addDb").hide();
            addLoadingModal();
            $.ajax({
                url: '/example/db/saveDb',
                type: 'POST',
                dataType: 'json',
                data: data,

            }).done(function (data) {
                $(".addDb").show();
                loading(false);
                if (data.rs) {
                    tips(data.rs, data.msg)
                    toList("/example/db/dbPage");
                } else {
                    tips(data.rs, data.msg);
                }
            }).fail(function () {
                tips(false, ajaxFailMsg)
            });
        });
    }
}


/**
 * 设为应用数据源信息
 */
function applyDataSource(id) {
    var data = {
        "id": id
    };
    Ewin.confirm({message: "确认要将id为" + id + "的数据设置成应用数据源吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/db/changeDb',
            type: 'GET',
            dataType: 'json',
            data: data,

        }).done(function (data) {
            if (data.rs) {
                toastr.success(data.ms);
                toList("/example/db/dbPage");
            } else {
                tips(data.rs, data.msg)
            }
        }).fail(function () {
            tips(false, data.msg)
        });
    });
}

/**
 * 删除数据源信息
 */
function deleteSource(id) {
    var ids = getIds($("#dbTable"));
    var data = {
        "ids": ids
    }
    $.ajax({
        url: '/example/db/deleteDb',
        type: 'GET',
        dataType: 'json',
        data: data,

    }).done(function (data) {
        if (data.rs) {
            tips(data.rs, data.msg);
            toList("/example/db/dbPage");
        } else {
            tips(data.rs, data.msg);
        }
    }).fail(function () {
        tips(false, data.msg);
    });
}

//获取数据源列表
$(function () {
    $(".addDb").hide();
    var obj = $('#dbTable');
    var url = '/example/db/dbList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'dataSourceName',
        title: '数据源名称',
        sortables: true
    }, {
        field: 'url',
        title: '数据库url',
        sortables: true
    }, {
        field: 'tempCode',
        title: '暂留字段',
        sortables: true
    }, {
        field: 'databaseType',
        title: '数据源类型',
        sortables: true
    }, {
        field: 'applyStatus',
        title: '应用状态',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.applyStatus == 1) {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>主类应用</button>";
            } else if (row.applyStatus == 0) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>非主类应用</button>";
            }
        }
    }, {
        title: '操作',
        align: 'center',
        formatter: datasourceOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
});

//操作按钮
function datasourceOpFormatter(value, row, index) {
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editDataSource(\'' + row.id + '\')"><i class="fa fa-edit"></i> 编辑</a> ');
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="applyDataSource(\'' + row.id + '\')"><i class="fa fa-edit"></i> 设为主数据</a> ');
    actions.push('<a class="btn btn-danger btn-sm"  href="javascript:void(0)" data-toggle="modal" data-target="#deleteModal"  onclick="deleteDbById(\'' + row.id + '\')"><i class="fa fa-remove"></i> 删除</a> ');
    return actions.join('');
}


/**
 * 删除id为id的数据
 * @param id
 */
function deleteDbById(id) {
    var data = {"ids": id};
    Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/db/deleteDb',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            if (data.rs) {
                tips(data.rs, data.msg)
                toList('/example/db/dbPage');
            } else {
                tips(data.rs, data.msg)
            }
        }).fail(function () {
            tips(false, data.msg)
        });
    });
}


/**
 * 修改数据源
 * @param id
 */
function editDataSource(id) {
    let data = {
        "id": id
    };
    $.ajax({
        url: '/example/db/getDb',
        type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs) {
            $('.dbs').hide();
            $('.addDb').show();
            $('#id').val(data.data.id);
            $('#dataSourceName').val(data.data.dataSourceName);
            $('#username').val(data.data.username);
            $('#password').val(data.data.password);
            $('#driverName').val(data.data.driverName);
            $('#tempCode').val(data.data.tempCode);
            $('#databaseType').val(data.data.databaseType);
            $('#url').val(data.data.url);
            tips(data.rs, data.msg)
        } else {
            tips(data.rs, data.msg)
        }
    }).fail(function () {
        tips(false, data.msg)
    });
}

//删除id为ids的数据集合
function deleteDbIds() {
    var ids = getIds($("#dbTable"));
    if (ids != '' && ids != null) {
        var data = {"ids": ids};
        if (vailDate(data)) {
            Ewin.confirm({message: "确认要删除所选文件？"}).on(function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    url: '/example/db/deleteDb',
                    type: 'POST',
                    dataType: 'json',
                    data: data,

                }).done(function (data) {
                    if (data.rs) {
                        tips(data.rs, data.msg)
                        toList('/example/db/dbPage');
                    } else {
                        tips(data.rs, data.msg)
                    }
                }).fail(function () {
                    tips(false, data.msg)
                });
            });
        }
    }
}

//获取包列表
$(function () {
    $(".addPg").hide();
    var obj = $('#packageTable');
    var url = '/example/db/packageList';
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
        title: '用户名称',
        sortables: true
    }, {
        field: 'packageName',
        title: '包的名称',
        sortables: true
    }, {
        field: 'packageValue',
        title: '包的值',
        sortables: true
    }, {
        field: 'applyStatus',
        title: '应用状态',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.applyStatus == 1) {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>主类应用</button>";
            } else if (row.applyStatus == 0) {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>非主类应用</button>";
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
        formatter: packageOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
});

//操作按钮
function packageOpFormatter(value, row, index) {
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editPackage(\'' + row.id + '\')"><i class="fa fa-edit"></i> 编辑</a> ');
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="applyPackage(\'' + row.id + '\')"><i class="fa fa-edit"></i> 设为主包</a> ');
    actions.push('<a class="btn btn-danger btn-sm"  href="javascript:void(0)" data-toggle="modal" data-target="#deleteModal"  onclick="deletePackage(\'' + row.id + '\')"><i class="fa fa-remove"></i> 删除</a> ');
    return actions.join('');
}

/**
 * 添加数据包
 */
function addPackage() {
    $("#id").val("");
    $("#packageName").val("");
    $("#packageValue").val("");
    $(".addPg").show();
    $(".pgs").hide();
}

/**
 * 修改包
 * @param id
 */
function editPackage(id) {
    let data = {
        "id": id
    };
    $.ajax({
        url: '/example/db/getPg',
        type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs) {
            $('.pgs').hide();
            $('.addPg').show();
            $('#id').val(data.data.id);
            $('#packageName').val(data.data.packageName);
            $('#packageValue').val(data.data.packageValue);
            tips(data.rs, data.msg)
        } else {
            tips(data.rs, data.msg)
        }
    }).fail(function () {
        tips(false, data.msg)
    });
}


/**
 * 保存数据包信息
 */
function savePackage() {
    var data = {
        "id": $("#id").val(),
        "packageName": $("#packageName").val(),
        "packageValue": $("#packageValue").val()
    };
    if (vailDate(data)) {
        Ewin.confirm({message: "确认提交数据源？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '/example/db/savePg',
                type: 'POST',
                dataType: 'json',
                data: data,

            }).done(function (data) {
                if (data.rs) {
                    tips(data.rs, data.msg)
                    toList("/example/db/packagePage");
                } else {
                    tips(data.rs, data.msg)
                }
            }).fail(function () {
                tips(false, data.msg)
            });
        });
    }
}


/**
 * 设为应用数据包信息
 * @param id
 */
function applyPackage(id) {
    data = {
        "id": id
    };
    Ewin.confirm({message: "确认要将id为" + id + "数据设置为主应用包吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/db/changePg',
            type: 'GET',
            dataType: 'json',
            data: data,

        }).done(function (data) {
            if (data.rs) {
                tips(data.rs, data.msg)
                toList('/example/db/packagePage');
            } else {
                tips(data.rs, data.msg)
            }
        }).fail(function () {
            tips(false, data.msg)
        });
    });
}

/**
 * 删除数据包信息
 * @param id
 */
function deletePackage(id) {
    data = {
        "ids": id
    };
    Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/db/deletePg',
            type: 'GET',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            if (data.rs) {
                tips(data.rs, data.msg)
                toList("/example/db/packagePage");
            } else {
                tips(data.rs, data.msg)
            }
        }).fail(function () {
            tips(false, data.msg)
        });
    });
}

//刷新
function dbSearch() {
    $('#dbTable').bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}

//刷新
function packageSearch() {
    $('#packageTable').bootstrapTable('refresh');//刷新Table，Bootstrap Table 会自动执行重新查询
}

//复位
function resetSearch() {
    $('#query-form').find('[name]').each(function () {
        $(this).val('');
    });
}

//删除id为ids的数据集合
function deletePackages() {
    var ids = getIds($("#packageTable"));
    if (ids != '' && ids != null) {
        var data = {"ids": ids};
        Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '/example/db/deletePg',
                type: 'POST',
                dataType: 'json',
                data: data,

            }).done(function (data) {
                if (data.rs) {
                    tips(data.rs, data.msg)
                    toList('/example/db/packagePage');
                } else {
                    tips(data.rs, data.msg)
                }
                ;
            }).fail(function () {
                tips(false, data.msg)
            });
        });
    }
}

/**
 * 自动填充数据
 */
$('#domain').bind('input propertychange', function () {
    autoFill();
})
$('#port').bind('input propertychange', function () {
    autoFill();
})

/**
 * 自动填充数据
 */

function autoFill() {
    $('#url').val("jdbc:mysql://" + $('#domain').val() + ":" + $('#port').val());
    $('#driverName').val("com.mysql.cj.jdbc.Driver");
    $('#databaseType').val("mysql");
}


/**
 * 显示表格
 */
$('#tableName').change(function () {
    var dbName = $('#dbName').val();
    var tableName = $(this).children('option:selected').val()
    if (!isBlank(tableName) && !isBlank(dbName)) {
        var data = {
            dbName: dbName,
            tableName: tableName
        }
        $.ajax({
            url: '/example/columns/getColumns',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            if (data.rs) {
                var html = "";
                var arry = data.data;
                if (notNull(arry)) {
                    $('#c_table').remove();
                    var obj = $('#column_table');
                    html = "<table id='c_table' style='margin-left: 25%' border=\"1\">\n" +
                        "    <tr>\n" +
                        "        <th colspan='7' align='center'>" + arry[0].tableName + ":" + arry[0].tableComment + "</th>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>字段名</td>\n" +
                        "        <td>字段类型</td>\n" +
                        "        <td>字段最大长度</td>\n" +
                        "        <td>字段是否为空</td>\n" +
                        "        <td>字段是否为主键</td>\n" +
                        "        <td>字段默认值</td>\n" +
                        "        <td>字段注释</td>\n" +
                        "    </tr>\n";

                    for (let i = 0; i < arry.length; i++) {
                        html = html + "<tr>\n" +
                            "        <td>" + arry[i].columnName + "</td>\n" +
                            "        <td>" + arry[i].columnType + "</td>\n" +
                            "        <td>" + arry[i].columnLength + "</td>\n" +
                            "        <td>" + arry[i].isNull + "</td>\n" +
                            "        <td>" + arry[i].isKey + "</td>\n" +
                            "        <td>" + arry[i].defaultValue + "</td>\n" +
                            "        <td>" + arry[i].columnComment + "</td>\n" +
                            "    </tr>\n";
                    }
                    html = html + "</table>";
                }
                obj.append(html);
            } else {
                tips(data.rs, data.msg)
            }
            ;
        }).fail(function () {
            tips(false, data.msg)
        });
    }
});


