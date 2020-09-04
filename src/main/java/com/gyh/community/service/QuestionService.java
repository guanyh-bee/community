package com.gyh.community.service;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.dto.PaginationDTO;
import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.mapper.QuestionExtMapper;
import com.gyh.community.mapper.QuestionMapper;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.Question;
import com.gyh.community.model.QuestionExample;
import com.gyh.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gyh
 * @create 2020-07-23 10:49
 */
@Service
public class QuestionService {
    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    QuestionMapper questionMapper;

    @Autowired(required = false)
    QuestionExtMapper questionExtMapper;
    //获取所有问题列表，有搜索展示搜索相关问题
    public PaginationDTO list(Integer page, Integer size,String search) {
        QuestionExample questionExample = new QuestionExample();
        int totalCount;
        if(search == null){
            totalCount = (int) questionMapper.countByExample(questionExample);
        }else {
            questionExample.createCriteria().andTitleLike("%"+search+"%");
            totalCount = (int) questionMapper.countByExample(questionExample);
        }


        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);
        Integer offSet = (paginationDTO.getPage()-1)*size;
        RowBounds rowBounds = new RowBounds(offSet,size);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.setOrderByClause("gmt_modified desc");
        List<Question> questions;
        if(search == null){
            questions  = questionMapper.selectByExampleWithRowbounds(questionExample1,rowBounds);
        }else {
            questionExample1.createCriteria().andTitleLike("%"+search+"%");
            questions = questionMapper.selectByExampleWithRowbounds(questionExample1,rowBounds);
        }


        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }
    //获取用户问题列表
    public PaginationDTO getList(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        int totalCount = (int) questionMapper.countByExample(questionExample);
        //Integer totalCount = questionMapper.countByUserId(userId);
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);
        Integer offSet = (page-1)*size;
        questionExample.clear();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        RowBounds rowBounds = new RowBounds(offSet,size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, rowBounds);


        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUEST_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            Integer updated = questionMapper.updateByPrimaryKeySelective(question);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUEST_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
