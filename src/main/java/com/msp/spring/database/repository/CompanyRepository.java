package com.msp.spring.database.repository;

import com.msp.spring.database.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//@Repository нет необходимости т.к. spring ищет по наследуемому интерфейсу
public interface CompanyRepository extends CrudRepository/*Repository*/<Company, Integer> {

    //CrudRepository уже имеет базовые методы
    //Optional<Company> findById(Integer id);
    //void delete(Company entity);

    @Query("select c from Company c " +
           "join fetch c.locales " +
           "where c.name = :name2 ")
    Optional<Company> findByName(@Param("name2") String name);

    List<Company> findAllByNameIsContainingIgnoringCase(String fragment);
}