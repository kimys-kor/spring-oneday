package com.oneday.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductOption is a Querydsl query type for ProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOption extends EntityPathBase<ProductOption> {

    private static final long serialVersionUID = -839003680L;

    public static final QProductOption productOption = new QProductOption("productOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QProductOption(String variable) {
        super(ProductOption.class, forVariable(variable));
    }

    public QProductOption(Path<? extends ProductOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOption(PathMetadata metadata) {
        super(ProductOption.class, metadata);
    }

}

