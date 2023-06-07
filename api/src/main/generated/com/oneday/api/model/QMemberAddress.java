package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberAddress is a Querydsl query type for MemberAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAddress extends EntityPathBase<MemberAddress> {

    private static final long serialVersionUID = -146036842L;

    public static final QMemberAddress memberAddress = new QMemberAddress("memberAddress");

    public final StringPath address = createString("address");

    public final StringPath addressType = createString("addressType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jibunAddress = createString("jibunAddress");

    public final NumberPath<java.math.BigDecimal> lat = createNumber("lat", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> lon = createNumber("lon", java.math.BigDecimal.class);

    public final StringPath roadAddress = createString("roadAddress");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath zonecode = createString("zonecode");

    public QMemberAddress(String variable) {
        super(MemberAddress.class, forVariable(variable));
    }

    public QMemberAddress(Path<? extends MemberAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberAddress(PathMetadata metadata) {
        super(MemberAddress.class, metadata);
    }

}

