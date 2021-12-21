$(document ).ready(function(){
   menuListInitFunc();
   /*menuFunc();*/
});

/**
 * 菜单初始化
 */
function menuListInitFunc(){
    var html = "";
    $.ajax({
        url: '/example/sysMenu/doGetMenus',
        type: 'POST',
        dataType: 'json',
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.code==200){
            if (notNull(data.data)){
                $.each(data.data,function (index,item){
                    html = html+" <li class='main_setting' ><cite>"+item.menuName+"</cite>" +
                        " <a onclick='addMenuFuc("+item.id+")'><span class=\"glyphicon glyphicon-plus\"></span></a>"+
                        " <a onclick='editMenuFuc("+item.id+")'><span class=\"glyphicon glyphicon-edit\"></span></a>"+
                        " <a onclick='deleteMenuFuc("+item.id+")'><span class=\"glyphicon glyphicon-trash\"></span></a>"
                    if (notNull(item.children)){
                        html = html+"<ul>" ;
                        $.each(item.children,function (childIndex,childItem){
                            html = html+" <li><cite>"+childItem.menuName+"</cite>" +
                                " <a onclick='editMenuFuc("+childItem.id+")'><span class=\"glyphicon glyphicon-edit\"></span></a>"+
                                " <a onclick='deleteMenuFuc("+childItem.id+")'><span class=\"glyphicon glyphicon-trash\"></span></a>"
                                "</li> "
                        });
                        html = html+"</ul>";
                    }
                    html=html+"</li>";
                });
                $("#sum_menu_setting").append(html);
            }
        }
    }).fail(function () {
    });
}

/**
 * 操作菜单函数
 */
function menuFunc(){
    $("body").on('click',".main_setting ",function(){
        //找到主菜单单项对应的子菜单项
        var ulNode = $(this).next("ul");
        if (notNull(ulNode)){
            ulNode .slideToggle();
        }
    });
}

/**
 * 添加菜单
 * @param pid
 */
function addMenuFuc(pid){
    $.ajax({
        url: '/example/sysMenu/doAddMenu',
        type: 'POST',
        dataType: 'json',
        data: {id:pid}
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        if (data.rs){
            toList("/example/sysMenu/addMenu/"+pid);
        }else {
            alert(data.msg);
        }
    }).fail(function () {
    });
}

/**
 * 删除菜单
 * @param id
 */
function deleteMenuFuc(id){
    $.ajax({
        url: '/example/sysMenu/doDeleteMenu',
        type: 'POST',
        dataType: 'json',
        data: {id:id}
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        toList("/example/sysMenu/settingMenu");
    }).fail(function () {
    });
}

/**
 * 添加菜单
 * @param id
 */
function editMenuFuc(id){
    $.ajax({
        url: '/example/sysMenu/doQueryMenu',
        type: 'POST',
        dataType: 'json',
        data: {id:id}
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
         if (data.rs){
                toList("/example/sysMenu/addMenu/"+id);
         }
    }).fail(function () {
    });
}

/**
 * 添加菜单
 * @param
 */
function saveMenuFuc(){
    data ={
        menuName:$('#menuName').val(),
        menuUrl:$('#menuUrl').val(),
        fileUrl:$('#fileUrl').val(),
        parentId:$('#parentId').val()
    }
    $.ajax({
        url: '/example/sysMenu/doSaveMenu',
        type: 'POST',
        dataType: 'json',
        data: data
        //传过来的data需要.data才可以获取当前对象。因为data是封装过的
    }).done(function (data) {
        console.log(data)
        if (data.rs){
            toList("/example/sysMenu/settingMenu");
        }
    }).fail(function () {
    });
}