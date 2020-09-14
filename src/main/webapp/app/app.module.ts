import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PgeSharedModule } from 'app/shared/shared.module';
import { PgeCoreModule } from 'app/core/core.module';
import { PgeAppRoutingModule } from './app-routing.module';
import { PgeHomeModule } from './home/home.module';
import { PgeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    PgeSharedModule,
    PgeCoreModule,
    PgeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PgeEntityModule,
    PgeAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
  exports: [BrowserAnimationsModule],
  providers: [NgbActiveModal]
})
export class PgeAppModule {}
