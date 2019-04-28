/*
 * ie兼容性处理,添加为数组添加includes 和 filter 方法
 * */
if (!Array.prototype.includes) {
    Array.prototype.includes = function(searchElement /*, fromIndex*/) {
        'use strict';
        if (this === null) {
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
Date.prototype.getWeekNumber = function(){
    var d = new Date(+this);
    d.setHours(0,0,0,0);
    d.setDate(d.getDate()+4-(d.getDay()||7));
    return Math.ceil((((d-new Date(d.getFullYear(),0,1))/8.64e7)+1)/7);
};
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

!(function(w){
    var SlotMg=SlotMg||{};
    var $filter=$('#j-filter'),
        $filterBtn=$filter.find('.nav'),
        $gameMenu=$('#j-gameMenu'),
        $gameContainer=$('#j-gameContainer'),
        $user=$(".username").text();
        isLogin=false,  //登录状态
        collectGames=[], //收藏游戏数据
        gameIds={},//游戏id归类；
        historyGames=[], //游戏历史记录数据
        $histroyBtn=$(".j-history");//游戏历史记录操作
        collectTmpGames=[], // 缓存的收藏游戏数据
        tpl=[
            '<div class="game_box">',
                '<div class="game_item {{class}} game-info" id="{{id}}" data-subtype="{{subType}}" data-tag="{{tag}}" data-json=\'{{json}}\'>',
                    '<img data-original="https://www.staticsources.com/images/{{categoryPic}}games/{{pic}}" class="lazy">',
                    '<p class="game_title">{{name}}</p>',
                    '<div class="game_btns">',
                        '{{linkDemo}}',
                        '{{linkPlay}}',
                    '</div>',
                    '<span {{isFavorite}} class="collect j-needlogin {{collectAction}}" data-state="0"></span>',
                '</div>',
            '</div>',
        ].join('');
    var DtConfig={
        gameUrl:$('#j-gameurl').val()||'',
        slotKey:$('#j-slotKey').val()||'',
        referWebsite:$('#j-referWebsite').val()||''
    };
    SlotMg.Reg=null;
    SlotMg.DataList=[];
    var nums = (new Date()).getWeekNumber().toString()+(new Date()).getDate().toString();
    SlotMg.DataUrl={
        PT:['https://www.staticsources.com/slot/tg/allGames.json']
        // TTG:['https://staticserverhost.com/games/slot/ttg.json?v=q140' + nums],
        // MGS:['https://staticserverhost.com/games/slot/mgs.json?v=q140' + nums],
        // QT:['https://staticserverhost.com/games/slot/qt.json?v=q140' + nums],
        // DT:['https://staticserverhost.com/games/slot/dt.json?v=q140' + nums],
        // NT:['https://staticserverhost.com/games/slot/nt.json?v=q140' + nums]
    };
    SlotMg.GameSession={
        'NT':false,
        'DT':false,
        'TTG':false
    };
    SlotMg.init=function(){
        isLogin=$('#j-isLogin').val()==='true';
        SlotMg.event();
        // 取消菜单点击事件
        SlotMg.menu();
        SlotMg.search();
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
                    if(state=='1'){//已经收藏
                        $that.attr('data-state',0).removeClass('active');

                        SlotMg.saveCollectGames(tmpObj,true);
                    }else{ //没有收藏
                        $that.attr('data-state',1).addClass('active');

                        tmpObj.isCollect=true;
                        SlotMg.saveCollectGames(tmpObj,false);
                    }
                });
                $(document).on('click','.game-info .collect[data-favorite]',function(){
                    var $that=$(this),
                        tmpObj=$that.closest('.game-info').data('json');
                    SlotMg.saveCollectGames(tmpObj,true);
                    $that.closest('.game-info').remove();
                });

                //收藏游戏列表
                $('#j-favoriteAction').on('click',function(){
                    if(isLogin===true){
                        $(this).parent().find('.game_btn').removeClass("active");
                        $(this).addClass("active");
                        if($(this).attr("data-check")=='true'){
                            //比对游戏是否已下架
                            for(var i=0;i<collectGames.length;i++){
                                if(gameIds[collectGames[i]['category']]){
                                    //正常数据
                                    if(gameIds[collectGames[i]['category']].indexOf(collectGames[i]['id'])>-1){
                                    }else{
                                        //已下架
                                        SlotMg.saveCollectGames(collectGames[i],true);
                                        collectGames.splice(i,0);
                                        i--;
                                    }
                                }else{
                                    //不正常的数据
                                    SlotMg.saveCollectGames(collectGames[i],true);
                                    collectGames.splice(i,0);
                                    i--;
                                }
                            }
                            $(this).attr("data-check",'false');
                        }
                        SlotMg.builHtml(collectGames);
                        SlotMg.lazyload();
                    }else{
                        toast_tip("请先登录!");
                    }
                });
            }

        }

        //游戏记录
        //=======

        try{
            if(localStorage.getItem($user+'hisotryGames')) {
                historyGames=JSON.parse(localStorage.getItem($user+'hisotryGames'))
            } else {
                historyGames=[];
                localStorage.setItem($user+'hisotryGames','');
            }
            $(document).on('click','.game_btns .play',function(e){
                if(isLogin===true){
                    var tmpObjStr=$(this).closest('.game-info').data('json');
                    SlotMg.saveHistory(tmpObjStr);
                }else{
                }

            });
        }catch(err){
            // console.log('游戏记录出错');
            // console.log(err)
        }

        //历史游戏列表
        $('.j-history').on('click',function(){
            if(isLogin===true){
                if(historyGames) {
                    SlotMg.builHtml(historyGames);
                    SlotMg.lazyload();
                }
            }else{
                toast_tip("请先登录!");
            }
        });
    };
    SlotMg.event=function(){
        // 过滤条件点击事件
        $filterBtn.on('click',function(e){
            var $this=$(e.currentTarget);
            SlotMg.setActiveClass($this);
            SlotMg.showGames();
            return false;
        });

        // 顶部菜单点击事件
        $gameMenu.find('a').on('click',function (e) {
            var $this=$(e.currentTarget);
            SlotMg.setActiveClass($this);
            SlotMg.showGames();
            //return false;
        });
    };
    /**
     * 根据游戏的大类动态获取游戏数据
     * @param type
     * @param callback
     */
    SlotMg.getByCategory=function(type,callback){
        var urls=SlotMg.DataUrl['PT'];
        //if(!urls) return;

        if(urls==='load'){
            callback();
            return;
        }
        var dfds=[];
        for (var i in SlotMg.DataUrl) {
            dfds.push($.getJSON(SlotMg.DataUrl[i],function(data){
                SlotMg.DataList=SlotMg.DataList.concat(data);
                //把ID保存下来
                if(data.length>0){
                    var category=data[0]['category'];
                    gameIds[category]=[]
                    for(var i=0,n=data.length;i<n;i++){
                        gameIds[category].push(data[i]['id']);
                    }
                }
            }));
        }
        $.when.apply(null,dfds)
            .done(function(){
                SlotMg.DataUrl['PT']='load';
                callback();
            })
            .fail(function(){

            });
    };
    /**
     *设置过滤信息
     */
    SlotMg.setFilter=function(){
        var $btn=$('#j-filter .nav.active,#j-gameMenu .nav.active');
        var tmpObj={
            'category':'', //老虎机平台类型
            'type':'',  //老虎机类型 :经典,电动吃角子
            'line':'', // 老虎机线性类型
            'subType':'', // 第二种类型类型
            'tag':[]
        };

        tmpObj.category=$filter;
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
                } else if(p=='line'){
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
        SlotMg.getByCategory(filter.category,function(){
            var whereArr=SlotMg.getWhere(filter);
            if(whereArr.length){
                var _funStr=' return '+whereArr.join(' && ');
                var _tmpFun = new Function("el",_funStr);  // 根据动态生成查询条件,动态生成方法
                // for test
                // console.group('动态function字符串');
                // console.log(_funStr);
                // console.groupEnd();
                // for test end
                var _d= SlotMg.DataList.filter(_tmpFun);
                SlotMg.builHtml(_d);
            }else{
                SlotMg.builHtml(SlotMg.DataList.slice(0,100));// 最多只获取100个数据
            }
            SlotMg.setCollectState();
        });
        //JSON.stringify(getPt());
    };
    /**
     * 游戏顶部菜单点击事件
     */
    SlotMg.menu=function(){
        // console.log('menu');
    };

    SlotMg.setActiveClass=function($ele){
        $ele.addClass('active').siblings().removeClass('active');
    };

    /**
     * 过滤查询信息
     */
    SlotMg.reset=function(){
        var _tmp=$filter.find('a:first-child');
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
            if(!v) return;
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

    /**
     * 设置游戏收藏状态
     */
    SlotMg.setCollectState=function(){
        if(isLogin){
            $.each(collectGames,function(index,ele){
                $('#'+ele.id).find('.collect').attr('data-state',1).addClass('active');
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
        window.localStorage.setItem($user+'hisotryGames',JSON.stringify(historyGames));
    };

    /**
     * 获取试玩连接
     * @param obj
     * @returns {string}
     */
    SlotMg.getLinkDemo=function(obj){
        //if(obj.category=='TTG-MG') return '';
        if(obj.state=='PLA') return '';
        var _tmp='';
        switch(obj.category){
            case 'PT':
                _tmp='http://cachedownload.morningstar88.com/casinoclient.html?mode=offline&affiliates=1&language=zh-cn&game={{id}}';
            break;
                break;
            case 'QT':
                _tmp='/gameQT.php?gameCode={{id}}&isfun=1&type={{type}}';
                _tmp= _tmp.replace(/\{\{type\}\}/g,obj.subType==='H5'?'0':'1');
                break;
            case 'NT':
                _tmp='http://load.sdjdlc.com/nt/demo.html?game={{id}}&language=en';
                break;
            case 'TTG':
                _tmp='http://pff.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName={{code}}&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct';
                break;
            case 'TTG-MG':
                _tmp='http://pff.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName={{code}}&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct';
                break;
            case 'DT':
                _tmp='http://play.dreamtech8.com/playSlot.aspx?gameCode={{id}}&isfun=1&type=dt&language=zh_CN';
                break;
            case 'MGS':
                if(obj.subType=='H5'){
                    _tmp='https://mobile22.gameassists.co.uk/MobileWebServices_40/casino/game/launch/UFAcom/{{id}}/zh-cn?loginType=VanguardSessionToken&isPracticePlay=true&casinoId=2712&isRGI=true&authToken=&lobbyurl=/gamePt.php?showtype=MGS'
                }else{
                    _tmp='http://redirector3.valueactive.eu/Casino/Default.aspx?applicationid=1024&theme=quickfire&usertype=5&sext1=demo&sext2=demo&csid=2712&serverid=2712&variant=TNG&ul=zh&gameid={{id}}';
                }
                break;
            case 'PNG':
                _tmp='/gamePNGFlash.aspx?practice=1&gameCode={{id}}';
                break;
            default:
                break;
        }
        _tmp= _tmp.replace(/\{\{id\}\}/g,obj.id)
            .replace(/\{\{code\}\}/g,obj.code)
            .replace(/\{\{type\}\}/g,obj.type);
        return '<a href="'+_tmp+'" target="_blank" class="btn try">免费试玩</a>';
    };

    /**
     * 获取进入游戏连接
     * @param obj
     * @returns {string}
     */
    SlotMg.getLinkPlay=function(obj){
        if($("#j-Type").val()=='false'){
            return '';
        }
        if(obj.category=='DT'&&obj.state=='DEM') return ''; //判断状态是否为试玩
        var _tmp='';
        var _onclick='';
        switch (obj.category){
            case 'PT': //
                _tmp='/loginGame.php?gameCode={{id}}';
                break;
            case 'QT': //
                _tmp='/gameQT.php?gameCode={{id}}&isfun=0&type={{type}}';
                _tmp= _tmp.replace(/\{\{type\}\}/g,obj.subType==='H5'?'0':'1');
                break;
            case 'NT':
                _tmp='javascript:;';
                _onclick=' onclick="SlotMg.playNt(this,\'{{id}}\')" ';
                break;
            case 'TTG':
                _tmp='javascript:;';
                _onclick=' onclick="SlotMg.playTtg(this,\'{{id}}\',\'{{code}}\')" ';
                break;
            case 'DT':
                _tmp='javascript:;';
                _onclick=' onclick="SlotMg.playDt(this,\'{{id}}\')" ';
                break;
            case 'MGS': //
                if(obj.subType=='H5'){
                    var currentUrl = window.location.href;
                    _tmp='/gameMGS4H5Desktop.php?gameCode={{id}}&lobby='+currentUrl;
                }else{
                    _tmp='/gameMGS.php?gameCode={{id}}&moduleid='+obj.moduleid;
                }
                break;
            default:
                break;
        }

        function format(url){
            return url.replace(/\{\{id\}\}/g,obj.id)
                .replace(/\{\{code\}\}/g,obj.code)
                .replace(/\{\{type\}\}/g,obj.type);
        }
        return '<a  href="'+format(_tmp)+'" target="_blank" '+format(_onclick)+'  class="j-needlogin btn play">立即游戏</a>';
    };

    SlotMg.playTtg=function(obj,id,code){
        var popup = window.open('about:blank', '_blank');
        var _tmp='https://ams-games.ttms.co/casino/default/game/game.html?playerHandle={{key}}&account=CNY&gameName={{code}}&gameId={{id}}&lang=zh-cn&t='+Math.random();
        _tmp=_tmp.replace(/\{\{id\}\}/g,id)
            .replace(/\{\{code\}\}/g,code);

        if(SlotMg.GameSession.TTG){
            _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.TTG);
            popup.location=_tmp;
        }else{
            var $obj=$(obj).text('等待中..');
            $.get('/loginTTGInfo.php?v='+Math.random(),function (data) {
                $obj.text('进入游戏');
                //data={"message":"success","data":{"TTplayerhandle":"100001418417010512240830226172084267"},"success":true};
                if(data.success){
                    SlotMg.GameSession.TTG=data.data.TTplayerhandle;
                    _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.TTG);
                    popup.location=_tmp;
                }else {
                    toast_tip(data.message)
                }
            });
        }
    };

    SlotMg.playNt=function(obj,id){
        var popup = window.open('about:blank', '_blank');

        var _tmp='http://load.sdjdlc.com/nt/?game='+id+'&key={{key}}&language=cn';
        if(SlotMg.GameSession.NT){
            _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.NT);

            popup.location=_tmp;

        }else{
            var $obj=$(obj).text('等待中..');
            $.get('/loginNTInfo.php?v='+Math.random(),function (data) {
                $obj.text('进入游戏');
                if(data.success){
                    SlotMg.GameSession.NT=data.data.nt_session;
                    _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.NT);
                    popup.location=_tmp;
                }else {
                    toast_tip(data.message)
                }
            });
        }
    };

    SlotMg.playDt=function(obj,id) {
        var popup = window.open('about:blank', '_blank');

        var _tmp='https://play.dtgame-dtweb.com/dtGames.aspx?slotKey={{key}}&gameCode={{id}}&isfun=0&clientType=0&closeUrl='+window.location.href;
        _tmp= _tmp.replace(/\{\{id\}\}/g,id);

        if(SlotMg.GameSession.DT){
            _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.DT);
            popup.location=_tmp;
        }else{
            var $obj=$(obj).text('等待中..');
            $.get('/loginDTInfo.php?v='+Math.random(),function (data) {
                $obj.text('进入游戏');
                if(data.success){
                    SlotMg.GameSession.DT=data.data.slotKey;
                    _tmp= _tmp.replace(/\{\{key\}\}/g,SlotMg.GameSession.DT);
                    popup.location=_tmp;
                }else {
                    toast_tip(data.message)
                }
            });
        }
    };

    SlotMg.builHtml=function(data){
        var _ret=[],animaClass='';
        // $('#j-gameNum').text(data.length);
        $.each(data,function (i, o) {
            var caPic=o.category.toLowerCase();
            if(o.category=='TTG-MG'){
                caPic='ttg';
            }
            var gamecategory=o.category;
            if(o.category=='MGS'){
                gamecategory='MG'
            }
            var line = o.line;
            if(line == undefined){
                line = '';
            }
            if (line != '') {
                line = '( ' + line + '线 )';
            }
            var gameEnter = SlotMg.getLinkPlay(o);
            if(o.tag.indexOf("HOT")>-1){
                _ret.push(tpl.replace(/\{\{pic\}\}/g,o.pic)
                    .replace(/\{\{name\}\}/g,o.name)
                    .replace(/\{\{id\}\}/g,o.id)
                    .replace(/\{\{class\}\}/g,animaClass)
                    .replace(/\{\{categoryPic\}\}/g,caPic)
                    .replace(/\{\{key\}\}/g,'')
                    .replace(/\{\{tags\}\}/g,'hot')
                    .replace(/\{\{eName\}\}/g,o.eName||'')
                    .replace(/\{\{line\}\}/g,line||'')
                    .replace(/\{\{category\}\}/g,gamecategory||'')
                    .replace(/\{\{tag\}\}/g,o.tag.join(','))
                    .replace(/\{\{json\}\}/g,JSON.stringify(o).replace(/\'/g,'’'))
                    .replace(/\{\{subType\}\}/g,o.subType)
                    .replace(/\{\{collectAction\}\}/g,o.isCollect?' active':'')
                    .replace(/\{\{isFavorite\}\}/g,o.isCollect?' data-favorite ':'')
                    // /*.replace(/\{\{like\}\}/g,SlotMg.getRandom(18859,70059))*/
                    .replace(/\{\{linkDemo\}\}/g,SlotMg.getLinkDemo(o))
                    .replace(/\{\{linkPlay\}\}/g,SlotMg.getLinkPlay(o)));
            }
            else{
                _ret.push(tpl.replace(/\{\{pic\}\}/g,o.pic)
                    .replace(/\{\{name\}\}/g,o.name)
                    .replace(/\{\{id\}\}/g,o.id)
                    .replace(/\{\{class\}\}/g,animaClass)
                    .replace(/\{\{categoryPic\}\}/g,caPic)
                    .replace(/\{\{key\}\}/g,'')
                    .replace(/\{\{tags\}\}/g,'')
                    .replace(/\{\{eName\}\}/g,o.eName||'')
                    .replace(/\{\{line\}\}/g,line||'')
                    .replace(/\{\{category\}\}/g,gamecategory||'')
                    .replace(/\{\{tag\}\}/g,o.tag.join(','))
                    .replace(/\{\{json\}\}/g,JSON.stringify(o).replace(/\'/g,'’'))
                    .replace(/\{\{subType\}\}/g,o.subType)
                    .replace(/\{\{collectAction\}\}/g,o.isCollect?' active':'')
                    .replace(/\{\{isFavorite\}\}/g,o.isCollect?' data-favorite ':'')
                    /*.replace(/\{\{like\}\}/g,SlotMg.getRandom(18859,70059))*/
                    .replace(/\{\{linkDemo\}\}/g,SlotMg.getLinkDemo(o))
                    .replace(/\{\{linkPlay\}\}/g,SlotMg.getLinkPlay(o)));
            }
        });
        $gameContainer.html(_ret);
        SlotMg.lazyload();

        if(!isLogin){
            $('.j-login').removeAttr('onclick');
        }
    };

    SlotMg.lazyload=function(){
        $('img.lazy').lazyload();
    };

    w.SlotMg=SlotMg;
})(window);
/**
 * 工具函数
 */
$(function () {
    var type=getQueryString('showtype'),
        $gameMenu=$('#j-gameMenu');
        $topMenu=$('.j-navigation');
    SlotMg.init();
    if(type){
        $gameMenu.find('a[data-tab="'+type+'"]').trigger('click');
        $topMenu.find('a').removeClass("active");
        $topMenu.find('a[data-tab="'+type+'"]').addClass("active");
    }else{
        $gameMenu.find('a:first').trigger('click');
        $topMenu.find('a').removeClass("active");
        $topMenu.find('a[data-tab="ALL"]').addClass("active");
    }
    // subType="DT-1" 类型的老虎机判断
    $(document).on('click','.game-info[data-subtype="DT-1"] .btn',function(){
        if(isIE()||!checkwebgl()){ //ie 浏览器及不支持webgl的
            $('#j-tip').modal('show');
            return false;
        }
        return true;
    });

    // 判断是否支持webgl
    function checkwebgl(){
        var cvs = document.createElement('canvas');
        var contextNames = ['webgl','experimental-webgl','moz-webgl','webkit-3d'];
        var ctx;
        if(navigator.userAgent.indexOf('MSIE') >= 0) {
            try{
                ctx = WebGLHelper.CreateGLContext(cvs, 'canvas');
            }catch(e){}
        } else{
            for(var i = 0; i < contextNames.length; i++){
                try{
                    ctx = cvs.getContext(contextNames[i]);
                    if(ctx){
                        break;
                    }
                }catch(e){

                }
            }
        }
        if(ctx){
            return true;
        }
        return false;

    }
    //ie?  判断是否是ie浏览器
    function isIE() {
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }
});
