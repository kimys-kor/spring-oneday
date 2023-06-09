package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdersProductOption is a Querydsl query type for OrdersProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersProductOption extends EntityPathBase<OrdersProductOption> {

    private static final long serialVersionUID = 294852091L;

    public static final QOrdersProductOption ordersProductOption = new QOrdersProductOption("ordersProductOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> ordersProductId = createNumber("ordersProductId", Long.class);

    public final NumberPath<Long> productOptionId = createNumber("productOptionId", Long.class);

    public QOrdersProductOption(String variable) {
        super(OrdersProductOption.class, forVariable(variable));
    }

    public QOrdersProductOption(Path<? extends OrdersProductOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdersProductOption(PathMetadata metadata) {
        super(OrdersProductOption.class, metadata);
    }

}

