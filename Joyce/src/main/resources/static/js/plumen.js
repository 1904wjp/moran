(function($){
	var options2;
	$.fn.extend({
		//插件名称；isBlank是切割符号，若为'0'则不切割
		_wx_ison: false,
		wxSelect:function(options,isBlank){
			////console.log("isBlank:",isBlank);
			options2 = options;
		//	//console.log("options1:",options2.data);
			var defaults = {
				data: [],
				height:240
			};
			var options = $.extend(defaults,options2);
		//	//console.log("options2:",options.data);
			return this.each(function(){
				//创建元素
				var _this = this,
					w = $(_this).width() || $(_this).find("input").width();
					var flag = !$(_this).attr("data-bind");
					if (isBlank!=='0'){
						flag = true;
					}
					if(flag){
						init();
						////console.log("options:",options.data);
						renders(options.data);
					}

				function init(){
					$(_this).attr("data-bind",true);
					$(_this).append("<span class='wxSelect_bottom'></span>")
					$(_this).find(".wxSelect_bottom").css({
						width:"24px",
						height:"95%",
						background:"#fff",
						display:"inline-block",
						position:"absolute",
						right:"5px",
						top:"1px",
						zIndex:2
					})
					$(_this).css({position:"relative",width:w + "px"}),
					$(_this).append("<span class='wxSelect_label'></span>")
					$(_this).find(".wxSelect_label").css({
						display: "inline-block",
						width:"0",
					    height:"0",
					    borderWidth:"8px 8px 0",
					    borderStyle:"solid",
					    borderColor:"#6c6c6c transparent transparent",
						cursor: "pointer",
						position: "absolute",
						right: "3px",
						top: "13px",
						borderRadius: '3px',
						zIndex:3
					})
					$(_this).append('<div class="dataBox"><ul class="dataList"></ul></div>');
					$(_this).find(".dataBox").css({
						width: "100%",
						maxHeight: "options.height" + "px",
						overflowY: "scroll",
						overflowX: "hidden",
						background:" #fff",
						boxShadow: "1px 2px 4px #ccc",
						display: "none",
						position:'absolute',
						zIndex:'999'
					});
					$(_this).find(".wxSelect_label").on("click",function(event) {
					$(_this).find(".dataBox").slideToggle(100);
					});
					function input(e){
						var val = $(_this).find("input").val().trim()
						,dataSelect = []
						,data = options.data;
						////console.log(val);
						if(val != ""){
							if (isBlank!=='0'){
								var valArray = val.split(isBlank);
								val = valArray[valArray.length-1];
								valArray.pop();
								oldVal = valArray.join(" ");
							}
							for(var i in data){
								if(data[i]['name'].indexOf(val) !== -1){
									dataSelect.push(data[i]);
								}
							}
							renders(dataSelect);
						}else{
							renders(data);
						}
						$(_this).find(".dataBox").slideDown(50);
					}
					$(_this).find("input").eq(0).on("input",input);
					$(_this).find(".dataList").on("click","li",function(){
						var val = $(this).text();
						if (isBlank!='0'){
							val =oldVal+" " + $(this).text();
						}
						var data = $(this).attr("value");
						if(val != ""){
							$(_this).find(".wx-input").val(val).attr("data-value",data);
							$(_this).find(".dataBox").slideUp(50);
						}
					});
					if(!$.fn._wx_ison){
						$(document).on("click",function(event){
							var e = event || window.event;
							e.stopPropagation();
							var flag = true
							,tag = $(".input-Selector")
							,target = $(e.target);
					        if(target.closest(tag).length == 0 && flag == true){
								$(".input-Selector").find(".dataBox").slideUp(50);
								flag = false;
				       		}
						});
						$.fn._wx_ison = true;
					}
				}
				//渲染列表
				function renders(data){
						$(_this).find(".dataList").html("");
						var html = "";
						for(var i = 0; i<data.length;i++){
							 html += "<li value="+data[i].value+">"+data[i].name+"</li>";
						}
						$(_this).find(".dataList").append(html);
						$(_this).find(".dataList li").css({
							padding:"8px 0 8px 5px",
							border:"1px solid #edf7ff"
						});
						$(_this).find(".dataList li").hover(function(){
							$(this).css({
								background:"#d5dee6"
							})
						},function(){
							$(this).css({background:"none"})
						})
				}
			})
		}
	})
})(jQuery);
