import { IMatiere } from 'app/shared/model/matiere.model';
import { IBulettin } from 'app/shared/model/bulettin.model';

export interface INote {
  id?: number;
  code?: string;
  noteI?: number;
  noteC?: number;
  observation?: string;
  matiere?: IMatiere;
  bulettin?: IBulettin;
}

export class Note implements INote {
  constructor(
    public id?: number,
    public code?: string,
    public noteI?: number,
    public noteC?: number,
    public observation?: string,
    public matiere?: IMatiere,
    public bulettin?: IBulettin
  ) {}
}
