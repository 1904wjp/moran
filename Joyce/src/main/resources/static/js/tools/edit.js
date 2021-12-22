var testEditor;
$(document).ready(function () {
    getArticleList();
});

/**
 * 获取文章列表
 */
function getArticleList() {
    var obj = $('#articleTable');
    var url = '/example/uedit/getList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'title',
        title: '文章名',
        sortables: true
    }, {
        field: 'author',
        title: '作者',
        sortables: true
    }, {
        field: 'createTime',
        title: '创建时间',
        sortables: true
    }, {
        field: 'updateTime',
        title: '更新时间',
        sortables: true
    }, {
        field: 'updateBy',
        title: '更新者',
        sortables: true
    }, {
        title: '操作',
        align: 'center',
        formatter: articleOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
}

//操作按钮
function articleOpFormatter(value, row, index) {
    var actions = [];
    actions.push('<a class="btn btn-success btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#queryModel"  onclick="queryArticleFuc(\'' + row.id + '\')"><i class="fa fa-adn"></i> 查看</a> ');
    /*actions.push('<a class="btn btn-danger btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteModal"  onclick="deleteArticleFuc(\''+row.id+'\')"><i class="fa fa-remove"></i> 删除</a> ');*/
    /*actions.push('<a class="btn btn-warning btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#deleteAuthorityModal"  onclick="deleteAuthority(\''+row.id+'\')"><i class="fa fa-remove"></i> 删除权限</a> ')*/
    return actions.join('');
}

/**
 * 保存文章
 */
function saveArticleFuc() {
    let resData = {
        "id": $('#article_id').val(),
        "title": $('#in_title').val(),
        "author": $('#in_author').val(),
        "content": $('#in_content').val()
    };
    let filter = {
        "title": $('#in_title').val(),
        "author": $('#in_author').val(),
        "content": $('#in_content').val()
    }
    if (vailDate(filter)) {
        Ewin.confirm({message: "确认提交数据？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: "/example/uedit/saveArticle",
                type: 'POST',
                dataType: 'json',
                data: resData
            }).done(function (data) {
                if (data.rs) {
                    $('#add_article_info').modal('hide');
                    toastr.success(data.msg);
                    toList("/example/uedit/uEditorListPage");
                } else {
                    toastr.error(data.msg);
                }
            }).fail(function () {
                toastr.error(ajaxFailMsg);
            });
        });
    }
}

/**
 * 刷新列表
 */
function articleSearch() {
    //刷新Table，Bootstrap Table 会自动执行重新查询
    $('#articleTable').bootstrapTable('refresh');
}

//删除id为ids的数据集合
function deleteArticleFuc(id) {
    let ids = getIds($("#articleTable"));
    let data = {"ids": ids};
    Ewin.confirm({message: "确认删除所选的数据？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            url: '/example/uedit/delete',
            type: 'POST',
            dataType: 'json',
            data: data,
            //传过来的data需要.data才可以获取当前对象。因为data是封装过的
        }).done(function (data) {
            if (data.rs) {
                toastr.success(data.msg);
                articleSearch();
            } else {
                toastr.error(data.msg);
            }
        }).fail(function () {
            toastr.error(ajaxFailMsg);
        });
    });
}

/**
 * 添加文章
 */
function addArticleFuc() {
    toList("/example/uedit/addUEditorPage");
    /*  alert("请按F9开始");*/
}

/**
 * 查询
 * @param id
 */
function queryArticleFuc(id) {
    let hello223 = id;
    toList("/example/uedit/get/" + hello223);
    /*var data={
           "id":id
       }
       $.ajax({
           url: '/example/uedit/get',
           type: 'POST',
           dataType: 'json',
           data: data,
           //传过来的data需要.data才可以获取当前对象。因为data是封装过的
       }).done(function (data) {
           if (data.rs){
               $("#article_id").val(data.data.id);
               $("#in_title").val(data.data.title);
               $("#in_content").attr("text",data.data.content);
               toList("/example/uedit/uEditorPage");
           }else {
               alert(data.msg);
           };
       }).fail(function () {
       });*/
}
