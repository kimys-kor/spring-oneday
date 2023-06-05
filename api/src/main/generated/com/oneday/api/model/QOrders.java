package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 887306953L;

    public static final QOrders orders = new QOrders("orders");

    public final QBaseTime _super = new QBaseTime(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> shipPrice = createNumber("shipPrice", Integer.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final StringPath zipcode = createString("zipcode");

    public QOrders(String variable) {
        super(Orders.class, forVariable(variable));
    }

    public QOrders(Path<? extends Orders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrders(PathMetadata metadata) {
        super(Orders.class, metadata);
    }

}

