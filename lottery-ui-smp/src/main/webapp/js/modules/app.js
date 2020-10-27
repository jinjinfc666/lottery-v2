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
    }).state('lottery_betting_5fc', {
        url: '/lottery_betting_5fc',
        templateUrl: 'embed/lottery_betting_5fc.html',
        params:{'seqNo':1}
    }).state('lottery_betting_yfb', {
        url: '/lottery_betting_yfb',
        templateUrl: 'embed/lottery_betting_yfb.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3', {
        url: '/lottery_betting_tc3',
        templateUrl: 'embed/lottery_betting_tc3.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_yzdw', {
        url: '/lottery_betting_tc3_yzdw',
        templateUrl: 'embed/lottery_betting_tc3_yzdw.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_ezdw', {
        url: '/lottery_betting_tc3_ezdw',
        templateUrl: 'embed/lottery_betting_tc3_ezdw.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_ezhs', {
        url: '/lottery_betting_tc3_ezhs',
        templateUrl: 'embed/lottery_betting_tc3_ezhs.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_ezzh', {
        url: '/lottery_betting_tc3_ezzh',
        templateUrl: 'embed/lottery_betting_tc3_ezzh.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_szdw', {
        url: '/lottery_betting_tc3_szdw',
        templateUrl: 'embed/lottery_betting_tc3_szdw.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_szhs', {
        url: '/lottery_betting_tc3_szhs',
        templateUrl: 'embed/lottery_betting_tc3_szhs.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_szzh', {
        url: '/lottery_betting_tc3_szzh',
        templateUrl: 'embed/lottery_betting_tc3_szzh.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_zs', {
        url: '/lottery_betting_tc3_zs',
        templateUrl: 'embed/lottery_betting_tc3_zs.html',
        params:{'seqNo':1}
    }).state('lottery_betting_tc3_zl', {
        url: '/lottery_betting_tc3_zl',
        templateUrl: 'embed/lottery_betting_tc3_zl.html',
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
}]);
