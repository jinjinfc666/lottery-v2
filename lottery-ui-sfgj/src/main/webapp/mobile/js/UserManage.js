/**
 * 负责所有帐户相关的操作
 */
function UserManage(base_url){
	var that = this;
	var _base_url = '/';
	var _ajaxObj = {};
	var _urlObj = {
		login:'mobi/login.php',
		logout:'mobi/logout.php',
		register:'mobi/register.php',
		registerAgent:'asp/addAgent.php',
		changePWD:'asp/change_pws.php', //修改密码
		changePayPWD:'asp/modifyWithdrawPwd.php', //修改支付密码
		setPayPWD:'asp/bindWithdrawPwd.php', //设置支付密码
		modifyInfo:'asp/change_info.php',
		findPasswordByEmail:'mobi/findPasswordByEmail.php',
		findPasswordByPhone:'mobi/findPasswordByPhone.php',
		checkUpgrade:'asp/checkUpgrade.php',
		checkSecurityUser:'mobi/checkSecurityUser.php',
		getQuestion:'mobi/getQuestion.php',
		saveQuestion:'mobi/mSaveQuestion.php',
		makeCall:'mobi/makeCall.php',
		getAgentReport:'mobi/agentReport.php',
		readEmail:'mobi/readEmail.php',
		unreadEmailCount:'mobi/unreadEmailCount.php',
	    credit:'mobi/credit.php',
	    //体验金相关--checkSameInfo、checkPhoneCode、checkValidCode、checkRepeatBankCards、sendVoiceCode
		checkSameInfo:'asp/haveSameInfo.php',
		checkPhoneCode:'asp/checkPhoneCode.php',
		checkValidCode:'mobi/checkValidCode.php',
		checkRepeatBankCards:'asp/repeatBankCards.php',
		sendVoiceCode:'mobi/sendVoiceCode.php'
	};

	/**
	 * login
	 */
	that.login = function(formData,callback){
		var _formData = {
			account:'',
			password:'',
			imageCode:''
		};
		$.extend(_formData,formData);

		//check form data
		var err = _loginDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			_formData = null;
			return;
		}
		mobileManage.ajax({
			url:getUrl('login'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * logout
	 */
	that.logout = function(callback){
		mobileManage.ajax({
			url:getUrl('logout'),
			callback:callback
		});
	};

	/**
	 * change password
	 */
	that.changePassword=function(formData,callback){
		var _formData = {
			password:'',
			new_password:''
			//confirm_password:''
		};
		$.extend(_formData,formData);
		//check
		var err = _changePWSDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		mobileManage.ajax({
			url:getUrl('changePWD'),
			param:_formData,
			callback:callback
		});
	};

    /**
     * set pay password
     */
    that.setPayPassword=function(formData,callback){
        var _formData = {
            loginPwd:'',
            withdrawPwd:''
            //confirm_password:''
        };
        $.extend(_formData,formData);
        //check
        var err = _setPayPWSDataCheck(_formData);
        if(err){
            callback({success:false,message:err});
            return;
        }
        mobileManage.ajax({
            url:getUrl('setPayPWD'),
            param:_formData,
            callback:callback
        });
    };

    /**
     * change pay password
     */
    that.changePayPassword=function(formData,callback){
        var _formData = {
            originalPwd:'',
            newPwd:''
            //confirm_password:''
        };
        $.extend(_formData,formData);
        //check
        var err = _changePayPWSDataCheck(_formData);
        if(err){
            callback({success:false,message:err});
            return;
        }
        mobileManage.ajax({
            url:getUrl('changePayPWD'),
            param:_formData,
            callback:callback
        });
    };

	/**
	 * 依郵件找回密碼
	 */
	that.findPasswordByEmail = function(formData,callback){
		var _formData = {
			account:'',
			email:'',
			imageCode:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _findPasswordByEmailCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		mobileManage.ajax({
			url:getUrl('findPasswordByEmail'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 手機發送簡訊找回密碼
	 */
	that.findPasswordByPhone = function(formData,callback){

		var _formData = {
			account:'',
			phone:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _findPasswordByPhoneCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		//点触验证
		mobileManage.openTouClick(function(tResult){
			if(tResult.success){
				mobileManage.getLoader().open("发送中");
				_formData['checkAddress'] = tResult.data.checkAddress.toString();
				_formData['checkKey'] = tResult.data.token;
				mobileManage.ajax({
					url:getUrl('findPasswordByPhone'),
					param:_formData,
					callback:function(result){
						mobileManage.getLoader().close();
						if(typeof callback === 'function'){
							callback(result);
						}
					}
  				});
			}else{
				toast_tip(tResult.message);
			}
		});
	};

	/**
	 * 一般注册
	 */
	that.register = function(formData,callback){
		var _formData = {
			account:'',
			password:'',
			confirmPassword:'',
			name:'',
			aliasName:'',
			qq:'',
			email:'',
			birthDate:'',
			intro:'',
			phone:'',
			qq:'',
			imageCode:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _registerDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		mobileManage.ajax({
			url:getUrl('register'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 代理注册
	 */
	that.registerAgent = function(formData,callback){
		var _formData = {
			loginname:'',
			password:'',
			confirmpassword:'',
			accountName:'',
			email:'',
			qq:'',
			phone:'',
			partner:'',
			referWebsite:'',
			validateCode:'',
			agentAgree:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _registerAgentDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		mobileManage.ajax({
			url:getUrl('registerAgent'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 修改账户资料
	 */
	that.modifyInfo = function(formData,callback){
		var _formData = {
			aliasName:'',
			qq:'',
            mailaddress:'',
			address:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _modifyInfoDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		$.extend(_formData,formData);
		mobileManage.ajax({
			url:getUrl('modifyInfo'),
			param:_formData,
			callback:callback
		});
	};


	/**
	 * 账户升级
	 */
	that.userUpgrade = function(formData,callback){
		var _formData = {
			helpType:''
		};
		$.extend(_formData,formData);
		mobileManage.ajax({
			url:getUrl('checkUpgrade'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 检查是否为安全用户
	 */
	that.checkSecurityUser = function(callback){
		mobileManage.ajax({
			url:getUrl('checkSecurityUser'),
			param:_formData,
			callback:callback
		});
	};


	/**
	 * 设定密保问题
	 */
	that.saveQuestion = function(formData,callback){
		var _formData = {
			password:'',
			answer:'',
			questionId:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _questionDataCheck(_formData);
		if(err){
			callback({success:false,message:err});
			return;
		}
		mobileManage.ajax({
			url:getUrl('saveQuestion'),
			param:_formData,
			callback:callback
		});
	};


	/**
	 * 查询密保问题
	 */
	that.getQuestion = function(callback){
		mobileManage.ajax({
			url:getUrl('getQuestion'),
			callback:callback
		});
	};

	/**
	 * 电话回波
	 */
	that.makeCall = function(formData,callback){
		var _formData = {
			phone:''
		};
		$.extend(_formData,formData);

		mobileManage.ajax({
			url:getUrl('makeCall'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 查询代理用户统计资讯
	 */
	that.getAgentReport = function(callback){
		mobileManage.ajax({
			url:getUrl('getAgentReport'),
			callback:callback
		});
	};

	/**
	 * 阅读站内信
	 * @param {object} 提交资料
	 * @param {function} 回调
	 */
	that.readEmail = function(formData,callback){
		var _formData = {
			emailId:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _readEmailDataCheck(_formData);
		if(err){
			if(typeof callback === 'function'){
				callback({success:false,message:err});
			}
			return;
		}
		mobileManage.ajax({
			url:getUrl('readEmail'),
			param:_formData,
			callback:callback
		});
	};

	/**
	 * 未读站内信数量
	 * @param {function} 回调
	 */
	that.unreadEmailCount = function(callback){
		mobileManage.ajax({
			url:getUrl('unreadEmailCount'),
			callback:callback
		});
	};

	/**
	 * 查詢帳戶餘額
	 */
	that.getCredit = function(callback){
		mobileManage.ajax({
			url:getUrl('credit'),
	    	callback:callback
	    });
	};

	//自助体验金(start)
	/**
	 * 检查是否有重复信息
	 * @public
	 * @param {Function} callback回调
	 *
	 */
	that.checkSameInfo = function(callback){
		mobileManage.ajax({
			url:getUrl('checkSameInfo'),
			callback:callback
		});
	};
	/**
	 * 检查短信回传
	 * @public
	 */
	that.checkPhoneCode = function(callback){
		mobileManage.ajax({
			url:getUrl('checkPhoneCode'),
			callback:callback
		});
	};

	/**
	 * 检查语音短信验证码
	 * @public
	 */
	that.checkValidCode = function(formData,callback){
		var _formData = {
				imageCode:''
		};
		$.extend(_formData,formData);
		//檢查
		var err = _validCodeCheck(_formData);
		if(err){
			if(typeof callback === 'function'){
				callback({success:false,message:err});
			}
			return;
		}
		mobileManage.ajax({
			url:getUrl('checkValidCode'),
			param:_formData,
			callback:callback
		});
	};

	//验证码检查
	function _validCodeCheck(formData){
		if(!formData.imageCode){
			return '[提示]请输入验证码！';
		}
		return undefined;
	}

	/**
	 * 检查是否有重复银行卡
	 * @public
	 */
	that.checkRepeatBankCards = function(callback){
		mobileManage.ajax({
			url:getUrl('checkRepeatBankCards'),
	    	callback:callback
	    });
	};

	/**
	 * 发送语音验证码到用户注册手机
	 * @param {Function} callback 回调
	 * @public
	 */
	that.sendVoiceCodeToPhone = function(callback){
		mobileManage.openTouClick(function(tResult){
			if(tResult.success){
				mobileManage.getLoader().open("发送中");
				mobileManage.ajax({
					url:getUrl('sendVoiceCode'),
					param:{
						checkAddress:tResult.data.checkAddress.toString(),
						checkKey: tResult.data.token
					},
					callback:function(result){
						mobileManage.getLoader().close();
						callback(result);
					}
				});
			}else{
				toast_tip(tResult.message);
			}
		});
	};
	//自助体验金(end)

	//驗證資料
	function _modifyInfoDataCheck(formData){
		/*if(formData.aliasName&&formData.aliasName.length>10){
			return '[提示]昵称格式错误！请填写10个以内的汉字、英文字母或数字！'
		}
		if(formData.address&&formData.address.length>50){
			return '[提示]邮寄地址最大长度50个字符！'
		}
        if(!formData.mailaddress){
            return '[提示]电子邮箱不可为空！'
        }
        if(/[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+/.test(formData.mailaddress)){
            return '[提示]电子邮箱格式错误！'
        }*/
		return undefined;
	}

	//驗證資料
	function _findPasswordByEmailCheck(formData){
		if(!formData.account){
			return '[提示]账号不可为空！'
		}
		if(!formData.email){
			return '[提示]电子邮箱不可为空！'
		}
		if(/[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+/.test(formData.email)){
			return '[提示]电子邮箱格式错误！'
		}
		return undefined;
	}

	//驗證資料
	function _findPasswordByPhoneCheck(formData){
		if(!formData.account){
			return '[提示]账号不可为空！'
		}
		if(!formData.phone){
			return '[提示]手机号码不可为空！'
		}
		if(/^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$/.test(formData.phone)){
			return '[提示]手机号码格式错误！'
		}
		return undefined;
	}

	//驗證登入資料
	function _loginDataCheck(formData){
		if(!formData.account){
			return '[提示]账号不可为空！'
		}
		if(!formData.password){
			return '[提示]密码不可为空！'
		}
		if(!formData.imageCode){
			return '[提示]验证码不可为空！'
		}
		return undefined;
	}



	//驗證修改密碼資料
	function _changePWSDataCheck(formData){
		if(!formData.password){
			return '[提示]用户旧密码不可为空！';
		}
		if(!formData.new_password){
			return '[提示]用户新密码不可为空！';
		}
		//if(!formData.confirm_password){
		//	return '[提示]用户确认新密码不可为空！';
		//}
        if(formData.password == formData.new_password){
            return '[提示]新密码和原密码不能一样！';
        }
        if(formData.confirm_password){
            if(formData.new_password!=formData.confirm_password){
                return '[提示]两次输入的密码不一致，请核对后重新输入！';
            }
		}

		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(formData.new_password)){
			return '[提示]密码需为6-16位，包含数字和字母！';
		}
		return undefined;
	}

    // 驗證设置支付密码
    function _setPayPWSDataCheck(formData){
        if(!formData.loginPwd){
            return '[提示]登录密码不可为空！';
        }
        if(!formData.withdrawPwd){
            return '[提示]支付密码不可为空！';
        }

       /* if(!/^(\d{6})$/.test(formData.withdrawPwd)){
            return '[提示]正确输入支付密码,6位纯数字组成！';
        }*/
        return undefined;
    }

    // 驗證修改支付密码
    function _changePayPWSDataCheck(formData){
        if(!formData.originalPwd){
            return '[提示]原来的支付密码不可为空！';
        }
        if(!formData.newPwd){
            return '[提示]新的支付支付密码不可为空！';
        }

        if(formData.originalPwd===formData.newPwd){
        	return '[提示]原来的支付密码和新的支付支付密码不能相同！';
		}

        /*if(!/^(\d{6})$/.test(formData.newPwd)){
            return '[提示]正确输入支付密码,6位纯数字组成！';
        }*/
        return undefined;
    }

	//驗證註冊資料
	function _registerDataCheck(formData){
		if(!formData.account){
			return '[提示]登入账号不可为空！';
		}
		if(!formData.password){
			return '[提示]登入密码不可为空！';
		}
		/*if(!formData.confirmPassword){
			return '[提示]确认密码不可为空！';
		}
		if(!formData.name){
			return '[提示]姓名不可为空！';
		}
		if (!/^[\u4e00-\u9fa5]+$/.test(formData.name)) {
            return "[提示]姓名只允许为汉字";
        }*/
		if(!formData.phone){
			return '[提示]电话号码不可为空！';
		}
		/*if(!formData.email){
			return '[提示]电子邮箱不可为空！';
		}*/

		if(!formData.qq){
			return '[提示]QQ不可为空！';
		}
		if(!formData.imageCode){
			return '[提示]验证码不可为空！'
		}
		/*if(!formData.birthDate){
			return '[提示]出生日期不可为空！'
		}*/
		if(formData.account==formData.password){
			return '[提示]登入账号与登录密码不可为相同！';
		}
		/*if(formData.password!=formData.confirmPassword){
			return '[提示]两次输入的密码不一致，请核对后重新输入！';
		}*/
		if(formData.account.length < 6 || formData.account.length >10){
			return '[提示]登入账号的长度请介于6-10字符之间！';
		}
		if(!/^[a-zA-Z0-9]{6,10}$/.test(formData.account)){
			return '[提示]用户名由6-10个数字或英文字母组成！';
		}
		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(formData.password)){
			return '[提示]密码需为6-16位，包含数字和字母';
		}
		/*if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(formData.email)){
			return '[提示]您输入的电子邮件地址格式有误，请核对后重新输入！';
		}
		if(formData.aliasName&&formData.aliasName.length>10){
			return '[提示]昵称格式错误！请填写10个以内的汉字、英文字母或数字！';
		}
		if(formData.intro&&formData.intro.length){
			return '[提示]邀请码长度不允许！';
		}*/
		return undefined;
	}

	//驗證代理註冊資料
	function _registerAgentDataCheck(formData){
		if(!formData.loginname){
			return '[提示]代理账号不可为空！';
		}
		if(!/^a_[a-z0-9]{1,13}$/.test(formData.loginname)){
			return '[提示]代理账号必须以a_开头，由3-15个数字或英文字母组成！';
		}
		if(!formData.password){
			return '[提示]密码不可为空！';
		}
		if(!/^[a-z0-9_]{5,15}$/.test(formData.password)){
			return '[提示]密码由6-16个数字或英文字母组成！';
		}
		if(!formData.confirmpassword){
			return '[提示]请再次输入密码！';
		}
		if(formData.password!=formData.confirmpassword){
			return '[提示]两次输入的密码不一致！';
		}
		if(!formData.accountName){
			return '[提示]请输入真实姓名！';
		}
		if(!/^[\u4e00-\u9fa5]+$/.test(formData.accountName.replace('·',''))){
			return '[提示]真实姓名只能为汉字！';
		}
		if(!formData.email){
			return '[提示]请输入电子邮箱！';
		}
		var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
		if(!regex.test(formData.email.trim().toLowerCase())){
			return "[提示]请输入正确的邮箱地址";
		}
		if(!formData.qq){
		   return '请输入QQ号码！'
		}
		if(!/^[0-9]*$/.test(formData.qq)){
			return '[提示]请正确填写您的QQ号码！';
		}
		if(!formData.phone){
			return '[提示]请输入手机号码！';
		}
		if(!/^[0-9]{11}$/.test(formData.phone)){
			return '[提示]请正确填写您的手机号码！';
		}
		if(!formData.referWebsite){
			return '[提示]请输入代理网址！';
		}
		if(!/^[A-Za-z0-9]{2,6}$/.test(formData.referWebsite)){
			return '[提示]请正确填写您的代理网址！';
		}
		if(!formData.validateCode){
			return '[提示]请输入验证码！';
		}
		if(!formData.agentAgree){
			return '[提示]请同意用户协议！';
		}
		return undefined;
	}

	//驗證问题資料
	function _questionDataCheck(formData){
		if(!formData.password){
			return '[提示]密码不可为空！'
		}
		if(!formData.questionId){
			return '[提示]请选择密保问题！'
		}
		if(!formData.answer){
			return '[提示]请填写你的回答！'
		}
		return undefined;
	}

	//驗證问题資料
	function _readEmailDataCheck(formData){
		if(!formData.emailId){
			return '[提示]信件不存在！';
		}
		return false;
	}


	//判断密码是必须是英文數组合
	function _isNumAndStr(str){
	     var regexpStr=/[a-zA-Z]+/;
	     var regexpNum=/\d+/;
	     var strFlag = regexpStr.test(str);
	     var numFlag = regexpNum.test(str);
	     if(strFlag&&numFlag)
	        return true;
	     return false;
    }

	/**
	 * get action url
	 */
	function getUrl(name){
		if(!_urlObj[name]){
			toast_tip(name+' 路径不存在！');
			return;
		}
		return _base_url+_urlObj[name];
	};
}
