$().ready(function (){
    $("#add_dayTask").hide();
    getDayTaskTable();
});
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
            field: 'createTime',
            title: '创建时间',
            sortables: true
        }, {
            field: 'todayTask',
            title: '今日任务',
            sortables: true
        }, {
            field: 'endTimes',
            title: '结束时间',
            sortables: true,
        }, {
            field: 'finalyTask',
            title: '最终任务',
            sortables: true,
        },{
            field: 'projectName',
            title: '项目名称',
            sortables: true,
        },{
            field: 'createBy',
            title: '创建者',
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

function addDayTask(){
    $("#tables").hide();
    $("#add_dayTask").show();
    $('#addEndTimes').val('');
    $('#addFinalyTask').val('');
    $('#addTodayTask').val('');
    $('#addProjectId').val('');
    $.ajax({
        url: '/example/dayTask/justTasks',
        type: 'GET',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs) {
            $('#addEndTimes').val(data.data.endTimes);
            $('#addFinalyTask').val(data.data.finalyTask);
            $('#addTodayTask').val(data.data.todayTask);
            $('#addProjectId').val(data.data.projectId);
        }
    });
}

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
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs) {
            $('#addId').val(data.data.id);
            $('#addEndTimes').val(data.data.endTimes);
            $('#addFinalyTask').val(data.data.finalyTask);
            $('#addTodayTask').val(data.data.todayTask);
            $('#addProjectId').val(data.data.projectId);
        }
    });
}

function saveDayTask(){
   /* var date = formatDate($('#addEndTimes').val());*/
    let data = {
     id:$('#addId').val(),
     endTimes: $('#addEndTimes').val(),
     finalyTask:$('#addFinalyTask').val(),
     todayTask:$('#addTodayTask').val(),
     projectId:$('#addProjectId').val()
    };
    console.log(JSON.stringify(data));
    $.ajax({
        url: '/example/dayTask/saveTasks',
        type: 'POST',
        dataType: 'json',
        data: data,
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs){
            toList('/example/dayTask/dayTaskListPage');
        }
        tips(data.rs,data.msg);
    });
}

function cencel(){
    $("#tables").show();
    $("#add_dayTask").hide();
}
//操作按钮
function datTaskOpFormatter(value, row, index) {
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="editDayTask(\'' + row.id + '\')"><i class="fa fa-edit"></i> 编辑</a> ');
    return actions.join('');
}
