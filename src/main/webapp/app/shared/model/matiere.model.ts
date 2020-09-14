import { IMatiereLt } from 'app/shared/model/matiere-lt.model';
import { IProfesseur } from 'app/shared/model/professeur.model';
import { IClasse } from 'app/shared/model/classe.model';

export interface IMatiere {
  id?: number;
  code?: string;
  coeficient?: number;
  matiereLt?: IMatiereLt;
  professeur?: IProfesseur;
  classe?: IClasse;
}

export class Matiere implements IMatiere {
  constructor(
    public id?: number,
    public code?: string,
    public coeficient?: number,
    public matiereLt?: IMatiereLt,
    public professeur?: IProfesseur,
    public classe?: IClasse
  ) {}
}
