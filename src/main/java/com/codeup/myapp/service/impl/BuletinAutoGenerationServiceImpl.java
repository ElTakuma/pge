package com.codeup.myapp.service.impl;
import com.codeup.myapp.domain.*;
import com.codeup.myapp.domain.enumeration.Mentions;
import com.codeup.myapp.domain.enumeration.SessionLt;
import com.codeup.myapp.repository.BulettinRepository;
import com.codeup.myapp.repository.EleveRepository;
import com.codeup.myapp.repository.MatiereRepository;
import com.codeup.myapp.repository.NoteRepository;
import com.codeup.myapp.service.BuletinAutoGenerationService;
import com.codeup.myapp.service.dto.BuletinDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BuletinAutoGenerationServiceImpl implements BuletinAutoGenerationService {

    private final Logger log = LoggerFactory.getLogger(BuletinAutoGenerationServiceImpl.class);
    final private MatiereRepository matiereRepository;
    final private BulettinRepository bulettinRepository;
    final private NoteRepository noteRepository;
    final private EleveRepository eleveRepository;

    public BuletinAutoGenerationServiceImpl(
        MatiereRepository matiereRepository,
        BulettinRepository bulettinRepository,
        NoteRepository noteRepository,
        EleveRepository eleveRepository
    ) {
        this.matiereRepository = matiereRepository;
        this.bulettinRepository = bulettinRepository;
        this.noteRepository = noteRepository;
        this.eleveRepository = eleveRepository;
    }

    public BuletinDTO createOneBulletin(Bulettin bulettin, String lo){
        System.out.println("DEBUT DE LA GENERATION DES BULLETION VIA LA CLASSE");
        log.debug("Request to save Bulettin : {}", bulettin);

        bulettin = bulettinRepository.save(bulettin);  // Enregistrement du bulletin
        System.out.println("TEST OF THE Bulletin ID ==> " + bulettin.getId());
        BuletinDTO buletinDTO = new BuletinDTO();
        buletinDTO.setBuletin(bulettin);

        List<Matiere> matieres = matiereRepository.matiereOfClasse(bulettin.getClasse().getId()); // recuperation de la liste des matieres de la classe concerner
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        int nbrMatiere = matieres.size();
        String nbrNote = "" + noteRepository.nombreNote();
        String zeroBeforeNote = "";
        Double totalNoteCoef = 0D;

        log.debug("Request to generate note of Bulletin : {}", bulettin.getId());
        int j = 7 - nbrNote.length();
        for (int i = 0; i < j; i++) {
            zeroBeforeNote = zeroBeforeNote + "0";
        }
        for (int i=0; i < nbrMatiere; i++) {
            note = new Note();
            note.setBulettin(bulettin);
            note.setCode("NT-" + (i+1) + "_" + lo);
            note.setMatiere(matieres.get(i));
            note.setNoteI(0d);
            note.setNoteC(note.getNoteI() * matieres.get(i).getCoeficient());
            note.setObservation("****");
            notes.add(noteRepository.save(note));
            //calcul du total des coeficients
            System.out.println("TEST de la valeur de note au tour "+ i + " ==> " + note);
//            totalCoef += matieres.get(i).getCoeficient();
        }
        System.out.println("TEST de la valeur de notes ==> " + notes);
        buletinDTO.setTCoef(totalNoteCoef);
        buletinDTO.setNotes(notes);
        buletinDTO.setMatieres(matieres);
        buletinDTO.setTNoteI(0D);
        buletinDTO.setMoyenne(0D);
        System.out.println("TEST de la valeur de buletinDTO ==> " + buletinDTO);
        bulettinRepository.save(buletinDTO.getBuletin());  // mise a jour du bulletin

        return buletinDTO;
    }

    public BuletinDTO updateBulettin(Bulettin bulettin) {
        System.out.println("**************************************************");
        System.out.println("DEBUT DE LA GENERATION DES BULLETION VIA LA CLASSE");
        log.debug("Request to update Bulettin : {}", bulettin);
        BuletinDTO buletinDTO = new BuletinDTO();
        List<Note> notes = noteRepository.noteOfBulletin(bulettin.getId());
        Long tC = 0L;
        Double tNI = 0d;
        Double tNC = 0d;
        Double moyenne;

        for (int i=0; i< notes.size(); i++) {
            tC += notes.get(i).getMatiere().getCoeficient();
            tNI += notes.get(i).getNoteI();
            tNC += notes.get(i).getNoteC();
        }
        bulettin.settCoef(tNC);
        System.out.println("TEST de la valeur de tNC ==> " + tNC);
        bulettin.settNoteI(tNI);
        System.out.println("TEST de la valeur de tNI ==> " + tNI);
        moyenne = tNC / tC;
        bulettin.setMoyenne(moyenne);
        if (moyenne >= 0 && moyenne < 5) { bulettin.setMention(Mentions.Null); }
        if (moyenne >= 5 && moyenne < 8) { bulettin.setMention(Mentions.Mauvais); }
        if (moyenne >= 8 && moyenne < 10) { bulettin.setMention(Mentions.Mediocre); }
        if (moyenne >= 10 && moyenne < 12) { bulettin.setMention(Mentions.Passable); }
        if (moyenne >= 12 && moyenne < 14) { bulettin.setMention(Mentions.AssezBien); }
        if (moyenne >= 14 && moyenne < 16) { bulettin.setMention(Mentions.Bien); }
        if (moyenne >= 16 && moyenne < 18) { bulettin.setMention(Mentions.Excellent); }
        if (moyenne >= 18 && moyenne < 20) { bulettin.setMention(Mentions.Parfait); }
        System.out.println("**************************************************");
        System.out.println("TEST de la valeur de buletin before==> " + bulettin);
        bulettin = bulettinRepository.save(bulettin);
        System.out.println("**************************************************");
        System.out.println("TEST de la valeur de buletin ==> " + bulettin);
        buletinDTO.setNotes(notes);
        buletinDTO.setBuletin(bulettin);
        return buletinDTO;
    }


    public BuletinDTO createOneBulletinFromEleve(Eleve eleve){
        String nbrBulettin = "";
        int nbrB = 0;
        String zeroBefore = "";
        Bulettin bulettin = new Bulettin();
        bulettin.setEleve(eleve);
        bulettin.setClasse(eleve.getClasse());
        SessionLt s = SessionLt.Sequence1;
        //Checking Session : succession de If pour determiner la session du bulletin
        if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
            s = SessionLt.Sequence2;
            if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                s = SessionLt.Trimestre1;
                if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                    s = SessionLt.Sequence3;
                    if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                        s = SessionLt.Sequence4;
                        if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                            s = SessionLt.Trimestre2;
                            if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                                s = SessionLt.Sequence5;
                                if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                                    s = SessionLt.Sequence6;
                                    if (bulettinRepository.nombreBulletinDeSession(s, bulettin.getClasse().getId(), eleve.getId()) != 0) {
                                        s = SessionLt.Trimestre3;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        bulettin.setSessionB(s);
        nbrB = bulettinRepository.nombreBulletin(); // ajouter a argument dans la requete pour compter par annee
        nbrBulettin += nbrB;
        int j = 5 - nbrBulettin.length();
        for (int i = 0; i < j; i++) {
            zeroBefore = zeroBefore + "0";
        }

        String prefixCode = "BLT-"+zeroBefore + (nbrB+1);
        bulettin.setCode(prefixCode + "_" + LocalDate.now().getYear());
        return createOneBulletin(bulettin, prefixCode);
    }

    /**
     *
     * @param classe
     * @return
     */
    @Override
    public List<BuletinDTO> createManyBulletinFromClasse(Classe classe){
        List<Eleve> eleves = eleveRepository.eleveOfClasse(classe.getId());
        System.out.println("TEST de la valeur de eleves ==> " + eleves);
        List<BuletinDTO> buletinDTOS = new ArrayList<>();
        int nbreEleve = eleves.size();
        for (int i=0; i < nbreEleve; i++) {
            log.debug("eleve N* : {}", i);
            buletinDTOS.add(createOneBulletinFromEleve(eleves.get(i)));
        }
        return buletinDTOS;
    }
}
