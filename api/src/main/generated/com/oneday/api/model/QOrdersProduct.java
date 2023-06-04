package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrdersProduct is a Querydsl query type for OrdersProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersProduct extends EntityPathBase<OrdersProduct> {

    private static final long serialVersionUID = -92658202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrdersProduct ordersProduct = new QOrdersProduct("ordersProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrders orders;

    public final QProduct product;

    public QOrdersProduct(String variable) {
        this(OrdersProduct.class, forVariable(variable), INITS);
    }

    public QOrdersProduct(Path<? extends OrdersProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrdersProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrdersProduct(PathMetadata metadata, PathInits inits) {
        this(OrdersProduct.class, metadata, inits);
    }

    public QOrdersProduct(Class<? extends OrdersProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

