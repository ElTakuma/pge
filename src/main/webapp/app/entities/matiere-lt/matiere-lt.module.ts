import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgeSharedModule } from 'app/shared/shared.module';
import { MatiereLtComponent } from './matiere-lt.component';
import { MatiereLtDetailComponent } from './matiere-lt-detail.component';
import { MatiereLtUpdateComponent } from './matiere-lt-update.component';
import { MatiereLtDeleteDialogComponent } from './matiere-lt-delete-dialog.component';
import { matiereLtRoute } from './matiere-lt.route';

@NgModule({
  imports: [PgeSharedModule, RouterModule.forChild(matiereLtRoute)],
  declarations: [MatiereLtComponent, MatiereLtDetailComponent, MatiereLtUpdateComponent, MatiereLtDeleteDialogComponent],
  entryComponents: [MatiereLtDeleteDialogComponent]
})
export class PgeMatiereLtModule {}
