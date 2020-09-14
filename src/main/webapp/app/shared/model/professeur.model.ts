import { IUser } from 'app/core/user/user.model';

export interface IProfesseur {
  id?: number;
  matricul?: string;
  nom?: string;
  prenom?: string;
  user?: IUser;
}

export class Professeur implements IProfesseur {
  constructor(public id?: number, public matricul?: string, public nom?: string, public prenom?: string, public user?: IUser) {}
}
