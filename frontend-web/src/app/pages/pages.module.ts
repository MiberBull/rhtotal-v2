import { FormRolesComponent } from './roles/form-roles/form-roles.component';
import { NgModule } from '@angular/core';
import { PAGES_ROUTES } from './pages.routes';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { BrowserModule } from '@angular/platform-browser';

//componentes
import { ClientComponent } from '../pages/client/client.component';
import { CreateClientComponent } from '../pages/create-client/create-client.component';
import { CommonModule } from '@angular/common';
import { MATERIAL_COMPONENTS } from '../app.material';
import { PagesComponent } from './pages.component';
import { RolesComponent } from './roles/roles.component';
import { InsuranceComponent } from './insurance/insurance.component';
import { CreateDiscountComponent } from './create-discount/create-discount.component';
import { NotificationComponent } from './notifications/notification.component';
import { UsersComponent } from './users/users.component';
import { CustomersComponent } from './customers/customers.component';
import { HomeComponent } from './home/home.component';
import { ComponentModule } from '../components/component.module';
import { NotificationsAdminComponent } from './notifications-admin/notifications-admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { InsuranceAdminComponent } from './insurance-admin/insurance-admin.component';
import { BannersComponent } from './banners/banners.component';
import { BanersAdminComponent } from './baners-admin/baners-admin.component';
import { BannersFormComponent } from './banners-form/banners-form.component';
import { DiscountComponent } from './discount/discount.component';
import { DiscountAdminComponent } from './discount-admin/discount-admin.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { UserToolbarComponent } from './user-toolbar/user-toolbar.component';
import { UserFormPersonaleInfoComponent } from './user-admin/user-form-personal-info/user-form-personal-info.component';
import { UserFormDomicileComponent } from './user-admin/user-form-domicile/user-form-domicile.component';
import { UserFormSocialNetworksComponent } from './user-admin/user-form-social-networks/user-form-social-networks.component';
import { UserFormContractingDataComponent } from './user-admin/user-form-contracting-data/user-form-contracting-data.component';
import { UserFormLastJobComponent } from './user-admin/user-form-last-job/user-form-last-job.component';
import { UserFormCompenPackageComponent } from './user-admin/user-form-compen-package/user-form-compen-package.component';
import { UserFormAssignmentDataComponent } from './user-admin/user-form-assignment-data/user-form-assignment-data.component';
import { StickyBelowViewDirective } from '../directives/sticky-below-view.directive';
import { MycurrencyDirective } from '../directives/mycurrency.directive';
import { ThreeStepsSelectorModule } from 'three-steps-selector';
import { VehicleInsuranceComponent } from './insurance-admin/vehicle-insurance/vehicle-insurance.component';
import { LifeInsuranceComponent } from './insurance-admin/life-insurance/life-insurance.component';
import { InsuranceMedicalExpensesComponent } from './insurance-admin/insurance-medical-expenses/insurance-medical-expenses.component';
import { DialogEventualitiesComponent } from './insurance-admin/dialog-eventualities/dialog-eventualities.component';
import { PlanCoverageComponent } from './insurance-admin/plan-coverage/plan-coverage.component';
import { ConfirmationDialogComponent } from './insurance-admin/confirmation-dialog/confirmation-dialog.component';
import { LoadingComponent } from '../components/loading/loading.component';
import { CustomCurrencyPipe } from '../pipes/CustomCurrencyPipe';
import { VacacionesComponent } from './vacaciones/vacaciones.component';
import { IncidenciasComponent } from './incidencias/incidencias.component';
import { DialogCreateIncidentComponent } from './incidencias/dialog-create-incident/dialog-create-incident.component';
import { TicketsComponent } from './tickets/tickets.component';
import { EncuestasComponent } from './encuestas/encuestas.component';
import { RepseCumplimientoComponent } from './repse-cumplimiento/repse-cumplimiento.component';
import { RepsePerfilComponent } from './repse-perfil/repse-perfil.component';
import { DocumentosComponent } from './documentos/documentos.component';
import { CfdiComponent } from './cfdi/cfdi.component';
import { TenantsComponent } from './tenants/tenants.component';

@NgModule({
  declarations: [
    ClientComponent,
    CreateClientComponent,
    PagesComponent,
    RolesComponent,
    InsuranceComponent,
    CreateDiscountComponent,
    FormRolesComponent,
    InsuranceComponent,
    NotificationComponent,
    UsersComponent,
    CustomersComponent,
    HomeComponent,
    NotificationsAdminComponent,
    DashboardComponent,
    InsuranceAdminComponent,
    BannersComponent,
    BanersAdminComponent,
    BannersFormComponent,
    DiscountComponent,
    DiscountAdminComponent,
    UserAdminComponent,
    UserToolbarComponent,
    UserFormPersonaleInfoComponent,
    UserFormDomicileComponent,
    UserFormSocialNetworksComponent,
    UserFormContractingDataComponent,
    UserFormLastJobComponent,
    UserFormCompenPackageComponent,
    UserFormAssignmentDataComponent,
    StickyBelowViewDirective,
    VehicleInsuranceComponent,
    LifeInsuranceComponent,
    InsuranceMedicalExpensesComponent,
    DialogEventualitiesComponent,
    PlanCoverageComponent,
    ConfirmationDialogComponent,
    LoadingComponent,
    MycurrencyDirective,
    CustomCurrencyPipe,
    VacacionesComponent,
    IncidenciasComponent,
    DialogCreateIncidentComponent,
    TicketsComponent,
    EncuestasComponent,
    RepseCumplimientoComponent,
    RepsePerfilComponent,
    DocumentosComponent,
    CfdiComponent,
    TenantsComponent,
  ],
  exports: [
    ClientComponent,
    CreateClientComponent,
    PagesComponent,
    RolesComponent,
    FormRolesComponent,
    InsuranceComponent,
    NotificationComponent,
    UsersComponent,
    CustomersComponent,
    BannersComponent,
    LoadingComponent,
  ],
  imports: [
    PAGES_ROUTES,
    ReactiveFormsModule,
    CommonModule,
    MATERIAL_COMPONENTS,
    SharedModule,
    ComponentModule,
    ThreeStepsSelectorModule,
    FormsModule,
    BrowserModule,
  ],
  entryComponents: [
    DialogEventualitiesComponent,
    PlanCoverageComponent,
    ConfirmationDialogComponent,
    DialogCreateIncidentComponent,
  ],
})
export class PagesModule {}
