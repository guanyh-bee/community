package com.gyh.community.service;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.dto.CommentDTO;
import com.gyh.community.dto.QuestionDTO;
import com.gyh.community.enums.CommentTypeEnum;
import com.gyh.community.enums.NotificationStatusEnum;
import com.gyh.community.enums.NotificationTypeEnum;
import com.gyh.community.mapper.*;
import com.gyh.community.model.*;
import com.gyh.community.vo.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    CommentExtMapper commentExtMapper;
    @Autowired(required = false)
    NotificationMapper notificationMapper;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void insertSelective(Comment comment, User commentator, HttpSession session) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            Comment comment1 = new Comment();
            comment1.setId(comment.getParentId());
            comment1.setCommentCount(1);
            commentExtMapper.incCommentCount(comment1);
            //通知
            Notification notification = new Notification();
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
            notification.setOuterId(Math.toIntExact(comment.getParentId()));
            notification.setNotifier(comment.getCommentator());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setReceiver(dbcomment.getCommentator());


            long parentId = comment.getParentId();
            Comment dbComment = commentMapper.selectByPrimaryKey(parentId);
            Long parentId1 = dbComment.getParentId();
            Integer parentId2 = Math.toIntExact(parentId1);
            Question question = questionMapper.selectByPrimaryKey(parentId2);
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUEST_NOT_FOUND);
            }
            notification.setOuterTitle(dbcomment.getContent());
            notification.setNotifierName(commentator.getName());
            notification.setOuterId(question.getId());
            notificationMapper.insertSelective(notification);

        } else {
            //回复问题
            long parentId = comment.getParentId();
            Question question = questionMapper.selectByPrimaryKey((int) parentId);
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUEST_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);

            Notification notification = new Notification();
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
            notification.setOuterId(Math.toIntExact(comment.getParentId()));
            notification.setNotifier(comment.getCommentator());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setReceiver(question.getCreator());
            notification.setNotifierName(commentator.getName());
            notification.setOuterTitle(question.getTitle());
            notification.setOuterId(question.getId());
            notificationMapper.insertSelective(notification);
        }
        User user = (User) session.getAttribute("user");
        Integer unreadCount = notificationService.getUnreadCount(user.getId());
        session.setAttribute("SessionUnreadCount",unreadCount);
    }

    public List<CommentVO> listByQuestionId(Integer id, CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        long idC = id;
        commentExample.createCriteria().andParentIdEqualTo(idC).andTypeEqualTo(commentTypeEnum.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        } else {
            Set<Integer> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
            List<Integer> ids = new ArrayList<>();
            ids.addAll(collect);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdIn(ids);
            List<User> users = userMapper.selectByExample(userExample);
            Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

            List<CommentVO> commentVOS = comments.stream().map(comment -> {
                CommentVO commentVO = new CommentVO();
                BeanUtils.copyProperties(comment, commentVO);
                commentVO.setUser(userMap.get(comment.getCommentator()));
                return commentVO;
            }).collect(Collectors.toList());
            return commentVOS;
        }


    }

    public List<QuestionDTO> listRalated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }


        String[] tags = questionDTO.getTag().split(",");
        String join = StringUtils.join(tags, "|");
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(join);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> collect = questions.stream().map(question1 -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(question1, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return collect;
    }
}
