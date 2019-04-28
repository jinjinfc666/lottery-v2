/**
 * Date 扩展方法
 */
Date.replaceChars = {
    shortMonths: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    longMonths: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    shortDays: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    longDays: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],

    // Day
    d: function() { return (this.getDate() < 10 ? '0' : '') + this.getDate(); },
    D: function() { return Date.replaceChars.shortDays[this.getDay()]; },
    j: function() { return this.getDate(); },
    l: function() { return Date.replaceChars.longDays[this.getDay()]; },
    N: function() { return this.getDay() + 1; },
    S: function() { return (this.getDate() % 10 == 1 && this.getDate() != 11 ? 'st' : (this.getDate() % 10 == 2 && this.getDate() != 12 ? 'nd' : (this.getDate() % 10 == 3 && this.getDate() != 13 ? 'rd' : 'th'))); },
    w: function() { return this.getDay(); },
    z: function() { var d = new Date(this.getFullYear(),0,1); return Math.ceil((this - d) / 86400000); }, // Fixed now
    // Week
    W: function() { var d = new Date(this.getFullYear(), 0, 1); return Math.ceil((((this - d) / 86400000) + d.getDay() + 1) / 7); }, // Fixed now
    // Month
    F: function() { return Date.replaceChars.longMonths[this.getMonth()]; },
    m: function() { return (this.getMonth() < 9 ? '0' : '') + (this.getMonth() + 1); },
    M: function() { return Date.replaceChars.shortMonths[this.getMonth()]; },
    n: function() { return this.getMonth() + 1; },
    t: function() { var d = new Date(); return new Date(d.getFullYear(), d.getMonth(), 0).getDate() }, // Fixed now, gets #days of date
    // Year
    L: function() { var year = this.getFullYear(); return (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)); },   // Fixed now
    o: function() { var d  = new Date(this.valueOf());  d.setDate(d.getDate() - ((this.getDay() + 6) % 7) + 3); return d.getFullYear();}, //Fixed now
    Y: function() { return this.getFullYear(); },
    y: function() { return ('' + this.getFullYear()).substr(2); },
    // Time
    a: function() { return this.getHours() < 12 ? 'am' : 'pm'; },
    A: function() { return this.getHours() < 12 ? 'AM' : 'PM'; },
    B: function() { return Math.floor((((this.getUTCHours() + 1) % 24) + this.getUTCMinutes() / 60 + this.getUTCSeconds() / 3600) * 1000 / 24); }, // Fixed now
    g: function() { return this.getHours() % 12 || 12; },
    G: function() { return this.getHours(); },
    h: function() { return ((this.getHours() % 12 || 12) < 10 ? '0' : '') + (this.getHours() % 12 || 12); },
    H: function() { return (this.getHours() < 10 ? '0' : '') + this.getHours(); },
    i: function() { return (this.getMinutes() < 10 ? '0' : '') + this.getMinutes(); },
    s: function() { return (this.getSeconds() < 10 ? '0' : '') + this.getSeconds(); },
    u: function() { var m = this.getMilliseconds(); return (m < 10 ? '00' : (m < 100 ?'0' : '')) + m; },
    // Timezone
    e: function() { return "Not Yet Supported"; },
    I: function() {
        var DST = null;
        for (var i = 0; i < 12; ++i) {
            var d = new Date(this.getFullYear(), i, 1);
            var offset = d.getTimezoneOffset();

            if (DST === null) DST = offset;
            else if (offset < DST) { DST = offset; break; }                     else if (offset > DST) break;
        }
        return (this.getTimezoneOffset() == DST) | 0;
    },
    O: function() { return (-this.getTimezoneOffset() < 0 ? '-' : '+') + (Math.abs(this.getTimezoneOffset() / 60) < 10 ? '0' : '') + (Math.abs(this.getTimezoneOffset() / 60)) + '00'; },
    P: function() { return (-this.getTimezoneOffset() < 0 ? '-' : '+') + (Math.abs(this.getTimezoneOffset() / 60) < 10 ? '0' : '') + (Math.abs(this.getTimezoneOffset() / 60)) + ':00'; }, // Fixed now
    T: function() { var m = this.getMonth(); this.setMonth(0); var result = this.toTimeString().replace(/^.+ \(?([^\)]+)\)?$/, '$1'); this.setMonth(m); return result;},
    Z: function() { return -this.getTimezoneOffset() * 60; },
    // Full Date/Time
    c: function() { return this.format("Y-m-d\\TH:i:sP"); }, // Fixed now
    r: function() { return this.toString(); },
    U: function() { return this.getTime() / 1000; }
};
Date.prototype.format = function(format) {
    var returnStr = '';
    var replace = Date.replaceChars;
    for (var i = 0; i < format.length; i++) {       var curChar = format.charAt(i);         if (i - 1 >= 0 && format.charAt(i - 1) == "\\") {
        returnStr += curChar;
    }
    else if (replace[curChar]) {
        returnStr += replace[curChar].call(this);
    } else if (curChar != "\\"){
        returnStr += curChar;
    }
    }
    return returnStr;
};
if(!String.format){
    String.format = function() {
        var theString = arguments[0];

        for (var i = 1; i < arguments.length; i++) {
            var regEx = new RegExp("\\{" + (i - 1) + "\\}", "gm");
            theString = theString.replace(regEx, arguments[i]);
        }
        return theString;
    };
};
// form表单对象化
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/**
 * loader 加载框
 */
