package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Classe;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long>, QuerydslPredicateExecutor<Classe> {
}
