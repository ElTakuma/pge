package com.codeup.myapp.service.impl;

import com.codeup.myapp.repository.NoteRepository;
import com.codeup.myapp.service.BulettinService;
import com.codeup.myapp.domain.Bulettin;
import com.codeup.myapp.repository.BulettinRepository;
import com.codeup.myapp.service.dto.BuletinDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bulettin}.
 */
@Service
@Transactional
public class BulettinServiceImpl implements BulettinService {

    private final Logger log = LoggerFactory.getLogger(BulettinServiceImpl.class);

    private final BulettinRepository bulettinRepository;
    private final NoteRepository noteRepository;

    public BulettinServiceImpl(
        BulettinRepository bulettinRepository,
        NoteRepository noteRepository
        ) {
        this.bulettinRepository = bulettinRepository;
        this.noteRepository = noteRepository;
    }

    /**
     * Save a bulettin.
     *
     * @param bulettin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Bulettin save(Bulettin bulettin) {
        log.debug("Request to save Bulettin : {}", bulettin);
        return bulettinRepository.save(bulettin);
    }

    /**
     * Get all the bulettins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bulettin> findAll(Pageable pageable) {
        log.debug("Request to get all Bulettins");
        return bulettinRepository.findAll(pageable);
    }

    /**
     * Get one bulettin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bulettin> findOne(Long id) {
        log.debug("Request to get Bulettin : {}", id);
        return bulettinRepository.findById(id);
    }
    /**
     * Get one bulettin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuletinDTO> findOneComplet(Long id) {
        BuletinDTO result = new BuletinDTO();
        Bulettin bulettin = bulettinRepository.getOne(id);
        result.setBuletin(bulettin);
        result.setNotes(noteRepository.noteOfBulletin(bulettin.getId()));
        log.debug("Request to get BulettinDTO : {}", id);
        return Optional.of(result);
    }

    /**
     * Delete the bulettin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bulettin : {}", id);
        bulettinRepository.deleteById(id);
    }
}
