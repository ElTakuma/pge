export interface IClasseLt {
  id?: number;
  reference?: string;
}

export class ClasseLt implements IClasseLt {
  constructor(public id?: number, public reference?: string) {}
}
