$().ready(function (){
    $('#download').hide();
    getDatabaseNames();
});


/**
 * 获取数据库名称
 */
function getDatabaseNames(){
    var dbName = $('#dbName').val();
    if (isBlank(dbName)){
        addLoadingModal("查询中...请稍后");
        $.ajax({
            url: '/example/columns/getAllDataBases',
            type: 'POST',
            dataType: 'json'
        }).done(function (data) {
            loading(false);
            if (data.rs){
                //$('#dbName div').remove();
                var res = data.data;
                var array = [];
                for (let i = 0; i < res.length; i++) {
                    array.push({
                        "name":res[i],
                        "value":res[i]
                    });
                }
                $("#dbNameDiv").wxSelect({
                    data:array
                },0);
            }else {
                tips(data.rs,data.msg);
            };
        }).fail(function (){
            tips(false,ajaxFailMsg);
        });
    }else {
        getTablesByDatabaseName();
    }
}

/**
 * 获取对应表名
 */
function getTablesByDatabaseName(){
    var dbName = $('#dbName').val();
       /* $('#tableName')[0].options.length=0;*/
        addLoadingModal("查询中...请稍后");
        let  data = {
            databaseName:dbName
        }
        $.ajax({
            url: '/example/columns/getAllTables',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            loading(false);
            if (data.rs){
               // $('#tableName div').remove();
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
                },0);
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
$('#tableName').change(tableStructure());
