package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdersProduct is a Querydsl query type for OrdersProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersProduct extends EntityPathBase<OrdersProduct> {

    private static final long serialVersionUID = -92658202L;

    public static final QOrdersProduct ordersProduct = new QOrdersProduct("ordersProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrdersProduct(String variable) {
        super(OrdersProduct.class, forVariable(variable));
    }

    public QOrdersProduct(Path<? extends OrdersProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdersProduct(PathMetadata metadata) {
        super(OrdersProduct.class, metadata);
    }

}

