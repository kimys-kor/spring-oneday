package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = -791151624L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> createdDt = createDateTime("createdDt", java.time.LocalDateTime.class);

    public final StringPath lastmodifiedBy = createString("lastmodifiedBy");

    public final DateTimePath<java.time.LocalDateTime> updatedDt = createDateTime("updatedDt", java.time.LocalDateTime.class);

    public QBaseEntity(String variable) {
        super(BaseEntity.class, forVariable(variable));
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntity(PathMetadata metadata) {
        super(BaseEntity.class, metadata);
    }

}

