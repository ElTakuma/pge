package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.ClasseLt;
import com.codeup.myapp.repository.ClasseLtRepository;
import com.codeup.myapp.service.ClasseLtService;

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
 * Integration tests for the {@link ClasseLtResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ClasseLtResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private ClasseLtRepository classeLtRepository;

    @Autowired
    private ClasseLtService classeLtService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasseLtMockMvc;

    private ClasseLt classeLt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseLt createEntity(EntityManager em) {
        ClasseLt classeLt = new ClasseLt()
            .reference(DEFAULT_REFERENCE);
        return classeLt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseLt createUpdatedEntity(EntityManager em) {
        ClasseLt classeLt = new ClasseLt()
            .reference(UPDATED_REFERENCE);
        return classeLt;
    }

    @BeforeEach
    public void initTest() {
        classeLt = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasseLt() throws Exception {
        int databaseSizeBeforeCreate = classeLtRepository.findAll().size();

        // Create the ClasseLt
        restClasseLtMockMvc.perform(post("/api/classe-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeLt)))
            .andExpect(status().isCreated());

        // Validate the ClasseLt in the database
        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeCreate + 1);
        ClasseLt testClasseLt = classeLtList.get(classeLtList.size() - 1);
        assertThat(testClasseLt.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createClasseLtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeLtRepository.findAll().size();

        // Create the ClasseLt with an existing ID
        classeLt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseLtMockMvc.perform(post("/api/classe-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeLt)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseLt in the database
        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeLtRepository.findAll().size();
        // set the field null
        classeLt.setReference(null);

        // Create the ClasseLt, which fails.

        restClasseLtMockMvc.perform(post("/api/classe-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeLt)))
            .andExpect(status().isBadRequest());

        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasseLts() throws Exception {
        // Initialize the database
        classeLtRepository.saveAndFlush(classeLt);

        // Get all the classeLtList
        restClasseLtMockMvc.perform(get("/api/classe-lts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classeLt.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }
    
    @Test
    @Transactional
    public void getClasseLt() throws Exception {
        // Initialize the database
        classeLtRepository.saveAndFlush(classeLt);

        // Get the classeLt
        restClasseLtMockMvc.perform(get("/api/classe-lts/{id}", classeLt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classeLt.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE));
    }

    @Test
    @Transactional
    public void getNonExistingClasseLt() throws Exception {
        // Get the classeLt
        restClasseLtMockMvc.perform(get("/api/classe-lts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasseLt() throws Exception {
        // Initialize the database
        classeLtService.save(classeLt);

        int databaseSizeBeforeUpdate = classeLtRepository.findAll().size();

        // Update the classeLt
        ClasseLt updatedClasseLt = classeLtRepository.findById(classeLt.getId()).get();
        // Disconnect from session so that the updates on updatedClasseLt are not directly saved in db
        em.detach(updatedClasseLt);
        updatedClasseLt
            .reference(UPDATED_REFERENCE);

        restClasseLtMockMvc.perform(put("/api/classe-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasseLt)))
            .andExpect(status().isOk());

        // Validate the ClasseLt in the database
        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeUpdate);
        ClasseLt testClasseLt = classeLtList.get(classeLtList.size() - 1);
        assertThat(testClasseLt.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingClasseLt() throws Exception {
        int databaseSizeBeforeUpdate = classeLtRepository.findAll().size();

        // Create the ClasseLt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseLtMockMvc.perform(put("/api/classe-lts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeLt)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseLt in the database
        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasseLt() throws Exception {
        // Initialize the database
        classeLtService.save(classeLt);

        int databaseSizeBeforeDelete = classeLtRepository.findAll().size();

        // Delete the classeLt
        restClasseLtMockMvc.perform(delete("/api/classe-lts/{id}", classeLt.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClasseLt> classeLtList = classeLtRepository.findAll();
        assertThat(classeLtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
