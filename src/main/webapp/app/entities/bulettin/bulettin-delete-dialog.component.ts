import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBulettin } from 'app/shared/model/bulettin.model';
import { BulettinService } from './bulettin.service';

@Component({
  templateUrl: './bulettin-delete-dialog.component.html'
})
export class BulettinDeleteDialogComponent {
  bulettin?: IBulettin;

  constructor(protected bulettinService: BulettinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bulettinService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bulettinListModification');
      this.activeModal.close();
    });
  }
}
