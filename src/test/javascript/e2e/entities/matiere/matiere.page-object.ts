import { element, by, ElementFinder } from 'protractor';

export class MatiereComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-matiere div table .btn-danger'));
  title = element.all(by.css('jhi-matiere div h2#page-heading span')).first();
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

export class MatiereUpdatePage {
  pageTitle = element(by.id('jhi-matiere-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  coeficientInput = element(by.id('field_coeficient'));

  matiereLtSelect = element(by.id('field_matiereLt'));
  professeurSelect = element(by.id('field_professeur'));
  classeLtSelect = element(by.id('field_classeLt'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setCoeficientInput(coeficient: string): Promise<void> {
    await this.coeficientInput.sendKeys(coeficient);
  }

  async getCoeficientInput(): Promise<string> {
    return await this.coeficientInput.getAttribute('value');
  }

  async matiereLtSelectLastOption(): Promise<void> {
    await this.matiereLtSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matiereLtSelectOption(option: string): Promise<void> {
    await this.matiereLtSelect.sendKeys(option);
  }

  getMatiereLtSelect(): ElementFinder {
    return this.matiereLtSelect;
  }

  async getMatiereLtSelectedOption(): Promise<string> {
    return await this.matiereLtSelect.element(by.css('option:checked')).getText();
  }

  async professeurSelectLastOption(): Promise<void> {
    await this.professeurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async professeurSelectOption(option: string): Promise<void> {
    await this.professeurSelect.sendKeys(option);
  }

  getProfesseurSelect(): ElementFinder {
    return this.professeurSelect;
  }

  async getProfesseurSelectedOption(): Promise<string> {
    return await this.professeurSelect.element(by.css('option:checked')).getText();
  }

  async classeLtSelectLastOption(): Promise<void> {
    await this.classeLtSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async classeLtSelectOption(option: string): Promise<void> {
    await this.classeLtSelect.sendKeys(option);
  }

  getClasseLtSelect(): ElementFinder {
    return this.classeLtSelect;
  }

  async getClasseLtSelectedOption(): Promise<string> {
    return await this.classeLtSelect.element(by.css('option:checked')).getText();
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

export class MatiereDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-matiere-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-matiere'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
