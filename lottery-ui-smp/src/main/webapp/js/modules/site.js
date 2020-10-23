var SITE="http://localhost/lottery-api";
//var SITE="http://miss369.com/lottery-api";
//var SITE="http://192.168.1.9/lottery-api";

var queryCaptchaCodeURL = SITE+"/captchas/verification-code-Img;jsessionid={sessionId}"; //获取验证码

var querySessionIdURL = SITE+"/captchas/query-sesionid"; //获取获取会话ID

var loginURL = SITE+"/oauth/token;jsessionid={sessionId}"; //用户登录

var refreshTokenURL = SITE+"/oauth/token"; //刷新token

var logoutURL = SITE+"/security/logout"; //退出登录

var queryBettingIssueURL = SITE+"/lotteries/{lotteryType}/betting-issue";  //获取优惠列表

var queryIssueRecBriefURL = SITE+"/issues/getIssuesBrief";

var modifyLoginPwdURL = SITE+"/users/attrs/login-pwd";  //修改登录密码

var modifyFundPwdURL = SITE+"/users/attrs/fund-pwd";  //设置提现密码

var updateMemberInfoURL = SITE+"/users/updateUserInfo";  //修改用户信息

var applyQrURL = SITE+"/users/{userName}/verify/phone-apply";  //发送手机验证码

var verifyPhoneURL = SITE+"/users/{userName}/verify/phone";  //绑定手机号

var queryUserInfoURL = SITE+"/users/info";  //获取账户信息

var queryUserAccURL = SITE+"/wallet/queryByUserIdUserAccount?userId={userId}";  //获取战虎信息

var bettingRecordURL = SITE+"/report/loyTstRecord";

var bettingRecordBriefURL = SITE+"/report/bettingRecBrief";

var queryAccOperationURL = SITE+"/report/userFlowDetail/type";

var accRecordURL = SITE+"/report/userFlowDetail";

var queryOrderStatusURL = SITE+"/report/loyTstRecord/LoyTstState";

var bettingURL = SITE+"/lotteries/{lotteryType}/bet/zh/0/wallet/{walletId}";  //获取初始奖池

var queryProfitRecURL = SITE+"/report/MReport"; //个人报表

var queryIssueByNumberURL = SITE+ "/history/queryIssueByNumber?lotteryType={lotteryType}&number={recNum}";

var queryLotterysURL = SITE+"/settingPlayType/lotteTypes";

var queryPrizeRateURL = SITE+"/lotteries/{lotteryType}/play-type/{playType}/prize-rates";

var cancelOrderURL = SITE+"/users/cancel/bet-order";

var regUserURL = SITE+"/users/self/players";

var queryRechargeTypeURL = SITE+"/pay-types/QDPayType";

var querySysParamsURL = SITE+"/users/getSystemParameters";

var queryPayChannelURL = SITE+"/pay-types/QDPayChannel";

var depositURL = SITE+"/payment/pay-loading";

var addBankCardURL = SITE+"/users/userAddBank";

var queryBankCardURL = SITE+"/users/byUNUBankList";

var queryBankURL = SITE+"/settings/queryBankCodeList";

var withdrawURL = SITE+"/users/withdraw/apply";

var queryMainPsURL = SITE+"/lotteries/{lotteryType}/play-type/main-ps";

var queryYzpsURL = SITE+"/lotteries/{lotteryType}/play-type/yzps";

var queryEzpsURL = SITE+"/lotteries/{lottery-type}/play-type/ezdw/{numType}";

var querySzdwURL = SITE+"/lotteries/{lottery-type}/play-type/szdw";

var queryEzzhURL = SITE+"/lotteries/{lottery-type}/play-type/ezzh";

//弹出框口
function openWin(tip) {

    var iWidth=window.screen.availWidth-300;    //弹出窗口的宽度;
    var iHeight=window.screen.availHeight-200;  //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    var win=window.open("", "", 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');

    win.document.open();
    win.document.write("<p style='text-align: center;font-size: 20px;margin-top: 80px;'>"+tip+"</p>");
    win.document.close();

    return win;
};

