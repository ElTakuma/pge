import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PgeSharedModule } from 'app/shared/shared.module';
import { ClasseComponent } from './classe.component';
import { ClasseDetailComponent } from './classe-detail.component';
import { ClasseUpdateComponent } from './classe-update.component';
import { ClasseDeleteDialogComponent } from './classe-delete-dialog.component';
import { classeRoute } from './classe.route';
import { ClasseMatiereUpdateComponent } from 'app/entities/classe/classe-matiere-update.component';

@NgModule({
  imports: [PgeSharedModule, RouterModule.forChild(classeRoute)],
  declarations: [ClasseComponent, ClasseDetailComponent, ClasseUpdateComponent, ClasseDeleteDialogComponent, ClasseMatiereUpdateComponent],
  entryComponents: [ClasseDeleteDialogComponent, ClasseMatiereUpdateComponent]
})
export class PgeClasseModule {}
