package com.gyh.community.mapper;

import com.gyh.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author gyh
 * @create 2020-07-16 14:52
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void question(Question question);
    @Select("select * from question limit #{offSet},#{size}")
    List<Question> list(@Param("offSet") Integer offSet, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator=#{userId} limit #{offSet},#{size}")
    List<Question> getList(@Param("userId") Integer userId,@Param("offSet") Integer offSet, @Param("size") Integer size);
    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(Integer userId);
    @Select("select * from question where id=#{id}")
    Question findById(Integer id);
}
