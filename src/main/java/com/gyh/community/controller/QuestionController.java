package com.gyh.community.controller;

import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author gyh
 * @create 2020-07-25 16:01
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
