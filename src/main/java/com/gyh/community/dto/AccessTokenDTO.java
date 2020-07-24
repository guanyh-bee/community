package com.gyh.community.dto;

import lombok.Data;

/**
 * @author gyh
 * @create 2020-07-06 15:24
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
