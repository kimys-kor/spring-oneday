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

    public final com.oneday.api.model.base.QBaseTime _super = new com.oneday.api.model.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Integer> discountPrice = createNumber("discountPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ordersNumber = createString("ordersNumber");

    public final EnumPath<com.oneday.api.model.base.OrderStatus> orderStatus = createEnum("orderStatus", com.oneday.api.model.base.OrderStatus.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> riderId = createNumber("riderId", Long.class);

    public final NumberPath<Integer> shipPrice = createNumber("shipPrice", Integer.class);

    public final NumberPath<Long> shopCouponId = createNumber("shopCouponId", Long.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final NumberPath<Long> userAddresId = createNumber("userAddresId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

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

