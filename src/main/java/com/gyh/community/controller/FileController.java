package com.gyh.community.controller;

import com.gyh.community.dto.FileDTO;
import com.gyh.community.provider.UploadProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author gyh
 * @create 2020-09-04 13:41t
 */
@Controller
public class FileController {
    @Autowired
    private UploadProvider uploadProvider;

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String url = uploadProvider.file(file.getInputStream(), file.getBytes().length, file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
