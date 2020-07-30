package com.gyh.community.enums;

/**
 * @author gyh
 * @create 2020-07-30 14:21
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : values()) {
            if(value.getType() == type){
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    CommentTypeEnum(int type) {
        this.type = type;
    }
}
