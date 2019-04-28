/*
 * ie兼容性处理,添加为数组添加includes 和 filter 方法
 * */
if (!Array.prototype.includes) {
    Array.prototype.includes = function(searchElement /*, fromIndex*/) {
        'use strict';
        if (this == null) {
            throw new TypeError('Array.prototype.includes called on null or undefined');
        }

        var O = Object(this);
        var len = parseInt(O.length, 10) || 0;
        if (len === 0) {
            return false;
        }
        var n = parseInt(arguments[1], 10) || 0;
        var k;
        if (n >= 0) {
            k = n;
        } else {
            k = len + n;
            if (k < 0) {k = 0;}
        }
        var currentElement;
        while (k < len) {
            currentElement = O[k];
            if (searchElement === currentElement ||
                (searchElement !== searchElement && currentElement !== currentElement)) { // NaN !== NaN
                return true;
            }
            k++;
        }
        return false;
    };
}


if (!Array.prototype.filter) {
    Array.prototype.filter = function(fun/*, thisArg*/) {
        'use strict';

        if (this === void 0 || this === null) {
            throw new TypeError();
        }

        var t = Object(this);
        var len = t.length >>> 0;
        if (typeof fun !== 'function') {
            throw new TypeError();
        }

        var res = [];
        var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
        for (var i = 0; i < len; i++) {
            if (i in t) {
                var val = t[i];

                // NOTE: Technically this should Object.defineProperty at
                //       the next index, as push can be affected by
                //       properties on Object.prototype and Array.prototype.
                //       But that method's new, and collisions should be
                //       rare, so use the more-compatible alternative.
                if (fun.call(thisArg, val, i, t)) {
                    res.push(val);
                }
            }
        }

        return res;
    };
}
/**˚
 * 获取第几周
 */
if (!Date.prototype.getWeekNumber) {
    Date.prototype.getWeekNumber = function(){
        var d = new Date(+this);
        d.setHours(0,0,0,0);
        d.setDate(d.getDate()+4-(d.getDay()||7));
        return Math.ceil((((d-new Date(d.getFullYear(),0,1))/8.64e7)+1)/7);
    };
}


function arrayContain(array,values){
    array=array||[];
    values=values||[];
    if(array.length<values.length) return false;
    var ret=0;
    for (var i = 0; i < values.length; i++) {
        array.includes(values[i]) && ret++;

    }
    if(values.length===ret) return true;

    return false;
}
var gameInfo;

