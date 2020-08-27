package com.gyh.community.mapper;

import com.gyh.community.model.Comment;

/**
 * @author gyh
 * @create 2020-08-20 13:39
 */
public interface CommentExtMapper {
    Integer incCommentCount(Comment comment);
}
