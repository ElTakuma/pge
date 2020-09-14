package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Bulettin;

import com.codeup.myapp.domain.enumeration.SessionLt;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bulettin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulettinRepository extends JpaRepository<Bulettin, Long>, QuerydslPredicateExecutor<Bulettin> {

    @Query("SELECT COUNT (b) FROM Bulettin b " +
        "WHERE b.sessionB = :session AND b.classe.id = :classeId AND b.eleve.id = :eleveId")
    int nombreBulletinDeSession(@Param("session") SessionLt session,
                                @Param("classeId") long classeId,
                                @Param("eleveId") long eleveId);

    @Query("SELECT COUNT (b) FROM Bulettin b")
    int nombreBulletin();
//
//    @Query("SELECT COUNT (b) FROM Note b WHERE b.code having :year")
//    int nombreBulletinByYear();
}
