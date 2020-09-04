package com.gyh.community.service;

import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.dto.NotificationDTO;
import com.gyh.community.dto.PaginationDTO;
import com.gyh.community.enums.NotificationStatusEnum;
import com.gyh.community.enums.NotificationTypeEnum;
import com.gyh.community.mapper.NotificationMapper;
import com.gyh.community.mapper.UserMapper;
import com.gyh.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
    @Autowired(required = false)
    UserMapper userMapper;

    public PaginationDTO getList(Integer id, Integer page, Integer size) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        int totalCount = (int) notificationMapper.countByExample(notificationExample);
        //Integer totalCount = questionMapper.countByUserId(userId);
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);
        Integer offSet = (page-1)*size;
        notificationExample.clear();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        RowBounds rowBounds = new RowBounds(offSet,size);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, rowBounds);

        if(notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.getTypeByCode(notification.getType()));
            notificationDTOS.add(notificationDTO);

        }

//        Set<Integer> distinctUserIds = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
//        List<Integer> ids = new ArrayList<>(distinctUserIds);
//        List<NotificationDTO> notificationDTOS = new ArrayList<>();
//        UserExample example = new UserExample();
//        example.createCriteria().andIdIn(ids);
//
//        List<User> users = userMapper.selectByExample(example);
//        Map<Integer, User> collect = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Integer getUnreadCount(Integer id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long l = notificationMapper.countByExample(example);
        return (int) l;
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(user.getId(),notification.getReceiver())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.getTypeByCode(notification.getType()));
        return notificationDTO;
    }
}
