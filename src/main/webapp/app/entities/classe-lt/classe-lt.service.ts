import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClasseLt } from 'app/shared/model/classe-lt.model';

type EntityResponseType = HttpResponse<IClasseLt>;
type EntityArrayResponseType = HttpResponse<IClasseLt[]>;

@Injectable({ providedIn: 'root' })
export class ClasseLtService {
  public resourceUrl = SERVER_API_URL + 'api/classe-lts';

  constructor(protected http: HttpClient) {}

  create(classeLt: IClasseLt): Observable<EntityResponseType> {
    return this.http.post<IClasseLt>(this.resourceUrl, classeLt, { observe: 'response' });
  }

  update(classeLt: IClasseLt): Observable<EntityResponseType> {
    return this.http.put<IClasseLt>(this.resourceUrl, classeLt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClasseLt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClasseLt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
