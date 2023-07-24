package com.oneday.api.repository;


import com.oneday.api.model.QShop;
import com.oneday.api.model.dto.ShopReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.oneday.api.model.QShop.shop;
import static com.oneday.api.model.QOrders.orders;

@Repository
public class ShopCustomRepository {

    @PersistenceContext
    private EntityManager em;

    // 관리자페이지 상점관리 상점리스트
    public Page<ShopReadDto> findShopListForAdmin(String orderCondition, String address, String keyword, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        // 어제 날짜00시
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();

        // 오늘 날짜00시
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();

        // 서브쿼리를 order by 사용하기 위해 path등록
        StringPath aliasSales = Expressions.stringPath("sales");

        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , shop.createdDt
                , ConstantImpl.create("%Y.%m.%d %H:%i:%s")).as("createdDt");

        QueryResults<ShopReadDto> result = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.name,
                        shop.ownerName,
                        shop.reviewNum,
                        shop.businessNumber,
                        shop.contactNumber,
                        formattedDate,
                        shop.shopAddress,
                        shop.shopDescription,
                        shop.time,
                        shop.profile1,
                        shop.profile2,
                        shop.profile3,
                        shop.lat,
                        shop.lon,
                        ExpressionUtils.as(
                                JPAExpressions.select(orders.price.sum())
                                        .from(orders)
                                        .where(
                                                orders.shopId.eq(shop.id),
                                                orders.createdDt.between(yesterdayStart,todayStart)
                                        ),
                                "sales")
                ))
                .from(shop)
                .where(
                        addressFilter(address),
                        keywordFilter(keyword)
                )
                .orderBy(
                        shop.createdDt.desc(),
                        orderFilter2(orderCondition,aliasSales)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ShopReadDto> data = result.getResults();


        long total = result.getTotal();
        return new PageImpl<>(data, pageable, total);
    }

    // 관리자 페이지 상점 상세
    public ShopReadDto findById(Long shopId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        // 어제 날짜00시
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();

        // 오늘 날짜00시
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();


        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , shop.createdDt
                , ConstantImpl.create("%Y.%m.%d %H:%i:%s")).as("createdDt");

        ShopReadDto result = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.name,
                        shop.ownerName,
                        shop.reviewNum,
                        shop.businessNumber,
                        shop.contactNumber,
                        formattedDate,
                        shop.shopAddress,
                        shop.shopDescription,
                        shop.time,
                        shop.profile1,
                        shop.profile2,
                        shop.profile3,
                        shop.lat,
                        shop.lon,
                        ExpressionUtils.as(
                                JPAExpressions.select(orders.price.sum())
                                        .from(orders)
                                        .where(
                                                orders.shopId.eq(shop.id),
                                                orders.createdDt.between(yesterdayStart,todayStart)
                                        ),
                                "sales")
                ))
                .from(shop)
                .where(
                        shop.id.eq(shopId)
                )
                .fetchOne();

        return result;
    }

    
    // 유저 페이지 가까운 거리순 상점 리스트
    public Page<ShopReadDto> findShopList(BigDecimal lat, BigDecimal lon, String orderCondition,Integer distance,
                                          String keyword, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 현재좌표, 가게좌표 비교 거리 계산
        NumberExpression<BigDecimal> distanceExpression = Expressions.numberTemplate(BigDecimal.class,
                "6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
                lat, lon, shop.lat, shop.lon);

        // 어제 날짜00시
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();

        // 오늘 날짜00시
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();

        // 서브쿼리를 order by 사용하기 위해 path등록
        StringPath aliasSales = Expressions.stringPath("sales");

        StringExpression formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , shop.createdDt
                , ConstantImpl.create("%Y.%m.%d %H:%i:%s")).as("createdDt");

        QueryResults<ShopReadDto> result = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.name,
                        shop.ownerName,
                        shop.reviewNum,
                        shop.businessNumber,
                        shop.contactNumber,
                        shop.createdDt,
                        formattedDate,
                        shop.shopDescription,
                        shop.time,
                        shop.profile1,
                        shop.profile2,
                        shop.profile3,
                        shop.lat,
                        shop.lon,
                        distanceExpression.as("distance"),
                        ExpressionUtils.as(
                                JPAExpressions.select(orders.price.sum())
                                        .from(orders)
                                        .where(
                                                orders.shopId.eq(shop.id),
                                                orders.createdDt.between(yesterdayStart,todayStart)
                                        ),
                                "sales")
                ))
                .from(shop)
                .where(
                        keywordFilter(keyword),
                        distanceIn(distance, distanceExpression)
                )
                .orderBy(
                        orderFilter(orderCondition,distanceExpression,aliasSales)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ShopReadDto> data = result.getResults();


        long total = result.getTotal();
        return new PageImpl<>(data, pageable, total);
    }

    private BooleanExpression distanceIn(Integer distance,NumberExpression distanceExpression) {
        if(distance == null){
            return null;
        }else{
            return distanceExpression.loe(distance);
        }
    }

    private BooleanExpression addressFilter(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        return shop.shopAddress.contains(address);
    }

    private BooleanExpression keywordFilter(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        return shop.name.contains(keyword).or(shop.shopDescription.contains(keyword));
    }


    private OrderSpecifier orderFilter(String orderCondition,NumberExpression distanceExpression, StringPath aliasSales ) {
        if (StringUtils.isEmpty(orderCondition) || orderCondition.equals("추천순")) {
            return distanceExpression.asc();
        } else if (orderCondition.equals("인기순")) {
            return aliasSales.desc();
        } else {
            // 리뷰 많은 순인 경우
            return shop.reviewNum.desc();
        }
    }

    private OrderSpecifier orderFilter2(String orderCondition, StringPath aliasSales ) {
        if (StringUtils.isEmpty(orderCondition)) {
            return shop.id.asc();
        } else {
            return aliasSales.desc();
        }
    }

    
}
