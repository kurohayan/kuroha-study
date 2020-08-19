package com.kuroha.algorithm.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyRequest;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author Chenyudeng
 */
@Slf4j
public class VodUtil {

    private static final String ACCESS_KEY_ID = "";

    private static final String ACCESS_KEY_SECRET = "";

    private static DefaultAcsClient client;

    static {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }



    public static class Upload {
        /**
         * 使用服务端上传
         * @param uploadAddress
         * @param uploadAuth
         * @param inputStream
         * @return
         */
        public static String uploadVideoByOss(String uploadAddress, String uploadAuth, InputStream inputStream) {
            uploadAddress = Base64Util.decode(uploadAddress.getBytes());
            uploadAuth = Base64Util.decode(uploadAuth.getBytes());
            JSONObject uploadAddressObject = JSON.parseObject(uploadAddress);
            JSONObject uploadAuthObject = JSON.parseObject(uploadAuth);
            String endpoint = uploadAddressObject.getString("Endpoint");
            String accessKeyId = uploadAuthObject.getString("AccessKeyId");
            String accessKeySecret = uploadAuthObject.getString("AccessKeySecret");
            String securityToken = uploadAuthObject.getString("SecurityToken");
            String bucketName = uploadAddressObject.getString("Bucket");
            String objectName = uploadAddressObject.getString("FileName");
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, securityToken);
            PutObjectResult result = oss.putObject(bucketName, objectName, inputStream);
            log.debug(result.getETag());
            return Const.SUCCESS;
        }
    }

    public static class Image {
        public static CreateUploadImageResponse createUploadImage() throws Exception {
            CreateUploadImageRequest request = new CreateUploadImageRequest();
            request.setImageType("default");
            request.setImageExt("png");
            request.setTitle("this is a sample");
//            JSONObject userData = new JSONObject();
//            JSONObject messageCallback = new JSONObject();
//            messageCallback.put("CallbackURL", "https://open-test.rightknights.com");
//            messageCallback.put("CallbackType", "http");
//            userData.put("MessageCallback", messageCallback.toJSONString());
//            JSONObject extend = new JSONObject();
//            extend.put("MyId", "user-defined-id");
//            userData.put("Extend", extend.toJSONString());
//            request.setUserData(userData.toJSONString());
            return client.getAcsResponse(request);
        }
    }

    /**
     * 视频模块
     */
    public static class Video {

        /**
         * 获取视频上传地址和凭证
         * @param title
         * @param fileSuffix
         * @return
         * @throws Exception
         */
        public static CreateUploadVideoResponse createUploadVideo(String title,String fileSuffix) throws Exception {
            return createUploadVideo(title,fileSuffix,null);
        }
        /**
         * 获取视频上传地址和凭证
         * @param title
         * @param fileSuffix
         * @return
         * @throws Exception
         */
        public static CreateUploadVideoResponse createUploadVideo(String title,String fileSuffix,String coverUrl) throws Exception {
            CreateUploadVideoRequest request = new CreateUploadVideoRequest();
            request.setTitle(title);
            if (fileSuffix.contains(".")) {
                request.setFileName(StringUtil.uuid() + fileSuffix);
            }else {
                request.setFileName(StringUtil.uuid() + "." + fileSuffix);
            }
            if (StringUtil.isNotBlank(coverUrl)) {
                request.setCoverURL(coverUrl);
            }
            return client.getAcsResponse(request);
        }

        /**
         * 修改视频信息
         * @param videoId 视频id
         * @param title 标题
         * @param coverUrl 水印地址
         * @return
         * @throws Exception
         */
        public static UpdateVideoInfoResponse updateVideoInfo(String videoId, String title, String coverUrl, String description) throws Exception {
            UpdateVideoInfoRequest request = new UpdateVideoInfoRequest();
            request.setVideoId(videoId);
            if(StringUtil.isNotBlank(title)) {
                request.setTitle(title);
            }
            if(StringUtil.isNotBlank(coverUrl)) {
                request.setCoverURL(coverUrl);
            }
            if(StringUtil.isNotBlank(description)) {
                request.setDescription(description);
            }
            return client.getAcsResponse(request);
        }

        /**
         * 获取图片上传地址和凭证
         * @param videoId
         * @return
         * @throws Exception
         */
        public static RefreshUploadVideoResponse refreshUploadVideo(String videoId) throws Exception {
            RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        }

        /**
         * 获取视频播放凭证
         * @param videoId
         * @return
         * @throws Exception
         */
        public static GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) throws Exception {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        }

        /**
         * 获取视频播放地址
         * @param videoId
         * @return
         * @throws Exception
         */
        public static GetPlayInfoResponse getPlayInfo(String videoId) throws Exception {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        }
        /**
         * 删除视频
         * @param videoId
         * @return
         * @throws Exception
         */
        public static DeleteVideoResponse deleteVideo(String videoId) throws Exception {
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            return client.getAcsResponse(request);
        }

    }

    /**
     * 转码模块
     */
    public static class Transcode {
        /**
         * 创建转码模板组
         * @param transcodeName
         * @param transcodeTemplateList
         * @return
         * @throws Exception
         */
        public static AddTranscodeTemplateGroupResponse addTranscodeTemplateGroup(String transcodeName, JSONArray transcodeTemplateList) throws Exception {
            AddTranscodeTemplateGroupRequest request = new AddTranscodeTemplateGroupRequest();
            //转码模板ID
            request.setName(transcodeName);
            request.setTranscodeTemplateList(transcodeTemplateList.toJSONString());
            return client.getAcsResponse(request);
        }

        /**
         * 构建创建转码模板
         * @param templateName
         * @return
         */
        public static JSONArray buildAddTranscodeTemplateList(String templateName,String watermarkId) {
            JSONObject transcodeTemplate = new JSONObject();
            //模板名称
            transcodeTemplate.put("TemplateName", templateName);
            //清晰度
            transcodeTemplate.put("Definition", "HD");
            //视频流转码配置
            JSONObject video = new JSONObject();
            video.put("Width", "1280");
            video.put("Bitrate", "1500");
            video.put("Fps", "25");
            video.put("Remove", "false");
            video.put("Codec", "H.265");
            video.put("Gop", "250");
            transcodeTemplate.put("Video", video);
            //音频流转码配置
            JSONObject audio = new JSONObject();
            audio.put("Codec", "AAC");
            audio.put("Bitrate", "96");
            audio.put("Remove", "false");
            audio.put("Channels", "2");
            audio.put("Samplerate", "44100");
            transcodeTemplate.put("Audio", audio);
            //封装容器
            JSONObject container = new JSONObject();
            container.put("Format", "mp4");
            transcodeTemplate.put("Container", container);
            //条件转码配置
            JSONObject transconfig = new JSONObject();
            transconfig.put("IsCheckReso", false);
            transconfig.put("IsCheckResoFail", false);
            transconfig.put("IsCheckVideoBitrate", false);
            transconfig.put("IsCheckVideoBitrateFail", false);
            transconfig.put("IsCheckAudioBitrate", false);
            transconfig.put("IsCheckAudioBitrateFail", false);
            transcodeTemplate.put("TransConfig", transconfig);
            //加密配置(只支持m3u8)
//            JSONObject encryptSetting = new JSONObject();
//            encryptSetting.put("EncryptType", "Private");
//            transcodeTemplate.put("EncryptSetting", encryptSetting);
            //水印ID(多水印关联)
            JSONArray watermarkIdList = new JSONArray();
            if (StringUtil.isBlank(watermarkId)) {
                //USER_DEFAULT_WATERMARK 代表默认水印ID
                watermarkIdList.add("USER_DEFAULT_WATERMARK");
            } else {
                watermarkIdList.add(watermarkId);
            }
            transcodeTemplate.put("WatermarkIds", watermarkIdList);
            JSONArray transcodeTemplateList = new JSONArray();
            transcodeTemplateList.add(transcodeTemplate);
            return transcodeTemplateList;
        }

        /**
         * 修改转码模板组配置
         * @param transcodeName
         * @param transcodeTemplateList
         * @param transcodeId
         * @return
         * @throws Exception
         */
        public static UpdateTranscodeTemplateGroupResponse updateTranscodeTemplateGroup(String transcodeName, JSONArray transcodeTemplateList, String transcodeId) throws Exception {
            UpdateTranscodeTemplateGroupRequest request = new UpdateTranscodeTemplateGroupRequest();
            request.setName(transcodeName);
            //转码模板组ID
            request.setTranscodeTemplateGroupId(transcodeId);
            request.setTranscodeTemplateList(transcodeTemplateList.toJSONString());
            return client.getAcsResponse(request);
        }

        /**
         * 构建修改转码模板
         * @param transcodeId
         * @param templateName
         * @return
         */
        public static JSONArray buildUpdateTranscodeTemplateList(String transcodeId,String templateName,String watermarkId) {
            JSONObject transcodeTemplate = new JSONObject();
            //转码模板ID
            transcodeTemplate.put("TranscodeTemplateId", transcodeId);
            //模板名称
            transcodeTemplate.put("TemplateName", templateName);
            //视频流转码配置
            //清晰度
            transcodeTemplate.put("Definition", "HD");
            //视频流转码配置
            JSONObject video = new JSONObject();
            video.put("Width", "1280");
            video.put("Bitrate", "1500");
            video.put("Fps", "25");
            video.put("Remove", "false");
            video.put("Codec", "H.264");
            video.put("Gop", "250");
            transcodeTemplate.put("Video", video);
            //音频流转码配置
            JSONObject audio = new JSONObject();
            audio.put("Codec", "AAC");
            audio.put("Bitrate", "64");
            audio.put("Remove", "false");
            audio.put("Channels", "2");
            audio.put("Samplerate", "32000");
            transcodeTemplate.put("Audio", audio);
            //封装容器
            JSONObject container = new JSONObject();
            container.put("Format", "mp4");
            transcodeTemplate.put("Container", container);
            //条件转码配置
            JSONObject transconfig = new JSONObject();
            transconfig.put("IsCheckReso", false);
            transconfig.put("IsCheckResoFail", false);
            transconfig.put("IsCheckVideoBitrate", false);
            transconfig.put("IsCheckVideoBitrateFail", false);
            transconfig.put("IsCheckAudioBitrate", false);
            transconfig.put("IsCheckAudioBitrateFail", false);
            transcodeTemplate.put("TransConfig", transconfig);
            //加密配置(只支持m3u8)
//            JSONObject encryptSetting = new JSONObject();
//            encryptSetting.put("EncryptType", "Private");
//            transcodeTemplate.put("EncryptSetting", encryptSetting);
            //水印ID(多水印关联)
            JSONArray watermarkIdList = new JSONArray();
            if (StringUtil.isBlank(watermarkId)) {
                //USER_DEFAULT_WATERMARK 代表默认水印ID
                watermarkIdList.add("USER_DEFAULT_WATERMARK");
            } else {
                watermarkIdList.add(watermarkId);
            }
            transcodeTemplate.put("WatermarkIds", watermarkIdList);
            JSONArray transcodeTemplateList = new JSONArray();
            transcodeTemplateList.add(transcodeTemplate);
            return transcodeTemplateList;
        }

        /**
         * 设置默认转码模板组
         * @param transcodeId
         * @return
         * @throws Exception
         */
        public static SetDefaultTranscodeTemplateGroupResponse setDefaultTranscodeTemplateGroup(String transcodeId) throws Exception {
            SetDefaultTranscodeTemplateGroupRequest request = new SetDefaultTranscodeTemplateGroupRequest();
            request.setTranscodeTemplateGroupId(transcodeId);
            return client.getAcsResponse(request);
        }

        public static GetTranscodeTemplateGroupResponse getTranscodeTemplateGroup(String transcodeId) throws Exception {
            GetTranscodeTemplateGroupRequest request = new GetTranscodeTemplateGroupRequest();
            request.setTranscodeTemplateGroupId(transcodeId);
            return client.getAcsResponse(request);
        }

        /**
         * 删除转码模板组配置
         * @param transcodeId
         * @return
         * @throws Exception
         */
        public static DeleteTranscodeTemplateGroupResponse deleteTranscodeTemplateGroup(String transcodeId) throws Exception {
            DeleteTranscodeTemplateGroupRequest request = new DeleteTranscodeTemplateGroupRequest();
            request.setTranscodeTemplateGroupId(transcodeId);
            request.setForceDelGroup("false");
            request.setTranscodeTemplateIds("ddddddd,ffffffff");
            return client.getAcsResponse(request);
        }

        /**
         * 提交媒体处理作业
         */
        public static SubmitTranscodeJobsResponse submitTranscodeJobs(String videoId,String transcodeId, String overrideParams,String encryptConfig) throws Exception {
            SubmitTranscodeJobsRequest request = new SubmitTranscodeJobsRequest();
            //需要转码的视频ID
            request.setVideoId(videoId);
            //转码模板ID
            request.setTemplateGroupId(transcodeId);
            //覆盖参数，暂只支持水印部分参数替换(只有需要替换水印相关信息才需要传递)
            if (StringUtil.isNotBlank(overrideParams)) {
                request.setOverrideParams(overrideParams);
            }
            //HLS标准加密配置(只有标准加密才需要传递)
            if (StringUtil.isNotBlank(encryptConfig)) {
                request.setEncryptConfig(encryptConfig);
            }
            return client.getAcsResponse(request);
        }

        /**
         * 构建HLS标准加密的配置信息
         * @return
         * @throws ClientException
         */
        public static String buildEncryptConfig(String checkUrl) throws ClientException {
            //点播给用户在KMS(秘钥管理服务)中的Service Key，可在用户秘钥管理服务对应的区域看到描述为vod的service key
            String serviceKey = "fb1168ae-af54-40c8-a578-f920dc9538ba";
            //随机生成一个加密的秘钥，返回的response包含明文秘钥以及密文秘钥，
            //视频标准加密只需要传递密文秘钥即可
            GenerateDataKeyResponse response = generateDataKey(serviceKey);
            JSONObject encryptConfig = new JSONObject();
            //解密接口地址，该参数需要将每次生成的密文秘钥与接口URL拼接生成，表示每个视频的解密的密文秘钥都不一样
            //至于Ciphertext这个解密接口参数的名称，用户可自行制定，这里只作为参考参数名称
            encryptConfig.put("DecryptKeyUri", checkUrl + "/open/checkVodToken?Ciphertext=" + response.getCiphertextBlob());
            //秘钥服务的类型，目前只支持KMS
            encryptConfig.put("KeyServiceType", "KMS");
            //密文秘钥
            encryptConfig.put("CipherText", response.getCiphertextBlob());
            return encryptConfig.toJSONString();
        }

        /**
         * 构建密匙
         * @param serviceKey
         * @return
         * @throws ClientException
         */
        public static GenerateDataKeyResponse generateDataKey(String serviceKey) throws ClientException {
            GenerateDataKeyRequest request = new GenerateDataKeyRequest();
            request.setKeyId(serviceKey);
            request.setKeySpec("AES_128");
            return client.getAcsResponse(request);
        }

        /**
         * 1、构建覆盖参数，目前只支持图片水印文件地址、文字水印的内容覆盖；
         * 2、需要替换的水印信息对应水印ID必须是关联在指定的模板ID(即TranscodeTemplateId)中；
         * 3、不支持通过媒体处理接口去增加一个没有关联上的水印
         * 注意：图片水印的文件存储源站需要和发起转码的视频存储源站一致
         * @return
         */
        public static JSONObject buildOverrideParams(String watermarkId,String watermarkUrl) {
            JSONObject overrideParams = new JSONObject();
            JSONArray watermarks = new JSONArray();
            //图片水印文件地址替换
            JSONObject watermark1 = new JSONObject();
            //模板上面关联需要替换的水印文件图片水印ID
            watermark1.put("WatermarkId", watermarkId);
            //需要替换成对应图片水印文件的OSS地址，水印文件存储源站需要和视频存储源站一致
            watermark1.put("FileUrl", watermarkUrl);
            watermarks.add(watermark1);
//            //文字水印内容替换
//            JSONObject watermark2 = new JSONObject();
//            //模板上面关联需要替换内容的文字水印ID
//            watermark2.put("WatermarkId", watermarkId);
//            //需要替换成对应的内容
//            watermark2.put("Content", "用户ID：66666");
//            watermarks.add(watermark2);
            overrideParams.put("Watermarks", watermarks);
            return overrideParams;
        }

    }

    /**
     * 水印模块
     */
    public static class Watermark {
        /**
         * 添加水印配置信息函数
         * @param watermarkName
         * @param watermarkUrl
         * @param watermarkConfig 水印数据
         * @return
         * @throws Exception
         */
        public static AddWatermarkResponse addWatermark(String watermarkName, String watermarkUrl, JSONObject watermarkConfig) throws Exception {
            AddWatermarkRequest request = new AddWatermarkRequest();
            //水印名称
            request.setName(watermarkName);
            //获取水印文件在oss的URL
            //图片水印必传图片文件的oss文件地址，水印文件必须和视频在同一个区域，例如:华东2视频，水印文件必须存放在华东2
            request.setFileUrl(watermarkUrl);
            //图片水印的位置配置数据
            request.setWatermarkConfig(watermarkConfig.toJSONString());
            //文字水印:Text; 图片水印:Image
            request.setType(watermarkConfig.getString("watermarkType"));
            return client.getAcsResponse(request);
        }

        /**
         * 修改水印配置信息函数
         * 注意：不支持图片文件地址的修改，如果更换请创建新的水印信息
         * @param watermarkName
         * @param watermarkId
         * @param watermarkConfig
         * @return
         * @throws Exception
         */
        public static UpdateWatermarkResponse updateWatermark(String watermarkName, String watermarkId, JSONObject watermarkConfig) throws Exception {
            UpdateWatermarkRequest request = new UpdateWatermarkRequest();
            request.setName(watermarkName);
            //需要更新配置信息的水印ID
            request.setWatermarkId(watermarkId);
            //水印配置数据
            //图片水印的位置配置数据
            request.setWatermarkConfig(watermarkConfig.toJSONString());
            return client.getAcsResponse(request);
        }

        /**
         * 删除水印配置信息函数
         * @param watermarkId
         * @return
         * @throws Exception
         */
        public static DeleteWatermarkResponse deleteWatermark(String watermarkId) throws Exception {
            DeleteWatermarkRequest request = new DeleteWatermarkRequest();
            request.setWatermarkId(watermarkId);
            return client.getAcsResponse(request);
        }

        /**
         * 设置默认水印配置信息函数
         * @param watermarkId
         * @return
         * @throws Exception
         */
        public static SetDefaultWatermarkResponse setDefaultWatermark(String watermarkId) throws Exception {
            SetDefaultWatermarkRequest request = new SetDefaultWatermarkRequest();
            //设置默认的水印ID
            request.setWatermarkId(watermarkId);
            return client.getAcsResponse(request);
        }

        /**
         * 构建图片水印的配置数据，根据具体设置需求修改对应的参数值
         * @return
         */
        public static JSONObject buildImageWatermarkConfig() {
            JSONObject watermarkConfig = new JSONObject();
            watermarkConfig.put("watermarkType","Image");
            //水印的横向偏移距离
            watermarkConfig.put("Dx", "8");
            //水印的纵向偏移距离
            watermarkConfig.put("Dy", "8");
            //水印显示的宽
            watermarkConfig.put("Width", "55");
            //水印显示的高
            watermarkConfig.put("Height", "55");
            //水印显示的相对位置(左上、右上、左下、右下)
            watermarkConfig.put("ReferPos", "BottomRight");
            //水印显示的时间线(开始显示和结束显示时间)
            JSONObject timeline = new JSONObject();
            //水印开始显示时间
            timeline.put("Start", "2");
            //水印结束显示时间
            timeline.put("Duration", "ToEND");
            watermarkConfig.put("Timeline", timeline);
            return watermarkConfig;
        }
        /**
         * 构建文字水印的配置数据，根据具体设置需求修改对应的参数值
         * @return
         */
        public static JSONObject buildTextWatermarkConfig() {
            JSONObject watermarkConfig = new JSONObject();
            watermarkConfig.put("watermarkType","Text");
            //文字水印显示的内容
            watermarkConfig.put("Content", "testwatermark");
            //文字水印的字体名称
            watermarkConfig.put("FontName", "SimSun");
            //文字水印的字体大小
            watermarkConfig.put("FontSize", 25);
            //文字水印的颜色(也可为RGB颜色取值，例如:#000000)
            watermarkConfig.put("FontColor", "Black");
            //文字水印的透明度
            watermarkConfig.put("FontAlpha", "0.2");
            //文字水印的字体描边颜色(也可为RGB颜色取值，例如:#ffffff)
            watermarkConfig.put("BorderColor", "White");
            //文字水印的描边宽度
            watermarkConfig.put("BorderWidth", 1);
            //文字水印距离视频画面上边的偏移距离
            watermarkConfig.put("Top", 20);
            //文字水印距离视频画面左边的偏移距离
            watermarkConfig.put("Left", 15);
            return watermarkConfig;
        }

    }

    /**
     * 视频编辑货块
     */
    public static class EditingVideo {
        /**
         * 制作修改视频
         * @param timeline
         * @param mediaMetadata
         * @param produceConfig
         * @return
         * @throws Exception
         */
        public ProduceEditingProjectVideoResponse produceEditingProjectVideo(String timeline, String mediaMetadata, String produceConfig) throws Exception {
            ProduceEditingProjectVideoRequest request = new ProduceEditingProjectVideoRequest();
            // Build Editing Project Timeline
            request.setTimeline(timeline);
            // Set Produce Media Metadata
            request.setMediaMetadata(mediaMetadata);
            // Set Produce Configuration
            request.setProduceConfig(produceConfig);

            return client.getAcsResponse(request);
        }

        /**
         * 构建媒体信息
         * @param title
         * @param description
         * @param coverUrl
         * @return
         */
        public static String buildMediaMetadata(String title,String description,String coverUrl){
            JSONObject mediaMetadata = new JSONObject();
            // Produce Media Title
            mediaMetadata.put("Title", title);
            // Produce Media Description
            mediaMetadata.put("Description", description);
            // Produce Media UserDefined Cover URL
            mediaMetadata.put("CoverURL", coverUrl);
            // Produce Media Category ID
            mediaMetadata.put("CateId", null);
            // Produce Media Category Name
            mediaMetadata.put("Tags", "Tag1,Tag2,Test");
            return mediaMetadata.toString();
        }
        public static String buildProduceConfig(){
            JSONObject produceConfig = new JSONObject();
        /*
         The produce process can generate media mezzanine file. You can use the mezzanine file to transcode other media files，just like the transcode process after file upload finished. This field describe the Transocde TemplateGroup ID after produce mezzanine finished.
         1. Not required
         2. Use default transcode template group id when empty
         */
            produceConfig.put("TemplateGroupId", null);
            return produceConfig.toString();
        }
        /**
         * This Sample shows how to merge two videos
         */
        public static String buildTimeline(String... videoIds){
            JSONObject timeline = new JSONObject();
            // Video Track
            JSONArray videoTracks = new JSONArray();
            JSONObject videoTrack = new JSONObject();
            // Video Track Clips
            JSONArray videoTrackClips = new JSONArray();
            for (String videoId : videoIds) {
                JSONObject videoTrackClip = new JSONObject();
                videoTrackClip.put("VideoId", videoId);
                videoTrackClip.put("Type","Video");
                videoTrackClips.add(videoTrackClip);
            }
            videoTrack.put("VideoTrackClips", videoTrackClips);
            videoTrack.put("Count",videoIds.length);
            videoTracks.add(videoTrack);
            timeline.put("VideoTracks", videoTracks);
            return timeline.toString();
        }
    }

}
