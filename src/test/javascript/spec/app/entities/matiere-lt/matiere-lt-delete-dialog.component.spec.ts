import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MatiereLtDeleteDialogComponent } from 'app/entities/matiere-lt/matiere-lt-delete-dialog.component';
import { MatiereLtService } from 'app/entities/matiere-lt/matiere-lt.service';

describe('Component Tests', () => {
  describe('MatiereLt Management Delete Component', () => {
    let comp: MatiereLtDeleteDialogComponent;
    let fixture: ComponentFixture<MatiereLtDeleteDialogComponent>;
    let service: MatiereLtService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [MatiereLtDeleteDialogComponent]
      })
        .overrideTemplate(MatiereLtDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatiereLtDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatiereLtService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
