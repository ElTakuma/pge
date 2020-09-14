import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBulettin, Bulettin } from 'app/shared/model/bulettin.model';
import { BulettinService } from './bulettin.service';
import { IEleve } from 'app/shared/model/eleve.model';
import { EleveService } from 'app/entities/eleve/eleve.service';
import { IClasse } from 'app/shared/model/classe.model';
import { ClasseService } from 'app/entities/classe/classe.service';

type SelectableEntity = IEleve | IClasse;

@Component({
  selector: 'jhi-bulettin-update',
  templateUrl: './bulettin-update.component.html'
})
export class BulettinUpdateComponent implements OnInit {
  isSaving = false;
  eleves: IEleve[] = [];
  classes: IClasse[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    sessionB: [],
    tCoef: [],
    tNoteI: [],
    moyenne: [],
    mention: [],
    eleve: [],
    classe: []
  });

  constructor(
    protected bulettinService: BulettinService,
    protected eleveService: EleveService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bulettin }) => {
      this.updateForm(bulettin);

      this.eleveService.query().subscribe((res: HttpResponse<IEleve[]>) => (this.eleves = res.body || []));

      this.classeService.query().subscribe((res: HttpResponse<IClasse[]>) => (this.classes = res.body || []));
    });
  }

  updateForm(bulettin: IBulettin): void {
    this.editForm.patchValue({
      id: bulettin.id,
      code: bulettin.code,
      sessionB: bulettin.sessionB,
      tCoef: bulettin.tCoef,
      tNoteI: bulettin.tNoteI,
      moyenne: bulettin.moyenne,
      mention: bulettin.mention,
      eleve: bulettin.eleve,
      classe: bulettin.classe
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bulettin = this.createFromForm();
    if (bulettin.id !== undefined) {
      this.subscribeToSaveResponse(this.bulettinService.update(bulettin));
    } else {
      this.subscribeToSaveResponse(this.bulettinService.create(bulettin));
    }
  }

  private createFromForm(): IBulettin {
    return {
      ...new Bulettin(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      sessionB: this.editForm.get(['sessionB'])!.value,
      tCoef: this.editForm.get(['tCoef'])!.value,
      tNoteI: this.editForm.get(['tNoteI'])!.value,
      moyenne: this.editForm.get(['moyenne'])!.value,
      mention: this.editForm.get(['mention'])!.value,
      eleve: this.editForm.get(['eleve'])!.value,
      classe: this.editForm.get(['classe'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBulettin>>): void {
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
