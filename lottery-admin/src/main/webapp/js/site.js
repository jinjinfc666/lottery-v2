var SITE="http://localhost:8888/lottery-api";
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

var mReportURL = SITE + "/report/MReport";

var mReportNextTeamURL = SITE + "/report/MReportNextTeam";

var orderSourceURL = SITE + "/report/OrderSource";

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

var updateIpBlackListURL = SITE + "/ip-black-lists/updateIpBlackList";

var queryPlayTypeURL = SITE + "/settingPlayType/queryPlayType";

var querySysUserURL = SITE + "/SysUser/querySysUser";

var queryByUserId = SITE + "/SysUser/byUserId";

var queryDailySettlementURL = SITE + "/report/daily-settlement";

var queryDailySettlementByUserURL = SITE + "/report/queryDailySettlementByUser";

var performSettlementURL = SITE + "/report/performSettlement";

var queryLotteNumberURL = SITE + "/settingPlayType/queryLotteNumber/{lotteryType}/{playType}/{market}";

var updateLotteNumberURL = SITE + "/settingPlayType/updateLotteNumber/{lotteryType}/{playType}/{market}";

var updatePlayTypeURL = SITE + "/settingPlayType/updatePlayType";

var updateUserTsURL = SITE + "/users/ts";

var queryUserTsURL = SITE + "/users/ts/{userName}/{lotteryType}";

var querySmallFlowTypeURL = SITE + "/settings/querySmallFlowType";

var addLotteryTypeURL = SITE + "/settings/addLotteryType";

var SmallFlowTypeURL = SITE + "/settings/SmallFlowType";

var SmallLotteryConfigURL = SITE + "/settings/SmallLotteryConfig";

var SmallLotteryTypeURL = SITE + "/settings/SmallLotteryType";

var smallLuckyDrawURL = SITE + "/settings/SmallLuckyDraw";

var SmallPaymentPlatformURL = SITE + "/settings/SmallPaymentPlatform";

var SmallPayTypeURL = SITE + "/settings/SmallPayType";

var SmallPTypeClassicficationURL = SITE + "/settings/SmallPTypeClassicfication";

var smallSignInDayURL = SITE + "/settings/SmallSignInDay";

var smallSystemParametersURL = SITE + "/settings/SmallSystemParameters";

var smallWithdrawURL = SITE + "/settings/SmallWithdraw";

var isOrNoURL = SITE + "/settingPlayType/isOrNo";

var querySmallLotteryConfigURL = SITE + "/settings/querySmallLotteryConfig";

var querySmallLotteryTypeURL = SITE + "/settings/querySmallLotteryType";

var updateSmallLuckyDrawStateURL = SITE + "/settings/updateSmallLuckyDrawState";

var updateSmallPaymentPlatformStateURL = SITE + "/settings/updateSmallPaymentPlatformState";

var querySmallPayTypeURL = SITE + "/settings/querySmallPayType";

var updateSmallPayTypeStateURL = SITE + "/settings/updateSmallPayTypeState";

var querySmallPTypeClassicficationURL = SITE + "/settings/querySmallPTypeClassicfication";

var querySmallSignInDayURL = SITE + "/settings/querySmallSignInDay";

var updateSmallSignInDayStateURL = SITE + "/settings/updateSmallSignInDayState";

var querySystemParametersURL = SITE + "/settings/querySystemParameters";

var updateSystemParametersStateURL = SITE + "/settings/updateSystemParametersState";

var queryWithdrawURL = SITE + "/settings/queryWithdraw";

var updateWithdrawStateURL = SITE + "/settings/updateWithdrawState";

var playTypeStateURL = SITE + "/settingPlayType/state";

var playTypeMulSinFlagURL = SITE + "/settingPlayType/mulSinFlag";

var playTypeIsHiddenURL = SITE + "/settingPlayType/isHidden";

var bigTypeURL = SITE + "/settings/bigType";

