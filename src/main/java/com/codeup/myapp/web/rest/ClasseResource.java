package com.codeup.myapp.web.rest;

import com.codeup.myapp.domain.Classe;
import com.codeup.myapp.service.BuletinAutoGenerationService;
import com.codeup.myapp.service.ClasseService;
import com.codeup.myapp.service.dto.BuletinDTO;
import com.codeup.myapp.service.dto.ClasseDTO;
import com.codeup.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.codeup.myapp.domain.Classe}.
 */
@RestController
@RequestMapping("/api")
public class ClasseResource {

    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);

    private static final String ENTITY_NAME = "classe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseService classeService;
    private final BuletinAutoGenerationService buletinAutoGenerationService;

    public ClasseResource(
        ClasseService classeService,
        BuletinAutoGenerationService buletinAutoGenerationService
    ) {
        this.classeService = classeService;
        this.buletinAutoGenerationService = buletinAutoGenerationService;
    }

    /**
     * {@code POST  /classes} : Create a new classe.
     *
     * @param classe the classe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classe, or with status {@code 400 (Bad Request)} if the classe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classes")
    public ResponseEntity<Classe> createClasse(@Valid @RequestBody Classe classe) throws URISyntaxException {
        log.debug("REST request to save Classe : {}", classe);
        if (classe.getId() != null) {
            throw new BadRequestAlertException("A new classe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classe.setEffectif((long) 0);
        Classe result = classeService.save(classe);
        return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classes} : Updates an existing classe.
     *
     * @param classe the classe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classe,
     * or with status {@code 400 (Bad Request)} if the classe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classes")
    public ResponseEntity<Classe> updateClasse(@Valid @RequestBody Classe classe) throws URISyntaxException {
        log.debug("REST request to update Classe : {}", classe);
        if (classe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Classe result = classeService.save(classe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classes} : get all the classes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classes in body.
     */
    @GetMapping("/classes")
    public ResponseEntity<List<Classe>> getAllClasses(Pageable pageable) {
        log.debug("REST request to get a page of Classes");
        Page<Classe> page = classeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classes/:id} : get the "id" classe.
     *
     * @param id the id of the classe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classes/{id}")
    public ResponseEntity<Classe> getClasse(@PathVariable Long id) {
        log.debug("REST request to get Classe : {}", id);
        Optional<Classe> classe = classeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classe);
    }

    /**
     * {@code GET  /classes-dto/:id} : get the "id" classe.BuletinDTO
     *
     * @param id the id of the classe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classes-dto/{id}")
    public ResponseEntity<ClasseDTO> getClasseDTO(@PathVariable Long id) {
        log.debug("REST request to get Classe : {}", id);
        Optional<ClasseDTO> classeDTO = classeService.findOneComplet(id);
        System.out.println("1- ##############################################");
        System.out.println("Testing in Ressource: classeDTO ==> " + classeDTO);
        return ResponseUtil.wrapOrNotFound(classeDTO);
    }

    /**
     * {@code DELETE  /classes/:id} : delete the "id" classe.
     *
     * @param id the id of the classe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        log.debug("REST request to delete Classe : {}", id);
        classeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/classes-bulletin")
    public ResponseEntity<Void> createBulletinFromClasse(@Valid @RequestBody Classe classe) {
        log.debug("REST request to save Bulletin of many Eleves : {}", classe);
        System.out.println("***************************************************");
        System.out.println("TESTING EXECUTION IN RESSOURCE");
        System.out.println("***************************************************");
        buletinAutoGenerationService.createManyBulletinFromClasse(classe);
        return ResponseEntity.accepted().build();
    }
}
