import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IClasse } from 'app/shared/model/classe.model';

export interface IEleve {
  id?: number;
  matricul?: string;
  nom?: string;
  prenom?: string;
  dateNaissance?: Moment;
  user?: IUser;
  classe?: IClasse;
}

export class Eleve implements IEleve {
  constructor(
    public id?: number,
    public matricul?: string,
    public nom?: string,
    public prenom?: string,
    public dateNaissance?: Moment,
    public user?: IUser,
    public classe?: IClasse
  ) {}
}
