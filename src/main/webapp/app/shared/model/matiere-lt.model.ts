export interface IMatiereLt {
  id?: number;
  reference?: string;
}

export class MatiereLt implements IMatiereLt {
  constructor(public id?: number, public reference?: string) {}
}
