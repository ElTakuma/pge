package com.codeup.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "note_i", nullable = false)
    private Double noteI;

    @NotNull
    @Column(name = "note_c", nullable = false)
    private Double noteC;

    @Column(name = "observation")
    private String observation;

    @ManyToOne
    @JsonIgnoreProperties("notes")
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties("notes")
    private Bulettin bulettin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Note code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getNoteI() {
        return noteI;
    }

    public Note noteI(Double noteI) {
        this.noteI = noteI;
        return this;
    }

    public void setNoteI(Double noteI) {
        this.noteI = noteI;
    }

    public Double getNoteC() {
        return noteC;
    }

    public Note noteC(Double noteC) {
        this.noteC = noteC;
        return this;
    }

    public void setNoteC(Double noteC) {
        this.noteC = noteC;
    }

    public String getObservation() {
        return observation;
    }

    public Note observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Note matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Bulettin getBulettin() {
        return bulettin;
    }

    public Note bulettin(Bulettin bulettin) {
        this.bulettin = bulettin;
        return this;
    }

    public void setBulettin(Bulettin bulettin) {
        this.bulettin = bulettin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", noteI=" + getNoteI() +
            ", noteC=" + getNoteC() +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
