package com.codeup.myapp.service;

import com.codeup.myapp.domain.ClasseLt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ClasseLt}.
 */
public interface ClasseLtService {

    /**
     * Save a classeLt.
     *
     * @param classeLt the entity to save.
     * @return the persisted entity.
     */
    ClasseLt save(ClasseLt classeLt);

    /**
     * Get all the classeLts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasseLt> findAll(Pageable pageable);

    /**
     * Get the "id" classeLt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClasseLt> findOne(Long id);

    /**
     * Delete the "id" classeLt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
