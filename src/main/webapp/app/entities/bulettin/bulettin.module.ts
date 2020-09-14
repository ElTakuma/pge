import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgeSharedModule } from 'app/shared/shared.module';
import { BulettinComponent } from './bulettin.component';
import { BulettinDetailComponent } from './bulettin-detail.component';
import { BulettinUpdateComponent } from './bulettin-update.component';
import { BulettinDeleteDialogComponent } from './bulettin-delete-dialog.component';
import { bulettinRoute } from './bulettin.route';
import { NoteBulettinUpdateComponent } from 'app/entities/bulettin/note-bulettin-update.component';

@NgModule({
  imports: [PgeSharedModule, RouterModule.forChild(bulettinRoute)],
  declarations: [
    BulettinComponent,
    BulettinDetailComponent,
    BulettinUpdateComponent,
    BulettinDeleteDialogComponent,
    NoteBulettinUpdateComponent
  ],
  entryComponents: [BulettinDeleteDialogComponent]
})
export class PgeBulettinModule {}
