package com.kuroha.mq.mqserver;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author samtofor
 */
@Data
@ConfigurationProperties("com.kuroha.mq")
public class MqProperties {

    private static final Logger log = LoggerFactory.getLogger(MqProperties.class);

    public static final String PREFIX = "com.kuroha.mq";

    public static final Pattern PATTERN = Pattern.compile("-(\\w)");

    private String serverAddr;

    private String accessKey;

    private String accessSecret;

    private String groupId;

    private String orderGroupId;

    private boolean enabled;

    private List<HashMap<String, String>> subscribe;

    private List<HashMap<String, String>> orderSubscribe;

}
