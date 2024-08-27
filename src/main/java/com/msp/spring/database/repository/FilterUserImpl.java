package com.msp.spring.database.repository;

import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.predicate.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.msp.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserImpl implements FilterUser {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getFirstName(), user.firstName::like)
                .add(filter.getLastName(), user.lastName::containsIgnoreCase)
                .add(filter.getBirthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

/*
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
*/
}
