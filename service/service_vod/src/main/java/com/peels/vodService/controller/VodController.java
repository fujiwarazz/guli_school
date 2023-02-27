package com.peels.vodService.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Exception.FileUploadException;
import com.peels.commonUtils.Result.Result;
import com.peels.vodService.service.VodService;
import com.peels.vodService.utils.InitObject;
import com.peels.vodService.utils.VodUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduVod/video")
public class VodController {

    @Resource
    private VodService vodService;

    /**
     * 上传至阿里云
     *
     * @param file
     * @return
     */

    @PostMapping("/upload")
    public Result<?> uploadVideo(MultipartFile file) throws FileUploadException {
        try {
            String videoId = vodService.uploadVideo(file);
            Map<String, String> map = new HashMap<>();
            map.put("videoId", videoId);
            return Result.Success(map);
        } catch (IOException e) {
            throw new FileUploadException(20003, "文件上传错误");
        }
    }

    @DeleteMapping("removeById/{id}")
    public Result<?> removeById(@PathVariable String id) throws FileUploadException {
        Boolean res = vodService.removeById(id);
        if (res) {
            return Result.Success();
        } else {
            return Result.Fail(20004, "文件上传失败");
        }
    }

    @DeleteMapping("removeByIds")
    public Result<?> removeByIds(@RequestBody List<String> ids) {
        try {
            vodService.removeByIds(ids);
            return Result.Success();
        } catch (Exception e) {
            return Result.Fail();
        }
    }

    @GetMapping("getPlayAuth/{id}")
    public Result<?> getPlayAuth(@PathVariable String id) {

        try {
            //初始化对象
            DefaultAcsClient client = InitObject.initVodClient(VodUtil.KEY_ID, VodUtil.KEY_SECRET);
            //创建获取视频地址的request和response
            GetPlayInfoResponse response = new GetPlayInfoResponse();
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(id);
            //调用初始化方法,传递request,获取数据
            response = client.getAcsResponse(request);

            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            GetPlayInfoResponse.PlayInfo playInfo1 = playInfoList.get(0);

            return Result.UseMap().addMap("url",playInfo1.getPlayURL());
        } catch (ClientException e) {
            e.printStackTrace();
            return Result.Fail();
        }
    }
}
