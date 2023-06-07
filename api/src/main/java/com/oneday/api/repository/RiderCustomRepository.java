package com.oneday.api.repository;

import com.oneday.api.model.Rider;
import com.oneday.api.model.dto.RiderReadDto;
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

import static com.oneday.api.model.QRider.rider;

@Repository
public class RiderCustomRepository {

    @PersistenceContext
    private EntityManager em;

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
