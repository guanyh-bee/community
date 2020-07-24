package com.gyh.community.dto;

import com.gyh.community.model.User;
import lombok.Data;

/**
 * @author gyh
 * @create 2020-07-23 10:48
 */
@Data
public class QuestionDTO {
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
    private User user;

}
