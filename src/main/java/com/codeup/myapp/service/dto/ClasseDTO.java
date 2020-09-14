package com.codeup.myapp.service.dto;

import com.codeup.myapp.domain.Classe;
import com.codeup.myapp.domain.ClasseLt;
import com.codeup.myapp.domain.Matiere;
import com.codeup.myapp.domain.Professeur;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ClasseDTO implements Serializable {
    private Long id;
    private String code;
    private Long effectif;
    private ClasseLt classeLt;
    private Professeur professeur;
    private List<Matiere> matieres;

    public ClasseDTO() {}

    public Classe getClasse() {
        Classe result = new Classe();
        if (id!=null) {
            result.setId(this.getId());
        }
        result.setCode(code);
        result.setEffectif(effectif);
        result.setClasseLt(this.getClasseLt());
        result.setProfesseur(this.getProfesseur());
        return result;
    }
    public void setClasse(Classe classe) {
        this.setId(classe.getId());
        this.setCode(classe.getCode());
        this.setClasseLt(classe.getClasseLt());
        this.setProfesseur(classe.getProfesseur());
    }

    public Long getId() {
        return id; }
    public void setId(Long id) {
        this.id = id; }

    public String getCode() {
        return code; }
    public void setCode(String code) {
        this.code = code; }

    public Long getEffectif() {
        return effectif; }
    public void setEffectif(Long effectif) {
        this.effectif = effectif; }

    public ClasseLt getClasseLt() {
        return classeLt; }
    public void setClasseLt(ClasseLt classeLt) {
        this.classeLt = classeLt; }

    public Professeur getProfesseur() {
        return professeur; }
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur; }

    public List<Matiere> getMatieres() {
        return matieres; }
    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClasseDTO classeDTO = (ClasseDTO) o;
        if (classeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", effectif='" + getEffectif() + "'" +
            ", professeur='" + getProfesseur() + "'" +
            ", classeLt=" + getClasseLt() +
            "}";
    }
}



