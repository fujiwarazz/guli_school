package com.peels.ossService.service;

import com.peels.commonUtils.Result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    Result<?> uploadAvatar(MultipartFile file);
}
