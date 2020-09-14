package com.codeup.myapp.service;

import com.codeup.myapp.domain.MatiereLt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MatiereLt}.
 */
public interface MatiereLtService {

    /**
     * Save a matiereLt.
     *
     * @param matiereLt the entity to save.
     * @return the persisted entity.
     */
    MatiereLt save(MatiereLt matiereLt);

    /**
     * Get all the matiereLts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatiereLt> findAll(Pageable pageable);

    /**
     * Get the "id" matiereLt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatiereLt> findOne(Long id);

    /**
     * Delete the "id" matiereLt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
