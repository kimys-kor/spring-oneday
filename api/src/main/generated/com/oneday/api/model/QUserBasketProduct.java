package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBasketProduct is a Querydsl query type for UserBasketProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBasketProduct extends EntityPathBase<UserBasketProduct> {

    private static final long serialVersionUID = 1829328858L;

    public static final QUserBasketProduct userBasketProduct = new QUserBasketProduct("userBasketProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserBasketProduct(String variable) {
        super(UserBasketProduct.class, forVariable(variable));
    }

    public QUserBasketProduct(Path<? extends UserBasketProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBasketProduct(PathMetadata metadata) {
        super(UserBasketProduct.class, metadata);
    }

}

