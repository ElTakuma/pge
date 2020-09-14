package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.Eleve;
import com.codeup.myapp.repository.EleveRepository;
import com.codeup.myapp.service.EleveService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EleveResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EleveResourceIT {

    private static final String DEFAULT_MATRICUL = "AAAAAAAAAA";
    private static final String UPDATED_MATRICUL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEleveMockMvc;

    private Eleve eleve;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eleve createEntity(EntityManager em) {
        Eleve eleve = new Eleve()
            .matricul(DEFAULT_MATRICUL)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE);
        return eleve;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eleve createUpdatedEntity(EntityManager em) {
        Eleve eleve = new Eleve()
            .matricul(UPDATED_MATRICUL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE);
        return eleve;
    }

    @BeforeEach
    public void initTest() {
        eleve = createEntity(em);
    }

    @Test
    @Transactional
    public void createEleve() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve
        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isCreated());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate + 1);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getMatricul()).isEqualTo(DEFAULT_MATRICUL);
        assertThat(testEleve.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEleve.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEleve.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void createEleveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve with an existing ID
        eleve.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatriculIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveRepository.findAll().size();
        // set the field null
        eleve.setMatricul(null);

        // Create the Eleve, which fails.

        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveRepository.findAll().size();
        // set the field null
        eleve.setNom(null);

        // Create the Eleve, which fails.

        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveRepository.findAll().size();
        // set the field null
        eleve.setPrenom(null);

        // Create the Eleve, which fails.

        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = eleveRepository.findAll().size();
        // set the field null
        eleve.setDateNaissance(null);

        // Create the Eleve, which fails.

        restEleveMockMvc.perform(post("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEleves() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get all the eleveList
        restEleveMockMvc.perform(get("/api/eleves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eleve.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricul").value(hasItem(DEFAULT_MATRICUL)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", eleve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eleve.getId().intValue()))
            .andExpect(jsonPath("$.matricul").value(DEFAULT_MATRICUL))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEleve() throws Exception {
        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEleve() throws Exception {
        // Initialize the database
        eleveService.save(eleve);

        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Update the eleve
        Eleve updatedEleve = eleveRepository.findById(eleve.getId()).get();
        // Disconnect from session so that the updates on updatedEleve are not directly saved in db
        em.detach(updatedEleve);
        updatedEleve
            .matricul(UPDATED_MATRICUL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE);

        restEleveMockMvc.perform(put("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEleve)))
            .andExpect(status().isOk());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getMatricul()).isEqualTo(UPDATED_MATRICUL);
        assertThat(testEleve.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEleve.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEleve.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingEleve() throws Exception {
        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Create the Eleve

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEleveMockMvc.perform(put("/api/eleves").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eleve)))
            .andExpect(status().isBadRequest());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEleve() throws Exception {
        // Initialize the database
        eleveService.save(eleve);

        int databaseSizeBeforeDelete = eleveRepository.findAll().size();

        // Delete the eleve
        restEleveMockMvc.perform(delete("/api/eleves/{id}", eleve.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
