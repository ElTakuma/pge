import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MatiereComponentsPage, MatiereDeleteDialog, MatiereUpdatePage } from './matiere.page-object';

const expect = chai.expect;

describe('Matiere e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let matiereComponentsPage: MatiereComponentsPage;
  let matiereUpdatePage: MatiereUpdatePage;
  let matiereDeleteDialog: MatiereDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Matieres', async () => {
    await navBarPage.goToEntity('matiere');
    matiereComponentsPage = new MatiereComponentsPage();
    await browser.wait(ec.visibilityOf(matiereComponentsPage.title), 5000);
    expect(await matiereComponentsPage.getTitle()).to.eq('pgeApp.matiere.home.title');
    await browser.wait(ec.or(ec.visibilityOf(matiereComponentsPage.entities), ec.visibilityOf(matiereComponentsPage.noResult)), 1000);
  });

  it('should load create Matiere page', async () => {
    await matiereComponentsPage.clickOnCreateButton();
    matiereUpdatePage = new MatiereUpdatePage();
    expect(await matiereUpdatePage.getPageTitle()).to.eq('pgeApp.matiere.home.createOrEditLabel');
    await matiereUpdatePage.cancel();
  });

  it('should create and save Matieres', async () => {
    const nbButtonsBeforeCreate = await matiereComponentsPage.countDeleteButtons();

    await matiereComponentsPage.clickOnCreateButton();

    await promise.all([
      matiereUpdatePage.setCodeInput('code'),
      matiereUpdatePage.setCoeficientInput('5'),
      matiereUpdatePage.matiereLtSelectLastOption(),
      matiereUpdatePage.professeurSelectLastOption(),
      matiereUpdatePage.classeLtSelectLastOption()
    ]);

    expect(await matiereUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await matiereUpdatePage.getCoeficientInput()).to.eq('5', 'Expected coeficient value to be equals to 5');

    await matiereUpdatePage.save();
    expect(await matiereUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await matiereComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Matiere', async () => {
    const nbButtonsBeforeDelete = await matiereComponentsPage.countDeleteButtons();
    await matiereComponentsPage.clickOnLastDeleteButton();

    matiereDeleteDialog = new MatiereDeleteDialog();
    expect(await matiereDeleteDialog.getDialogTitle()).to.eq('pgeApp.matiere.delete.question');
    await matiereDeleteDialog.clickOnConfirmButton();

    expect(await matiereComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
