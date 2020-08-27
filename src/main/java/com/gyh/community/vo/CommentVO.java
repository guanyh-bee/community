package com.gyh.community.vo;

import com.gyh.community.model.User;
import lombok.Data;

/**
 * @author gyh
 * @create 2020-08-01 15:43
 */
@Data
public class CommentVO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
