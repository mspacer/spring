package com.msp.spring.database.repository;

import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserImpl implements FilterUser {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        query.select(userRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null) {
            predicates.add(cb.like(userRoot.get("firstName"), filter.getFirstName()));
        }
        if (filter.getLastName() != null) {
            predicates.add(cb.like(userRoot.get("lastName"), filter.getLastName()));
        }
        if (filter.getBirthDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(userRoot.get("birthDate"), filter.getBirthDate()));
        }

        query.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }
}
