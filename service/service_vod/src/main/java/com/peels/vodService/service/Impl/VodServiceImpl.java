package com.peels.vodService.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Exception.FileUploadException;
import com.peels.vodService.service.VodService;
import com.peels.vodService.utils.InitObject;
import com.peels.vodService.utils.VodUtil;
import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) throws FileUploadException {
        UploadStreamResponse response = null;
        try {
            String name =file.getOriginalFilename();
            assert name != null;
            String title = name.replace(".mp4","") + RandomUtil.randomString("GLXY",6);
            UploadStreamRequest request = new UploadStreamRequest(VodUtil.KEY_ID, VodUtil.KEY_SECRET, title, name, file.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            response = uploader.uploadStream(request);
        } catch (IOException e) {
            throw new FileUploadException(20003,"文件上传失败");
        }
        String videoId;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = "";
        }
        return videoId;
    }

    @Override
    public Boolean removeById(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(VodUtil.KEY_ID, VodUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void removeByIds(List<String> ids) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(VodUtil.KEY_ID, VodUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = String.join(",", ids);
            request.setVideoIds(join);
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new BusinessException(20002,"批量删除失败");
        }
    }
}
