package com.codeup.myapp.service;

import com.codeup.myapp.domain.Classe;

import com.codeup.myapp.service.dto.BuletinDTO;
import com.codeup.myapp.service.dto.ClasseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Classe}.
 */
public interface ClasseService {

    /**
     * Save a classe.
     *
     * @param classe the entity to save.
     * @return the persisted entity.
     */
    Classe save(Classe classe);

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Classe> findAll(Pageable pageable);

//    Page<ClasseDTO> findAllDTO(Pageable pageable);

    /**
     * Get the "id" classe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Classe> findOne(Long id);

    Optional<ClasseDTO> findOneComplet(Long id);

    /**
     * Delete the "id" classe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
