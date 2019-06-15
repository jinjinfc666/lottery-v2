var app = angular.module('authentication', []);

//控制器
app.controller('authCtrl', 
		["$scope", "$http", "authService", "$state", "walletService" , "userInfoServ",
			function ($scope, $http, authService,$state, walletService, userInfoServ) {
	
	
	$scope.credentials = {
			username:'',
			password:'',
			captchaCode:''
	};
	
	$scope.queryCaptchaCode = function(){
		$scope.queryMemberInfo();
		
		authService.querySessionId().then(function(sessionId){
			sessionStorage.setItem("sessionId", sessionId);
			authService.queryCaptchaCode(sessionId).then(function(res){
				$scope.captchaCode = "data:image/png;base64,"+res;
			});
		});
		
		
		$scope.$on('user.logout', function(handle, data){
    		$scope.logout();
        });
		
	};
	
	$scope.queryMemberInfo = function(){
		var userInfo = sessionStorage.getItem("userInfo");
		if(typeof userInfo != 'undefined'
			&& userInfo != null){
			$scope.isLogin = true;
			
			$scope.userInfo = JSON.parse(userInfo);
			
			userInfoServ.queryUserAcc($scope.userInfo.id).then(function(userAcc){
				var mainAcc = null;
				var redPacketAcc = null;
				
				for(var i = 0; i < userAcc.length; i++){
					var val = userAcc[i];
					if(val.accType == 1){
						mainAcc = val;
					}else{
						redPacketAcc = val;
					}
				}
				
				if(mainAcc == null){
					return ;
				}
				
				mainAcc.total = mainAcc.balance + mainAcc.freeze;
				sessionStorage.setItem("mainAcc", JSON.stringify(mainAcc));
				sessionStorage.setItem("redPacketAcc", JSON.stringify(redPacketAcc));
				
				$scope.mainAcc = mainAcc;
				$scope.redPacketAcc = redPacketAcc;
			});
		}
	};
	
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

    $scope.showRegSuccessModal = function () {

        $("#registerSuccess").show();        
    };

    $scope.hideRegSuccessModal = function () {

        $("#registerSuccess").hide();
        $scope.hideRegModal();
        location.href="index.html";
        
    };
    
    $scope.showForgetModal = function () {

        $("#forgetModal").show();
    };
    
    $scope.showAndroidTutorial = function (){
    	$("#j_tech2").show();
    };
    
    $scope.hideAndroidTutorial = function (){
    	$("#j_tech2").hide();
    };
    
    $scope.showAppleTutorial = function (){
    	$("#j_tech1").show();
    };
    
    $scope.hideAppleTutorial = function (){
    	$("#j_tech1").hide();
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

    //刷新主账户余额
    $scope.refreshMainbal=function () {

        $scope.mianBalance="正在查询";

        $http.get(getBalance, {params: {'listOfAcctIds': '' + $scope.mianAcctId + ''}}).then(function (res) {

            $scope.mianBalance = res.data.data['' + $scope.mianAcctId + ''];

        }, function () {

            $scope.mianBalance="查询失败";
        });
        
    };

    $scope.forwardLogin = function(credentials){
    	$scope.login(credentials);
    	$state.go("userCenter");
    };
    
    $scope.redirectAccountAction = function(){
    	if($scope.isLogin){
    		$state.go("userCenter");
    	}else{
    		$state.go("login");
    	}
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
        	var sessionId = sessionStorage.getItem("sessionId");
            authService.login(credentials, sessionId).then(function(res){
            	if(res == 0){
            		$scope.queryCaptchaCode();
            	}else{
            		
            		//$scope.credentials = credentials;
            		userInfoServ.queryUserInfo().then(function(userInfo){
            			userInfoServ.queryUserAcc(userInfo.id).then(function(userAcc){
            				var mainAcc = null;
            				var redPacketAcc = null;
            				
            				for(var i = 0; i < userAcc.length; i++){
            					var val = userAcc[i];
            					if(val.accType == 1){
            						mainAcc = val;
            					}else{
            						redPacketAcc = val;
            					}
            				}
            				
            				sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
            				sessionStorage.setItem("mainAcc", JSON.stringify(mainAcc));
            				sessionStorage.setItem("redPacketAcc", JSON.stringify(redPacketAcc));
            				
            				$scope.userInfo = userInfo;
            				$scope.mainAcc = mainAcc;
            				$scope.redPacketAcc = redPacketAcc;
            				
            				$scope.isLogin = true;
            			});
            		});
            	}
            });
        }

    };

    
    
    //账户退出
    $scope.logout = function () {

    	authService.logout().then(function(res){
    		sessionStorage.clear();
    		$scope.isLogin = false;
    		$scope.userInfo = null;
			$scope.mainAcc = null;
			$scope.redPacketAcc = null;
    	},
    	 function() {
    		sessionStorage.clear();
    		$scope.isLogin = false;
    		$scope.userInfo = null;
			$scope.mainAcc = null;
			$scope.redPacketAcc = null;
    	 });

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

    
   
    $scope.mobileLogin_userPanel = function(){
    	if($scope.isLogin){
    		$("#memeber-panel").toggle();
    	}else{
    		$("#login-panel").toggle();
    	}
    	
    };
    
    
    $scope.changeActive = function(menueNum){
    	if(menueNum == 0){
    		$scope.isMainActive = true;
    		$scope.isGameActive = false;
    		$scope.isAccActive = false;
    		
    	}else if(menueNum == 1){
    		$scope.isMainActive = false;
    		$scope.isGameActive = true;
    		$scope.isAccActive = false;
    		
    	}else if(menueNum == 2){
    		$scope.isMainActive = false;
    		$scope.isGameActive = false;
    		$scope.isAccActive = true;
    		
    	}else if(menueNum == 3){
    		$scope.isLoginActive = true;
    		$scope.isRegActive = false;
    	}else if(menueNum == 4){
    		$scope.isLoginActive = false;
    		$scope.isRegActive = true;
    	}
    	
    };
    
    $scope.mobileIndexInit = function(){
    	$scope.isMainActive = true;
		$scope.isGameActive = false;
		$scope.isAccActive = false;
		
		$scope.isLoginActive = true;
		$scope.isRegActive = false;
		
		$state.go("main");
    };
    
    $scope.hiddenHeader = function(){
    	$("#header").addClass("hiddenHeader");
    };
    
    $scope.showHeader = function(){
    	$("#header").removeClass("hiddenHeader");
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
    
        
    $scope.moveTutorial = function(){
    	
    };
    
    $scope.showForgetPwd = function(){
    	$("#forget_pwd").show();
    };
    
    $scope.hideForgetPwd = function(){
    	$("#forget_pwd").hide();
    };
    
    $scope.showMoney = function(){
    	$("aside.money_des").toggle();
    };
    
    $("p.down_btn a").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("p.down_btn a").removeClass("active");
		$(this).addClass("active");
		
		if(tabType == "0"){
			$("#j_tech1").show();
			$("#j_tech2").hide();
		}else if(tabType == "1"){
			$("#j_tech1").hide();
			$("#j_tech2").show();
		}
	});
    
    $("div.forget_nav span").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.forget_nav span").removeClass("active");
		$(this).addClass("active");
		
		$("div.forget_content div.j-nav_tab").each(function(){
			if($(this).attr("data-num") == tabType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
    
    $scope.changeUserCenterFocus = function(dataNum){
    	$("div.userbar a.u_b_item").removeClass("active");
    	$("div.userbar a.u_b_item[data-num='"+dataNum+"']").addClass("active");
    };
    
    $("div.nav-box nav a").on("click", function(){
    	$("div.nav-box nav a").removeClass("active");
    	$(this).addClass("active");
    });
    
    
    $scope.slotNav = function(){
    	$("#slotNav").toggle();
    }
    
    $scope.baccaratNav = function(){    	
    	$("#baccaratNav").toggle();
    }
    
    $scope.fishingNav = function(){    	
    	$("#fishingNav").toggle();
    }
    
    $scope.phoneNav = function(){
    	$("#phoneNav").toggle();
    }
    
    
    
    $scope.displayAllWallet = function(){
    	$scope.platWallet = new Array();
    	$scope.tpWallet = new Array();
    	
    	walletService.queryWallet().then(function(res){
    		for(var i = 0; i < res.length; i++){
    			var wallet = res[i];
    			if(walletService.isPlatWallet(wallet)){    				
    				$scope.platWallet.push(wallet);
    			}else{
    				$scope.tpWallet.push(wallet);
    			}
    		}
    		
    		for(var i = 0;i<$scope.platWallet.length;i++){
    			$scope.refreshBalance($scope.platWallet[i].id);
    		}
    	}, function(error){
    		
    	});
    }
    
    $scope.refreshBalance = function(id){
    	var realId = ''+id;
    	if(realId.indexOf("_") > 0){
    		realId = realId.substring(realId.indexOf("_")+1);
    	}
    	walletService.inquireBalance(realId).then(function(res){
    		$("#"+id).html(res);
    	}, function(error){
    		
    	});
    }
    
    //$scope.displayAllWallet();
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


}]).service('authService', ["$http", "$rootScope", "$cookies", "$q", function ($http, $rootScope, $cookies, $q) {
    this.login = function (credentials, sessionId) {
    	var deferred = $q.defer();
    	var loginURL_ = loginURL.replace("{sessionId}", sessionId);
    	var grantType = "password";
    	var clientId = "lottery-client";
    	var clientSecret = "secret_1";
    	var captcha = credentials.captchaCode;
    	var userName = credentials.username;
    	var pwd = credentials.password;
    	
        showLoading('登录中……');
        $http({
            method: 'POST',
            headers: {'Content-type': 'application/x-www-form-urlencoded'},
            url: loginURL_,
            data: $.param({
            	grant_type: grantType,
            	client_id: clientId,
            	client_secret: clientSecret,
                username: userName,
                password: pwd,
                captcha: captcha
            })
        }).success(function (data, status, headers, config) {

            hideLoading();
            //if (data.data.status == 1) {
                sessionStorage.setItem("accessToken", data.access_token);
                sessionStorage.setItem("refreshToken", data.refresh_token);
                sessionStorage.setItem("tokenExpiresIn", data.expires_in);
                sessionStorage.setItem("loginTime", new Date().getTime());
                /*delCookie("uuid");
                setCookie("uuid", data.data.uuid);
                
                window.location.href = "index.html";*/
            //}

            deferred.resolve(1);
        }).error(function (data, status, headers, config) {
            hideLoading();
            //$("#loginCodeImg").attr("src", get_code + Math.random());
            deferred.resolve(0);
        });
        
        return deferred.promise;
    };

    this.refreshToken = function () {

    	var deferred = $q.defer();    	
    	var grantType = "refresh_token";
    	var clientId = "lottery-client";
    	var clientSecret = "secret_1";
    	var refreshToken = sessionStorage.getItem("refreshToken");
        
        $http({
            method: 'POST',
            url: refreshTokenURL,
            data: $.param({
            	grant_type: grantType,
            	client_id: clientId,
            	client_secret: clientSecret,
                refresh_token: refreshToken
            })
        }).success(function (data, status, headers, config) {

        	sessionStorage.setItem("accessToken", data.access_token);
            sessionStorage.setItem("refreshToken", data.refresh_token);
            sessionStorage.setItem("tokenExpiresIn", data.expires_in);
            sessionStorage.setItem("loginTime", new Date().getTime());

            deferred.resolve(1);
        }).error(function (data, status, headers, config) {
        	deferred.reject(-1);
        });
        
        return deferred.promise;
    };
    
    this.logout = function () {

    	var deferred = $q.defer();
    	
        $http({
            method: 'GET',
            url: logoutURL
        }).success(function (data, status, headers, config) {

            //hideLoading();
            //if (data.data.status == 1) {
                
                /*delCookie("uuid");
                setCookie("uuid", data.data.uuid);
                
                window.location.href = "index.html";*/
            //}

            deferred.resolve(1);
        }).error(function (data, status, headers, config,textStatus, errorThrown) {
        	deferred.reject(-1);
        });
        
        return deferred.promise;
    };


    this.queryCaptchaCode = function(sessionId){
    	var deferred = $q.defer();
    	var captchaCode = '';
    	
    	
    	
    	var queryCaptchaCodeURL_ = queryCaptchaCodeURL.replace('{sessionId}', sessionId); 
    	$http.get(queryCaptchaCodeURL_).then(function(res){
    		
    		if (res.data.status == 1) {

    			captchaCode = res.data.data;

	        }
    		
    		deferred.resolve(captchaCode);
    	}, function(){        		
    		deferred.reject(-1);
    	});
		
		return deferred.promise;
    };
    
    this.querySessionId = function(){
    	var deferred = $q.defer();
    	var sessionId = '';
    	
    	$http.get(querySessionIdURL).then(function(res){
    		
    		if (res.data.status == 1) {

    			sessionId = res.data.data.sessionId;

	        }
    		
    		deferred.resolve(sessionId);
    	}, function(){
    		
    		deferred.reject(-1);
    	});
    	
    	return deferred.promise;
    }
}]);


