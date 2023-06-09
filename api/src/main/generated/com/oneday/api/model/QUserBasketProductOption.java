package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBasketProductOption is a Querydsl query type for UserBasketProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBasketProductOption extends EntityPathBase<UserBasketProductOption> {

    private static final long serialVersionUID = -1716911889L;

    public static final QUserBasketProductOption userBasketProductOption = new QUserBasketProductOption("userBasketProductOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productOptionId = createNumber("productOptionId", Long.class);

    public final NumberPath<Long> userBasketProductId = createNumber("userBasketProductId", Long.class);

    public QUserBasketProductOption(String variable) {
        super(UserBasketProductOption.class, forVariable(variable));
    }

    public QUserBasketProductOption(Path<? extends UserBasketProductOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBasketProductOption(PathMetadata metadata) {
        super(UserBasketProductOption.class, metadata);
    }

}

