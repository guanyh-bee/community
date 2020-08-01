package com.gyh.community.controller;

import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.model.Comment;
import com.gyh.community.service.CommentService;
import com.gyh.community.service.QuestionService;
import com.gyh.community.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){


        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incView(id);
        questionDTO.setViewCount(questionDTO.getViewCount()+1);

        List<CommentVO> comments = commentService.listByQuestionId(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
