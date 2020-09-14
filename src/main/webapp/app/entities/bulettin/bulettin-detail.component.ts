import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBulettin } from 'app/shared/model/bulettin.model';
import { INote } from 'app/shared/model/note.model';
import { MatDialog } from '@angular/material/dialog';
import { NoteBulettinUpdateComponent } from 'app/entities/bulettin/note-bulettin-update.component';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { NoteService } from 'app/entities/note/note.service';

@Component({
  selector: 'jhi-bulettin-detail',
  templateUrl: './bulettin-detail.component.html'
})
export class BulettinDetailComponent implements OnInit {
  bulettin: IBulettin | null = null;
  displayedColumns: string[] = ['matiere', 'noteI', 'coef', 'noteC', 'observation', 'code'];
  notes!: INote[];
  note!: INote;
  isSaving = false;
  constructor(protected activatedRoute: ActivatedRoute, protected noteService: NoteService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bulettin }) => (this.bulettin = bulettin));
    if (this.bulettin?.notes) {
      this.notes = this.bulettin.notes;
    }
  }

  editNote(id: number | undefined): void {
    const dialogRef = this.dialog.open(NoteBulettinUpdateComponent, {
      width: '65%',
      data: id
    });
    dialogRef.afterClosed().subscribe(result => {
      this.note = result;
      // this.isSaving = true;
      // if (this.note) {
      //   if (this.note.id !== undefined) {
      //     this.subscribeToSaveResponse(this.noteService.update(note));
      //   } else {
      //     this.subscribeToSaveResponse(this.noteService.create(note));
      //   }
      // }
    });
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
  previousState(): void {
    window.history.back();
  }
}
