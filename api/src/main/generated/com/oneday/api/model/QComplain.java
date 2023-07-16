package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComplain is a Querydsl query type for Complain
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComplain extends EntityPathBase<Complain> {

    private static final long serialVersionUID = 464687053L;

    public static final QComplain complain = new QComplain("complain");

    public final com.oneday.api.model.base.QBaseTime _super = new com.oneday.api.model.base.QBaseTime(this);

    public final EnumPath<com.oneday.api.model.base.ComplainCategory> complainCategory = createEnum("complainCategory", com.oneday.api.model.base.ComplainCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QComplain(String variable) {
        super(Complain.class, forVariable(variable));
    }

    public QComplain(Path<? extends Complain> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComplain(PathMetadata metadata) {
        super(Complain.class, metadata);
    }

}

