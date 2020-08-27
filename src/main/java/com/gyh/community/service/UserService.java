package com.gyh.community.service;

import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.User;
import com.gyh.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    UserMapper userMapper;
    public User createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);

        if(users.size() == 0){
          //create
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insertSelective(user);
            return user;
        }else {
            //update
            User dbUser = users.get(0);
            User update = new User();
            update.setGmtModified(System.currentTimeMillis());
            update.setBio(user.getBio());
            update.setAvatar(user.getAvatar());
            update.setAccountId(user.getAccountId());
            update.setName(user.getName());
            update.setToken(user.getToken());
            update.setId(dbUser.getId());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(update,userExample);
            return update;
        }
    }
}
