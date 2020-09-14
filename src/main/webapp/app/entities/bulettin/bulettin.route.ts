import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBulettin, Bulettin } from 'app/shared/model/bulettin.model';
import { BulettinService } from './bulettin.service';
import { BulettinComponent } from './bulettin.component';
import { BulettinDetailComponent } from './bulettin-detail.component';
import { BulettinUpdateComponent } from './bulettin-update.component';

@Injectable({ providedIn: 'root' })
export class BulettinResolve implements Resolve<IBulettin> {
  constructor(private service: BulettinService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBulettin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.findComplet(id).pipe(
        flatMap((bulettin: HttpResponse<Bulettin>) => {
          if (bulettin.body) {
            return of(bulettin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bulettin());
  }
}

export const bulettinRoute: Routes = [
  {
    path: '',
    component: BulettinComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pgeApp.bulettin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BulettinDetailComponent,
    resolve: {
      bulettin: BulettinResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.bulettin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BulettinUpdateComponent,
    resolve: {
      bulettin: BulettinResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.bulettin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BulettinUpdateComponent,
    resolve: {
      bulettin: BulettinResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.bulettin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
