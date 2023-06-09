package com.oneday.api.service;

import com.oneday.api.model.ImgFile;
import com.oneday.api.model.ShopReview;
import com.oneday.api.model.dto.ShopReviewRegisterDto;
import com.oneday.api.repository.ShopReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopReviewService {

    private final ShopReviewRepository shopReviewRepository;

    private final ImgFileService imgFileService;

    @Value("${key.imgPath}")
    private String imgPath;

    @Value("${key.imgUrl}")
    private String imgUrl;

    public ShopReview save(Long userId, ShopReviewRegisterDto dto) {
        List<String> filePathList = saveImg(dto);

        // text리뷰인지 체크
        boolean isText = true;
        if(dto.getProfileImg1() != null || dto.getProfileImg2() != null  || dto.getProfileImg3() != null ) isText=false;

        ShopReview shopReview = new ShopReview(userId, dto.getShopId(), dto.getOrdersId(),isText,
                dto.getContent(), dto.getScore(), filePathList.get(0), filePathList.get(1), filePathList.get(2));
        ShopReview save = shopReviewRepository.save(shopReview);
        return save;
    }

    // 평점 계산
    public Float countAvg(Long shopId) {
        return shopReviewRepository.AvgReviewScoreByShopIdEquals(shopId);
    }

    // 전체 찾기
    public List<ShopReview> findAllByShopIdEquals(Long shopId) {
        return shopReviewRepository.findAllByShopIdEquals(shopId);
    }

    // 아이디로 찾기
    public ShopReview findById(Long shopReviewId) {
        return shopReviewRepository.findById(shopReviewId).orElse(null);
    }

    // 댓글 삭제
    public void deleteOne(Long shopReviewId) {
        shopReviewRepository.deleteOne(shopReviewId);
    }


    private List<String> saveImg(ShopReviewRegisterDto dto) {
        List<MultipartFile> files = new ArrayList<>();
        List<String> filePathList = new ArrayList<>();
        if(dto.getProfileImg1() != null) files.add(dto.getProfileImg1());
        if(dto.getProfileImg2() != null) files.add(dto.getProfileImg2());
        if(dto.getProfileImg3() != null) files.add(dto.getProfileImg3());


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
