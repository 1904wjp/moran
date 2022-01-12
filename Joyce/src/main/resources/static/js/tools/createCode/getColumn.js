$().ready(function (){
    $('#download').hide();
});

/**
 * 获取对应表名
 */
function getTablesByDatabaseName(){
        $('#tableName')[0].options.length=0;
        addLoadingModal($("#loading"),"查询中...请稍后");
        let  data = {
            databaseName:$('#dbName').val()
        }
        $.ajax({
            url: '/example/columns/getAllTables',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            loading(false);
            if (data.rs){
                 for (let i = 0; i < data.data.length; i++) {
                    $('#tableName').append("<option value=\""+data.data[i].tableName+"\">"+data.data[i].tableName+"("+data.data[i].tableComment+")"+"</option>");
                  }
            }else {
                toastr.error(data.msg);
            };
        }).fail(function (){
            toastr.error(ajaxFailMsg);
        });
}