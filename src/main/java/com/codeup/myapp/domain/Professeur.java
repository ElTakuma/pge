package com.codeup.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Professeur.
 */
@Entity
@Table(name = "professeur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Professeur implements Serializable {

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

    @Column(name = "prenom")
    private String prenom;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

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

    public Professeur matricul(String matricul) {
        this.matricul = matricul;
        return this;
    }

    public void setMatricul(String matricul) {
        this.matricul = matricul;
    }

    public String getNom() {
        return nom;
    }

    public Professeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Professeur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public User getUser() {
        return user;
    }

    public Professeur user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professeur)) {
            return false;
        }
        return id != null && id.equals(((Professeur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Professeur{" +
            "id=" + getId() +
            ", matricul='" + getMatricul() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
