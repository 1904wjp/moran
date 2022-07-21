/**
 * 获取资源
 */
function getSource(id){
    var obj = $('.wrap');
    obj.remove();
    $.ajax({
        url: '/example/source/get/'+id,
        type: 'POST',
        dataType: 'json',
        data: data,
    }).done(function (data) {

        if (data.rs){
            res = data.data;
            var html = '';
            $.each(res.map,function (key,value) {
                if (key.includes("out")){
                    html+=" <div className=\""+key+"\">\n" +
                        "                    <img src=\""+value.url+"\" className=\"pic\"/>\n" +
                        "                </div>";
                }
                if (key.includes("in")){
                    html+=" <span className=\""+key+"\">\n" +
                        "                    <img src=\""+value.url+"\" className=\"in_pic\"/>\n" +
                        "                </span>";
                }
            });
            obj.append(html);
        }
    }).fail(function () {
        tips(false,ajaxFailMsg);
    });
}