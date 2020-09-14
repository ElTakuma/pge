import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMatiereLt } from 'app/shared/model/matiere-lt.model';

type EntityResponseType = HttpResponse<IMatiereLt>;
type EntityArrayResponseType = HttpResponse<IMatiereLt[]>;

@Injectable({ providedIn: 'root' })
export class MatiereLtService {
  public resourceUrl = SERVER_API_URL + 'api/matiere-lts';

  constructor(protected http: HttpClient) {}

  create(matiereLt: IMatiereLt): Observable<EntityResponseType> {
    return this.http.post<IMatiereLt>(this.resourceUrl, matiereLt, { observe: 'response' });
  }

  update(matiereLt: IMatiereLt): Observable<EntityResponseType> {
    return this.http.put<IMatiereLt>(this.resourceUrl, matiereLt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatiereLt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatiereLt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
