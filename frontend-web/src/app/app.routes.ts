import { Routes, RouterModule } from "@angular/router";

import { LoginComponent } from '../app/login/login.component';


import { routesWeb } from './../environments/environment';
import { ResetComponent } from "./reset/reset.component";
import { ResetConfirmationComponent } from './reset-confirmation/reset.confirmation.component';
import { NewUserComponent } from "./login/new-user/new-user.component";


export const appRoutes: Routes = [
    { path: routesWeb.LOGIN, component: LoginComponent },
    { path: routesWeb.NEW_USER, component: NewUserComponent},
    { path: routesWeb.EMPTY, component: LoginComponent },
    { path: routesWeb.RESET_ACCOUNT, component: ResetComponent},
    { path: routesWeb.RESET_ACCOUNT_CONFIRMATION, component: ResetConfirmationComponent}
  ];

  export const APP_ROUTES = RouterModule.forRoot( appRoutes, { useHash: true } );