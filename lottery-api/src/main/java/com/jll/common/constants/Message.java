package com.jll.common.constants;

public class Message {
	public final static String KEY_ERROR_CODE = "error_code";
	
	public final static String KEY_ERROR_MES = "error_mes";
	
	public final static String KEY_STATUS = "status";
	
	public final static String WINNING_MOUNT = "winningAmount";
	public final static String KEY_DATA = "data";
	public final static String KEY_DATA_TYPE = "data_type";
	public final static String KEY_DATA_REDIRECT = "redirect";
	public final static String KEY_DATA_QR_CODE = "qr_code";
	public final static String KEY_DATA_BANK_ACC = "bank_acc";
	public final static String KEY_DATA_BANK_NAME = "bank_name";
	public final static String KEY_EXPIRED_TIME = "expired_time";
	public final static String KEY_DEFAULT_PASSWORD = "default_password";
	public final static String KEY_REMAKE = "remark";
	
	
	public static enum status{
		SUCCESS(1),
		FAILED(0);
		
		private int value;
		
		private status(int value) {
			this.value = value;
		}
		
		public int getCode() {
			return value;
		}
	}
	
	//code : **-->module name;****-->errorNum
	public static enum Error{
		ERROR_COMMON_ERROR_PARAMS("000001", "Error parameter!!"),
		ERROR_COMMON_NO_PERMISSION("000002", "No permission!!"),
		ERROR_COMMON_ERROR_LOGIN("000003", "No pricipal!!"),		
		ERROR_COMMON_NO_ACCOUNT_OPERATION("000004", "Account operation is required!!"),		
		ERROR_COMMON_OTHERS("009999", "Others!!"),
		
		ERROR_PAYMENT_TLCLOUD_CONFIGURATION("090001", "configuration of tl-cloud is wrong!!"),
		ERROR_PAYMENT_TLCLOUD_FAILED_PUSH_ORDER("090002", "Failed to push order!!"),
		ERROR_PAYMENT_TLCLOUD_FAILED_CANCEL_ORDER("090003", "Failed to cancel order!!"),
		ERROR_PAYMENT_DEPOSIT_ERROR_ORDER("090004", "No order existing!!"),
		
		ERROR_PAYMENT_CAIPAY_FAILED_CANCEL_ORDER("090005", "configuration of cai-pay is wrong!!"),
		ERROR_PAYMENT_CAIPAY_FAILED_SIGNATURE_PARAMS("090006", "Can not sign the parameters!!"),
		ERROR_SYSTEM_CONFIG_NO_RECEIVER_BANK_CARD("090007", "No Receiver bank card is specified!!"),
				
