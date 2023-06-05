package com.oneday.api.repository;

import com.oneday.api.model.QMember;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.model.dto.ShopReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
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

@Repository
public class ShopCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<ShopReadDto> findAll(Pageable pageable) {



        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ShopReadDto> results = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.ownerName,
                        shop.email,
                        shop.phoneNum,
                        shop.createdDt,
                        shop.updatedDt
                ))
                .from(shop)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<ShopReadDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<ShopReadDto>(data, pageable, total);

    }

    public Page<ShopReadDto> findByDistance(BigDecimal lat, BigDecimal lon, Integer distance, String orderCondition, String keyword, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        NumberExpression<BigDecimal> distanceExpression = Expressions.numberTemplate(BigDecimal.class,
                "6371 * acos(cos(radians({0})) * cos(radians({2})) * cos(radians({3}) - radians({1})) + sin(radians({0})) * sin(radians({2})))",
                lat, lon, shop.lat, shop.lon);


        QueryResults<ShopReadDto> results = queryFactory.select(Projections.fields(ShopReadDto.class,
                        shop.id,
                        shop.ownerName,
                        shop.email,
                        shop.phoneNum,
                        shop.createdDt,
                        shop.updatedDt,
                        distanceExpression.as("distance")
                ))
                .from(shop)
                .where(
                        keywordFilter(keyword),
                        distanceIn(distance, distanceExpression)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<ShopReadDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<ShopReadDto>(data, pageable, total);

    }

    private BooleanExpression distanceIn(Integer distance, NumberExpression distanceExpression) {
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
        return shop.name.contains(keyword).or(shop.ownerName.contains(keyword));
    }

    private OrderSpecifier orderFilter(String orderCondition, NumberExpression distanceExpression, StringPath aliasSales ) {
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
            return null;
        }
    }

}
