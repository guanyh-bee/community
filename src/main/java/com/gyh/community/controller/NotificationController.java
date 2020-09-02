package com.gyh.community.controller;

import com.gyh.community.dto.NotificationDTO;
import com.gyh.community.mapper.NotificationMapper;
import com.gyh.community.model.User;
import com.gyh.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @RequestMapping("/notification/{id}")
    public String readNotification(@PathVariable("id") Integer id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user ==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        return "redirect:/question/"+notificationDTO.getOuterId();
    }
}
