package com.oneday.api.repository;

import com.oneday.api.model.QMember;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.model.dto.ShopReadDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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


}
