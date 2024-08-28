package com.msp.spring.database.repository;

import com.msp.spring.database.dto.PersonalInfo;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.entity.User;
import com.msp.spring.database.repository.predicate.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static com.msp.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserImpl implements FilterUser {

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplateNamed;

    private final String  FIND_BY_COMPANY_AND_ROLE =
            "SELECT u.firstname, u.lastname, u.birth_date " +
            "FROM users u " +
            "WHERE u.company_id = ? AND u.role = ?";

    private final String  UPDATE_COMPANY_AND_ROLE =
            "UPDATE users " +
            "SET company_id = ?, role = ?" +
            "WHERE id = ?";

    private final String  UPDATE_COMPANY_AND_ROLE_NAMED =
            "UPDATE users " +
            "SET company_id = :company_id, role = :role " +
            "WHERE id = :id";

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

    @Override
    public List<PersonalInfo> findAllByCompanyAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(rs.getString("firstname"),
                                                 rs.getString("lastname"),
                                                 rs.getDate("birth_date").toLocalDate()),
                companyId, role.name());
    }

    @Override
    public int[] updateCompanyAndRole(List<User> users) {
        List<Object[]> args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();

        return jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public int[] updateCompanyAndRoleNamed(List<User> users) {
        MapSqlParameterSource[] args = users.stream()
                .map(user -> Map.of("company_id", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        return jdbcTemplateNamed.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
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
