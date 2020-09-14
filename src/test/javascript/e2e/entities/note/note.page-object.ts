import { element, by, ElementFinder } from 'protractor';

export class NoteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-note div table .btn-danger'));
  title = element.all(by.css('jhi-note div h2#page-heading span')).first();
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

export class NoteUpdatePage {
  pageTitle = element(by.id('jhi-note-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  noteIInput = element(by.id('field_noteI'));
  noteCInput = element(by.id('field_noteC'));
  observationInput = element(by.id('field_observation'));

  matiereSelect = element(by.id('field_matiere'));
  bulettinSelect = element(by.id('field_bulettin'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setNoteIInput(noteI: string): Promise<void> {
    await this.noteIInput.sendKeys(noteI);
  }

  async getNoteIInput(): Promise<string> {
    return await this.noteIInput.getAttribute('value');
  }

  async setNoteCInput(noteC: string): Promise<void> {
    await this.noteCInput.sendKeys(noteC);
  }

  async getNoteCInput(): Promise<string> {
    return await this.noteCInput.getAttribute('value');
  }

  async setObservationInput(observation: string): Promise<void> {
    await this.observationInput.sendKeys(observation);
  }

  async getObservationInput(): Promise<string> {
    return await this.observationInput.getAttribute('value');
  }

  async matiereSelectLastOption(): Promise<void> {
    await this.matiereSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matiereSelectOption(option: string): Promise<void> {
    await this.matiereSelect.sendKeys(option);
  }

  getMatiereSelect(): ElementFinder {
    return this.matiereSelect;
  }

  async getMatiereSelectedOption(): Promise<string> {
    return await this.matiereSelect.element(by.css('option:checked')).getText();
  }

  async bulettinSelectLastOption(): Promise<void> {
    await this.bulettinSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async bulettinSelectOption(option: string): Promise<void> {
    await this.bulettinSelect.sendKeys(option);
  }

  getBulettinSelect(): ElementFinder {
    return this.bulettinSelect;
  }

  async getBulettinSelectedOption(): Promise<string> {
    return await this.bulettinSelect.element(by.css('option:checked')).getText();
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

export class NoteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-note-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-note'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
