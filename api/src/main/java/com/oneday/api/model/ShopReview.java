package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShopReview extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long shopId;
    private Long ordersId;

    // true: 텍스트리뷰, false:사진리뷰
    private boolean isText;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String Content;

    @ColumnDefault("0")
    private int score;

    private String profile1;
    private String profile2;
    private String profile3;

    public ShopReview(Long userId, Long shopId, Long ordersId,boolean isText, String content, int score, String profile1, String profile2, String profile3) {
        this.userId = userId;
        this.shopId = shopId;
        this.ordersId = ordersId;
        this.isText = isText;
        this.Content = content;
        this.score = score;
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.profile3 = profile3;
    }
}
