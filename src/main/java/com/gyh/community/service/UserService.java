package com.gyh.community.service;

import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired(required = false)
    UserMapper userMapper;
    public User createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());

        if(dbUser==null){
          //create
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
            return user;
        }else {
            //update
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatar(user.getAvatar());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
            return dbUser;
        }
    }
}
