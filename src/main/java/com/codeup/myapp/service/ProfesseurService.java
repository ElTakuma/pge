package com.codeup.myapp.service;

import com.codeup.myapp.domain.Professeur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Professeur}.
 */
public interface ProfesseurService {

    /**
     * Save a professeur.
     *
     * @param professeur the entity to save.
     * @return the persisted entity.
     */
    Professeur save(Professeur professeur);

    /**
     * Get all the professeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Professeur> findAll(Pageable pageable);

    /**
     * Get the "id" professeur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Professeur> findOne(Long id);

    /**
     * Delete the "id" professeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
