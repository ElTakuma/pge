package com.codeup.myapp.service;

import com.codeup.myapp.domain.Bulettin;

import com.codeup.myapp.service.dto.BuletinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Bulettin}.
 */
public interface BulettinService {

    /**
     * Save a bulettin.
     *
     * @param bulettin the entity to save.
     * @return the persisted entity.
     */
    Bulettin save(Bulettin bulettin);

    /**
     * Get all the bulettins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Bulettin> findAll(Pageable pageable);

    /**
     * Get the "id" bulettin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bulettin> findOne(Long id);

    Optional<BuletinDTO> findOneComplet(Long id);

    /**
     * Delete the "id" bulettin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
