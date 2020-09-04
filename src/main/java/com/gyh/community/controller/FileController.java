package com.gyh.community.controller;

import com.gyh.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gyh
 * @create 2020-09-04 13:41
 */
@Controller
public class FileController {
    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/img/kasa.jpg");
        return fileDTO;
    }
}
