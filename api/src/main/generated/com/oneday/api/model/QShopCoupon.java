package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopCoupon is a Querydsl query type for ShopCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopCoupon extends EntityPathBase<ShopCoupon> {

    private static final long serialVersionUID = -1164159840L;

    public static final QShopCoupon shopCoupon = new QShopCoupon("shopCoupon");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> endDt = createDateTime("endDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRatio = createBoolean("isRatio");

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startDt = createDateTime("startDt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QShopCoupon(String variable) {
        super(ShopCoupon.class, forVariable(variable));
    }

    public QShopCoupon(Path<? extends ShopCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopCoupon(PathMetadata metadata) {
        super(ShopCoupon.class, metadata);
    }

}

