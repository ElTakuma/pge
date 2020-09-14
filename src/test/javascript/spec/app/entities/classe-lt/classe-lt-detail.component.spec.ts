import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { ClasseLtDetailComponent } from 'app/entities/classe-lt/classe-lt-detail.component';
import { ClasseLt } from 'app/shared/model/classe-lt.model';

describe('Component Tests', () => {
  describe('ClasseLt Management Detail Component', () => {
    let comp: ClasseLtDetailComponent;
    let fixture: ComponentFixture<ClasseLtDetailComponent>;
    const route = ({ data: of({ classeLt: new ClasseLt(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [ClasseLtDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClasseLtDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClasseLtDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classeLt on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classeLt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
