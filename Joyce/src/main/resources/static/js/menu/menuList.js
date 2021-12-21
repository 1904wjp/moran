$(document ).ready(function(){
   menuListInitFunc();
   menuFunc();
});

/**
 * 操作菜单函数
 */
function menuFunc(){
    $(".menu").on('click','.list',function(){
        //找到主菜单单项对应的子菜单项
        var ulNode = $(this).next("ul");
        if (notNull(ulNode)){
            ulNode.slideToggle();
            console.log(ulNode.next("li").attr("class"))
        }
    });
}

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
                    html = html+" <li class='list' ><a href='"+item.menuUrl+"'><cite>"+item.menuName+"</cite></a> ";
                    if (notNull(item.children)){
                        html = html+"<ul>" ;
                        $.each(item.children,function (childIndex,childItem){
                            html = html+" <li class='items'><a href='"+childItem.menuUrl+"'><cite>"+childItem.menuName+"</cite></a></li> "
                        });
                        html = html+"</ul>";
                    }
                    html=html+"</li>";
                });
                $(".menu").append(html);
            }
        }
    }).fail(function () {
    });
    $(".menu").append(html);
}


/*menuTable();
 var list = document.querySelectorAll('.list');*/
/* function accordion(e) {
       e.stopPropagation();
       if (this.classList.contains('active')) {
           this.classList.remove('active');
       } else
       if (this.parentElement.parentElement.classList.contains('active')) {
           this.classList.add('active');
       } else
       {
           for (i = 0; i < list.length; i++) {
               list[i].classList.remove('active');
           }
           this.classList.add('active');
       }
   }
   for (i = 0; i < list.length; i++) {
       list[i].addEventListener('click', accordion);
   }*/