package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Eleve;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Eleve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long>, QuerydslPredicateExecutor<Eleve> {
    @Query("SELECT e FROM Eleve e WHERE e.classe.id = :id")
    List<Eleve> eleveOfClasse(@Param("id") long id);
}
