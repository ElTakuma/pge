package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.MatiereLt;
import com.codeup.myapp.repository.MatiereLtRepository;
import com.codeup.myapp.service.MatiereLtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MatiereLtResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MatiereLtResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private MatiereLtRepository matiereLtRepository;

    @Autowired
    private MatiereLtService matiereLtService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereLtMockMvc;

    private MatiereLt matiereLt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatiereLt createEntity(EntityManager em) {
        MatiereLt matiereLt = new MatiereLt()
            .reference(DEFAULT_REFERENCE);
        return matiereLt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatiereLt createUpdatedEntity(EntityManager em) {
        MatiereLt matiereLt = new MatiereLt()
            .reference(UPDATED_REFERENCE);
        return matiereLt;
    }

    @BeforeEach
    public void initTest() {
        matiereLt = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiereLt() throws Exception {
        int databaseSizeBeforeCreate = matiereLtRepository.findAll().size();

        // Create the MatiereLt
        restMatiereLtMockMvc.perform(post("/api/matiere-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereLt)))
            .andExpect(status().isCreated());

        // Validate the MatiereLt in the database
        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeCreate + 1);
        MatiereLt testMatiereLt = matiereLtList.get(matiereLtList.size() - 1);
        assertThat(testMatiereLt.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createMatiereLtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereLtRepository.findAll().size();

        // Create the MatiereLt with an existing ID
        matiereLt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereLtMockMvc.perform(post("/api/matiere-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereLt)))
            .andExpect(status().isBadRequest());

        // Validate the MatiereLt in the database
        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereLtRepository.findAll().size();
        // set the field null
        matiereLt.setReference(null);

        // Create the MatiereLt, which fails.

        restMatiereLtMockMvc.perform(post("/api/matiere-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereLt)))
            .andExpect(status().isBadRequest());

        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatiereLts() throws Exception {
        // Initialize the database
        matiereLtRepository.saveAndFlush(matiereLt);

        // Get all the matiereLtList
        restMatiereLtMockMvc.perform(get("/api/matiere-lts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiereLt.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }
    
    @Test
    @Transactional
    public void getMatiereLt() throws Exception {
        // Initialize the database
        matiereLtRepository.saveAndFlush(matiereLt);

        // Get the matiereLt
        restMatiereLtMockMvc.perform(get("/api/matiere-lts/{id}", matiereLt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiereLt.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }

    @Test
    @Transactional
    public void getNonExistingMatiereLt() throws Exception {
        // Get the matiereLt
        restMatiereLtMockMvc.perform(get("/api/matiere-lts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiereLt() throws Exception {
        // Initialize the database
        matiereLtService.save(matiereLt);

        int databaseSizeBeforeUpdate = matiereLtRepository.findAll().size();

        // Update the matiereLt
        MatiereLt updatedMatiereLt = matiereLtRepository.findById(matiereLt.getId()).get();
        // Disconnect from session so that the updates on updatedMatiereLt are not directly saved in db
        em.detach(updatedMatiereLt);
        updatedMatiereLt
            .reference(UPDATED_REFERENCE);

        restMatiereLtMockMvc.perform(put("/api/matiere-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatiereLt)))
            .andExpect(status().isOk());

        // Validate the MatiereLt in the database
        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeUpdate);
        MatiereLt testMatiereLt = matiereLtList.get(matiereLtList.size() - 1);
        assertThat(testMatiereLt.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiereLt() throws Exception {
        int databaseSizeBeforeUpdate = matiereLtRepository.findAll().size();

        // Create the MatiereLt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereLtMockMvc.perform(put("/api/matiere-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereLt)))
            .andExpect(status().isBadRequest());

        // Validate the MatiereLt in the database
        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiereLt() throws Exception {
        // Initialize the database
        matiereLtService.save(matiereLt);

        int databaseSizeBeforeDelete = matiereLtRepository.findAll().size();

        // Delete the matiereLt
        restMatiereLtMockMvc.perform(delete("/api/matiere-lts/{id}", matiereLt.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatiereLt> matiereLtList = matiereLtRepository.findAll();
        assertThat(matiereLtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
