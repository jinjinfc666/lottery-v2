//var SITE="http://localhost:8080/lottery-api";
//var SITE="http://miss369.com/lottery-api";
var SITE="http://localhost/lottery-api";

var queryCaptchaCodeURL = SITE+"/captchas/verification-code-Img;jsessionid={sessionId}"; //获取验证码

var querySessionIdURL = SITE+"/captchas/query-sesionid"; //获取获取会话ID

var loginURL = SITE+"/oauth/token;jsessionid={sessionId}"; //用户登录

var refreshTokenURL = SITE+"/oauth/token"; //刷新token

var logoutURL = SITE+"/security/logout"; //退出登录

var register=SITE+"lottery/registerMember.do"; //注册

var queryBettingIssueURL = SITE+"/lotteries/{lotteryType}/betting-issue";  //获取优惠列表

var queryIssueRecBriefURL = SITE+"/issues/getIssuesBrief";

var findPwdByMobile=SITE+"changePwdMember!findPwdByMobile.do";  //忘记密码，短信找回

var findPwdByEmail=SITE+"changePwdMember!findPwdByEmail.do";  //忘记密码，短信找回

var modifyLoginPwdURL = SITE+"/users/attrs/login-pwd";  //修改登录密码

var modifyFundPwdURL = SITE+"/users/attrs/fund-pwd";  //设置提现密码

var modifyWithdrawPwd=SITE+"member!updateWithdrawPwd.do";  //修改提现密码

var getMemberSecInfo=SITE+"member!loadSecurityInfo.do";  //获取用户信息

var withdrawSubmit=SITE+"withdraw.do"; //用户提现

//var getBankList=SITE+"mBank!list.do"; //获取银行卡列表

var bandingCard=SITE+"mBank!addCard.do"; //绑定银行卡

var updateMemberInfoURL = SITE+"/users/updateUserInfo";  //修改用户信息

var getIndexGame=SITE+"slot!getSlotCatWithRecommendedGameList.do";  //获取首页游戏

var getGameList=SITE+"slot!getGameList.do";  //获取游戏列表

var getNotice=SITE+"publicAnnouncement!sysAnnouncements.do";  //获取站内信

var applyQrURL = SITE+"/users/{userName}/verify/phone-apply";  //发送手机验证码

var verifyPhoneURL = SITE+"/users/{userName}/verify/phone";  //绑定手机号

var queryUserInfoURL = SITE+"/users/info";  //获取账户信息

var queryUserAccURL = SITE+"/wallet/queryByUserIdUserAccount?userId={userId}";  //获取战虎信息

var memFundTransfer=SITE+"memFundTranfer.do";  //账户转账

var getBalance=SITE+"member!loadIDBals.do"; //获取账户余额

var bettingRecordURL = SITE+"/report/loyTstRecord";

var bettingRecordBriefURL = SITE+"/report/bettingRecBrief";

var queryAccOperationURL = SITE+"/report/userFlowDetail/type";

var accRecordURL = SITE+"/report/userFlowDetail";

var checkIn=SITE+"member!checkIn.do"; //用户签到

var checkBirthday=SITE+"member!checkBirthday.do"; //检测用户生日

var drawBirthday=SITE+"member!drawBirthday.do"; //领取生日彩金

var checkTyj=SITE+"member!checkTyj.do"; //检测是否可以领取体验金

var withdrawTyj=SITE+"member!drawTyj.do"; //领取体验金

var checkCanUpgrade=SITE+"member!checkCanUpGrade.do"; //检测是否可以升级

var memberUpGrade=SITE+"member!memberUpGrade.do"; //用户升级

var getRechargeChannel=SITE+"channel!channel.do"; //获取充值渠道

var rechargeChannelSubmit=SITE+"channelDeposit.do"; //充值提交

var getAeDemoUrl=SITE+"aeConfig!getDemoUrl.do";  //获取AE的试玩地址

var getAeRealUrl=SITE+"ae!getGameUrl.do";  //获取AE的正式地址

var getTcGameUrl=SITE+"tc!getGameUrl.do";  //获取TC的游戏地址

var getMgconfig=SITE+"mgConfig.do";  //获取MG游戏配置

var getMgGameInfo=SITE+"mg!getGameInfo.do";  //获取MG游戏信息

var getTtgConfig=SITE+"ttgConfig.do"; //获取TTG游戏配置

var getTtgToken=SITE+"ttg!getToken.do"; //获取TTG的token

var getPtConfig=SITE+"pt!getGameInfo.do";   //获取pt的配置信息

var getGameListBytitle=SITE+"slot!getGameListByTitle.do";  //关键字查询游戏名称

var queryOrderStatusURL = SITE+"/report/loyTstRecord/LoyTstState";

var bettingURL = SITE+"/lotteries/{lotteryType}/bet/zh/0/wallet/{walletId}";  //获取初始奖池

var queryProfitRecURL = SITE+"/report/MReport"; //个人报表

var queryIssueByNumberURL = SITE+ "/history/queryIssueByNumber?lotteryType={lotteryType}&number={recNum}";

var queryLotterysURL = SITE+"/settingPlayType/lotteTypes";

var queryPrizeRateURL = SITE+"/lotteries/{lotteryType}/play-type/{playType}/prize-rates";

var queryExpertPushNumURL = SITE+"/experts/push-numbers";


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
