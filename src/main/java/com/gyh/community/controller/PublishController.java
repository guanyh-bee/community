package com.gyh.community.controller;

import com.gyh.community.mapper.QuestionMapper;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.Question;
import com.gyh.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author gyh
 * @create 2020-07-16 11:42
 */
@Controller
public class PublishController {
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpSession session, Model model, HttpServletRequest request){
        model.addAttribute("question",question);
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
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                };
            }
        }

        if(user == null){
            model.addAttribute("msg","没有权限");

            return "publish";
        }else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setCreator(user.getId());
            questionMapper.question(question);
            return "redirect:/";
        }

    }
}
