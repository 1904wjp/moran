$().ready(function (){
    $('#download').hide();
    getDatabaseNames();
});


/**
 * 获取数据库名称
 */
function getDatabaseNames(){
    addLoadingModal("查询中...请稍后");
    $.ajax({
        url: '/example/columns/getAllDataBases',
        type: 'POST',
        dataType: 'json'
    }).done(function (data) {
        loading(false);
        if (data.rs){
            $('#dbName option').remove();
            $('#dbName').append('<option value="">请选择表</option>');
            for (let i = 0; i < data.data.length; i++) {
                $('#dbName').append("<option value=\""+data.data[i]+"\">"+data.data[i]+"</option>");
            }
        }else {
            tips(data.rs,data.msg);
        };
    }).fail(function (){
        tips(false,data.msg);
    });
}

/**
 * 获取对应表名
 */
function getTablesByDatabaseName(){
       /* $('#tableName')[0].options.length=0;*/
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
                $('#tableName div').remove();
                var res = data.data;
                var array = [];
                for (let i = 0; i < res.length; i++) {
                    var comment ="";
                    if (notNull(res[i].tableComment)){
                        comment = "("+res[i].tableComment+")";
                    }
                    array.push({
                        "name":res[i].tableName+comment,
                        "value":res[i].tableName
                    });
                }
                $("#tableNameDiv").wxSelect({
                    data:array
                });
               // console.log(array);
               /* $('#tableName').append('<option value="">请选择表</option>');
                 for (let i = 0; i < data.data.length; i++) {
                    var comment ="";
                    if (notNull(data.data[i].tableComment)){
                        comment = "("+data.data[i].tableComment+")";
                    }
                    $('#tableName').append("<option value=\""+data.data[i].tableName+"\">"+data.data[i].tableName+comment+"</option>");
                  }*/
            }else {
                tips(data.rs,data.msg);
            };
        }).fail(function (){
            tips(false,data.msg);
        });
}
/**
 * 下载文件
 */
function downloadWebFile() {
    toList("/example/columns/downloadWebFile");
    $('#download').hide();
}

function lookDetailTableData(){
   var dbName = $('#dbName').val();
   var tableName = $('#tableName').val();
   toList("/example/columns/getTablesPage/"+dbName+"/"+tableName)
}