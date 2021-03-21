//json格式的时间转换为Date
function ConvertJSONDateToJSDateObject(JSONDateString) {
    var date = new Date(parseInt(JSONDateString.replace("/Date(", "").replace(")/", ""), 10));
    var year = date.getFullYear();
    var month = date.getMonth + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    if(hour<10){
        hour="0"+hour;
    }
    if(minute<10){
        minute="0"+minute;
    }
    if(second<10){
       second="0"+second; 
    }
    var datastr = year + "-" + month + "-" + currentDate + " " + hour + ":" + minute + ":" + second;
    return datastr;
}
//比较时间大小
function CompareDate(d1,d2)
{
  return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));
}
//默认时间
function getNowFormatDate() {
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
}
//昨天
function yestoday(){
    var day1 = new Date();
    day1.setTime(day1.getTime()-24*60*60*1000);
    var s1 = day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate();
    return s1;
}
//明天
function tomorrow(){
    var day1 = new Date();
    day1.setTime(day1.getTime()+24*60*60*1000);
    var s1 = day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate();
    return s1;
}
//今天
function getNowFormatDate() {
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
}
//错误信息匹配和存储
function getErroe(demo){
    var m = new Map(
            [
                ["000001", "参数错误!!"],
                ["000002", "没有权限!!"],
                ["000003", "用户存在!!"],
                ["000004", "账户操作类型必需!!"],
                ["009999", "其他错误!!!"],
                
                ["090001", "通略云配置错误!!"],
                ["090002", "保存投注订单失败!!"],
                ["090003", "取消投注订单失败!!"],
                ["090004", "订单不存在!!"],
                ["090005", "彩付配置错误!!"],
                ["090006", "不能识别参数!!"],
                ["090007", "没有指定收款银行卡!!"],
                /******user module**************/
                ["010001", "用户已经存在!!"],
                ["010002", "没有总代!!"],
                ["010003", "用户无效!!"],
                ["010004", "用户名无效!!"],
                ["010005", "无效登录密码!!"],
                ["010006", "无效资金密码!!"],
                ["010007", "无效Email!!"],
                ["010008", "无效电话号码!!"],
                ["010009", "无效真实姓名!!"],
                ["010010", "无效用户类型!!"],
                ["010011", "无效平台返点!!"],
                ["010012", "保存用户信息失败!!"],
                ["010013", "用户注册失败!!"],
                
                ["010014", "旧资金密码错误!!"],
                ["010015", "旧登陆密码错误!!"],
                ["010016", "相同真实姓名已经绑定，请联系客服帮您解决问题!!"],
                ["010017", "相同 Email 已绑定，请换其他 Email 地址!!"],
                ["010018", "相同电话号码已绑定，请换其他电话号码!!"],
                ["010019", "相同银行卡已经绑定，请输入其他银行卡!!"],
                ["010020", "无效银行卡!!"],
                ["010021", "绑定银行卡数量已达上限!!"],
                ["010022", "短信重置密码失败!!"],
                ["010023", "Email 重置密码失败!!"],
                ["010024", "当前充值金额不满足最低下限要求 !!"],
                ["010025", "不满足最小流水倍数要求!!"],
                ["010026", "活动只能参加一次!!"],
                ["010027", " 当前投注金额，不满足最低投注下限要求!!"],
                ["010028", "无效钱包!!"],
                ["010029", "用户积分不够!!!"],
                ["010030", "无效活动!!!"],
                ["010031", "订单信息错误!!!"],
                ["010032", "资金密码错误!!!"],
                ["010033", "未绑定提款银行卡，请先绑定银行卡!!!"],
                ["010034", "提款银行卡信息错误!!!"],
                ["010035", "未达到最小提款金额!!!"],
                ["010036", "已达到日提款次数!!!"],
                ["010037", "用户余额不足!!!"],
                ["010038", "操作失败!!!"],
                ["010039", "钱包已经冻结!!!"],
                ["010040", "同一个IP，每天只能注册一个试玩用户!!!"],
                ["010041", "此功能不对试玩用户开放!!!"],
                ["010042", "这个用户没有银行卡!!!!"],
                ["010043","这个用户不是代理!!!"],
                ["010044","平台返点错误!!!"],
                ["010045","一天只能签到一次!!!"],
                ["010046", "提现金额已经超出限制，无法参加此活动 !!"],
                ["010047","该活动已经结束!!!"],
                ["010048","您的账号已处于锁定状态. 请再五分钟后登录! ! !"],
                ["010049","您的账号已处于锁定状态, 请联系客服! ! !"],
                ["010055","请输入退水和占成! ! !"],
                ["010056","占成应该低于上级代理的对应值! ! !"],
                ["010057","退水和占成应该大于0! ! !"],
                /**************third party*************************/
                ["020001", "无效的验证短信验证码!!"],
                ["020002", "无效的短信 URL!!"],
                ["020003", "Email 发送失败!!"],
                ["020004", "无效 Email!!"],
                /************************game module*******************************/
                ["030001", "无效彩种!!"],
                ["030002", "此彩种今天已经结束，欢迎明天继续!!"],
                ["030003", "游戏还未开始!!"],
                ["030004", "请先配置玩法!!"],
                ["030005", "无效的追号标识!!"],
                ["030006", "请提交至少1个订单!!"],
                ["030007", "在非追号模式下只允许一个订单!!"],
                ["030008", "期次已经过期!!"],
                ["030009", "余额不足!!"],
                ["030010", "还没有玩法!!"],
                ["030011", "无效玩法!!"],
                ["030012", "无效投注号码!!"],
                ["030013", "投注号码处理失败!!"],
                ["030014", "没有需要延迟派奖的订单!!!"],
                ["030015", "这个订单已经是非延迟派奖!!!"],
                ["030016", "人工派奖失败，这笔订单对应的期次还没有获取到开奖号码，所以无法进行人工派奖!!!"],
                ["030017", "人工派奖失败，订单不存在!!!"],
                /*** system model****/
                ["040001", "标题不能为空!!"],
                ["040002", "内容不能为空!!"],
                ["040003", "接收人类型错误!!"],
                ["040004", "接收人错误!!"],
                
                ["050001", "没有访问此资源的权限!!"],
                ["050002", "无效投注倍数!!"],
                ["050003", "无效金额单位!!"],
                ["050004", "登出错误!!"],
                /**************pay model*************************/
                ["060001", "此支付类型已经无效!!!"],
                ["060002", "支付金额已经超出系统设置最大金额!!!"],
                /**************log module*************************/
                ["070001", "无效验证吗!!!"],
                /**************Issue module*************************/
                ["080001", "无效的期次状态!!!"],
                ["080002", "不允许人工开奖!!!"],
                /**************PlayType module*************************/
                ["100001","玩法已经存在!!!"],
                ["100002","玩法不存在!!!"],
                /**************DepositApplication Or withdrawApplication module*************************/
                ["110001","这条充值申请不存在或以过期!!!"],
                ["110002","这条提现申请不存在或以过期!!!"],
                /**************IP module*************************/
                ["120001","IP已存在!!!"],
                /**************payType module*************************/
                ["130001","充值方式已存在!!!"],
                ["130002","充值方式不存在!!!"],
                /**************payChannel module*************************/
                ["140001","充值渠道不存在!!!"],
                ["140002","充值渠道已存在!!!"],
                /**************payChannel module*************************/
                ["150001","系统角色已存在!!!"],
                ["150002","系统角色不存在!!!"],
                /**************SysCode module*************************/
                ["160001","代码类型已经存在!!!"],
                ["160002","代码值已经存在!!!"],
                /**************userBank module*************************/
                ["170001","此银行卡不存在!!!"],
                /*************************Login module******************************/
                ["180001","非法用户，禁止登陆!!!"],
                ["180002","获取验证码失败,请刷新验证码!!!"],
                
                
                ["009999","其他错误!!!"]
                
                
            ]
            );
    return m.get(demo);
}
function getDifferenceTime(startTime,endTime){
    var dateDiff = endTime.getTime() - startTime;//时间差的毫秒数
    var dateDiff1=dateDiff/1000/60;
    return dateDiff1;
}
function updateToken(){
    var startTime=sessionStorage.getItem("startTime");
    var nowTime=new Date();
    var dateDiff1=getDifferenceTime(startTime,nowTime);
    if(dateDiff1>=25&&dateDiff1<=30){
        var refresh_token=sessionStorage.getItem("refresh_token");
        var domain=parseDomain1();
        var url= refreshTokenURL;
        $.ajax({
            url: url,
            type: "POST",
            async: false, // 设置同步方式
            data: {
                grant_type:"refresh_token",
                client_id:"lottery-admin",
                client_secret:"secret_1",
                refresh_token:refresh_token
            },
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                checkError(XMLHttpRequest,textStatus);
            },
            success: function (data) {
                sessionStorage.setItem("access_token",data.access_token);
                sessionStorage.setItem("refresh_token",data.refresh_token);
                sessionStorage.setItem("startTime",new Date().getTime());
            }
        });
    }else if(dateDiff1>30){
        sessionStorage.removeItem('access_token');
        sessionStorage.removeItem('refresh_token');
        sessionStorage.removeItem('startTime');
        var domain1=parseDomain2();
        var url = domain1+"/lottery-admin/index.html";
        top.location.href = url;
        return false;
    }else{
        return true;
    }
}
function parseDomain1() {
    var currURL = location.href;
    var domain = '';
    if (currURL.indexOf("http") >= 0) {
        if (currURL.indexOf("/", currURL.indexOf("http") + 7) > 0) {
            domain = currURL.substring(0, currURL.indexOf("/", currURL
                    .indexOf("http") + 7));
        } else {
            domain = currURL;
        }
    }
    if (typeof domain == 'undefined' || domain.length == 0) {
        return '';
    }
    if (domain.indexOf(":", 7) > 0) {
        domain = domain.substring(0, domain.indexOf(":", 7));
    }
    return domain;
}
function parseDomain2() {
    var currURL = location.href;
    var domain = '';
    if (currURL.indexOf("http") >= 0) {
        if (currURL.indexOf("/", currURL.indexOf("http") + 7) > 0) {
            domain = currURL.substring(0, currURL.indexOf("/", currURL
                    .indexOf("http") + 7));
        } else {
            domain = currURL;
        }
    }
    if (typeof domain == 'undefined' || domain.length == 0) {
        return '';
    }
    return domain;
}
function refreshLogin() {
    var access_token=sessionStorage.getItem("access_token");
    var token="Bearer " + access_token;
    var domain=parseDomain();
    var url=domain+"/lottery-api/security/logout";
    $.ajax({
        url: url,
        type: "GET",
        async: false, // 设置同步方式
        data: {
        },
        dataType: "json",
//                    beforeSend: function(xhr) {
//                        xhr.setRequestHeader("Authorization");
//                    },
//                    headers:{"Authorization":token},
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Authorization", token);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            checkError(XMLHttpRequest,textStatus);
        },
        success: function (data) {
            if(data.status=="1"){;
                var domain1=parseDomain2();
                var url = domain1+"/lottery-admin/index.html";
                top.location.href = url;
            }else{
                var domain1=parseDomain2();
                var url = domain1+"/lottery-admin/index.html";
                top.location.href = url;
            }
        }
    });
}
function checkError(XMLHttpRequest,textStatus){
    if(XMLHttpRequest.responseJSON.error=="invalid_token"){
         alert("登录过期！！！");
        var domain1=parseDomain2();
        var url = domain1+"/lottery-admin/index.html";
        top.location.href = url;
        return false;
    }else if(XMLHttpRequest.responseJSON.error=="unauthorized"){
        alert(getErroe(XMLHttpRequest.responseJSON.error_description));
         var domain1=parseDomain2();
         var url = domain1+"/lottery-admin/index.html";
         top.location.href = url;
        return false;
    }else{
        alert("系统错误！！！");
        var domain1=parseDomain2();
        var url = domain1+"/lottery-admin/index.html";
        top.location.href = url;
        return false;
    }
}
function startTimeStr(startTime){
    return startTime+" 00:00:00";
}
function endTimeStr(endTime){
    return endTime+" 23:59:59";
}

function parseParam(url, paraName) {
	var parentUrl = url;
	var userName = "";
	var usernameIndx = parentUrl.indexOf(paraName);
	if (usernameIndx < 0) {
		return "";
	}
	usernameIndxEnd = parentUrl.indexOf("&", usernameIndx);
	if (usernameIndxEnd < 0) {
		userName = parentUrl.substring(usernameIndx);
	} else {
		userName = parentUrl.substring(usernameIndx, usernameIndxEnd);
	}

	if (typeof userName == "undefined" || userName == "") {
		return "";
	}

	userName = userName.split("=")[1];
	if (typeof userName == "undefined" || userName == "") {
		return "";
	}
	return userName;
}

function initPageing(data){
	if(data.pageIndex == 1){
    	$('#mainP').attr('disabled',true);
    	$('#preP').attr('disabled',true);
    }else{
    	$('#mainP').attr('disabled',false);
    	$('#preP').attr('disabled',false);
    }
    
    if(data.pageIndex == data.totalPages){
    	$('#nextP').attr('disabled',true);
    	$('#lastP').attr('disabled',true);
    }else{
    	$('#nextP').attr('disabled',false);
    	$('#lastP').attr('disabled',false);
    }
}