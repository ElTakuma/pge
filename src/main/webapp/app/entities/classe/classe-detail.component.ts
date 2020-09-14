import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClasse } from 'app/shared/model/classe.model';
import { MatDialog } from '@angular/material/dialog';
import { ClasseMatiereUpdateComponent } from 'app/entities/classe/classe-matiere-update.component';
import { IMatiere } from 'app/shared/model/matiere.model';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { ClasseService } from 'app/entities/classe/classe.service';

@Component({
  selector: 'jhi-classe-detail',
  templateUrl: './classe-detail.component.html'
})
export class ClasseDetailComponent implements OnInit {
  classe: IClasse | null = null;
  matiere: IMatiere | null = null;
  matieres!: IMatiere[];
  matieres2!: IMatiere[];
  isSaving = false;
  animal!: string;
  name!: string;
  displayedColumns: string[] = ['code', 'matiereLt', 'coeficient'];
  constructor(
    protected activatedRoute: ActivatedRoute,
    protected matiereService: MatiereService,
    protected classeService: ClasseService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classe }) => (this.classe = classe));
    if (this.classe?.matieres) {
      this.matieres = this.classe.matieres;
    }
  }

  reload(): void {
    window.history.go(0);
  }
  previousState(): void {
    window.history.back();
  }

  createBull(): void {
    if (this.classe) {
      this.subscribeToSaveResponse(this.classeService.createBulletins(this.classe));
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ClasseMatiereUpdateComponent, {
      width: '80%',
      data: {
        id: this.matiere?.id,
        code: this.matiere?.code,
        coeficient: this.matiere?.coeficient,
        matiereLt: this.matiere?.matiereLt,
        professeur: this.matiere?.professeur,
        classe: this.classe
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
      this.matiere = result;
      if (this.matiere) {
        this.subscribeToSaveResponse(this.matiereService.create(this.matiere));
      }
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasse>>): void {
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
}
