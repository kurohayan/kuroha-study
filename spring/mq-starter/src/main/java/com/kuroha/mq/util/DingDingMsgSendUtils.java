package com.kuroha.mq.mqserver.util;

import com.alibaba.fastjson.JSON;
import com.kuroha.mq.mqserver.entity.dto.AtDto;
import com.kuroha.mq.mqserver.entity.dto.MsgDto;
import com.kuroha.mq.mqserver.entity.dto.TextDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

public class DingDingMsgSendUtils {
    private static Logger logger = LoggerFactory.getLogger(DingDingMsgSendUtils.class);
    @Value("${dingding.access_token:085ca59fa38a3030e0faa82dd99890debc0688865dcf8a6088d0ff3d44b7ca9f}")
    private static String accessToken;
    @Value("${dingding.atPhone}")
    private static String atPhones;

    private static void dealDingDingMsgSend(String textMsg) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=" + (StringUtils.isBlank(accessToken) ? "085ca59fa38a3030e0faa82dd99890debc0688865dcf8a6088d0ff3d44b7ca9f" : accessToken);
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        try {
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                logger.info("【发送钉钉群消息】消息响应结果：" + JSON.toJSONString(result));
            }
        } catch (Exception var7) {
            logger.error("【发送钉钉群消息】error：" + var7.getMessage(), var7);
        }

    }

    public static void sendDingDingGroupMsg(String content) {
        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
        dealDingDingMsgSend(textMsg);
    }

    public static void sendDingDingGroupMsg(String content, String atPhone) {
        sendDingDingGroupMsg(content, false, atPhone);
    }

    public static void sendDingDingGroupMsgAt(String content) {
        sendDingDingGroupMsg(content, true, (String)null);
    }

    public static void sendDingDingGroupMsg(String content, boolean atAll, String atPhone) {
        content = content.replace("\"", "'");
        String textMsg = "";
        MsgDto msgDto = new MsgDto();
        msgDto.setMsgtype("text");
        TextDto textDto = new TextDto();
        textDto.setContent(content);
        msgDto.setText(textDto);
        AtDto atDto = new AtDto();
        atDto.setAtAll(atAll);
        if (!atAll) {
            if (StringUtils.isBlank(atPhone)) {
                atPhone = atPhones;
            }

            List<String> result = Arrays.asList(atPhone.split(","));
            atDto.setAtMobiles(result);
        }

        msgDto.setAt(atDto);
        textMsg = JSON.toJSONString(msgDto);
        System.out.println(textMsg);
        logger.info("发送钉钉消息  ======> {}", textMsg);
        dealDingDingMsgSend(textMsg);
    }
}
