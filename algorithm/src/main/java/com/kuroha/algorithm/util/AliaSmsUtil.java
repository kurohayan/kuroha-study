package com.kuroha.algorithm.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Chenyudeng
 */
@Slf4j
public class AliaSmsUtil {
    /**
     * 发送短信code
     */
    public enum  MessageCode {
        //主站手机验证码
        //备用 SMS_189015349   您的验证码：${code}，您正进行身份验证，打死不告诉别人！
        CODE1("SMS_129745418",TYPE_ID1),
        //微信付费授权成功短信通知
        CODE2("SMS_129485137",TYPE_ID1),
        //获取东方号等平台账号和密码   你在${typeName}的用户名为：${userName}，密码为：${pwd}。登录该平台可修改密码
        CODE3("SMS_136384264",TYPE_ID1),
        //用户第三方注册初始密码    恭喜你成功注册xxx，你的初始密码为${pwd}，请尽快修改。
        CODE4("SMS_136380008",TYPE_ID1),
        //升级为品牌馆成功    恭喜你已成功开通xxx，原创的优质内容将大大提升收益！电脑登录xxx查看更多。
        CODE5("SMS_136387197",TYPE_ID1),
        //版权小课0元购课兑换码
        CODE6("SMS_188640493",TYPE_ID2),
        //服务升级提醒    为提升您作品的版权保护成功率，保障您的权益不受侵害，xxx全新升级版权保护服务协议，请立即前往xxx官网完成服务升级
        CODE7("SMS_190075144",TYPE_ID1);
        private String templateCode;
        private int type;
        MessageCode(String templateCode,int type) {
            this.templateCode = templateCode;
            this.type = type;
        }
        public String getTemplateCode() {
            return templateCode;
        }
        public int getType() {
            return type;
        }
    }


    /**
     * 产品名称:云通信短信API产品
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 第一个账号
     */
    public static final int TYPE_ID1 = 1;
    /**
     * AKId
     */
    private static final String ACCESS_KEY_ID = "";
    /**
     * AKSecret
     */
    private static final String ACCESS_KEY_SECRET = "";
    /**
     * 第二个账号
     */
    public static final int TYPE_ID2 = 2;
    /**
     * AKId
     */
    private static final String ACCESS_KEY_ID2 = "";
    /**
     * AKSecret
     */
    private static final String ACCESS_KEY_SECRET2 = "";
    //属于第一个账号
    /**
     * 主站手机验证码
     */
    public static final String smsType1="SMS_129745418";
    /**
     * 微信付费授权成功短信通知
     */
    public static final String smsType2="SMS_129485137";
    /**
     * 获取东方号等平台账号和密码
     */
    public static final String smsType3="SMS_136384264";
    /**
     * 用户第三方注册初始密码
     */
    public static final String smsType4="SMS_136380008";
    /**
     * 升级为品牌馆成功
     */
    public static final String smsType5="SMS_136387197";

    private static IAcsClient client1;
    private static IAcsClient client2;

    static {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    }

    private static IAcsClient getAcsClient(MessageCode messageCode) {
        IAcsClient acsClient;
        IClientProfile profile;
        switch (messageCode.getType()) {
            case 2:
                if (client2 != null) {
                    acsClient = client2;
                    break;
                }
                synchronized (AliaSmsUtil.class) {
                    if (client2 != null) {
                        acsClient = client2;
                        break;
                    }
                    DefaultProfile.addEndpoint( "cn-hangzhou", PRODUCT, DOMAIN);
                    profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID2, ACCESS_KEY_SECRET2);
                    acsClient = new DefaultAcsClient(profile);
                    client2 = acsClient;
                }
                break;
            case 1:
            default:
                if (client1 != null) {
                    acsClient = client1;
                    break;
                }
                synchronized (AliaSmsUtil.class) {
                    if (client1 != null) {
                        acsClient = client1;
                        break;
                    }
                    DefaultProfile.addEndpoint( "cn-hangzhou", PRODUCT, DOMAIN);
                    profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                    acsClient = new DefaultAcsClient(profile);
                    client1 = acsClient;
                }
        }
        return acsClient;
    }

    /**
     * 发送短信
     * @param mobile
     * @param messageCode
     * @param jsonObject
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendMessage(String mobile, MessageCode messageCode,JSONObject jsonObject) throws ClientException {
        //初始化acsClient
        IAcsClient acsClient = getAcsClient(messageCode);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("维权骑士");
        //必填:短信签名-可在短信控制台中找到
        request.setTemplateCode(messageCode.getTemplateCode());
        if (jsonObject != null) {
            request.setTemplateParam(jsonObject.toJSONString());
        }
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse response = acsClient.getAcsResponse(request);
        String responseStr = "Code=" + response.getCode()+":Message=" + response.getMessage()+
                ":RequestId=" + response.getRequestId()+":BizId=" + response.getBizId()+":mobile:"+mobile+":templateCode:"+messageCode.getTemplateCode();
        log.debug(responseStr);
        String reCode = response.getCode();
        exceptionEmail(response, responseStr, reCode);
        return response;
    }

    /**
     * 异常信息处理
     * @param response
     * @param responseStr
     * @param reCode
     */
    private static void exceptionEmail(SendSmsResponse response, String responseStr, String reCode) {
        if (StringUtils.isBlank(reCode)) {
            MailUtil.sendException("AliaSmsUtil/sendSms/",
                    "短信异常提醒，reCode=" + reCode + ":" + response.getRequestId() + ":" + response.getBizId(), 1);
        } else {
            if (!"OK".equals(reCode)) {
                switch (reCode) {
                    case "isv.OUT_OF_SERVICE":
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常业务停机：" + reCode + "：" + responseStr, 1);
                        break;
                    case "isp.SYSTEM_ERROR":
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常系统错误：" + reCode + "：" + responseStr, 1);
                        break;
                    case "isv.BUSINESS_LIMIT_CONTROL":
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常业务限流：" + reCode + "：" + responseStr, 1);
                        break;
                    case "isv.BLACK_KEY_CONTROL_LIMIT":
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常黑名单管控：" + reCode + "：" + responseStr, 1);
                        break;
                    case "isv.AMOUNT_NOT_ENOUGH":
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常账户余额不足：" + reCode + "：" + responseStr, 1);
                        break;
                    default:
                        MailUtil.sendException("AliaSmsUtil/sendSms/", "短信发送异常：" + reCode + "：" + responseStr, 1);
                        break;
                }
            }
        }
    }

}
