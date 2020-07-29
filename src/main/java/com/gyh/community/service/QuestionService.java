package com.gyh.community.service;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.Exception.ICustomizeErrorCode;
import com.gyh.community.dto.PaginationDTO;
import com.gyh.community.dto.QuestionDTO;
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


    public PaginationDTO list(Integer page, Integer size) {
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        int totalCount = (int) questionMapper.countByExample(questionExample);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);
        Integer offSet = (page-1)*size;
        RowBounds rowBounds = new RowBounds(offSet,size);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),rowBounds);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO getList(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        int totalCount = (int) questionMapper.countByExample(questionExample);
        //Integer totalCount = questionMapper.countByUserId(userId);
        PaginationDTO paginationDTO = new PaginationDTO();
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
        paginationDTO.setQuestionDTOS(questionDTOS);
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
}
