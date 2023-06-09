package com.oneday.api.repository;

import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.OrdersReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.oneday.api.model.QOrders.orders;


@Repository
public class OrdersCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<OrdersReadDto> findAll(String startDt, String endDt, OrderStatus orderStatus, Pageable pageable) {


        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<OrdersReadDto> results = queryFactory.select(Projections.fields(OrdersReadDto.class,
                        orders.orderStatus,
                        orders.shopId,
                        orders.userId,
                        orders.userAddresId,
                        orders.price,
                        orders.shipPrice
                ))
                .from(orders)
                .where(
                        dateIn(startDt,endDt),
                        statusFilter(orderStatus)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<OrdersReadDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<>(data, pageable, total);
    }

    // 날짜 필터
    private BooleanExpression dateIn(String startDt, String endDt) {
        if (StringUtils.isEmpty(startDt) || StringUtils.isEmpty(endDt)) {
            return null;
        }

        LocalDate startDate = LocalDate.parse(startDt);
        LocalDate endDate = LocalDate.parse(endDt);

        //goe, loe 사용
        BooleanExpression isGoeStartDate = startDt == null ? null: orders.createdDt.goe(LocalDateTime.of(startDate, LocalTime.MIN));
        BooleanExpression isLoeEndDate = endDt == null ? null : orders.createdDt.loe(LocalDateTime.of(endDate, LocalTime.MAX).withNano(0));
        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }

    private BooleanExpression statusFilter(OrderStatus orderStatus) {
        if (StringUtils.isEmpty(orderStatus)) {
            return null;
        }
        return orders.orderStatus.eq(orderStatus);
    }


}
