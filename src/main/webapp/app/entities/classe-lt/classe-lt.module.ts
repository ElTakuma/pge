import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgeSharedModule } from 'app/shared/shared.module';
import { ClasseLtComponent } from './classe-lt.component';
import { ClasseLtDetailComponent } from './classe-lt-detail.component';
import { ClasseLtUpdateComponent } from './classe-lt-update.component';
import { ClasseLtDeleteDialogComponent } from './classe-lt-delete-dialog.component';
import { classeLtRoute } from './classe-lt.route';

@NgModule({
  imports: [PgeSharedModule, RouterModule.forChild(classeLtRoute)],
  declarations: [ClasseLtComponent, ClasseLtDetailComponent, ClasseLtUpdateComponent, ClasseLtDeleteDialogComponent],
  entryComponents: [ClasseLtDeleteDialogComponent]
})
export class PgeClasseLtModule {}
