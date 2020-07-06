package com.gyh.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * @author gyh
 * @create 2020-07-02 11:55
 */
@Controller
public class IndexConttroller {
    @RequestMapping("/")
    public String hello(){

        return "index";
    }
}
