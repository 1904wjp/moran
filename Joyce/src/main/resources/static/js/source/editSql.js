$().ready(function (){
    getKeysData();
    getTablesByDatabaseName2();
});
var map = new Map();
var arr = [];
var tempArr =[];
var sqlTableColumns = [];
var set = new Set();
/**
 * 获取sql关键字
 */
function getKeysData(){
        addLoadingModal("查询中...请稍后");
        $.ajax({
            url: '/example/source/getEditSqlData',
            type: 'POST',
            dataType: 'json'
        }).done(function (data) {
            loading(false);
            if (data.rs){
                //$('#dbName div').remove();
                var res = data.data;
                for (let i = 0; i < res.length; i++) {
                    arr.push({
                        "name":res[i],
                        "value":res[i]
                    });
                }
                tempArr = arr;
                $(".data-select").wxSelect({
                    data:arr
                }," ");
            }else {
                tips(data.rs,data.msg);
            };
        }).fail(function (){
            tips(false,ajaxFailMsg);
        });
    }

/**
 * 获取对应表
 */
function getTablesByDatabaseName2(){
    var dbName = $('#dbName').val();
    dbName = "zm_ccsp_so";
    /* $('#tableName')[0].options.length=0;*/
    //addLoadingModal("查询中...请稍后");
    let  data = {
        databaseName:dbName
    }
    $.ajax({
        url: '/example/columns/getAllTables2',
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {
      //  loading(false);
        if (data.rs){
            // $('#tableName div').remove();
            var res = data.data;
            map.set(dbName,res);
        }else {
            tips(data.rs,data.msg);
        };
    }).fail(function (){
        tips(false,data.msg);
    });
}

$("input[name=exe]").click(function () {
    let  data = {
        sql:$("input[name=x-sql]").val()
    }
    $.ajax({
        url: '/example/db/executeSimpleSql',
        type: 'GET',
        dataType: 'json',
        data: data,
    }).done(function (data) {
        tips(data.rs);
    }).fail(function (){
        tips(false,data.msg);
    });
});

$("input[name=x-sql]").keydown(function(e){
   // console.log(e.keyCode);
    var text =  $(this).val();
    //按下点触发
    if (e.keyCode===190 || e.keyCode===110){
       // var lastChar = text.substr(-1);
        //console.log(lastChar);
            var sqlArr = text.split(/[ ,.]/);
            var lastChar = sqlArr[sqlArr.length-1];
             sqlTableColumns = [];
             set.clear();
        for (let i = 0; i < sqlArr.length; i++) {
            //console.log(sqlArr[i],lastChar)
            if (sqlArr[i]===lastChar){
                set.add(sqlArr[i]);
                if ((i-1)>=0&&(i-1)<sqlArr.length){
                    set.add(sqlArr[i-1]);
                }
            }
        }
       // console.log(set)
        for (let setKey of set) {
           //console.log('tableMap',tableMap)
            var newMap = objToStrMap(map.get('zm_ccsp_so')).get(setKey);
            if (null!=newMap){
                if (newMap.length!==0){
                    for (let i = 0; i < newMap.length; i++) {
                        sqlTableColumns.push({'name':newMap[i].columnName,'value':newMap[i].columnName});
                    }
                }
            }
        }
        $(".data-select").wxSelect({
            data:sqlTableColumns
        },".");
    }
    //按下点空格
    if (e.keyCode===32 ){
        $(".data-select").wxSelect({
            data:tempArr
        }," ");
    }
});

function objToStrMap(obj) {
    let strMap = new Map();
    for (let k of Object.keys(obj)) {
        strMap.set(k+'', obj[k]);
    }
    return strMap;
}

