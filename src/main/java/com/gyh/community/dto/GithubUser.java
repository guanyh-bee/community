package com.gyh.community.dto;

import lombok.Data;

/**
 * @author gyh
 * @create 2020-07-06 16:26
 */
@Data
public class GithubUser {
    private Integer id;
    private String name;
    private String bio;
    private String avatar_url;

}
