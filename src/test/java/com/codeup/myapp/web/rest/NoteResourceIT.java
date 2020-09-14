package com.codeup.myapp.web.rest;

import com.codeup.myapp.PgeApp;
import com.codeup.myapp.domain.Note;
import com.codeup.myapp.repository.NoteRepository;
import com.codeup.myapp.service.NoteService;

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
 * Integration tests for the {@link NoteResource} REST controller.
 */
@SpringBootTest(classes = PgeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class NoteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTE_I = 1D;
    private static final Double UPDATED_NOTE_I = 2D;

    private static final Double DEFAULT_NOTE_C = 1D;
    private static final Double UPDATED_NOTE_C = 2D;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoteMockMvc;

    private Note note;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createEntity(EntityManager em) {
        Note note = new Note()
            .code(DEFAULT_CODE)
            .noteI(DEFAULT_NOTE_I)
            .noteC(DEFAULT_NOTE_C)
            .observation(DEFAULT_OBSERVATION);
        return note;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createUpdatedEntity(EntityManager em) {
        Note note = new Note()
            .code(UPDATED_CODE)
            .noteI(UPDATED_NOTE_I)
            .noteC(UPDATED_NOTE_C)
            .observation(UPDATED_OBSERVATION);
        return note;
    }

    @BeforeEach
    public void initTest() {
        note = createEntity(em);
    }

    @Test
    @Transactional
    public void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // Create the Note
        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNote.getNoteI()).isEqualTo(DEFAULT_NOTE_I);
        assertThat(testNote.getNoteC()).isEqualTo(DEFAULT_NOTE_C);
        assertThat(testNote.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    public void createNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // Create the Note with an existing ID
        note.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setCode(null);

        // Create the Note, which fails.

        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteIIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setNoteI(null);

        // Create the Note, which fails.

        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteCIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setNoteC(null);

        // Create the Note, which fails.

        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList
        restNoteMockMvc.perform(get("/api/notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].noteI").value(hasItem(DEFAULT_NOTE_I.intValue())))
            .andExpect(jsonPath("$.[*].noteC").value(hasItem(DEFAULT_NOTE_C.intValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }

    @Test
    @Transactional
    public void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", note.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(note.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.noteI").value(DEFAULT_NOTE_I.intValue()))
            .andExpect(jsonPath("$.noteC").value(DEFAULT_NOTE_C.intValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION));
    }

    @Test
    @Transactional
    public void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNote() throws Exception {
        // Initialize the database
        noteService.save(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        Note updatedNote = noteRepository.findById(note.getId()).get();
        // Disconnect from session so that the updates on updatedNote are not directly saved in db
        em.detach(updatedNote);
        updatedNote
            .code(UPDATED_CODE)
            .noteI(UPDATED_NOTE_I)
            .noteC(UPDATED_NOTE_C)
            .observation(UPDATED_OBSERVATION);

        restNoteMockMvc.perform(put("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNote)))
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNote.getNoteI()).isEqualTo(UPDATED_NOTE_I);
        assertThat(testNote.getNoteC()).isEqualTo(UPDATED_NOTE_C);
        assertThat(testNote.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Create the Note

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc.perform(put("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(note)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNote() throws Exception {
        // Initialize the database
        noteService.save(note);

        int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Delete the note
        restNoteMockMvc.perform(delete("/api/notes/{id}", note.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
