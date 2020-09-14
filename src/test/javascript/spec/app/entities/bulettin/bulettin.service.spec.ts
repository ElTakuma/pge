import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BulettinService } from 'app/entities/bulettin/bulettin.service';
import { IBulettin, Bulettin } from 'app/shared/model/bulettin.model';
import { SessionLt } from 'app/shared/model/enumerations/session-lt.model';
import { Mentions } from 'app/shared/model/enumerations/mentions.model';

describe('Service Tests', () => {
  describe('Bulettin Service', () => {
    let injector: TestBed;
    let service: BulettinService;
    let httpMock: HttpTestingController;
    let elemDefault: IBulettin;
    let expectedResult: IBulettin | IBulettin[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BulettinService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Bulettin(0, 'AAAAAAA', SessionLt.Trimestre1, 0, 0, 0, Mentions.Parfait);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bulettin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Bulettin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bulettin', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            sessionB: 'BBBBBB',
            tCoef: 1,
            tNoteI: 1,
            moyenne: 1,
            mention: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bulettin', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            sessionB: 'BBBBBB',
            tCoef: 1,
            tNoteI: 1,
            moyenne: 1,
            mention: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bulettin', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
