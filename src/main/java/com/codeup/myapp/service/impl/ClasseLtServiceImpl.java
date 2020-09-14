package com.codeup.myapp.service.impl;

import com.codeup.myapp.service.ClasseLtService;
import com.codeup.myapp.domain.ClasseLt;
import com.codeup.myapp.repository.ClasseLtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClasseLt}.
 */
@Service
@Transactional
public class ClasseLtServiceImpl implements ClasseLtService {

    private final Logger log = LoggerFactory.getLogger(ClasseLtServiceImpl.class);

    private final ClasseLtRepository classeLtRepository;

    public ClasseLtServiceImpl(ClasseLtRepository classeLtRepository) {
        this.classeLtRepository = classeLtRepository;
    }

    /**
     * Save a classeLt.
     *
     * @param classeLt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClasseLt save(ClasseLt classeLt) {
        log.debug("Request to save ClasseLt : {}", classeLt);
        return classeLtRepository.save(classeLt);
    }

    /**
     * Get all the classeLts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClasseLt> findAll(Pageable pageable) {
        log.debug("Request to get all ClasseLts");
        return classeLtRepository.findAll(pageable);
    }

    /**
     * Get one classeLt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClasseLt> findOne(Long id) {
        log.debug("Request to get ClasseLt : {}", id);
        return classeLtRepository.findById(id);
    }

    /**
     * Delete the classeLt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClasseLt : {}", id);
        classeLtRepository.deleteById(id);
    }
}
