package com.codeup.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.codeup.myapp.domain.enumeration.SessionLt;

import com.codeup.myapp.domain.enumeration.Mentions;

/**
 * A Bulettin.
 */
@Entity
@Table(name = "bulettin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bulettin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_b")
    private SessionLt sessionB;

    @Column(name = "t_coef")
    private Double tCoef;

    @Column(name = "t_note_i")
    private Double tNoteI;

    @Column(name = "moyenne")
    private Double moyenne;

    @Enumerated(EnumType.STRING)
    @Column(name = "mention")
    private Mentions mention;

    @ManyToOne
    @JsonIgnoreProperties("bulettins")
    private Eleve eleve;

    @ManyToOne
    @JsonIgnoreProperties("bulettins")
    private Classe classe;

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

    public Bulettin code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SessionLt getSessionB() {
        return sessionB;
    }

    public Bulettin sessionB(SessionLt sessionB) {
        this.sessionB = sessionB;
        return this;
    }

    public void setSessionB(SessionLt sessionB) {
        this.sessionB = sessionB;
    }

    public Double gettCoef() {
        return tCoef;
    }

    public Bulettin tCoef(Double tCoef) {
        this.tCoef = tCoef;
        return this;
    }

    public void settCoef(Double tCoef) {
        this.tCoef = tCoef;
    }

    public Double gettNoteI() {
        return tNoteI;
    }

    public Bulettin tNoteI(Double tNoteI) {
        this.tNoteI = tNoteI;
        return this;
    }

    public void settNoteI(Double tNoteI) {
        this.tNoteI = tNoteI;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public Bulettin moyenne(Double moyenne) {
        this.moyenne = moyenne;
        return this;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    public Mentions getMention() {
        return mention;
    }

    public Bulettin mention(Mentions mention) {
        this.mention = mention;
        return this;
    }

    public void setMention(Mentions mention) {
        this.mention = mention;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public Bulettin eleve(Eleve eleve) {
        this.eleve = eleve;
        return this;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Classe getClasse() {
        return classe;
    }

    public Bulettin classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bulettin)) {
            return false;
        }
        return id != null && id.equals(((Bulettin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bulettin{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", sessionB='" + getSessionB() + "'" +
            ", tCoef=" + gettCoef() +
            ", tNoteI=" + gettNoteI() +
            ", moyenne=" + getMoyenne() +
            ", mention='" + getMention() + "'" +
            "}";
    }
}
