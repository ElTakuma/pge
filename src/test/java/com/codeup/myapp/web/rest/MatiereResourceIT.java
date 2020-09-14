package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.Matiere;
import com.codeup.myapp.repository.MatiereRepository;
import com.codeup.myapp.service.MatiereService;

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
 * Integration tests for the {@link MatiereResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MatiereResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_COEFICIENT = 1L;
    private static final Long UPDATED_COEFICIENT = 2L;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereMockMvc;

    private Matiere matiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .code(DEFAULT_CODE)
            .coeficient(DEFAULT_COEFICIENT);
        return matiere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createUpdatedEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .code(UPDATED_CODE)
            .coeficient(UPDATED_COEFICIENT);
        return matiere;
    }

    @BeforeEach
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere
        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMatiere.getCoeficient()).isEqualTo(DEFAULT_COEFICIENT);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setCode(null);

        // Create the Matiere, which fails.

        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoeficientIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setCoeficient(null);

        // Create the Matiere, which fails.

        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].coeficient").value(hasItem(DEFAULT_COEFICIENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.coeficient").value(DEFAULT_COEFICIENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereService.save(matiere);

        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findById(matiere.getId()).get();
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .code(UPDATED_CODE)
            .coeficient(UPDATED_COEFICIENT);

        restMatiereMockMvc.perform(put("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatiere)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMatiere.getCoeficient()).isEqualTo(UPDATED_COEFICIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Create the Matiere

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereMockMvc.perform(put("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereService.save(matiere);

        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Delete the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
