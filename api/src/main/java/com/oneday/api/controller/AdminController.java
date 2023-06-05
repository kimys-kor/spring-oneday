package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.*;
import com.oneday.api.model.dto.*;
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
import java.util.List;
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
    @GetMapping(value = "/member/findAll")
    public Response<Object> findAllMember(Pageable pageable
    ) {
        Page<MemberDto> all = memberService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,all);
    }


    // 상점 추가
    @PostMapping(value = "/register/shop")
    public Response<Object> registerShop(
            ShopRegisterDto shopRegisterDto
            ) {

        shopService.save(shopRegisterDto);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상점 업데이트
    @PostMapping(value = "/shop/update")
    public Response<Object> updateShop(
            Long shopId,
            ShopRegisterDto shopRegisterDto
    ) {
        shopService.update(shopId,shopRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    //  상점 리스트
    @GetMapping(value = "/shop/findAll")
    public Response<Object> findAllShop(Pageable pageable
    ) {
        Page<ShopReadDto> all = shopService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,all);
    }

    // 상점 상세
    @GetMapping(value = "/shop/findOne")
    public Response<Object> findOneShop(Long shopId
    ) {
        Shop byId = shopService.findById(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byId);
    }

    // 상점 삭제
    @GetMapping(value = "/shop/delete")
    public Response<Object> deleteShop(Long shopId
    ) {
        shopService.delete(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상품 추가
    @PostMapping(value = "/product/save")
    public Response<Object> saveProduct(
            ProductRegisterDto productRegisterDto
    ) {
        Product save = productService.save(productRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  save);
    }

    // 상품 업데이트
    @PostMapping(value = "/product/update")
    public Response<Object> updateProduct(
            Long productId,
            ProductRegisterDto productRegisterDto
    ) {
        Product update = productService.update(productId, productRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  update);
    }

    // 상품 상세
    @GetMapping(value = "/product/findOne")
    public Response<Object> findOneProduct(
            Long productId
    ) {
        Product byId = productService.findById(productId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  byId);
    }

    // 상점별 상품 리스트
    @GetMapping(value = "/product/findAll")
    public Response<Object> findAllProduct(
            Long shopId
    ) {
        List<Product> byShopIdEquals = productService.findByShopIdEquals(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  byShopIdEquals);
    }

    // 주문 현황 리스트
    @GetMapping(value = "/orders/findAll")
    public Response<Object> findAllOrders(
            @RequestParam(required = false) String startDt,
            @RequestParam(required = false) String endDt,
            @RequestParam(required = false) OrderStatus orderStatus,
            Pageable pageable
    ) {
        Page<OrdersReadDto> all = ordersService.findAll(startDt,endDt,orderStatus,pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  all);
    }




}
