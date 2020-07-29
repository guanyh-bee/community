package com.gyh.community.controller;

import com.gyh.community.dto.AccessTokenDTO;
import com.gyh.community.dto.GithubUser;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.User;
import com.gyh.community.provider.GithubProvider;
import com.gyh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author gyh
 * @create 2020-07-06 14:55
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    UserService userService;
    

    @GetMapping("callback")
    public String callback(@RequestParam("code")String code, @RequestParam("state")String state, HttpSession session, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.17b170297832cc61");
        accessTokenDTO.setClient_secret("4f63b4e0015557ce11572e9bc4f37cf06ce26b49");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:9999/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUseruser = githubProvider.getUser(accessToken);
        if(githubUseruser == null){
            return "redirect:/";
        }else {
            User user = new User();
            user.setAccountId(String.valueOf(githubUseruser.getId()));
            user.setName(githubUseruser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatar(githubUseruser.getAvatar_url());
            user = userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);

            session.setAttribute("user",user);
            return "redirect:/";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
