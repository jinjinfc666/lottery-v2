/**
 * 
 */
var promoModule = angular.module('agent', []);

promoModule.controller('agentRegCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$scope.registerModal = function(){
		$("#registerAgentModal").toggle();
	};
}]);