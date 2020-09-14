package com.codeup.myapp.web.rest;

import com.codeup.myapp.domain.Bulettin;
import com.codeup.myapp.service.BulettinService;
import com.codeup.myapp.service.dto.BuletinDTO;
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
 * REST controller for managing {@link com.codeup.myapp.domain.Bulettin}.
 */
@RestController
@RequestMapping("/api")
public class BulettinResource {

    private final Logger log = LoggerFactory.getLogger(BulettinResource.class);

    private static final String ENTITY_NAME = "bulettin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BulettinService bulettinService;

    public BulettinResource(BulettinService bulettinService) {
        this.bulettinService = bulettinService;
    }

    /**
     * {@code POST  /bulettins} : Create a new bulettin.
     *
     * @param bulettin the bulettin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bulettin, or with status {@code 400 (Bad Request)} if the bulettin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bulettins")
    public ResponseEntity<Bulettin> createBulettin(@Valid @RequestBody Bulettin bulettin) throws URISyntaxException {
        log.debug("REST request to save Bulettin : {}", bulettin);
        if (bulettin.getId() != null) {
            throw new BadRequestAlertException("A new bulettin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulettin result = bulettinService.save(bulettin);
        return ResponseEntity.created(new URI("/api/bulettins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bulettins} : Updates an existing bulettin.
     *
     * @param bulettin the bulettin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bulettin,
     * or with status {@code 400 (Bad Request)} if the bulettin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bulettin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bulettins")
    public ResponseEntity<Bulettin> updateBulettin(@Valid @RequestBody Bulettin bulettin) throws URISyntaxException {
        log.debug("REST request to update Bulettin : {}", bulettin);
        if (bulettin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bulettin result = bulettinService.save(bulettin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bulettin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bulettins} : get all the bulettins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bulettins in body.
     */
    @GetMapping("/bulettins")
    public ResponseEntity<List<Bulettin>> getAllBulettins(Pageable pageable) {
        log.debug("REST request to get a page of Bulettins");
        Page<Bulettin> page = bulettinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bulettins/:id} : get the "id" bulettin.
     *
     * @param id the id of the bulettin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bulettin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bulettins/{id}")
    public ResponseEntity<Bulettin> getBulettin(@PathVariable Long id) {
        log.debug("REST request to get Bulettin : {}", id);
        Optional<Bulettin> bulettin = bulettinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bulettin);
    }

    @GetMapping("/bulettins-dto/{id}")
    public ResponseEntity<BuletinDTO> getBulettinComplet(@PathVariable Long id) {
        log.debug("REST request to get BulettinComplet : {}", id);
        Optional<BuletinDTO> buletinDTO = bulettinService.findOneComplet(id);
        return ResponseUtil.wrapOrNotFound(buletinDTO);
    }

    /**
     * {@code DELETE  /bulettins/:id} : delete the "id" bulettin.
     *
     * @param id the id of the bulettin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bulettins/{id}")
    public ResponseEntity<Void> deleteBulettin(@PathVariable Long id) {
        log.debug("REST request to delete Bulettin : {}", id);
        bulettinService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
