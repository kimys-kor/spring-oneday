package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserShopCoupon is a Querydsl query type for UserShopCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserShopCoupon extends EntityPathBase<UserShopCoupon> {

    private static final long serialVersionUID = 301435083L;

    public static final QUserShopCoupon userShopCoupon = new QUserShopCoupon("userShopCoupon");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isUsed = createBoolean("isUsed");

    public final NumberPath<Long> shopCouponId = createNumber("shopCouponId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserShopCoupon(String variable) {
        super(UserShopCoupon.class, forVariable(variable));
    }

    public QUserShopCoupon(Path<? extends UserShopCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserShopCoupon(PathMetadata metadata) {
        super(UserShopCoupon.class, metadata);
    }

}

