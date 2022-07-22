/**
 * 获取资源
 */
function getSource(id) {
    var obj = $('.cube');
    $.ajax({
        url: '/example/source/getAlbum/' + id,
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        if (data.rs) {
            $('#pic').remove();
            $('#in_pic').remove();
            res = data.data;
            $.each(res.map, function (key, value) {
                if (key.includes("out")) {
                    var html = " <div class=\"" + key + "\">\n" +
                               " <img src=\"" + value.url + "\" class=\"pic\"/>\n" +
                               " </div>\n";
                    obj.append(html);
                }
            });

            $.each(res.map, function (key, value) {
                if (key.includes("in")) {
                    var html = " <span class=\"" + key + "\">\n" +
                               " <img src=\"" + value.url + "\" class=\"in_pic\"/>\n" +
                               " </span>\n";
                    obj.append(html);
                }
            });
        }
    }).fail(function () {
        tips(false, ajaxFailMsg);
    });
}


/**
 * 搜索
 */
function albumSearch(){
    $('#albumSearch').bootstrapTable("refresh");
}

//资源列表
function getAlbumTables() {
    var obj = $('#albumSearch');
    var url = '/example/source/getAlbumList';
    var queryObj = $('#query-form');
    var columns = [{
        checkbox: true
    }, {
        title: '序号',
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'name',
        title: '相册名称',
        sortables: true
    }, {
        field: 'total',
        title: '张数',

        sortables: true
    }, {
        field: 'type',
        title: '类型',
        sortables: true,
        formatter: function (value, row, index) {
            if (row.status === '0') {
                return "<button class='btn-success'disabled='disabled'style='padding:3px; border:10px;'>内外盒子</button>";
            }
           /* else if (row.status === '2') {
                return "<button class='btn-default'disabled='disabled'style='padding:3px; border:10px;'>备用</button>";
            } else {
                return "<button class='btn-secondary'disabled='disabled'style='padding:3px; border:10px;'>未知</button>";
            }*/
        }
    }, {
        field: 'createTime',
        title: '创建时间',
        sortables: true
    }, {
        field: 'createBy',
        title: '创建者',
        sortables: true
    }, {
        title: '操作',
        align: 'center',
        formatter: albumOpFormatter//格式化输出
    }];
    tables(obj, url, queryObj, columns);
}

/**
 * 按钮
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function albumOpFormatter(value,row,index){
    var actions = [];
    actions.push('<a class="btn btn-primary btn-sm" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" onclick="toList(\'/example/source/albumPage/' + row.id + '\')"><i class="fa fa-edit"></i> 查看</a> ');
    return  actions.join('');
}