!(function (w,$) {
    var Loader=function () {
        var that=this;
        this.html=[
            '<div class="loading j-loading">',
            '    <div class="bubblingG">',
            '        <span id="bubblingG_1">',
            '        </span>',
            '        <span id="bubblingG_2">',
            '        </span>',
            '        <span id="bubblingG_3">',
            '        </span>',
            '    </div>',
            '</div>'
        ].join('');
        this.$tpl=$(this.html);
        this.open=function () {
            var $tmp=$('.j-loading');
            if($tmp.length>0){
                $tmp.removeClass('hidden');
            }else{
                $('body').append(that.$tpl);
            }
        };
        this.close=function () {
            var $tmp=$('.j-loading');
            if($tmp.length>0){
                $tmp.addClass('hidden');
            }else{
                //$('body').append(that.$tpl);
            }
        };
    };

    w.loader=new Loader();
})(window,jQuery)

/*
 * 点触加载管理
 */
!function(){
    //点触Url
    var _touClickUrl = '//js.touclick.com/js.touclick?b=b85d9b8e-adfb-47da-a58d-8f30cef74f1a';
    window.TouClick = false;
    /**
     * 点触
     * @param {function} callback 回调函数
     */
    var openTouClick = function(callback){
        if(!TouClick){
           loader.open();
            //透过jquery 动态加载 js
            $.getScript(_touClickUrl).done(function( script, textStatus ) {
                loader.close();
                if(TouClick){
                    _startTouClick(callback);
                }else{
                    callback({success:false,message:'点触验证加载失败，请刷新页面！'});
                }
            }).fail(function( jqxhr, settings, exception ) {
                loader.close();
                callback({success:false,message:'点触验证加载失败，请刷新页面！'});
            });
        }else{
            _startTouClick(callback);
        }
    };
}();

function TouClickFunc(divname,name,phone,code){
    /*var _touClickUrl = '//js.touclick.com/js.touclick?b=b85d9b8e-adfb-47da-a58d-8f30cef74f1a';
    if(!TouClick){
       loader.open();
        //透过jquery 动态加载 js
        $.getScript(_touClickUrl).done(function( script, textStatus ) {
            loader.close();
            if(TouClick){
                openTouchClick(divname,name,phone,code);
            }else{
                toast_tip('点触验证加载失败，请刷新页面！');
            }
        }).fail(function( jqxhr, settings, exception ) {
            loader.close();
            toast_tip('点触验证加载失败，请刷新页面！');
        });
    }else{
        openTouchClick(divname,name,phone,code);
    }*/

    openTouchClick(divname,name,phone,code);
}
function openTouchClick(divname,name,phone,code){
   /* var tc = TouClick(divname,{
            onSuccess : function(obj){
                var token = obj.token;
                var check_address = obj.checkAddress;
                var sid = obj.sid;
                $.ajax({
                    url : "/getPwd/getbackPwdByDx_dc.php",
                    type : "post",
                    dataType : "json",
                    data : {"name":name, "phone":phone, "check_address":check_address, "token":token, "sid":sid},
                    success : function(result) {
                        toast_tip(result.message);
                        tc.destory();
                    }
                });
            },
        });*/

    $.ajax({
        url : "/getPwd/getbackPwdByDx_dc.php",
        type : "post",
        dataType : "json",
        data : {"name":name, "phone":phone, "check_address":"", "token":"", "sid":""},
        success : function(result) {
            toast_tip(result.message);
        }
    });

    return false;
}
// 提示
function toast_tip(msg) {
	// alert(msg);
  layer.msg(msg, function(){
  //关闭后的操作
  });
    // $('.toast_tips').addClass("hidden");
    // $(".toast_tips .toast_content").text(msg);
    // $('.toast_tips').removeClass("hidden");
    // var t=setTimeout(function(){
    //     $('.toast_tips').addClass("hidden");
    // },2000);
    // $('.toast_tips').on('click',function () {
    //     $(this).addClass("hidden");
    //     clearTimeout(t);
    // });
}

