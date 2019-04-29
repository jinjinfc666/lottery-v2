//var SITE="http://localhost/lottery-api";
//var SITE="http://103.117.121.146/lottery-api";
var SITE = "http://localhost:8080/lottery-api";

var querySessionIdURL = SITE+"/captchas/query-sesionid"; //获取获取会话ID

var queryCaptchaCodeURL = SITE+"/captchas/verification-code-Img;jsessionid={sessionId}"; //获取验证码

var loginURL = SITE+"/oauth/token;jsessionid={sessionId}"; //用户登录

var queryNowUserInfoURL = SITE + "/users/queryNowUserInfo";

var queryUserURL = SITE + "/SysUser/queryGetByUserId";

var queryLotteTypesURL = SITE + "/report/loyTstRecord/lotteTypes";

var queryLottStateURL = SITE + "/report/loyTstRecord/LoyTstState";

var queryLotteryTerminalTypeURL = SITE + "/report/loyTstRecord/LoyTstTerminalType";

var queryLotteryRecordURL = SITE + "/report/loyTstRecord";
