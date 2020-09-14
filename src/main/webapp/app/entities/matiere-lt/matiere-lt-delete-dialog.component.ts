import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatiereLt } from 'app/shared/model/matiere-lt.model';
import { MatiereLtService } from './matiere-lt.service';

@Component({
  templateUrl: './matiere-lt-delete-dialog.component.html'
})
export class MatiereLtDeleteDialogComponent {
  matiereLt?: IMatiereLt;

  constructor(protected matiereLtService: MatiereLtService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matiereLtService.delete(id).subscribe(() => {
      this.eventManager.broadcast('matiereLtListModification');
      this.activeModal.close();
    });
  }
}
