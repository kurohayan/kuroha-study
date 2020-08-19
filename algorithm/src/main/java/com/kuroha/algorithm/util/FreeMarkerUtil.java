package com.kuroha.algorithm.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

/**
 * @author kuroha
 */
@Slf4j
public class FreeMarkerUtil {

    private static FreeMarkerConfig freeMarkerConfig;

    private static FreeMarkerUtil freeMarkerUtil;

    public static final String PATH = "/tmp/template/";
//    public static final String PATH = "/Users/a123123/test/rightkngiths-all/copy/src/main/resources/template/";

    private FreeMarkerUtil() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setDefaultEncoding("UTF-8");
        FileTemplateLoader fileTemplateLoader = null;
        File file = new File(PATH);
        if (!file.exists()) {
            if (file.mkdirs()){
                log.debug("创建成功");
            }
        }
        try {
            String templateName ="monitor.ftl";
            OssUtil.downloadFile("freemarker/" + templateName,PATH + templateName);
            String fontName = "msyh.ttf";
            OssUtil.downloadFile("font/" + fontName,"/tmp/" + fontName);
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileTemplateLoader = new FileTemplateLoader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setTemplateLoader(fileTemplateLoader);
        freeMarkerConfigurer.setConfiguration(configuration);
        freeMarkerConfig = freeMarkerConfigurer;
    }

    public static FreeMarkerUtil getInstance() {
        if (freeMarkerUtil == null) {
            synchronized (FreeMarkerUtil.class) {
                if (freeMarkerUtil == null) {
                    freeMarkerUtil = new FreeMarkerUtil();
                }
            }
        }
        return freeMarkerUtil;
    }

    public String createHtml(String templateName, String targetFilePath, Map<String, Object> map) throws Exception{
        //获得模版包
        Configuration configuration = freeMarkerConfig.getConfiguration();
        Template template = configuration.getTemplate(templateName);
        //定义输出流，注意必须指定编码
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(targetFilePath)),"UTF-8"));
        //生成模版
        template.process(map, writer);
        return targetFilePath;
    }

}
