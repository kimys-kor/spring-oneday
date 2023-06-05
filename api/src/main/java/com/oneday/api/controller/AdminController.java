package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.ImgFile;
import com.oneday.api.service.ImgFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ImgFileService imgFileService;

    @Value("${key.imgPath}")
    private String imgPath;

    @Value("${key.imgUrl}")
    private String imgUrl;

    @GetMapping(value = "/test")
    public Response<Object> findOne() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,"hihi");
    }

    @PostMapping(value = "/upload")
    public Response<Object> upload(
            @RequestParam MultipartFile file
    ) {

        LocalDateTime dateTimeNow = LocalDateTime.now();
        ImgFile imgFile = new ImgFile();
        String fileNameDateTime = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        try {
            String origFileName = file.getOriginalFilename();
            String fileName = fileNameDateTime + origFileName;
            String savePath = imgPath;

            String filePath = savePath + "/" + fileName;
            file.transferTo(new File(filePath));

            imgFile.setOrigFileName(origFileName);
            imgFile.setFilePath(filePath);
            imgFile.setFileName(fileName);
            imgFile = imgFileService.save(imgFile);

        } catch (Exception e) {
            System.out.println(e);
        }



        return new Response(ResultCode.DATA_NORMAL_PROCESSING,"upload Success");
    }
}