		/******user module**************/
		ERROR_USER_EXISTING("010001", "The user is existing!!"),
		ERROR_USER_NO_GENERAL_AGENCY("010002", "No general agency!!"),
		ERROR_USER_NO_VALID_USER("010003", "No valid user!!"),
		ERROR_USER_INVALID_USER_NAME("010004", "Invalid user name!!"),
		ERROR_USER_INVALID_USER_LOGIN_PWD("010005", "Invalid user login pwd!!"),
		ERROR_USER_INVALID_USER_FUND_PWD("010006", "Invalid user fund pwd!!"),
		ERROR_USER_INVALID_EMAIL("010007", "Invalid Email!!"),
		ERROR_USER_INVALID_PHONE_NUMBER("010008", "Invalid Phone number!!"),
		ERROR_USER_INVALID_REAL_NAME("010009", "Invalid Real name!!"),
		ERROR_USER_INVALID_USER_TYPE("010010", "Invalid User type!!"),
		ERROR_USER_INVALID_PLAT_REBATE("010011", "Invalid Platform rebate!!"),
		ERROR_USER_FAILED_SAVE("010012", "Failed save user!!"),
		ERROR_USER_FAILED_REGISTER("010013", "Failed register user!!"),	
		
		
		ERROR_OLD_FUND_PWD_ERROR("010014", "Old fund password error!!"),
		ERROR_OLD_LOGIN_PWD_ERROR("010015", "Old login password error!!"),
		ERROR_MORE_UPDATE_REAL_NAME("010016", "The real name is binding and cannot be modified. If necessary, please contact customer service.!!"),
		ERROR_MORE_UPDATE_EMAIL("010017", "The email is binding and cannot be modified. If necessary, please contact customer service.!!"),
		ERROR_MORE_UPDATE_PHONE_NUM("010018", "The phone num is binding and cannot be modified. If necessary, please contact customer service.!!"),
		ERROR_BANK_CARD_HAS_BIND("010019", "The bank card  has been bound by other users. Please re-input the bank card.!!"),
		ERROR_USER_INVALID_BANK_CARD("010020", "Invalid bank card!!"),
		ERROR_USER_MORE_BIND_BANK_CARD("010021", "Users bind up to %d bank cards!!"),
		ERROR_USER_FAILED_RESET_LOGIN_PWD_SMS("010022", "Failed Reset login password by SMS!!"),
		ERROR_USER_FAILED_RESET_LOGIN_PWD_EMAIL("010023", "Failed Reset login password by Email!!"),
		ERROR_PROMS_OTHER_CONDITION_DEPOSIT_DISSATISFY("010024", "Participation in activities must meet the minimum deposit amount of %s, your current deposit amount of %s !!"),
		ERROR_PROMS_OTHER_CONDITION_FLOWING_DISSATISFY("010025", "Participation in activities must meet the minimum flowing water rate of %s times, your current flowing water rate of %s times!!"),
		ERROR_PROMS_ONLY_ONCE_DISSATISFY("010026", "The activity can only take part in one time!!"),
		ERROR_PROMS_OTHER_CONDITION_BET_AMT_DISSATISFY("010027", "Participation in activities must meet the minimum bet amount of %s times, your current bet amount of %s times!!"),
		ERROR_USER__WALLET_INVALID("010028", "Wallet is invalid!!"),
		ERROR_USER_POINT_NOT_ENOUGH("010029", "User point not enough!!!"),
		ERROR_PROMS_INVALID("010030", "This promo invalid!!!"),
		ERROR_ORDER_ERROR("010031", "This order info error!!!"),
		ERROR_WTD_PWD_ERROR("010032", "Withdrawal password error!!!"),
		ERROR_WTD_BIND_BANK_CARD("010033", "Withdrawal befor please bind bank card!!!"),
		ERROR_WTD_BANK_CARD_ERROR("010034", "Withdrawal bank card info error!!!"),
		ERROR_WTD_AMT_ERROR("010035", "Minimum withdrawals %s, maximum withdrawals %s!!!"),
		ERROR_WTD_TIMES_ERROR("010036", "Daily withdrawals can be %s times,You can withdrawals %s times today!!!"),
		ERROR_USER_BALANCE_NOT_ENOUGH("010037", "User balance not enough !!!"),
		ERROR_USER_TRANS_RED_WALLET_FAIL("010038", "The current user flow is %s, less than %s, transaction fail !!!"),
		ERROR_WALLET_IS_FREEZE("010039", "This wallet is freeze!!!!"),
		ERROR_SAME_IP_LIMIT_MAX_REG("010040", "The same IP(%s) can only register %s demo users!!!!"),
		ERROR_DEMO_USER_DISABLE_FUN("010041", "Demo user disable current function!!!!"),		
		ERROR_USER_NO_BANKCARD("010042", "user no bankcard!!!!"),
		ERROR_USER_NO_AGENCY("010043","user no agency!!!"),
		ERROR_USER_PLATREBATE_WRONG("010044","platrebate wrong!!!"),
		ERROR_USER_SIGNED_IN("010045","Signed in!!!"),
		ERROR_PROMS_OTHER_CONDITION_WITHDRAW_DISSATISFY("010046", "The maximum amount of cash to participate in the event is %s, Your cash withdrawal is %s, You have exceeded the maximum amount of cash !!"),
		ERROR_USER_ACTIVITY_OVER("010047","The activity is over!!!"),
		ERROR_USER_HAS_BEEN_LOCKED("010048","Your account has been locked. Please try to log in again after 5 minutes! ! !"),
		ERROR_USER_HAS_BEEN_LOCKED_SERVICE("010049","Your account has been locked, please contact customer service! ! !"),
		ERROR_USER_HAS_BEEN_DESTROY_SERVICE("010050","please contact customer service! ! !"),
		ERROR_USER_FAILED_TO_GET_VERIFICATION_CODE("010051","Failed to get verification code, please re-acquire! ! !"),
		ERROR_USER_BANK_CARD_REAL_NAME("010052","Please fill in your real name before you can add a bank card.! ! !"),
		ERROR_USER_CONSUMPTION_LESS_THAN_LIMITION("010053","The consumption amount is less than limit!!!!"),
		ERROR_USER_NO_PUSH_CONFIG("010054", "Have not push number configuration yet!!"),
		
