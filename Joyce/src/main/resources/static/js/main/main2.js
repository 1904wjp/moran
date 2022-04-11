$().ready(function () {
    initMainPictures();
    addLoadingModal();
});

/**
 * 初始化图片
 */
function initMainPictures() {
    $.ajax({
        url: '/example/source/getSourceImage',
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        loading(false);
        if (data.rs) {
            if (data.data.length==0){
                tips(false, "该用户未设置图片");
            }
            let res = data.data;
            $('.intro__title').text(res.source.sourceName);
            $('.intro__description__content').text(res.source.descContent);
            $('.intro__image').attr("src", res.source.url);
            let arrys = res.sources;
            let start = 0;
            let len = arrys.length;
            if (len>=12){
                start = len -12;
            }
            for (let i = start; i < len; i++) {
                $('.items-wrap').append("<a href=\"#\" class=\"item\">\n" +
                    "            <input type='hidden' value='" + arrys[i].id + "'/>" +
                    "            <img class=\"item__image\" src=\"" + arrys[i].url + "\" alt=\""+arrys[i].sourceName+"\"/>\n" +
                    "            <h2 class=\"item__title\">arrys[i].sourceName</h2>\n" +
                    "        </a>");
            }
        } else {
            tips(data.rs, data.msg);
        }
        ;
    }).fail(function () {
        tips(false, ajaxFailMsg);
    });
}


$(".items-wrap").on('click', '.item', function () {
    var data = {
        "id": $(this).find("input").val(),
        "status": 1
    };
    Ewin.confirm({message: "确认设置成主页壁纸？"}).on(function (e) {
        if (!e) {
            return;
        }
        addLoadingModal();
        $.ajax({
            url: '/example/source/setMainSource',
            type: 'POST',
            dataType: 'json',
            data: data,
        }).done(function (data) {
            loading(false);
            if (data.rs) {
                /*.trigger--active .icon--cross {
	opacity: 1;
	-webkit-transform: scale3d(1, 1, 1);
	transform: scale3d(1, 1, 1);
}*/
                $('.intro__title').text(data.data.sourceName);
                $('.intro__description__content').text(data.data.descContent);
                $('.intro__image').attr("src", data.data.url);
            }
            tips(data.rs, data.msg);
        }).fail(function () {
            loading(false);
            tips(false, ajaxFailMsg);
        });
    });
});