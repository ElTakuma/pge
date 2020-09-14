package com.codeup.myapp.repository;

import com.codeup.myapp.domain.ClasseLt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClasseLt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseLtRepository extends JpaRepository<ClasseLt, Long>, QuerydslPredicateExecutor<ClasseLt> {
}
