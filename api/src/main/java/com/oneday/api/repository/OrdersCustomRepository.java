package com.oneday.api.repository;

import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.OrdersReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.oneday.api.model.QOrders.orders;
import static com.oneday.api.model.QUserAddress.userAddress;
import static com.oneday.api.model.QUser.user;


@Repository
public class OrdersCustomRepository {

    @PersistenceContext
    private EntityManager em;

    //데이터 포맷 변경


    public Page<OrdersReadDto> findAllByUserId(Long userId, String startDt, String endDt, OrderStatus orderStatus, Pageable pageable) {


        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , orders.createdDt
                , ConstantImpl.create("%Y.%m.%d %H:%i:%s")).as("createdDt");


        QueryResults<OrdersReadDto> results = queryFactory.select(Projections.fields(OrdersReadDto.class,
                        orders.id,
                        orders.orderStatus,
                        orders.ordersNumber,
                        formattedDate,
                        orders.shopId,
                        user.email,
                        userAddress.address,
                        userAddress.zonecode,
                        orders.price,
                        orders.shipPrice
                ))
                .from(orders)
                .leftJoin(userAddress).on(userAddress.id.eq(orders.userAddresId))
                .leftJoin(user).on(user.id.eq(orders.userId))
                .where(
                        userIdFilter(userId),
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

    public Page<OrdersReadDto> findAllByShopId(Long shopId, String startDt, String endDt, OrderStatus orderStatus, Pageable pageable) {


        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , orders.createdDt
                , ConstantImpl.create("%Y.%m.%d %H:%i:%s")).as("createdDt");


        QueryResults<OrdersReadDto> results = queryFactory.select(Projections.fields(OrdersReadDto.class,
                        orders.id,
                        orders.orderStatus,
                        orders.ordersNumber,
                        formattedDate,
                        orders.shopId,
                        user.email,
                        userAddress.address,
                        userAddress.zonecode,
                        orders.price,
                        orders.shipPrice
                ))
                .from(orders)
                .leftJoin(userAddress).on(userAddress.id.eq(orders.userAddresId))
                .leftJoin(user).on(user.id.eq(orders.userId))
                .where(
                        orders.shopId.eq(shopId),
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


    // 유저 아이디 필터
    private BooleanExpression userIdFilter(Long userId) {
        if(userId == null) return null;
        return orders.userId.eq(userId);
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
        if (orderStatus.equals(OrderStatus.All) ) {
            return null;
        }
        return orders.orderStatus.eq(orderStatus);
    }


}
