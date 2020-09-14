package com.codeup.myapp.repository;

import com.codeup.myapp.domain.Note;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, QuerydslPredicateExecutor<Note> {

    @Query("SELECT COUNT (n) FROM Note n")
    int nombreNote();

    @Query("SELECT n FROM Note n WHERE n.bulettin.id = :id")
    List<Note> noteOfBulletin(@Param("id") long id);
}
