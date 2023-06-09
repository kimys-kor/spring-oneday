package com.oneday.api.repository;

import com.oneday.api.model.QUser;
import com.oneday.api.model.dto.MemberDto;
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


@Repository
public class MemberCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<MemberDto> findAll(Pageable pageable) {

        QUser user = QUser.user;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<MemberDto> results = queryFactory.select(Projections.fields(MemberDto.class,
                        user.id,
                        user.email,
                        user.nickname,
                        user.phoneNum,
                        user.createdDt,
                        user.status
                ))
                .from(user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<MemberDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<>(data, pageable, total);

    }


}