// 提示并且跳转地址
function toast_tip2(msg,url) {
  layer.msg(msg, function(){
  //关闭后的操作
  window.location = url;
  });
    // toast_tip(msg)
    // setTimeout(function(){
    //     window.location = url;
    // }, 2000);
}
//数字分割
function splitNum(str){
    if(!str){
        return 0;
    }
    if(str<1000){
        return str;
    }
    var strArr = (str+"").split("").reverse().join("").replace(/(\d{3})/g,"$1,").split("").reverse();
    strArr[0] == "," && (strArr[0] = "");
    return strArr.join("");
}

// 签到
function dosign(){
    loader.open();
    $.ajax({
        url : "/asp/doSignRecord.php",
        type : "post", // 请求方式
        dataType : "json", // 响应的数据类型
        async : true, // 异步
        success : function(result) {
            loader.close();
            toast_tip(result.message);
        },
        error:function(){
            loader.close();
        }
    });
}
//获取链接参数
function getQueryString(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
//得到事件
function getEvent(){
     if(window.event)    {return window.event;}
     func=getEvent.caller;
     while(func!=null){
         var arg0=func.arguments[0];
         if(arg0){
             if((arg0.constructor==Event || arg0.constructor ==MouseEvent
                || arg0.constructor==KeyboardEvent)
                ||(typeof(arg0)=="object" && arg0.preventDefault
                && arg0.stopPropagation)){
                 return arg0;
             }
         }
         func=func.caller;
     }
     return null;
}
//阻止冒泡
function cancelBubble()
{
    var e=getEvent();
    if(window.event){
        //e.returnValue=false;//阻止自身行为
        e.cancelBubble=true;//阻止冒泡
     }else if(e.preventDefault){
        //e.preventDefault();//阻止自身行为
        e.stopPropagation();//阻止冒泡
     }
}

$(function () {
    // 获取通告
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
    //导航栏悬浮弹出下拉
    $(".nav-box .nav a").hover(
      function(){
        $(".nav_box.active").slideUp(300);
        $("."+$(this).data("more")).slideDown(300).css("z-index","9").addClass("active");

      },function(){
        $(".nav_box").css("z-index","7");
      });
    //
    $(".nav-box").hover(function(){},function(){
      $(".nav_box").slideUp(300).css("z-index","7");
    })
    //回到顶部
    $('.j-to_top,.j-index').click(function(){
        $('html,body').animate({ scrollTop: 0 }, 1000);
    });
    //app二维码
    getDownloadCode();
    //回撥
    $(".j-callback").click(function(){
        $("#callback").show();
    })
    // 教程绑定
    $(".j-tech").click(function(){
        var $this=$(this);
        var target=$this.data('target');
        $(target).show();
    })
    var isLogin=$('#j-isLogin').val()==='true';
    $(document).on('click','.j-needlogin',function(){
        if(!isLogin){
            toast_tip("请先登录！");
            return false;
        }
    });
    //签到按钮
    $(".j-dosign").click(function(){
        dosign();
    })
    // 顶部查询余额
    $(".j-user-money").click(function(){
        var $this=$(this)
        if($this.hasClass("loadding")){
            toast_tip("正在刷新请勿重复点击！");
            return ;
        }else{
            var _target=$this.data("target");
            $(".user-money-"+_target).text("正在刷新...")
            $this.addClass("loadding");
            $.post("/asp/getGameMoney.php",{gameCode:_target.toUpperCase()},function(data){
                    if(data.code=="10000"){
                            $this.removeClass("loadding");
                            $(".user-money-"+_target).text(data.data+'元');
                        }else{
                        toast_tip(data.message);
                    }
            });
        }
    });
    // tab选项
    $(document).on('click','.j-nav_item',function(){
        var $this=$(this);
        $this.addClass("active").siblings().removeClass("active");
        var target=$this.data('target');
        $(".j-nav_tab[data-id='"+target+"']").addClass("active").siblings().removeClass("active");
    })
    $(".forget_item").click(function(){
      var $this=$(this);
      $this.addClass("active").siblings().removeClass("active");
      var target=$this.data('target');
      $(".j-nav_tab[data-id='"+target+"']").addClass("active").siblings().removeClass("active");
    })
	// 密码查看
    $('.j-changpwd').click(function () {
        ('changing')
        var el = $(this);
        var input=$(this).parent().find('.inpt-pwd');
        el.toggleClass('close');
        if(el.hasClass('close')) {
            input.attr('type', 'password');
        } else {
            input.attr('type', 'text');
        }
    });
	// 弹出注册弹窗
	$(".j-register").click(function(){
		$("#register").css("display","block");
	});
    // 弹出注册弹窗
    $(".j-forget_pwd").click(function(){
        $("#forget_pwd").css("display","block");
    })
	// 关闭注册
	$(".j-close").click(function(){
	    $(this).parents('.register').hide();
	});
  $(".register").click(function(){
    $(this).hide();
  })
  $(".register_content").click(function(){
    cancelBubble();
    // window.event? window.event.cancelBubble = true : event.stopPropagation();
    // event.stopPropagation();
  })
    $(".j-reflesh").click(function(){
        window.location.reload();
    })

	// 显示金额
	$(".j-showmoney").click(function(){
		$(".j-moneydes").slideToggle(300);
		// console.log("金额下拉")
	});
	// 获取站内信
	getLetterCount();
    techViews();
	// 当前所处的tab
	var pathname = window.location.pathname;
	var hash = window.location.hash;
    var query=getQueryString('showtype');
    $('.j-nav a').removeClass('active');
	if(pathname.indexOf( '/index.jsp')>-1 || pathname == '/'){
		$('.j-nav a[data-link="index"]').addClass('active');
	}
	else if(pathname.indexOf( '/promotion.jsp')>-1){
        $('.j-nav a[data-link="promotion"]').addClass('active');
	}
    else if(pathname.indexOf( '/agent.jsp')>-1){
        $('.j-nav a[data-link="agent"]').addClass('active');
    }else if(pathname.indexOf( '/vip.jsp')>-1){
        $('.j-nav a[data-link="vip"]').addClass('active');
    }
    // 当时哪款游戏
    if(query=='PT'){
        $('.j-nav a[data-link="PT"]').addClass('active');
    }
    if(query=='MGS'){
        $('.j-nav a[data-link="MGS"]').addClass('active');
    }
    if(query=='TTG'){
        $('.j-nav a[data-link="TTG"]').addClass('active');
    }
    if(query=='NT'){
        $('.j-nav a[data-link="NT"]').addClass('active');
    }
    if(query=='DT'){
        $('.j-nav a[data-link="DT"]').addClass('active');
    }
    if(query=='QT'){
        $('.j-nav a[data-link="QT"]').addClass('active');
    }

	var HeaderBar=function () {
        var that=this;
        this.$popbtn=$('.popbtn');
        this.$headerAction=$('#j-headerAction');
		this.$formBtn=$(".j-login");
		this.$logout=$(".j-logout")
        this.$formLogin=$('#form-login');
        this.$formRegister=$('#form-register');
        this.$formAgent=$('#form-agent');
        this.$formCallback=$("#form-callback");
        this.$formFindPwdBySms=$('#form-findPwdBySms');
        this.$formFindPwdByEmail=$('#form-findPwdByEmail');

        this.$code=this.$formLogin.find('.j-code');
        this.$regCode=this.$formRegister.find('.j-code');
        this.$agentCode=this.$formAgent.find('.j-code');
        this.url='/mobi/mobileValidateCode.php';
        /**
         * 登录功能
         */
		this.$formBtn.click(function(){
			that.$formLogin.submit();
			// console.log("formbtn")
		});
        this.login=function(){
            that.$formLogin.submit(function () {
                var formData=that.$formLogin.serializeObject();
                var login_type=that.$formLogin.data('type');
                var user_head=formData.account.slice(0,2);
                if(!formData.account){
                    toast_tip('请输入用户名！');
                    return false;
                }
                if(login_type=='money'&&user_head=='a_'){
                    toast_tip2('代理账户，请使用代理入口','/agent.jsp');
                    return false;
                }
                if(login_type=='agent'&&user_head!='a_'){
                    toast_tip2('游戏账户，请使用游戏账户入口','/index.jsp');
                    return false;
                }
                formData.isRemember=$(".j-switch").data('selected');
                var msg=that.checkLoginForm(formData);
				// alert(msg);
                if(msg){
                    toast_tip(msg);
                }else{
                    $.post("/mobi/logout.php",function(result){
                        if(result.code=="10000"){
                            $.post("/mobi/login.php",formData ,function(result){
                                that.$code.click();
                                if(result.code=="10000"){
                                    if( $('.j-switch').attr('data-selected') == 1 ){
                                        localStorage.setItem('account',formData.loginname);
                                    }
                                    sessionStorage.setItem('permission',1);
                                    sessionStorage.setItem('notice',2);
                                    window.location.reload();
                                }else{
                                    toast_tip(result.message);
                                }
                            })
                        }else{
                            toast_tip(result.message);
                        }
                    });
                }
                return false;
            });
        };

        /**
         * 注册功能
         */
        this.register=function () {
            that.$formRegister.submit(function () {
                var formData=that.$formRegister.serializeObject();
                var msg=that.checkRegisterForm(formData);
                if(msg){
                    toast_tip(msg);
                }else{
                    $.ajax({
                        type:"post",
                        url:"/mobi/register.php",
                        data:formData,
                        success:function(result){
                            if(result.code=='10000'){
                                $(".j-register_content").hide();
                                $(".j-register_success").removeClass("hidden");
                                // toast_tip2(result.message,'/index.jsp');
                            }else{
                                toast_tip(result.message);
                                that.$regCode.click();
                            }
                        }
                    });
                }
                return false;
            });
        };
        /**
         * 代理注册功能
         */
        this.agentregister=function(){
            that.$formAgent.submit(function () {
                var formData=that.$formAgent.serializeObject();
                var msg=that.checkAgent(formData);
                if(msg){
                    toast_tip(msg);
                }else{
                    loader.open();
                    $.ajax({
                        type:"post",
                        url:"/asp/addAgent.php",
                        data:formData,
                        success:function(result){
                            loader.close();
                            that.$agentCode.click();
                            toast_tip(result.message);
                        }
                    });
                }
                return false;
            });
        };
        //回撥
        this.callback=function(){
            that.$formCallback.submit(function(){
                var formData=that.$formCallback.serializeObject();
                var msg=that.checkCallback(formData);
                if(msg){
                    toast_tip(msg);
                }else{
                    loader.open();
                    $.ajax({
                        type:"post",
                        url:"/mobi/makeCall.php",
                        data:formData,
                        success:function(result){
                            loader.close();
                            toast_tip(result.message);
                        }
                    });
                }
            return false;
            })
        }
        this.init=function () {
            var $loginTab=$('#j-login .pop-tab');

            // 标签切换的时候刷新验证码
            $loginTab.find('a').click(function () {
                var target=$(this).attr('href');
                var url=$(target).find('.j-code').attr('src');
                $(target).find('.j-code').attr('src',url+'?v='+Math.random());
            });

            // 头部注册操作、登录操作or代理注册

            that.$popbtn.click(function () {
                var action=$(this).data('action');
                $loginTab.find('a[href="#'+action+'"]').click();
                $(".login-box > .tab-panel").removeClass("active");
                $('#'+action).addClass("active");
            });

            that.login();

            that.register();

            that.agentregister();

            that.findPwdBySms();

            that.findPwdByEmail();

            that.callback();
            // that.$code.click(that.changeCode);
            // that.$regCode.click(that.changeCode);
            // that.$agentCode.click(that.changeCode);
            //退出登录
            that.$logout.click(that.logout);

        };

        this.changeCode=function ($ele) {
            var url=$(this).attr('src');
            $(this).attr('src',url+'?v='+Math.random());
        };

        this.logout=function () {
            $.post("/mobi/logout.php",function(result){
                    if(result.code=="10000"){
                        window.location.reload();
                    }else{
                        toast_tip(result.message);
                    }
                }
            );
        };
        /**
         * 通过短信找回密码
         */
        this.findPwdBySms=function () {
            that.$formFindPwdBySms.submit(function () {
                var formData=that.$formFindPwdBySms.serializeObject();
                var msg=that.checkFindPasswordByPhone(formData);
                if(msg){
                    toast_tip(msg);
                }else{
                    TouClickFunc('touclick-container',formData.name,formData.phone)
                }
                return false;
            });
        };
        /**
         * 验证找回密码
         * @param formData
         * @returns {*}
         */
        this.findPwdByEmail=function(){
            that.$formFindPwdByEmail.submit(function () {
                var formData = that.$formFindPwdByEmail.serializeObject ();
                var name = $ ("#ipt_username2").val ();
                var yxdz = $ ("#ipt_mail2").val ();
                var code = $ ("#ipt_verificationcode2").val ();
                var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
                if (null === formData.name || formData.name === "") {
                    toast_tip ('请输入游戏帐号');
                    return false;
                }
                if (null === formData.yxdz || formData.yxdz === ""|| !regex.test(formData.yxdz )) {
                    toast_tip ('请输入正确的邮箱地址！');
                    return false;
                }
                if (null === formData.code || formData.code === "") {
                    toast_tip ('请输入验证码！');
                    return false;
                }
                loader.open ();
                $.ajax ({
                    url: "/getPwd/getbackPwdByEmail.php",
                    type: "post",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        loader.close ();
                        toast_tip(result.message);
                    }
                });
                return false;
            })
        };
        /**
         * 验证登录表单
         * @param formData
         * @returns {*}
         */
        this.checkLoginForm=function (formData) {
            if (!formData.account) {
                return "账号不能为空！";
            }
            if (!formData.password) {
                return "密码不能为空！";
            }
            if (!formData.imageCode ) {
                return "验证码不能为空！";
            }
            return '';
        };
        /**
         * 验证注册表单
         * @param formData
         * @returns {*}
         */
        this.checkRegisterForm=function (formData) {
            if(!formData.account){
                return "用户名不能为空";
            }
            if(formData.account.length < 6 || formData.account.length >10){
                return '登入账号的长度请介于6-10字符之间！';
            }
            if(!/^[a-zA-Z0-9]{6,10}$/.test(formData.account)){
                return '用户名由6-10个数字或英文字母组成！';
            }
            if(!formData.password){
                return "密码不能为空";
            }
            if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(formData.password)){
                return '密码需为6-16位，包含数字和字母';
            }
            if(!formData.qq){
                return "QQ号码不能为空";
            }
            if(!/^[0-9]{4,20}$/.test(formData.qq)){
                return 'QQ号码格式错误！';
            }
            if(!formData.phone){
                return "手机号码不能为空";
            }
            if(!/^1\d{10}$/.test(formData.phone)){
                return '手机号码格式错误！';
            }
            if(!formData.imageCode){
                return "验证码不能为空";
            }

            return '';
        };
        //驗證資料
        this.checkFindPasswordByPhone=function(formData){
            if(!formData.name){
                return '游戏账号不可为空！';
            }
            if(!formData.phone){
                return '手机号码不可为空！';
            }
            if(!/^1\d{10}$/.test(formData.phone)){
                return '手机号码格式错误！';
            }
            return '';
        };

        /**
         * 验证代理注册
         * @param formData
         * @returns {*}
         */
        this.checkAgent=function(formData){

            if(!formData.loginname){
                return '代理账号不可为空！';
            }
            if(!/^a_[a-z0-9]{1,13}$/.test(formData.loginname)){
                return '代理账号必须以a_开头，由3-15个数字或英文字母组成！';
            }

            if(!formData.password){
                return '密码不可为空！';
            }
            if(!/^[a-z0-9_]{5,15}$/.test(formData.password)){
                return '密码由6-16个数字或英文字母组成！';
            }
            if(!formData.confirmpassword){
                return '请再次输入密码！';
            }
            if(formData.password!=formData.confirmpassword){
                return '两次输入的密码不一致！';
            }
            if(!formData.accountName){
                return '请输入真实姓名！';
            }
            if(!/^[\u4e00-\u9fa5]+$/.test(formData.accountName.replace('·',''))){
                return '真实姓名只能为汉字！';
            }
            if(!formData.email){
                return '请输入电子邮箱！';
            }
            var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
            // console.log(formData.email.trim().toLowerCase());
            if(!regex.test(formData.email.trim().toLowerCase())){
                return "请输入正确的邮箱地址";
            }
            if(!formData.qq){
               return '请输入QQ号码！'
            }
            if(!/^[0-9]*$/.test(formData.qq)){
                return '请正确填写您的QQ号码！';
            }
            if(!formData.phone){
                return '请输入联系电话！';
            }
            if(!/^1\d{10}$/.test(formData.phone)){
                return '请正确填写您的手机号码！';
            }
            if(!formData.referWebsite){
                return '请输入推广网址！';
            }
            if(!/^[A-Za-z0-9]{2,6}$/.test(formData.referWebsite)){
                return '请正确填写您的推广网址！';
            }
            if(!formData.validateCode){
                return '请输入验证码！';
            }
            if(!formData.agentAgree){
                return '请同意用户协议！';
            }
            return '';
        };
        this.checkCallback=function(formData){
            if(!/^1\d{10}$/.test(formData.phone)){
                return '请正确填写您的手机号码！';
            }
            return '';
        }
        this.init();
    };
    new  HeaderBar();
	// $(".game_btn").click(function(){
	// 	$('.j-navigation a').removeClass('active');
	// 	$(this).addClass("active");
	// });
	// $(".ptbtn").click(function(){
	// 	$("#j-gameMenu a").eq(0).click();
	// });
	// $(".dtbtn").click(function(){
	// 	$("#j-gameMenu a").eq(1).click();
	// })
});
//获取站内信
function getLetterCount() {
	// 获取站内信
	var isLogin=$("#j-isLogin").val();
	if (isLogin !== 'false' && isLogin !== ""){
		$.ajax({
			url:"/asp/getGuestbookCountNew.php",
			cache: false,
			dataType : "json",
			type:'get',
			success:function(response){
				if(response.code=='10000') {
					if (response.data == 0) {
						$ ('.j-msg').html ('');
					}
					else {
						$ ('.j-msg').html (response.data);
					}
				}else{
					toast_tip(response.message);
				}
			},
			error:function(){
				// toast_tip("网络错误!")
			}
		});
	}
}

