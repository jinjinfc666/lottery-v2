var app = angular.module('authentication', []);

//控制器
app.controller('authCtrl', ["$scope", "$http", "AuthService","playgameService","$state", function ($scope, $http, AuthService,playgameService,$state,$location) {

	$("a.login_tab").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("a.login_tab").removeClass('active');
		$(this).addClass('active');
		$("div.form_field_warp").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
	$("div.main_nav_with_arrow li").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.main_nav_with_arrow li").removeClass('active');
		$(this).addClass('active');
		$("div.tab-bd div.tab-panel").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
				if(tabType == 1){
					$("#forgetCodeImg").attr("src", get_code + Math.random());
				}
			}else{
				$(this).hide();
			}
		});
	});
	
	$("div.hot_game_list div.slot_game_item").on("click",function(){
		$("div.dialog_enter_game").show();
	});
	
	$("div.dialog_enter_game span.dialog_close").on("click",function(){
		$("div.dialog_enter_game").hide();
	});
	
    $scope.showLoginModal = function () {

        $("#loginCodeImg").attr("src", get_code + Math.random());
        $("#loginModal").show();
    };

    $scope.hideLoginModal = function () {

        $("#loginModal").hide();
    };

    $scope.showRegModal = function () {

        $("#regCodeImg").attr("src", get_code + Math.random());
        $("#registModal").show();
    };

    $scope.hideRegModal = function () {

        $("#registModal").hide();
    };

    $scope.showForgetModal = function () {

        $("#forgetModal").show();
    };

    $scope.hideForgetModal = function () {

        $("#forgetModal").hide();
    };

    $scope.findByMobile = function () {

        $("#mobileLi").addClass("active");
        $("#emailLi").removeClass("active");
        $("#mobileBox").show();
        $("#emailBox").hide();
    };

    $scope.findByEmail = function () {

        $("#forgetCodeImg").attr("src", get_code + Math.random());
        $("#mobileLi").removeClass("active");
        $("#emailLi").addClass("active");
        $("#mobileBox").hide();
        $("#emailBox").show();
    };

    $scope.credentials = {
        username: 'allen0512a',
        password: 'abc123',
        captchaCode: ''
    };


    $scope.isLogin = false;
    var userInfo = sessionStorage.getItem("memberInfo");
    $scope.isLogin = userInfo != undefined;

    if ($scope.isLogin) {

        var userObj = JSON.parse(userInfo);
        $scope.username = userObj.username;
        $scope.nickName = userObj.nickname;
        if($scope.mianBalance==undefined){

            var accouts=userObj.accounts;
            for(var i=0;i<accouts.length;i++){

                if(accouts[i].type.code=='M_CA'){

                    $scope.mianBalance=accouts[i].balance;
                    $scope.mianAcctId=accouts[i].id;
                    break;
                }
            }
        }

    }

    //刷新主账户余额
    $scope.refreshMainbal=function () {

        $scope.mianBalance="正在查询";

        $http.get(getBalance, {params: {'listOfAcctIds': '' + $scope.mianAcctId + ''}}).then(function (res) {

            $scope.mianBalance = res.data.data['' + $scope.mianAcctId + ''];

        }, function () {

            $scope.mianBalance="查询失败";
        });
        
    };

    //用户登录
    $scope.login = function (credentials) {

        if (credentials.username == '') {

            showToast("用户名不能为空");

        } else if (credentials.password == '') {

            showToast("密码不能为空");

        } else if (credentials.captchaCode == '') {

            showToast("验证码不能为空");

        } else {

            AuthService.login(credentials).then(function(value){
            	$scope.isLogin = true;
                var userInfo = sessionStorage.getItem("memberInfo");
                var userObj = JSON.parse(userInfo);
                $scope.username = userObj.username;
                $scope.nickName = userObj.nickname;
                if($scope.mianBalance==undefined){

                    var accouts=userObj.accounts;
                    for(var i=0;i<accouts.length;i++){

                        if(accouts[i].type.code=='M_CA'){

                            $scope.mianBalance=accouts[i].balance;
                            $scope.mianAcctId=accouts[i].id;
                            break;
                        }
                    }
                }
            });
        }

    };

    //账户退出
    $scope.logout = function () {

        AuthService.logout();

    };

    //用户注册
    $scope.regist = function () {

        var userRe = /^[A-Za-z0-9]{6,10}$/;
        var pwdRe = /^[A-Za-z0-9]{6,16}$/;

        if ($scope.username == undefined) {

            showToast("登入账户的长度请介于6-10字符之间");

        } else if (!userRe.test($scope.username)) {

            showToast("登入账户的长度请介于6-10字符之间");

        } else if ($scope.password == undefined) {

            showToast("密码需6-16位，包含数字和字母");

        } else if (!pwdRe.test($scope.password)) {

            showToast("密码的长度介于6-16位字符之间");

        } else if ($scope.mobile == undefined) {

            showToast("手机格式不正确");

        } else if (!isMobile($scope.mobile)) {

            showToast("手机格式不正确");

        } else if ($scope.regcode == undefined) {

            showToast("请输入验证码");

        } else {

            $http({
                method: 'post',
                url: register,
                data: $.param({
                    'm.username': '' + $scope.username + ''
                    , 'm.password': '' + $scope.password + ''
                    , 'mobileNumber': '' + $scope.mobile + ''
                    , 'auth': '' + $scope.regcode + ''
                }),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {

                    sessionStorage.setItem("memberInfo", JSON.stringify(res.data.data.member));

                    showToast("注册成功");

                    window.location.href = 'index.html';

                } else {

                    $("#regCodeImg").attr("src", get_code + Math.random());
                    showToast(sx.results[res.data.code]);
                }

            }, function () {


            });

        }

    };

    //通过手机号找回密码
    $scope.findPwdByMobile = function () {

        if ($scope.musername == undefined) {

            showToast("请输入游戏账户");

        } else if ($scope.mmobile == undefined) {

            showToast("手机号码格式不正确");

        } else if (!isMobile($scope.mmobile)) {

            showToast("手机号码格式不正确");

        } else {

            $http({
                method: 'post',
                url: findPwdByMobile,
                data: $.param({
                    'username': '' + $scope.musername + ''
                    , 'mobile': '' + $scope.mmobile + ''
                }),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {


                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {


            });

        }

    };

    //通过短信找回密码
    $scope.findPwdByEmail = function () {

        if ($scope.eusername == undefined) {

            showToast("请输入游戏账户");

        } else if ($scope.eemail == undefined) {

            showToast("请输入对应的邮箱");

        } else if (!isEmail($scope.eemail)) {

            showToast("邮箱格式不正确");

        } else if ($scope.ecode == undefined) {

            showToast("请输入验证码");

        } else {

            $http({
                method: 'post',
                url: findPwdByEmail,
                data: $.param({
                    'username': '' + $scope.eusername + ''
                    , 'email': '' + $scope.eemail + ''
                    , 'auth': '' + $scope.ecode + ''
                }),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {


                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {


            });

        }

    };


    //用户签到
    $scope.checkIn = function () {

        $http.get(checkIn).then(function (res) {

            if (res.data.code == 0) {

                showToast("签到成功");

            } else {

                if (res.data.code == -1600023) {

                    $state.go("userCenter.userFinDeposit",null,{reload:true});

                    var msg = sx.results[res.data.code].replace("{money}", res.data.message);
                    showToast(msg);

                }else{

                    showToast(sx.results[res.data.code]);
                }
            }


        }, function () {

        });


    }


    //读取游戏信息
    $http.get('',{params:{clientType:'W'},cache:true}).then(function (res) {

        if(res.data.code==1){

            $scope.allGameArray=res.data.data;

            var typeArray=new Array();

            for(var i=0;i<res.data.data.length;i++){

                var type={};
                type.title=res.data.data[i].catCode;
                if(i==0){
                    type.cls='active';
                }else{
                    type.cls='';
                }

                typeArray.push(type);

            }

            $scope.typeArray=typeArray;

            $scope.setCurrentGame();

        }

    }, function () {

        console.log("读取游戏信息失败");

    });

    //切换游戏类型
    $scope.changeGameType=function (catCode) {

        for(var i=0;i<$scope.typeArray.length;i++){

            if($scope.typeArray[i].title==catCode){
                $scope.typeArray[i].cls='active';
            }else{
                $scope.typeArray[i].cls='';
            }

        }

        $scope.setCurrentGame();
        
    };

    //切换游戏
    $scope.setCurrentGame=function () {

        var curCatCode='';
        for(var i=0;i<$scope.typeArray.length;i++){

            if($scope.typeArray[i].cls=='active'){

                curCatCode=$scope.typeArray[i].title;
                break;
            }
        }

        for(var i=0;i<$scope.allGameArray.length;i++){

            if($scope.allGameArray[i].catCode==curCatCode){

                $scope.currentGame=$scope.allGameArray[i].games;
                break;

            }

        }
        
    };

    //玩游戏
    $scope.palyGame=function (id,gameId,type,code,catSeq,clientType) {

        playgameService.palyGame(id,gameId,type,code,catSeq,clientType);

    };

    //试玩
    $scope.palyTry=function (id,type,code,catSeq,clientType) {

        playgameService.playTry(id,type,code,catSeq,clientType);

    };


    $scope.mobileLogin_userPanel = function(){
    	if($scope.isLogin){
    		$("#memeber-panel").toggle();
    	}else{
    		$("#login-panel").toggle();
    	}
    	
    };
    
    $scope.hiddenHeader = function(){
    	$("header").hide();
    };
    
    $scope.showHeader = function(){
    	$("header").show();
    };
    
    $scope.goBack = function(level){
    	var url = location.href;
    	if(typeof level == 'undefined'){
    		level = -1;
    	}
    	if(url.indexOf("level") > 0){
    		level = url.substring(url.indexOf("level")).split("=")[1];
    	}
    	
    	if(level == 0){
    		$scope.showHeader();
    	}
    	history.go(-1);
    };
    
    $scope.captchaCode = get_code + Math.random();
    
    $scope.obtainCaptchaCode = function(){
    	$scope.captchaCode = get_code + Math.random();
    	$("#loginCodeImg").attr('src',$scope.captchaCode);
    }
    
    $scope.redirectAccountAction = function(){
    	if($scope.isLogin){
    		location.href="#userCenter";
    	}else{
    		location.href="#login";
    	}
    }
}]);

app.controller('mainController', ["$scope", "$http","$interval","$timeout", function ($scope, $http,$interval,$timeout) {

    $http.get(getPromList, {params: {client: '3'}, cache: true}).then(function (res) {

        var promArray = new Array();

        for (var i = 0; i < res.data.data.length; i++) {

            if (i == 0) {

                res.data.data[i].cls = "active";
                res.data.data[i].dis = "";

            } else {

                res.data.data[i].cls = "";
                res.data.data[i].dis = "none";
            }

            promArray.push(res.data.data[i]);

        }

        $scope.promList = promArray;

        $interval(function () {

            var pre=0;
            var next=1;

            for(var j=0;j<$scope.promList.length;j++){

                if($scope.promList[j].cls=='active'){
                    pre=j;
                    next=j+1;
                }

            }

            if(pre==$scope.promList.length-1){

                next=0;
            }

            $scope.promList[pre].cls = "fade-leave-active fade-leave-to";
            $scope.promList[next].cls = "active fade-enter-active fade-enter-to";

            $timeout(function () {

                $scope.promList[pre].cls = "";
                $scope.promList[pre].dis = "none";

                $scope.promList[next].cls = "active";
                $scope.promList[next].dis = "";
            },1000);


        },5000);


    }, function () {

    });


}]).service('AuthService', ["$http", "$rootScope", "$cookies", "$q", function ($http, $rootScope, $cookies, $q) {
    var authService = {};

    authService.login = function (credentials) {

        showLoading('登录中……');
        var deferred = $q.defer();
        $http({
            method: 'POST',
            headers: {'Content-type': 'application/x-www-form-urlencoded'},
            url: login,
            data: $.param({
                username: credentials.username,
                password: credentials.password,
                auth: credentials.captchaCode
            })
        }).success(function (data, status, headers, config) {

            hideLoading();
            if (data.code == 1) {
            	deferred.resolve(data.code);
                sessionStorage.setItem("memberInfo", JSON.stringify(data.data.member));

                delCookie("uuid");
                setCookie("uuid", data.data.uuid);
                
                window.location.href = "#userCenter";

            } else {
            	deferred.reject(data.code);
                $("#loginCodeImg").attr("src", get_code + Math.random());
                showToast(lg.results[data.code]);
            }

        }).error(function (data, status, headers, config) {
        	deferred.reject(0);
            hideLoading();
            $("#loginCodeImg").attr("src", get_code + Math.random());
        });
        
        return deferred.promise;
    };

    authService.logout = function () {

        $http.get(logout).then(function (res) {

            if (res.data.code == 1) {

                sessionStorage.removeItem("memberInfo");
                window.location.href = "index.html";

            } else {

                showToast(sx.results[res.data.code]);
            }

        }, function () {


        });

    };


    return authService;
}]).service('playgameService', ["$http", "commonService", function ($http, commonService) {


    //开始玩游戏
    this.palyGame = function (id, gameId, type, code, catSeq, clientType) {

        if (sessionStorage.getItem("memberInfo") == undefined) {

            window.location.href = "login.html";

        } else {

            var newTab = openWin("游戏加载中……");

            if (catSeq == 1) { //若是AE游戏

                $http.get(getAeRealUrl,{params: {gameId: gameId}}).then(function (res) {

                    if (res.data.code == 0) {

                        newTab.location.href = res.data.data.gameUrl;
                    }

                }, function () {

                    console.log("读取AE游戏配置信息失败");

                });

            }

        }
    };


    //若是试玩
    this.playTry = function (gameId, type, code, catSeq, clientType) {

        var newTab = openWin("游戏加载中……");

        if (catSeq == 1) { //若是AE游戏

            $http.get(getAeDemoUrl,{params: {gameId: gameId}}).then(function (res) {

                if (res.data.code == 0) {

                    newTab.location.href = res.data.data.demoUrl;

                }

            }, function () {

                console.log("读取游戏配置信息失败");

            });

        }
    };

    }
]).service('commonService', ["$http", function ($http) {

    //过滤账户信息
    this.pingAccount = function (data) {

        var array = new Array();

        for (var i = 0; i < data.length; i++) {

            var memAcc = data[i];

            if (memAcc.product == undefined || (memAcc.product != undefined && memAcc.product.inUse == "Y" && memAcc.product.singleWallet == "N")) {

                if (memAcc.type.code != "P_SSC") {

                    if (memAcc.type.code == "M_CA") {

                        memAcc.title = "主账户";

                    } else {

                        memAcc.title = memAcc.product.code + "老虎机";

                    }

                    array.push(memAcc);

                }

            }

        }

        return array;

    };

    //拼接账户ID
    this.pingAccoutId = function (array) {

        var acctIds = "";

        for (var i = 0; i < array.length; i++) {

            acctIds += array[i].id;
            if (i != array.length - 1) {

                acctIds += "_";

            }

        }

        return acctIds;

    };


    this.formatMoney = function (input, p1) {

        var f = parseFloat(input);
        if (isNaN(f)) {
            return "";
        }
        input = input + "";
        var b = parseFloat(input.replace(/[^\d\.-]/g, "")).toFixed(p1 + 1);
        var result = b.substring(0, b.toString().length - 1);  //这里先将a转换为float类型再保留三位小数，最后截取字符串前b.length - 1位

        return result;

    };

    this.openUrl = function (url) {

        var a = document.createElement("a");
        a.setAttribute("href", url);
        a.setAttribute("target", "_blank");
        a.setAttribute("id", "hrefId");
        // 防止反复添加
        if (!document.getElementById("hrefId")) {
            document.body.appendChild(a);
        }
        a.click();

    };

    //检测是否可以试玩
    this.checkTry = function (gameArray) {

        for (var i = 0; i < gameArray.length; i++) {

            var game = gameArray[i];

            if (game.catSeqNo == 4 && game.clientType == 'M') {

                gameArray[i]['canTry'] = false;

            } else {

                gameArray[i]['canTry'] = true;

            }

        }

        return gameArray;


    };

    //检测是否可以试玩
    this.checkIndexTry = function (gameArray) {

        for (var i = 0; i < gameArray.length; i++) {

            var gameType = gameArray[i];

            for (var j = 0; j < gameType.games.length; j++) {

                var game = gameType.games[j];

                if (game.catSeqNo == 4 && game.clientType == 'M') {

                    gameArray[i].games[j]['canTry'] = false;

                } else {

                    gameArray[i].games[j]['canTry'] = true;

                }

            }


        }

        return gameArray;


    };


}]);


