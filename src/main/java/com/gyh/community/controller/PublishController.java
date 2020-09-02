package com.gyh.community.controller;

import com.gyh.community.cache.TagCache;
import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.dto.TagDTO;
import com.gyh.community.mapper.QuestionMapper;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.Question;
import com.gyh.community.model.User;
import com.gyh.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author gyh
 * @create 2020-07-16 11:42
 */
@Controller
public class PublishController {
    @Autowired
    QuestionService questionServicer;
    @Autowired(required = false)
    UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("selectTags",TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpSession session, Model model, HttpServletRequest request){
        model.addAttribute("question",question);
        model.addAttribute("selectTags",TagCache.get());
        if(question.getTitle()==""){
            model.addAttribute("msg","标题不能为空");
            return "publish";
        }
        if(question.getDescription()==""){
            model.addAttribute("msg","问题补充不能为空");
            return "publish";
        }
        if(question.getTag()==""){
            model.addAttribute("msg","标签不能为空");
            return "publish";
        }

        String s = TagCache.checkTag(question.getTag());
        if(StringUtils.isNotBlank(s)){
            model.addAttribute("msg","输入非法标签"+s);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");


        if(user == null){
            model.addAttribute("msg","没有权限");

            return "publish";
        }else {

            question.setCreator(user.getId());
            questionServicer.createOrUpdate(question);
            return "redirect:/";
        }

    }
    @RequestMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        QuestionDTO question = questionServicer.getById(id);
        model.addAttribute("question",question);
        model.addAttribute("selectTags",TagCache.get());
        if(question.getTitle()==""){
            model.addAttribute("msg","标题不能为空");
            return "publish";
        }
        if(question.getDescription()==""){
            model.addAttribute("msg","问题补充不能为空");
            return "publish";
        }
        if(question.getTag()==""){
            model.addAttribute("msg","标签不能为空");
            return "publish";
        }
        return "publish";
    }
}
