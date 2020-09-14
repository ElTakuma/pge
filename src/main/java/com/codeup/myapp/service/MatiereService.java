package com.codeup.myapp.service;

import com.codeup.myapp.domain.Matiere;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Matiere}.
 */
public interface MatiereService {

    /**
     * Save a matiere.
     *
     * @param matiere the entity to save.
     * @return the persisted entity.
     */
    Matiere save(Matiere matiere);

    /**
     * Get all the matieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Matiere> findAll(Pageable pageable);

    /**
     * Get the "id" matiere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Matiere> findOne(Long id);

    /**
     * Delete the "id" matiere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
