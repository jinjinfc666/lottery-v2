var app = angular.module('wallet', []);

app.controller('depositCtrl', ["$scope", "$http", "walletService", "userInfoServ", function ($scope, $http, walletService, userInfoServ) {
	
	$("div.thirdPay_type li").on("click",function(){
		var tabType = $(this).attr("data-num");
		$("div.thirdPay_type li").removeClass('active');
		$(this).addClass('active');
		$("div.deposit_form").each(function(){
			var cType = $(this).attr("data-num");
			if(tabType == cType){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	});
	
	$scope.initDeposit = function(){
		userInfoServ.querySysParams().then(function(res){
			$scope.depositSysParams = res;
		});
		
		
		$scope.queryRechargeType();
	};
	
	$scope.queryRechargeType = function(){
		walletService.queryRechargeType().then(function(res){
			$scope.rechargeType = res;
		}, function(error){
			showToast("查询充值方式失败!!");
		});
	};
    
    
	$scope.queryPayChannel = function(payTypeClass){
		//payTypeClassId=1
		walletService.queryPayChannel(payTypeClass).then(function(res){
			$scope.payChannel = res;
		}, function(error){
			showToast("查询充值渠道失败!!");
		});
	};
	
	$scope.selRechargeType = function(){
		$scope.queryPayChannel($scope.curRechargeType);
	};
	
	$scope.selPayChannel = function(){
		$scope.currPayChannel;
		 for(var i=0;i<$scope.payChannel.length;i++){
			 if($scope.currPayChannel == $scope.payChannel[i].id){
				 $scope.currPayChannelMaxAmount = $scope.payChannel[i].maxAmount;
				 break;
			 }
	     }
	};
	
   
    //充值提交
    $scope.deposit = function () {

        var money = $scope.amount;
        var channelId=$scope.currPayChannel;
        var curRechargeType = $scope.curRechargeType;
        var maxDepositAmount = $scope.currPayChannelMaxAmount;


        if (money == "") {

            showToast("请输入充值金额");
            return;
        }

        if (!isZheng(money)) {

            showToast("请输入正确的金额");
            return;
        }


        if(parseFloat(money) <= 0){
            showToast("充值金额不能小于0");
            return;
        }
        if(parseFloat(money) > parseFloat(maxDepositAmount)){

            showToast("充值金额不能大于"+maxDepositAmount);
            return;
        }


        walletService.deposit(curRechargeType, channelId, money).then(function(res){
        	if(res.data_type == 1){
        		openWinImage(res.qr_code);
        	}else if(res.data_type == 5){
        		openWinURL(res.qr_code);
        	}
        }, function(error){
			showToast("充值失败!!");
		});
        
    };


}]).controller('withdrawCtrl', ["$scope", "$http", "walletService", "userInfoServ",  function ($scope, $http, walletService, userInfoServ) {

    //读取银行列表
   /* $http.get(getBankList).then(function (res) {

        $scope.bankList = res.data.data;

    }, function () {

    });*/
	$scope.initWithdraw = function(){
		var mainAcc = JSON.parse(sessionStorage.getItem("mainAcc"));
		var redPacketAcc = JSON.parse(sessionStorage.getItem("redPacketAcc"));
		
		$scope.mainAccId = mainAcc.id;
		$scope.redPacketAccId = redPacketAcc.id;
		$scope.walletId = mainAcc.id;
		$scope.mianBalance = mainAcc.balance;
		userInfoServ.queryBankCard().then(function(res){
    		$scope.bankCards = res;
    	},
    	function(err){
    		//console.log(err);
    		//showToast('注册用户失败！！');
    	});
		
	};
	
    $scope.withdraw = function () {

        var bankId = $scope.bankId;
        var walletId = $scope.walletId;
		
        if ($scope.money == undefined) {

            showToast("请输入提现金额");

        } else if (!isZheng($scope.money)) {

            showToast("请输入正确的提现金额");

        } else if (parseFloat($scope.money) < 100) {

            showToast("提现金额最少为100元");

        } else if (bankId == '') {

            showToast("请选择提现银行");

        } else if ($scope.drawPwd == undefined) {

            showToast("请输入提现密码");

        } else {
        	//bankId, walletId, password, amount
        	walletService.withdraw(bankId, walletId, $scope.drawPwd, $scope.money).then(function(res){
        		showToast("提款成功!!");
            }, function(error){
    			showToast("提款失败!!");
    		});
            
        }


    };


}])
.controller('transferCtrl', ["$scope", "$http", "$q", function ($scope, $http, $q) {

	$("div.flex_1 span.icon_transfer02").on("click",function(){
		
		$("div[name=page_content]").each(function(){
			$(this).toggle();
		});
	});
	
    $scope.outputAccBal = "";
    $scope.inputAccBal = "";
    $scope.inWallet = new Array();
    $scope.outWallet = new Array();
    
    

    //选择账户
    $scope.accountChange1 = function () {
        var acctId=$scope.gameAcct1;
        //clear the outWallet
        $scope.inWallet = new Array();
        $scope.inputAccBal = '';
        if(acctId!=''){
            $scope.setBalance("正在查询平台金额",acctId);
            $scope.accountChange(acctId).then(function(value){
            	$scope.outputAccBal = "余额: " + $scope.getBalance(acctId);
            });
            
            var selOutWallet = $scope.findWalletById(acctId);
            if(selOutWallet != undefined && selOutWallet.type.code=='M_CA'){
            	$scope.inWallet = $scope.gameAccount;
            }else{
            	$scope.inWallet = $scope.mainAccount;
            }
        }
    }

    //选择账户
    $scope.accountChange2=function () {
        var acctId=$scope.gameAcct2;
        if(acctId!='') {
            $scope.setBalance("正在查询平台金额", acctId);
            $scope.accountChange(acctId).then(function(value){
            	$scope.inputAccBal = "余额: " + $scope.getBalance(acctId);
            });
        }
    }

    //选择账户
    $scope.accountChange=function (accountId) {
    	var deferred = $q.defer();
        $http.get(getBalance, {params: {'listOfAcctIds': '' +accountId+ ''}}).then(function (res) {

            var bal=res.data.data['' + accountId + ''];

            $scope.setBalance(bal,accountId);
            
            deferred.resolve(res.data.code);
        }, function () {

            $scope.setBalance('查询金额失败',accountId);
            deferred.resolve(res.data.code);

        });
        
        return deferred.promise;
        
    };

    $scope.findWalletById = function(id){
    	for(var i=0;i<$scope.outWallet.length;i++){

            if($scope.outWallet[i].id == id){
            	return $scope.outWallet[i];
            }
    	}
    }
    //设置账户余额
    $scope.setBalance=function(bal,accountId){

        for(var i=0;i<$scope.outWallet.length;i++){

            if($scope.outWallet[i].id==accountId){

                if(!isNaN(bal)){
                    $scope.outWallet[i].balance=bal;
                    //$scope.gameAccount[i].t=$scope.gameAccount[i].product.code+"平台 ("+bal+"元)";
                }else{
                	$scope.outWallet[i].balance=0.0;
                    //$scope.gameAccount[i].t=$scope.gameAccount[i].product.code+"平台 ("+bal+")";
                }
                break;
            }
        }

    }

    //读取账户余额
    $scope.getBalance=function (accountId) {

        for(var i=0;i<$scope.outWallet.length;i++){

            if($scope.outWallet[i].id==accountId){

                $scope.gameBal=$scope.outWallet[i].balance;
                return $scope.gameBal;
            }
        }
    }

    $scope.isPlatWallet = function(id){
    	var wallet = $scope.findWalletById(id);
        if(wallet != undefined && wallet.type.code=='M_CA'){
        	return true;
        }
        
        return false;
    }
    
    //转账提交
    $scope.transferSubmit = function () {  //转账提交

        var outAcc = $scope.gameAcct1;
        var inAcc = $scope.gameAcct2;

        $scope.getBalance(outAcc);

        if(outAcc==undefined || inAcc == undefined){
                showToast("请选择游戏平台");
                return;

        }else if($scope.transMoney == undefined || isNaN($scope.transMoney) || $scope.transMoney < 0){

                showToast("请填写转账金额");
                return;

        }else if(!isNaN($scope.gameBal) && parseFloat($scope.transMoney)>parseFloat($scope.gameBal)){ 

                showToast("转出金额不足");
                return;
        }

        var money = $scope.transMoney;

        showLoading("转账中…");

        $http({
            method: 'post',
            url: memFundTransfer,
            data: $.param({'fromAcctId': '' + outAcc + '', 'toAcctId': '' + inAcc + '', 'amount': '' + money + ''}),
            headers: {'Content-type': 'application/x-www-form-urlencoded'}
        }).then(function (res) {

            hideLoading();
            if (res.data.code == 0) {

                showToast("转账成功");

            } else {

                showToast(sx.results[res.data.code]);
            }

        }, function () {
            hideLoading();
            showToast("转账失败");
        });

    };

}]).service('walletService', ["$http",'$q', function ($http, $q) {
	
	this.queryRechargeType = function(){
		var deferred = $q.defer();
    	
    	$http.get(queryRechargeTypeURL,
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			deferred.resolve(res.data.data);
	        }else{
	        	deferred.reject(res.data.status);
	        }
    		
    	}, function(error){
    		
    		deferred.reject(error);
    	});
    	
    	return deferred.promise;
	};
	
	this.queryPayChannel = function(payTypeClass){
		var deferred = $q.defer();
    	
    	$http.get(queryPayChannelURL,
    			{params: {payTypeClassId: payTypeClass}},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			deferred.resolve(res.data.data);
	        }else{
	        	deferred.reject(res.data.status);
	        }
    		
    	}, function(error){
    		
    		deferred.reject(error);
    	});
    	
    	return deferred.promise;
	};
	
	this.deposit = function(payTypeClass, payChannel, amount){
		var deferred = $q.defer();
		$http.post(depositURL,
				{'payType': '' + payTypeClass + '', 'payChannel': '' + payChannel + '', 'amount': '' + amount + ''},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			 deferred.resolve(res.data.data);

	        }else{
	        	deferred.reject(-1);
	        }
    		
    	}, function(error){    		
    		deferred.reject(-1);
    	});	
		
		return deferred.promise;
	};
	
	this.withdraw = function(bankId, walletId, password, amount){
		var deferred = $q.defer();
		$http.post(withdrawURL,
				{'bankId': '' + bankId + '', 'walletId': '' + walletId + '', 'password': '' + password + '', 'amount': '' + amount + ''},
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			 deferred.resolve(res.data.data);

	        }else{
	        	deferred.reject(-1);
	        }
    		
    	}, function(error){    		
    		deferred.reject(-1);
    	});	
		
		return deferred.promise;
	};
	/**
	 * query the all wallet including the platform wallet and the third part wallet
	 * 
	 * return :
	 * 			success : the array containing all the wallet
	 *          failed : the error code
	 */
	this.queryWallet = function(){
		var deferred = $q.defer();
		
		return deferred.promise;
	};
	
	/**
	 * inquire the balance of specified wallet
	 * 
	 * return :success : the balance
	 *         failed  : error code
	 */
    this.inquireBalance = function (walletId) {
    	var deferred = $q.defer();
        $http.get(getBalance, {params: {'listOfAcctIds': '' + walletId + ''}}).then(function (res) {

            var bal=res.data.data['' + walletId + ''];
            
            deferred.resolve(bal);
        }, function () {

            $scope.setBalance('查询金额失败',accountId);
            deferred.reject(-1);

        });
        
        return deferred.promise;
        
    };
    
    this.isPlatWallet = function(wallet){    	
        if(wallet != undefined && wallet.type.code=='M_CA'){
        	return true;
        }        
        return false;
    };
}]);
