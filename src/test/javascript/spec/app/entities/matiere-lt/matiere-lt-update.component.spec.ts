import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { MatiereLtUpdateComponent } from 'app/entities/matiere-lt/matiere-lt-update.component';
import { MatiereLtService } from 'app/entities/matiere-lt/matiere-lt.service';
import { MatiereLt } from 'app/shared/model/matiere-lt.model';

describe('Component Tests', () => {
  describe('MatiereLt Management Update Component', () => {
    let comp: MatiereLtUpdateComponent;
    let fixture: ComponentFixture<MatiereLtUpdateComponent>;
    let service: MatiereLtService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [MatiereLtUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MatiereLtUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatiereLtUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatiereLtService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatiereLt(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatiereLt();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
