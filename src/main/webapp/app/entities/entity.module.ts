import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'classe-lt',
        loadChildren: () => import('./classe-lt/classe-lt.module').then(m => m.PgeClasseLtModule)
      },
      {
        path: 'matiere-lt',
        loadChildren: () => import('./matiere-lt/matiere-lt.module').then(m => m.PgeMatiereLtModule)
      },
      {
        path: 'professeur',
        loadChildren: () => import('./professeur/professeur.module').then(m => m.PgeProfesseurModule)
      },
      {
        path: 'eleve',
        loadChildren: () => import('./eleve/eleve.module').then(m => m.PgeEleveModule)
      },
      {
        path: 'note',
        loadChildren: () => import('./note/note.module').then(m => m.PgeNoteModule)
      },
      {
        path: 'matiere',
        loadChildren: () => import('./matiere/matiere.module').then(m => m.PgeMatiereModule)
      },
      {
        path: 'bulettin',
        loadChildren: () => import('./bulettin/bulettin.module').then(m => m.PgeBulettinModule)
      },
      {
        path: 'classe',
        loadChildren: () => import('./classe/classe.module').then(m => m.PgeClasseModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PgeEntityModule {}
