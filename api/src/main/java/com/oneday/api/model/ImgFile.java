package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ImgFile")
public class ImgFile {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(300)")
    private String origFileName;
    @Column(nullable = false, columnDefinition = "varchar(300)")
    private String fileName;
    @Column(nullable = false, columnDefinition = "varchar(300)")
    private String filePath;
}