!(function(w){
    var SlotMg=SlotMg||{};

    var $filter=$('#j-filter'),
        $filterBtn=$filter.find('a'),
        $resetBtn=$('#j-resetBtn'),
        $gameMenu=$('#j-gameMenu'),
        $toggleBtn=$('#j-toggleBtn'),
        isLogin=false,  //登录状态
        collectGames=[], //收藏游戏数据
        historyGames=[], //游戏历史记录数据
        tpl=['<div id="{{id}}"  class="slot_game_item game-info" data-json=\'{{json}}\'>',
           '     <div class="layout_image_hover_text" >',
           '         <a class="btn-open" href="javascript:;">',
            '        <span class="game_tag {{ty}}"></span>',
            // '        {{line}}',
           '             <img class="game_img lazy" {{lazyLoad}}="https://www.staticsources.com/images/phone/{{categoryPic}}games/{{pic}}" width="150" height="150">',
           '         </a>',
           '     </div>',
           '     <div class="display_flex_h game_item_operations"><a href="javascript:;" class="flex_1 link_try btn-open">{{linkDemo}}</a> <a {{isFavorite}} data-state="0" href="javascript:;" class="collect j-login link_fav{{collectAction}}"></a></div>',
           '</div>'].join('');

    var DtConfig={
        gameUrl:$('#j-gameurl').val()||'',
        slotKey:$('#j-slotKey').val()||'',
        referWebsite:$('#j-referWebsite').val()||''
    };

    // 游戏配置信息
    var GameConfig={
        baseUrl:$('#j-baseUrl').val(),
        ptToken:$('#j-ptToken').val(),
        ntToken:$('#j-ntToken').val(),
        dtToken:$('#j-dtToken').val(),
        dtReferWebSite:$('#-dtReferWebSite').val(),
        userName:$('#j-username').val(),
        isLogin:$('#j-isLogin').val()
    };
    var version = (new Date()).getWeekNumber().toString()+(new Date()).getDate().toString();
    SlotMg.Reg=null;
    SlotMg.GameContainer=$('#j-gameContainer');
    SlotMg.IsLazyLoad=true;
    SlotMg.DataList=[];
    SlotMg.DataUrl={
        PT:'https://www.staticsources.com/slot/tg/allPhoneGames.json'
        // MGS:'//staticserverhost.com/games/slot/phone/mgsPhone.json?v=q13'+version,
        // QT:'//staticserverhost.com/games/slot/phone/qtPhone.json?v=q13'+version,
        // DT:'//staticserverhost.com/games/slot/phone/dtPhone.json?v=q13'+version,
        // NT:'//staticserverhost.com/games/slot/phone/ntPhone.json?v=q13'+version,
        // TTG:['//staticserverhost.com/games/slot/phone/ttgPhone.json?v=q13'+version
      // ]
    };
    SlotMg.init=function(){
        isLogin=$('#j-isLogin').val()==='true';

        SlotMg.event();
        //SlotMg.menu();
        SlotMg.search();

        $(document).on('click','.j-login',function(){
            if(!isLogin){
                toast_tip('请先登录账户！！');
                return false;
            }
        });
        // 获取收藏游戏
        if(isLogin){
            var def=SlotMg.queryCollectGames();

            def.done(function(result){
                if(result.code=='10000'){
                    collectGames=JSON.parse(result.data.gameList);
                }else{
                    collectGames=[];
                }
                favoriteInit(true);

            }).fail(function(){
                toast_tip('获取数据失败');
            });

            function favoriteInit(){
                //收藏游戏点击事件
                $(document).on('click','.game-info .collect',function(){
                    var $that=$(this),
                        state=$that.attr('data-state'),
                        tmpObj=$that.closest('.game-info').data('json');

                    if(state=='0'){ //没有收藏
                        $that.attr('data-state',1).addClass('faved');
                        tmpObj.isCollect=true;
                        SlotMg.saveCollectGames(tmpObj,false);
                        //console.table(collectGames);
                    }else if(state=='1'){//已经收藏
                        $that.attr('data-state',0).removeClass('faved');
                        SlotMg.saveCollectGames(tmpObj,true);
                        //console.table(collectGames);
                    }
                });
                $(document).on('click','.game-info .collect[data-favorite]',function(){
                    var $that=$(this),
                        tmpObj=$that.closest('.game-info').data('json');
                    SlotMg.saveCollectGames(tmpObj,true);
                    //console.table(collectGames);
                    $that.closest('.game-info').remove();
                });

                //收藏游戏列表
                $('#j-favoriteAction').on('click',function(){
                    if(isLogin===true){
                        if(collectGames.length>0){
                            SlotMg.builHtml(collectGames);
                            SlotMg.lazyload();
                        }else{
                        	var rel="<div class='section_find'><div class='section_icon'><img src='/mobile/images/gameicon_03.png'><p>暂时还没有收藏游戏</p></div></div><div class='possible'>您可能会喜欢</div>";
                			$(".slot_game_result").html(rel);
                			SlotMg.GameContainer.html(recommendGames(SlotMg.DataList));
                	        SlotMg.lazyload();
                        }
                    }
                });
            }

        }

        //游戏记录
        //=======
        try{
            if(localStorage.getItem('hisotryGames')) {
                historyGames=JSON.parse(localStorage.getItem('hisotryGames'))
            } else {
                historyGames=[];
                localStorage.setItem('hisotryGames','');
            }
            $(document).on('click','.game-info .btn',function(e){
                var tmpObjStr=$(this).closest('.game-info').data('json');
                SlotMg.saveHistory(tmpObjStr);
            });
        }catch(err){
            console.log('游戏记录出错');
        }
        //游戏历史记录列表
        $('#j-historyAction').on('click',function(){
            SlotMg.builHtml(historyGames);
            SlotMg.lazyload();
        });

        //试玩事件处理
        $(document).on('click','.j-dialog_main2 .btn_try',function(){
            var $that=$(this),
            obj=gameInfo;
            SlotMg.linkAction2(obj);
        });

        //正式游戏
        $(document).on('click','.j-dialog_main2 .btn-play',function(){
            if(isLogin){
                var $that=$(this),
                    obj=gameInfo;
                SlotMg.linkAction(obj,0);
            }
            else{
                toast_tip('请先登录账户！！');
            }
        });

        //打开窗口
        $(document).on('click','.game-info .btn-open',function(){
            var $that=$(this),obj=$that.closest('.game-info').data('json');

            $('.dialog_enter_game').show();
            $('.j-dialog_main2 .title_wrap').text(obj.name);

            if(obj.category == 'PT' || obj.category == 'AG'){
                $('.j-dialog_main2 .btn_try').addClass('hidden');
            }
            else{
                $('.j-dialog_main2 .btn_try').removeClass('hidden');
            }
            gameInfo = obj;
        });
    };

    SlotMg.event=function(){
        $filterBtn.on('click',function(e){
            e.preventDefault();
            var $this=$(e.currentTarget),
                type=$this.data('toggle');

            SlotMg.setActiveClass($this);
            if($this.is('[data-value]')){

                /* if(type==='game-tab'){
                 SlotMg.reset();
                 var href=$this.attr('href');

                 !$(href).hasClass('active') && SlotMg.setActiveClass($(href));
                 }*/


                SlotMg.showGames();
            }
            return false;

        });

        $toggleBtn.on('click',SlotMg.toggleShow);
        $resetBtn.on('click',SlotMg.reset);

    };


    /**
     * 根据游戏的大类动态获取游戏数据
     * @param type
     * @param callback
     */
    SlotMg.getByCategory=function(type,callback){
        var urls=SlotMg.DataUrl['PT'];

        if(urls==='load'){
            callback();
            return;
        }
        var dfds=[];

        for (var i in SlotMg.DataUrl) {
            dfds.push($.getJSON(SlotMg.DataUrl[i],function(data){
                SlotMg.DataList=SlotMg.DataList.concat(data);
            }));

        }
        $.when.apply(null,dfds)
            .done(function(){
                //console.log('done')
                SlotMg.DataUrl['PT']='load';
                callback();
            })
            .fail(function(){
                console.log('游戏加载失败');
            });
    };

    /**
     *设置过滤信息
     */
    SlotMg.setFilter=function(){
        var $btn=$filter.find('.filter_dropdown_content_sec .filter_item.active a');
        var tmpObj={
            'category':'', //老虎机平台类型
            'type':'',  //老虎机类型 :经典,电动吃角子
            'line':'', // 老虎机线性类型
            'subType':'', // 第二种类型类型
            'tag':[]
        };
        //tmpObj.category=$filter;
        var tmp={'tag':[]};
        $.each($btn, function(index,obj){
            var dataValue=$(obj).data('value');
            if(dataValue){
                var tagvalue=dataValue['tag'];
                if(tagvalue){
                    !tmp.tag.includes(tagvalue)&&tmp.tag.push(tagvalue);
                }else{
                    tmp=$.extend(tmp,dataValue);
                }
            }
        });

        var ret=$.extend(tmpObj,tmp);

        builReg(ret.tag);

        function builReg(filterArr){
            if(filterArr==0) return;
            var retStr='';
            for (var i = 0; i < filterArr.length; i++) {
                retStr += '(?=.*,'+filterArr[i]+')';
            }
            //retStr=retStr.replace(/\|+$/, '');
            SlotMg.Reg=new RegExp('^'+retStr+'.*$');

        }
       /* console.group('filter信息');
        console.log(ret);
        console.log(SlotMg.Reg);
        console.groupEnd();*/

        return ret;
    };
    /**
     * 获取查询条件返回数组的形式
     */
    SlotMg.getWhere=function(arr){
        var ret=[];
        for(var p in arr){
            if(arr[p]){
                if(p=='tag'){
                    arr[p].length>0
                    && ret.push('SlotMg.Reg.test(","+el.'+p+'.join(","))');
                    //ret.push('el.'+p+'.includes("'+FilterObj[p]+'")');
                }else if(p=='line'){
                    ret.push('SlotMg.LineCp("'+arr['line']+'",el.'+p+')');
                }else{
                    ret.push('el.'+p+'=="'+arr[p]+'"');
                }
            }
        }
        return ret;
    };
    /**
     * 判断是否在集合范围
     * @param rang
     * @param val
     * @returns {boolean}
     * @constructor
     */
    SlotMg.LineCp=function(rang,val){
        val=parseInt(val);
        var r=rang.split('-'),
            start=parseInt(r[0]),
            end=r[1]||'';

        if(end){
            if(start<=val && val<=end){
                return true;
            }
        }else {
            if(start<=val){
                return true;
            }
        }

        return false;
    };

    /**
     * 多条件查找游戏获取游戏
     */
    SlotMg.showGames=function(){

        var filter=SlotMg.setFilter();

        //console.log(filter);

        SlotMg.getByCategory(filter.category,function(){
            var whereArr=SlotMg.getWhere(filter);
            if(whereArr.length){
                var _funStr=' return '+whereArr.join(' && ');
                var _tmpFun = new Function("el",_funStr);  // 根据动态生成查询条件,动态生成方法
               /* console.group('动态function字符串');
                console.log(_funStr);
                console.groupEnd();*/
                var _d= SlotMg.DataList.filter(_tmpFun);

                SlotMg.builHtml(_d);
            }else{
                //SlotMg.builHtml(SlotMg.DataList.slice(0,100));// 最多只获取100个数据
                SlotMg.builHtml(SlotMg.DataList);// 最多只获取100个数据
            }

            SlotMg.setCollectState();
        });

    };
	//随机四个
	SlotMg.getHotGames=function(){
		//SlotMg.DataList
		var ret=[];
		//SlotMg.DataList
		return ret;
	};

    SlotMg.setActiveClass=function($ele){
        var $target=$ele;
        if($target.parent().hasClass('filter_item')){
            $target=$ele.parent();
        }
        $target.addClass('active').siblings().removeClass('active');
    };

    /**
     * 过滤查询信息
     */
    SlotMg.reset=function(){
        var _tmp=$filter.find('.search-row a:first-child');
        SlotMg.setActiveClass(_tmp);
    };
    SlotMg.getStart=function(){

    };
    /**
     * 获取随机数
     * @param min 开始的数
     * @param max 结束的数
     * @param int 小数点位数
     * @returns {string}
     */
    SlotMg.getRandom=function(min, max,int) {
        var ret= Math.random() * (max - min) + min;
        int=int||0;

        return ret.toFixed(int);
    };
    SlotMg.toggleShow=function(){
        $filter.slideToggle();
    };
    /**
     * 查找输入框
     */
    SlotMg.search=function(){
        var $searchForm=$('#j-searchForm'), // 查找表单
            $searchIpt=$searchForm.find('.j-ipt'), // 查找输入框
            $searchSelect=$searchForm.find('.j-select'), // 查找结果显示在下拉菜单
            $searchBtn=$searchForm.find('.j-btnSearch'), //查找按钮
            $selectAction=$searchSelect.find('a'),//下拉菜单的item
            searchList=[];

        function get(v){
            if(!v) return ;

            searchList= SlotMg.DataList.filter(function(el){
                return el.name.indexOf(v)!=-1
                    ||el.eName.toLowerCase().indexOf(v)!=-1;
            });

        }
        function buildSelect(){
            if(searchList==0){
                $searchSelect.html('').slideUp();
                return;
            }
            var _ret=[];
            $.each(searchList,function(i,o){
                _ret.push('<a href="javascript:;" data-id="'+o.id+'">'+o.name+'</a>');
                if(i==9) return false;
            });
            $searchSelect.html(_ret.join('')).slideDown();
        }

        $searchIpt.on('keyup',function () {
            searchList=[];
            var v=$searchIpt.val();
            if(v){
                get($searchIpt.val());
                buildSelect();
            }

        });
        $searchForm.on('click','.j-select a',function(e){
            var _id=$(e.currentTarget).data('id');
            $searchIpt.val($(e.currentTarget).text());

            $searchSelect.slideUp();

            searchList= SlotMg.DataList.filter(function(el){
                return el.id==_id;
            });
            SlotMg.builHtml(searchList);
            SlotMg.setCollectState();
        });

        $searchBtn.on('click',function(){
            $searchIpt.val();
            get($searchIpt.val());
            SlotMg.builHtml(searchList);
            SlotMg.setCollectState();
        });

        $searchIpt.on('focusout',function(){
            searchList=[];
            $searchSelect.slideUp();
        });
    };
    /**
     * 获取收藏游戏
     */
    SlotMg.queryCollectGames=function(){
    	return $.getJSON('/mobi/queryGameStatus.php');
    };

    /**
     * 保存收藏游戏
     */
    SlotMg.saveCollectGames=function(obj,isDel){
        if(!obj) return;
        var tmpIndex=-1;
        if(collectGames){
            $.each(collectGames, function(index, item) {
                if(item.id === obj.id) {
                    tmpIndex=index;
                    return false;
                }
            });
            if(tmpIndex!==-1&&!isDel){ //添加模式，找不到才进行添加操作
                return;
            }
        }
        if(isDel){ //删除操作
            collectGames.splice(tmpIndex,1);
        }else{
            collectGames.unshift(obj);
            collectGames.length>20 && collectGames.slice(0,19);
        }

        $.post('/mobi/saveOrUpdateGameStatus.php',{'gameList':JSON.stringify(collectGames)},function(data){
        })
    };

    SlotMg.setCollectState=function(){
        if(isLogin){
            $.each(collectGames,function(index,ele){
                $('#'+ele.id).find('.collect').attr('data-state',1).addClass('faved');
                    //.html('<i class="iconfont icon-heart2"></i>已收藏');
            });
        }

    };

    SlotMg.saveHistory=function(obj){
        if(!obj) return;
        var tmp;
        if(historyGames){
            tmp= historyGames.filter(function(item){
                return item.id===obj.id;
            });
            if(tmp.length>0){
                return;
            }
        }
        historyGames.unshift(obj);
        if(historyGames.length>20){
            historyGames=historyGames.slice(0,19);
        }
        window.localStorage.setItem('hisotryGames',JSON.stringify(historyGames));
    };
    // 获取真钱链接
    SlotMg.linkAction=function(obj,isFun){
        switch (obj.category){
            case 'PT':
                window.location.href='/mobi/ptH5Login.php?gameCode='+obj.id;
                break;
            case 'MGS':
            	var currentUrl = window.location.href;
                $.post('/mobi/gameH5MGS.php',{gameCode:obj.id,isfun:isFun,lobby:currentUrl},function(result){
                    if(result.success){
                        window.location.href = result.data.url;
                    }else{
                        toast_tip(result.message);
                    }
                });
                break;
            case 'DT':
                var dtUrl='https://play.dtgame-dtweb.com/dtGames.aspx?slotKey={0}&language=zh-cn&gameCode={1}&isfun=0&closeUrl={2}';
                if(GameConfig.dtToken){
                    window.location.href = String.format(dtUrl,GameConfig.dtToken,obj.id,GameConfig.dtReferWebSite);
                }else{
                    $.post('/mobi/loginDT.php',function(result){
                        if(result.success){
                            window.location.href = String.format(dtUrl,result.data.key,obj.id,result.data.referWebsite);
                        }else{
                            toast_tip(result.message);
                        }
                    })
                }
                break;
            case 'QT':
            	SlotMg.load_qtgame(obj.id, isFun, 'qtGames', window.location.href);
                break;
            case 'NT':
                var ntUrl='http://load.sdjdlc.com/nt/?game={0}&language=cn&lobbyUrl={1}&key={2}';
                if(GameConfig.ntToken){
                    window.location.href=String.format(ntUrl,obj.id,window.location.href,GameConfig.ntToken);
                }else{
                    $.post('/mobi/getNTGame.php',function(result){
                        if(result.success){
                            window.location.href = String.format(ntUrl,obj.id,window.location.href,result.message);
                        }else{
                            toast_tip(result.message);
                        }
                    });
                }
                break;
            case 'AG':
                $.post('/mobi/mobileGameAgFish.php',function(result){
                    if(result.success){
                        window.location.href =result.data;
                    }else{
                        toast_tip(result.message);
                    }
                });
            case 'TTG':
                var popup = window.open('about:blank', '_blank');
                var _tmp='https://ams-games.ttms.co/casino/default/game/game.html?playerHandle={{key}}&account=CNY&gameName={{code}}&gameId={{id}}&deviceType=mobile&lang=zh-cn&t='+Math.random();
                _tmp=_tmp.replace(/\{\{id\}\}/g,obj.id)
                    .replace(/\{\{code\}\}/g,obj.code);
                    var $obj=$(obj).text('等待中..');
                    $.get('/loginTTGInfo.php?v='+Math.random(),function (data) {
                        $obj.text('进入游戏');
                        if(data.code=='10000'){
                            console.log(data);
                            data=data.data;
                            _tmp= _tmp.replace(/\{\{key\}\}/g,data.TTplayerhandle);
                            popup.location=_tmp;
                        }else {
                            toast_tip(data.message)
                        }
                    });
                break;
            default:
                break;
        }

    };
    // 获取试玩链接
    SlotMg.linkAction2=function(obj){
        //pt 没有试玩游戏
        if(obj.category=='PT') return '<a href="javascript:;" class="flex_1 link_try btn-play">' + obj.name + '</a>';
        if(obj.state=='PLA') return '';
        var _tmp='';
        switch(obj.category){
            case 'PT':
                _tmp='http://cachedownload.morningstar88.com/casinoclient.html?mode=offline&affiliates=1&language=zh-cn&game={{id}}';
            break;
                break;
            case 'QT':
                _tmp="javascript:SlotMg.load_qtgame('" + obj.id + "', 1, 'qtGames', '" + window.location.href + "')";
               //_tmp='/mobi/getQTGame.php?gameCode=' + obj.id + '&isfun=1&gameType=qtGames&origin=' + window.location.href;
                break;
            case 'NT':
                if (obj.id && obj.id == 'wolfcub_mobile_html') {
                     _tmp = 'https://cecom-static.casinomodule.com/games/wolfcub-client/game/wolfcub-client.xhtml?gameName=wolf-cub.desktop&staticServer=https%3A%2F%2Fcecom-static.casinomodule.com%2F&targetElement=gameContainer&casinoId=mrsmithcasino&allowFullScreen=false&mobileParams.lobbyURL=https%3A%2F%2Fbaidu.com&gameId={{id}}_sw&server=https%3A%2F%2Fcecom-game.casinomodule.com%2F&lang=cn&sessId=DEMO-USD&operatorId=unbranded';
                 } else if (obj.id && obj.id == 'bloodsuckers2_mobile_html') {
                     _tmp = 'https://cecom-static.casinomodule.com/games/bloodsuckers2_mobile_html/game/bloodsuckers2_mobile_html.xhtml?gameName=blood-suckers-2.desktop&staticServer=https%3A%2F%2Fcecom-static.casinomodule.com%2F&targetElement=gameContainer&casinoId=mrsmithcasino&allowFullScreen=false&mobileParams.lobbyURL=https%3A%2F%2Fm.richslots.it&gameId={{id}}_sw&server=https%3A%2F%2Fcecom-game.casinomodule.com%2F&lang=cn&sessId=DEMO-2564448594028&operatorId=touch';
                 } else {
                     _tmp = 'https://netent-static.casinomodule.com/games/{{id}}/game/{{id}}.xhtml?lobbyURL=https://www.netent.com/en/section/entertain/&server=https://netent-game.casinomodule.com/&sessId=DEMO1499130519111-1903-EUR&operatorId=default&gameId={{id}}&lang=en&integration=standard&keepAliveURL=&targetElement=game&flashParams.bgcolor=000000&gameName={{id}}&staticServer=https://netent-static.casinomodule.com/';
                 }
                 break;
            case 'DT':
                _tmp='http://play.dreamtech8.com/playSlot.aspx?gameCode={{id}}&isfun=1&type=dt&closeUrl='+window.location.href;
                break;
            case 'MGS':
                _tmp="https://mobile22.gameassists.co.uk/MobileWebServices_40/casino/game/launch/LILAI/{{id}}/zh-cn?loginType=VanguardSessionToken&isPracticePlay=true&casinoId=2712&isRGI=true&authToken=&lobbyurl=" + window.location.host + "/mobile/mgs.jsp"
                break;
            case  'TTG':
                if (obj.code != "EGIGame") {
                    _tmp = 'http://pff.ttms.co/casino/default/game/casino5.html?gameName={{code}}&gameType=0&deviceType=mobile&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct&lobbyUrl={{baseUrl}}';
                } else {
                    _tmp = 'http://pff.ttms.co/casino/default/game/casino5.html?gameName=EGIGame&gameType=0&deviceType=mobile&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct&lobbyUrl={{baseUrl}}';
                }
                break;
            default:
                break;
        }

        _tmp= _tmp.replace(/\{\{id\}\}/g,obj.id)
            .replace(/\{\{code\}\}/g,obj.code)
            .replace(/\{\{type\}\}/g,obj.type);

        window.location.href=_tmp;

        //return '<a href="'+_tmp+'" class="flex_1 link_try btn-try">免费试玩</a>';
    };
    SlotMg.load_qtgame=function(gameCode, isfun, gameType, origin){
    	$.post('/mobi/getQTGame.php',{gameCode:gameCode, isfun:isfun, gameType:gameType, origin:origin},
            function(result){
                if(result.success){
                    window.location.href = result.data.url;
                }else{
                    toast_tip(result.message);
                }
        	})
    };
    /**
     * 随机推荐四款热门游戏
     */
    recommendGames=function(data){
    	var rgArray  = data.filter(function(el){
    		return (el.pic !="") && (el.tag.indexOf("HOT") != -1);
    	});
      //    	console.table(rgArray);
    	var rdmArray = [],gList = [];
    	for(var i=0; i<4; i++) {
    		var rdm = 0;
    		do {
    			var exist = false;
    			rdm = Math.floor( Math.random() * (rgArray.length - 1) );
    			if(rdmArray.indexOf(rdm) != -1) exist = true;
    		}while (exist);
    		gList.push(rgArray[rdm]);
    	}
    	var _ret=[],animaClass='';

        $.each(gList,function (i, o) {
            var caPic=o.category.toLowerCase();
            if(o.state!==0) {
                var ty = '';
                for( var i in o.tag ){
                    if(o.tag[i] == 'HOT'){
                        ty = 'game_tag_hot';
                    }
                }

                var line = '';

                if (o.line != undefined && o.line != '') {
                    line = '<span class="game_line">' + o.line + '线</span>';
                }
                _ret.push(tpl.replace(/\{\{pic\}\}/g,o.pic)
                    //.replace(/\{\{name\}\}/g,o.name)
                    .replace(/\{\{ty\}\}/g,ty)
                    .replace(/\{\{line\}\}/g,line)
                    .replace(/\{\{id\}\}/g,o.id)
                    .replace(/\{\{class\}\}/g,animaClass)
                    .replace(/\{\{categoryPic\}\}/g,caPic)
                    .replace(/\{\{key\}\}/g,'')
                    .replace(/\{\{eName\}\}/g,o.eName||'')
                    .replace(/\{\{json\}\}/g,JSON.stringify(o))
                    .replace(/\{\{subType\}\}/g,o.subType)
                    .replace(/\{\{collectAction\}\}/g,o.isCollect?' faved':'')
                    .replace(/\{\{isFavorite\}\}/g,o.isCollect?' data-favorite ':'')
                    .replace(/\{\{lazyLoad\}\}/g,SlotMg.IsLazyLoad?'data-original':'src')
                    .replace(/\{\{linkDemo\}\}/g,o.name));
            }
        })
		return _ret;
	};

    SlotMg.builHtml=function(data){
    	$(".slot_game_result").html('');
    	if(data&&data.length==0){
    		var rel="<div class='section_find'><div class='section_icon'><img src='/mobile/images/gameicon_03.png'><p>很抱歉，未找到相关的游戏</p></div></div><div class='possible'>您可能会喜欢</div>";
			$(".slot_game_result").html(rel);
			SlotMg.GameContainer.html(recommendGames(SlotMg.DataList));
	        SlotMg.lazyload();
    	}

    	if(data&&data.length>0){
	        var _ret=[],animaClass='';
	        $.each(data,function (i, o) {
                var ty = '';
                for( var i in o.tag ){
                    if(o.tag[i] == 'HOT'){
                        ty = 'game_tag_hot';
                    }
                }

                var line = '';

                if (o.line != undefined && o.line != '') {
                    line = '<span class="game_line">' + o.line + '线</span>';
                }
	            var caPic=o.category.toLowerCase();
	            if(o.state!==0) {
	                _ret.push(tpl.replace(/\{\{pic\}\}/g,o.pic)
	                    //.replace(/\{\{name\}\}/g,o.name)
                        .replace(/\{\{ty\}\}/g,ty)
                        .replace(/\{\{line\}\}/g,line)
	                    .replace(/\{\{id\}\}/g,o.id)
	                    .replace(/\{\{class\}\}/g,animaClass)
	                    .replace(/\{\{categoryPic\}\}/g,caPic)
	                    .replace(/\{\{key\}\}/g,'')
	                    .replace(/\{\{eName\}\}/g,o.eName||'')
	                    .replace(/\{\{json\}\}/g,JSON.stringify(o))
	                    .replace(/\{\{subType\}\}/g,o.subType)
	                    .replace(/\{\{collectAction\}\}/g,o.isCollect?' faved':'')
	                    .replace(/\{\{isFavorite\}\}/g,o.isCollect?' data-favorite ':'')
	                    .replace(/\{\{lazyLoad\}\}/g,SlotMg.IsLazyLoad?'data-original':'src')
	                    /*.replace(/\{\{like\}\}/g,SlotMg.getRandom(18859,70059))*/
	                    .replace(/\{\{linkDemo\}\}/g,o.name));
	            }

	        });
	        SlotMg.GameContainer.html(_ret);
	        SlotMg.lazyload();
    	}
    };

    SlotMg.lazyload=function(){
        if(SlotMg.IsLazyLoad){
            $('img.lazy').lazyload();
        }
    };
    w.SlotMg=SlotMg;
})(window);

