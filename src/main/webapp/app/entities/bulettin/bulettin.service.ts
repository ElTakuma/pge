import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBulettin } from 'app/shared/model/bulettin.model';

type EntityResponseType = HttpResponse<IBulettin>;
type EntityArrayResponseType = HttpResponse<IBulettin[]>;

@Injectable({ providedIn: 'root' })
export class BulettinService {
  public resourceUrl = SERVER_API_URL + 'api/bulettins';
  public resourceUrlDto = SERVER_API_URL + 'api/bulettins-dto';

  constructor(protected http: HttpClient) {}

  create(bulettin: IBulettin): Observable<EntityResponseType> {
    return this.http.post<IBulettin>(this.resourceUrl, bulettin, { observe: 'response' });
  }

  update(bulettin: IBulettin): Observable<EntityResponseType> {
    return this.http.put<IBulettin>(this.resourceUrl, bulettin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBulettin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findComplet(id: number): Observable<EntityResponseType> {
    return this.http.get<IBulettin>(`${this.resourceUrlDto}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBulettin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
