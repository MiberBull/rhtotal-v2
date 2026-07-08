import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { TenantInterceptor } from './interceptors/tenant.interceptor';

import { RecaptchaModule } from 'ng-recaptcha';
import { DragulaModule } from 'ng2-dragula';
import { AppRoutingModule } from './/app-routing.module';
import { PagesModule } from './pages/pages.module';

import { APP_PROVIDERS } from './app.providers';
import { APP_ROUTES } from './app.routes';
import { MATERIAL_COMPONENTS } from '../app/app.material';

import { AppComponent, routes } from './app.component';
import { LoginComponent } from './login/login.component';
import { ForgetPasswordModal } from './login/forget-password/forget-password.component';
import { NewUserComponent } from './login/new-user/new-user.component';
import { MAT_DATE_FORMATS } from '@angular/material';
import { ResetComponent } from './reset/reset.component';
import { ResetConfirmationComponent } from './reset-confirmation/reset.confirmation.component';
import { PipeTree } from './pipes/pipe-tree';
import { InputMaskDirective } from './input-mask.directive';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ForgetPasswordModal,
    NewUserComponent,
    ResetComponent,
    ResetConfirmationComponent,
    InputMaskDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MATERIAL_COMPONENTS,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forRoot(routes),
    RecaptchaModule.forRoot(),
    DragulaModule.forRoot(),
    APP_ROUTES,
    PagesModule,
  ],
  entryComponents: [ForgetPasswordModal],
  bootstrap: [AppComponent],
  providers: [
    APP_PROVIDERS,
    { provide: HTTP_INTERCEPTORS, useClass: TenantInterceptor, multi: true },
  ],
})
export class AppModule {}