// 获取下载验证码
function getDownloadCode(){
    // var _origin=window.location.origin;
    var _url="https://www.staticsources.com/app/tg/app.html";
    // var _src=_origin+"/asp/generateQRCode.php?qrtext="+_url;
    $(".download_code").attr("src",'/taige/images/generateQRCode_App.png?v=20180116');
    $(".download_link").text(_url);
}
function techViews(){
    var _C_left=$(".app-left");
    var _R_right=$(".app-right");
    _C_left.click(function(){
        var _target=$(this).parent().find(".tech_img");
        var _index=_target.find(".active").index();
        if(_index>0){
            _target.find(".views").eq(_index-1).addClass("active").siblings().removeClass("active");
        }
    });
    _R_right.click(function(){
        var _target=$(this).parent().find(".tech_img");
        var _index=_target.find(".active").index();
        var _length=_target.find(".views").length-1;
        if(_index<_length){
            _target.find(".views").eq(_index+1).addClass("active").siblings().removeClass("active");
        }
    })
}
// 获取弹框公告
function getNotice(){
    var $tipsModal=$('#j_news_box'),
        $tit=$tipsModal.find('.j_news_title'),
        $content=$tipsModal.find('.j_new_show');
    $.post('/asp/checkConfigSystem.php',{typeNo:'type002', itemNo:'001'},function(result){
        if(result.code=='10000'){
            var array = result.data.split('#');
            $tit.html(array[0]);
            $content.html(array[1].replace(/\r\n/g,'<br/>').replace(/\n/g,'<br/>'));
            $tipsModal.show();
            if( sessionStorage.getItem('notice') == '2' ){
                sessionStorage.setItem('notice',3);
            }
            else{
                sessionStorage.setItem('notice',1);
            }
            $.getJSON ('/asp/getCarouselListAll.php', function (data) {
              if(data.code=='10000') {
                var str = '';
                var val=data.data;
                for (var i in val) {
                  var money = Math.floor (val[ i ].prize);
                  if (money >= 1000) {
                    money = splitNum (money);
                  }
                  str += '<a href="javascript:void(0);"  ' + 'class="cfx">';
                  // str += 	'<img  class="fl" src="taige/images/index/guide.jpg">';
                  str += 	'<div class="fl des" >'
                  str += 		'恭喜玩家<span>' + val[ i ].userName + '</span>在' + val[ i ].platform + ' 【'+val[ i ].gameName+'】 投注'+val[i].betAmt+'赢得奖池 <span>' + splitNum(val[ i ].prize)+ '元 </span>';
                  // str += 		'<p class="time">'+val[i].prizeTime+'</p>';
                  str += 	'</div>';
                  str += '</a>';
                }
                $ ('.j-new-group').html (str);
              }
            })
        }
    })
}
/* * 获取试玩连接 */
function getLinkDemo (obj) {
	//if(obj.category=='TTG-MG') return '';
	//if (obj.state == 'PLA') return '';
	var _tmp = '';
	switch (obj.platform) {
		case 'PT':
            _tmp='http://cachedownload.morningstar88.com/casinoclient.html?mode=offline&affiliates=1&language=zh-cn&game={{id}}';
			break;
		case 'QT':
			_tmp = '/gameQT.php?gameCode={{id}}&isfun=1&type={{type}}';
			_tmp = _tmp.replace (/\{\{type\}\}/g, obj.subType === 'H5' ? '0' : '1');
			break;
		case 'NT':
			_tmp = 'http://load.sdjdlc.com/nt/demo.html?game={{id}}&language=en';
			break;
		case 'TTG':
			_tmp = 'http://pff.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName={{code}}&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct';
			break;
		case 'TTG-MG':
			_tmp = 'http://pff.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName={{code}}&lang=zh-cn&playerHandle=999999&gameId={{id}}&account=FunAcct';
			break;
		case 'DT':
			_tmp = 'http://play.dreamtech8.com/playSlot.aspx?gameCode={{id}}&isfun=1&type=dt&language=zh_CN';
			break;
		case 'MGS':
			if (obj.subType == 'H5') {
				_tmp = 'https://mobile22.gameassists.co.uk/MobileWebServices_40/casino/game/launch/UFAcom/{{id}}/zh-cn?loginType=VanguardSessionToken&isPracticePlay=true&casinoId=2712&isRGI=true&authToken=&lobbyurl=/gamePt.php?showtype=MGS'
			}
			else {
				_tmp = 'http://redirector3.valueactive.eu/Casino/Default.aspx?applicationid=1024&theme=quickfire&usertype=5&sext1=demo&sext2=demo&csid=2712&serverid=2712&variant=TNG&ul=zh&gameid={{id}}';
			}
			break;
		case 'PNG':
			_tmp = '/gamePNGFlash.aspx?practice=1&gameCode={{id}}';
			break;
		default:
			break;
	}
	_tmp = _tmp.replace (/\{\{id\}\}/g, obj.gameId)
		.replace (/\{\{code\}\}/g, obj.code)
		.replace (/\{\{type\}\}/g, obj.type);
	return  _tmp;
}
/* * 获取进入游戏连接 */
function getLinkPlay(obj) {
	if($("#j-Type").val()=='false'){
		return '';
	}
	if (obj.platform == 'DT' && obj.state == 'DEM'){
		return '';
	} //判断状态是否为试玩
	var _tmp = '';
	var _onclick = '';
	switch (obj.platform) {
		case 'PT': //
			_tmp = '/loginGame.php?gameCode={{id}}';
			break;
		case 'QT': //
			_tmp = '/gameQT.php?gameCode={{id}}&isfun=0&type={{type}}';
			_tmp = _tmp.replace (/\{\{type\}\}/g, obj.subType === 'H5' ? '0' : '1');
			break;
		case 'NT':
			_tmp = 'javascript:;';
			_onclick = ' onclick="playNt(this,\'{{id}}\')" ';
			break;
		case 'TTG':
			_tmp = 'javascript:;';
			_onclick = ' onclick="playTtg(this,\'{{id}}\',\'{{code}}\')" ';
			break;
		case 'DT':
			_tmp = 'javascript:;';
			_onclick = ' onclick="playDt(this,\'{{id}}\')" ';
			break;
		case 'MGS': //
			if (obj.subType == 'H5') {
				var currentUrl = window.location.href;
                _tmp='/gameMGS4H5Desktop.php?gameCode={{id}}&lobby='+currentUrl;
			}
			else {
				_tmp = '/gameMGS.php?gameCode={{id}}';
			}
			break;
		default:
			break;
	}
	var re='href="'+format(_tmp,obj)+"' "+format(_onclick,obj);
	return 'href="'+format(_tmp,obj)+'" '+format(_onclick,obj);
}
function format (url,obj) {
		return url.replace(/\{\{id\}\}/g,obj.gameId)
			.replace(/\{\{code\}\}/g,obj.code)
			.replace(/\{\{type\}\}/g,obj.type);
	}
