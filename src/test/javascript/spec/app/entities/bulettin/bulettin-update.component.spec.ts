import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { BulettinUpdateComponent } from 'app/entities/bulettin/bulettin-update.component';
import { BulettinService } from 'app/entities/bulettin/bulettin.service';
import { Bulettin } from 'app/shared/model/bulettin.model';

describe('Component Tests', () => {
  describe('Bulettin Management Update Component', () => {
    let comp: BulettinUpdateComponent;
    let fixture: ComponentFixture<BulettinUpdateComponent>;
    let service: BulettinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [BulettinUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BulettinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BulettinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BulettinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bulettin(123);
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
        const entity = new Bulettin();
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
