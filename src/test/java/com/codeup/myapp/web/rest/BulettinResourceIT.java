package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.Bulettin;
import com.codeup.myapp.repository.BulettinRepository;
import com.codeup.myapp.service.BulettinService;

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

import com.codeup.myapp.domain.enumeration.SessionLt;
import com.codeup.myapp.domain.enumeration.Mentions;
/**
 * Integration tests for the {@link BulettinResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BulettinResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final SessionLt DEFAULT_SESSION_B = SessionLt.Trimestre1;
    private static final SessionLt UPDATED_SESSION_B = SessionLt.Trimestre2;

    private static final Double DEFAULT_T_COEF = 1D;
    private static final Double UPDATED_T_COEF = 2D;

    private static final Double DEFAULT_T_NOTE_I = 1D;
    private static final Double UPDATED_T_NOTE_I = 2D;

    private static final Double DEFAULT_MOYENNE = 1D;
    private static final Double UPDATED_MOYENNE = 2D;

    private static final Mentions DEFAULT_MENTION = Mentions.Parfait;
    private static final Mentions UPDATED_MENTION = Mentions.Excellent;

    @Autowired
    private BulettinRepository bulettinRepository;

    @Autowired
    private BulettinService bulettinService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBulettinMockMvc;

    private Bulettin bulettin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulettin createEntity(EntityManager em) {
        Bulettin bulettin = new Bulettin()
            .code(DEFAULT_CODE)
            .sessionB(DEFAULT_SESSION_B)
            .tCoef(DEFAULT_T_COEF)
            .tNoteI(DEFAULT_T_NOTE_I)
            .moyenne(DEFAULT_MOYENNE)
            .mention(DEFAULT_MENTION);
        return bulettin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bulettin createUpdatedEntity(EntityManager em) {
        Bulettin bulettin = new Bulettin()
            .code(UPDATED_CODE)
            .sessionB(UPDATED_SESSION_B)
            .tCoef(UPDATED_T_COEF)
            .tNoteI(UPDATED_T_NOTE_I)
            .moyenne(UPDATED_MOYENNE)
            .mention(UPDATED_MENTION);
        return bulettin;
    }

    @BeforeEach
    public void initTest() {
        bulettin = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulettin() throws Exception {
        int databaseSizeBeforeCreate = bulettinRepository.findAll().size();

        // Create the Bulettin
        restBulettinMockMvc.perform(post("/api/bulettins").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulettin)))
            .andExpect(status().isCreated());

        // Validate the Bulettin in the database
        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeCreate + 1);
        Bulettin testBulettin = bulettinList.get(bulettinList.size() - 1);
        assertThat(testBulettin.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBulettin.getSessionB()).isEqualTo(DEFAULT_SESSION_B);
        assertThat(testBulettin.gettCoef()).isEqualTo(DEFAULT_T_COEF);
        assertThat(testBulettin.gettNoteI()).isEqualTo(DEFAULT_T_NOTE_I);
        assertThat(testBulettin.getMoyenne()).isEqualTo(DEFAULT_MOYENNE);
        assertThat(testBulettin.getMention()).isEqualTo(DEFAULT_MENTION);
    }

    @Test
    @Transactional
    public void createBulettinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulettinRepository.findAll().size();

        // Create the Bulettin with an existing ID
        bulettin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulettinMockMvc.perform(post("/api/bulettins").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulettin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulettin in the database
        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bulettinRepository.findAll().size();
        // set the field null
        bulettin.setCode(null);

        // Create the Bulettin, which fails.

        restBulettinMockMvc.perform(post("/api/bulettins").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulettin)))
            .andExpect(status().isBadRequest());

        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBulettins() throws Exception {
        // Initialize the database
        bulettinRepository.saveAndFlush(bulettin);

        // Get all the bulettinList
        restBulettinMockMvc.perform(get("/api/bulettins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulettin.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].sessionB").value(hasItem(DEFAULT_SESSION_B.toString())))
            .andExpect(jsonPath("$.[*].tCoef").value(hasItem(DEFAULT_T_COEF.intValue())))
            .andExpect(jsonPath("$.[*].tNoteI").value(hasItem(DEFAULT_T_NOTE_I.intValue())))
            .andExpect(jsonPath("$.[*].moyenne").value(hasItem(DEFAULT_MOYENNE.intValue())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())));
    }

    @Test
    @Transactional
    public void getBulettin() throws Exception {
        // Initialize the database
        bulettinRepository.saveAndFlush(bulettin);

        // Get the bulettin
        restBulettinMockMvc.perform(get("/api/bulettins/{id}", bulettin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bulettin.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.sessionB").value(DEFAULT_SESSION_B.toString()))
            .andExpect(jsonPath("$.tCoef").value(DEFAULT_T_COEF.intValue()))
            .andExpect(jsonPath("$.tNoteI").value(DEFAULT_T_NOTE_I.intValue()))
            .andExpect(jsonPath("$.moyenne").value(DEFAULT_MOYENNE.intValue()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBulettin() throws Exception {
        // Get the bulettin
        restBulettinMockMvc.perform(get("/api/bulettins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulettin() throws Exception {
        // Initialize the database
        bulettinService.save(bulettin);

        int databaseSizeBeforeUpdate = bulettinRepository.findAll().size();

        // Update the bulettin
        Bulettin updatedBulettin = bulettinRepository.findById(bulettin.getId()).get();
        // Disconnect from session so that the updates on updatedBulettin are not directly saved in db
        em.detach(updatedBulettin);
        updatedBulettin
            .code(UPDATED_CODE)
            .sessionB(UPDATED_SESSION_B)
            .tCoef(UPDATED_T_COEF)
            .tNoteI(UPDATED_T_NOTE_I)
            .moyenne(UPDATED_MOYENNE)
            .mention(UPDATED_MENTION);

        restBulettinMockMvc.perform(put("/api/bulettins").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulettin)))
            .andExpect(status().isOk());

        // Validate the Bulettin in the database
        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeUpdate);
        Bulettin testBulettin = bulettinList.get(bulettinList.size() - 1);
        assertThat(testBulettin.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBulettin.getSessionB()).isEqualTo(UPDATED_SESSION_B);
        assertThat(testBulettin.gettCoef()).isEqualTo(UPDATED_T_COEF);
        assertThat(testBulettin.gettNoteI()).isEqualTo(UPDATED_T_NOTE_I);
        assertThat(testBulettin.getMoyenne()).isEqualTo(UPDATED_MOYENNE);
        assertThat(testBulettin.getMention()).isEqualTo(UPDATED_MENTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBulettin() throws Exception {
        int databaseSizeBeforeUpdate = bulettinRepository.findAll().size();

        // Create the Bulettin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulettinMockMvc.perform(put("/api/bulettins").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bulettin)))
            .andExpect(status().isBadRequest());

        // Validate the Bulettin in the database
        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulettin() throws Exception {
        // Initialize the database
        bulettinService.save(bulettin);

        int databaseSizeBeforeDelete = bulettinRepository.findAll().size();

        // Delete the bulettin
        restBulettinMockMvc.perform(delete("/api/bulettins/{id}", bulettin.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bulettin> bulettinList = bulettinRepository.findAll();
        assertThat(bulettinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
