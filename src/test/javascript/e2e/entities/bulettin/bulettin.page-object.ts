import { element, by, ElementFinder } from 'protractor';

export class BulettinComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-bulettin div table .btn-danger'));
  title = element.all(by.css('jhi-bulettin div h2#page-heading span')).first();
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

export class BulettinUpdatePage {
  pageTitle = element(by.id('jhi-bulettin-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codeInput = element(by.id('field_code'));
  sessionBSelect = element(by.id('field_sessionB'));
  tCoefInput = element(by.id('field_tCoef'));
  tNoteIInput = element(by.id('field_tNoteI'));
  moyenneInput = element(by.id('field_moyenne'));
  mentionSelect = element(by.id('field_mention'));

  eleveSelect = element(by.id('field_eleve'));
  classeSelect = element(by.id('field_classe'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setSessionBSelect(sessionB: string): Promise<void> {
    await this.sessionBSelect.sendKeys(sessionB);
  }

  async getSessionBSelect(): Promise<string> {
    return await this.sessionBSelect.element(by.css('option:checked')).getText();
  }

  async sessionBSelectLastOption(): Promise<void> {
    await this.sessionBSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTCoefInput(tCoef: string): Promise<void> {
    await this.tCoefInput.sendKeys(tCoef);
  }

  async getTCoefInput(): Promise<string> {
    return await this.tCoefInput.getAttribute('value');
  }

  async setTNoteIInput(tNoteI: string): Promise<void> {
    await this.tNoteIInput.sendKeys(tNoteI);
  }

  async getTNoteIInput(): Promise<string> {
    return await this.tNoteIInput.getAttribute('value');
  }

  async setMoyenneInput(moyenne: string): Promise<void> {
    await this.moyenneInput.sendKeys(moyenne);
  }

  async getMoyenneInput(): Promise<string> {
    return await this.moyenneInput.getAttribute('value');
  }

  async setMentionSelect(mention: string): Promise<void> {
    await this.mentionSelect.sendKeys(mention);
  }

  async getMentionSelect(): Promise<string> {
    return await this.mentionSelect.element(by.css('option:checked')).getText();
  }

  async mentionSelectLastOption(): Promise<void> {
    await this.mentionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async eleveSelectLastOption(): Promise<void> {
    await this.eleveSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async eleveSelectOption(option: string): Promise<void> {
    await this.eleveSelect.sendKeys(option);
  }

  getEleveSelect(): ElementFinder {
    return this.eleveSelect;
  }

  async getEleveSelectedOption(): Promise<string> {
    return await this.eleveSelect.element(by.css('option:checked')).getText();
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

export class BulettinDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bulettin-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bulettin'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
