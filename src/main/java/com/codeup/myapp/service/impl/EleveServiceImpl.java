package com.codeup.myapp.service.impl;

import com.codeup.myapp.service.EleveService;
import com.codeup.myapp.domain.Eleve;
import com.codeup.myapp.repository.EleveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Eleve}.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    public EleveServiceImpl(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    /**
     * Save a eleve.
     *
     * @param eleve the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Eleve save(Eleve eleve) {
        log.debug("Request to save Eleve : {}", eleve);
        return eleveRepository.save(eleve);
    }

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Eleve> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll(pageable);
    }

    /**
     * Get one eleve by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Eleve> findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        return eleveRepository.findById(id);
    }

    /**
     * Delete the eleve by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.deleteById(id);
    }
}
