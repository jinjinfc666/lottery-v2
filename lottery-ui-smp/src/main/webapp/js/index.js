$(function () {
	getBanner();
	queryNews();
	queryCarouse();
	gamesShow();
});
//////////////// 轮播 /////////////////
function getBanner() {
	$.ajax ({
		url: "/asp/queryBannerList.php",
		cache: true,
		dataType: "json",
		data: {bannerType: "0"},//只查询PC端banner
		type: 'POST',
		success: function (result) {
			var bdiv = '';
			var pagerAdd = '';
			var btitle='';
			if ( result.code == "10000" && result.data.length>0) {
				var values = result.data;
				for (var i = 0; i < values.length; i++) {
					bdiv += '<a href="' + values[ i ].hyperlinkUrl + '" style="background-image:url(' + values[ i ].showUrl + ')"></a>';
					pagerAdd += '<span data-num="'+i+'"></span>'
					btitle += '<span class="b_t_item" data-num="'+i+'" style="width:'+(100/values.length)+'%;" title="'+values[i].title+'">'+values[i].title+'</span>'
				}
				//<a href="/t1/promotion.jsp" class="banner1" style="background-image: url(/images/banner/pc4.jpg);"></a>
				$ (".j-bannerImg").html (bdiv);
				$ (".j-bannerPage").html (pagerAdd);
				$(".j-bannerTitle").html(btitle);
				// 开始轮播
				bannerBind();
				$(".j-bannerPage span").eq(0).click();
				$(".j-bannerTitle .b_t_item").eq(0).click();

			}
			return;
		},
		error: function () {
			return;
		}
	})
}
function bannerBind() {
	bannerAuto();
	$('.j-bannerPage > span,.j-bannerTitle .b_t_item').on('click',function () {
		clearInterval(bannerInterval);
		var num = $(this).data('num');
		bannerChange(num);
		bannerAuto();
	})
}
var bannerInterval;
var to_right=true;
function bannerAuto() {
	bannerInterval = setInterval(function () {
		var num = $('.j-bannerPage > .active').data('num');
		var total = $('.j-bannerPage > span').length;
		if(to_right){
			if (num+1== total) {
				to_right = false;
				num--;
			}else{
				num++;
			}
		}else{
			if (num== 0) {
				to_right = true;
				num++;
			}
			num--;
		}
		bannerChange(num);
	},5000)
}
function bannerChange(num) {
	$('.j-bannerTitle .b_t_item').removeClass('active').addClass("left");
	$('.j-bannerTitle .b_t_item').eq(num).addClass('active');
	$('.j-bannerPage > span').removeClass('active');
	$('.j-bannerPage > span').eq(num).addClass('active');
	for(var i=0;i<num;i++){
		$('.j-bannerImg > a').eq(i).css("left",'-'+(num-i)+'00%');
	}
	for(var i=num;i<$('.j-bannerImg > a').length;i++){
		$('.j-bannerImg > a').eq(i).css("left",(i-num)+'00%');
	}
}
////////公告//////////
function queryNews () {
	$.getJSON ('/asp/getNewAnnouncement.php', function (data) {
		if (data.code=='10000') {
			var val=data.data;
			var marquee = '';
			var shows= val.length;
			if (shows>6){
				$ ('.j-marquee').addClass('overflow');
			}
			for (var i =0;i<shows;i++) {
				marquee += '<a href="javascript:void(0);" class="j-news"><span class="ico1"></span><span class="des">' + val[ i ].title + '</span><span class="time">'+val[i].createtime.slice(0,10)+'</span><span class="hidden content">'+val[i].content+'</span></a>'
			}
			$ ('.j-marquee').html (marquee);
			clickNews();
		}else{
			var texts ='<a href="javascript:void(0);"><span class="ico1">提示</span><span class="des">'+data.message+'</span></a>';
			$ ('.j-marquee').html (texts);
		}
	});
}

