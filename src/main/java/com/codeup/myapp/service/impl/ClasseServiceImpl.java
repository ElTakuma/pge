package com.codeup.myapp.service.impl;

import com.codeup.myapp.domain.Eleve;
import com.codeup.myapp.domain.QClasse;
import com.codeup.myapp.repository.MatiereRepository;
import com.codeup.myapp.service.BuletinAutoGenerationService;
import com.codeup.myapp.service.ClasseService;
import com.codeup.myapp.domain.Classe;
import com.codeup.myapp.repository.ClasseRepository;
import com.codeup.myapp.service.dto.BuletinDTO;
import com.codeup.myapp.service.dto.ClasseDTO;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Classe}.
 */
@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseServiceImpl.class);

    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;
    private final BuletinAutoGenerationService buletinAutoGenerationService
        ;

    public ClasseServiceImpl(
        ClasseRepository classeRepository,
        MatiereRepository matiereRepository,
        BuletinAutoGenerationService buletinAutoGenerationService
    ) {
        this.classeRepository = classeRepository;
        this.matiereRepository = matiereRepository;
        this.buletinAutoGenerationService = buletinAutoGenerationService;
    }

    /**
     * Save a classe.
     *
     * @param classe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Classe save(Classe classe) {
        log.debug("Request to save Classe : {}", classe);
        return classeRepository.save(classe);
    }

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Classe> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
        return classeRepository.findAll(pageable);
    }

//    /**
//     * Get all the classes.
//     *
//     * @param pageable the pagination information.
//     * @return the list of entities.
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public Page<ClasseDTO> findAllDTO(Pageable pageable) {
//        log.debug("Request to get all ClassesDTO");
//        BooleanExpression predicate = null;
//        QClasse classe = QClasse.classe;
//        return classeRepository.findAll(predicate, pageable);
//    }

    /**
     * Get one classe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Classe> findOne(Long id) {
        log.debug("Request to get Classe : {}", id);
        return classeRepository.findById(id);
    }
    /**
     * Get one classe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOneComplet(Long id) {
        log.debug("Request to get ClasseDTO : {}", id);
        ClasseDTO result = new ClasseDTO();
        result.setClasse(classeRepository.getOne(id));
//        System.out.println("3- ##############################################");
//        System.out.println("Testing in impl: result.classe ==> " + result.getClasse());
        result.setMatieres(matiereRepository.matiereOfClasse(id));
        return Optional.of(result);
    }

    /**
     * Delete the classe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.deleteById(id);
    }

}
