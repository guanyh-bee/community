package com.gyh.community.model;

import lombok.Data;

/**
 * @author gyh
 * @create 2020-07-16 14:59
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;


}
