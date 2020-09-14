import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatiere, Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { MatiereComponent } from './matiere.component';
import { MatiereDetailComponent } from './matiere-detail.component';
import { MatiereUpdateComponent } from './matiere-update.component';

@Injectable({ providedIn: 'root' })
export class MatiereResolve implements Resolve<IMatiere> {
  constructor(private service: MatiereService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatiere> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matiere: HttpResponse<Matiere>) => {
          if (matiere.body) {
            return of(matiere.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Matiere());
  }
}

export const matiereRoute: Routes = [
  {
    path: '',
    component: MatiereComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pgeApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatiereDetailComponent,
    resolve: {
      matiere: MatiereResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatiereUpdateComponent,
    resolve: {
      matiere: MatiereResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatiereUpdateComponent,
    resolve: {
      matiere: MatiereResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiere.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
