package com.msp.spring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@NamedQuery(name = "Company.findByName",
query = "select c from Company c where lower(c.name) = lower(:name) ")
public class Company implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection // use default table company_locales
    @CollectionTable(name = "company_locales",
                    joinColumns = {
                        @JoinColumn(name = "company_id")
                    })
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    @Builder.Default
    private Map<String, String> locales = new HashMap<>();
}
