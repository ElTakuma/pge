package com.codeup.myapp.service.impl;

import com.codeup.myapp.service.MatiereService;
import com.codeup.myapp.domain.Matiere;
import com.codeup.myapp.repository.MatiereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Matiere}.
 */
@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {

    private final Logger log = LoggerFactory.getLogger(MatiereServiceImpl.class);

    private final MatiereRepository matiereRepository;

    public MatiereServiceImpl(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    /**
     * Save a matiere.
     *
     * @param matiere the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Matiere save(Matiere matiere) {
        log.debug("Request to save Matiere : {}", matiere);
        return matiereRepository.saveAndFlush(matiere);
    }

    /**
     * Get all the matieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Matiere> findAll(Pageable pageable) {
        log.debug("Request to get all Matieres");
        return matiereRepository.findAll(pageable);
    }

    /**
     * Get one matiere by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Matiere> findOne(Long id) {
        log.debug("Request to get Matiere : {}", id);
        return matiereRepository.findById(id);
    }

    /**
     * Delete the matiere by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matiere : {}", id);
        matiereRepository.deleteById(id);
    }
}
