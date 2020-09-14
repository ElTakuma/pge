package com.codeup.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricul", nullable = false, unique = true)
    private String matricul;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("eleves")
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricul() {
        return matricul;
    }

    public Eleve matricul(String matricul) {
        this.matricul = matricul;
        return this;
    }

    public void setMatricul(String matricul) {
        this.matricul = matricul;
    }

    public String getNom() {
        return nom;
    }

    public Eleve nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Eleve prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Eleve dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public User getUser() {
        return user;
    }

    public Eleve user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classe getClasse() {
        return classe;
    }

    public Eleve classe(Classe classe) {
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
        if (!(o instanceof Eleve)) {
            return false;
        }
        return id != null && id.equals(((Eleve) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", matricul='" + getMatricul() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            "}";
    }
}
