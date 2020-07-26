package com.gyh.community.mapper;

import com.gyh.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author gyh
 * @create 2020-07-15 14:35
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatar})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(String accountId);
    @Update("update user set name=#{name},token=#{token},avatar=#{avatar},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    Integer update(User user);
}
