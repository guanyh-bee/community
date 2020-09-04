package com.gyh.community.controller;


import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.enums.CommentTypeEnum;

import com.gyh.community.model.Question;
import com.gyh.community.model.User;
import com.gyh.community.service.CommentService;
import com.gyh.community.service.NotificationService;
import com.gyh.community.service.QuestionService;
import com.gyh.community.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author gyh
 * @create 2020-07-25 16:01
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model, HttpSession session){


        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incView(id);
        questionDTO.setViewCount(questionDTO.getViewCount()+1);
        List<QuestionDTO> relatedQuestions = commentService.listRalated(questionDTO);
        List<CommentVO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);

        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("commentVOs",comments);
        model.addAttribute("questionDTO",questionDTO);
        User user = (User) session.getAttribute("user");
        Integer unreadCount = notificationService.getUnreadCount(user.getId());
        session.setAttribute("SessionUnreadCount",unreadCount);
        return "question";
    }
}
