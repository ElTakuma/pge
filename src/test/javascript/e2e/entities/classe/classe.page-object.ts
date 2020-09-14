import { element, by, ElementFinder } from 'protractor';

export class ClasseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-classe div table .btn-danger'));
  title = element.all(by.css('jhi-classe div h2#page-heading span')).first();
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

export class ClasseUpdatePage {
  pageTitle = element(by.id('jhi-classe-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  effectifInput = element(by.id('field_effectif'));

  classeLtSelect = element(by.id('field_classeLt'));
  professeurSelect = element(by.id('field_professeur'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setEffectifInput(effectif: string): Promise<void> {
    await this.effectifInput.sendKeys(effectif);
  }

  async getEffectifInput(): Promise<string> {
    return await this.effectifInput.getAttribute('value');
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

export class ClasseDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-classe-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-classe'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