var updateBigTypeStateURL = SITE + "/settings/updateBigTypeState";

var updateBigTypeURL = SITE + "/settings/updateBigType";

var updateSmallFlowTypeURL = SITE + "/settings/updateSmallFlowType";

var updateSmallFlowTypeSeqURL = SITE + "/settings/updateSmallFlowTypeSeq";

var querySmallLotteryConfigOneURL = SITE + "/settings/querySmallLotteryConfigOne";

var updateSmallLotteryConfigURL = SITE + "/settings/updateSmallLotteryConfig";

var updateSmallLotteryConfigSeqURL = SITE + "/settings/updateSmallLotteryConfigSeq";

var updateSmallLotteryTypeURL = SITE + "/settings/updateSmallLotteryType";

var updateSmallLuckyDrawURL = SITE + "/settings/updateSmallLuckyDraw";

var querySmallLuckyDrawURL = SITE + "/settings/querySmallLuckyDraw";

var updateSmallLuckyDrawSeqURL = SITE + "/settings/updateSmallLuckyDrawSeq";

var updateSmallPaymentPlatformURL = SITE + "/settings/updateSmallPaymentPlatform";

var querySmallPaymentPlatformURL = SITE + "/settings/querySmallPaymentPlatform";

var updateSmallPaymentPlatformSeqURL = SITE + "/settings/updateSmallPaymentPlatformSeq";

var updateSmallPayTypeURL = SITE + "/settings/updateSmallPayType";

var updateSmallPayTypeSeqURL = SITE + "/settings/updateSmallPayTypeSeq";

var updateSmallPTypeClassicficationURL = SITE + "/settings/updateSmallPTypeClassicfication";

var updateSmallPTypeClassicficationSeqURL = SITE + "/settings/updateSmallPTypeClassicficationSeq";

var updateSmallLotteryTypeSeqURL = SITE + "/settings/updateSmallLotteryTypeSeq";

var updateSmallSignInDayURL = SITE + "/settings/updateSmallSignInDay";

var updateSmallSignInDaySeqURL = SITE + "/settings/updateSmallSignInDaySeq";

var updateSystemParametersURL = SITE + "/settings/updateSystemParameters";

var updateWithdrawURL = SITE + "/settings/updateWithdraw";

var queryPayTypeURL = SITE + "/pay-types/queryPayType";

var payTypeIdPayChannelURL = SITE + "/pay-types/ByPayTypeIdPayChannel";

var updatePayChannelStateURL = SITE + "/pay-types/updatePayChannelState";

var updatePayChannelEnableURL = SITE + "/pay-types/updatePayChannelEnable";

var queryShowTypeURL = SITE + "/pay-types/queryShowType";

var queryTypeClasspayURL = SITE + "/pay-types/queryTypeClasspay";

var queryPayTypeIdURL = SITE + "/pay-types/queryPayTypeId";

var queryPayChannelIsOrNoURL = SITE + "/pay-types/queryPayChannelIsOrNo";

var queryEMAURL = SITE + "/pay-types/queryEMA";

var addPayChannelURL = SITE + "/pay-types/addPayChannel";

var uploadQRCodeURL = SITE + "/pay-types/uploadQRCode";

var queryTypeClassURL = SITE + "/pay-types/queryTypeClass";

var updatePayChannelURL = SITE + "/pay-types/updatePayChannel";

var updatePayChannelSeqURL = SITE + "/pay-types/updatePayChannelSeq";

var queryAllPayTypeURL = SITE + "/pay-types/queryAllPayType";

var updatePayTypeStateURL = SITE + "/pay-types/updatePayTypeState";

var updatePayTypeIsTpURL = SITE + "/pay-types/updatePayTypeIsTp";

var queryIsOrNoURL = SITE + "/pay-types/queryIsOrNo";

var queryCaptchaIsURL = SITE + "/pay-types/queryCaptchaIs";

var addPayTypeNameURL = SITE + "/pay-types/addPayTypeName";

