package com.codeup.myapp.service;

import com.codeup.myapp.domain.Note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Note}.
 */
public interface NoteService {

    /**
     * Save a note.
     *
     * @param note the entity to save.
     * @return the persisted entity.
     */
    Note save(Note note);

    Note saveByBulletin(Note note);

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Note> findAll(Pageable pageable);

    /**
     * Get the "id" note.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Note> findOne(Long id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
