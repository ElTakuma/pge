package com.codeup.myapp.service.impl;

import com.codeup.myapp.service.ProfesseurService;
import com.codeup.myapp.domain.Professeur;
import com.codeup.myapp.repository.ProfesseurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Professeur}.
 */
@Service
@Transactional
public class ProfesseurServiceImpl implements ProfesseurService {

    private final Logger log = LoggerFactory.getLogger(ProfesseurServiceImpl.class);

    private final ProfesseurRepository professeurRepository;

    public ProfesseurServiceImpl(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    /**
     * Save a professeur.
     *
     * @param professeur the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Professeur save(Professeur professeur) {
        log.debug("Request to save Professeur : {}", professeur);
        return professeurRepository.save(professeur);
    }

    /**
     * Get all the professeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Professeur> findAll(Pageable pageable) {
        log.debug("Request to get all Professeurs");
        return professeurRepository.findAll(pageable);
    }

    /**
     * Get one professeur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Professeur> findOne(Long id) {
        log.debug("Request to get Professeur : {}", id);
        return professeurRepository.findById(id);
    }

    /**
     * Delete the professeur by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professeur : {}", id);
        professeurRepository.deleteById(id);
    }
}
