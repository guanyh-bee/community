package com.gyh.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author gyh
 * @create 2020-09-02 14:49
 */
@Data
public class TagDTO {
    private String category;
    private List<String> tags;
}
