import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgeTestModule } from '../../../test.module';
import { BulettinDetailComponent } from 'app/entities/bulettin/bulettin-detail.component';
import { Bulettin } from 'app/shared/model/bulettin.model';

describe('Component Tests', () => {
  describe('Bulettin Management Detail Component', () => {
    let comp: BulettinDetailComponent;
    let fixture: ComponentFixture<BulettinDetailComponent>;
    const route = ({ data: of({ bulettin: new Bulettin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgeTestModule],
        declarations: [BulettinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BulettinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BulettinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bulettin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bulettin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
