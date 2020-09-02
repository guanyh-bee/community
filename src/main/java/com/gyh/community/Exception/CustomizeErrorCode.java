package com.gyh.community.Exception;

public enum CustomizeErrorCode  implements ICustomizeErrorCode{
    QUEST_NOT_FOUND("你查找的问题不存在，换个问题试试？",2001),
    COMMENT_PARAM_NOT_FOUND("未选择任何问题或评论回复？",2002),
    NOT_LOGIN("没有登录，不能评论",2003),
    SYS_ERR("服务冒烟了，请稍后再试",2004),
    COMMENT_TYPE_WRONG("评论类型错误或不存在",2006),
    COMMENT_NOT_FOUND("回复的评论不存在了，换一个试试",2007),
    COMMENT_CONTENT_EMPTY("回复不能为空",2008),
    NOTIFICATION_NOT_FOUND("通知不存在",2009),
    READ_NOTIFICATION_FAIL("兄弟，看别人的吗",2010);

    private String message;
    private Integer code;
    CustomizeErrorCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
