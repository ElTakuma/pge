import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INote, Note } from 'app/shared/model/note.model';
import { NoteService } from './note.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { IBulettin } from 'app/shared/model/bulettin.model';
import { BulettinService } from 'app/entities/bulettin/bulettin.service';

type SelectableEntity = IMatiere | IBulettin;

@Component({
  selector: 'jhi-note-update',
  templateUrl: './note-update.component.html'
})
export class NoteUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  bulettins: IBulettin[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    noteI: [null, [Validators.required]],
    noteC: [null, [Validators.required]],
    observation: [],
    matiere: [],
    bulettin: []
  });

  constructor(
    protected noteService: NoteService,
    protected matiereService: MatiereService,
    protected bulettinService: BulettinService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.updateForm(note);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.bulettinService.query().subscribe((res: HttpResponse<IBulettin[]>) => (this.bulettins = res.body || []));
    });
  }

  updateForm(note: INote): void {
    this.editForm.patchValue({
      id: note.id,
      code: note.code,
      noteI: note.noteI,
      noteC: note.noteC,
      observation: note.observation,
      matiere: note.matiere,
      bulettin: note.bulettin
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.createFromForm();
    if (note.id !== undefined) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  private createFromForm(): INote {
    return {
      ...new Note(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      noteI: this.editForm.get(['noteI'])!.value,
      noteC: this.editForm.get(['noteC'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      matiere: this.editForm.get(['matiere'])!.value,
      bulettin: this.editForm.get(['bulettin'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
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
