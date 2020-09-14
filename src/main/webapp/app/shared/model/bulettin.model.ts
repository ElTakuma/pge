import { IEleve } from 'app/shared/model/eleve.model';
import { IClasse } from 'app/shared/model/classe.model';
import { SessionLt } from 'app/shared/model/enumerations/session-lt.model';
import { Mentions } from 'app/shared/model/enumerations/mentions.model';
import { INote } from 'app/shared/model/note.model';

export interface IBulettin {
  id?: number;
  code?: string;
  sessionB?: SessionLt;
  tCoef?: number;
  tNoteI?: number;
  moyenne?: number;
  mention?: Mentions;
  eleve?: IEleve;
  classe?: IClasse;
  notes?: INote[];
}

export class Bulettin implements IBulettin {
  constructor(
    public id?: number,
    public code?: string,
    public sessionB?: SessionLt,
    public tCoef?: number,
    public tNoteI?: number,
    public moyenne?: number,
    public mention?: Mentions,
    public eleve?: IEleve,
    public classe?: IClasse,
    public notes?: INote[]
  ) {}
}
