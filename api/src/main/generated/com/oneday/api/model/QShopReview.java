package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopReview is a Querydsl query type for ShopReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopReview extends EntityPathBase<ShopReview> {

    private static final long serialVersionUID = -743935022L;

    public static final QShopReview shopReview = new QShopReview("shopReview");

    public final StringPath Content = createString("Content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isText = createBoolean("isText");

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final StringPath profile1 = createString("profile1");

    public final StringPath profile2 = createString("profile2");

    public final StringPath profile3 = createString("profile3");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QShopReview(String variable) {
        super(ShopReview.class, forVariable(variable));
    }

    public QShopReview(Path<? extends ShopReview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopReview(PathMetadata metadata) {
        super(ShopReview.class, metadata);
    }

}

