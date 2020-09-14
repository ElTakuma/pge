import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClasseLt } from 'app/shared/model/classe-lt.model';

@Component({
  selector: 'jhi-classe-lt-detail',
  templateUrl: './classe-lt-detail.component.html'
})
export class ClasseLtDetailComponent implements OnInit {
  classeLt: IClasseLt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classeLt }) => (this.classeLt = classeLt));
  }

  previousState(): void {
    window.history.back();
  }
}
