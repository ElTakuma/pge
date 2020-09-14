package com.codeup.myapp.web.rest;

import com.codeup.myapp.domain.ClasseLt;
import com.codeup.myapp.service.ClasseLtService;
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
 * REST controller for managing {@link com.codeup.myapp.domain.ClasseLt}.
 */
@RestController
@RequestMapping("/api")
public class ClasseLtResource {

    private final Logger log = LoggerFactory.getLogger(ClasseLtResource.class);

    private static final String ENTITY_NAME = "classeLt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseLtService classeLtService;

    public ClasseLtResource(ClasseLtService classeLtService) {
        this.classeLtService = classeLtService;
    }

    /**
     * {@code POST  /classe-lts} : Create a new classeLt.
     *
     * @param classeLt the classeLt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classeLt, or with status {@code 400 (Bad Request)} if the classeLt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classe-lts")
    public ResponseEntity<ClasseLt> createClasseLt(@Valid @RequestBody ClasseLt classeLt) throws URISyntaxException {
        log.debug("REST request to save ClasseLt : {}", classeLt);
        if (classeLt.getId() != null) {
            throw new BadRequestAlertException("A new classeLt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasseLt result = classeLtService.save(classeLt);
        return ResponseEntity.created(new URI("/api/classe-lts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classe-lts} : Updates an existing classeLt.
     *
     * @param classeLt the classeLt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeLt,
     * or with status {@code 400 (Bad Request)} if the classeLt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classeLt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classe-lts")
    public ResponseEntity<ClasseLt> updateClasseLt(@Valid @RequestBody ClasseLt classeLt) throws URISyntaxException {
        log.debug("REST request to update ClasseLt : {}", classeLt);
        if (classeLt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClasseLt result = classeLtService.save(classeLt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeLt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classe-lts} : get all the classeLts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classeLts in body.
     */
    @GetMapping("/classe-lts")
    public ResponseEntity<List<ClasseLt>> getAllClasseLts(Pageable pageable) {
        log.debug("REST request to get a page of ClasseLts");
        Page<ClasseLt> page = classeLtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classe-lts/:id} : get the "id" classeLt.
     *
     * @param id the id of the classeLt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeLt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classe-lts/{id}")
    public ResponseEntity<ClasseLt> getClasseLt(@PathVariable Long id) {
        log.debug("REST request to get ClasseLt : {}", id);
        Optional<ClasseLt> classeLt = classeLtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classeLt);
    }

    /**
     * {@code DELETE  /classe-lts/:id} : delete the "id" classeLt.
     *
     * @param id the id of the classeLt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classe-lts/{id}")
    public ResponseEntity<Void> deleteClasseLt(@PathVariable Long id) {
        log.debug("REST request to delete ClasseLt : {}", id);
        classeLtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
