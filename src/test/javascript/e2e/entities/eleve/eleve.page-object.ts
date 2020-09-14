import { element, by, ElementFinder } from 'protractor';

export class EleveComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-eleve div table .btn-danger'));
  title = element.all(by.css('jhi-eleve div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EleveUpdatePage {
  pageTitle = element(by.id('jhi-eleve-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  matriculInput = element(by.id('field_matricul'));
  nomInput = element(by.id('field_nom'));
  prenomInput = element(by.id('field_prenom'));
  dateNaissanceInput = element(by.id('field_dateNaissance'));

  userSelect = element(by.id('field_user'));
  classeSelect = element(by.id('field_classe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMatriculInput(matricul: string): Promise<void> {
    await this.matriculInput.sendKeys(matricul);
  }

  async getMatriculInput(): Promise<string> {
    return await this.matriculInput.getAttribute('value');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setPrenomInput(prenom: string): Promise<void> {
    await this.prenomInput.sendKeys(prenom);
  }

  async getPrenomInput(): Promise<string> {
    return await this.prenomInput.getAttribute('value');
  }

  async setDateNaissanceInput(dateNaissance: string): Promise<void> {
    await this.dateNaissanceInput.sendKeys(dateNaissance);
  }

  async getDateNaissanceInput(): Promise<string> {
    return await this.dateNaissanceInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async classeSelectLastOption(): Promise<void> {
    await this.classeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async classeSelectOption(option: string): Promise<void> {
    await this.classeSelect.sendKeys(option);
  }

  getClasseSelect(): ElementFinder {
    return this.classeSelect;
  }

  async getClasseSelectedOption(): Promise<string> {
    return await this.classeSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EleveDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-eleve-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-eleve'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
