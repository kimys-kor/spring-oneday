package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShopAddress is a Querydsl query type for ShopAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShopAddress extends EntityPathBase<ShopAddress> {

    private static final long serialVersionUID = 460172986L;

    public static final QShopAddress shopAddress = new QShopAddress("shopAddress");

    public final StringPath address = createString("address");

    public final StringPath addressType = createString("addressType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> lat = createNumber("lat", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> lon = createNumber("lon", java.math.BigDecimal.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final StringPath zonecode = createString("zonecode");

    public QShopAddress(String variable) {
        super(ShopAddress.class, forVariable(variable));
    }

    public QShopAddress(Path<? extends ShopAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopAddress(PathMetadata metadata) {
        super(ShopAddress.class, metadata);
    }

}

