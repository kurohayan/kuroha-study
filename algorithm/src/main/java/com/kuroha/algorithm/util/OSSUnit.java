package com.kuroha.algorithm.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.Date;

@Slf4j
public class OSSUnit {

    /**
     * 阿里云API的外网域名
     */
    private static final String OSS_ENDPOINT = "oss-cn-qingdao.aliyuncs.com";
    /**
     * 阿里云API的内网域名
     */
    private static final String OSS_ENDPOINT_INTERNAL  = "oss-cn-qingdao-internal.aliyuncs.com";
    /**
     * 阿里云API的密钥Access Key ID
     */
    private static final String ACCESS_ID = "";
    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static final String ACCESS_KEY = "";
    /**
     * 公共读文件存放的bucket（默认）
     */
    public static final String BUCKET_NAME = "";
    /**
     * 私有文件存放的bucket
     */
    public static final String PRIVATE_BUCKET_NAME = "";
    /**
     * 天网存放文章地址的bucket
     */
    public static final String ARTICLE_BUCKET_NAME = "";
    /**
     * 天网存放文章地址的bucket
     */
    public static final String COMMON_BUCKET_NAME = "";

    private static OSSClient ossClientInternal = null;
    private static OSSClient ossClient = null;
    private static final boolean ossInternal = false;

    /**
     * 获取阿里云OSS客户端对象（走内网）
     *
     * @return OSSClient
     * @author SunCheng 2017年10月24日 上午11:54:42
     */
    public static final OSSClient getOSSClientInternal() {
        if (ossClientInternal == null) {
            DefaultCredentialProvider credentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(ACCESS_ID, ACCESS_KEY);
            ClientConfiguration conf = getClientConfig();
            if (ossInternal) {
                // 专用网络
                conf.setSupportCname(false);
                ossClientInternal =  new OSSClient(OSS_ENDPOINT_INTERNAL, credentialProvider, conf);
            }else {
                // 外网
                ossClientInternal =  getOSSClient();
            }
        }
        return ossClientInternal;
    }
    /**
     * 获取阿里云OSS客户端对象（走外网）
     *
     * @return OSSClient
     * @author SunCheng 2017年10月24日 上午11:54:42
     */
    public static final OSSClient getOSSClient() {
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

    public static  String uploadPngOSS(File file, String bucketName, String diskName, String holdName)
            throws Exception {
        String resultStr = null;
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        String fileName = file.getName();
        Long fileSize = file.length();
        // 创建上传Object的Metadata

        try (InputStream is = new FileInputStream(file)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType("application/png");
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 上传文件
            PutObjectResult putResult = getOSSClientInternal().putObject(bucketName, diskName + holdName, is, metadata);
            // 解析结果
            resultStr = putResult.getETag();
            metadata = null;
        }
        return resultStr;
    }

    /**
     * 向阿里云的OSS存储中存储文件，改为通过内网上传  sunc
     *
     * @param is 上传文件流
     * @param bucketName bucket名称，默认值为rk-secure-base
     * @param diskName 上传文件的目录 --bucket下文件的路径
     * @param holdName 保存的名称
     * @return String 唯一MD5数字签名
     * @author Sunc 2016年5月23日 下午8:44:39
     * @throws Exception
     */
    public static final String uploadObjectOSS(InputStream is, String bucketName, String diskName, String holdName)
            throws Exception {
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
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
        return StringUtil.splicingString("https://",bucketName,".oss-cn-qingdao.aliyuncs.com/",diskName,holdName);
    }

    public static final String uploadImageOSS(InputStream is, String bucketName, String diskName, String holdName)
            throws Exception {
        String resultStr = null;
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        metadata.setCacheControl("no-cache");
        metadata.setHeader("Pragma", "no-cache");
        metadata.setContentEncoding("utf-8");
        metadata.setContentType("image/png");
        // 上传文件
        OSSClient ossClientInternal = getOSSClientInternal();
        PutObjectResult putResult = ossClientInternal.putObject(bucketName, diskName + holdName, is, metadata);
        // 解析结果
        resultStr = putResult.getETag();
        return resultStr;
    }

    public static final String uploadImageOSS(InputStream is, String bucketName, String diskName, String holdName,String tempAddress) {
        //生成文件
        File file = new File(tempAddress);
        String resultStr = null;
        try {
            if (StringUtil.isNull(bucketName)) {
                bucketName = BUCKET_NAME;
            }

            //读取io流进入文件
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();


            // 创建上传Object的Metadata
            int fileSize = is.available();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            //如果是图片 ContentType 不要动
            metadata.setContentType("image/jpeg");
            //设置大小 还有下载下来的名称
            metadata.setContentDisposition("filename/filesize=" + diskName + holdName + "/" + fileSize + "Byte.");
            // 上传文件
            PutObjectResult putResult = getOSSClientInternal().putObject(bucketName, diskName + holdName, file, metadata);
            // 解析结果
            resultStr = putResult.getETag();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != file){
                if(file.delete()){
                }
            }
        }

        return resultStr;
    }



