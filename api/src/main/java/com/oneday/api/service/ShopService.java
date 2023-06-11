package com.oneday.api.service;

import com.oneday.api.model.ImgFile;
import com.oneday.api.model.Product;
import com.oneday.api.model.Shop;
import com.oneday.api.model.ShopLike;
import com.oneday.api.model.dto.ShopReadDto;
import com.oneday.api.model.dto.ShopRegisterDto;
import com.oneday.api.repository.ShopCustomRepository;
import com.oneday.api.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    @Value("${key.imgPath}")
    private String imgPath;
    @Value("${key.imgUrl}")
    private String imgUrl;
    private final ShopRepository shopRepository;
    private final ShopCustomRepository shopCustomRepository;
    private final ImgFileService imgFileService;
    private final ShopReviewService shopReviewService;
    private final ShopLikeService shopLikeService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;


    public Shop save(Long userId,ShopRegisterDto dto) {
        List<String> filePathList = saveImg(dto);
        Shop shop = new Shop(userId, dto.getName(), dto.getOwnerName(), dto.getBusinessNumber(), dto.getContactNumber(),
                dto.getShopAddress(), dto.getTime(), dto.getShopDescription() ,dto.getLat(), dto.getLon(),
                filePathList.get(0),filePathList.get(1),filePathList.get(2));
        return shopRepository.save(shop);
    }

    public Shop updatePluReview(Long shopId) {
        Shop byId = findById(shopId);
        byId.setReviewNum(byId.getReviewNum()+1);
        return shopRepository.save(byId);
    }

    public Shop updateMinusReview(Long shopId) {
        Shop byId = findById(shopId);
        byId.setReviewNum(byId.getReviewNum()-1);
        return shopRepository.save(byId);
    }

    public Shop update(Long shopId,ShopRegisterDto dto) {
        List<String> filePathList = saveImg(dto);
        Shop byId = findById(shopId);

        byId.setName(dto.getName());
        byId.setOwnerName(dto.getOwnerName());
        byId.setBusinessNumber(dto.getBusinessNumber());
        byId.setContactNumber(dto.getContactNumber());
        byId.setShopAddress(dto.getShopAddress());
        byId.setTime(dto.getTime());
        byId.setLat(dto.getLat());
        byId.setLon(dto.getLon());
        byId.setProfile1(filePathList.get(0));
        byId.setProfile1(filePathList.get(1));
        byId.setProfile1(filePathList.get(2));
        return shopRepository.save(byId);
    }

    // 상점 리스트
    public Page<ShopReadDto> findShopList(Long userId, BigDecimal lat, BigDecimal lon, String orderCondition,
                                          Integer distance, String keyword, Pageable pageable) {

        Page<ShopReadDto> shopList = shopCustomRepository.findShopList(lat,lon, orderCondition, distance, keyword, pageable);

        // 가게별 tag들 추가
        for (ShopReadDto shop : shopList.getContent()) {
            Float aFloat = shopReviewService.countAvg(shop.getId());
            if(aFloat == null) aFloat = 0.0F;

            shop.setAvgStar(aFloat);
            shop.setPeoples(cntConversion(shop.getPp()));
            shop.setReviewNumber(cntConversion(Long.valueOf(shop.getRn())));

            ShopLike shopLike = null;
            if(userId != null) shopLike = shopLikeService.findByShopIdAndUserIdEquals(shop.getId(), userId);
            if (shopLike == null) {
                shop.setLike(false);
            } else {
                shop.setLike(true);
            }
        }
        return shopList;
    }


    public Shop findById(Long shopId) {
        return shopRepository.findById(shopId).orElse(null);
    }

    // 상점 삭제
    public void delete(Shop shop) {
        shopRepository.delete(shop);
        // 상점의 상품도 삭제
        List<Product> productList = productService.findAllByShopIdEquals(shop.getId());
        productService.deleteAll(productList);
        List<Long> productIdList = productList.stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        // 상품의 옵션도 삭제
        productOptionService.deleteAll(productIdList);
    }

    // k 변환
    public String cntConversion(Long cnt) {
        String k = "";
        // 추천 수
        if (cnt >= 1000) {
            Long i = cnt / 1000;
            String s = String.valueOf(cnt % 1000).substring(0, 1);
            if (s.equals("0")) {
                k = i + "k";
            } else {
                k = i + "." + s + "k";
            }
        } else {
            k = String.valueOf(cnt);
        }

        return k;
    }


    private List<String> saveImg(ShopRegisterDto dto) {
        List<MultipartFile> files = new ArrayList<>();
        List<String> filePathList = new ArrayList<>();

        System.out.println(dto.getProfileImg1()+" 이미지1");
        System.out.println(dto.getProfileImg2()+" 이미지2");
        System.out.println(dto.getProfileImg3()+" 이미지3");

        if(dto.getProfileImg1() != null) files.add(dto.getProfileImg1());
        if(dto.getProfileImg2() != null) files.add(dto.getProfileImg2());
        if(dto.getProfileImg3() != null) files.add(dto.getProfileImg3());

        System.out.println(filePathList.size()+" 사이즈");



        for (MultipartFile file : files) {
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
                filePath = imgFile.getFileName();
                filePathList.add(imgUrl+filePath);
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        int maxSize = 3;
        int size = filePathList.size();
        int loop = maxSize-size;
        if (size < maxSize) {
            for (int i = 0; i < loop; i++) {
                filePathList.add("");
            }
        }

        return filePathList;
    }


}
