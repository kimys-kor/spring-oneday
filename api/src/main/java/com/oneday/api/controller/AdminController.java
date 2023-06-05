package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.ImgFile;
import com.oneday.api.model.Member;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${key.imgPath}")
    private String imgPath;

    @Value("${key.imgUrl}")
    private String imgUrl;

    @Autowired
    ImgFileService imgFileService;

    @Autowired
    MemberService memberService;

    @Autowired
    ShopService shopService;

    @Autowired
    ProductService productService;

    @Autowired
    RiderService riderService;

    @Autowired
    OrdersService ordersService;


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

    // 멤버 상세
    @GetMapping(value = "/findOne")
    public Response<Object> findOne(
            @RequestParam Long memberId
    ) {
        Member byId = memberService.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byId);
    }

    // 멤버 리스트
    @GetMapping(value = "/findAll")
    public Response<Object> findAll(Pageable pageable
    ) {
        Page<MemberDto> all = memberService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,all);
    }





}
