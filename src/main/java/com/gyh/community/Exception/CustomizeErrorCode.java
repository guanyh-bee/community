package com.gyh.community.Exception;

public enum CustomizeErrorCode  implements ICustomizeErrorCode{
    QUEST_NOT_FOUND("你查找的问题不存在，换个问题试试？");
    private String message;
    CustomizeErrorCode(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
