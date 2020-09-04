package com.gyh.community.controller;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.dto.CommentDTO;
import com.gyh.community.dto.ResultDTO;
import com.gyh.community.enums.CommentTypeEnum;
import com.gyh.community.enums.NotificationStatusEnum;
import com.gyh.community.enums.NotificationTypeEnum;
import com.gyh.community.mapper.NotificationMapper;
import com.gyh.community.model.Comment;
import com.gyh.community.model.Notification;
import com.gyh.community.model.User;
import com.gyh.community.service.CommentService;
import com.gyh.community.vo.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


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
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_CONTENT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());


        commentService.insertSelective(comment,user,session);

        return new ResultDTO(200,"成功",comment);
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<Comment> comments(@PathVariable("id")Integer id){
        List<CommentVO> commentVOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentVOS);
    }
}
