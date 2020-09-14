package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Professeur;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Professeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long>, QuerydslPredicateExecutor<Professeur> {
}
