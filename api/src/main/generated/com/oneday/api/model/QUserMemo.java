package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMemo is a Querydsl query type for UserMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMemo extends EntityPathBase<UserMemo> {

    private static final long serialVersionUID = 797443721L;

    public static final QUserMemo userMemo = new QUserMemo("userMemo");

    public final com.oneday.api.model.base.QBaseEntity _super = new com.oneday.api.model.base.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastmodifiedBy = _super.lastmodifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserMemo(String variable) {
        super(UserMemo.class, forVariable(variable));
    }

    public QUserMemo(Path<? extends UserMemo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMemo(PathMetadata metadata) {
        super(UserMemo.class, metadata);
    }

}

