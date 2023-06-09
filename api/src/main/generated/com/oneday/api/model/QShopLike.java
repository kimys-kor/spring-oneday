package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopLike is a Querydsl query type for ShopLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopLike extends EntityPathBase<ShopLike> {

    private static final long serialVersionUID = 718602897L;

    public static final QShopLike shopLike = new QShopLike("shopLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QShopLike(String variable) {
        super(ShopLike.class, forVariable(variable));
    }

    public QShopLike(Path<? extends ShopLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopLike(PathMetadata metadata) {
        super(ShopLike.class, metadata);
    }

}

