package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShop is a Querydsl query type for Shop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShop extends EntityPathBase<Shop> {

    private static final long serialVersionUID = -1219077158L;

    public static final QShop shop = new QShop("shop");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Orders, QOrders> orders = this.<Orders, QOrders>createList("orders", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final StringPath ownerName = createString("ownerName");

    public final StringPath phoneNum = createString("phoneNum");

    public final ListPath<Product, QProduct> product = this.<Product, QProduct>createList("product", Product.class, QProduct.class, PathInits.DIRECT2);

    public QShop(String variable) {
        super(Shop.class, forVariable(variable));
    }

    public QShop(Path<? extends Shop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShop(PathMetadata metadata) {
        super(Shop.class, metadata);
    }

}
