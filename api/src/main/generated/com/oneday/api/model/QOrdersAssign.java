package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdersAssign is a Querydsl query type for OrdersAssign
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersAssign extends EntityPathBase<OrdersAssign> {

    private static final long serialVersionUID = -431379176L;

    public static final QOrdersAssign ordersAssign = new QOrdersAssign("ordersAssign");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final NumberPath<Long> riderId = createNumber("riderId", Long.class);

    public final StringPath riderName = createString("riderName");

    public QOrdersAssign(String variable) {
        super(OrdersAssign.class, forVariable(variable));
    }

    public QOrdersAssign(Path<? extends OrdersAssign> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdersAssign(PathMetadata metadata) {
        super(OrdersAssign.class, metadata);
    }

}

