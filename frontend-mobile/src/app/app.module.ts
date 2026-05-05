import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';
import { SplashScreen } from '@ionic-native/splash-screen';
import { StatusBar } from '@ionic-native/status-bar';
import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { ConsumeApiProvider } from '../providers/consume-api/consume-api';
import { EventsManagerProvider } from '../providers/events-manager/events-manager';
import {
  MatProgressBarModule,
  ErrorStateMatcher,
  ShowOnDirtyErrorStateMatcher,
} from '@angular/material';
import { LoginProvider } from '../providers/login/login';
import { LoginPage } from '../pages/login/login';
import { OnboardingPage } from '../pages/onboarding/onboarding';
import { CreateAccountPage } from '../pages/create-account/create-account';
import { ModalTermComponent } from '../components/modal-term/modal-term';
import { MyCvPage } from '../pages/my-cv/my-cv';
import { MyAccountPage } from '../pages/my-account/my-account';
import { PlaceHistoryPage } from '../pages/place-history/place-history';
import { MyCredentialPage } from '../pages/my-credential/my-credential';
import { HelpPage } from '../pages/help/help';
import { AboutUsPage } from '../pages/about-us/about-us';
import { CreatePdfProvider } from '../providers/create-pdf/create-pdf';
import { File } from '@ionic-native/file';
import { FileChooser } from '@ionic-native/file-chooser';
import { FileOpener } from '@ionic-native/file-opener';
import { FilePath } from '@ionic-native/file-path';
import { Base64 } from '@ionic-native/base64';
import { PdfViewerModule } from '../../node_modules/ng2-pdf-viewer';
import { ConsumeHelpProvider } from '../providers/consume-help/consume-help';
import {
  //BrMaskerIonic3,
  BrMaskerModule,
} from 'brmasker-ionic-3';
import { ConsumeSaveInfoProvider } from '../providers/consume-save-info/consume-save-info';
import { DbStorageRhtotalProvider } from '../providers/db-storage-rhtotal/db-storage-rhtotal';
import { SQLite } from '@ionic-native/sqlite';
import { Dialogs } from '@ionic-native/dialogs';
import { LocalNotifications } from '@ionic-native/local-notifications';
import { Crashlytics } from '@ionic-native/fabric';

import { ViewEventProvider } from '../providers/view-event/view-event';
import { CypherProvider } from '../providers/cypher/cypher';

import { DialogsProvider } from '../providers/dialogs/dialogs';
import { BenefitsPage } from '../pages/benefits/benefits';
import { QuotesafePage } from '../pages/quotesafe/quotesafe';
import { BenefitsOptionPage } from '../pages/benefits-option/benefits-option';
import { ImagesApiProvider } from '../providers/images-api/images-api';
import { StorageProvider } from '../providers/storage/storage';
import { ConsumeBenefistProvider } from '../providers/consume-benefist/consume-benefist';

import { NotificationsProvider } from '../providers/notifications/notifications';
import { DiscountOptionPage } from '../pages/discount-option/discount-option';
import { DetailBenefisPage } from '../pages/detail-benefis/detail-benefis';
import { InAppBrowser } from '../../node_modules/@ionic-native/in-app-browser';
import { Push } from '@ionic-native/push';
import { MyDataPage } from '../pages/my-data/my-data';
import { ThreeStepsSelectorModule } from 'three-steps-selector';
import { AddressPage } from '../pages/address/address';
import { PersonalInformationPage } from '../pages/personal-information/personal-information';
import { SocialNetworksPage } from '../pages/social-networks/social-networks';
import { ScreenOrientation } from '../../node_modules/@ionic-native/screen-orientation';
import { ImagePicker } from '../../node_modules/@ionic-native/image-picker';
import { Camera } from '../../node_modules/@ionic-native/camera';
import { UsersProvider } from '../providers/users/users';
import { RhPage } from '../pages/rh/rh';
import { RhProvider } from '../providers/rh/rh';
import { ActualPositionPage } from '../pages/actual-position/actual-position';
import { CfdiPage } from '../pages/cfdi/cfdi';
import { FileTransferProvider } from '../providers/file-transfer/file-transfer';
import { FileTransfer } from '@ionic-native/file-transfer';
import { DocumentViewer } from '@ionic-native/document-viewer';
import { AndroidPermissions } from '@ionic-native/android-permissions';
import { RestorePasswordPage } from '../pages/restore-password/restore-password';
import { Keyboard } from '../../node_modules/@ionic-native/keyboard';
import { PositionHistoryPage } from '../pages/position-history/position-history';
import { CompensationPackagePage } from '../pages/compensation-package/compensation-package';
import { EmploymentBenefitsPage } from '../pages/employment-benefits/employment-benefits';
import { InsurancePage } from '../pages/insurance/insurance';
import { SalaryPage } from '../pages/salary/salary';
import { BonusesPage } from '../pages/bonuses/bonuses';
import { FormJobComponent } from '../components/form-job/form-job';
import { CredentialPage } from '../pages/credential/credential';

