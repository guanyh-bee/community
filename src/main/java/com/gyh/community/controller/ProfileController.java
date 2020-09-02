package com.gyh.community.controller;

import com.gyh.community.dto.PaginationDTO;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.Notification;
import com.gyh.community.model.User;
import com.gyh.community.service.NotificationService;
import com.gyh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author gyh
 * @create 2020-07-25 10:14
 */
@Controller
public class ProfileController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
           return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            Integer userId = user.getId();
            PaginationDTO paginationDTO = questionService.getList(userId, page, size);
            model.addAttribute("pagination",paginationDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");

            PaginationDTO paginationDTO = notificationService.getList(user.getId(), page, size);

            model.addAttribute("pagination",paginationDTO);
        }
        Integer unreadCount = notificationService.getUnreadCount(user.getId());
        model.addAttribute("unreadCount",unreadCount);
        return "profile";

    }
}
