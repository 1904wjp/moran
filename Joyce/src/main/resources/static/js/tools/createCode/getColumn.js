$().ready(function (){
    $('#download').hide();
});

/**
 * 获取对应表名
 */
function getTablesByDatabaseName(){
        $('#tableName')[0].options.length=0;
        addLoadingModal("查询中...请稍后");
        let  data = {
            databaseName:$('#dbName').val()
        }
        $.ajax({
            url: '/example/columns/getAllTables',
            type: 'POST',
            dataType: 'json',
            data: data,

        }).done(function (data) {
            loading(false);
            if (data.rs){
                $('#tableName').append('<option value="">请选择表</option>');
                 for (let i = 0; i < data.data.length; i++) {
                    $('#tableName').append("<option value=\""+data.data[i].tableName+"\">"+data.data[i].tableName+"("+data.data[i].tableComment+")"+"</option>");
                  }
            }else {
                tips(data.rs,data.msg)
            };
        }).fail(function (){
            tips(false,data.msg)
        });
}
/**
 * 下载文件
 */
function downloadWebFile() {
    toList("/example/columns/downloadWebFile");
    $('#download').hide();
}