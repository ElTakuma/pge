package com.codeup.myapp.service;

import com.codeup.myapp.domain.Bulettin;
import com.codeup.myapp.domain.Classe;
import com.codeup.myapp.domain.Eleve;
import com.codeup.myapp.service.dto.BuletinDTO;

import java.util.List;

public interface BuletinAutoGenerationService {

//    BuletinDTO createOneBulletin(Bulettin bulettin, String lo);
//
//    BuletinDTO createOneBulletinFromEleve(Eleve eleve, int lo);

    List<BuletinDTO> createManyBulletinFromClasse(Classe classe);
}
