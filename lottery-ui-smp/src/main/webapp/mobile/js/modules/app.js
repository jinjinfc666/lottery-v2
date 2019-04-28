var app = angular.module('myApp-mobile', ['ui.router','ngCookies','authentication','user','wallet']);

app.config(["$httpProvider", function ($httpProvider) {
	    $httpProvider.interceptors.push('httpInterceptor');
	    $httpProvider.interceptors.push(['$injector', function ($injector) {
	        return $injector.get('AuthInterceptor');
	    }
    ]);
}])
.config(["$stateProvider", "$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('main');
    $stateProvider.state('default', {
        url: '/main',
        templateUrl: 'embed/main.html'
    }).state('login', {
        url: '/login',
        templateUrl: 'embed/login/login.html'
    }).state('register', {
        url: '/register',
        templateUrl: 'embed/user-center/register.html'
    }).state('forgotpwd', {
        url: '/forgotpwd',
        templateUrl: 'embed/login/forgotpwd.html'
    }).state('slot', {
        url: '/slot',
        templateUrl: 'embed/slot.html'
    }).state('promo', {
        url: '/promo',
        templateUrl: 'embed/promo.html'
    }).state('vip', {
        url: '/vip',
        templateUrl: 'embed/vip.html'
    }).state('agent', {
        url: '/agent',
        templateUrl: 'embed/agent/agent.html'
    }).state('agent_project', {
        url: '/agent_project',
        templateUrl: 'embed/agent_project.html'
    }).state('agentRegister', {
        url: '/agentRegister',
        templateUrl: 'embed/agent/agent-register.html'
    }).state('agentBrandIntro', {
        url: '/agentBrandIntro',
        templateUrl: 'embed/agent/agent-brand-introduce.html'
    }).state('agentPolicy', {
        url: '/agentPolicy',
        templateUrl: 'embed/agent/agent-policy.html'
    }).state('agentFAQ', {
        url: '/agentFAQ',
        templateUrl: 'embed/agent/agent-FAQ.html'
    }).state('agentContact', {
        url: '/agentContact',
        templateUrl: 'embed/agent/agent-contact.html'
    }).state('userCenter', {
        url: '/userCenter',
        templateUrl: 'embed/user-center/user-center.html'
    }).state('fundManDeposit', {
        url: '/fundManDeposit',
        templateUrl: 'embed/user-center/fund-management-deposit.html'
    }).state('bindingBankCard', {
        url: '/bindingBankCard',
        templateUrl: 'embed/user-center/binding-bank-card.html'
    }).state('userInfo', {
        url: '/userInfo',
        templateUrl: 'embed/user-center/user-info.html'
    }).state('fundManWithdraw', {
        url: '/fundManWithdraw',
        templateUrl: 'embed/user-center/fund-management-withdraw.html'
    }).state('fundManTransfer', {
        url: '/fundManTransfer',
        templateUrl: 'embed/user-center/fund-management-transfer.html'
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
    }).state('siteMes', {
        url: '/siteMes',
        templateUrl: 'embed/mes/site-mes.html'
    }).state('accountMan', {
        url: '/accountMan',
        templateUrl: 'embed/user-center/account-management.html'
    }).state('modifyLoginPwd', {
        url: '/modifyLoginPwd',
        templateUrl: 'embed/user-center/account-management-login-pw.html'
    }).state('modifyPayPwd', {
        url: '/modifyPayPwd',
        templateUrl: 'embed/user-center/account-management-pay-pw.html'
    });

}])
.factory('httpInterceptor', ["$q", function ($q) {

    return {
        request: function (request) {

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

            console.log("响应拦截" + response.data.code);
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

}])
.factory('AuthInterceptor', function ($rootScope, $q) {
    return {
        responseError: function (response) {
            return $q.reject(response);
        }
    };
});
