import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BulettinDeleteDialogComponent } from 'app/entities/bulettin/bulettin-delete-dialog.component';
import { BulettinService } from 'app/entities/bulettin/bulettin.service';

describe('Component Tests', () => {
  describe('Bulettin Management Delete Component', () => {
    let comp: BulettinDeleteDialogComponent;
    let fixture: ComponentFixture<BulettinDeleteDialogComponent>;
    let service: BulettinService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [BulettinDeleteDialogComponent]
      })
        .overrideTemplate(BulettinDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BulettinDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BulettinService);
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
