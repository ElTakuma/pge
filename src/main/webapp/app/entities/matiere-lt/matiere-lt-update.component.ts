import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatiereLt, MatiereLt } from 'app/shared/model/matiere-lt.model';
import { MatiereLtService } from './matiere-lt.service';

@Component({
  selector: 'jhi-matiere-lt-update',
  templateUrl: './matiere-lt-update.component.html'
})
export class MatiereLtUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.required]]
  });

  constructor(protected matiereLtService: MatiereLtService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiereLt }) => {
      this.updateForm(matiereLt);
    });
  }

  updateForm(matiereLt: IMatiereLt): void {
    this.editForm.patchValue({
      id: matiereLt.id,
      reference: matiereLt.reference
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiereLt = this.createFromForm();
    if (matiereLt.id !== undefined) {
      this.subscribeToSaveResponse(this.matiereLtService.update(matiereLt));
    } else {
      this.subscribeToSaveResponse(this.matiereLtService.create(matiereLt));
    }
  }

  private createFromForm(): IMatiereLt {
    return {
      ...new MatiereLt(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiereLt>>): void {
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
}
