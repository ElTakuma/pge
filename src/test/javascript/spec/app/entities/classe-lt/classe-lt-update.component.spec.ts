import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { ClasseLtUpdateComponent } from 'app/entities/classe-lt/classe-lt-update.component';
import { ClasseLtService } from 'app/entities/classe-lt/classe-lt.service';
import { ClasseLt } from 'app/shared/model/classe-lt.model';

describe('Component Tests', () => {
  describe('ClasseLt Management Update Component', () => {
    let comp: ClasseLtUpdateComponent;
    let fixture: ComponentFixture<ClasseLtUpdateComponent>;
    let service: ClasseLtService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [ClasseLtUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClasseLtUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClasseLtUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasseLtService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClasseLt(123);
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
        const entity = new ClasseLt();
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
