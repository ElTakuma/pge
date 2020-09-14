import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatiereLt } from 'app/shared/model/matiere-lt.model';

@Component({
  selector: 'jhi-matiere-lt-detail',
  templateUrl: './matiere-lt-detail.component.html'
})
export class MatiereLtDetailComponent implements OnInit {
  matiereLt: IMatiereLt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiereLt }) => (this.matiereLt = matiereLt));
  }

  previousState(): void {
    window.history.back();
  }
}
