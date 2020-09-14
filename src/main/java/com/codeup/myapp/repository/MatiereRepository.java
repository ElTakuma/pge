package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Matiere;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long>, QuerydslPredicateExecutor<Matiere> {
    @Query("SELECT m FROM Matiere m WHERE m.classe.id = :id")
    List<Matiere> matiereOfClasse(@Param("id") long id);
}
