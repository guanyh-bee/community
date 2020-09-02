package com.gyh.community.enums;

public enum NotificationStatusEnum {
    UNREAD(1),
    READ(2);
    private Integer status;


    NotificationStatusEnum(Integer type) {
        this.status = type;
    }

    public Integer getStatus() {
        return status;
    }


}
