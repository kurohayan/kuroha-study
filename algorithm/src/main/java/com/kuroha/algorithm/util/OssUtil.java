package com.kuroha.algorithm.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @author Chenyudeng
 */
@Slf4j
public class OssUtil {

    /**
     * 阿里云API的外网域名
     */
    private static final String OSS_ENDPOINT = "oss-cn-qingdao.aliyuncs.com";

    /**
     * 阿里云API的密钥Access Key ID
     */
    private static final String ACCESS_ID = "";
    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static final String ACCESS_KEY = "";

    private static OSSClient ossClient = null;

    /**
     * 获取阿里云OSS客户端对象（走内网）
     *
     * @return OSSClient
     * @author SunCheng 2017年10月24日 上午11:54:42
     */
    private static OSSClient getOSSClientInternal() {
        return getOSSClient();
    }
    /**
     * 获取阿里云OSS客户端对象（走外网）
     *
     * @return OSSClient
     * @author SunCheng 2017年10月24日 上午11:54:42
     */
    private static OSSClient getOSSClient() {
        if (ossClient == null) {
            DefaultCredentialProvider credentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(ACCESS_ID, ACCESS_KEY);
            ClientConfiguration conf = getClientConfig();
            ossClient =  new OSSClient(OSS_ENDPOINT, credentialProvider, conf);
        }
        return ossClient;
    }

    private static final ClientConfiguration getClientConfig(){
        ClientConfiguration conf = new ClientConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(1024);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(10000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(10000);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        conf.setConnectionRequestTimeout(1000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(10000);
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(3);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        conf.setSupportCname(true);
        // 设置是否开启二级域名的访问方式，默认不开启。
        conf.setSLDEnabled(false);
        // 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTP);
        return conf;
    }


    /**
     * 根据key获取OSS服务器上的文件输入流，改为通过内网获取
     * @param key Bucket下的文件的路径名+文件名
     */
    public static final InputStream getOSSInputStream(String key) {
        OSSObject ossObj = getOSSClientInternal().getObject("rk-secure-base",key);
        return ossObj.getObjectContent();
    }

    /**
     * 下载文件
     * @param filename 文件下载到本地保存的路径
     * @throws Exception
     */
    public static String downloadFile(String key, String filename)
            throws Exception {
        getOSSClientInternal().getObject(new GetObjectRequest("rk-secure-base", key), new File(filename));
        return filename;
    }

    /**
     * 向阿里云的OSS存储中存储文件
     * @param is 上传文件流
     * @param bucketName bucket名称，默认值为rk-secure-base
     * @param diskName 上传文件的目录 --bucket下文件的路径
     * @param holdName 保存的名称
     * @return String 唯一MD5数字签名
     * @throws Exception
     */
    public static final String uploadObjectOSS(InputStream is, String bucketName, String diskName, String holdName)
            throws Exception {
        if (StringUtil.isNull(is,bucketName,diskName,holdName)) {
            return Const.ERROR_PARAM;
        }
        String fileName;
        if (holdName.contains("/")) {
            fileName = holdName.substring(holdName.lastIndexOf("/") + 1);
        } else {
            fileName = holdName;
        }
        int fileSize = is.available();
        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        metadata.setCacheControl("no-cache");
        metadata.setHeader("Pragma", "no-cache");
        metadata.setContentEncoding("utf-8");
        metadata.setContentType(getContentType(holdName));
        metadata.setContentDisposition("filename/filesize=" + URLEncoder.encode(fileName, "UTF-8") + "/" + fileSize + "Byte.");
        // 上传文件
        OSSClient ossClientInternal = getOSSClientInternal();
        PutObjectResult putResult = ossClientInternal.putObject(bucketName, diskName + holdName, is, metadata);
        log.debug(putResult.getETag());
        // 解析结果
        return StringUtil.splicingString("https://",bucketName,".",OSS_ENDPOINT,"/",diskName,holdName);
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName
     *            文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        suffix = suffix.toLowerCase();
        switch (suffix) {
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "tif":
                return "image/tiff";
            case "txt":
                return "text/plain;charset=utf-8";
            case "vsd":
                return "application/vnd.visio";
            case "ppt":
            case "pptx":
                return "application/vnd.ms-powerpoint";
            case "doc":
            case "docx":
                return "application/msword";
            case "xml":
                return "text/xml";
            case "pdf":
                return "application/pdf";
            case "xls":
            case "xlsx":
                return "application/x-xls";
            case "wav":
                return "audio/wav";
            case "wma":
                return "audio/x-ms-wma";
            case "midi":
                return "audio/mid";
            case "mp3":
            case "flac":
                return "audio/mp3";
            case "rmvb":
                return "application/vnd.rn-realmedia-vbr";
            case "avi":
                return "video/avi";
            case "flv":
            case "3gp":
            case "mkv":
            case "mp4":
                return "video/mpeg4";
            case "zip":
                return "application/zip";
            case "html":
            default:
                return "text/html;charset=utf-8";

        }
    }

}
