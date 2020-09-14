import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClasseLt } from 'app/shared/model/classe-lt.model';
import { ClasseLtService } from './classe-lt.service';

@Component({
  templateUrl: './classe-lt-delete-dialog.component.html'
})
export class ClasseLtDeleteDialogComponent {
  classeLt?: IClasseLt;

  constructor(protected classeLtService: ClasseLtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classeLtService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classeLtListModification');
      this.activeModal.close();
    });
  }
}
