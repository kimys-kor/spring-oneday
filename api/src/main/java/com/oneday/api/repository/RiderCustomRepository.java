package com.oneday.api.repository;

import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.RiderDetailDto;
import com.oneday.api.model.dto.RiderReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.oneday.api.model.QRider.rider;
import static com.oneday.api.model.QOrders.orders;
import static com.oneday.api.model.QShop.shop;

@Repository
public class RiderCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public RiderDetailDto findOne(Long riderId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                JPAExpressions.select(orders.updatedDt.max())
                        .from(orders)
                        .where(orders.riderId.eq(rider.id), orders.orderStatus.eq(OrderStatus.COMPLETE)),
                ConstantImpl.create("%Y.%m.%d %H:%i:%s")
        ).as("lastComplete");


        RiderDetailDto riderDetailDto = queryFactory.select(Projections.fields(RiderDetailDto.class,
                        rider.id,
                        rider.phone,
                        rider.riderName,
                        rider.email,
                        rider.point,
                        rider.status,
                        rider.createdDt,
                        rider.updatedDt,
                        ExpressionUtils.as(JPAExpressions.select(orders.count())
                                .from(orders)
                                .where(orders.riderId.eq(rider.id)),"complete"),
                        ExpressionUtils.as(JPAExpressions.select(orders.price.sum())
                                .from(orders)
                                .where(orders.riderId.eq(rider.id)),"completePrice"),
                        formattedDate
                ))
                .from(rider)
                .where(rider.id.eq(riderId))
                .fetchOne();


        return riderDetailDto;

    }

    public Page<RiderReadDto> findAll(Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<RiderReadDto> results = queryFactory.select(Projections.fields(RiderReadDto.class,
                        rider.id,
                        rider.phone,
                        rider.riderName,
                        rider.email,
                        rider.point,
                        rider.status,
                        rider.createdDt,
                        rider.updatedDt
                ))
                .from(rider)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<RiderReadDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<RiderReadDto>(data, pageable, total);

    }
}
