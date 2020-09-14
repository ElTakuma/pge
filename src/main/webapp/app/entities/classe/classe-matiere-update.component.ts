import { Component, Inject, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from './classe.service';
import { ActivatedRoute } from '@angular/router';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { MatiereLtService } from 'app/entities/matiere-lt/matiere-lt.service';
import { ProfesseurService } from 'app/entities/professeur/professeur.service';
import { ClasseLtService } from 'app/entities/classe-lt/classe-lt.service';
import { HttpResponse } from '@angular/common/http';
import { IMatiereLt } from 'app/shared/model/matiere-lt.model';
import { IProfesseur } from 'app/shared/model/professeur.model';
import { IClasseLt } from 'app/shared/model/classe-lt.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

type SelectableEntity = IMatiereLt | IProfesseur | IClasseLt;

@Component({
  selector: 'jhi-classe-matiere-update-component',
  templateUrl: './classe-matiere-update.component.html'
})
export class ClasseMatiereUpdateComponent implements OnInit {
  classe?: IClasse;
  isSaving = false;
  matierelts: IMatiereLt[] = [];
  professeurs: IProfesseur[] = [];
  classelts: IClasseLt[] = [];
  matiere!: IMatiere;

  constructor(
    protected classeService: ClasseService,
    protected eventManager: JhiEventManager,
    protected matiereService: MatiereService,
    protected matiereLtService: MatiereLtService,
    protected professeurService: ProfesseurService,
    protected classeLtService: ClasseLtService,
    protected activatedRoute: ActivatedRoute,
    public dialogRef: MatDialogRef<ClasseMatiereUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: IMatiere
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classe }) => {
      this.classe = classe;
      this.matiereLtService.query().subscribe((res: HttpResponse<IMatiereLt[]>) => (this.matierelts = res.body || []));
      this.professeurService.query().subscribe((res: HttpResponse<IProfesseur[]>) => (this.professeurs = res.body || []));
      this.classeLtService.query().subscribe((res: HttpResponse<IClasseLt[]>) => (this.classelts = res.body || []));
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
