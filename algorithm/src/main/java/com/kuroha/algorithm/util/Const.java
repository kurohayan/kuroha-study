package com.kuroha.algorithm.util;


/**
 * 常量类
 *
 * @author kuroha
 * @date 2019-04-02 15:52:18
 */
public interface Const {

	Integer PAGE_LENGTH = 10;
	/**
	 * 微信公众号二维码失效时间
	 */
	long WECHAT_OUT_TIME = 15L;
	/**
	 * 微信公众号二维码失效时间
	 */
	long WECHAT_MEUB_OUT_TIME = 5L;

	String MD5 = "MD5";

	String MASTER = "master";

	Integer REGISTRATION_POINT = 100;

	int SEND_NUM = 5;

	long TIME_DAY = 86400000;

	/**
	 * 成功
	 */
	String SUCCESS = "SUCCESS";
	String OK = "OK";
	/**
	 * 失败
	 */
	String FAIL = "FAIL";
	/**
	 * 参数错误
	 */
	String ERROR_PARAM = "PARAM_ERROR";
	/**
	 * 类型
	 */
	String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 10M
	 */
	int DEFAULT_MAX_POST_SIZE = 1024 * 1024 * 10;
	/**
	 * 15min
	 */
	int DEFAULT_SECONDS_OF_TOKEN_TIME_OUT = 900;

	String HTTP_FILE = "httpFile";

	// resultCode
	/**
	 * 成功
	 */
	String RESULT_CODE_SUCCESS = "0000";
	/**
	 * 失败
	 */
	String RESULT_CODE_FAIL = "0001";
	/**
	 * 系统错误
	 */
	String RESULT_CODE_ERROR = "0002";
	/**
	 * 系统错误
	 */
	String RESULT_CODE_TIME_OUT = "0005";
	/**
	 * 参数错误
	 */
	String RESULT_CODE_ERROR_PARAM = "0007";
	/**
	 * 权限错误
	 */
	String RESULT_CODE_ERROR_PERMISSION = "0003";
	/**
	 * 流量限制错误
	 */
	String RESULT_CODE_ERROR_FREQUENCY = "0004";
	/**
	 * 微信支付错误
	 */
	String RESULT_CODE_ERROR_WECHATPAY = "0010";
	/**
	 * 未登陆
	 */
	String RESULT_CODE_ERROR_LOGIN = "0014";
	/**
	 * 检测次数缺失
	 */
	String RESULT_CODE_ERROR_CD_NUM = "1000";
	/**
	 * 月卡已到限额
	 */
	String RESULT_CODE_ERROR_CD_TIME = "1001";
	/**
	 * 余额不足
	 */
	String RESULT_CODE_ERROR_MONEY = "0100";

	/**
	 * 短视频监测已存在相同链接任务
	 */
	String RESULT_CODE_MULTIPLE_TASK = "0123";

	String MULTIPLE_TASK = "已存在相同链接的监测任务";

	String ERROR_MONEY = "ERROR_MONEY";
	/**
	 *
	 */
	// resultMessage
	/**
	 * 成功
	 */
	String RESULT_MSG_SUCCESS = "操作成功";
	/**
	 * 失败
	 */
	String RESULT_MSG_FAIL = "操作失败";
	/**
	 * 系统错误
	 */
	String RESULT_MSG_ERROR = "系统错误";
	/**
	 * 参数错误
	 */
	String RESULT_MSG_ERROR_PARAM = "参数错误";
	/**
	 * 参数错误
	 */
	String RESULT_MSG_TIME_OUT = "签名错误或超时";
	/**
	 * 权限错误
	 */
	String RESULT_MSG_ERROR_PERMISSION = "权限错误";
	/**
	 * 流量限制错误
	 */
	String RESULT_MSG_ERROR_FREQUENCY = "拒绝访问";
	/**
	 * 微信支付错误
	 */
	String RESULT_MSG_ERROR_WECHATPAY = "未绑定微信";
	/**
	 * 用户未登录
	 */
	String RESULT_MSG_ERROR_LOGIN = "用户未登录";

	/**
	 * 余额不足
	 */
	String RESULT_MSG_ERROR_MONEY = "余额不足";
	String WECHAT_PAY = "wechatPay";
	String WECHAT_BIND_ERROR = "wechatBindError";

	String UNKNOWN = "unknown";

	String TICKET = "ticket";
}