function openWinBankInfo(bank_name, bank_acc, remark) {

    var iWidth=window.screen.availWidth-300;    //弹出窗口的宽度;
    var iHeight=window.screen.availHeight-200;  //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    var win=window.open("", "", 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');

    win.document.open();
    win.document.write("<div style=\"text-align: center\">");
    win.document.write("<div style=\"height: 50px;text-align: left;margin: 50px 20px 10px 50px;\">");
	    win.document.write("<span style=\"width:120px; margin: 0 10px 0 0; background: #009688; font-size: 50px; color: white;\">银行账号:</span><span style='text-align: center;font-size: 50px;margin-top: 80px;'>"+bank_acc+"</span><br>");
	win.document.write("</div>");
	win.document.write("<div style=\"height: 50px;text-align: left;margin: 50px 20px 10px 50px;\">");
	    win.document.write("<span style=\"width:120px; margin: 0 10px 0 0; background: #009688; font-size: 50px; color: white;\">账户姓名:</span><span style='text-align: center;font-size: 50px;margin-top: 80px;'>"+bank_name+"</span><br>");
	win.document.write("</div>");
	win.document.write("<div style=\"height: 50px;text-align: left;margin: 50px 20px 10px 50px;\">");
	    win.document.write("<span style=\"width:120px;padding: 0 100px 0 0; margin: 0 10px 0 0; background: #009688; font-size: 50px; color: white;\">附言:</span><span style='text-align: center;font-size: 50px;margin-top: 80px;'>"+remark+"</span>");
	win.document.write("</div>");
	win.document.write("</div>");
	    win.document.close();
	
    return win;
};

function openWinURL(url) {

    var iWidth=window.screen.availWidth-300;    //弹出窗口的宽度;
    var iHeight=window.screen.availHeight-200;  //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    var win=window.open(url, "", 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');

    /*win.document.open();
	    win.document.write("<p style='text-align: center;font-size: 20px;margin-top: 80px;'>"+tip+"</p>");
	    win.document.close();
	*/
    return win;
};

function openWinImage(imageURL) {

    var iWidth=window.screen.availWidth-300;    //弹出窗口的宽度;
    var iHeight=window.screen.availHeight-200;  //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    var win=window.open("", "", 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');

    win.document.open();
    win.document.write("<img src='"+imageURL+"'>");
    win.document.close();

    return win;
};

//修改密码的可见性
function chagePwdText(id,idIcon) {

    var obj=$("#"+id);
    var objIcon=$("#"+idIcon);
    var type=obj.attr('type');
    if(type=='password'){

        obj.attr('type','text');
        objIcon.removeClass('eyes close');
        objIcon.addClass('eyes');

    }else{

        obj.attr('type','password');
        objIcon.removeClass('eyes');
        objIcon.addClass('eyes close');

    }

}

function chageMobilePwdText(id,idIcon) {

    var obj=$("#"+id);
    var objIcon=$("#"+idIcon);
    var type=obj.attr('type');
    if(type=='password'){

        obj.attr('type','text');
        
        objIcon.addClass('opened');
    }else{

        obj.attr('type','password');
        objIcon.removeClass('opened');

    }

}

function ischina(str) {
    var reg=/^[\u4E00-\u9FA5]{2,4}$/;   /*定义验证表达式*/
    return reg.test(str);     /*进行验证*/
}

/*校验邮件地址是否合法 */
function isEmail(str) {
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    return reg.test(str);
}

function isZheng(str) {

    var reg=/^[0-9]*[1-9][0-9]*$/;
    return reg.test(str);

}

function isMobile(str) {

    var reg=/^1\d{10}$/;
    return reg.test(str);

}

var time=2;
var timer;

function showToast(msg) {

    $("#toastDiv").show();
    $("#toastTxt").html(msg);

    timer = setInterval("CountDown()", 1000);
    
}

function CountDown() {

    time--;
    if(time==0){

        $("#toastDiv").hide();
        time=2;
        clearInterval(timer);
    }
    
}

function showLoading(msg) {

    $("#loadTxt").html(msg);
    $("#loadDiv").show();
}

function hideLoading() {

    $("#loadDiv").hide();

}

//设置cookie
function setCookie(name,value)
{
    var Days = 365;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";
};

//读取cookie
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
};

//删除cookie
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString()+";path=/";
};
