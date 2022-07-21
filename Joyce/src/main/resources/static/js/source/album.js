/**
 * 获取资源
 */
function getSource(id){
    var obj = $('.cube');
    $.ajax({
        url: '/example/source/get/'+id,
        type: 'GET',
        dataType: 'json'
    }).done(function (data) {
        if (data.rs){
            $('#pic').remove();
            $('#in_pic').remove();
            res = data.data;
            $.each(res.map,function (key,value) {
                if (key.includes("out")){
                    var html =" <div class=\""+key+"\">\n" +
                        "                   <img src=\""+value.url+"\" class=\"pic\"/>\n" +
                        "                </div>\n";
                    obj.append(html);
                }

            });

            $.each(res.map,function (key,value) {
                if (key.includes("in")){
                   var html=" <span class=\""+key+"\">\n" +
                        "                    <img src=\""+value.url+"\" class=\"in_pic\"/>\n" +
                        "                </span>\n";
                    obj.append(html);
                }
            });

        }
    }).fail(function () {
        tips(false,ajaxFailMsg);
    });
}