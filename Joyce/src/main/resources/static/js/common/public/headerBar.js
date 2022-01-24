$(function () {

    var val = $('.title').val();
    var menuList = [{
        id: 0,
        parentId: 0,
        val: "index",
        url: "/main",
        name: "首页",
        childs: []
    }, {
        id: 1,
        parentId: 0,
        val: "websocket",
        url: "/example/user/websocket",
        name: "一起聊天",
        childs: []
    }, {
        id: 2,
        parentId: 0,
        val: "columns",
        url: "/example/columns/getColumnsPage",
        name: "生成代码",
        childs: [3, 4]
    }, {
        id: 3,
        parentId: 2,
        val: "pg",
        url: "/example/db/packagePage",
        name: "配置包",
        childs: []
    }, {
        id: 4,
        parentId: 2,
        val: "db",
        url: "/example/db/dbPage",
        name: "配置数据源",
        childs: []
    }, {
        id: 5,
        parentId: 0,
        val: "uedit",
        url: "/example/uedit/uEditorListPage",
        name: "写笔记",
        childs: []
    },{
        id: 6,
        parentId: 0,
        val: "covid",
        url: "/example/covid/map",
        name: "疫情数据",
        childs: []
    },{
        id: 7,
        parentId: 0,
        val: "dayTask",
        url: "/example/dayTask/dayTaskListPage",
        name: "每日看板",
        childs: []
    }, {
        id: 8,
        parentId: 0,
        val: "au",
        url: "/aboutUs",
        name: "关于我们",
        childs: []
    }  ];
    let menu = $('#menu_bar');
    for (i = 0, len = menuList.length; i < len; i++) {
        if (menuList[i].parentId == 0) {
            if (menuList[i].childs.length == 0) {
                if (val == menuList[i].val) {
                    menu.append(" <li class='active'><a href=\"" + menuList[i].url + " \">" + menuList[i].name + "</a></li>");
                } else {
                    menu.append(" <li><a href=\"" + menuList[i].url + " \">" + menuList[i].name + "</a></li>");
                }
            } else {
                if (val == menuList[i].val) {
                    let temp = " <li class='active'><a href=\"" + menuList[i].url + " \">" + menuList[i].name + "</a><ul>";
                    for (let j = 0; j < menuList[i].childs.length; j++) {
                        temp = temp+"<li class='childs'><a href=\"" + menuList[menuList[i].childs[j]].url + " \">" + menuList[menuList[i].childs[j]].name + "</a></li>";
                    }
                    temp = temp+"</ul></li>";
                    menu.append(temp);
                    $('.childs').show();
                } else {
                    let temp = " <li><a href=\"" + menuList[i].url + " \">" + menuList[i].name + "</a><ul>";
                    for (let j = 0; j < menuList[i].childs.length; j++) {
                        temp = temp+"<li class='child'><a href=\"" + menuList[menuList[i].childs[j]].url + " \">" + menuList[menuList[i].childs[j]].name + "</a></li>";
                    }
                    temp = temp+"</ul></li>";
                    menu.append(temp);
                }
            }
        }
    }
});

/**
 * 添加资源背景
 */
function toAddSource(){
    toList("/example/source/sourcePage");
}

/**
 *用户下拉菜单显示和隐藏
 */
var index = 0;
function dropdownBlock(){
    if (index%2==0){
        $(".list-group").show();
    }else {
        $(".list-group").hide();
    }
    ++index;
}
$("#user_info").hover(function(){
    $(".list-group").show();
},function(){
    $(".list-group").hide();
});