/**
 * 工具函数
 */
!function(window,$){
    var Util=window.Util||{};
    /**
     * 获取url参数值
     * @param name
     * @param url
     * @returns {*}
     */
    Util.getQueryString=function (name, url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    root = typeof exports !== "undefined" && exports !== null ? exports : window;

    root.Util = Util;
}(window);

$(function () {
    var type=Util.getQueryString('showtype'),
        $gameMenu=$('#j-filter');
    SlotMg.init();
    if(type){
        if(type==='AMA'){
            $gameMenu.find('a[data-value=\'{"tag":"AMA"}\']').trigger('click');
        }else{
            $gameMenu.find('a[data-tab="'+type+'"]').trigger('click');
        }
    }else{
        $gameMenu.find('a[data-tab="ALL"]').trigger('click');
    }


    // 收缩展示
    var $filterDropdown=$('#filter_dropdown_trigger'),
        $filterContent=$('.filter_dropdown_content'),
        $btnSlidup=$('#j-slideup');

    function toggleShowContent(){
        $filterDropdown.toggleClass('toggled');
        if ($filterDropdown.hasClass('toggled')) {
            $filterContent.slideDown();
        } else {
            $filterContent.slideUp();
        }
        return false;
    }

    $filterDropdown.click(toggleShowContent);
    $btnSlidup.click(toggleShowContent);

});
