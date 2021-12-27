$().ready(function (){
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
            field: 'endTime',
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
            formatter: userOpFormatter//格式化输出
        }];
        tables(obj, url, queryObj, columns);

}