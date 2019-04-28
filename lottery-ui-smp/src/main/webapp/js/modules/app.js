var ignoreTokenArray = new Array('query-sesionid','verification-code-Img');

var app = angular.module('myApp', ['ui.router','ngCookies','authentication','user','agent','wallet','lottery', 'sysMes']);

app.config(["$httpProvider", function ($httpProvider) {
		$httpProvider.defaults.withCredentials = false;
	    $httpProvider.interceptors.push('httpInterceptor');
	    $httpProvider.interceptors.push(['$injector', function ($injector) {
	        return $injector.get('AuthInterceptor');
	    }
    ]);
}])
.config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('lottery_list');
    $stateProvider.state('lottery_list', {
        url: '/lottery_list',
        templateUrl: 'embed/lottery_list.html'
    }).state('lottery_betting_cqssc', {
        url: '/lottery_betting',
        templateUrl: 'embed/lottery_betting_cqssc.html',
        params:{'seqNo':1}
    }).state('lottery_betting_bjpk10', {
        url: '/lottery_betting',
        templateUrl: 'embed/lottery_betting_bjpk10.html',
        params:{'seqNo':1}
    }).state('lottery_betting_xyft', {
        url: '/lottery_betting',
        templateUrl: 'embed/lottery_betting_xyft.html',
        params:{'seqNo':1}
    }).state('lottery_betting_xjssc', {
        url: '/lottery_betting',
        templateUrl: 'embed/lottery_betting_xjssc.html',
        params:{'seqNo':1}
    }).state('userCenter', {
        url: '/userCenter',
        templateUrl: 'embed/user_center.html'
    }).state('userCenter.fundManDeposit', {
        url: '/fundManDeposit',
        templateUrl: 'embed/user-center/fund-management-deposit.html'
    }).state('userCenter.fundManWithdraw', {
        url: '/fundManWithdraw',
        templateUrl: 'embed/user-center/fund-management-withdraw.html'
    }).state('userCenter.fundManTransfer', {
        url: '/fundManTransfer',
        templateUrl: 'embed/user-center/fund-management-transfer.html'
    }).state('userCenter.promo', {
        url: '/promo',
        templateUrl: 'embed/user-center/promo-self-service.html'
    }).state('userCenter.userInfo', {
        url: '/userInfo',
        templateUrl: 'embed/user-center/user-info.html'
    }).state('userCenter.memberHierarchy', {
        url: '/memberHierarchy',
        templateUrl: 'embed/user-center/member-hierarchy.html'
    }).state('userCenter.accMan', {
        url: '/accMan',
        templateUrl: 'embed/user-center/account-management.html'
    }).state('userCenter.hisRecord', {
        url: '/hisRecord',
        templateUrl: 'embed/user-center/historical-record.html'
    }).state('userCenter.siteEmail', {
        url: '/siteEmail',
        templateUrl: 'embed/user-center/site-email.html'
    }).state('about', {
        url: '/about',
        templateUrl: 'embed/about.html'
    });

}])
.factory('httpInterceptor', ["$q", function ($q) {

    return {
        request: function (request) {
        	console.log("请求URL :: " + request.url);
            return request;
        },
        requestError: function (rejection) {

            console.log("请求失败拦截");
            if (canRecover(rejection)) {
                return responseOrNewPromise
            }
            return $q.reject(rejection);
        },
        response: function (response) {

            console.log("响应拦截        url:"+ response.config.url+"   status:" + response.data.status);
            if (response.data.code == -2 || response.data.code == -102) {

                window.location.href = "index.html";
            }
            return response;
        },
        responseError: function (rejection) {

            console.log("响应失败拦截" + rejection.status);

            if (rejection.status == 1600 || rejection.status == 1601 || rejection.status == 520) {  //若发现位登录

                window.location.href = "index.html";
            }

            return $q.reject(rejection);
        }
    };
}])
.filter("trusted", ["$sce", function ($sce) {

    return function (html) {

        if (typeof html == 'string')   //判断类型为字符串
            return $sce.trustAsHtml(html);
        return html;

    };

}]).factory('AuthInterceptor', ['$rootScope', '$q', function ($rootScope, $q) {
    return {
    	request: function(config){
            config.headers = config.headers || {};
            if(sessionStorage.getItem('accessToken')){
            	var isIgnoreToken = false;
            	for(var i = 0; i < ignoreTokenArray.length; i++){
            		if(config.url.indexOf(ignoreTokenArray[i]) >= 0){
            			isIgnoreToken = true;
            			break;
            		}
            	}
            	
            	
                var loginTime = sessionStorage.getItem('loginTime');
                var currTime = new Date();
                var intervalTime = currTime.getTime() - loginTime;
                intervalTime = intervalTime / 1000;
                intervalTime = intervalTime / 60;
                //小于10分钟则刷新token
                if(intervalTime > 30 && config.url.indexOf("logout") == -1){
                	//token超时需要重新登录
                	$rootScope.$broadcast('user.logout', {
                		
                	});
                	
                	return $q.reject(408);
                	
                }else if(intervalTime >= 15 && config.url.indexOf("logout") == -1 ){
                	var grantType = "refresh_token";
                	var clientId = "lottery-client";
                	var clientSecret = "secret_1";
                	var refreshToken = sessionStorage.getItem("refreshToken");
                	var promiseToHaveValidToken;
                	                	
                	promiseToHaveValidToken = $.ajax({
                		  data:$.param({
                        	grant_type: grantType,
                        	client_id: clientId,
                        	client_secret: clientSecret,
                            refresh_token: refreshToken
                		  }),
                		  url:refreshTokenURL,
                		  type: "POST",
                		  dataType: "json",
                		  async: false,
                		  success: function(data){
                			  sessionStorage.setItem("accessToken", data.access_token);
                              sessionStorage.setItem("refreshToken", data.refresh_token);
                              sessionStorage.setItem("tokenExpiresIn", data.expires_in);
                              sessionStorage.setItem("loginTime",new Date().getTime());
                              //return config;
                		    },
                		    error: function(XMLHttpRequest, textStatus, errorThrown){
                		    	//return config;
                		    }
                	});
                		
                }
                
                if(!isIgnoreToken){
            		config.headers.authorization = 'Bearer ' + sessionStorage.getItem('accessToken');
            	}
            	
                return config;
                
            }else{
            	return config;
            }
        },
        responseError: function (response) {
            return $q.reject(response);
        }
    };
    
}]).factory("PrintToConsole", ["$rootScope", function ($rootScope) {
    var handler = { active: false };
    handler.toggle = function () { handler.active = !handler.active; };
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        if (handler.active) {
            console.log("$stateChangeStart --- event, toState, toParams, fromState, fromParams");
            console.log(arguments);
        };
    });
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        if (handler.active) {
            console.log("$stateChangeError --- event, toState, toParams, fromState, fromParams, error");
            console.log(arguments);
        };
    });
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        if (handler.active) {
            console.log("$stateChangeSuccess --- event, toState, toParams, fromState, fromParams");
            console.log(arguments);
        };
    });
    $rootScope.$on('$viewContentLoading', function (event, viewConfig) {
        if (handler.active) {
            console.log("$viewContentLoading --- event, viewConfig");
            console.log(arguments);
        };
    });
    $rootScope.$on('$viewContentLoaded', function (event) {
        if (handler.active) {
            console.log("$viewContentLoaded --- event");
            console.log(arguments);
        };
    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
        if (handler.active) {
            console.log("$stateNotFound --- event, unfoundState, fromState, fromParams");
            console.log(arguments);
        };
    });
    return handler;
}]).service('commonService', ["$http", function ($http) {

    //过滤账户信息
    this.pingAccount = function (data) {

        var array = new Array();

        for (var i = 0; i < data.length; i++) {

            var memAcc = data[i];

            if (memAcc.product == undefined || (memAcc.product != undefined && memAcc.product.inUse == "Y" && memAcc.product.singleWallet == "N")) {

                if (memAcc.type.code == "M_CA") {

                    memAcc.title = "主账户";

                } else if(memAcc.type.code == "P_AG"){

                    memAcc.title = "AG真人";

                } else {

                    memAcc.title = memAcc.product.code + "电游";

                }

                array.push(memAcc);

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
