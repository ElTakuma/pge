package com.codeup.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "coeficient", nullable = false)
    private Long coeficient;

    @ManyToOne
    @JsonIgnoreProperties("matieres")
    private MatiereLt matiereLt;

    @ManyToOne
    @JsonIgnoreProperties("matieres")
    private Professeur professeur;

    @ManyToOne
    @JsonIgnoreProperties("matieres")
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

    public Matiere code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCoeficient() {
        return coeficient;
    }

    public Matiere coeficient(Long coeficient) {
        this.coeficient = coeficient;
        return this;
    }

    public void setCoeficient(Long coeficient) {
        this.coeficient = coeficient;
    }

    public MatiereLt getMatiereLt() {
        return matiereLt;
    }

    public Matiere matiereLt(MatiereLt matiereLt) {
        this.matiereLt = matiereLt;
        return this;
    }

    public void setMatiereLt(MatiereLt matiereLt) {
        this.matiereLt = matiereLt;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public Matiere professeur(Professeur professeur) {
        this.professeur = professeur;
        return this;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Classe getClasse() {
        return classe;
    }

    public Matiere classeLt(Classe classe) {
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
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", coeficient=" + getCoeficient() +
            "}";
    }
}
