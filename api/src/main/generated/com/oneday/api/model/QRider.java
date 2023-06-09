package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRider is a Querydsl query type for Rider
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRider extends EntityPathBase<Rider> {

    private static final long serialVersionUID = 862409238L;

    public static final QRider rider = new QRider("rider");

    public final com.oneday.api.model.base.QBaseTime _super = new com.oneday.api.model.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Double> point = createNumber("point", Double.class);

    public final StringPath riderName = createString("riderName");

    public final EnumPath<com.oneday.api.model.base.UserStatus> status = createEnum("status", com.oneday.api.model.base.UserStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QRider(String variable) {
        super(Rider.class, forVariable(variable));
    }

    public QRider(Path<? extends Rider> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRider(PathMetadata metadata) {
        super(Rider.class, metadata);
    }

}

