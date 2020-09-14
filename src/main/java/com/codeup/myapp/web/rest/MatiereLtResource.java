package com.codeup.myapp.web.rest;

import com.codeup.myapp.domain.MatiereLt;
import com.codeup.myapp.service.MatiereLtService;
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
 * REST controller for managing {@link com.codeup.myapp.domain.MatiereLt}.
 */
@RestController
@RequestMapping("/api")
public class MatiereLtResource {

    private final Logger log = LoggerFactory.getLogger(MatiereLtResource.class);

    private static final String ENTITY_NAME = "matiereLt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatiereLtService matiereLtService;

    public MatiereLtResource(MatiereLtService matiereLtService) {
        this.matiereLtService = matiereLtService;
    }

    /**
     * {@code POST  /matiere-lts} : Create a new matiereLt.
     *
     * @param matiereLt the matiereLt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matiereLt, or with status {@code 400 (Bad Request)} if the matiereLt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matiere-lts")
    public ResponseEntity<MatiereLt> createMatiereLt(@Valid @RequestBody MatiereLt matiereLt) throws URISyntaxException {
        log.debug("REST request to save MatiereLt : {}", matiereLt);
        if (matiereLt.getId() != null) {
            throw new BadRequestAlertException("A new matiereLt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatiereLt result = matiereLtService.save(matiereLt);
        return ResponseEntity.created(new URI("/api/matiere-lts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matiere-lts} : Updates an existing matiereLt.
     *
     * @param matiereLt the matiereLt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereLt,
     * or with status {@code 400 (Bad Request)} if the matiereLt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matiereLt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matiere-lts")
    public ResponseEntity<MatiereLt> updateMatiereLt(@Valid @RequestBody MatiereLt matiereLt) throws URISyntaxException {
        log.debug("REST request to update MatiereLt : {}", matiereLt);
        if (matiereLt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatiereLt result = matiereLtService.save(matiereLt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matiereLt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matiere-lts} : get all the matiereLts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matiereLts in body.
     */
    @GetMapping("/matiere-lts")
    public ResponseEntity<List<MatiereLt>> getAllMatiereLts(Pageable pageable) {
        log.debug("REST request to get a page of MatiereLts");
        Page<MatiereLt> page = matiereLtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matiere-lts/:id} : get the "id" matiereLt.
     *
     * @param id the id of the matiereLt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matiereLt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matiere-lts/{id}")
    public ResponseEntity<MatiereLt> getMatiereLt(@PathVariable Long id) {
        log.debug("REST request to get MatiereLt : {}", id);
        Optional<MatiereLt> matiereLt = matiereLtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiereLt);
    }

    /**
     * {@code DELETE  /matiere-lts/:id} : delete the "id" matiereLt.
     *
     * @param id the id of the matiereLt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matiere-lts/{id}")
    public ResponseEntity<Void> deleteMatiereLt(@PathVariable Long id) {
        log.debug("REST request to delete MatiereLt : {}", id);
        matiereLtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