		/**************third party*************************/
		ERROR_TP_INVALID_SMS("020001", "Invalid SMS!!"),
		ERROR_TP_INVALID_SMS_URL("020002", "Invalid SMS URL!!"),
		ERROR_TP_SENDING_EMAIL("020003", "Failed sending Email!!"),
		ERROR_TP_INVALID_EMAIL("020004", "Invalid Email!!"),
		
		/************************game module*******************************/
		ERROR_GAME_LOTTERY_TYPE_INVALID("030001", "Invalid lottery type!!"),
		ERROR_GAME_END("030002", "The game is over today!!"),
		ERROR_GAME_NO_START("030003", "The game does not yet start!!"),
		ERROR_GAME_MISSTING_PLAY_TYPE("030004", "Please first configure the play type!!"),
		ERROR_GAME_INVALID_ZH_FLAG("030005", "Invalid zh flag!!"),
		ERROR_GAME_NO_ORDER("030006", "At least one order is required!!"),
		ERROR_GAME_MULTIPLE_ORDERS_NOT_ALLOWED("030007", "Only one order is allowed under non zhui hao!!"),
		ERROR_GAME_EXPIRED_ISSUE("030008", "The issue is expired!!"),
		ERROR_GAME_BAL_INSUFFICIENT("030009", "Balance is insufficient!!"),
		ERROR_GAME_NO_PLAY_TYPE("030010", "No Play Type!!"),
		ERROR_GAME_INVALID_PLAY_TYPE("030011", "Invalid Play Type!!"),
		ERROR_GAME_INVALID_BET_NUM("030012", "Invalid Play Bet number!!"),
		ERROR_GAME_FAILED_PROCESS_BETTING_NUM("030013", "Failed to process beting number!!"),
		ERROR_GAME_NO_ORDERS_TO_DELAY_THE_AWARD("030014", "There are no orders to delay the award.!!"),
		ERROR_GAME_ORDER_IS_ALREADY_NON_DELAYED_AWARD("030015", "This order is already a non-delayed award.!!!"),
		ERROR_GAME_ORDER_NO_DRAW("030016", "The order corresponding to this order has not yet been awarded, so it is impossible to make a manual award!!!"),
		ERROR_GAME_ORDER_DOES_NOT_EXIST("030017", "Order does not exist!!!"),
		ERROR_GAME_PAYOUT_UP_LIMIT("030018", "Pay out up to limit!!!"),
		
		/*** system model****/
		ERROR_MESSAGE_TITLE_IS_EMPTY("040001", "Message title is empty!!"),
		ERROR_MESSAGE_CONTENT_IS_EMPTY("040002", "Message content is empty!!"),
		ERROR_NOTIFY_RECEIVER_TYPE_ERROR("040003", "Notify receiver type error!!"),
		ERROR_NOTIFY_RECEIVER_ERROR("040004", "Notify receiver error!!"),
		
