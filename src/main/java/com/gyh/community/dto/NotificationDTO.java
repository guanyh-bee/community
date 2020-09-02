package com.gyh.community.dto;

import com.gyh.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private User notifier;
    private String outerTitle;
    private Integer status;
    private Long gmtCreate;
    private String typeName;
    private String notifierName;
    private Integer outerId;
    private Integer type;
}
