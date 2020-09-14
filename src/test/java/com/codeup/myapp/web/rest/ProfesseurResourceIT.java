package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.Professeur;
import com.codeup.myapp.repository.ProfesseurRepository;
import com.codeup.myapp.service.ProfesseurService;

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
 * Integration tests for the {@link ProfesseurResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProfesseurResourceIT {

    private static final String DEFAULT_MATRICUL = "AAAAAAAAAA";
    private static final String UPDATED_MATRICUL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfesseurMockMvc;

    private Professeur professeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professeur createEntity(EntityManager em) {
        Professeur professeur = new Professeur()
            .matricul(DEFAULT_MATRICUL)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM);
        return professeur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professeur createUpdatedEntity(EntityManager em) {
        Professeur professeur = new Professeur()
            .matricul(UPDATED_MATRICUL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);
        return professeur;
    }

    @BeforeEach
    public void initTest() {
        professeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesseur() throws Exception {
        int databaseSizeBeforeCreate = professeurRepository.findAll().size();

        // Create the Professeur
        restProfesseurMockMvc.perform(post("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professeur)))
            .andExpect(status().isCreated());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeCreate + 1);
        Professeur testProfesseur = professeurList.get(professeurList.size() - 1);
        assertThat(testProfesseur.getMatricul()).isEqualTo(DEFAULT_MATRICUL);
        assertThat(testProfesseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProfesseur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void createProfesseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professeurRepository.findAll().size();

        // Create the Professeur with an existing ID
        professeur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesseurMockMvc.perform(post("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professeur)))
            .andExpect(status().isBadRequest());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatriculIsRequired() throws Exception {
        int databaseSizeBeforeTest = professeurRepository.findAll().size();
        // set the field null
        professeur.setMatricul(null);

        // Create the Professeur, which fails.

        restProfesseurMockMvc.perform(post("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professeur)))
            .andExpect(status().isBadRequest());

        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = professeurRepository.findAll().size();
        // set the field null
        professeur.setNom(null);

        // Create the Professeur, which fails.

        restProfesseurMockMvc.perform(post("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professeur)))
            .andExpect(status().isBadRequest());

        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfesseurs() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);

        // Get all the professeurList
        restProfesseurMockMvc.perform(get("/api/professeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricul").value(hasItem(DEFAULT_MATRICUL)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));
    }
    
    @Test
    @Transactional
    public void getProfesseur() throws Exception {
        // Initialize the database
        professeurRepository.saveAndFlush(professeur);

        // Get the professeur
        restProfesseurMockMvc.perform(get("/api/professeurs/{id}", professeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professeur.getId().intValue()))
            .andExpect(jsonPath("$.matricul").value(DEFAULT_MATRICUL))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM));
    }

    @Test
    @Transactional
    public void getNonExistingProfesseur() throws Exception {
        // Get the professeur
        restProfesseurMockMvc.perform(get("/api/professeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesseur() throws Exception {
        // Initialize the database
        professeurService.save(professeur);

        int databaseSizeBeforeUpdate = professeurRepository.findAll().size();

        // Update the professeur
        Professeur updatedProfesseur = professeurRepository.findById(professeur.getId()).get();
        // Disconnect from session so that the updates on updatedProfesseur are not directly saved in db
        em.detach(updatedProfesseur);
        updatedProfesseur
            .matricul(UPDATED_MATRICUL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);

        restProfesseurMockMvc.perform(put("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfesseur)))
            .andExpect(status().isOk());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeUpdate);
        Professeur testProfesseur = professeurList.get(professeurList.size() - 1);
        assertThat(testProfesseur.getMatricul()).isEqualTo(UPDATED_MATRICUL);
        assertThat(testProfesseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProfesseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesseur() throws Exception {
        int databaseSizeBeforeUpdate = professeurRepository.findAll().size();

        // Create the Professeur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesseurMockMvc.perform(put("/api/professeurs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professeur)))
            .andExpect(status().isBadRequest());

        // Validate the Professeur in the database
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfesseur() throws Exception {
        // Initialize the database
        professeurService.save(professeur);

        int databaseSizeBeforeDelete = professeurRepository.findAll().size();

        // Delete the professeur
        restProfesseurMockMvc.perform(delete("/api/professeurs/{id}", professeur.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Professeur> professeurList = professeurRepository.findAll();
        assertThat(professeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
