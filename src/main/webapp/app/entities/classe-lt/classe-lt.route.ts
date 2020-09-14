import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClasseLt, ClasseLt } from 'app/shared/model/classe-lt.model';
import { ClasseLtService } from './classe-lt.service';
import { ClasseLtComponent } from './classe-lt.component';
import { ClasseLtDetailComponent } from './classe-lt-detail.component';
import { ClasseLtUpdateComponent } from './classe-lt-update.component';

@Injectable({ providedIn: 'root' })
export class ClasseLtResolve implements Resolve<IClasseLt> {
  constructor(private service: ClasseLtService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClasseLt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classeLt: HttpResponse<ClasseLt>) => {
          if (classeLt.body) {
            return of(classeLt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClasseLt());
  }
}

export const classeLtRoute: Routes = [
  {
    path: '',
    component: ClasseLtComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pgeApp.classeLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClasseLtDetailComponent,
    resolve: {
      classeLt: ClasseLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.classeLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClasseLtUpdateComponent,
    resolve: {
      classeLt: ClasseLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.classeLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClasseLtUpdateComponent,
    resolve: {
      classeLt: ClasseLtResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pgeApp.classeLt.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