import { DefaultImagePipe } from '../pipes/default-image/default-image';
import { MaxLimitDirective } from '../directives/max-limit/max-limit';
import { HiringDataPage } from '../pages/hiring-data/hiring-data';
import { AsignationDataPage } from '../pages/asignation-data/asignation-data';
import { LastJobPage } from '../pages/last-job/last-job';
import { ChartsModule } from 'ng2-charts';
import { IOSFilePicker } from '../../node_modules/@ionic-native/file-picker';
import { InsuranceBenefitsPage } from '../pages/insurance-benefits/insurance-benefits';
import { DetailInsurancePage } from '../pages/detail-insurance/detail-insurance';
import { EmailServices } from '../providers/email-services/email-services';
import { NetworkService } from '../providers/network-service/network.service';
import { Network } from '@ionic-native/network';
import { Toast } from '@ionic-native/toast';
import { ServiceValidator } from '../providers/service-validator/service-validator';
import { AppVersion } from '@ionic-native/app-version';

export const MATERIAL_COMPONENTS = [MatProgressBarModule];

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    LoginPage,
    MyCvPage,
    MyAccountPage,
    PlaceHistoryPage,
    MyCredentialPage,
    OnboardingPage,
    HelpPage,
    AboutUsPage,
    CreateAccountPage,
    ModalTermComponent,
    BenefitsPage,
    QuotesafePage,
    BenefitsOptionPage,
    DiscountOptionPage,
    DetailBenefisPage,
    MyDataPage,
    AddressPage,
    PersonalInformationPage,
    SocialNetworksPage,
    MyDataPage,
    RhPage,
    EmploymentBenefitsPage,
    InsurancePage,
    BonusesPage,
    SalaryPage,
    ActualPositionPage,
    CompensationPackagePage,
    CfdiPage,
    RestorePasswordPage,
    PositionHistoryPage,
    FormJobComponent,
    CredentialPage,
    DefaultImagePipe,
    MaxLimitDirective,
    HiringDataPage,
    AsignationDataPage,
    LastJobPage,
    InsuranceBenefitsPage,
    DetailInsurancePage,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ThreeStepsSelectorModule,
    MATERIAL_COMPONENTS,
    HttpClientModule,
    PdfViewerModule,
    BrMaskerModule,
    ChartsModule,
    IonicModule.forRoot(MyApp, {
      monthNames: [
        'Enero',
        'Febrero',
        'Marzo',
        'Abril',
        'Mayo',
        'Junio',
        'Julio',
        'Agosto',
        'Septiembre',
        'Octubre',
        'Noviembre',
        'Diciembre',
      ],
    }),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    LoginPage,
    MyCvPage,
    PlaceHistoryPage,
    MyCredentialPage,
    MyAccountPage,
    HelpPage,
    AboutUsPage,
    OnboardingPage,
    CreateAccountPage,
    ModalTermComponent,
    BenefitsPage,
    QuotesafePage,
    BenefitsOptionPage,
    DiscountOptionPage,
    DetailBenefisPage,
    MyDataPage,
    AddressPage,
    EmploymentBenefitsPage,
    InsurancePage,
    BonusesPage,
    SalaryPage,
    PersonalInformationPage,
    SocialNetworksPage,
    MyDataPage,
    RhPage,
    ActualPositionPage,
    CfdiPage,
    CompensationPackagePage,
    RestorePasswordPage,
    PositionHistoryPage,
    FormJobComponent,
    CredentialPage,
    HiringDataPage,
    AsignationDataPage,
    LastJobPage,
    InsuranceBenefitsPage,
    DetailInsurancePage,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    ConsumeApiProvider,
    EventsManagerProvider,
    LoginProvider,
    CreatePdfProvider,
    FileOpener,
    FileChooser,
    FilePath,
    Base64,
    File,
    Push,
    Crashlytics,
    LocalNotifications,
    ConsumeHelpProvider,
    ConsumeSaveInfoProvider,
    DbStorageRhtotalProvider,
    DbStorageRhtotalProvider,
    DbStorageRhtotalProvider,
    SQLite,
    ViewEventProvider,
    CypherProvider,
    DialogsProvider,
    Dialogs,
    ImagesApiProvider,
    StorageProvider,
    ConsumeBenefistProvider,
    NotificationsProvider,
    InAppBrowser,
    ScreenOrientation,
    ImagePicker,
    Camera,
    UsersProvider,
    RhProvider,
    FileTransferProvider,
    FileTransfer,
    DocumentViewer,
    AndroidPermissions,
    Keyboard,
    IOSFilePicker,
    EmailServices,
    NetworkService,
    Toast,
    Network,
    AppVersion,
    ServiceValidator,
    { provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher },
  ],
})
export class AppModule {}
