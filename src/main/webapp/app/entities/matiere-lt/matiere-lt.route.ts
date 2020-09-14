import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatiereLt, MatiereLt } from 'app/shared/model/matiere-lt.model';
import { MatiereLtService } from './matiere-lt.service';
import { MatiereLtComponent } from './matiere-lt.component';
import { MatiereLtDetailComponent } from './matiere-lt-detail.component';
import { MatiereLtUpdateComponent } from './matiere-lt-update.component';

@Injectable({ providedIn: 'root' })
export class MatiereLtResolve implements Resolve<IMatiereLt> {
  constructor(private service: MatiereLtService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatiereLt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matiereLt: HttpResponse<MatiereLt>) => {
          if (matiereLt.body) {
            return of(matiereLt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatiereLt());
  }
}

export const matiereLtRoute: Routes = [
  {
    path: '',
    component: MatiereLtComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pgeApp.matiereLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatiereLtDetailComponent,
    resolve: {
      matiereLt: MatiereLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiereLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatiereLtUpdateComponent,
    resolve: {
      matiereLt: MatiereLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiereLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatiereLtUpdateComponent,
    resolve: {
      matiereLt: MatiereLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.matiereLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
