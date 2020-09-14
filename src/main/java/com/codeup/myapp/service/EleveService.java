package com.codeup.myapp.service;

import com.codeup.myapp.domain.Eleve;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Eleve}.
 */
public interface EleveService {

    /**
     * Save a eleve.
     *
     * @param eleve the entity to save.
     * @return the persisted entity.
     */
    Eleve save(Eleve eleve);

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Eleve> findAll(Pageable pageable);

    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Eleve> findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
