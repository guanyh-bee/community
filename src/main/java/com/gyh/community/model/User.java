package com.gyh.community.model;

import lombok.Data;

/**
 * @author gyh
 * @create 2020-07-15 14:31
 */
@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatar;


}
