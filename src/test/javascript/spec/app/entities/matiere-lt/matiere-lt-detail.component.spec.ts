import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { MatiereLtDetailComponent } from 'app/entities/matiere-lt/matiere-lt-detail.component';
import { MatiereLt } from 'app/shared/model/matiere-lt.model';

describe('Component Tests', () => {
  describe('MatiereLt Management Detail Component', () => {
    let comp: MatiereLtDetailComponent;
    let fixture: ComponentFixture<MatiereLtDetailComponent>;
    const route = ({ data: of({ matiereLt: new MatiereLt(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [MatiereLtDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MatiereLtDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatiereLtDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matiereLt on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matiereLt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
