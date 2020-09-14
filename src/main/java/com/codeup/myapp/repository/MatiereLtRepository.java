package com.codeup.myapp.repository;

import com.codeup.myapp.domain.MatiereLt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MatiereLt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereLtRepository extends JpaRepository<MatiereLt, Long>, QuerydslPredicateExecutor<MatiereLt> {
}
