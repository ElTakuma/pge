package com.codeup.myapp.service.impl;

import com.codeup.myapp.service.MatiereLtService;
import com.codeup.myapp.domain.MatiereLt;
import com.codeup.myapp.repository.MatiereLtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MatiereLt}.
 */
@Service
@Transactional
public class MatiereLtServiceImpl implements MatiereLtService {

    private final Logger log = LoggerFactory.getLogger(MatiereLtServiceImpl.class);

    private final MatiereLtRepository matiereLtRepository;

    public MatiereLtServiceImpl(MatiereLtRepository matiereLtRepository) {
        this.matiereLtRepository = matiereLtRepository;
    }

    /**
     * Save a matiereLt.
     *
     * @param matiereLt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MatiereLt save(MatiereLt matiereLt) {
        log.debug("Request to save MatiereLt : {}", matiereLt);
        return matiereLtRepository.save(matiereLt);
    }

    /**
     * Get all the matiereLts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatiereLt> findAll(Pageable pageable) {
        log.debug("Request to get all MatiereLts");
        return matiereLtRepository.findAll(pageable);
    }

    /**
     * Get one matiereLt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MatiereLt> findOne(Long id) {
        log.debug("Request to get MatiereLt : {}", id);
        return matiereLtRepository.findById(id);
    }

    /**
     * Delete the matiereLt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatiereLt : {}", id);
        matiereLtRepository.deleteById(id);
    }
}
