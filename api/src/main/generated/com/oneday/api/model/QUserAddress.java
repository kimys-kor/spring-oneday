package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserAddress is a Querydsl query type for UserAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAddress extends EntityPathBase<UserAddress> {

    private static final long serialVersionUID = -915082171L;

    public static final QUserAddress userAddress = new QUserAddress("userAddress");

    public final StringPath address = createString("address");

    public final StringPath addressType = createString("addressType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> lat = createNumber("lat", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> lon = createNumber("lon", java.math.BigDecimal.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath zonecode = createString("zonecode");

    public QUserAddress(String variable) {
        super(UserAddress.class, forVariable(variable));
    }

    public QUserAddress(Path<? extends UserAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAddress(PathMetadata metadata) {
        super(UserAddress.class, metadata);
    }

}

