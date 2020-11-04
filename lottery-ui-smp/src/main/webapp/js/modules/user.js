/**
 * 
 */
var userModule = angular.module('user', []);

userModule.controller('userInfoCtrl', ["$scope", "$http","$state", "$interval", 'userInfoServ', "authService", function ($scope, $http, $state,$interval, userInfoServ, authService) {
	$scope.initUserInfo = function(){
		if(sessionStorage.getItem("userInfo") == null){
			return ;
		}
		
		$scope.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		
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
			
			$state.go('userCenter.userInfo');
		});
	};
	
	$("span.j-nav_item").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("span.j-nav_item").removeClass('active');
		$(this).addClass('active');
		
		$("div[data-type='user_info']:visible div.u_c_w_content").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});		
	});
	
	$("span.j-close").each(function(){
		$(this).on("click",function(){
			$("#bankCardModal").hide();
		});
	});
	
	$scope.getMemberInfo = function () {
		if(sessionStorage.getItem("userInfo") == null){
			return ;
		}
		
		$scope.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		if(typeof $scope.userInfo.realName != 'undefined' 
			&& $scope.userInfo.realName != null
			&& $scope.userInfo.realName.length > 0){
			$scope.userInfo.isSetted = true;
		}else{
			$scope.userInfo.isSetted = false;
		}
    };

    /*
    $scope.getBankList = function () {

        $http.get(getBankList).then(function (res) {

            $scope.bankList = res.data.data;

        }, function () {

        });

    };
    */


    //保存用户信息
    $scope.updateMemInfo = function () {
        if (typeof $scope.userInfo.realName != 'undefined'
        	&& $scope.userInfo.realName != null
        	&& $scope.userInfo.realName.length > 0
        	&& !ischina($scope.userInfo.realName)) {
        	
            showToast("请输入真实姓名");

        }else if (typeof $scope.userInfo.email != 'undefined'
        	&& $scope.userInfo.email != null
        	&& $scope.userInfo.email.length > 0
        	&& !isEmail($scope.userInfo.email)) {

            showToast("请输入正确的电子邮件");

        }else {
        	userInfoServ.updateUserInfo($scope.userInfo).then(function(res){
        		sessionStorage.setItem("userInfo", JSON.stringify($scope.userInfo));
        		showToast('成功更新用户基本信息！！');
        	},
        	function(err){
        		console.log(err);
        		showToast('更新用户基本信息失败！！');
        	});
        }
    };


    //保存银行卡
    $scope.saveCard = function () {

        if ($scope.cardNo == undefined) {

            showToast("请输入银行卡号");


        } else {
        	userInfoServ.saveCard($scope.curBank, $scope.cardNo).then(function(res){
        		showToast('成功绑定银行卡！！');
        		$scope.bankCardModal();
        	},
        	function(err){
        		showToast('绑定银行卡失败！！');
        	});
        }
    }

    //修改邮箱
    $scope.upEmail = function () {

        if ($scope.uemail == undefined) {

            showToast("请输入邮箱");

        } else if (!isEmail($scope.uemail)) {

            showToast("请输入正确的邮箱");

        } else {

            $http({
                method: 'post',
                url: updateSingle,
                data: $.param({'email': '' + $scope.uemail + ''}),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {

                    showToast("修改成功");

                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {

                console.log("修改失败");

            });


        }
    };

    //修改qq
    $scope.upQq = function () {

        if ($scope.uqq == undefined) {

            showToast("请输入QQ");

        } else {

            $http({
                method: 'post',
                url: updateSingle,
                data: $.param({'qqCode': '' + $scope.uqq + ''}),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {

                    showToast("修改成功");

                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {

                console.log("修改失败");

            });


        }

    };

    //修改微信
    $scope.upWx = function () {

        if ($scope.uwx == undefined) {

            showToast("请输入微信");

        } else {

            $http({
                method: 'post',
                url: updateSingle,
                data: $.param({'wxCode': '' + $scope.uwx + ''}),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {

                    showToast("修改成功");

                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {

                console.log("修改失败");

            });

        }
    };

    $scope.vm = {
        canGetCode: true,
        time: 60
    };


    //获取手机验证码
    $scope.applyPhoneQr = function () {
    	
    	userInfoServ.applyPhoneQr().then(function(){
    		
    	},
    	function(){
    		showToast("获取验证码失败!!");
    	});    	
       
    };


    //绑定手机
    $scope.verifyPhone = function () {

    	userInfoServ.verifyPhone().then(function(){
    		
    	},
    	function(){
    		showToast("电话号码验证失败!!");
    	}); 

    };
    
    $scope.bankCardModal = function(){
    	$("#bankCardModal").toggle();
    }
    
    $scope.mobileVerify = function(){
    	$("#d_sms").show();
    	$("#btn_mobileVerify").hide();
    }
    
    $scope.goMobileVerify = function(){
    	$("div.u_c_nav span[data-num=2]").click();
    };
    
    $scope.showSMS = function(){
    	if($scope.isSms == null){
    		$scope.isSms = true;
    	}else{
    		$scope.isSms = !$scope.isSms;
    	}
    	
    }
    
    $scope.regUser = function (userType,platRebate) {
        if (typeof $scope.userInfo.realName != 'undefined'
        	&& $scope.userInfo.realName != null
        	&& $scope.userInfo.realName.length > 0
        	&& !ischina($scope.userInfo.realName)) {
        	
            showToast("请输入真实姓名");

        }else if (typeof $scope.userInfo.userName == 'undefined'
        	|| $scope.userInfo.userName == null
        	|| $scope.userInfo.userName.length == 0) {

            showToast("请输入正确的登录用户");

        }else if (typeof $scope.userInfo.loginPwd == 'undefined'
        	|| $scope.userInfo.loginPwd == null
        	|| $scope.userInfo.loginPwd.length == 0) {

            showToast("请输入正确的登录密码");

        }else if (typeof $scope.userInfo.email != 'undefined'
        	&& $scope.userInfo.email != null
        	&& $scope.userInfo.email.length > 0
        	&& !isEmail($scope.userInfo.email)) {

            showToast("请输入正确的电子邮件");

        }else {
        	var location = window.location.href;
        	if(location.indexOf('?') < 0){
        		showToast('请联系您的上级获取注册地址!');
        		return;
        	}
        	
        	var agentId = location.substring(location.indexOf('?') + 1);
        	agentId = agentId.substring(8);
        	$scope.userInfo.superior = agentId;
        	$scope.userInfo.userType = userType;
        	$scope.userInfo.platRebate = platRebate;
        	
        	userInfoServ.regUser($scope.userInfo).then(function(res){
        		//sessionStorage.setItem("userInfo", JSON.stringify($scope.userInfo));
        		showToast('成功注册用户！！');
        	},
        	function(err){
        		console.log(err);
        		showToast('注册用户失败！！');
        	});
        }
    };
    
    $scope.initBankCard = function () {
    	userInfoServ.queryBank().then(function(res){
    		$scope.banks = res;
    	},
    	function(err){
    		//console.log(err);
    		//showToast('注册用户失败！！');
    	});
    	
    	
    };
    
    $scope.queryBankCard = function () {
    	userInfoServ.queryBankCard().then(function(res){
    		$scope.bankCards = res;
    	},
    	function(err){
    		//console.log(err);
    		//showToast('注册用户失败！！');
    	});
    	
    	
    };
    
}]).controller('accManCtrl', ["$scope", "$http", "$location","$state", "userInfoServ", "authService", function ($scope, $http, $location, $state, userInfoServ, authService) {

	$("span.j-nav_item").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("span.j-nav_item").removeClass("active");
		$(this).addClass("active");
		$("div.u_c_window").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
	
	
	$scope.modifyLoginPwd = function () {

        if ($scope.oldPwd == undefined) {

            showToast("请输入原密码");
        } else if ($scope.newPwd == undefined) {

            showToast("请输入新密码");

        } else if ($scope.cnewPwd == undefined) {

            showToast("请输入确认密码");

        } else if ($scope.newPwd != $scope.cnewPwd) {

            showToast("两次输入的密码需一致");

        } else {
        	userInfoServ.modifyLoginPwd($scope.oldPwd, $scope.newPwd).then(function(){
        		showToast("成功修改登陆密码!!");
        		authService.logout();
        	},
        	function(){
        		showToast("更新登录密码失败!!");
        	});  
        	
            

        }

    };

    $scope.modifyFundPwd = function () {

        if ($scope.oldPwd == undefined) {

            showToast("请输入原密码");
        } else if ($scope.newPwd == undefined) {

            showToast("请输入新密码");

        } else if ($scope.cnewPwd == undefined) {

            showToast("请输入确认密码");

        } else if ($scope.newPwd != $scope.cnewPwd) {

            showToast("两次输入的密码需一致");

        } else {
        	userInfoServ.modifyFundPwd($scope.oldPwd, $scope.newPwd).then(function(){
        		showToast("成功修改资金密码!!");
        	},
        	function(){
        		showToast("更新登录密码失败!!");
        	});  
        	
            

        }

    };
	
}]).controller('siteEmailCtrl', ["$scope", "$http","$state", function ($scope, $http, $state,$location) {

	$("a.row").on("click",function(){
		$("#j_letter_box").show();
	});
	
	$("span.j-close").on("click",function(){
		$("#j_letter_box").hide();
	});
	
}]).controller('userBalCtrl', ["$scope", "$http","$state", "walletService", function ($scope, $http, $state,walletService) {

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
    
    $scope.displayAllWallet();
	
}]).controller('annoucementCtrl', ["$scope", "$http","$state", "annServ", function ($scope, $http, $state, annServ) {
	$scope.accSet = new Array();
	
	$scope.queryAnnouncement = function(){
		annServ.queryAnnouncement().then(function(res){
			$scope.accSet = res;
		}, function(){
			
		});		
	}
	
	/**
	 * display the content of announcement
	 * 
	 * @annId the id of announcement
	 */
	$scope.showAnn = function(annId){
		$("#annC_"+annId).toggle();
	}
	
	
}]).controller('helpCenterCtrl', ["$scope", "$http","$state", function ($scope, $http, $state) {
	
	$scope.pageInit = function(){
		$("div.aboutbar div.a_b_item").on('click', function(){
			var num = $(this).attr("data-index");
			$("div.aboutbar div.a_b_item").removeClass("active");
			$(this).addClass("active");
			
			$("div.aboutshow div.j-tab").hide();
			$("div.aboutshow div.j-tab[data-index=" + num + "]").show();
		});
	}
	
	/**
	 * display the content of announcement
	 * 
	 * @annId the id of announcement
	 */
	$scope.showAnn = function(annId){
		$("#annC_"+annId).toggle();
	}
	
	
}]).service('promoServ', ['$http', '$q',function($http, $q){
	
	this.queryBanner = function(){
		var deferred = $q.defer();
		
		$http.get(getPromList, {params: {client: '3'}, cache: true}).then(function (res) {

	        var promArray = new Array();

	        for (var i = 0; i < res.data.data.length; i++) {

	            if (i == 0) {

	                res.data.data[i].cls = "active";
	                res.data.data[i].dis = "block";
	                res.data.data[i].width = parseInt(100/res.data.data.length);
	            } else {

	                res.data.data[i].cls = "";
	                res.data.data[i].dis = "none";
	                res.data.data[i].width = parseInt(100/res.data.data.length);
	            }

	            promArray.push(res.data.data[i]);

	        }

	        

	        deferred.resolve(promArray);
	    }, function () {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	
	 
	 
	 
}]).service('annServ', ['$http', '$q',function($http, $q){//announcement service
	
	this.queryAnnouncement = function(){
		var deferred = $q.defer();
		var annSet = new Array();
		$http.get(getNotice).then(function (res) {
			if(res.data.code == 0){
				annSet = res.data.data;
				deferred.resolve(annSet);
			}else{
				deferred.reject(-1);
			}
	    }, function () {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	
	 
	 
	 
}]).service('userInfoServ', ['$http', '$q',function($http, $q){
	
	this.queryUserInfo = function(){
		var deferred = $q.defer();
		var annSet = new Array();
		$http.get(queryUserInfoURL).then(function (res) {
			if(res.data.status == 1){
				var userInfo = res.data.data;
				
				deferred.resolve(userInfo);
			}else{
				deferred.reject(-1);
			}
	    }, function () {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.queryUserAcc = function(userId){
		var deferred = $q.defer();
		var queryUserAccURL_ = queryUserAccURL.replace("{userId}", userId);
		
		$http.get(queryUserAccURL_).then(function (res) {
			if(res.data.status == 1){
				var userAcc = res.data.data;
				
				deferred.resolve(userAcc);
			}else{
				deferred.reject(-1);
			}
	    }, function () {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	 
	this.updateUserInfo = function(userInfo){
		var deferred = $q.defer();
		//var queryUserAccURL_ = queryUserAccURL.replace("{userId}", userInfo.id);
		
		$http.put(updateMemberInfoURL,
				userInfo,
				{'Content-Type': 'application/json'}).then(function (res) {
			if(res.data.status == 1){
				//var userAcc = res.data.data;
				
				deferred.resolve(1);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	}; 
	
	this.applyPhoneQr = function(){
		var deferred = $q.defer();
		var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		var applyQrURL_ = applyQrURL.replace("{userName}", userInfo.userName);
		
		$http.get(applyQrURL_).then(function (res) {
			if(res.data.status == 1){
				//var userAcc = res.data.data;
				
				deferred.resolve(1);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.verifyPhone = function(){
		var deferred = $q.defer();
		var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		var verifyPhoneURL_ = verifyPhoneURL.replace("{userName}", userInfo.userName);
		
		$http.put(verifyPhoneURL_,
				userInfo,
				{'Content-Type': 'application/json'}).then(function (res) {
			if(res.data.status == 1){
				//var userAcc = res.data.data;
				
				deferred.resolve(1);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.modifyLoginPwd = function(oldPwd, newPwd){
		var deferred = $q.defer();
		//var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		//var verifyPhoneURL_ = verifyPhoneURL.replace("{userName}", userInfo.userName);
		$http.post(modifyLoginPwdURL,
				{'oldPwd': '' + oldPwd + '', 'newPwd': '' + newPwd + '', 'confirmPwd': '' + newPwd + ''},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			 deferred.resolve(1);

	        }else{
	        	deferred.reject(-1);
	        }
    		
    	}, function(error){    		
    		deferred.reject(-1);
    	});	
		
		return deferred.promise;
	};
	
	 
	this.modifyFundPwd = function(oldPwd, newPwd){
		var deferred = $q.defer();
		$http.post(modifyFundPwdURL,
				{'oldPwd': '' + oldPwd + '', 'newPwd': '' + newPwd + '', 'confirmPwd': '' + newPwd + ''},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			 deferred.resolve(1);

	        }else{
	        	deferred.reject(-1);
	        }
    		
    	}, function(error){    		
    		deferred.reject(-1);
    	});	
		
		return deferred.promise;
	};
	
	this.regUser = function(userInfo){
		var deferred = $q.defer();
		//var regUserURL_ = regUserURL.replace("{agentId}", userInfo.superior);
		var regUserURL_ = regUserURL;
		var userName = (userInfo.userName == null || typeof userInfo.userName == 'undefined')?'':userInfo.userName;
		$http.post(regUserURL_,
				{'email': '' + (userInfo.email == null || typeof userInfo.email == 'undefined')?'':userInfo.email
					+ '', 'fundPwd': '' + userInfo.loginPwd 
					+ '', 'loginPwd': '' + userInfo.loginPwd
					+ '', 'phoneNum': '' + (userInfo.phoneNum == null || typeof userInfo.phoneNum == 'undefined')?'':userInfo.phoneNum
					+ '', 'platRebate': '' + userInfo.platRebate 
					+ '', 'qq': '' + (userInfo.qq == null || typeof userInfo.qq == 'undefined')?'':userInfo.qq
					+ '', 'realName': '' + (userInfo.realName == null || typeof userInfo.realName == 'undefined')?'':userInfo.realName
					+ '', 'userId': '' + (userInfo.userId == null || typeof userInfo.userId == 'undefined')?'':userInfo.userId
					+ '', 'userName': '' + userName
					+ '', 'userType': '' + userInfo.userType
					+ '', 'superior': '' + userInfo.superior
					+ '', 'wechat': '' + (userInfo.wechat == null || typeof userInfo.wechat == 'undefined')?'':userInfo.wechat + ''},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			 deferred.resolve(1);

	        }else{
	        	deferred.reject(-1);
	        }
    		
    	}, function(error){    		
    		deferred.reject(-1);
    	});	
		
		return deferred.promise;
	};
	
	this.querySysParams = function(){
		var deferred = $q.defer();
		
		$http.get(querySysParamsURL).then(function (res) {
			if(res.data.status == 1){
				//var userAcc = res.data.data;
				
				deferred.resolve(1);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.queryBank = function(){
		var deferred = $q.defer();
		
		$http.get(queryBankURL).then(function (res) {
			if(res.data.status == 1){
				deferred.resolve(res.data.data);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.queryBankCard = function(){
		var deferred = $q.defer();
		
		$http.get(queryBankCardURL).then(function (res) {
			if(res.data.status == 1){
				deferred.resolve(res.data.data);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	
	this.saveCard = function(curBank, cardNo){
		var deferred = $q.defer();
		
		$http.post(addBankCardURL,
				{'bankBranch': '' + curBank
					+ '', 'cardNum': '' + cardNo
					+ '', 'remark': ''},
    			{'Content-Type': 'application/json'}).then(function(res){
			if(res.data.status == 1){
				deferred.resolve(res.data.data);
			}else{
				deferred.reject(-1);
			}
	    }, function (err) {
	    	deferred.reject(-1);
	    });
		
		return deferred.promise;
	};
	
	this.changeUserCurrMarket = function(selCreditMarket){
		var deferred = $q.defer();
		
    	$http.post(
    			changeUserCurrMarketURL, 
    			{'creditMarket': '' + selCreditMarket.marketId},
    			{'Content-type': 'application/json'}
    			
        ).success(function (data, status, headers, config) {

            deferred.resolve(1);
        }).error(function (data, status, headers, config) {
            //$("#loginCodeImg").attr("src", get_code + Math.random());
            deferred.reject(-1);
        });
        
        return deferred.promise;
    }
}]);