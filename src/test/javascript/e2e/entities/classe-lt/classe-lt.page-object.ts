import { element, by, ElementFinder } from 'protractor';

export class ClasseLtComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-classe-lt div table .btn-danger'));
  title = element.all(by.css('jhi-classe-lt div h2#page-heading span')).first();
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

export class ClasseLtUpdatePage {
  pageTitle = element(by.id('jhi-classe-lt-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  referenceInput = element(by.id('field_reference'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setReferenceInput(reference: string): Promise<void> {
    await this.referenceInput.sendKeys(reference);
  }

  async getReferenceInput(): Promise<string> {
    return await this.referenceInput.getAttribute('value');
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

export class ClasseLtDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-classeLt-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-classeLt'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
