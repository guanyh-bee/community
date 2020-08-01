package com.gyh.community.service;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.enums.CommentTypeEnum;
import com.gyh.community.mapper.CommentMapper;
import com.gyh.community.mapper.QuestionExtMapper;
import com.gyh.community.mapper.QuestionMapper;
import com.gyh.community.model.Comment;
import com.gyh.community.model.CommentExample;
import com.gyh.community.model.Question;
import com.gyh.community.model.QuestionExample;
import com.gyh.community.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gyh
 * @create 2020-07-30 14:25
 */
@Service
public class CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    QuestionExtMapper questionExtMapper;


    @Transactional
    public void insertSelective(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            long parentId = comment.getParentId();
            Question question = questionMapper.selectByPrimaryKey((int) parentId);
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUEST_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }
    }

    public List<CommentVO> listByQuestionId(Integer id) {
        CommentExample commentExample = new CommentExample();
        long idC = id;
        commentExample.createCriteria().andParentIdEqualTo(idC).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }else {

        }

        return null;
    }
}
