var SITE="http://localhost/lottery-api";
//var SITE="http://103.117.121.146/lottery-api";
//var SITE = "http://localhost:8080/lottery-api";

var querySessionIdURL = SITE+"/captchas/query-sesionid"; //获取获取会话ID

var queryCaptchaCodeURL = SITE+"/captchas/verification-code-Img;jsessionid={sessionId}"; //获取验证码

var loginURL = SITE+"/oauth/token;jsessionid={sessionId}"; //用户登录

var refreshTokenURL = SITE + "/oauth/token";

var queryNowUserInfoURL = SITE + "/users/queryNowUserInfo";

var queryUserURL = SITE + "/SysUser/queryGetByUserId";

var queryLotteTypesURL = SITE + "/report/loyTstRecord/lotteTypes";

var queryLottStateURL = SITE + "/report/loyTstRecord/LoyTstState";

var queryLotteryTerminalTypeURL = SITE + "/report/loyTstRecord/LoyTstTerminalType";

var queryLotteryRecordURL = SITE + "/report/loyTstRecord";

var queryGeneralURL = SITE + "/users/queryGeneral";

var mReportNextTeamSMURL = SITE + "/report/MReportNextTeamSM";

//var queryUserSettlementURL = SITE + "/user-settlements";

//var performSettlementURL = SITE + "/user-settlements/{id}";

var queryAgentXYURL = SITE + "/users/queryAllUserAgentXY";

var queryUserXYURL = SITE + "/users/queryAllUserAgentXY";

var queryAgentEntrustURL = SITE + "/users/queryAllUserAgentEntrust";

var queryUserEntrustURL = SITE + "/users/queryAllUserAgentEntrust";

var queryUserInfoURL = SITE + "/users/queryUserInfo";

var queryAgentURL = SITE + "/users/queryAllUserAgent";

var queryPrizeTemplateURL = SITE + "/lotteries/prize-templates";

var createAgentURL = SITE + "/users/agents/{agentId}";

var queryTransferURL = SITE + "/transfers";

var queryAllIpBlackListURL = SITE + "/ip-black-lists/queryAllIpBlackList";

var addIpBlackListURL = SITE + "/ip-black-lists/addIpBlackList";

var deleteIpBlackListURL = SITE + "/ip-black-lists/{blackListId}";

var queryPlayTypeURL = SITE + "/settingPlayType/queryPlayType";

var querySysUserURL = SITE + "/SysUser/querySysUser";

var queryByUserId = SITE + "/SysUser/byUserId";

var queryDailySettlementURL = SITE + "/report/daily-settlement";

var queryGeneralURL = SITE + "/users/queryGeneral";

var queryGeneralURL = SITE + "/users/queryGeneral";

var queryDailySettlementByUserURL = SITE + "/report/queryDailySettlementByUser";

var performSettlementURL = SITE + "/report/performSettlement";

var queryLotteNumberURL = SITE + "/settingPlayType/queryLotteNumber/{lotteryType}/{playType}/{market}";

var updateLotteNumberURL = SITE + "/settingPlayType/updateLotteNumber/{lotteryType}/{playType}/{market}";

var updatePlayTypeURL = SITE + "/settingPlayType/updatePlayType";

var updateUserTsURL = SITE + "/users/ts";

var queryUserTsURL = SITE + "/users/ts/{userName}/{lotteryType}";
