import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClasseLt, ClasseLt } from 'app/shared/model/classe-lt.model';
import { ClasseLtService } from './classe-lt.service';

@Component({
  selector: 'jhi-classe-lt-update',
  templateUrl: './classe-lt-update.component.html'
})
export class ClasseLtUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.required]]
  });

  constructor(protected classeLtService: ClasseLtService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classeLt }) => {
      this.updateForm(classeLt);
    });
  }

  updateForm(classeLt: IClasseLt): void {
    this.editForm.patchValue({
      id: classeLt.id,
      reference: classeLt.reference
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classeLt = this.createFromForm();
    if (classeLt.id !== undefined) {
      this.subscribeToSaveResponse(this.classeLtService.update(classeLt));
    } else {
      this.subscribeToSaveResponse(this.classeLtService.create(classeLt));
    }
  }

  private createFromForm(): IClasseLt {
    return {
      ...new ClasseLt(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasseLt>>): void {
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
