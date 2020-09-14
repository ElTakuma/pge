import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EleveComponentsPage, EleveDeleteDialog, EleveUpdatePage } from './eleve.page-object';

const expect = chai.expect;

describe('Eleve e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let eleveComponentsPage: EleveComponentsPage;
  let eleveUpdatePage: EleveUpdatePage;
  let eleveDeleteDialog: EleveDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Eleves', async () => {
    await navBarPage.goToEntity('eleve');
    eleveComponentsPage = new EleveComponentsPage();
    await browser.wait(ec.visibilityOf(eleveComponentsPage.title), 5000);
    expect(await eleveComponentsPage.getTitle()).to.eq('pgeApp.eleve.home.title');
    await browser.wait(ec.or(ec.visibilityOf(eleveComponentsPage.entities), ec.visibilityOf(eleveComponentsPage.noResult)), 1000);
  });

  it('should load create Eleve page', async () => {
    await eleveComponentsPage.clickOnCreateButton();
    eleveUpdatePage = new EleveUpdatePage();
    expect(await eleveUpdatePage.getPageTitle()).to.eq('pgeApp.eleve.home.createOrEditLabel');
    await eleveUpdatePage.cancel();
  });

  it('should create and save Eleves', async () => {
    const nbButtonsBeforeCreate = await eleveComponentsPage.countDeleteButtons();

    await eleveComponentsPage.clickOnCreateButton();

    await promise.all([
      eleveUpdatePage.setMatriculInput('matricul'),
      eleveUpdatePage.setNomInput('nom'),
      eleveUpdatePage.setPrenomInput('prenom'),
      eleveUpdatePage.setDateNaissanceInput('2000-12-31'),
      eleveUpdatePage.userSelectLastOption(),
      eleveUpdatePage.classeSelectLastOption()
    ]);

    expect(await eleveUpdatePage.getMatriculInput()).to.eq('matricul', 'Expected Matricul value to be equals to matricul');
    expect(await eleveUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await eleveUpdatePage.getPrenomInput()).to.eq('prenom', 'Expected Prenom value to be equals to prenom');
    expect(await eleveUpdatePage.getDateNaissanceInput()).to.eq('2000-12-31', 'Expected dateNaissance value to be equals to 2000-12-31');

    await eleveUpdatePage.save();
    expect(await eleveUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await eleveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Eleve', async () => {
    const nbButtonsBeforeDelete = await eleveComponentsPage.countDeleteButtons();
    await eleveComponentsPage.clickOnLastDeleteButton();

    eleveDeleteDialog = new EleveDeleteDialog();
    expect(await eleveDeleteDialog.getDialogTitle()).to.eq('pgeApp.eleve.delete.question');
    await eleveDeleteDialog.clickOnConfirmButton();

    expect(await eleveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
