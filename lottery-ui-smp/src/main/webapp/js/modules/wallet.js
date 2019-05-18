var app = angular.module('wallet', []);

app.controller('depositCtrl', ["$scope", "$http", function ($scope, $http) {
	
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
	
    //获取充值方式
    $http.get(getRechargeChannel).then(function (res) {

        if (res.data.code == 1) {

            $scope.rechargeChannel = res.data.data;


            //收集微信，支付宝，银联
            var wx = new Array();
            var al = new Array();
            var bk = new Array();
            var bkCode = new Array();
            var qq = new Array();

            for (var i = 0; i < $scope.rechargeChannel.length; i++) {

                var name = $scope.rechargeChannel[i].name;
                var channelId = $scope.rechargeChannel[i].channelRelationId;
                var clientType=$scope.rechargeChannel[i].clientType;

                if(clientType=='W' || clientType=='B'){

                    if (name.indexOf('WX')>-1) {

                        wx.push(channelId);

                    } else if (name.indexOf('AL')>-1) {

                        al.push(channelId);

                    } else if (name.indexOf('QQ')>-1) {

                        qq.push(channelId);

                    } else if (name=='BK') {

                        bk.push(channelId);

                    } else if (name=='BKCODE') {

                        bkCode.push(channelId);

                    }

                }

            }


            var payType = new Array();

            var wxItem = new Array();
            var alItem = new Array();
            var qqItem = new Array();
            var bkItem = new Array();
            var bkCodeItem = new Array();

            for (var i = 0; i < $scope.rechargeChannel.length; i++) {

                var clientType=$scope.rechargeChannel[i].clientType;

                if(clientType=='W' || clientType=='B') {

                    var item = {};
                    item.channelId = $scope.rechargeChannel[i].channelRelationId;
                    item.t = $scope.rechargeChannel[i].description;
                    item.val = $scope.rechargeChannel[i].name;

                    if (item.val.indexOf('WX') > -1) {

                        if (wx.length > 1) {

                            item.t = "微信" + (wx.indexOf(item.channelId) + 1);
                        }

                        wxItem.push(item);

                    } else if (item.val.indexOf('AL') > -1) {

                        if (al.length > 1) {

                            item.t = "支付宝" + (al.indexOf(item.channelId) + 1);
                        }

                        alItem.push(item);

                    } else if (item.val.indexOf('QQ') > -1) {

                        if (qq.length > 1) {

                            item.t = "QQ" + (qq.indexOf(item.channelId) + 1);
                        }

                        qqItem.push(item);

                    } else if (item.val == 'BK') {

                        if (bk.length > 1) {

                            item.t = "网银" + (bk.indexOf(item.channelId) + 1);
                        }

                        bkItem.push(item);

                    } else if (item.val == 'BKCODE') {

                        if (bkCode.length > 1) {

                            item.t = "网银扫码" + (bkCode.indexOf(item.channelId) + 1);
                        }

                        bkCodeItem.push(item);

                    }

                }

            }


            if (wxItem.length > 0) {

                var obj = {};
                obj.type = "微信支付";
                obj.cls = "";
                obj.payItem = wxItem;
                obj.img = "images/userhome/wechat.jpg";
                payType.push(obj);

            }

            if (alItem.length > 0) {

                var obj = {};
                obj.type = "支付宝";
                obj.cls = "";
                obj.payItem = alItem;
                obj.img = "images/userhome/wechat.jpg";
                payType.push(obj);

            }

            if (qqItem.length > 0) {

                var obj = {};
                obj.type = "QQ支付";
                obj.cls = "";
                obj.payItem = qqItem;
                obj.img = "images/userhome/qqpay.jpg";
                payType.push(obj);

            }

            if (bkItem.length > 0) {

                var obj = {};
                obj.type = "网银支付";
                obj.cls = "";
                obj.payItem = bkItem;
                obj.img = "images/userhome/mcpay.jpg";
                payType.push(obj);

            }

            if (bkCodeItem.length > 0) {

                var obj = {};
                obj.type = "银联扫码";
                obj.cls = "";
                obj.payItem = bkCodeItem;
                obj.img = "images/userhome/unionpay.jpg";
                payType.push(obj);

            }

            $scope.payType = payType;
            if($scope.payType.length>0){
                $scope.changeType(0);
            }

        }

    }, function () {


    });
    
    $scope.changeType=function (index) {

        for(var i=0;i<$scope.payType.length;i++){

            $scope.payType[i].cls='';
        }

        $scope.payType[index].cls='active';
        $scope.payItem=$scope.payType[index].payItem;

        $scope.curChannel=$scope.payItem[0].channelId;

        
    };


    //充值提交
    $scope.rechargeSubmit = function () {

        var money = $scope.amount;
        var channelId=$scope.curChannel;

        var data = $scope.rechargeChannel;
        var currentChannel;
        for (var i = 0; i < data.length; i++) {

            if (channelId == data[i].channelRelationId) {

                currentChannel = data[i];
                break;
            }

        }

        var type=currentChannel.name;


        if (money == "") {

            showToast("请输入充值金额");
            return;
        }

        if (!isZheng(money)) {

            showToast("请输入正确的金额");
            return;
        }


        if(parseFloat(money)<parseFloat(currentChannel.minDeposit)){

            showToast("充值金额不能小于"+currentChannel.minDeposit);
            return;
        }
        if(parseFloat(money)>parseFloat(currentChannel.maxDeposit)){

            showToast("充值金额不能大于"+currentChannel.maxDeposit);
            return;
        }


        $http({
            method: 'post',
            url: rechargeChannelSubmit,
            data: $.param({
                'channelRelationId': '' + channelId + '', 'amount': '' + money + '', 'payMethod': 'G',
                'type': '' + type + '', 'clientType': 'W', "p.code": '', "client": 3
            }),
            headers: {'Content-type': 'application/x-www-form-urlencoded'}
        }).then(function (res) {

            if (res.data.code == 0) {

                if(res.data.data.response!=''){

                    var url = decodeURIComponent(res.data.data.response);

                    var newTab = openWin("支付加载中，请稍候……");
                    newTab.location.href = url;

                }else{

                    showToast("支付失败");
                }

            }else{

                showToast("支付失败");
            }

        },function () {

            showToast("支付失败");
        });

    };


}]).controller('withdrawCtrl', ["$scope", "$http", function ($scope, $http) {

    //读取银行列表
    $http.get(getBankList).then(function (res) {

        $scope.bankList = res.data.data;

    }, function () {

    });

    $scope.withdrawSubmit = function () {

        var bankId = $('#bankId option:selected').val();

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

            $http({
                method: 'post',
                url: withdrawSubmit,
                data: $.param({
                    'amount': '' + $scope.money + '', 'bankAcct.id': '' + bankId + '',
                    'password': '' + $scope.drawPwd + '', 'smsEnabled': 'false'
                }),
                headers: {'Content-type': 'application/x-www-form-urlencoded'}
            }).then(function (res) {

                if (res.data.code == 0) {

                    showToast("提现成功");

                } else {

                    showToast(sx.results[res.data.code]);
                }

            }, function () {

                console.log("修改失败");

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
	/**
	 * query the all wallet including the platform wallet and the third part wallet
	 * 
	 * return :
	 * 			success : the array containing all the wallet
	 *          failed : the error code
	 */
	this.queryWallet = function(){
		var deferred = $q.defer();
		/*$http.get(getMemAccount, {cache: true}).then(function (res) {
	        var allAccount = commonService.pingAccount(res.data.data);
	        
	        deferred.resolve(allAccount);
	    }, function () {
	    	deferred.reject(-1);
	    });*/
		
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
