import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMatiere } from 'app/shared/model/matiere.model';

type EntityResponseType = HttpResponse<IMatiere>;
type EntityArrayResponseType = HttpResponse<IMatiere[]>;

@Injectable({ providedIn: 'root' })
export class MatiereService {
  public resourceUrl = SERVER_API_URL + 'api/matieres';
  public resourceUrlBeta = SERVER_API_URL + 'api/matieres-beta';

  constructor(protected http: HttpClient) {}

  create(matiere: IMatiere): Observable<EntityResponseType> {
    return this.http.post<IMatiere>(this.resourceUrl, matiere, { observe: 'response' });
  }
  createBeta(matiere: IMatiere): Observable<EntityResponseType> {
    return this.http.post<IMatiere>(this.resourceUrlBeta, matiere, { observe: 'response' });
  }

  update(matiere: IMatiere): Observable<EntityResponseType> {
    return this.http.put<IMatiere>(this.resourceUrl, matiere, { observe: 'response' });
  }
  updateBeta(matiere: IMatiere): Observable<EntityResponseType> {
    return this.http.put<IMatiere>(this.resourceUrlBeta, matiere, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatiere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatiere[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
