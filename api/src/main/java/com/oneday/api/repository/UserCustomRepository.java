package com.oneday.api.repository;


import com.oneday.api.model.QUser;
import com.oneday.api.model.dto.UserReadDto;
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

import static com.oneday.api.model.base.UserRole.ROLE_USER;


@Repository
public class UserCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<UserReadDto> findAll(Pageable pageable) {

        QUser user = QUser.user;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<UserReadDto> results = queryFactory.select(Projections.fields(UserReadDto.class,
                        user.status,
                        user.id,
                        user.phoneNum,
                        user.email,
                        user.nickname,
                        user.createdDt,
                        user.lastLogin,
                        user.point
                ))
                .from(user)
                .where(
                        user.role.eq(ROLE_USER)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<UserReadDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<>(data, pageable, total);

    }


}
