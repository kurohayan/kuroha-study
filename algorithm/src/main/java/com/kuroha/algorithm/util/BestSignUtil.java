package com.kuroha.algorithm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Chenyudeng
 */
@Slf4j
public class BestSignUtil {

    private static String developerId = "";

    private static String privateKey = "";

    private static String serverHost = "https://openapi.bestsign.info/openapi/v2";

    private static String sdkHost = "https://openapi.bestsign.info/openapi/v2";

    private static final String urlSignParams = "?developerId=%s&rtick=%s&signType=rsa&sign=%s";

    private static boolean flag = false;

    public static String account = "";

    public static String bindPlatfrormTid = "";

    public static String fenFaTid = "";

    public static String weiQuanTid = "";
    /**
     * 初始化
     * @param environment
     */
    public static void init(String environment) {
        if (flag) {
            return;
        }
        flag = true;
        if (Const.MASTER.equals(environment)) {
            developerId = "";
            privateKey = "";
            serverHost = "https://openapi.bestsign.cn/openapi/v2";
            sdkHost = "https://openapi.bestsign.cn/openapi/v2";
            account = "";
            bindPlatfrormTid = "";
            fenFaTid = "";
            weiQuanTid = "";
        }
    }

    /**
     * 注册用户
     * @param account 用户唯一标识,手机号,邮箱等
     * @param name 姓名
     * @return
     * @throws Exception
     */
    public static String regised(String account,String name) throws Exception {
        String host = sdkHost;
        String method = "/user/reg";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("name", name);
        requestBody.put("userType", "1");
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        log.info("xxxxxxxxxxxx"+jsonObject.toJSONString()+"xxxxxxxxxxxx");
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            return Const.SUCCESS;
        }
        return Const.FAIL;
    }
    /**
     * 生成用户签名/印章图片
     * @param account 用户唯一标识,手机号,邮箱等
     * @return
     * @throws Exception
     */
    public static String createSignatureImage(String account) throws Exception {
        String host = sdkHost;
        String method = "/signatureImage/user/create";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        // 字体名称  SimHei 黑体 SimSun 宋体 SimKai 楷体
        requestBody.put("fontName", "SimSun");
        // 字号 12-120
        requestBody.put("fontSize", "30");
        // 字体颜色  red（红），black（黑），blue（蓝），purple（紫），grey（灰），brown（棕），tan(褐色)，cyan(青色)
        requestBody.put("fontColor", "red");
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            return Const.SUCCESS;
        }
        return Const.FAIL;
    }
    /**
     * 上传用户签名/印章图片
     * @param account 用户唯一标识,手机号,邮箱等
     * @param imageData 图片base64字符串
     * @return
     * @throws Exception
     */
    public static String uploadSignatureImage(String account,String imageData) throws Exception {
        String host = sdkHost;
        String method = "/signatureImage/user/upload";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        // 字体名称  SimHei 黑体 SimSun 宋体 SimKai 楷体
        requestBody.put("imageData", imageData);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            return Const.SUCCESS;
        }
        return Const.FAIL;
    }

    /**
     * 个人手机号三要素验证码获取
     * @param name 姓名
     * @param identity 身份证
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    public static String identityVerified(String account, String name,String identity, String mobile) throws Exception {
        String host = sdkHost;
        String method = "/realName/personal/identity3/vcode/sender";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("name", name);
        requestBody.put("identity", identity);
        requestBody.put("identityType", "0");
        requestBody.put("mobile", mobile);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析Base64Util
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            int result = data.getIntValue("result");
            if (result == 1) {
                return data.getString("personalIdentity3Key");
            } else {
                return Const.ERROR_PARAM;
            }
        }
        return Const.ERROR_PARAM;
    }

    /**
     * 个人手机号三要素验证码校验
     * @param vCode 验证码
     * @param personalIdentity3Key key
     * @return
     * @throws Exception
     */
    public static String identityVerifiedCheck(String account, String vCode,String personalIdentity3Key) throws Exception {
        String host = sdkHost;
        String method = "/realName/personal/identity3/vcode/verify";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("vcode", vCode);
        requestBody.put("personalIdentity3Key", personalIdentity3Key);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        int errno = jsonObject.getIntValue("errno");
        if (errno == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            int result = data.getIntValue("result");
            if (result == 1) {
                return Const.SUCCESS;
            }
        }
        return Const.FAIL;
    }
    /**
     * 身份证OCR识别
     * @param identityImage 身份证人像面照片
     * @param identityBackImage 身份证国徽面照片
     * @return JSONObject or null
     * 生日 birthday 1987-11-21
     * 身份证号 idcardNum 350802198808285516
     * 姓名 realName 代用名
     * 地址 address 福建省龙岩市东城东宝路336号
     * 性别 gender 男
     * 民族 race 汉
     * 签发机构 issuedBy 宁都县公安局
     * 有效期 validate 2006.05.17-2026.05.17
     * @throws Exception
     */
    public static JSONObject ocr(String identityImage, String identityBackImage) throws Exception {
        String host = sdkHost;
        String method = "/credentialVerify/ocrIDCard";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("identityImage", identityImage);
        requestBody.put("identityBackImage", identityBackImage);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        Integer errno = jsonObject.getInteger("errno");
        if (errno != null && errno == 0) {
            return jsonObject.getJSONObject("data");
        }
        return null;
    }
    /**
     * 注册个人用户并申请证书
     * @param account 用户唯一标识,手机号,邮箱等
     * @param name 姓名
     * @param identity 身份证
     * @return
     * @throws Exception
     */
    public static String regisedAndApplyCert(String account,String name,String identity) throws Exception {
        String host = sdkHost;
        String method = "/user/reg";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        JSONObject credential = new JSONObject();
        credential.put("identity",identity);
        requestBody.put("account", account);
        requestBody.put("name", name);
        requestBody.put("userType", "1");
        requestBody.put("credential", credential);
        requestBody.put("applyCert", "1");
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getString("taskId");
        }
        return Const.FAIL;
    }
    /**
     * 异步申请证书状态查询
     * @param account 用户唯一标识,手机号,邮箱等
     * @param taskId 异步任务id
     * @return
     * @throws Exception
     */
    public static String getCertStatus(String account, String taskId) throws Exception {
        String host = sdkHost;
        String method = "/user/reg";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("taskId", taskId);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            int status = data.getIntValue("status");
            if (status == 5) {
                return Const.SUCCESS;
            }
        }
        return Const.FAIL;
    }
    /**
     * 查询证书编号
     * @param account 用户唯一标识,手机号,邮箱等
     * @return
     * @throws Exception
     */
    public static String getCertId(String account) throws Exception {
        String host = sdkHost;
        String method = "/user/getCert";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getString("certId");
        }
        return Const.FAIL;
    }
    /**
     * 获取证书详细信息
     * @param account 用户唯一标识,手机号,邮箱等
     * @return
     * @throws Exception
     */
    public static JSONObject getCertInfo(String account, String certId) throws Exception {
        String host = sdkHost;
        String method = "/user/cert/info";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("certId", certId);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (jsonObject.getIntValue("errno") == 0) {
            return jsonObject.getJSONObject("data");
        }
        return null;
    }

    /**
     * 发送消息
     * @param host
     * @param method
     * @param requestBody
     * @return
     * @throws IOException
     */
    private static String sendPost(String host, String method, JSONObject requestBody) throws IOException {
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String body = requestBody.toJSONString();
        log.info(StringUtil.splicingString("sendPost-host:",host,method,urlParams,"--requestBody:",body));
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, body);
        log.info("sendPost-result:" + responseBody);
        return responseBody;
    }

    /**
     * 根据模板生成合同文件,返回合同token
     */
    public static String createContractPdfByTemplate(String tid, JSONObject templateValues) throws IOException {
        String host = sdkHost;
        String method = "/template/createContractPdf/";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("tid", tid);
        requestBody.put("templateValues",templateValues);
        requestBody.put("account", account);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        Integer errno = jsonObject.getInteger("errno");
        if (errno != null && errno == 0) {
            return jsonObject.getJSONObject("data").getString("templateToken");
        }
        return null;
    }

    /**
     * 根据token和模板编号生成合同
     */
    public static String createByTemplate(String tid,String token,String expireTime,String title) throws IOException {
        String host = sdkHost;
        String method = "/contract/createByTemplate/";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("tid", tid);
        requestBody.put("account", account);
        requestBody.put("templateToken",token);
        requestBody.put("title",title);
        if(StringUtil.isNotBlank(expireTime)){
            requestBody.put("expireTime",expireTime);
        }
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        Integer errno = jsonObject.getInteger("errno");
        if (errno != null && errno == 0) {
            return jsonObject.getJSONObject("data").getString("contractId");
        }
        return null;
    }

    /**
     * 用模板变量签署合同
     */
    public static String signCertWithTemplate(String contractId, String tid, JSONObject vars) throws IOException {
        String host = sdkHost;
        String method = "/contract/sign/template/";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("tid", tid);
        requestBody.put("vars",vars);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        Integer errno = jsonObject.getInteger("errno");
        if (errno != null && errno == 0) {
            return "success";
        }
        return null;
    }

    /**
     * 根据tid生成vars
     */
    public static JSONObject buildVarsByTid(String tid, String accountId){
        JSONObject vars = new JSONObject();
        if(account.equals(accountId)){
            JSONObject stamp = new JSONObject();
            stamp.put("account",accountId);
            vars.put("stamp",stamp);
            JSONObject stampDate = new JSONObject();
            stampDate.put("account",accountId);
            vars.put("stampDate",stampDate);
            return vars;
        }
        //写作平台绑定协议
        if(bindPlatfrormTid.equals(tid)){
            JSONObject sign = new JSONObject();
            sign.put("account",accountId);
            vars.put("sign",sign);
            JSONObject signDate = new JSONObject();
            signDate.put("account",accountId);
            vars.put("signDate",signDate);
        }
        if(fenFaTid.equals(tid) || weiQuanTid.equals(tid)){
            JSONObject sign = new JSONObject();
            sign.put("account",accountId);
            vars.put("sign",sign);
            JSONObject sign2 = new JSONObject();
            sign2.put("account",accountId);
            vars.put("sign2",sign2);
            JSONObject signDate = new JSONObject();
            signDate.put("account",accountId);
            vars.put("signDate",signDate);
            JSONObject signDate2 = new JSONObject();
            signDate2.put("account",accountId);
            vars.put("signDate2",signDate2);
        }
        return vars;
    }

    /**
     * 构建写作平台协议变量参数
     */
    public static JSONObject buildBindPlatformTemplateValues(String authorName, String idCardNo, List<?> infos){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorName",authorName);
        jsonObject.put("idCardNo",idCardNo);
        StringBuilder sb = new StringBuilder();
        if(infos.size()>12){
            for (int i = 0; i < 12; i++) {
                Object vo = infos.get(i);
                sb.append("平台：").append(vo.toString()).append("\r\n")
                        .append("平台昵称：").append(vo.toString()).append("\r\n")
                        .append("平台链接：").append(vo.toString()).append("\r\n");
                jsonObject.put("platforms",sb.toString());
            }
            for(int i=11;i<infos.size();i++){
                Object vo = infos.get(i);
                sb.setLength(0);
                sb.append("平台：").append(vo.toString()).append("\r\n")
                        .append("平台昵称：").append(vo.toString()).append("\r\n")
                        .append("平台链接：").append(vo.toString()).append("\r\n");
                jsonObject.put("platforms2",sb.toString());
            }
        }else{
            infos.forEach(e->{
                sb.append("平台：").append(e.toString()).append("\r\n")
                        .append("平台昵称：").append(e.toString()).append("\r\n")
                        .append("平台链接：").append(e.toString()).append("\r\n");
            });
            jsonObject.put("platforms",sb.toString());
        }
        return jsonObject;
    }


    /**
     * 构建分发协议变量参数
     */
    public static JSONObject buildFenFaTemplateValues(String authorName, String idCardNo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorName",authorName);
        jsonObject.put("idCardNo",idCardNo);
        jsonObject.put("authorName2",authorName);
        jsonObject.put("idCardNo2",idCardNo);
        return jsonObject;
    }

    /**
     * 构建维权协议变量参数
     */
    public static JSONObject buildWeiQuanTemplateValues(String authorName, String idCardNo) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorName",authorName);
        jsonObject.put("idCardNo",idCardNo);
        jsonObject.put("authorName2",authorName);
        jsonObject.put("idCardNo2",idCardNo);
        InputStream behindIs = OSSUnit.getOSSInputStream(OSSUnit.PRIVATE_BUCKET_NAME, "",
                "");
        String behind =  Base64Util.encode(behindIs);
        InputStream frontIs = OSSUnit.getOSSInputStream(OSSUnit.PRIVATE_BUCKET_NAME, "",
                "");
        String front =  Base64Util.encode(frontIs);
        jsonObject.put("front",front);
        jsonObject.put("behind",behind);
        return jsonObject;
    }

    /**
     * 锁定并结束合同
     */
    public static String lockAndEndContract(String contractId) throws IOException {
        String host = sdkHost;
        String method = "/storage/contract/lock";
        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        // 发送请求
        String responseBody = sendPost(host,method,requestBody);
        // 返回结果解析
        JSONObject jsonObject = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        Integer errno = jsonObject.getInteger("errno");
        if (errno != null && errno == 0) {
            return "success";
        }
        return null;
    }
    // ***************常规接口start*****************************************************
    

    /**
     * 4 上传合同(包含基础接口的上传文件和创建合同) 2.9.0版本新增
     *
     * @param account 用户账号
     * @param expireTime 合同有效期，单位：秒；计算方法：当前系统时间的时间戳秒数+有效期秒数
     * @param title 合同标题
     * @param description 合同内容描述
     * @return 合同id
     * @throws Exception
     */
    public static String contractUpload(String account, String fmd5, String ftype,
                                 String fname, String fpages, String fdata, String expireTime,
                                 String title, String description) throws Exception {
        String host = sdkHost;
        String method = "/storage/contract/upload/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("fmd5", fmd5);
        requestBody.put("ftype", ftype);
        requestBody.put("fname", fname);
        requestBody.put("fpages", fpages);
        requestBody.put("fdata", fdata);
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("expireTime", expireTime);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("contractId");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.1 合同签署(用于自动签署)
     *
     * @param contractId
     *            合同ID
     * @param signer
     *            签署者账号
     * @param signatureImageData
     *            签名图片
     * @param signaturePositions
     *            签名位置数组，如果在5.5设置了签名位置，此参数可以为空
     * @return
     * @throws Exception
     */
    public String contractSignCert(String contractId, String signer,
                                   String signatureImageData, JSONArray signaturePositions)
            throws Exception {
        String host = sdkHost;
        String method = "/storage/contract/sign/cert/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("signer", signer);
        requestBody.put("signatureImageData", signatureImageData);
        requestBody.put("signaturePositions", signaturePositions);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        System.out.println(userObj.toJSONString());
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            return "success";
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.13 发送合同（用于手动签署）
     *
     * @param contractId
     *            合同ID
     * @param account
     *            需要手动签署的用户账号
     * @param signaturePositions
     *            指定的默认签署位置，json array格式，每一项为一个json对象。 x,y坐标使用百分比，取值0.0 -
     *            1.0。如果不指定此参数，表示由签署者自行在页面上选一个签署位置
     * @param returnUrl
     *            手动签署时，当用户签署完成后，指定回跳的页面地址
     * @param vcodeMobile
     *            手动签署时指定接收手机验证码的手机号，如果需要采用手动签署页面，则此项必填
     * @param expireTime
     *            预览链接的过期时间，unix时间戳。超过此时间则无法签署合同，需要获取新的签署合同url
     * @param dpi
     *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
     * @return url
     * @throws Exception
     */
    public String contractSend(String contractId, String account,
                               JSONArray signaturePositions, String returnUrl, String vcodeMobile,
                               String expireTime, String dpi) throws Exception {
        String host = serverHost;
        String method = "/contract/send/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("signer", account);
        requestBody.put("signaturePositions", signaturePositions);
        requestBody.put("returnUrl", returnUrl);
        requestBody.put("vcodeMobile", vcodeMobile);
        requestBody.put("expireTime", expireTime);
        requestBody.put("quality", "100");
        requestBody.put("dpi", dpi);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("url");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    // *********************************常规API接口end****************************************

    // *********************************特殊API接口start****************************************

    /**
     * 5.1 创建合同
     *
     * @param account
     *            用户账号
     * @param fid
     *            文件ID 上传文件后产生的文件ID（fid）
     * @param expireTime
     *            合同有效期，单位：秒；计算方法：当前系统时间的时间戳秒数+有效期秒数
     * @param title
     *            合同标题
     * @param description
     *            合同内容描述
     * @return 合同id
     * @throws Exception
     */
    public String contractCreate(String account, String fid, String expireTime,
                                 String title, String description) throws Exception {
        String host = serverHost;
        String method = "/contract/create/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("fid", fid);
        requestBody.put("expireTime", expireTime);
        requestBody.put("title", title);
        requestBody.put("description", description);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("contractId"); // 文件id，公有云API无此参数；混合云SDK返回该参数
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.2 添加单个签署人
     *
     * @param contractId
     *            合同ID
     * @param signer
     *            签署人用户账号，必须先成功注册账号
     * @return 0-成功
     * @throws Exception
     */
    public int contractAddSigner(String contractId, String signer)
            throws Exception {
        String host = serverHost;
        String method = "/contract/addSigner/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("signer", signer);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            return 0;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.5 设置合同签署参数
     *
     * @param contractId
     *            合同编号
     * @param account
     *            签署者用户账号
     * @param signaturePositions
     *            指定的默认签署位置，json array格式，每一项为一个json对象。 x,y坐标使用百分比，取值0.0 -
     *            1.0。如果不指定此参数，表示由签署者自行在页面上选一个签署位置
     * @param returnUrl
     *            手动签署时，当用户签署完成后，指定回跳的页面地址
     * @param vcodeMobile
     *            手动签署时指定接收手机验证码的手机号，如果需要采用手动签署页面，则此项必填
     * @return
     * @throws Exception
     */
    public int setSignerConfig(String contractId, String account,
                               JSONArray signaturePositions, String returnUrl, String vcodeMobile)
            throws Exception {
        String host = serverHost;
        String method = "/contract/setSignerConfig/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("account", account);
        requestBody.put("signaturePositions", signaturePositions);
        //在有指定signaturePOSTion参数的情况下，是否允许拖动签名位置。取值1/0。（0：不允许，1：允许，下面都是这样的）。默认值：1
        requestBody.put("isAllowChangeSignaturePosition", "1");
        requestBody.put("returnUrl", returnUrl);
        requestBody.put("password", "");
        requestBody.put("isVerifySigner", "0");
        requestBody.put("vcodeMail", "");
        requestBody.put("vcodeMobile", vcodeMobile);
        //手动签署时是否手绘签名。取值0/1。 0手动签署时签名图片点击不触发签名面板。 1手动签署时可以点击签名图片触发手绘签名
        requestBody.put("isDrawSignatureImage", "1");
        requestBody.put("signatureImageName", "default"); // 使用默认值
        requestBody.put("certType", ""); // 系统自动判断
        requestBody.put("app", "");

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            return 0;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.13 获取手动签署URL
     *
     * @param contractId
     *            合同ID
     * @param account
     *            需要手动签署的用户账号
     * @param expireTime
     *            预览链接的过期时间，unix时间戳。超过此时间则无法签署合同，需要获取新的签署合同url
     * @param dpi
     *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
     * @return url
     * @throws Exception
     */
    public String getSignURL(String contractId, String account,
                             String expireTime, String dpi) throws Exception {
        String host = serverHost;
        String method = "/contract/getSignURL/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("account", account);
        requestBody.put("expireTime", expireTime);
        requestBody.put("quality", "100");
        requestBody.put("dpi", dpi);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("url");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.4 查询合同信息
     *
     * @param contractId
     *            合同ID
     * @return 文件id
     * @throws Exception
     */
    public JSONObject contractGetInfo(String contractId) throws Exception {
        String host = serverHost;
        String method = "/contract/getInfo/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            return data;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.14 获取合同预览URL
     *
     * @param contractId
     *            合同ID
     * @param account
     *            合同签署者的账号
     * @param expireTime
     *            预览链接的过期时间，unix时间戳。超过此时间则无法预览合同，需要获取新的预览合同url
     * @param dpi
     *            预览图片清晰度，枚举值：70-低清（默认），90-普清，120-高清，160-超清
     * @return url
     * @throws Exception
     */
    public String getPreviewURL(String contractId, String account,
                                String expireTime, String dpi) throws Exception {
        String host = serverHost;
        String method = "/contract/getPreviewURL/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("account", account);
        requestBody.put("expireTime", expireTime);
        requestBody.put("quality", "100");
        requestBody.put("dpi", dpi);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("url");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }

    /**
     * 5.16 下载合同PDF文件
     *
     * @param contractId
     *            合同编号
     * @return
     * @throws Exception
     */
    public static byte[] contractDownload(String contractId) throws Exception {
        String host = sdkHost;
        String method = "/storage/contract/download/";

        // 组装url参数
        String urlParams = "contractId=" + contractId;

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId,
                privateKey, host, method, rtick, urlParams, null);
        // 签名参数追加为url参数
        urlParams = String.format(urlSignParams, developerId, rtick,
                paramsSign) + "&" + urlParams;
        // 发送请求
        byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
                urlParams);
        // 返回结果解析
        return responseBody;
    }

    /**
     * pdf文件添加图片或者文字元素
     *
     * @param account
     *            用户账号
     * @param fid
     *            文件id
     * @param elements
     *            pdf元素列表，格式如下： [ { "pageNum": "1", "x": "0.1", "y": "0.1",
     *            "type": "text", "value": "我是测试文本内容", "fontSize": "14" } ]
     * @return
     * @throws Exception
     */
    public String storageAddPDFElements(String account, String fid,
                                        JSONArray elements) throws Exception {
        String host = sdkHost;
        String method = "/storage/addPdfElements/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("fid", fid);
        requestBody.put("elements", elements);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("fid"); // 文件id，公有云API无此参数；混合云SDK返回该参数
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":" + userObj.getString("errmsg"));
        }
    }


    /**
     * 1.9 异步申请状态查询
     * @param account 用户账号
     * @param taskId 任务单号
     * @return 状态 1-新申请 2-申请中 3-申请超时 4-申请失败 5-申请成功 -1-无效的申请（数据库无此值）
     * @throws Exception
     */
    public String asyncApplyCertStatus(String account, String taskId) throws Exception {
        String host = serverHost;
        String method = "/user/async/applyCert/status/";

        //组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("taskId", taskId);

        //生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        //计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(developerId, privateKey, host, method, rtick, null, requestBody.toJSONString());
        //签名参数追加为url参数
        String urlParams = String.format(urlSignParams, developerId, rtick, paramsSign);
        //发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method, urlParams, requestBody.toJSONString());
        //返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        //返回errno为0，表示成功，其他表示失败
        if(userObj.getIntValue("errno") == 0){
            JSONObject data = userObj.getJSONObject("data");
            if(data != null){
                return data.getString("status");
            }
            return null;
        }else{
            throw new Exception(userObj.getIntValue("errno")+":"+userObj.getString("errmsg"));
        }
    }

}
