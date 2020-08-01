package com.gyh.community.controller;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.dto.CommentDTO;
import com.gyh.community.dto.ResultDTO;
import com.gyh.community.model.Comment;
import com.gyh.community.model.User;
import com.gyh.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @author gyh
 * @create 2020-07-30 12:47
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO<Comment> post( @RequestBody  CommentDTO commentDTO, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());

        commentService.insertSelective(comment);
        return new ResultDTO(200,"成功",comment);
    }
}