    /**
     * 根据key获取OSS服务器上的文件输入流，改为通过内网获取  sunc
     *
     * @param bucketName
     *            bucket名称 默认值为rk-secure-base
     * @param diskName
     *            文件路径
     * @param key
     *            Bucket下的文件的路径名+文件名
     */
    public static final InputStream getOSSInputStream(String bucketName, String diskName, String key) {
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        OSSObject ossObj = null;
        try {
            ossObj = getOSSClientInternal().getObject(bucketName, diskName + key);
        } catch (Exception e) {
            // 如果私有上没有，可能是还没迁移过来，先去公有上单独迁移再下载
            copyObject(BUCKET_NAME, diskName + key, bucketName, diskName + key);
            ossObj = getOSSClientInternal().getObject(bucketName, diskName + key);
        }
        return ossObj.getObjectContent();
    }
    /**
     * 根据key获取OSS服务器上的文件输入流，改为通过内网获取(可优化,将key改为list形式,直接修改ossObject来达到减少对象的创建)
     *
     * @param bucketName bucket名称 默认值为rk-secure-base
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static final String getOSSText(String bucketName, String diskName, String key) {
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        try {
            OSSObject ossObject = getOSSClientInternal().getObject(bucketName, diskName + key);
            BufferedReader br = new BufferedReader(new InputStreamReader(ossObject.getObjectContent(),"UTF-8"));
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取图片信息
     * @param bucketName
     * @param diskName
     * @param key
     */
    public static String getImgInfo(String bucketName, String diskName, String key) {
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        try {
            String style = "image/info";
            GetObjectRequest request = new GetObjectRequest(bucketName, diskName + key);
            request.setProcess(style);
            OSSObject ossObject = getOSSClientInternal().getObject(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param bucketName
     *            Bucket名 默认值为rk-secure-base
     * @param diskName
     *            文件路径
     * @param Objectkey
     *            上传到OSS起的名
     * @param filename
     *            文件下载到本地保存的路径
     * @throws OSSException
     * @throws ClientException
     */
    public static String downloadFile(String bucketName, String diskName, String Objectkey, String filename)
            throws OSSException, ClientException {
        log.info("---------------bucketName "+bucketName);
        log.info("---------------diskName "+diskName);
        log.info("---------------Objectkey "+Objectkey);
        log.info("---------------filename "+filename);
        if (StringUtil.isNull(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        try {
            getOSSClientInternal().getObject(new GetObjectRequest(bucketName, diskName + Objectkey), new File(filename));
        } catch (Exception e) {
            // 如果私有上没有，可能是还没迁移过来，先去公有上单独迁移再下载
            e.printStackTrace();
            copyObject(BUCKET_NAME, diskName + Objectkey, bucketName, diskName + Objectkey);
            getOSSClientInternal().getObject(new GetObjectRequest(bucketName, diskName + Objectkey), new File(filename));
        }
        return filename;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param bucketName bucket名称
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static void deleteFile(String bucketName, String diskName, String key) {
        getOSSClientInternal().deleteObject(bucketName, diskName + key);
        log.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
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

    /**
     * 获得图片路径,这个必须用外网
     *
     * @param fileUrl
     * @return
     */
    public static String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return getUrl(split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得url链接,这个必须用外网
     *
     * @param key
     * @return
     */
    public static String getUrl(String key,long time) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + time);
        // 生成URL
        URL url = getOSSClient().generatePresignedUrl(BUCKET_NAME, key, expiration);
        if (url != null) {
            String urlStr=url.toString();
            if(0< urlStr.indexOf("?"))
            {
                urlStr = urlStr.substring(0,urlStr.indexOf("?"));
            }
            return urlStr;
        }
        return null;
    }
    /**
     * 获得url链接,这个必须用外网
     *
     * @param key
     * @return
     */
    public static String getUrl(String buckName, String key,long time) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + time);
        // 生成URL
        URL url = getOSSClient().generatePresignedUrl(buckName, key, expiration);
        if (url != null) {
            String urlStr=url.toString();
            if(0< urlStr.indexOf("?"))
            {
                urlStr = urlStr.substring(0,urlStr.indexOf("?"));
            }
            return urlStr;
        }
        return null;
    }
    /**
     * 获得url链接,这个必须用外网
     *
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        return getUrl(key,3600L * 1000 * 24 * 365 * 10);
    }

    /**
     * 复制文件
     *
     * @param
     * @return
     */
    public static final void copyObject(OSSClient client, String sourceBucketName, String sourceKey,
                                        String destinationBucketName, String destinationKey) {
        if (StringUtil.isNull(sourceBucketName)) {
            sourceBucketName = BUCKET_NAME;
        }
        if (StringUtil.isNull(destinationBucketName)) {
            destinationBucketName = BUCKET_NAME;
        }
        getOSSClientInternal().copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
        log.info("复制" + sourceKey + "成功");
    }


    /** 获取文件列表
     *
     * @param
     * @return
     */
    public static ObjectListing getListObject(String bucketName, String Objectkey) throws OSSException, ClientException {
        if(StringUtil.isNull(bucketName)){
            bucketName = BUCKET_NAME;
        }
        return getOSSClientInternal().listObjects(bucketName,Objectkey);
    }


    /**
     * 复制文件
     *
     * @param srcBucketName 源bucket名
     * @param srcKey        源文件名
     * @param dstBucketName 目标bucket名
     * @param dstKey        目标文件名
     * @return 是否成功
     * @throws InvalidParameterException 任一参数为空
     * @author linjun on 2017年6月22日 上午10:19:05
     */
    public static Boolean copyObject(String srcBucketName, String srcKey, String dstBucketName, String dstKey) throws InvalidParameterException {
        if (srcBucketName == null || "".equals(srcBucketName.trim())) {
            throw new InvalidParameterException("源bucket为空");
        }
        if (srcKey == null || "".equals(srcKey.trim())) {
            throw new InvalidParameterException("源文件名为空");
        }
        if (dstBucketName == null || "".equals(dstBucketName.trim())) {
            throw new InvalidParameterException("目标bucket为空");
        }
        if (dstKey == null || "".equals(dstKey.trim())) {
            throw new InvalidParameterException("目标文件名为空");
        }
        OSSClient ossClient = null;
        boolean success = true;
        try {

            ossClient = getOSSClientInternal();
            ossClient.copyObject(srcBucketName, srcKey, dstBucketName, dstKey);

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    public static String uploadTxtOSS(File file, String bucketName, String diskName, String holdName) throws IOException {
        String resultStr = null;
        if (null == bucketName || "".equals(bucketName)) {
            bucketName = BUCKET_NAME;
        }
        //       try {
        InputStream is = new FileInputStream(file);
        String fileName = file.getName();
        Long fileSize = file.length();
        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        metadata.setCacheControl("no-cache");
        metadata.setHeader("Pragma", "no-cache");
        metadata.setContentEncoding("utf-8");
        metadata.setContentType("text/html; charset=utf-8");
        metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
        // 上传文件
        PutObjectResult putResult = null;
        putResult = getOSSClientInternal().putObject(bucketName, diskName + holdName, is, metadata);
        log.info(putResult.toString());
        // 解析结果
        resultStr = putResult.getETag();
        return resultStr;
    }


}
