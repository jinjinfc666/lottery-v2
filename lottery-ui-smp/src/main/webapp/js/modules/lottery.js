/**
 * 
 */
var slotModule = angular.module('lottery', []);

app.controller('lotteryCtrl', ["$scope", "$http","$stateParams", "$interval", "playgameService", "$state" , "userInfoServ", "hisRecService", "sysCodeTranslateFactory", "expertService", "issueService", function ($scope, $http, $stateParams, $interval, playgameService, $state , userInfoServ , hisRecService, sysCodeTranslateFactory, expertService, issueService) {

	    //通过游戏名称搜索
	    $scope.searchByName = function () {

	        if($scope.gameTitle == undefined || $scope.gameTitle == ''){

	            showToast("请输入游戏关键词");

	        }else{

	            $scope.getGameByTitle($scope.gameTitle,false);

	        }

	    };



	    $scope.displayIssueRec = function(){
			if(typeof $scope.isIssueRecOpen == 'undefined'
				|| $scope.isIssueRecOpen == null){
				$scope.isIssueRecOpen = false;
				
			}else{
				$scope.isIssueRecOpen = !$scope.isIssueRecOpen;
			}
			
			if($scope.isIssueRecOpen){
				$scope.issueRecImg = 'images/triangel_open.png';
			}else{
				$scope.issueRecImg = 'images/triangel_close.png';
			}
			
		};
	    
	    /**
	     * query the hot games
	     */
	    $scope.queryHotGame = function(){
	    	$scope.hotGames = new Array();
	    	
			playgameService.queryHotGame().then(function(res){
				hotGames = res;
			}, function(error){
				showToast("查询热门游戏失败!!");
			});
		};
		
		 /**
	     * query the hot games
	     */
	    $scope.queryLotterys = function(){
	    	$scope.lotterys = new Array();
	    	$scope.isMenuOpen = false;
	    	$scope.isMenuClose = true;
	    	
			playgameService.queryLotterys().then(function(res){
				if(res != null && res.length > 0){
					
				}
				
				$scope.lotterys = res;
			}, function(error){
				showToast("查询热门游戏失败!!");
			});
		};
		
		$scope.dropMenuToggle = function(){
			$scope.isMenuOpen = !$scope.isMenuOpen;
	    	$scope.isMenuClose = !$scope.isMenuClose;
		};
		
		$scope.clickLottery = function(lotteryType){
			/*var alink = $('#a_'+lotteryType);
			$('#a_'+lotteryType).click();*/
			var url = "lottery_betting_" + lotteryType;
			$state.go(url);
		};
		
		
		var timer = null;
		var bettingRecTimer = null;
		var queryMemeberTimer = null;
		var queryIssueRetGroupTimer = null;
		$scope.queryBulletinBoard = function(lotteryType){
			$scope.issueRecImg = 'images/triangel_close.png';
			sessionStorage.removeItem("bulletinBoard");
			$scope.downCounter = null;
			$scope.downCounter_ = 0;
			//$scope.bitIndex_ = 0;
			timer = $interval(function () {
				if($scope.downCounter_ > 0){
					CountDown($scope.downCounter_);
				}
				
				playgameService.queryBulletinBoard(lotteryType).then(function(res){
					//$scope.bulletinBoard = res;
					if(res != null
							&& res.currIssue != null
							&& res.currIssue.downCounter != null){
						if(typeof $scope.bulletinBoard == 'undefined'
							|| $scope.bulletinBoard == null
							|| typeof $scope.bulletinBoard.currIssue == 'undefined'
							|| $scope.bulletinBoard.currIssue == null
							|| $scope.bulletinBoard.currIssue.id != res.currIssue.id){
							
							CountDown(res.currIssue.downCounter);
							
						}
						
					}
					
					if(res != null 
							&& res.lastIssue != null
							&& res.lastIssue.retNum != null){
							var lastIssueRetNumArray = new Array();
							var lastIssueRetNum = res.lastIssue.retNum;
							
							var lastIssueRetNumArrayStr = lastIssueRetNum.split(',');
							for(var i = 0; i< lastIssueRetNumArrayStr.length; i++){
								lastIssueRetNumArray.push(parseInt(lastIssueRetNumArrayStr[i]));
							}

							$scope.lastIssueRetNum = lastIssueRetNumArray;
					}else{
						$scope.lastIssueRetNum = new Array();
					}
					
					$scope.bulletinBoard = res;
					
					sessionStorage.setItem("bulletinBoard", JSON.stringify(res));
				});
				
			//},1000,5);
			},1000);
			
			
			bettingRecTimer = $interval(function () {				
				$scope.queryBettingRecBrief(lotteryType, 1);
				$scope.queryIssueRecBrief(lotteryType, 1);
			},120000);
			
			queryMemeberTimer = $interval(function () {				
				$scope.queryMemberInfo();
			},30000);

						
			$scope.$on('$destroy',function(){
		        $interval.cancel(timer);
		        $interval.cancel(bettingRecTimer);
		        $interval.cancel(queryMemeberTimer);
		    })
		};
		
		
		$scope.queryIssueRetGroup = function(){
			
			queryIssueRetGroupTimer = $interval(function(){
				playgameService.queryBulletinBoard("xyft").then(function(res){
										
					if(res != null 
							&& res.lastIssue != null
							&& res.lastIssue.retNum != null){
							var lastIssueRetNumArray = new Array();
							var lastIssueRetNum = res.lastIssue.retNum;
							
							var lastIssueRetNumArrayStr = lastIssueRetNum.split(',');
							for(var i = 0; i< lastIssueRetNumArrayStr.length; i++){
								lastIssueRetNumArray.push(parseInt(lastIssueRetNumArrayStr[i]));
							}

							$scope.lastIssueRetNumXyft = lastIssueRetNumArray;
					}else{
						$scope.lastIssueRetNumXyft = new Array();
					}
					
				});
				
				playgameService.queryBulletinBoard("cqssc").then(function(res){
					
					if(res != null 
							&& res.lastIssue != null
							&& res.lastIssue.retNum != null){
							var lastIssueRetNumArray = new Array();
							var lastIssueRetNum = res.lastIssue.retNum;
							
							var lastIssueRetNumArrayStr = lastIssueRetNum.split(',');
							for(var i = 0; i< lastIssueRetNumArrayStr.length; i++){
								lastIssueRetNumArray.push(parseInt(lastIssueRetNumArrayStr[i]));
							}

							$scope.lastIssueRetNumCqssc = lastIssueRetNumArray;
					}else{
						$scope.lastIssueRetNumCqssc = new Array();
					}
					
				});
				
				playgameService.queryBulletinBoard("bjpk10").then(function(res){
					
					if(res != null 
							&& res.lastIssue != null
							&& res.lastIssue.retNum != null){
							var lastIssueRetNumArray = new Array();
							var lastIssueRetNum = res.lastIssue.retNum;
							
							var lastIssueRetNumArrayStr = lastIssueRetNum.split(',');
							for(var i = 0; i< lastIssueRetNumArrayStr.length; i++){
								lastIssueRetNumArray.push(parseInt(lastIssueRetNumArrayStr[i]));
							}

							$scope.lastIssueRetNumBjpk10 = lastIssueRetNumArray;
					}else{
						$scope.lastIssueRetNumBjpk10 = new Array();
					}
					
				});
				
				playgameService.queryBulletinBoard("xjssc").then(function(res){
					
					if(res != null 
							&& res.lastIssue != null
							&& res.lastIssue.retNum != null){
							var lastIssueRetNumArray = new Array();
							var lastIssueRetNum = res.lastIssue.retNum;
							
							var lastIssueRetNumArrayStr = lastIssueRetNum.split(',');
							for(var i = 0; i< lastIssueRetNumArrayStr.length; i++){
								lastIssueRetNumArray.push(parseInt(lastIssueRetNumArrayStr[i]));
							}

							$scope.lastIssueRetNumXjssc = lastIssueRetNumArray;
					}else{
						$scope.lastIssueRetNumXjssc = new Array();
					}
					
				});
				
			}, 30000)
			
			$scope.$on('$destroy',function(){
		        $interval.cancel(queryIssueRetGroupTimer);
		    })
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
		
		function CountDown(downCounter) {
			console.log('downCounter:::::' + downCounter +"  currentTime::::"+ new Date().getTime());
			var downCounter_ = downCounter;
			downCounter_--;
			
			$scope.downCounter_ = downCounter_;
			
		    if(downCounter_ == 0){
		    	//$interval.cancel(timer);
		    	var downCounterVal = new Array();
		    	downCounterVal.push("0");
		    	downCounterVal.push("0");
		    	downCounterVal.push(":");
		    	downCounterVal.push("0");
		    	downCounterVal.push("0");
		    	$scope.downCounter = downCounterVal;
		    }else{
		    	var downCounterVal = new Array();
				var minutes = parseInt(downCounter_ / 60) ;
				var seconds = downCounter_ % 60;
				var minutes_ = null;
				var seconds_ = null;
				
				if(minutes < 10){
					minutes_ = '0'+minutes;
				}else{
					minutes_ = ''+minutes;
				}
				
				if(seconds < 10){
					seconds_ = '0'+seconds;
				}else{
					seconds_ = ''+seconds;
				}
				
				downCounterVal.push(minutes_.substring(0,1));
				downCounterVal.push(minutes_.substring(1));
				downCounterVal.push(":");
				downCounterVal.push(seconds_.substring(0,1));
				downCounterVal.push(seconds_.substring(1));
				
				$scope.downCounter = downCounterVal;
		    }
		    
		}
		
		var cqsscOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		var cqsscHeaderOptions = ['万位', '千位', '百位', '十位', '个位'];
		var cqsscDwdBettingValOptions = ['bettingVal,,,,',',bettingVal,,,',',,bettingVal,,',',,,bettingVal,',',,,,bettingVal'];
		
		var xjsscOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		var xjsscHeaderOptions = ['万位', '千位', '百位', '十位', '个位'];
		var xjsscDwdBettingValOptions = ['bettingVal,,,,',',bettingVal,,,',',,bettingVal,,',',,,bettingVal,',',,,,bettingVal'];
		
		var fivefcOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		var fivefcHeaderOptions = ['万位', '千位', '百位', '十位', '个位'];
		var fivefcDwdBettingValOptions = ['bettingVal,,,,',',bettingVal,,,',',,bettingVal,,',',,,bettingVal,',',,,,bettingVal'];
		
		
		var xyftOptions = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'];
		var xyftHeaderOptions = ['冠军', '亚军', '季军', '第四名', '第五名', '第六名', '第七名', '第八名', '第九名', '第十名'];
		var xyftDwdBettingValOptions = ['bettingVal,,,,,,,,,',',bettingVal,,,,,,,,',',,bettingVal,,,,,,,',',,,bettingVal,,,,,,',',,,,bettingVal,,,,,',',,,,,bettingVal,,,,',',,,,,,bettingVal,,,',',,,,,,,bettingVal,,',',,,,,,,,bettingVal,',',,,,,,,,,bettingVal'];
		
		var bjpk10Options = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'];
		var bjpk10HeaderOptions = ['冠军', '亚军', '季军', '第四名', '第五名', '第六名', '第七名', '第八名', '第九名', '第十名'];
		var bjpk10DwdBettingValOptions = ['bettingVal,,,,,,,,,',',bettingVal,,,,,,,,',',,bettingVal,,,,,,,',',,,bettingVal,,,,,,',',,,,bettingVal,,,,,',',,,,,bettingVal,,,,',',,,,,,bettingVal,,,',',,,,,,,bettingVal,,',',,,,,,,,bettingVal,',',,,,,,,,,bettingVal'];
		
		var yfbOptions = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'];
		var yfbHeaderOptions = ['冠军', '亚军', '季军', '第四名', '第五名', '第六名', '第七名', '第八名', '第九名', '第十名'];
		var yfbDwdBettingValOptions = ['bettingVal,,,,,,,,,',',bettingVal,,,,,,,,',',,bettingVal,,,,,,,',',,,bettingVal,,,,,,',',,,,bettingVal,,,,,',',,,,,bettingVal,,,,',',,,,,,bettingVal,,,',',,,,,,,bettingVal,,',',,,,,,,,bettingVal,',',,,,,,,,,bettingVal'];
		
		var tc3Options = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		var tc3HeaderOptions = ['百位', '十位', '个位'];
		var tc3DwdBettingValOptions = ['bettingVal,,',',bettingVal,',',,bettingVal'];
		
//		$scope.initBettingNum = function(lotteryType, bitCounter){
//			playgameService.queryMainPs(lotteryType, playTypeParams).then(function(ret){
//				$scope.mainPs = ret.mainPs;
//				$scope.hs = ret.hs;
//				$scope.dwd = ret.dwd;
//			});
//				
//			sessionStorage.setItem(bitNumBuffeKey, JSON.stringify($scope.bitNumArray));
//		};
		
		$scope.queryMainPs = function(lotteryType){
			playgameService.queryMainPs(lotteryType).then(function(ret){
				$scope.mainPs = ret.mainPs;
				$scope.hs = ret.hs;
				$scope.dwd = ret.dwd;
			});
		};
		
		$scope.queryYzps = function(lotteryType){
			playgameService.queryYzps(lotteryType).then(function(ret){
				$scope.mainPs = ret.mainPs;
				$scope.bwsz = ret.bwsz;
				$scope.swsz = ret.swsz;
				$scope.gwsz = ret.gwsz;
			});
		};
		
		$scope.queryEzdw = function(lotteryType, numType){
			playgameService.queryEzdw(lotteryType, numType).then(function(ret){
				$scope.ezdw = ret.ezdw;
			});
		};
		
		$scope.querySzdw = function(lotteryType){
			playgameService.querySzdw(lotteryType).then(function(ret){
				$scope.szdw = ret.szdw;
			});
		};
		
		$scope.queryEzhs = function(lotteryType, numType){
			playgameService.queryEzhs(lotteryType, numType).then(function(ret){
				$scope.ezhs = ret.ezhs;
			});
		};		
		
		$scope.queryEzzh = function(lotteryType){
			playgameService.queryEzzh(lotteryType).then(function(ret){
				$scope.ezzh = ret.ezzh;
			});
		};
		
		$scope.querySzhs = function(lotteryType){
			playgameService.querySzhs(lotteryType).then(function(ret){
				$scope.szhs = ret.szhs;
			});
		};
		
		$scope.querySzzh = function(lotteryType){
			playgameService.querySzzh(lotteryType).then(function(ret){
				$scope.szzh = ret.szzh;
			});
		};
		
		$scope.queryZs = function(lotteryType){
			playgameService.queryZs(lotteryType).then(function(ret){
				$scope.zs = ret.zs;
			});
		};
		
		$scope.initBettingNumAuth = function(lotteryType, bitCounter){
			$scope.bitNumArray = new Array();
			var headerOptions = null;
			var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
			var bitNumBuffeKey = 'bitNumArray_' + lotteryType + "_" + userInfo.userName;
			
			if(lotteryType == 'cqssc'){
				headerOptions = cqsscHeaderOptions;
				
			}else if(lotteryType == 'xjssc'){
				headerOptions = xjsscHeaderOptions;
				
			}
			else if(lotteryType == 'bjpk10'){
				headerOptions = bjpk10HeaderOptions;
				
			}else if(lotteryType == 'xyft'){
				headerOptions = xyftHeaderOptions;
				
			}else if(lotteryType == '5fc'){
				headerOptions = fivefcHeaderOptions;
				
			}else if(lotteryType == 'yfb'){
				headerOptions = yfbHeaderOptions;
			}else if(lotteryType == 'tc3'){
				headerOptions = tc3HeaderOptions;
			}
			
			if(sessionStorage.getItem(bitNumBuffeKey) != null){
				$scope.bitNumArray = JSON.parse(sessionStorage.getItem(bitNumBuffeKey));
			}else{
				for(var i = 0; i < bitCounter; i++){
					var header = headerOptions[i];
					var bitNum = new Array();
					var bitEle = {};
					bitEle.header = header;
					bitEle.bitNum = bitNum;
					bitEle.bitIndex = i;
					$scope.bitNumArray.push(bitEle);
					
					//console.log('bit index ::' + i);
					$scope.produceOneBit(i, lotteryType, bitNumBuffeKey);
				}				
			}
			
			
		};
		
		
		$scope.produceOneBit = function(bitNum, lotteryType, bitNumBuffeKey){
			var bettingNumers = new Array();
			var bettingNumDx = new Array();
			var bettingNumDs = new Array();
			var bettingNumDwd = new Array();
			var isDx = false;
			var isDs = false;
			var isDwd = false;
			var bitName = null;
			var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
			
			if(bitNum == 0){
				bitName = 'dym';
			}else if(bitNum == 1){
				bitName = 'dem';
			}else if(bitNum == 2){
				bitName = 'dsm';
			}else if(bitNum == 3){
				bitName = 'dsim';
			}else if(bitNum == 4){
				bitName = 'dwm';
			}else if(bitNum == 5){
				bitName = 'dlm';
			}else if(bitNum == 6){
				bitName = 'dqm';
			}else if(bitNum == 7){
				bitName = 'dbm';
			}else if(bitNum == 8){
				bitName = 'djm';
			}else if(bitNum == 9){
				bitName = 'dshim';
			}
			
			var playTypeParams = {};
			playTypeParams.bitName = bitName;
			playTypeParams.bitNum = bitNum;
			playgameService.queryPlayTypes(lotteryType, playTypeParams).then(function(playTypeRet){
				for(var i = 0;i < playTypeRet.playTypes.length; i++){
					var playType = playTypeRet.playTypes[i];
					if(playType.classification.indexOf('dwd') >= 0){
						isDwd = true;
					}
					
					if(playType.classification.indexOf('dx') >= 0){
						isDx = true;
					}
					
					if(playType.classification.indexOf('ds') >= 0){
						isDs = true;
					}
				}
				
				if(!isDwd){
					for(var i = 0; i< lotteryOptions.length; i++){
						var bettingNum = {};
						bettingNum.playTypeId = -1;
						bettingNum.playTypeName = '';
						bettingNum.bettingNumKey = ''+lotteryOptions[i];
						bettingNum.bettingNumVal = lotteryOptions[i];
						bettingNum.prizeRate = '--';
						bettingNum.isSm = false;
						bettingNum.vClass = "{'ng-scope':true}";
						bettingNumDwd.push(bettingNum);
					}
					
					isDwd = !isDwd;
				}
				
				if(!isDx){
					var bettingNumBig = {};
					bettingNumBig.playTypeId = -1;
					bettingNumBig.playTypeName = 'dx|大小/dym|第一名';
					bettingNumBig.bettingNumKey = '大';
					bettingNumBig.bettingNumVal = "01";
					bettingNumBig.prizeRate = '--';
					bettingNumBig.isSm = true;
					bettingNumBig.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
					bettingNumDx.push(bettingNumBig);
					
					var bettingNumSmall = {};
					bettingNumSmall.playTypeId = -1;
					bettingNumSmall.playTypeName = 'dx|大小/dym|第一名';
					bettingNumSmall.bettingNumKey = '小';
					bettingNumSmall.bettingNumVal = "00";
					bettingNumSmall.prizeRate = '--';
					bettingNumSmall.isSm = true;
					bettingNumSmall.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
					bettingNumDx.push(bettingNumSmall);
					
					isDx = !isDx;
				}
				
				if(!isDs){
					var bettingNumD = {};
					bettingNumD.playTypeId = -1;
					bettingNumD.playTypeName = '';
					bettingNumD.bettingNumKey = '单';
					bettingNumD.bettingNumVal = "01";
					bettingNumD.prizeRate = '--';
					bettingNumD.isSm = true;
					bettingNumD.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
					bettingNumDs.push(bettingNumD);
					
					var bettingNumS = {};
					bettingNumS.playTypeId = -1;
					bettingNumS.playTypeName = '';
					bettingNumS.bettingNumKey = '双';
					bettingNumS.bettingNumVal = "00";
					bettingNumS.prizeRate = '--';
					bettingNumS.isSm = true;
					bettingNumS.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
					bettingNumDs.push(bettingNumS);	
					
					isDs = !isDs;
				}
				for(var i = 0;i < playTypeRet.playTypes.length; i++){
					var playType = playTypeRet.playTypes[i];
					if(playType.classification.indexOf('dwd|') >= 0 
							|| playType.classification.indexOf('dx|') >= 0
							|| playType.classification.indexOf('ds|') >= 0){
						var prizeRateKey = '';
						if(playType.classification.indexOf('dwd|') >= 0){
							prizeRateKey = 'prizeRate_digital_' + lotteryType + "_" + userInfo.userName;
						}else if(playType.classification.indexOf('dx|') >= 0){
							prizeRateKey = 'prizeRate_dx_' + lotteryType + "_" + userInfo.userName;
						}else if(playType.classification.indexOf('ds|') >= 0){							
							prizeRateKey = 'prizeRate_ds_' + lotteryType + "_" + userInfo.userName;
						}
						
						playgameService.queryPrizeRate(lotteryType,  
															playType, 
															playTypeRet.bitNum, 
															playTypeRet.bitName,
															prizeRateKey).then(function(ret){
							var playType = ret.playType;
							var prizeRate = ret.prizeRate;
							var bitName_ = ret.bitName;
							var bitNum_ = ret.bitNum;
							var prizeRateKey_ = ret.prizeRateKey;
							
							sessionStorage.setItem(prizeRateKey_, prizeRate);
							//console.log('current play type :: ' + playType.classification);
							if(playType.classification.indexOf('dwd|') >= 0 
									&& isDwd){
								var lotteryOptions = null;
								var dwdBettingValOptions = null;
								
								bettingNumDwd = new Array();
								
								if(lotteryType == 'cqssc'){
									lotteryOptions = cqsscOptions;
									dwdBettingValOptions = cqsscDwdBettingValOptions;
								}else if(lotteryType == 'xjssc'){
									lotteryOptions = xjsscOptions;
									dwdBettingValOptions = xjsscDwdBettingValOptions;
								}
								else if(lotteryType == 'bjpk10'){
									lotteryOptions = bjpk10Options;
									dwdBettingValOptions = bjpk10DwdBettingValOptions;
								}else if(lotteryType == 'xyft'){
									lotteryOptions = xyftOptions;
									dwdBettingValOptions = xyftDwdBettingValOptions;
								}else if(lotteryType == '5fc'){
									lotteryOptions = fivefcOptions;
									dwdBettingValOptions = fivefcDwdBettingValOptions;
								}else if(lotteryType == 'yfb'){
									lotteryOptions = yfbOptions;
									dwdBettingValOptions = yfbDwdBettingValOptions;
								}else if(lotteryType == 'tc3'){
									lotteryOptions = tc3Options;
									dwdBettingValOptions = tc3DwdBettingValOptions;
								}
								
								for(var i = 0; i< lotteryOptions.length; i++){
									var bettingNum = {};
									bettingNum.playTypeId = playType.id;
									bettingNum.playTypeName = playType.classification;
									bettingNum.bettingNumKey = ''+lotteryOptions[i];
									bettingNum.bettingNumVal = dwdBettingValOptions[bitNum_].replace('bettingVal', lotteryOptions[i]);
									bettingNum.prizeRate = prizeRate;
									bettingNum.isSm = false;
									bettingNum.vClass = "{'ng-scope':true}";
									bettingNumDwd.push(bettingNum);
								}
								
								isDwd = !isDwd;
							}
							
							if(playType.classification.indexOf('dx|') >= 0
									&& playType.classification.indexOf(bitName_) >= 0
									&& isDx){																
								bettingNumDx = new Array();
								
								var bettingNumBig = {};
								bettingNumBig.playTypeId = playType.id;
								bettingNumBig.playTypeName = playType.classification;
								bettingNumBig.bettingNumKey = '大';
								bettingNumBig.bettingNumVal = "01";
								bettingNumBig.prizeRate = prizeRate;
								bettingNumBig.isSm = true;
								bettingNumBig.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDx.push(bettingNumBig);
								
								var bettingNumSmall = {};
								bettingNumSmall.playTypeId = playType.id;
								bettingNumSmall.playTypeName = playType.classification;
								bettingNumSmall.bettingNumKey = '小';
								bettingNumSmall.bettingNumVal = "00";
								bettingNumSmall.prizeRate = prizeRate;
								bettingNumSmall.isSm = true;
								bettingNumSmall.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDx.push(bettingNumSmall);
								
								isDx = !isDx;
								
								//console.log('play type dx---count :: ' + bettingNumDx.length);
							}
							
							if(playType.classification.indexOf('ds|') >= 0
									&& playType.classification.indexOf(bitName_) >= 0
									&& isDs){
								bettingNumDs = new Array();
								var bettingNumD = {};
								bettingNumD.playTypeId = playType.id;
								bettingNumD.playTypeName = playType.classification;
								bettingNumD.bettingNumKey = '单';
								bettingNumD.bettingNumVal = "01";
								bettingNumD.prizeRate = prizeRate;
								bettingNumD.isSm = true;
								bettingNumD.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDs.push(bettingNumD);
								
								var bettingNumS = {};
								bettingNumS.playTypeId = playType.id;
								bettingNumS.playTypeName = playType.classification;
								bettingNumS.bettingNumKey = '双';
								bettingNumS.bettingNumVal = "00";
								bettingNumS.prizeRate = prizeRate;
								bettingNumS.isSm = true;
								bettingNumS.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDs.push(bettingNumS);	
								
								isDs = !isDs;
								
								//console.log('play type ds---count :: ' + bettingNumDs.length);
							}
							
							var newNumbers = new Array();
							
							if(bettingNumDx.length > 0){
								$scope.bitNumArray[bitNum_].bitNum = bettingNumDx.concat($scope.bitNumArray[bitNum_].bitNum);
								//console.log('bit index :: ' + bitNum_ + '   merge dx to newNumbers---count :: ' + $scope.bitNumArray[bitNum_].bitNum.length);
							}
							
							if(bettingNumDs.length > 0){
								if($scope.bitNumArray[bitNum_].bitNum.length > 0
										&& $scope.bitNumArray[bitNum_].bitNum[0].playTypeName.indexOf('dx') >= 0){
									newNumbers.push($scope.bitNumArray[bitNum_].bitNum[0]);
									newNumbers.push($scope.bitNumArray[bitNum_].bitNum[1]);
									
									newNumbers.push(bettingNumDs[0]);
									newNumbers.push(bettingNumDs[1]);
									
									if($scope.bitNumArray[bitNum_].bitNum.length > 2){
										for(var ii = 2; ii < $scope.bitNumArray[bitNum_].bitNum.length; ii++){
											newNumbers.push($scope.bitNumArray[bitNum_].bitNum[ii]);
										}
									}
									
									$scope.bitNumArray[bitNum_].bitNum = newNumbers;
								}else{
									$scope.bitNumArray[bitNum_].bitNum = bettingNumDs.concat($scope.bitNumArray[bitNum_].bitNum);
								}	
								
								//console.log('bit index :: ' + bitNum_ + '  merge ds to newNumbers---count :: ' + $scope.bitNumArray[bitNum_].bitNum.length);
							}
							
							if(bettingNumDwd.length > 0){
								$scope.bitNumArray[bitNum_].bitNum = $scope.bitNumArray[bitNum_].bitNum.concat(bettingNumDwd);
								//console.log('bit index :: ' + bitNum_ + '  merge dwd to newNumbers---count :: ' + $scope.bitNumArray[bitNum_].bitNum.length);
							}
							
							
							sessionStorage.setItem(bitNumBuffeKey, JSON.stringify($scope.bitNumArray));
							
							bettingNumDx = new Array();
							bettingNumDs = new Array();
							bettingNumDwd = new Array();
						},
						function(prizeRet){
							if(prizeRet.playType.classification.indexOf('dwd') >= 0
									&& isDwd){
								var lotteryOptions = null;
								bettingNumDwd = new Array();
								
								if(lotteryType == 'cqssc'){
									lotteryOptions = cqsscOptions;
									
								}else if(lotteryType == 'bjpk10'){
									lotteryOptions = bjpk10Options;
									
								}else if(lotteryType == 'xyft'){
									lotteryOptions = xyftOptions;
									
								}else if(lotteryType == '5fc'){
									lotteryOptions = fivefcOptions;
									
								}else if(lotteryType == 'yfb'){
									headerOptions = yfbHeaderOptions;
									lotteryOptions = yfbOptions;
								}else if(lotteryType == 'tc3'){
									headerOptions = tc3HeaderOptions;
									lotteryOptions = tc3Options;
								}
								
								for(var i = 0; i< lotteryOptions.length; i++){
									var bettingNum = {};
									bettingNum.playTypeId = -1;
									bettingNum.playTypeName = '';
									bettingNum.bettingNumKey = ''+lotteryOptions[i];
									bettingNum.bettingNumVal = lotteryOptions[i];
									bettingNum.prizeRate = '--';
									bettingNum.isSm = true;
									bettingNum.vClass = "{'ng-scope':true}";
									bettingNumDwd.push(bettingNum);
								}	
								
								isDwd = !isDwd;
							}
							/*ret.bitNum = bitNum;
					    	ret.playType = playType;*/
							if(prizeRet.playType.classification.indexOf('dx|') >= 0
									&& prizeRet.playType.classification.indexOf(prizeRet.bitName) >= 0
									&& isDx){
								var bettingNumBig = {};
								bettingNumDx = new Array();
								
								bettingNumBig.playTypeId = prizeRet.playType.id;
								bettingNumBig.playTypeName = prizeRet.playType.classification;
								bettingNumBig.bettingNumKey = '大';
								bettingNumBig.bettingNumVal = "01";
								bettingNumBig.prizeRate = '--';
								bettingNumBig.isSm = true;
								bettingNumBig.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDx.push(bettingNumBig);
								
								var bettingNumSmall = {};
								bettingNumSmall.playTypeId = prizeRet.playType.id;
								bettingNumSmall.playTypeName = prizeRet.playType.classification;
								bettingNumSmall.bettingNumKey = '小';
								bettingNumSmall.bettingNumVal = "00";
								bettingNumSmall.prizeRate = '--';
								bettingNumSmall.isSm = true;
								bettingNumSmall.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDx.push(bettingNumSmall);	
								
								isDx = !isDx;
							}
							
							if(prizeRet.playType.classification.indexOf('ds|') >= 0
									&& prizeRet.playType.classification.indexOf(prizeRet.bitName) >= 0
									&& isDs){
								var bettingNumD = {};
								bettingNumDs = new Array();
								
								bettingNumD.playTypeId = prizeRet.playType.id;
								bettingNumD.playTypeName = prizeRet.playType.classification;
								bettingNumD.bettingNumKey = '单';
								bettingNumD.bettingNumVal = "01";
								bettingNumD.prizeRate = '--';
								bettingNumD.isSm = true;
								bettingNumD.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDs.push(bettingNumD);
								
								var bettingNumS = {};
								bettingNumS.playTypeId = prizeRet.playType.id;
								bettingNumS.playTypeName = prizeRet.playType.classification;
								bettingNumS.bettingNumKey = '双';
								bettingNumS.bettingNumVal = "00";
								bettingNumS.prizeRate = '--';
								bettingNumS.isSm = true;
								bettingNumS.vClass = "{'ng-scope':true,'displaySm':isSmDisplay}";
								bettingNumDs.push(bettingNumS);		
								
								isDs = isDs;
							}
							
							var newNumbers = new Array();
							
							if(bettingNumDx.length > 0){
								$scope.bitNumArray[prizeRet.bitNum].bitNum = bettingNumDx.concat($scope.bitNumArray[prizeRet.bitNum].bitNum);
								//console.log('bit index :: ' + prizeRet.bitNum + '   merge dx to newNumbers---count :: ' + $scope.bitNumArray[prizeRet.bitNum].bitNum.length);
							}
							
							if(bettingNumDs.length > 0){
								if($scope.bitNumArray[prizeRet.bitNum].bitNum.length > 0
										&& $scope.bitNumArray[prizeRet.bitNum].bitNum[0].playTypeName.indexOf('dx') >= 0){
									newNumbers.push($scope.bitNumArray[prizeRet.bitNum].bitNum[0]);
									newNumbers.push($scope.bitNumArray[prizeRet.bitNum].bitNum[1]);
									
									newNumbers.push(bettingNumDs[0]);
									newNumbers.push(bettingNumDs[1]);
									
									if($scope.bitNumArray[prizeRet.bitNum].bitNum.length > 2){
										for(var ii = 2; ii < $scope.bitNumArray[prizeRet.bitNum].bitNum.length; ii++){
											newNumbers.push($scope.bitNumArray[prizeRet.bitNum].bitNum[ii]);
										}
									}
									
									$scope.bitNumArray[prizeRet.bitNum].bitNum = newNumbers;
								}else{
									$scope.bitNumArray[prizeRet.bitNum].bitNum = bettingNumDs.concat($scope.bitNumArray[prizeRet.bitNum].bitNum);
								}	
								
								//console.log('bit index :: ' + prizeRet.bitNum + '  merge ds to newNumbers---count :: ' + $scope.bitNumArray[prizeRet.bitNum].bitNum.length);
							}
							
							if(bettingNumDwd.length > 0){
								$scope.bitNumArray[prizeRet.bitNum].bitNum = $scope.bitNumArray[prizeRet.bitNum].bitNum.concat(bettingNumDwd);
								//console.log('bit index :: ' + prizeRet.bitNum + '  merge dwd to newNumbers---count :: ' + $scope.bitNumArray[prizeRet.bitNum].bitNum.length);
							}
							
							bettingNumDx = new Array();
							bettingNumDs = new Array();
							bettingNumDwd = new Array();
						});
						
					}
				}
				
				
			});
		}
		
		
		$scope.selNum = function(myevent){
			var selFlag = null;
			var dataBit = null;
			var dataIndex = null;
			var presetMoney = null;
			$("input[name='presetMoney']").each(function(){
				presetMoney = $(this).val();
				if(typeof presetMoney != 'undefined'
					&& presetMoney != null
					&& presetMoney.length > 0){
					return false;
				}
			});
			
			
			if(myevent.target.tagName != 'TD'){
				dataUnit = $(myevent.target.parentElement).attr("data-unit");
			}else{
				dataUnit = $(myevent.target).attr("data-unit");				
			}
			
			selFlag = $('#'+dataUnit).attr("data-sel");
			dataBit = $('#'+dataUnit).attr("data-bit");
			dataIndex = $('#'+dataUnit).attr("data-index");
			
			if(selFlag == '0'){
				$('#'+dataUnit).attr("data-sel", '1');
				$("td[data-unit="+dataUnit +"]").each(function(){
					$(this).addClass('selNumBg');						
				});
				
				if(typeof presetMoney != 'undefined'
					&& presetMoney.length > 0){
					$('#'+dataBit+'_'+dataIndex+'_betAmount').val(presetMoney);
				}
			}else{
				$('#'+dataUnit).attr("data-sel", '0');
				$('#'+dataBit+'_'+dataIndex+'_betAmount').val('');
				$("td[data-unit="+dataUnit +"]").each(function(){
					$(this).removeClass('selNumBg');						
				});
			}
			
		};
		
		
		$scope.resetBettingAmount = function(){
			//var presetBettingAmount = $('#presetMoney').val();
			var presetMoney = null;
			$("input[name='presetMoney']").each(function(){
				presetMoney = $(this).val();
				if(typeof presetMoney != 'undefined'
					&& presetMoney != null
					&& presetMoney.length > 0){
					return false;
				}
			});
			
			$('tr.ng-scope > td > input').each(function(){
				var selFlag = $(this.parentElement.parentElement).attr("data-sel");
				if(selFlag == '1'){
					$(this).val(presetMoney);					
				}
			});
		};
		
		$scope.betting = function(lotteryType){
			var bet = null;
			var bets = new Array();
			var bulletinBoard = JSON.parse(sessionStorage.getItem("bulletinBoard"));
			var issueId = null;
			var walletId = JSON.parse(sessionStorage.getItem("mainAcc")).id;
			var times = 1;
			var pattern = 1;
			var isZh = 0;
			var terminalType = 0; 
			var blankCounter = 0;
			
			if(typeof bulletinBoard == 'undefined'
				|| bulletinBoard == null
				|| bulletinBoard.currIssue == null){
				showToast("无投注期次!");
				return;
			}
			
			
			issueId = bulletinBoard.currIssue.id;
			
			$('td[data-sel="1" ]').each(function(){
				bet = {};
				var selFlag = $(this).attr("data-sel");
				var dataUnit = $(this).attr("data-unit");
				var betNum = $(this).attr("data-bettingNum");
				var playTypeId = $(this).attr("data-play");
				var betAmount = $('#'+dataUnit+'_betAmount').val();
				var odds = $(this).attr("data_odds");
				times = betAmount / pattern;
				bet.issueId = issueId;
				bet.playType = playTypeId;
				bet.betNum = betNum;
				bet.times = times;
				bet.pattern = pattern;
				bet.isZh = isZh;
				bet.terminalType = terminalType;
				bet.prizeRate = odds;
				bet.betAmount = betAmount;
				
				if(typeof betAmount == 'undefined' 
					|| betAmount == null
					|| betAmount == ''){
					blankCounter++;
				}
				
				bets.push(bet);
				
				
			});
			
			if(blankCounter > 0){
				showToast("请输入投注金额!");
				return;
			}
			playgameService.bet(bets, lotteryType, walletId).then(function(){
				showToast("投注成功!");
				
				$scope.queryBettingRecBrief(lotteryType, 1);
				$scope.queryMemberInfo();
				$scope.queryExpertPushNum(lotteryType);
				
				$('tr.ng-scope[data-sel="1" ]').each(function(){
					$(this).removeClass('selNumBg');
					$(this).attr("data-sel", "0");
					$(this).children("td").children("input").val('');
					$('html, body').animate({scrollTop:0}, 'slow'); 
				});
			},
			function(error){
				var codeType = 'error_mes';
				var attr = error;
				var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
				showToast(codeVal);
			});
		};

		$scope.bettingZx = function(lotteryType){
			var bet = null;
			var bets = new Array();
			var bulletinBoard = JSON.parse(sessionStorage.getItem("bulletinBoard"));
			var issueId = null;
			var walletId = JSON.parse(sessionStorage.getItem("mainAcc")).id;
			var times = 1;
			var pattern = 1;
			var isZh = 0;
			var terminalType = 0; 
			var blankCounter = 0;
			
			if(typeof bulletinBoard == 'undefined'
				|| bulletinBoard == null
				|| bulletinBoard.currIssue == null){
				showToast("无投注期次!");
				return;
			}
			
			
			issueId = bulletinBoard.currIssue.id;
			
			if(typeof betAmount == 'undefined' 
				|| betAmount == null
				|| betAmount == ''){
				blankCounter++;
			}
			
			var betNum = '';
			for(var i = 0; i < 6; i++){
				bet = {};
				bet.issueId = issueId;
				bet.playType = playTypeId;
				bet.betAmount = betAmount;
				times = betAmount / pattern;
				bet.times = times;
				bet.pattern = pattern;
				bet.isZh = isZh;
				bet.terminalType = terminalType;
				var prizeRate = '';
				
				$('col_'+i).find('td[data-sel="1" ]').each(function(){
					var selFlag = $(this).attr("data-sel");
					var dataUnit = $(this).attr("data-unit");
					var betNum_ = $(this).attr("data-bettingNum");
					var playTypeId = $(this).attr("data-play");
//					var betAmount = $('#'+dataUnit+'_betAmount').val();
					var odds = $(this).attr("data_odds");
					
					
//					bet.betNum = betNum;
					betNum += betNum_;
//					bet.times = times;
//					bet.pattern = pattern;
					
//					bet.prizeRate = odds;
					prizeRate = odds;
					
					
				});
				var len = parseLen(i);
				if(betNum.length != len){
					alert('');
					return;
				}
				bet.betNum = betNum;
				bet.prizeRate = prizeRate;
				bets.push(bet);
			}
			$('td[data-sel="1" ]').each(function(){
				bet = {};
				var selFlag = $(this).attr("data-sel");
				var dataUnit = $(this).attr("data-unit");
				var betNum = $(this).attr("data-bettingNum");
				var playTypeId = $(this).attr("data-play");
				var betAmount = $('#'+dataUnit+'_betAmount').val();
				var odds = $(this).attr("data_odds");
				times = betAmount / pattern;
				bet.issueId = issueId;
				bet.playType = playTypeId;
				bet.betNum = betNum;
				bet.times = times;
				bet.pattern = pattern;
				bet.isZh = isZh;
				bet.terminalType = terminalType;
				bet.prizeRate = odds;
				bet.betAmount = betAmount;
				
				if(typeof betAmount == 'undefined' 
					|| betAmount == null
					|| betAmount == ''){
					blankCounter++;
				}
				
				bets.push(bet);
				
				
			});
			
			if(blankCounter > 0){
				showToast("请输入投注金额!");
				return;
			}
			playgameService.bet(bets, lotteryType, walletId).then(function(){
				showToast("投注成功!");
				
				$scope.queryBettingRecBrief(lotteryType, 1);
				$scope.queryMemberInfo();
				$scope.queryExpertPushNum(lotteryType);
				
				$('tr.ng-scope[data-sel="1" ]').each(function(){
					$(this).removeClass('selNumBg');
					$(this).attr("data-sel", "0");
					$(this).children("td").children("input").val('');
					$('html, body').animate({scrollTop:0}, 'slow'); 
				});
			},
			function(error){
				var codeType = 'error_mes';
				var attr = error;
				var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
				showToast(codeVal);
			});
		};
		
		$scope.queryBettingRecBrief = function(lotteryType, pageIndex){
			
			if(sessionStorage.getItem('userInfo') == null){
				return ;
			}
			
			var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
			
			$scope.queryRecParams = {};
			$scope.queryRecParams.pageIndex = pageIndex;
			$scope.queryRecParams.pageSize = 15;
			$scope.queryRecParams.userName = userInfo.userName;
			$scope.queryRecParams.lotteryType = lotteryType;
			
			hisRecService.queryBettingRecBrief($scope.queryRecParams).then(function(res){
				$scope.bettingRecs = res;
				if($scope.bettingRecs.pageIndex == 1){
					$scope.isPre = false;
				}else{
					$scope.isPre = true;
				}
				
				if($scope.bettingRecs.pageIndex == $scope.bettingRecs.totalPages ){
					$scope.isNext = false;
				}else{
					$scope.isNext = true;
				}
			}, function(error){
				showToast("查询订单失败!!");
			});
		};
		
		
		$scope.queryIssueRecBrief = function(lotteryType, pageIndex){
						
			$scope.queryRecParams = {};
			$scope.queryRecParams.pageIndex = pageIndex;
			$scope.queryRecParams.pageSize = 15;
			$scope.queryRecParams.startTime = $scope.getNowFormatDate();
			$scope.queryRecParams.endTime = $scope.getNowFormatDate();
			$scope.queryRecParams.lotteryType = lotteryType;
			
			hisRecService.queryIssueRecBrief($scope.queryRecParams).then(function(res){
				$scope.issueRecs = res;				
			}, function(error){
				showToast("查询开奖失败!!");
			});
		};
		
		
		 $scope.getBettingNum = function(lotteryType, playType, bettingNum) {
		    	var codeType = "play_type_" + lotteryType;
		    	
		    	if(playType.indexOf('ds') >= 0 || playType.indexOf('dx') >= 0){
		    		var playType_ = playType.substring(0, playType.indexOf('|'));
		    		var attr = playType_ + "_" + bettingNum;
		        	var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
			    	if(codeVal != null){
			    		var number = playType.substring(playType.length - 2, playType.length - 1);
			    		var numberDes = number;
			    		if(lotteryType == 'cqssc' || lotteryType == 'xjssc' || lotteryType == '5fc'){
			    			if(number == '一'){
			    				numberDes = '万位';
			    			}else if(number == '二'){
			    				numberDes = '千位';
			    			}else if(number == '三'){
			    				numberDes = '百位';
			    			}else if(number == '四'){
			    				numberDes = '十位';
			    			}else if(number == '五'){
			    				numberDes = '个位';
			    			}
			    		}else if(lotteryType == 'bjpk10' 
			    			|| lotteryType == 'xyft'
			    				|| lotteryType == 'yfb'){
			    			if(number == '一'){
			    				numberDes = '冠军';
			    			}else if(number == '二'){
			    				numberDes = '亚军';
			    			}else if(number == '三'){
			    				numberDes = '季军';
			    			}else{
			    				numberDes = '第' + number + '名';
			    			}
			    		}else if(lotteryType == 'tc3'){
			    			if(number == '一'){
			    				numberDes = '百位';
			    			}else if(number == '二'){
			    				numberDes = '十位';
			    			}else if(number == '三'){
			    				numberDes = '个位';
			    			}
			    		}
			    		codeVal = codeVal.replace('{number}', numberDes);
			    	}
		    	}else if(playType.indexOf('dwd') >= 0){
		    		var playType_ = playType.substring(0, playType.indexOf('|'));
		    		var bettingNumArray = bettingNum.split(',');
		    		var bettingNumIndex = -1;
		    		var bettingNumVal = '';
		    		var attr = null;
		    		
		    		for(var i = 0;i < bettingNumArray.length; i++){
		    			if(bettingNumArray[i]){
		    				bettingNumIndex = i;
		    				bettingNumVal = bettingNumArray[i];
		    				
		    				attr = playType_ + "_" + bettingNumIndex;
		    				break;
		    			}
		    		}    		
		    		
		        	var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
			    	if(codeVal != null){
			    		codeVal = codeVal.replace('{number}', bettingNumVal);
			    	}
		    	}
		    	
		    	return codeVal;
		    };
		    
		    $scope.getNowFormatDate = function(){
		        var date = new Date();
		        var seperator1 = "-";
		        var year = date.getFullYear();
		        var month = date.getMonth() + 1;
		        var strDate = date.getDate();
		        if (month >= 1 && month <= 9) {
		            month = "0" + month;
		        }
		        if (strDate >= 0 && strDate <= 9) {
		            strDate = "0" + strDate;
		        }
		        var currentdate = year + seperator1 + month + seperator1 + strDate;
		        return currentdate;
		    };
		    
		    
		    $scope.queryExpertPushNum = function(lotteryType){
				$scope.queryExpertPushNumParams = {};
				$scope.queryExpertPushNumParams.lotteryType = lotteryType;
				$scope.isExpertPushNumber = false;
				$scope.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
				if(typeof $scope.userInfo == 'undefined'
					|| $scope.userInfo == null){
					return;
				}
						
				expertService.queryExpertPushNum($scope.queryExpertPushNumParams).then(function(res){
					// {"ds":"单","dx":"小","numbers":"01,03","amount":100,"raceLane":"冠军"}
					$scope.raceLane = "赛道:";
					$scope.raceLaneValue = res.raceLane;
					$scope.ds = "单/双:" + res.ds;
					$scope.dsAmount = "金额:" + res.dsAmount;
					$scope.dx = "大/小:" + res.dx;
					$scope.dxAmount = "金额:" + res.dxAmount;
					$scope.numbers = "数字:" + res.numbers;
					$scope.numbersAmount = "金额:" + res.numbersAmount;
					
					$scope.isExpertPushNumber = true;
				}, function(error){
					showToast("查询推荐号码失败!!");
				});
			};
			
		    
			 $scope.getNowFormatDate = function(){
			        var date = new Date();
			        var seperator1 = "-";
			        var year = date.getFullYear();
			        var month = date.getMonth() + 1;
			        var strDate = date.getDate();
			        if (month >= 1 && month <= 9) {
			            month = "0" + month;
			        }
			        if (strDate >= 0 && strDate <= 9) {
			            strDate = "0" + strDate;
			        }
			        var currentdate = year + seperator1 + month + seperator1 + strDate;
			        return currentdate;
			    };
			    
			    
			    $scope.queryZhIssue = function(lottoType){
					
					issueService.queryZhIssue(lottoType).then(function(res){
												
						$scope.zhIssue = res;
					}, function(error){
						showToast("查询追号期次失败!!");
					});
				};
				
			
				$scope.selRowFun = function(){
					var bitIndex_ = $('#bitIndex').val();
					var selRow = $scope.bitNumArray[bitIndex_];
					$scope.selRow = selRow;
				};
				
				$scope.produceSchedule = function(){
					var playTypeId_ = $('#playTypeId_').val();
					var betAmount_ = $('#betAmount_').val();
					var betTimes_ = $('#betTimes_').val();
					var isIncrease_ = $('#isIncrease')[0].checked?1:0;
					//var isPize_ = $('#isPize').val();
					
					if(typeof playTypeId_ == 'undefined' || playTypeId_ == null || playTypeId_.length == 0 || playTypeId_ == '? undefined:undefined ?'){
						showToast("请选择号码!!");
						return ;
					}else if( playTypeId_ == '-1'){
						showToast("请登录!!");
						//return ;
					}
					
					if(typeof betAmount_ == 'undefined' || betAmount_ == null || betAmount_.length == 0){
						showToast("请输入金额!!");
						return ;
					}
					
					if(typeof betTimes_ == 'undefined' || betTimes_ == null || betTimes_.length == 0){
						showToast("请输入金额!!");
						return ;
					}
					
					var initTimes = isIncrease_;
					$('tr.ng-scope[data-type="10" ]').each(function(){
						var dataIndex_ = $(this).attr('data-index');
						var isPrize_ = $('#isPize_'+dataIndex_)[0].checked?1:0;
						var isSelected = $('#isSel_'+dataIndex_)[0].checked?1:0;
						
						var amount_ = 0
						
						if(isSelected == 0){
							return;
						}
						
						
						
						if(isIncrease_ == 0){
							amount_ = betAmount_ ;
						}else{
							amount_ = betAmount_ * initTimes;
						}
						
						$('#zhAmount_'+dataIndex_).val(amount_);
						
						if( isPrize_ == 1){
							initTimes = 1;
						}else{
							initTimes++;
						}
						
					});
				};
				
				$scope.changeProduceScheduleState = function(state){
					if(state == 0){
						$scope.isProduceSchedule = false;
					}else if(state == 1){
						$scope.isProduceSchedule = true;
					}
				};
				
				$scope.selPlayTypeFunc = function(){
					var playTypeId_ = $('#playTypeId_').val();
					for(var i = 0; i < $scope.selRow.bitNum.length; i++){
						var row_ = $scope.selRow.bitNum[i];
						if(row_.playTypeId == playTypeId_){
							$("#bettingNum_").val(row_.bettingNumVal);
							return;
						}
					}
				};
				
				$scope.submitSchedule = function(lotteryType){
					var betNum_ = $('#bettingNum_').val();
					var playTypeId = $(this).attr("playTypeId_");
					var bet = null;
					var bets = new Array();
					var times = 1;
					var pattern = 1;
					var isZh = 1;
					var terminalType = 0; 
					var walletId = JSON.parse(sessionStorage.getItem("mainAcc")).id;
					
					if(typeof betNum_ == 'undefined' || betNum_ == null || betNum_.length == 0 || betNum_ == '? undefined:undefined ?'){
						showToast("请选择号码!!");
						return ;
					}else if( betNum_ == '-1'){
						showToast("请登录!!");
						return ;
					}					
					
					$('tr.ng-scope[data-type="10" ]').each(function(){
						
						var dataIndex_ = $(this).attr('data-index');
						var isPrize_ = $('#isPize_'+dataIndex_)[0].checked?1:0;
						var isSelected = $('#isSel_'+dataIndex_)[0].checked?1:0;
						var betAmount = $('#zhAmount_'+dataIndex_).val();
						var issueId = $('#issueId_'+dataIndex_).val();
						bet = {};
						
						if(isSelected == 0){
							return;
						}
						
						if(typeof betAmount == 'undefined' || betAmount == null || betAmount.length == 0 || betAmount == '? undefined:undefined ?'){
							//showToast("请先生成计划!!");
							return false;
						}			
						
						
						
						//var selFlag = $(this).attr("data-sel");
						//var dataBit = $(this).attr("data-bit");
						//var dataIndex = $(this).attr("data-index");
						//var betNum = $(this).attr("data-bettingNum");
						
						//var betAmount = $('#'+dataBit+'_'+dataIndex+'_betAmount').val();
						times = betAmount / pattern;
						bet.issueId = issueId;
						bet.playType = playTypeId;
						bet.betNum = betNum_;
						bet.times = times;
						bet.pattern = pattern;
						bet.isZh = isZh;
						bet.terminalType = terminalType;
						bet.isPrize = isPrize_;
						if(typeof betAmount == 'undefined' 
							|| betAmount == null
							|| betAmount == ''){
							blankCounter++;
						}
						
						bets.push(bet);
						
						
					});
					
					if(bets.length == 0){
						showToast("请至少选择一个有效投注期次!");
						return;
					}
					
					playgameService.bet(bets, lotteryType, walletId).then(function(){
						showToast("成功生成投注计划!");
						
						//$scope.queryBettingRecBrief(lotteryType, 1);
						$scope.queryMemberInfo();
						//$scope.queryExpertPushNum(lotteryType);
						
						/*$('tr.ng-scope[data-sel="1" ]').each(function(){
							$(this).removeClass('selNumBg');
							$(this).attr("data-sel", "0");
							$(this).children("td").children("input").val('');
							//$('html, body').animate({scrollTop:0}, 'slow'); 
						});*/
					},
					function(error){
						var codeType = 'error_mes';
						var attr = error;
						var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
						showToast(codeVal);
					});
				};
				
				
}]).controller('hisRecCtrl', ["$scope", "$http","$state", "$location", "playgameService", "hisRecService", "sysCodeTranslateFactory", function ($scope, $http, $state,$location, playgameService, hisRecService, sysCodeTranslateFactory) {
	$scope.initHisRecTab = function(){
		$("span.j-nav_item").on("click",function(){
			var tabType = $(this).attr("data-num");
			$("span.j-nav_item").removeClass('active');
			$(this).addClass('active');
			
			$("div.u_c_w_content").each(function(){
				var cType = $(this).attr("data-num");
				if(tabType == cType){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		});		
	};
	
	$scope.initHisRecQuery = function(){
		$scope.sysCodeTranslateFactory = sysCodeTranslateFactory;
		
		playgameService.queryLotterys().then(function(res){
			$scope.lotterys = res;
		}, function(error){
			showToast("查询彩种信息失败!!");
		});
		
		playgameService.queryOrderStatus().then(function(res){
			$scope.orderStatus = res;
		}, function(error){
			showToast("查询订单状态失败!!");
		});
		$scope.bettingRecs = {};
		$scope.bettingRecs.pageIndex = 1;
		$scope.bettingRecs.totalPages = 1;
		$scope.queryRecParams = {};
	};
	
	$scope.initMsearch = function(){
		
		$scope.mSearchParam = {};
		$scope.queryRecParams = {};
		$scope.queryAccRecParams = {};
		
		$scope.mSearchParam.searchDate = 1;
	};
	
	$scope.queryBettingRec = function(pageIndex){
		var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
		$scope.queryRecParams.pageIndex = pageIndex;
		$scope.queryRecParams.startTime = $("#4_startTime").val();
		$scope.queryRecParams.endTime = $("#4_endTime").val();
		$scope.queryRecParams.userName = userInfo.userName;
		
		if(typeof $scope.queryRecParams.startTime != 'undefined'
			&& $scope.queryRecParams.startTime != null
			&& $scope.queryRecParams.startTime.length > 0){
			$scope.queryRecParams.startTime = $scope.queryRecParams.startTime +" 00:00:00";
		}
		
		if(typeof $scope.queryRecParams.endTime != 'undefined'
			&& $scope.queryRecParams.endTime != null
			&& $scope.queryRecParams.endTime.length > 0){
			$scope.queryRecParams.endTime = $scope.queryRecParams.endTime +" 23:59:59";
		}
		
		hisRecService.queryBettingRec($scope.queryRecParams).then(function(res){
			$scope.bettingRecs = res;
			if($scope.bettingRecs.pageIndex == 1){
				$scope.isPre = false;
			}else{
				$scope.isPre = true;
			}
			
			if($scope.bettingRecs.pageIndex == $scope.bettingRecs.totalPages ){
				$scope.isNext = false;
			}else{
				$scope.isNext = true;
			}
		}, function(error){
			showToast("查询订单失败!!");
		});
	};
	
	
	
	$scope.queryOrderStatus = function(){
		playgameService.queryOrderStatus().then(function(res){
			$scope.orderStatus = res;
		}, function(error){
			showToast("查询账户类型失败!!");
		});
	}
	
	$scope.getOrderStatus = function(status) {
        if (status == 0){
        	return "等待派奖";
        }else if (status == 1){
            return "已中奖";
        }else if (status == 2){
            return "未中奖";
        }else if (status == 3){
            return "用户取消订单";
        }else if (status == 4){
            return "系统取消订单";
        }else if (status == 5){
            return "等待重新派奖";
        }
    };
    
    $scope.getBettingNum = function(lotteryType, playType, bettingNum) {
    	var codeType = "play_type_" + lotteryType;
    	
    	if(playType.indexOf('ds') >= 0 || playType.indexOf('dx') >= 0){
    		var playType_ = playType.substring(0, playType.indexOf('|'));
    		var attr = playType_ + "_" + bettingNum;
        	var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
	    	if(codeVal != null){
	    		var number = playType.substring(playType.length - 2, playType.length - 1);
	    		var numberDes = number;
	    		if(lotteryType == 'cqssc' || lotteryType == 'xjssc' || lotteryType == '5fc'){
	    			if(number == '一'){
	    				numberDes = '万位';
	    			}else if(number == '二'){
	    				numberDes = '千位';
	    			}else if(number == '三'){
	    				numberDes = '百位';
	    			}else if(number == '四'){
	    				numberDes = '十位';
	    			}else if(number == '五'){
	    				numberDes = '个位';
	    			}
	    		}else if(lotteryType == 'bjpk10' 
	    			|| lotteryType == 'xyft'
	    			|| lotteryType == 'yfb'){
	    			if(number == '一'){
	    				numberDes = '冠军';
	    			}else if(number == '二'){
	    				numberDes = '亚军';
	    			}else if(number == '三'){
	    				numberDes = '季军';
	    			}else{
	    				numberDes = '第' + number + '名';
	    			}
	    		}
	    		codeVal = codeVal.replace('{number}', numberDes);
	    	}
    	}else if(playType.indexOf('dwd') >= 0){
    		var playType_ = playType.substring(0, playType.indexOf('|'));
    		var bettingNumArray = bettingNum.split(',');
    		var bettingNumIndex = -1;
    		var bettingNumVal = '';
    		var attr = null;
    		
    		for(var i = 0;i < bettingNumArray.length; i++){
    			if(bettingNumArray[i]){
    				bettingNumIndex = i;
    				bettingNumVal = bettingNumArray[i];
    				
    				attr = playType_ + "_" + bettingNumIndex;
    				break;
    			}
    		}    		
    		
        	var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
	    	if(codeVal != null){
	    		codeVal = codeVal.replace('{number}', bettingNumVal);
	    	}
    	}
    	
    	return codeVal;
    };
    
    $scope.initAccRecQuery = function(){
    	//$scope.sysCodeTranslateFactory = sysCodeTranslateFactory;
    	
		playgameService.queryAccOperation().then(function(res){
			$scope.operationTypes = res;
		}, function(error){
			showToast("查询彩种信息失败!!");
		});
		
		
		$scope.accRecs = {};
		$scope.accRecs.pageIndex = 1;
		$scope.accRecs.totalPages = 1;
		$scope.queryAccRecParams = {};
	};
	
	$scope.translateDataItemType = function(dataItemTypeVal){
		var codeType = 'data_item_type';
		var attr = dataItemTypeVal;
		var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
		return codeVal;
	};
	
	$scope.queryAccRec = function(pageIndex){
		//$scope.sysCodeTranslateFactory = sysCodeTranslateFactory;
		var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
		$scope.queryAccRecParams.pageIndex = pageIndex;
		$scope.queryAccRecParams.startTime = $("#5_startTime").val();
		$scope.queryAccRecParams.endTime = $("#5_endTime").val();
		$scope.queryAccRecParams.userName = userInfo.userName;
		
		if(typeof $scope.queryAccRecParams.startTime != 'undefined'
			&& $scope.queryAccRecParams.startTime != null
			&& $scope.queryAccRecParams.startTime.length > 0){
			$scope.queryAccRecParams.startTime = $scope.queryAccRecParams.startTime +" 00:00:00";
		}
		
		if(typeof $scope.queryAccRecParams.endTime != 'undefined'
			&& $scope.queryAccRecParams.endTime != null
			&& $scope.queryAccRecParams.endTime.length > 0){
			$scope.queryAccRecParams.endTime = $scope.queryAccRecParams.endTime +" 23:59:59";
		}
		
		hisRecService.queryAccRec($scope.queryAccRecParams).then(function(res){
			$scope.accRecs = res;
			if($scope.accRecs.pageIndex == 1){
				$scope.isPre = false;
			}else{
				$scope.isPre = true;
			}
			
			if($scope.accRecs.pageIndex == $scope.accRecs.totalPages ){
				$scope.isNext = false;
			}else{
				$scope.isNext = true;
			}
		}, function(error){
			showToast("查询订单失败!!");
		});
	};
	
	
	$scope.initProfitRecQuery = function(){		
		$scope.profitRecs = {};
		$scope.profitRecs.pageIndex = 1;
		$scope.profitRecs.totalPages = 1;
		$scope.queryProfitRecParams = {};
	};
	
	$scope.queryProfitRec = function(pageIndex){
		$scope.queryProfitRecParams.pageIndex = pageIndex;
		$scope.queryProfitRecParams.startTime = $("#6_startTime").val();
		$scope.queryProfitRecParams.endTime = $("#6_endTime").val();
		
		$scope.userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
		if(typeof $scope.userInfo != 'undefined'
			&& $scope.userInfo != null){
			$scope.queryProfitRecParams.userName = $scope.userInfo.userName;
			$scope.queryProfitRecParams.userType = $scope.userInfo.userType;
		}
		if(typeof $scope.queryProfitRecParams.startTime != 'undefined'
			&& $scope.queryProfitRecParams.startTime != null
			&& $scope.queryProfitRecParams.startTime.length > 0){
			$scope.queryProfitRecParams.startTime = $scope.queryProfitRecParams.startTime +" 00:00:00";
		}else{
			showToast("请输入开始时间!!");
			return;
		}
		
		if(typeof $scope.queryProfitRecParams.endTime != 'undefined'
			&& $scope.queryProfitRecParams.endTime != null
			&& $scope.queryProfitRecParams.endTime.length > 0){
			$scope.queryProfitRecParams.endTime = $scope.queryProfitRecParams.endTime +" 23:59:59";
		}else{
			showToast("请输入结束时间!!");
			return;
		}
		
		hisRecService.queryProfitRec($scope.queryProfitRecParams).then(function(res){
			$scope.profitRecs = res;
			if($scope.profitRecs.pageIndex == 1){
				$scope.isPre = false;
			}else{
				$scope.isPre = true;
			}
			
			if($scope.profitRecs.pageIndex == $scope.profitRecs.totalPages ){
				$scope.isNext = false;
			}else{
				$scope.isNext = true;
			}
		}, function(error){
			showToast("查询利润统计失败!!");
		});
	};
	
	$scope.cancelOrder = function(orderNum){
		hisRecService.cancelOrder(orderNum).then(function(res){
			showToast("成功取消订单");
		},function(error){
			var codeType = 'error_mes';
			var attr = error;
			var codeVal =  sysCodeTranslateFactory.codeTranslate(codeType, attr);
			showToast(codeVal);
		});
	};
	
		
	$scope.mStartSearch = function(pageIndex){
		var recOption = $scope.mSearchParam.recOption;
		
		$scope.changeTimeArea();
		if(recOption == "0"){
			$scope.queryBettingRec(pageIndex);
		}else if(recOption == "1"){
			//$scope.accRecs = {};
			//$scope.accRecs.pageIndex = 1;
			//$scope.accRecs.totalPages = 1;
			$scope.queryAccRecParams = {};
			
			$scope.queryAccRec(pageIndex);
		}else if(recOption == "2"){
			$scope.initProfitRecQuery();
			$scope.queryProfitRec(pageIndex);
		}
	};
	
	$scope.changeTimeArea = function(){
		var timeType = $scope.mSearchParam.searchDate;
		var startTime = null;
		var endTime = null;
		
		if(timeType == "1"){
			startTime = GetDateStr(0) + " 00:00:00";
			endTime = GetDateStr(0) + " 23:59:59";
			
		}else if(timeType == "7"){
			startTime = GetDateStr(-7) + " 00:00:00";
			endTime = GetDateStr(0) + " 23:59:59";
		}else if(timeType == "30"){
			startTime = GetDateStr(-30) + " 00:00:00";
			endTime = GetDateStr(0) + " 23:59:59";
		}
		
		$("#4_startTime").val(startTime);
		$("#4_endTime").val(endTime);
		$("#5_startTime").val(startTime);
		$("#5_endTime").val(endTime);
		$("#6_startTime").val(startTime);
		$("#6_endTime").val(endTime);
	};
	
}])
.controller('lotteryUICtrl', ["$scope", "$http","$state", "$location", "playgameService", "hisRecService", function ($scope, $http, $state,$location, playgameService, hisRecService) {
	$scope.iniCqsscUI = function(){
		$scope.isZHActive = true;
		$scope.isBitActive = false;
		$scope.isSmDisplay = false;
	};
	
	
	
	$scope.changeFocus = function(focusType){
		if(focusType == 0){
			$scope.isZHActive = true;
			$scope.isBitActive = false;
			//控制双面
			$scope.isSmDisplay = false;
		}else if(focusType == 1){
			$scope.isZHActive = false;
			$scope.isBitActive = true;
			$scope.isSmDisplay = true;
		}
	};
	
}]).service('playgameService', ["$http", "$q", function ($http, $q) {


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

            }else if(catSeq==2){  //若是pt游戏

                newTab.location.href = "pt_pc.html?gameCode="+code;

                /*
                $http.get(getPtConfig).then(function (res) {

                    if (res.data.code == 1) {

                        currentClient=clientType;

                        currentUserName=res.data.data.username;
                        currentgame=code;
                        currentTab=newTab;

                        var realMode = 1;
                        iapiLogin(res.data.data.username, res.data.data.password, realMode, "en");

                    }

                }, function () {

                    console.log("读取PT游戏配置信息失败");

                });
                */


            }else if(catSeq==3){  //PNG

                $http.get(getTcGameUrl,{params: {gameCode: code,productType: gameId,gameMode:'1'}}).then(function (res) {

                    if (res.data.code == 0) {

                        newTab.location.href = res.data.data.gameUrl;
                    }

                }, function () {

                    console.log("读取TC游戏配置信息失败");

                });


            }else if(catSeq==4){  //MG游戏

                //读取MG配置信息
                $http.get(getMgconfig).then(function (res) {

                    if (res.data.code == 1) {

                        var mgConfiginfo = res.data.data;

                        //读取MG账户信息
                        $http.get(getMgGameInfo).then(function (res) {

                            if (res.data.code == 1) {

                                var proPcUrl = mgConfiginfo.productionFlashURL;

                                var url = proPcUrl.replace("${0}", res.data.data.username)
                                    .replace("${1}", res.data.data.password)
                                    .replace("${2}", code);

                                newTab.location.href=url;

                            }

                        }, function () {

                            console.log("读取MG游戏账户信息失败");

                        });

                    }

                }, function () {

                    console.log("读取MG游戏配置信息失败");

                });


            }else if(catSeq==5){ //TTG游戏

                $http.get(getTtgConfig).then(function (res) {

                    if (res.data.code == 1) {

                        var ttgConfig = res.data.data;

                        //读取TTG token
                        $http.get(getTtgToken).then(function (res) {

                            if (res.data.code == 1) {

                                var proPcUrl = ttgConfig.productionFlashURL;

                                var url = proPcUrl.replace("${0}", res.data.data.token)
                                        .replace("${1}", ttgConfig.account)
                                        .replace("${2}", code)
                                        .replace("${3}", type)
                                        .replace("${4}", gameId)
                                        .replace("${5}", ttgConfig.lang)
                                        .replace("${6}", ttgConfig.lsdid);

                                newTab.location.href=url;

                            }else{

                                newTab.close();
                                alert("游戏加载失败，请重试或联系客服！");

                            }

                        }, function () {

                            console.log("读取TTG游戏账户信息失败");

                        });

                    }

                }, function () {

                    console.log("读取TTG游戏配置信息失败");

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

        }else if(catSeq==2){ //PT游戏

            var demoPcUrl = "http://cache.download.banner.happypenguin88.com/casinoclient.html?" +
                    "language={languagecode}&game={gamecode}&mode=offline&affiliates=1&currency={currencycode}";

            var url = demoPcUrl.replace("{languagecode}", "ZH-CN")
                    .replace("{gamecode}", code)
                    .replace("{currencycode}", "CNY");

            newTab.location.href=url;

        }else if(catSeq==3){  //AG或PNG


        }else if(catSeq==4){  //MG游戏

            //读取MG配置信息
            $http.get(getMgconfig).then(function (res) {

                if (res.data.code == 1) {

                    var demoPcUrl = res.data.data.demoFlashURL;

                    var url = demoPcUrl.replace("${0}", code);

                    newTab.location.href=url;

                }

            }, function () {

                console.log("读取MG游戏配置信息失败");

            });


        }else if(catSeq==5){ //TTG游戏

            //读取TTG配置信息
            $http.get(getTtgConfig).then(function (res) {

                if (res.data.code == 1) {

                    var demoPcUrl = res.data.data.demoFlashURL;

                    var url = demoPcUrl.replace("${0}", code)
                        .replace("${1}", type)
                        .replace("${2}", gameId)
                        .replace("${3}", res.data.data.lang)
                        .replace("${4}", res.data.data.lsdid);

                    newTab.location.href=url;

                }

            }, function () {

                console.log("读取TTG游戏配置信息失败");

            });

        }

    };
    
    this.queryHotGame = function(){
    	var deferred = $q.defer();
    	
    	var hotGames = new Array();
    	
    	$http.get(queryHotGames).then(function(res){
    		
    		if (res.data.code == 1) {

    			hotGames = res.data.data;

	        }
    		
    		deferred.resolve(hotGames);
    	}, function(){
    		
    		deferred.reject(-1);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.queryLotterys = function(){
    	var deferred = $q.defer();
    	
    	var lotterys = new Array();
    	
    	$http.get(queryLotterysURL).then(function(res){
    		
    		if (res.data.status == 1) {

    			lotterys = res.data.data;

	        }
    		
    		deferred.resolve(lotterys);
    	}, function(){
    		
    		deferred.reject(-1);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.queryBulletinBoard = function(lotteryType){
    	var deferred = $q.defer();
    	
    	
    	var bulletinBoard = null;
    	var queryBettingIssueURL_ = queryBettingIssueURL.replace('{lotteryType}', lotteryType);
    	
    	$http.get(queryBettingIssueURL_).then(function(res){
    		
    		if (res.data.status == 1) {

    			bulletinBoard = res.data.data;

	        }
    		
    		deferred.resolve(bulletinBoard);
    	}, function(){
    		
    		deferred.reject(-1);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.queryPlayTypes = function(lotteryType, playTypeParams){
    	var deferred = $q.defer();
    	var bulletinBoard = null;
    	var queryBettingIssueURL_ = queryBettingIssueURL.replace('{lotteryType}', lotteryType);
    	var playTypeRet = {};
    	playTypeRet.bitNum = playTypeParams.bitNum;
    	playTypeRet.bitName = playTypeParams.bitName;
    	
    	$http.get(queryBettingIssueURL_).then(function(res){
    		
    		if (res.data.status == 1) {

    			playTypeRet.playTypes = res.data.data.playType;
    			
	        }
    		
    		deferred.resolve(playTypeRet);
    	}, function(error){
    		
    		deferred.reject(playTypeRet);
    	});
    	
    	return deferred.promise;
    };
    
    this.queryPrizeRate = function(lotteryType, playType, bitNum, bitName,prizeRateKey){
    	var deferred = $q.defer();
    	    	 
    	var prizeRate = sessionStorage.getItem(prizeRateKey);
    	var queryPrizeRateURL_ = queryPrizeRateURL.replace('{lotteryType}', lotteryType);
    	queryPrizeRateURL_ = queryPrizeRateURL_.replace('{playType}', playType.id);
    	var ret = {};
    	ret.bitNum = bitNum;
    	ret.playType = playType;
    	ret.bitName = bitName;
    	ret.prizeRateKey = prizeRateKey;
    	
    	if(typeof prizeRate != 'undefined'
    		&& prizeRate != null){
    		ret.prizeRate = prizeRate;
    		deferred.resolve(ret);
    		
    		return deferred.promise;
    	}
    	
    	$http.get(queryPrizeRateURL_).then(function(res){
    		if (res.data.status == 1) {
    			prizeRate = res.data.data.single_betting_prize;
	        }
    		
    		console.log('query the prize rate :: ' + queryPrizeRateURL_ + '   prize rate value :: ' + prizeRate);
    		ret.prizeRate = prizeRate;
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
        
    this.bet = function(bets, lotteryType, walletId){
    	var deferred = $q.defer();
    	
    	var prizeRate = null;
    	var bettingURL_ = bettingURL.replace('{lotteryType}', lotteryType);
    	bettingURL_ = bettingURL_.replace('{walletId}', walletId);
    	var ret = {};
    	
    	$http.post(bettingURL_,
    			bets,
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			deferred.resolve(res.data.status);

	        }else{
	        	deferred.reject(res.data.error_code);
	        }
    		
    	}, function(error){
    		
    		deferred.reject(error);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.queryOrderStatus = function(){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(queryOrderStatusURL,
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
    
    
    this.queryAccOperation = function(){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(queryAccOperationURL,
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
    
    
	
    this.queryMainPs = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var queryMainPsURL_ = queryMainPsURL.replace('{lotteryType}', lotteryType);
    	var ret = {};
    	/*ret.mainPs = bitNum;
    	ret.hs = playType;
    	ret.dwd = bitName;*/
    	    	
    	$http.get(queryMainPsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.mainPs = res.data.data.mainPs;
    	    	ret.hs = res.data.data.hs;;
    	    	ret.dwd = res.data.data.dwd;
    	    	ret.status= res.data.status;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.queryYzps = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var queryYzpsURL_ = queryYzpsURL.replace('{lotteryType}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(queryYzpsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.mainPs = res.data.data.mainPs;
    			ret.bwsz = res.data.data.bwsz;
    			ret.swsz = res.data.data.swsz;
    			ret.gwsz = res.data.data.gwsz;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.queryEzdw = function(lotteryType, numType){
    	var deferred = $q.defer();
    	    	 
    	var queryEzpsURL_ = queryEzpsURL.replace('{numType}', numType);
    	queryEzpsURL_ = queryEzpsURL_.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(queryEzpsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.ezdw = res.data.data.ezdw;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.queryEzhs = function(lotteryType, numType){
    	var deferred = $q.defer();
    	    	 
    	var queryEzhsURL_ = queryEzhsURL.replace('{numType}', numType);
    	queryEzhsURL_ = queryEzhsURL_.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(queryEzhsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.ezhs = res.data.data.ezhs;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.querySzdw = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var querySzdwURL_ = querySzdwURL.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(querySzdwURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.szdw = res.data.data.szdw;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.queryEzzh = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var queryEzzhURL_ = queryEzzhURL.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(queryEzzhURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.ezzh = res.data.data.ezzh;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    this.querySzhs = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var querySzhsURL_ = querySzhsURL.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(querySzhsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.szhs = res.data.data.szhs;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.querySzzh = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var querySzzhURL_ = querySzzhURL.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(querySzzhURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.szzh = res.data.data.szzh;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    
    this.queryZs = function(lotteryType){
    	var deferred = $q.defer();
    	    	 
    	var queryZsURL_ = queryZsURL.replace('{lottery-type}', lotteryType);
    	var ret = {};
    	    	
    	$http.get(queryZsURL_).then(function(res){
    		if (res.data.status == 1) {
    			ret.zs = res.data.data.zs;
	        }
    		deferred.resolve(ret);
    	}, function(){
    		deferred.reject(ret);
    	});
    	
    	return deferred.promise;
    };
    
    }
]).service('hisRecService', ["$http", "$q", function ($http, $q) {
	this.queryBettingRec = function(queryRecParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(bettingRecordURL,
    			{params:queryRecParams},
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
    
    this.queryBettingRecBrief = function(queryRecParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(bettingRecordBriefURL,
    			{params:queryRecParams},
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
    
    this.queryIssueRecBrief = function(queryRecParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(queryIssueRecBriefURL,
    			{params:queryRecParams},
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
    
    this.queryAccRec = function(queryAccRecParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(accRecordURL,
    			{params:queryAccRecParams},
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
    
    
    
    this.queryProfitRec = function(queryProfitRecParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(queryProfitRecURL,
    			{params:queryProfitRecParams},
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
    
    
    this.cancelOrder = function(orderNum){
    	var deferred = $q.defer();
    	
    	var cancelOrderParams = {};
    	cancelOrderParams.orderNum = orderNum;
    	$http.post(cancelOrderURL,
    			cancelOrderParams,
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			deferred.resolve(res.data.data);
	        }else{
	        	deferred.reject(res.data.error_code);
	        }
    		
    	}, function(error){
    		
    		deferred.reject(error);
    	});
    	
    	return deferred.promise;
    };
    
    
    
	}
]).service('expertService', ["$http", "$q", function ($http, $q) {
	this.queryExpertPushNum = function(queryExpertPushNumParams){
    	var deferred = $q.defer();
    	    	    	
    	$http.get(queryExpertPushNumURL,
    			{params:queryExpertPushNumParams},
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
    
    
	}
]).service('issueService', ["$http", "$q", function ($http, $q) {
	this.queryZhIssue = function(lottoType){
    	var deferred = $q.defer();
    	
    	var queryZhIssueURL_ = queryZhIssueURL.replace('{lotoType}', lottoType);
    	
    	$http.get(queryZhIssueURL_,
    			{'Content-Type': 'application/json'}).then(function(res){
    		
    		if (res.data.status == 1) {
    			deferred.resolve(res.data.data);
	        }else{
	        	deferred.reject(res.data.error_code);
	        }
    		
    	}, function(error){
    		
    		deferred.reject(error);
    	});
    	
    	return deferred.promise;
    };
    
    
	}
]);