var updatePayTypeURL = SITE + "/pay-types/updatePayType";

var queryAllPayTypeSeqURL = SITE + "/pay-types/queryAllPayTypeSeq";

var updatePayTypeSeqURL = SITE + "/pay-types/updatePayTypeSeq";

var queryPayTypeByIdURL = SITE + "/pay-types/queryPayTypeById";

var queryPayTypeByCodeNameURL = SITE + "/settings/queryPayTypeByCodeName";

var querySysRoleUserInfoURL = SITE + "/permission-control/querySysRoleUserInfo";

var updateSysRoleURL = SITE + "/permission-control/updateSysRole";

var addSysRoleURL = SITE + "/permission-control/addSysRole";

var resetLoginPwdURL = SITE + "/users/resetLoginPwd";

var addSysUserURL = SITE + "/users/sys-users";

var updatePwdURL = SITE + "/users/attrs/login-pwdAdmin";

var updateLoginPwdURL = SITE + "/users/attrs/login-pwd";

var querySysRoleURL = SITE + "/permission-control/querySysRole";

var updateSysAuthorityURL = SITE + "/SysUser/updateSysAuthority";

var updateUserTypeURL = SITE + "/users/updateUserType";

var reportDWTypeURL = SITE + "/report/DWD/DWType";

var reportDWDStateURL = SITE + "/report/DWD/DWDState";

var reportDWDURL = SITE + "/report/DWD";

var paymentOrderEndURL = SITE + "/payment/order/end";

var updateDepositStateURL = SITE + "/report/DWD/UpdateDepositState";

var delayPayoutURL = SITE + "/sys/oper/issue/order/{id}/delay-payout";
	
var orderCancelURL = SITE + "/sys/oper/order/{orderNum}/cancel";

var manualPayoutURL = SITE + "/sys/oper/order/{orderNum}/manual-payout";

var issueDeplayPayoutURL = SITE + "/sys/oper/issue/{issueNum}/delay-payout";

var manualDrawResultURL = SITE + "/sys/oper/{lotteryType}/issue/{issueNum}/manual-draw-result";

var getIssuesStateURL = SITE + "/issues/getIssuesState";

var getIssuesURL = SITE + "/issues/getIssues";

var issueRepayoutURL = SITE + "/sys/oper/issue/{issueNum}/re-payout";

var issuePayoutURL = SITE + "/sys/oper/issue/{issueNum}/payout";

var issueDisbaleURL = SITE + "/sys/oper/issue/{issueNum}/disbale";

var dwrRemarkURL = SITE + "/report/DWD/DWDRemark";

var withdrwaNotiesURL = SITE + "/users/withdraw/notices";

var querySmPanKouURL = SITE + "/settings/querySmPanKou";

var queryByBankURL = SITE + "/users/byUIBankList";

var queryByUserIdUserAccountURL = SITE + "/wallet/queryByUserIdUserAccount";

var userTransferURL = SITE + "/users/amount/transfer";

var updateUserAccountStateURL = SITE + "/wallet/updateUserAccountState";

var queryUserAccountURL = SITE + "/wallet/queryUserAccount";

var usersOperationAmountURL = SITE + "/users/operation/amount";

var queryByIdUserAccountURL = SITE + "/wallet/queryByIdUserAccount";

var queryMainWalletURL = SITE + "/wallet/queryMainWallet";

var queryAllUserInfoURL = SITE + "/users/queryAllUserInfo";

var resetFundPwdURL = SITE + "/users/resetFundPwd";

var sgnupRecRecordURL = SITE + "/signup/sgnupRecRecord";

var userFlowDetailTypeURL = SITE + "/report/userFlowDetail/type";

var userFlowDetailURL = SITE + "/report/userFlowDetail";

var lReportNextTeamURL = SITE + "/report/LReportNextTeam";

var statBetNumURL = SITE + "/report/statBetNum";

var lReportTeamSumURL = SITE + "/report/LReportTeamSum";





























