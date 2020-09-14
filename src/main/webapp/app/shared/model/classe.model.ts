import { IClasseLt } from 'app/shared/model/classe-lt.model';
import { IProfesseur } from 'app/shared/model/professeur.model';
import { IMatiere } from 'app/shared/model/matiere.model';

export interface IClasse {
  id?: number;
  code?: string;
  effectif?: number;
  classeLt?: IClasseLt;
  professeur?: IProfesseur;
  matieres?: IMatiere[];
}

export class Classe implements IClasse {
  constructor(
    public id?: number,
    public code?: string,
    public effectif?: number,
    public classeLt?: IClasseLt,
    public professeur?: IProfesseur,
    public matieres?: IMatiere[]
  ) {}
}
