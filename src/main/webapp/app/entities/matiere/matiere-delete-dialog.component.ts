import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';

@Component({
  templateUrl: './matiere-delete-dialog.component.html'
})
export class MatiereDeleteDialogComponent {
  matiere?: IMatiere;

  constructor(protected matiereService: MatiereService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matiereService.delete(id).subscribe(() => {
      this.eventManager.broadcast('matiereListModification');
      this.activeModal.close();
    });
  }
}
