package com.peels.eduService.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    List<VideoVo>children;



}
