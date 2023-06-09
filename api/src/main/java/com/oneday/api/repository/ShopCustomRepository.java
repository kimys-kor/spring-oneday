package com.oneday.api.repository;


import com.oneday.api.model.dto.ShopReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
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

        QueryResults<ShopReadDto> result = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.name,
                        shop.ownerName,
                        ExpressionUtils.as(shop.reviewNum, "rn"),
                        shop.businessNumber,
                        shop.contactNumber,
                        shop.shopAddress,
                        shop.shopDescription,
                        shop.time,
                        shop.profile1,
                        shop.profile2,
                        shop.profile3,
                        shop.lat,
                        shop.lon,
                        distanceExpression.as("distance"),
                        ExpressionUtils.as(
                                JPAExpressions.select(orders.count())
                                        .from(orders)
                                        .where(
                                                orders.shopId.eq(shop.id),
                                                orders.createdDt.between(yesterdayStart,todayStart)
                                        ),
                                "pp"),
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
            // 30km 반경 일일 판매량순 정렬
            // 어제 날짜00시
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalDateTime yesterdayStart = yesterday.atStartOfDay();

            // 오늘 날짜00시
            LocalDate today = LocalDate.now();
            LocalDateTime todayStart = today.atStartOfDay();

            return aliasSales.desc();
        } else {
            // 리뷰 많은 순인 경우
            return shop.reviewNum.desc();
        }
    }

}
