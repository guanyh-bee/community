package com.gyh.community.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author gyh
 * @create 2020-07-30 12:54
 */
@Data
public class CommentDTO implements Serializable {
    private Long parentId;
    private String content;
    private Integer type;
}