		ERROR_SYSTEM_AUTH_NO_ACCESS_PERMISSION("050001", "No Permission to access resource!!"),
		ERROR_SYSTEM_INVALID_BETTING_TIMES("050002", "Invalid betting times!!"),
		ERROR_SYSTEM_INVALID_BETTING_MONEY_UNIT("050003", "Invalid betting money unit!!"),
		ERROR_SYSTEM_LOG_OUT("050004", "Error log out!!"),
		/**************pay model*************************/
		ERROR_MESSAGE_PAY_TYPE_DISABLE("060001", "This pay type is disable!!!"),
		ERROR_MESSAGE_PAYMENT_AMOUNT_MORE("060002", "This pay type payment amount more than maximum %d !!!"),
		
		/**************log module*************************/
		ERROR_LOGIN_INVALID_CAPTCHA("070001", "Invalid captcha!!!"),
		
		/**************Issue module*************************/
		ERROR_ISSUE_INVALID_STATUS("080001", "Invalid issue status!!!"),
		ERROR_ISSUE_NOT_ALLOWED_MANUAL_DRAW_RESULT("080002", "Not allowed to manual draw result!!!"),
		/**************PlayType module*************************/
		ERROR_PLAYTYPE_ALREADY_EXISTS("100001","playType already exists!!!"),
		ERROR_PLAYTYPE_DOES_NOT_EXIST("100002","playType does not exist!!!"),
		/**************DepositApplication Or withdrawApplication module*************************/
		ERROR_DEPOSITAPPLICATION_DOES_NOT_EXIST("110001","DepositApplication does not exist!!!"),
		ERROR_WITHDRAWAPPLICATION_DOES_NOT_EXIST("110002","withdrawApplication does not exist!!!"),
		/**************IP module*************************/
		ERROR_IP_ALREADY_EXISTS("120001","ip already exists!!!"),
		ERROR_IP_DATA_DOES_NOT_EXIST_MODIFIED_IP_ALREADY_EXISTS("120002","Data does not exist or Modified ip already exists!!!"),
		/**************payType module*************************/
		ERROR_PAYTYPE_ALREADY_EXISTS("130001","payType already exists!!!"),
		ERROR_PAYTYPE_DOES_NOT_EXIST("130002","payType does not exist!!!"),
		/**************payChannel module*************************/
		ERROR_PAYCHANNEL_DOES_NOT_EXIST("140001","payChannel does not exist!!!"),
		ERROR_PAYCHANNEL_ALREADY_EXISTS("140002","payChannel already exists!!!"),
		/**************payChannel module*************************/
		ERROR_SYSROLE_ALREADY_EXISTS("150001","SysRole already exists!!!"),
		ERROR_SYSROLE_DOES_NOT_EXIST("150002","SysRole does not exist!!!"),
		/**************SysCode module*************************/
		ERROR_SYSCODE_ALREADY_EXISTS("160001","SysCode already exists!!!"),
		ERROR_CODE_VALUE_ALREADY_EXISTS("160002","Code value already exists!!!"),
		/**************userBank module*************************/
		ERROR_USERBANK_DOES_NOT_EXIST("170001","userBank does not exist!!!"),
		/*************************Login module******************************/
		ERROR_LOGIN_ILLEGAL_USER_NO_LOGIN("180001","Illegal user, no login!!!"),
		ERROR_LOGIN_FAILED_TO_GET_VERIFICATION_CODE("180002","Failed to get verification code!!!"),
		
		
		/*************************User settlement module******************************/
		ERROR_SETTLEMENT_NON_EXISTING("190001","Specified Id is not existing!!!"),
		
		/*************************withdraw******************************/
		ERROR_TRANSFER_VALID_FROM_USER("200001","System user can't transfer money to others!!!");
		
		private String code;
		
		private String errorMes;
		
		private Error(String code, String errorMes) {
			this.code = code;
			this.errorMes = errorMes;
		}
		
		public String getCode() {
			return this.code;
		}
		
		public String getErrorMes() {
			return this.errorMes;
		}
		
		public static Error getErrorByCode(String code) {
			Error[] errors = Error.values();
			for(Error err: errors) {
				if(err.getCode().equals(code)) {
					return err;
				}
			}
			return null;
		}
	}
}
