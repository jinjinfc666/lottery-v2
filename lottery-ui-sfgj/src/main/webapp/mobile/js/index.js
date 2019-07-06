$(function () {
    function init(){
        getHotGames();
        ttgAction();
        initBanner();
        var cvalue = sessionStorage.getItem('notice');
        if(!cvalue){
        	getNotice();
        }
        else{
            if ( $('#j-isLogin').val() ) {
                if (cvalue == '2') {
                    getNotice();
                }
            }
        }
    }
    // 获取弹框公告
    function getNotice(){
        var $tipsModal=$('#j-tips'),
            $tit=$tipsModal.find('.j-noticeTitle'),
            $content=$tipsModal.find('.j-noticeContent');
        $.post('/asp/checkConfigSystem.php',{typeNo:'type002', itemNo:'001'},function(result){
            if(result.code=='10000'){
                var array = result.data.split('#');
                $tit.html(array[0]);
                $content.html(array[1]);
                $tipsModal.addClass('show');
                $tipsModal.find('.btn-close').on('click',function () {
                    $tipsModal.removeClass('show');
                });
                if( sessionStorage.getItem('notice') == '2' ){
                    sessionStorage.setItem('notice',3);
                }
                else{
                    sessionStorage.setItem('notice',1);
                }
            }
        })
    }
    /**
     * 获取热门游戏
     */
    function getHotGames(){
        $(document).on('click','.j-login',function(){
            if(!sessionStorage.getItem('permission')||sessionStorage.getItem('permission') === '0'){
                toast_tip('请先登录账户！！');
                return false;
            }
        });
        var arr=[];
        $.getJSON('/asp/queryRecommandGamesList.php',{gameType: "1"},function(data){
            data=data.data;
            if(!data||data.length==0) return;
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                arr.push({
                    "eName": "",
                    "name": obj.gameName,
                    "id": obj.gameId,
                    "code": obj.gameId,
                    "category": obj.platform,
                    "type": "SLO",
                    "line": "",
                    "state": 1,
                    "pic": obj.picPath,
                    "tag": []
                });
            }
            SlotMg.DataList=arr;
            SlotMg.IsLazyLoad=false;
            SlotMg.GameContainer=$('#j-gameContainer');
            SlotMg.builHtml(SlotMg.DataList);
            SlotMg.lazyload();

            builSlider();
        });

        function builSlider(){
            var items = $('.hot_game_list').find('.game-info');
            var itemWidth = 0;
            items.each(function(index, ele2) {
                if ( items.eq(index).hasClass('hidden') ) {
                }
                else{
                    itemWidth = itemWidth + $(ele2).outerWidth(true);
                }
            });
            $('.hot_game_list .auto_width').width(itemWidth+15);
        }
    }

	function initBanner(){
		//查询配置的banner图片
		$.ajax({
	        url:"/asp/queryBannerList.php",
	        cache: false,
	        dataType : "json",
	        data : {bannerType:"1"},//只查询手机端banner
	        type: 'POST',
	        success:function(data){
	        	var bdiv = '';
	          	if(data.code =="10000" && data.data ){
	          		var values = data.data;
	          		for(var i=0;i<values.length;i++){
	          			bdiv += "<div class='swiper-slide'><a href='"+values[i].hyperlinkUrl+"'><img src='"+values[i].showUrl+"' style='width:100%'/></a></div>";
	          		}
	          	}
	          	$("#ulid").html(bdiv);
	          	initSlide();//初始化轮播图方法
	          	return;
	        },
	        error:function(){
	        	initSlide();//初始化轮播图方法
	          	return;
	        }
	      });
	}

    /**
     * 初始化slide
     */
    function initSlide(){
        new Swiper('.swiper-container',{
            speed: 500,
            loop:true,
            autoplay:5000,
            grabCursor: true,
            paginationClickable: false,
            autoplayDisableOnInteraction: false
        });
    }

    function ttgAction(){
        //TTG老虎机
        var $btnTtg=$('#j-btnTtg');
        $btnTtg.click(function(){
        	var url = 'https://ams-games.ttms.co/casino/bet8/lobby/index.html?playerHandle={0}&account={1}&lang=zh-cn&platformUrl={2}&deviceType=mobile';
            var permission=sessionStorage.getItem('permission');

            if(permission==='1'){
                if(window.TTplayerhandle){
                    window.location.href = String.format(url,TTplayerhandle,'CNY',window.location.href);
                }else{
                    $btnTtg.find('.img_text').text('进入游戏中');
                    $.post('/mobi/loginTTG.php',function(result){
                        $btnTtg.find('.img_text').text('TTG');
                        if(result.success||result.code=='10000'){
                            window.TTplayerhandle = result.message;
                            window.location.href = String.format(url,TTplayerhandle,'CNY',window.location.href);
                        }else{
                            toast_tip(result.message);
                        }
                    }).fail(function(){
                        $btnTtg.find('.img_text').text('TTG');
                    });
                }
            }else if(permission==='2'){
                toast_tip('代理账户不能游戏');
            }else{
                //window.location.href = String.format(url,'999999','FunAcct',window.location.href);
            	toast_tip('请先登录');
            }
        });
    }

    init();

});
