import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClasse, Classe } from 'app/shared/model/classe.model';
import { ClasseService } from './classe.service';
import { IClasseLt } from 'app/shared/model/classe-lt.model';
import { ClasseLtService } from 'app/entities/classe-lt/classe-lt.service';
import { IProfesseur } from 'app/shared/model/professeur.model';
import { ProfesseurService } from 'app/entities/professeur/professeur.service';

type SelectableEntity = IClasseLt | IProfesseur;

@Component({
  selector: 'jhi-classe-update',
  templateUrl: './classe-update.component.html'
})
export class ClasseUpdateComponent implements OnInit {
  isSaving = false;
  classelts: IClasseLt[] = [];
  professeurs: IProfesseur[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    effectif: [],
    classeLt: [],
    professeur: [],
    matieres: this.fb.array([])
  });

  constructor(
    protected classeService: ClasseService,
    protected classeLtService: ClasseLtService,
    protected professeurService: ProfesseurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classe }) => {
      this.updateForm(classe);

      this.classeLtService.query().subscribe((res: HttpResponse<IClasseLt[]>) => (this.classelts = res.body || []));

      this.professeurService.query().subscribe((res: HttpResponse<IProfesseur[]>) => (this.professeurs = res.body || []));
    });
  }

  updateForm(classe: IClasse): void {
    this.editForm.patchValue({
      id: classe.id,
      code: classe.code,
      effectif: classe.effectif,
      classeLt: classe.classeLt,
      professeur: classe.professeur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classe = this.createFromForm();
    if (classe.id !== undefined) {
      this.subscribeToSaveResponse(this.classeService.update(classe));
    } else {
      this.subscribeToSaveResponse(this.classeService.create(classe));
    }
  }

  private createFromForm(): IClasse {
    return {
      ...new Classe(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      effectif: this.editForm.get(['effectif'])!.value,
      classeLt: this.editForm.get(['classeLt'])!.value,
      professeur: this.editForm.get(['professeur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasse>>): void {
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
