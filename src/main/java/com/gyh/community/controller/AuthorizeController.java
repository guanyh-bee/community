package com.gyh.community.controller;

import com.gyh.community.dto.AccessTokenDTO;
import com.gyh.community.dto.GithubUser;
import com.gyh.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author gyh
 * @create 2020-07-06 14:55
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("callback")
    public String callback(@RequestParam("code")String code, @RequestParam("state")String state, HttpSession session){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.17b170297832cc61");
        accessTokenDTO.setClient_secret("4f63b4e0015557ce11572e9bc4f37cf06ce26b49");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user == null){
            return "redirect:/";
        }else {
            session.setAttribute("user",user);
            return "redirect:/";
        }
    }


}
