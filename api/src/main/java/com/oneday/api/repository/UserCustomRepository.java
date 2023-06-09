package com.oneday.api.repository;

import com.oneday.api.model.QUser;
import com.oneday.api.model.dto.UserDto;
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

    public Page<UserDto> findAll(Pageable pageable) {

        QUser user = QUser.user;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<UserDto> results = queryFactory.select(Projections.fields(UserDto.class,
                        user.id,
                        user.email,
                        user.nickname,
                        user.phoneNum,
                        user.createdDt,
                        user.status
                ))
                .from(user)
                .where(
                        user.role.eq(ROLE_USER)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<UserDto> data = results.getResults();


        long total = results.getTotal();
        return new PageImpl<>(data, pageable, total);

    }


}
