import { Routes, RouterModule } from "@angular/router";

//Componentes
import { PagesComponent } from "./pages.component";
import { ClientComponent } from "../pages/client/client.component";
import { RolesComponent } from "../pages/roles/roles.component";
import { FormRolesComponent } from "./roles/form-roles/form-roles.component";
import { InsuranceComponent } from "../pages/insurance/insurance.component";
import { NotificationComponent } from "./notifications/notification.component";
import { LoginGuard } from "../services/guards/login-guard.guard";
import { UsersComponent } from "../pages/users/users.component";
import { CustomersComponent } from './customers/customers.component';
import { NotificationsAdminComponent } from "./notifications-admin/notifications-admin.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { InsuranceAdminComponent } from './insurance-admin/insurance-admin.component';
import { BannersComponent } from './banners/banners.component';
import { BanersAdminComponent } from './baners-admin/baners-admin.component';
import { DiscountComponent } from './discount/discount.component';
import { DiscountAdminComponent } from './discount-admin/discount-admin.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { UserFormPersonaleInfoComponent } from './user-admin/user-form-personal-info/user-form-personal-info.component';
import { UserFormDomicileComponent } from './user-admin/user-form-domicile/user-form-domicile.component';
import { UserFormContractingDataComponent } from "./user-admin/user-form-contracting-data/user-form-contracting-data.component";
import { UserFormSocialNetworksComponent } from './user-admin/user-form-social-networks/user-form-social-networks.component';
import { UserFormLastJobComponent } from './user-admin/user-form-last-job/user-form-last-job.component';
import { UserFormCompenPackageComponent } from "./user-admin/user-form-compen-package/user-form-compen-package.component";
import { UserFormAssignmentDataComponent } from './user-admin/user-form-assignment-data/user-form-assignment-data.component';

const pagesRoutes: Routes = [
  {
    path: "home",
    component: PagesComponent,
    canActivate: [LoginGuard],
    children: [
      { path: "roles", component: RolesComponent },
      { path: "adminRoles", component: FormRolesComponent },
      { path: "seguros", component: InsuranceComponent },
      { path: "admin-seguros", component: InsuranceAdminComponent },
      { path: "descuentos", component: DiscountComponent },
      { path: "admin-descuentos", component: DiscountAdminComponent },
      { path: "notificaciones", component: NotificationComponent },
      { path: "admin-notificaciones", component: NotificationsAdminComponent },
      { path: "usuarios", component: UsersComponent },
      { path: "admin-usuario", component: UserAdminComponent,children:[
        { path: "datos-personales", component: UserFormPersonaleInfoComponent },
        { path: "redes-sociales", component: UserFormSocialNetworksComponent },
        { path: "datos-contratacion", component: UserFormContractingDataComponent },
        { path: "ultimo-empleo", component: UserFormLastJobComponent },
        { path: "paquete-compensacion", component: UserFormCompenPackageComponent },
        { path: "datos-asignacion", component: UserFormAssignmentDataComponent },
        { path: "domicilio", component: UserFormDomicileComponent}] },
      { path: "crear-cliente", component: ClientComponent },
      { path: "clientes", component: CustomersComponent },
      { path: "dashboard", component: DashboardComponent },
      { path: "banners", component: BannersComponent },
      { path: "admin-banners", component: BanersAdminComponent }
    ]
  }
];

export const PAGES_ROUTES = RouterModule.forChild(pagesRoutes);
