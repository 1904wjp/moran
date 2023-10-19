$().ready(function () {
    getUris();
    getLoggingTables();
});

function loggingResetSearch(){
    resetSearch();
    $("#uri option:first").prop("selected", 'selected');
}


function getUris() {
    $.ajax({
        url: '/example/sysMenu/getBaseMenus', type: 'GET', dataType: 'json'
    }).done(function (data) {
        if (data.rs) {
            //  console.log(data.data);
            var html = '\n';
            for (let i = 0; i < data.data.length; i++) {
                html += '<option name="'+data.data[i].name+'" value="'+data.data[i].url+'">'+data.data[i].name+'</option>\n';
            }
            $('#uri').append(html);
        }
    }).fail(function () {
        tips(false, ajaxFailMsg);
    });
}

function loggingSearch() {
    console.log("刷新");
    //刷新Table，Bootstrap Table 会自动执行重新查询
   // getLoggingTables();
    $('#loggingTable').bootstrapTable('refresh');
}
/**
 * 获取用户
 */
function getLoggingTables() {
    var obj = $('#loggingTable');
    var url = '/example/logging/loggingList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号', formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'username', title: '用户名', sortables: true
    }, {
        field: 'uri', title: '访问的接口', sortables: true
    }, {
        field: 'loginIp', title: '登录ip', sortables: true
    }, {
        field: 'loginSys', title: '登录系统', sortables: true
    }, {
        field: 'params', title: '参数', sortables: true,cellStyle:formatTableUnit,formatter:paramsMatter
    },  {
        field: 'eventDesc', title: '事件信息', sortables: true
    }, {
        field: 'createTime', title: '创建时间', sortables: true
    }];
    tables(obj, url, queryObj, columns);
}

// $('#uri').change(function () {
//     $('#query-form').find('select').each(function () {
//         console.log($(this).attr('name'));
//         console.log($(this).find("option:selected").val());
//     });
// });