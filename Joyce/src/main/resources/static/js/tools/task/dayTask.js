$().ready(function (){
    $("#add_dayTask").hide();
    getDayTaskTable();
})

/**
 * 看板列表
 */
function getDayTaskTable(){

        var obj = $('#dayTaskTable');
        var url = '/example/dayTask/getList';
        var queryObj = $('#query-form');
        var columns = [{
            checkbox: true
        }, {
            title: '序号',
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            field: 'startTimes',
            title: '开始时间',
            sortables: true
        }, {
            field: 'endTimes',
            title: '结束时间',
            sortables: true,
        }, {
            field: 'time',
            title: '加班时常',
            sortables: true,
        },{
            field: 'createTime',
            title: '创建时间',
            sortables: true,
        },{
            field: 'nickname',
            title: '加班人',
            sortables: true
        }, {
            title: '操作',
            align: 'center',
            formatter: datTaskOpFormatter//格式化输出
        }];
        tables(obj, url, queryObj, columns);

}

//刷新
function dayTaskSearch() {
    //刷新Table，Bootstrap Table 会自动执行重新查询
    $('#dayTaskTable').bootstrapTable('refresh');
}

/**
 * 添加每日任务
 */
function addDayTask(){
    $("#tables").hide();
    $("#add_dayTask").show();
    $('#addEndTimes').val('');
    $('#addStartTimes').val('');
    $('#addTodayTask').val('');
    $('#addProjectId').val('');
    $.ajax({
        url: '/example/dayTask/justTasks',
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        if (data.rs) {
            var date = new Date();
            date.setHours(9,0,0);
            console.log("1=",date)
            $('#addStartTimes').val(date);
            date.setHours(18,0,0);
            console.log("2=",date)
            $('#addEndTimes').val(date);
        }
    });
}

/**
 * 导出excel表格
 */
function exportDayTaskTable(){
     /*  toList('/example/dayTask/exportTasks');exportTasksByManySheet*/
    toList('/example/dayTask/exportTasksByManySheet');
}

/**
 * 编辑每日任务
 * @param id
 */
function editDayTask(id){
    $("#tables").hide();
    $("#add_dayTask").show();
    let data = {
        id:id
    };
    $.ajax({
        url: '/example/dayTask/getTasks',
            type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs) {
            $('#addId').val(data.data.id);
            $('#addEndTimes').val(data.data.endTime);
            $('#addStartTimes').val(data.data.startTime);
            $('#time').val(data.data.time);
        }
    });
}

/**
 * 保存每日任务
 */
function saveDayTask(){
   /* var date = formatDate($('#addEndTimes').val());*/
    let data = {
     id:$('#addId').val(),
     startTimes: $('#addStartTimes').val(),
     endTimes: $('#addEndTimes').val(),
     isWorkDay:$('#isWorkDay').val()
    };
    console.log(JSON.stringify(data));
    $.ajax({
        url: '/example/dayTask/saveTasks',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        if (data.rs){
            toList('/example/dayTask/dayTaskListPage');
        }
        tips(data.rs,data.msg);
    });
}

/**
 * 取消关闭
 */
function cancel(){
    $("#tables").show();
    $("#add_dayTask").hide();
}
//操作按钮
function datTaskOpFormatter(value, row, index) {
    var actions = [];
    let userId = $('#session_user_id').val();
    if (userId==(row.userId)){
        actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editDayTask(\'' + row.id + '\')"><i class="fa fa-edit"></i> 编辑</a> ');
    }
    return actions.join('');
}
function importExcel(){

    let data = {
        path:$('#excelTable').val()
    }
    $.ajax({
        url: '/example/dayTask/importExcel',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        tips(data.rs,data.msg);
    });
}

//获取上传图片路径2
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}