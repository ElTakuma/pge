package com.codeup.myapp.service.dto;

import com.codeup.myapp.domain.*;
import com.codeup.myapp.domain.enumeration.Mentions;
import com.codeup.myapp.domain.enumeration.SessionLt;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BuletinDTO implements Serializable {

    private Long id;
    @NotNull
    private String code;
    private SessionLt sessionB;
    private Double tCoef;
    private Double tNoteI;
    private Double moyenne;
    private Mentions mention;
    private List<Note> notes;
    private List<Matiere> matieres;
    private Eleve eleve;
    private Classe classe;

    public BuletinDTO(){}

    public Bulettin getBuletin() {
        Bulettin result = new Bulettin();
        if (id != null) {
            result.setId(this.getId());
        }
        result.setCode(code);
        result.setSessionB(sessionB);
        result.settCoef(tCoef);
        result.settNoteI(tNoteI);
        result.setMoyenne(moyenne);
        result.setMention(this.getMention());
        result.setClasse(this.getClasse());
        result.setEleve(this.getEleve());
        return result;
    }

    public void setBuletin(Bulettin buletin){
        this.setId(buletin.getId());
        this.setCode(buletin.getCode());
        this.setSessionB(buletin.getSessionB());
        this.setTCoef(buletin.gettCoef());
        this.setTNoteI(buletin.gettNoteI());
        this.setMoyenne(buletin.getMoyenne());
        this.setMention(buletin.getMention());
        this.setClasse(buletin.getClasse());
        this.setEleve(buletin.getEleve());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public SessionLt getSessionB() { return sessionB; }
    public void setSessionB(SessionLt sessionB) { this.sessionB = sessionB; }

    public Double getTCoef() { return tCoef; }
    public void setTCoef(Double tCoef) { this.tCoef = tCoef; }

    public Double getTNoteI() { return tNoteI; }
    public void setTNoteI(Double tNoteI) { this.tNoteI = tNoteI; }

    public Double getMoyenne() { return moyenne; }
    public void setMoyenne(Double moyenne) { this.moyenne = moyenne; }

    public Mentions getMention() { return mention; }
    public void setMention(Mentions mention) { this.mention = mention; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public List<Matiere> getMatieres() { return matieres; }
    public void setMatieres(List<Matiere> matieres) { this.matieres = matieres; }

    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; }

    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuletinDTO buletinDTO = (BuletinDTO) o;
        if (buletinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buletinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BulettinDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", sessionB='" + getSessionB() + "'" +
            ", tCoef=" + getTCoef() +
            ", tNoteI=" + getTNoteI() +
            ", moyenne=" + getMoyenne() +
            ", mention='" + getMention() + "'" +
            "}";
    }
}
