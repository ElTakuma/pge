import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClasseLtComponentsPage, ClasseLtDeleteDialog, ClasseLtUpdatePage } from './classe-lt.page-object';

const expect = chai.expect;

describe('ClasseLt e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let classeLtComponentsPage: ClasseLtComponentsPage;
  let classeLtUpdatePage: ClasseLtUpdatePage;
  let classeLtDeleteDialog: ClasseLtDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ClasseLts', async () => {
    await navBarPage.goToEntity('classe-lt');
    classeLtComponentsPage = new ClasseLtComponentsPage();
    await browser.wait(ec.visibilityOf(classeLtComponentsPage.title), 5000);
    expect(await classeLtComponentsPage.getTitle()).to.eq('pgeApp.classeLt.home.title');
    await browser.wait(ec.or(ec.visibilityOf(classeLtComponentsPage.entities), ec.visibilityOf(classeLtComponentsPage.noResult)), 1000);
  });

  it('should load create ClasseLt page', async () => {
    await classeLtComponentsPage.clickOnCreateButton();
    classeLtUpdatePage = new ClasseLtUpdatePage();
    expect(await classeLtUpdatePage.getPageTitle()).to.eq('pgeApp.classeLt.home.createOrEditLabel');
    await classeLtUpdatePage.cancel();
  });

  it('should create and save ClasseLts', async () => {
    const nbButtonsBeforeCreate = await classeLtComponentsPage.countDeleteButtons();

    await classeLtComponentsPage.clickOnCreateButton();

    await promise.all([classeLtUpdatePage.setReferenceInput('reference')]);

    expect(await classeLtUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');

    await classeLtUpdatePage.save();
    expect(await classeLtUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await classeLtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ClasseLt', async () => {
    const nbButtonsBeforeDelete = await classeLtComponentsPage.countDeleteButtons();
    await classeLtComponentsPage.clickOnLastDeleteButton();

    classeLtDeleteDialog = new ClasseLtDeleteDialog();
    expect(await classeLtDeleteDialog.getDialogTitle()).to.eq('pgeApp.classeLt.delete.question');
    await classeLtDeleteDialog.clickOnConfirmButton();

    expect(await classeLtComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