var GameSession={
	'NT':false,
	'DT':false,
	'TTG':false
};
function playTtg(obj, id, code) {
	var popup = window.open ('about:blank', '_blank');
	var _tmp = 'https://ams-games.ttms.co/casino/generic/game/game.html?playerHandle={{key}}&account=CNY&gameName={{code}}&gameId={{id}}&lang=zh-cn&t=' + Math.random ();
	_tmp = _tmp.replace (/\{\{id\}\}/g, id)
		.replace (/\{\{code\}\}/g, code);

	if (GameSession.TTG) {
		_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.TTG);
		popup.location = _tmp;
	}
	else {
		// var $obj = $ (obj).text ('等待中..');
		$.get ('/loginTTGInfo.php?v=' + Math.random (), function (data) {
			// $obj.text ('进入游戏');
			//data={"message":"success","data":{"TTplayerhandle":"100001418417010512240830226172084267"},"success":true};
			if (data.success) {
				GameSession.TTG = data.data.TTplayerhandle;
				_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.TTG);
				popup.location = _tmp;
			}
			else {
				toast_tip (data.message)
			}
		});
	}
}
function playNt(obj, id) {
	var popup = window.open ('about:blank', '_blank');

	var _tmp = 'http://load.sdjdlc.com/nt/?game=' + id + '&key={{key}}&language=cn';
	if (GameSession.NT) {
		_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.NT);

		popup.location = _tmp;

	}
	else {
		var $obj = $ (obj).text ('等待中..');
		$.get ('/loginNTInfo.php?v=' + Math.random (), function (data) {
			$obj.text ('进入游戏');
			if (data.success) {
				GameSession.NT = data.data.nt_session;
				_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.NT);

				popup.location = _tmp;

			}
			else {
				toast_tip (data.message)
			}
		});
	}
}
function playDt(obj, id) {
	var popup = window.open ('about:blank', '_blank');
	var _tmp = 'https://play.dtgame-dtweb.com/dtGames.aspx?slotKey={{key}}&gameCode={{id}}&isfun=0&clientType=0&closeUrl=' + window.location.href;
	_tmp = _tmp.replace (/\{\{id\}\}/g, id);
	if (GameSession.DT) {
		_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.DT);
		popup.location = _tmp;
	}
	else {
		var $obj = $ (obj).text ('等待中..');
		$.get ('/loginDTInfo.php?v=' + Math.random (), function (data) {
			$obj.text ('进入游戏');
			if (data.success) {
				GameSession.DT = data.data.slotKey;
				_tmp = _tmp.replace (/\{\{key\}\}/g, GameSession.DT);
				popup.location = _tmp;
			}
			else {
				toast_tip (data.message)
			}
		});
	}
}
