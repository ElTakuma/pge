import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BulettinComponentsPage, BulettinDeleteDialog, BulettinUpdatePage } from './bulettin.page-object';

const expect = chai.expect;

describe('Bulettin e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bulettinComponentsPage: BulettinComponentsPage;
  let bulettinUpdatePage: BulettinUpdatePage;
  let bulettinDeleteDialog: BulettinDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Bulettins', async () => {
    await navBarPage.goToEntity('bulettin');
    bulettinComponentsPage = new BulettinComponentsPage();
    await browser.wait(ec.visibilityOf(bulettinComponentsPage.title), 5000);
    expect(await bulettinComponentsPage.getTitle()).to.eq('pgeApp.bulettin.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bulettinComponentsPage.entities), ec.visibilityOf(bulettinComponentsPage.noResult)), 1000);
  });

  it('should load create Bulettin page', async () => {
    await bulettinComponentsPage.clickOnCreateButton();
    bulettinUpdatePage = new BulettinUpdatePage();
    expect(await bulettinUpdatePage.getPageTitle()).to.eq('pgeApp.bulettin.home.createOrEditLabel');
    await bulettinUpdatePage.cancel();
  });

  it('should create and save Bulettins', async () => {
    const nbButtonsBeforeCreate = await bulettinComponentsPage.countDeleteButtons();

    await bulettinComponentsPage.clickOnCreateButton();

    await promise.all([
      bulettinUpdatePage.setCodeInput('code'),
      bulettinUpdatePage.sessionBSelectLastOption(),
      bulettinUpdatePage.setTCoefInput('5'),
      bulettinUpdatePage.setTNoteIInput('5'),
      bulettinUpdatePage.setMoyenneInput('5'),
      bulettinUpdatePage.mentionSelectLastOption(),
      bulettinUpdatePage.eleveSelectLastOption(),
      bulettinUpdatePage.classeSelectLastOption()
    ]);

    expect(await bulettinUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await bulettinUpdatePage.getTCoefInput()).to.eq('5', 'Expected tCoef value to be equals to 5');
    expect(await bulettinUpdatePage.getTNoteIInput()).to.eq('5', 'Expected tNoteI value to be equals to 5');
    expect(await bulettinUpdatePage.getMoyenneInput()).to.eq('5', 'Expected moyenne value to be equals to 5');

    await bulettinUpdatePage.save();
    expect(await bulettinUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bulettinComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Bulettin', async () => {
    const nbButtonsBeforeDelete = await bulettinComponentsPage.countDeleteButtons();
    await bulettinComponentsPage.clickOnLastDeleteButton();

    bulettinDeleteDialog = new BulettinDeleteDialog();
    expect(await bulettinDeleteDialog.getDialogTitle()).to.eq('pgeApp.bulettin.delete.question');
    await bulettinDeleteDialog.clickOnConfirmButton();

    expect(await bulettinComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
