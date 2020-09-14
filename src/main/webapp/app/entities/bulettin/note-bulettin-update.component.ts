import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { INote } from 'app/shared/model/note.model';
import { NoteService } from 'app/entities/note/note.service';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-note-bulettin-update-component',
  templateUrl: './note-bulettin-update.component.html'
})
export class NoteBulettinUpdateComponent implements OnInit {
  isSaving = false;
  note!: INote | null;

  constructor(
    protected noteService: NoteService,
    public dialogRef: MatDialogRef<NoteBulettinUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.noteService.find(this.data).subscribe((res: HttpResponse<INote>) => (this.note = res.body));
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  save(): void {
    this.isSaving = true;
    if (this.note) {
      this.subscribeToSaveResponse(this.noteService.update(this.note));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.reload();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  reload(): void {
    window.history.go(0);
  }
}
