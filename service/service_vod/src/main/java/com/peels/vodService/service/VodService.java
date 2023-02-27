package com.peels.vodService.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VodService {

    String uploadVideo(MultipartFile file) throws IOException;

    Boolean removeById(String id);

    void removeByIds(List<String> ids);
}
