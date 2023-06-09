package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShop is a Querydsl query type for Shop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShop extends EntityPathBase<Shop> {

    private static final long serialVersionUID = -1219077158L;

    public static final QShop shop = new QShop("shop");

    public final com.oneday.api.model.base.QBaseTime _super = new com.oneday.api.model.base.QBaseTime(this);

    public final StringPath businessNumber = createString("businessNumber");

    public final StringPath contactNumber = createString("contactNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> lat = createNumber("lat", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> lon = createNumber("lon", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath profile1 = createString("profile1");

    public final StringPath profile2 = createString("profile2");

    public final StringPath profile3 = createString("profile3");

    public final NumberPath<Integer> reviewNum = createNumber("reviewNum", Integer.class);

    public final StringPath shopAddress = createString("shopAddress");

    public final StringPath shopDescription = createString("shopDescription");

    public final StringPath time = createString("time");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QShop(String variable) {
        super(Shop.class, forVariable(variable));
    }

    public QShop(Path<? extends Shop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShop(PathMetadata metadata) {
        super(Shop.class, metadata);
    }

}

