package com.kuroha.algorithm.util;

import com.alibaba.fastjson.JSONObject;
import com.kuroha.algorithm.entity.Page;

/**
 * 默认返回工具类
 * @author kuroha
 */
public class ResultUtil {

	public static String returnErrorPermission() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code", Const.RESULT_CODE_ERROR_PERMISSION);
		jsonObject.put("msg",Const.RESULT_MSG_ERROR_PERMISSION);
		return jsonObject.toJSONString();
	}

	public static String returnNotLogin() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code", Const.RESULT_CODE_ERROR_LOGIN);
		jsonObject.put("msg", Const.RESULT_MSG_ERROR_LOGIN);
		return jsonObject.toJSONString();
	}

	public static String returnError(String code, String msg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",code);
		jsonObject.put("msg",msg);
		return jsonObject.toJSONString();
	}
	public static String returnError(String msg) {
		return returnError(Const.RESULT_CODE_ERROR,msg);
	}
	public static String returnError() {
		return returnError(Const.RESULT_CODE_ERROR,Const.RESULT_MSG_ERROR);
	}


	public static String returnFail(String code, String msg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",code);
		jsonObject.put("msg",msg);
		return jsonObject.toJSONString();
	}

	public static String returnFail(String code, String msg,Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",code);
		jsonObject.put("msg",msg);
		jsonObject.put("data",data);
		return jsonObject.toJSONString();
	}

	public static String returnFail(String msg) {
		return returnFail(Const.RESULT_CODE_FAIL,msg);
	}
	public static String returnFail() {
		return returnFail(Const.RESULT_CODE_FAIL,Const.RESULT_MSG_FAIL);
	}

	public static String returnParamError() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",Const.RESULT_CODE_ERROR_PARAM);
		jsonObject.put("msg",Const.RESULT_MSG_ERROR_PARAM);
		return jsonObject.toJSONString();
	}

	public static JSONObject returnFail2(String code, String msg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",code);
		jsonObject.put("msg",msg);
		return jsonObject;
	}
	public static JSONObject returnFail2(String msg) {
		return returnFail2(Const.RESULT_CODE_FAIL,msg);
	}
	public static JSONObject returnFail2() {
		return returnFail2(Const.RESULT_CODE_FAIL,Const.RESULT_MSG_FAIL);
	}

	public static String returnSuccess() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",true);
		jsonObject.put("code", Const.RESULT_CODE_SUCCESS);
		jsonObject.put("msg", Const.RESULT_MSG_SUCCESS);
		return jsonObject.toJSONString();
	}

    public static String returnDefault(boolean flag, String code, String msg, Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",flag);
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("data",data);
		return jsonObject.toJSONString();
	}

	public static String returnDefault(boolean flag, String code, String msg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",flag);
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		return jsonObject.toJSONString();
	}

    public JSONObject returnSuccess2() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",true);
		jsonObject.put("code", Const.RESULT_CODE_SUCCESS);
		jsonObject.put("msg", Const.RESULT_MSG_SUCCESS);
		return jsonObject;
	}

	public static String returnSuccess(Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",true);
		jsonObject.put("code", Const.RESULT_CODE_SUCCESS);
		jsonObject.put("msg", Const.RESULT_MSG_SUCCESS);
		jsonObject.put("data", data);
		return jsonObject.toJSONString();
	}

	public static String returnSuccess(Page page, Object data) {
		JSONObject object = new JSONObject();
		object.put("page",page);
		object.put("data",data);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",true);
		jsonObject.put("code", Const.RESULT_CODE_SUCCESS);
		jsonObject.put("msg", Const.RESULT_MSG_SUCCESS);
		jsonObject.put("data", object);
		return jsonObject.toJSONString();
	}

	public static String returnMessage(String message) {
		if (Const.SUCCESS.equals(message)) {
			return returnSuccess(message);
		}
		if (Const.ERROR_PARAM.equals(message)) {
			return returnParamError();
		}
		return returnFail();
	}
	public static String returnData(Object data) {
		if (Const.ERROR_PARAM.equals(data)) {
			return returnParamError();
		}
		if (Const.FAIL.equals(data) || data == null) {
			return returnFail();
		}
		return returnSuccess(data);
	}
	public static String returnData(Object data, Page page) {
		if (Const.ERROR_PARAM.equals(data)) {
			return returnParamError();
		}
		if (Const.FAIL.equals(data)) {
			return returnFail();
		}
		return returnSuccess(page,data);
	}

	public static String returnMoneyError() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag",false);
		jsonObject.put("code",Const.RESULT_CODE_ERROR_MONEY);
		jsonObject.put("msg",Const.RESULT_MSG_ERROR_MONEY);
		return jsonObject.toJSONString();
	}
	public static String returnMessage(boolean flag,String code,String msg,Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", flag);
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("data", data);
		return jsonObject.toJSONString();
	}
}
