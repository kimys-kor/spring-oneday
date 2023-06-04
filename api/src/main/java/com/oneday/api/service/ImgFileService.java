package com.oneday.api.service;

import com.oneday.api.model.ImgFile;
import com.oneday.api.repository.ImgFileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImgFileService {
    @Autowired
    ImgFileRepository imgFileRepository;

    public ImgFile save(ImgFile imgFile) {
        return imgFileRepository.save(imgFile);
    }

    public ImgFile findByIdEquals(int id) {
        return imgFileRepository.findByIdEquals(id);
    }
}
