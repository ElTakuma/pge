import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatiereLt } from 'app/shared/model/matiere-lt.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MatiereLtService } from './matiere-lt.service';
import { MatiereLtDeleteDialogComponent } from './matiere-lt-delete-dialog.component';

@Component({
  selector: 'jhi-matiere-lt',
  templateUrl: './matiere-lt.component.html'
})
export class MatiereLtComponent implements OnInit, OnDestroy {
  matiereLts?: IMatiereLt[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected matiereLtService: MatiereLtService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.matiereLtService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IMatiereLt[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInMatiereLts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMatiereLt): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMatiereLts(): void {
    this.eventSubscriber = this.eventManager.subscribe('matiereLtListModification', () => this.loadPage());
  }

  delete(matiereLt: IMatiereLt): void {
    const modalRef = this.modalService.open(MatiereLtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.matiereLt = matiereLt;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IMatiereLt[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/matiere-lt'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.matiereLts = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