// 新聞點擊事件
function clickNews(){
	$(".j-marquee .j-news").click(function(){
		var $this=$(this);
		var content=$this.find(".content").html();
		$("#j_new_show").html(content.replace(/\r\n/g,'<br/>').replace(/\n/g,'<br/>'));
		$("#j_news_").show();
	})
}
////////////中奖信息/////////
function queryCarouse(){
	$.getJSON ('/asp/getCarouselListAll.php', function (data) {
		if(data.code=='10000') {
			var str = '';
			var val=data.data;
			for (var i in val) {
				var money = Math.floor (val[ i ].prize);
				if (money >= 1000) {
					money = splitNum (money);
				}
				str += '<a href="javascript:void(0);" ' + 'class="cfx">';
				str += 	'<img  class="fl" src="'+val[i].link+'">';
				str += 	'<div class="fl des" >'
				str += 		'<span>来自' + val[ i ].userName + '<br/>在' + val[ i ].platform + '<span class="font-blue">'+val[ i ].gameName+'</span>赢得 <span class="font-red"><br/>' + splitNum(val[ i ].prize)+ '</span>元</span>';
				// str += 		'<p class="time">'+val[i].prizeTime+'</p>';
				str += 	'</div>';
				str += '</a>';
			}
			$ ('.j-group').html (str);
			// 开始滚动控制
			var length = val.length;
			if(length>3){
				setInterval (function () {
					$ ('.j-group > a').eq (0).animate({'marginTop': '-92px'},500,'swing',function(){
						var html = '<a href="' + $ ('.j-group > a').eq (0).attr ('href') + '" target="_blank" class="cfx">' + $ ('.j-group > a').eq (0).html () + '</a>';
						$ ('.j-group > a').eq (0).remove ();
						$ ('.j-group').append (html);
					});
				}, 3000)
			}
		}else{
			// toast_tip(data.message);
			return;
		}
	});
}
///////////热门游戏//////////
function gamesShow () {
	$.post ('/asp/queryRecommandGamesList.php', {gameType: "0"}, function (data) {
		if(data.code=='10000') {
				gameBuild(data.data);
		}else{
			// toast_tip(data.message);
			return;
		}
	});
}
function gameBuild(data){
	var $container = $ (".j-hotGame");
	var game_tpl = [
		'<div class="hotgameitem hot_{{gameType}}" id="{{gameId}}" >',
			'<div class="images">',
				'<img src="https://www.staticsources.com/images/{{pictype}}games/{{picPath}}">',
				'<div class="btns">',
					'<a class="btn play j-needlogin"  {{playPath}} >开始游戏</a>',
					'<a class="btn try"href="{{tryPath}}" target="_blank">试玩游戏</a>',
			'	</div>',
			'</div>',
			'<p class="title">{{gameName}}</p>',
		'</div>',
	].join ('');
	var htmlArr = [];
	// 设置tab
	for(var i=0;i<data.length;i++){
		var _games=[];
		//设置每个类型的游戏列表
		for(var i=0;i<data.length;i++){
			var obj = data[i];
			var caPic=obj.platform.toLowerCase();
			if(obj.platform=='TTG-MG'){
				caPic='ttg';
			}
			if(obj.platform.toLowerCase()=='dt'||obj.platform.toLowerCase()=='png'||obj.platform.toLowerCase()=='ttg'){
				obj.picPath= obj.gameId.toLowerCase()+'.png';
			}else{
				obj.picPath=obj.gameId.toLowerCase()+'.jpg';
			}
			_games.push(game_tpl.replace (/\{\{gameId\}\}/g, obj.gameId)
				.replace (/\{\{pictype\}\}/g, caPic)
				.replace (/\{\{gameType\}\}/g, obj.platform)
				.replace (/\{\{picPath\}\}/g, obj.picPath)
				.replace (/\{\{playPath\}\}/g, getLinkPlay(obj))
				.replace (/\{\{tryPath\}\}/g, getLinkDemo(obj))
				.replace (/\{\{gameName\}\}/g, obj.gameName)
			)
		}
		$(".j-hotgamebox").append(_games.join(''));
	}
	gameEvent();
	$(".j-hotGameNav .h_g_n_select").eq(0).click();
}
//点击交互
function gameEvent(){
	var _index=0;
	var _target='';
	$(".j-hotGameNav .h_g_n_select").click(function(){
		_index=0;
		$(this).addClass("active").siblings().removeClass("active");
		_target=$(this).data('target');
		$(".hotgameitem").hide();
		$("."+_target).show();
		// $(".hotgamebox").addClass("hidden");
		// $(_target).removeClass("hidden");
	});
	$(".j-hotgame-l").click(function(){
		var all=$("."+_target).length;
		if(_index>0){
			_index=_index-1;
			for(var i=0;i<all;i++){
				if(i<_index||i>(_index+6)){
					$("."+_target).eq(i).hide();
				}else{
					$("."+_target).eq(i).show();
				}
			}
		}else{
			return;
		}
	});
	$(".j-hotgame-r").click(function(){
		var all=$("."+_target).length;
		if(_index<(all-6)){
			_index=_index+1;
			for(var i=0;i<all;i++){
				if(i<_index||i>(_index+6)){
					$("."+_target).eq(i).hide();
				}else{
					$("."+_target).eq(i).show();
				}
			}
		}else{
			return;
		}
	})
}
