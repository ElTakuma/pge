import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatiere, Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { IMatiereLt } from 'app/shared/model/matiere-lt.model';
import { MatiereLtService } from 'app/entities/matiere-lt/matiere-lt.service';
import { IProfesseur } from 'app/shared/model/professeur.model';
import { ProfesseurService } from 'app/entities/professeur/professeur.service';
import { IClasseLt } from 'app/shared/model/classe-lt.model';
import { ClasseLtService } from 'app/entities/classe-lt/classe-lt.service';

type SelectableEntity = IMatiereLt | IProfesseur | IClasseLt;

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html'
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;
  matierelts: IMatiereLt[] = [];
  professeurs: IProfesseur[] = [];
  classes: IClasseLt[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    coeficient: [null, [Validators.required]],
    matiereLt: [],
    professeur: [],
    classe: []
  });

  constructor(
    protected matiereService: MatiereService,
    protected matiereLtService: MatiereLtService,
    protected professeurService: ProfesseurService,
    protected classeLtService: ClasseLtService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.updateForm(matiere);

      this.matiereLtService.query().subscribe((res: HttpResponse<IMatiereLt[]>) => (this.matierelts = res.body || []));

      this.professeurService.query().subscribe((res: HttpResponse<IProfesseur[]>) => (this.professeurs = res.body || []));

      this.classeLtService.query().subscribe((res: HttpResponse<IClasseLt[]>) => (this.classes = res.body || []));
    });
  }

  updateForm(matiere: IMatiere): void {
    this.editForm.patchValue({
      id: matiere.id,
      code: matiere.code,
      coeficient: matiere.coeficient,
      matiereLt: matiere.matiereLt,
      professeur: matiere.professeur,
      classe: matiere.classe
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiere = this.createFromForm();
    if (matiere.id !== undefined) {
      this.subscribeToSaveResponse(this.matiereService.update(matiere));
    } else {
      this.subscribeToSaveResponse(this.matiereService.create(matiere));
    }
  }

  private createFromForm(): IMatiere {
    return {
      ...new Matiere(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      coeficient: this.editForm.get(['coeficient'])!.value,
      matiereLt: this.editForm.get(['matiereLt'])!.value,
      professeur: this.editForm.get(['professeur'])!.value,
      classe: this.editForm.get(['classe'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
