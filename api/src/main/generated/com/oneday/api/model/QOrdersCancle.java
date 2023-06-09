package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdersCancle is a Querydsl query type for OrdersCancle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersCancle extends EntityPathBase<OrdersCancle> {

    private static final long serialVersionUID = -390898827L;

    public static final QOrdersCancle ordersCancle = new QOrdersCancle("ordersCancle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOrdersCancle(String variable) {
        super(OrdersCancle.class, forVariable(variable));
    }

    public QOrdersCancle(Path<? extends OrdersCancle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdersCancle(PathMetadata metadata) {
        super(OrdersCancle.class, metadata);
    }

}

