/**
 * 
 */
var promoModule = angular.module('user', []);

promoModule.controller('promoCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$(".pro_box a").each(function(){
		$(this).on("click",function(){
			var promoType = $(this).attr('data-num');
			$("#dlg"+promoType).show();
		});
	});

	$("span.j-close").each(function(){
		$(this).on("click",function(){
			var promoType = $(this).attr("num");
			$("#dlg"+promoType).hide();
		});
	});
	
	
	$("div.user_info_main li").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.user_info_main li").removeClass('active');
		$(this).addClass('active');
		$("div.user_info_main div.user_center_nav_content").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();				
			}else{
				$(this).hide();
			}
		});
	});
}]).controller('userInfoCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$("span.j-nav_item").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.u_c_window").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
}]).controller('accManCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$("span.j-nav_item").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.u_c_window").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
	$scope.bindingBankCard = function(){
		location.href="#userInfo";
	}
}]).controller('hisRecCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$("span.j-nav_item").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.u_c_window").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
}]).controller('siteEmailCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$("a.row").on("click",function(){
		$("#j_letter_box").show();
	});
	
	$("span.j-close").on("click",function(){
		$("#j_letter_box").hide();
	});
	
}]);