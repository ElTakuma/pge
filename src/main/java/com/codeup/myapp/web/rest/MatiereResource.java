package com.codeup.myapp.web.rest;

import com.codeup.myapp.domain.Matiere;
import com.codeup.myapp.service.MatiereService;
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
 * REST controller for managing {@link com.codeup.myapp.domain.Matiere}.
 */
@RestController
@RequestMapping("/api")
public class MatiereResource {

    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);

    private static final String ENTITY_NAME = "matiere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatiereService matiereService;

    public MatiereResource(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    /**
     * {@code POST  /matieres} : Create a new matiere.
     *
     * @param matiere the matiere to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matiere, or with status {@code 400 (Bad Request)} if the matiere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matieres")
    public ResponseEntity<Matiere> createMatiere(@Valid @RequestBody Matiere matiere) throws URISyntaxException {
        log.debug("REST request to save Matiere : {}", matiere);
        if (matiere.getId() != null) {
            throw new BadRequestAlertException("A new matiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Matiere result = matiereService.save(matiere);
        return ResponseEntity.created(new URI("/api/matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matieres} : Updates an existing matiere.
     *
     * @param matiere the matiere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiere,
     * or with status {@code 400 (Bad Request)} if the matiere is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matiere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matieres")
    public ResponseEntity<Matiere> updateMatiere(@Valid @RequestBody Matiere matiere) throws URISyntaxException {
        log.debug("REST request to update Matiere : {}", matiere);
        if (matiere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Matiere result = matiereService.save(matiere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matiere.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matieres} : get all the matieres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matieres in body.
     */
    @GetMapping("/matieres")
    public ResponseEntity<List<Matiere>> getAllMatieres(Pageable pageable) {
        log.debug("REST request to get a page of Matieres");
        Page<Matiere> page = matiereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matieres/:id} : get the "id" matiere.
     *
     * @param id the id of the matiere to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matiere, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matieres/{id}")
    public ResponseEntity<Matiere> getMatiere(@PathVariable Long id) {
        log.debug("REST request to get Matiere : {}", id);
        Optional<Matiere> matiere = matiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiere);
    }

    /**
     * {@code DELETE  /matieres/:id} : delete the "id" matiere.
     *
     * @param id the id of the matiere to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matieres/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        log.debug("REST request to delete Matiere : {}", id);
        matiereService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
