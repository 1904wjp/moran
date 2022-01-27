$().ready(function () {
    initMainPictures();
    addLoadingModal();
});

/**
 * 初始化图片
 */
function initMainPictures() {
    $.ajax({
        url: '/getSourceImage',
        type: 'GET',
        dataType: 'json',
    }).done(function (data) {
        loading(false);
        if (data.rs) {
            $('.intro__title').text(data.data.source.sourceName);
            $('.intro__description__content').text(data.data.source.descContent);
            $('.intro__image').attr("src", data.data.source.url);
            let arrys;
            for (let i = 0; i < (arrys =data.data.sources).length; i++) {
                $('.items-wrap').append("<a href=\"#\" class=\"item\">\n" +
                    "            <input type='hidden' value='" + data.data.sources[i].id + "'/>" +
                    "            <img class=\"item__image\" src=\"" + data.data.sources[i].url + "\" alt=\"item01\"/>\n" +
                    "            <h2 class=\"item__title\">data.data.sources[i].sourceName</h2>\n" +
                    "        </a>");
            }
        } else {
            tips(data.rs, data.msg);
        }
        ;
    }).fail(function () {
        tips(false, ajaxFailMsg)
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
            if (data.rs) {
                $('.intro__title').text(data.data.sourceName);
                $('.intro__description__content').text(data.data.descContent);
                $('.intro__image').attr("src", data.data.url);
            }
            loading(false);
            tips(data.rs, data.msg);
        }).fail(function () {
            loading(false);
            tips(false, ajaxFailMsg);
        });
    });
});