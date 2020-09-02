package com.gyh.community.cache;

import com.gyh.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gyh
 * @create 2020-09-02 14:51
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setCategory("开发语言");
        tagDTO.setTags(Arrays.asList("java","python","c","c++","php","js","css"));
        tagDTOS.add(tagDTO);

        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setCategory("平台架构");
        tagDTO1.setTags(Arrays.asList("spring","express","struts","koa","yii","ruby-on-rails","flask"));
        tagDTOS.add(tagDTO1);
        return tagDTOS;
    }

    public static String checkTag(String tags){
        List<TagDTO> tagDTOS = get();
        List<String> collect = tagDTOS.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
        String[] strings = tags.split(",");
        String collect1 = Arrays.stream(strings).filter(s -> !collect.contains(s)).collect(Collectors.joining(","));
        return collect1;

    }
}
