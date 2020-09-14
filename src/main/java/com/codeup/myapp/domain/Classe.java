package com.codeup.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Classe.
 */
@Entity
@Table(name = "classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "effectif")
    private Long effectif;

    @ManyToOne
    @JsonIgnoreProperties("classes")
    private ClasseLt classeLt;

    @ManyToOne
    @JsonIgnoreProperties("classes")
    private Professeur professeur;

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

    public Classe code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getEffectif() {
        return effectif;
    }

    public Classe effectif(Long effectif) {
        this.effectif = effectif;
        return this;
    }

    public void setEffectif(Long effectif) {
        this.effectif = effectif;
    }

    public ClasseLt getClasseLt() {
        return classeLt;
    }

    public Classe classeLt(ClasseLt classeLt) {
        this.classeLt = classeLt;
        return this;
    }

    public void setClasseLt(ClasseLt classeLt) {
        this.classeLt = classeLt;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public Classe professeur(Professeur professeur) {
        this.professeur = professeur;
        return this;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", effectif=" + getEffectif() +
            "}";
    }
}
