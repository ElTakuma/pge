import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MatiereLtComponentsPage, MatiereLtDeleteDialog, MatiereLtUpdatePage } from './matiere-lt.page-object';

const expect = chai.expect;

describe('MatiereLt e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let matiereLtComponentsPage: MatiereLtComponentsPage;
  let matiereLtUpdatePage: MatiereLtUpdatePage;
  let matiereLtDeleteDialog: MatiereLtDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MatiereLts', async () => {
    await navBarPage.goToEntity('matiere-lt');
    matiereLtComponentsPage = new MatiereLtComponentsPage();
    await browser.wait(ec.visibilityOf(matiereLtComponentsPage.title), 5000);
    expect(await matiereLtComponentsPage.getTitle()).to.eq('pgeApp.matiereLt.home.title');
    await browser.wait(ec.or(ec.visibilityOf(matiereLtComponentsPage.entities), ec.visibilityOf(matiereLtComponentsPage.noResult)), 1000);
  });

  it('should load create MatiereLt page', async () => {
    await matiereLtComponentsPage.clickOnCreateButton();
    matiereLtUpdatePage = new MatiereLtUpdatePage();
    expect(await matiereLtUpdatePage.getPageTitle()).to.eq('pgeApp.matiereLt.home.createOrEditLabel');
    await matiereLtUpdatePage.cancel();
  });

  it('should create and save MatiereLts', async () => {
    const nbButtonsBeforeCreate = await matiereLtComponentsPage.countDeleteButtons();

    await matiereLtComponentsPage.clickOnCreateButton();

    await promise.all([matiereLtUpdatePage.setReferenceInput('reference')]);

    expect(await matiereLtUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');

    await matiereLtUpdatePage.save();
    expect(await matiereLtUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await matiereLtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MatiereLt', async () => {
    const nbButtonsBeforeDelete = await matiereLtComponentsPage.countDeleteButtons();
    await matiereLtComponentsPage.clickOnLastDeleteButton();

    matiereLtDeleteDialog = new MatiereLtDeleteDialog();
    expect(await matiereLtDeleteDialog.getDialogTitle()).to.eq('pgeApp.matiereLt.delete.question');
    await matiereLtDeleteDialog.clickOnConfirmButton();

    expect(await matiereLtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
