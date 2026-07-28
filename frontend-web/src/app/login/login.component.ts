import { Router } from '@angular/router';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';

import { DataService } from './../services/data.service';
import { LocalStorageService } from './../services/local-sotorage/localstorage.service';
import { LoginService } from './../services/login/login.service';

import { ForgetPasswordModal } from './forget-password/forget-password.component';
import { environment, parameters, routesWeb, EXPRESSION } from './../../environments/environment';
import { LoginTO, UserTO } from './../models/user.model';
import { ResetComponent } from '../reset/reset.component';
import { PATH_SECURITY } from '../../environments/environment.prod';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  formLogin: FormGroup;
  intents: number;
  isLoading = false;
  response: LoginTO = new LoginTO();
  enable: boolean = true;
  showPassword = false;

  constructor(
    private formBuilder: FormBuilder,
    private _loginService: LoginService,
    private _localStorage: LocalStorageService,
    private _data: DataService,
    private dialog: MatDialog,
    private _route: Router
  ) {
    localStorage.clear();
  }

  ngOnInit() {
    this.formLogin = this.formBuilder.group({
      email: [
        environment.EMPTY_INPUT,
        [Validators.required, Validators.email, Validators.maxLength(environment.LONG_EMAIL)],
      ],
      password: [
        environment.EMPTY_INPUT,
        [
          Validators.required,
          Validators.maxLength(environment.MAX_LENGTH_PASSWORD),
          Validators.minLength(environment.MIN_LENGTH_PASSWORD),
        ],
      ],
    });

    this.fillParameters();

    this._data.getIsLoadingEvent().subscribe((isLoad) => (this.isLoading = isLoad));
  }

  fillParameters() {
    this._loginService.getParameters(parameters.intents).subscribe(
      (params: any) => {
        this.intents = params;
        this._localStorage.addItem(environment.PARAMS, params);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  onSubmit() {
    if (isNaN(this.intents)) {
      this.fillParameters();
    }

    this.enable = false;
    if (this.validateEmail(this.formLogin.value.email) && this.formLogin.valid) {
      this.onLogin();
    } else {
      this._data.setGeneralNotificationMessage(environment.USER_ERROR);
    }
    this.enable = true;
  }

  validateEmail(email: string) {
    const emailRegex = EXPRESSION.EMAIL;
    return emailRegex.test(email);
  }

  decreaseIntents() {
    return (this.intents -= 1);
  }

  onLogin() {
    this._data.setIsLoadingEvent(true);
    const userLogin: UserTO = new UserTO();
    userLogin.email = this.formLogin.value.email;
    userLogin.password = this.formLogin.value.password;

    const loginTO: LoginTO = new LoginTO();
    loginTO.user = userLogin;
    loginTO.user.email = loginTO.user.email.toLocaleLowerCase();
    loginTO.device = 2;

    this._loginService.login(loginTO).subscribe(
      (response) => {
        this.response = response;
        this.optionsResponse(response);
      },
      (error) => {
        this._data.setGeneralNotificationMessage(environment.ERROR_SERVIDOR);
        this._data.setIsLoadingEvent(false);
      },
      () => this._data.setIsLoadingEvent(false)
    );
  }

  optionsResponse(options: LoginTO) {
    switch (options.flag) {
      case 0:
        this.goHome(options);
        break;
      case 1:
        this._data.setGeneralNotificationMessage(options.message);
        this.enable = true;
        break;
      case 2:
        if (this.decreaseIntents() <= 0) {
          this.blockUser(options);
        } else {
          this._data.setGeneralNotificationMessage(options.message);
        }
        this.enable = true;
        break;
      case 3:
        this._data.setGeneralNotificationMessage(options.message);
        this.enable = true;
        break;
      case 4:
        this._route.navigate([routesWeb.NEW_USER], { queryParams: { website: parameters.WEB } });
        break;
    }
  }

  resetIntents() {
    if (this.validateEmail(this.formLogin.value.email)) this.response.block = false;
    this.response.flag = -1;
    this.response.message = null;
    this._localStorage
      .getItem(environment.PARAMS, environment.INTENTS)
      .subscribe((intent) => (this.intents = Number.parseInt(intent)));
  }

  blockUser(reposne: LoginTO) {
    const userBlock: UserTO = new UserTO();
    userBlock.email = this.formLogin.value.email;
    userBlock.password = this.formLogin.value.password;

    reposne.block = true;
    reposne.user = userBlock;

    this._loginService.blockingUserByIntents(reposne).subscribe(
      (result: any) => {
        this._data.setGeneralNotificationMessage(result.message);
      },
      (error) => {
        this._data.setGeneralNotificationMessage(error.message);
      },
      () => (this.enable = true)
    );
  }

  openForgetPassword() {
    this._route.navigate([routesWeb.NEW_USER], {
      queryParams: {
        website: parameters.WEB,
      },
    });
  }

  goHome(response) {
    this._localStorage.addItem(environment.ANSWER_LOGIN, response);
    localStorage.setItem('tenantId', response.tenantId || 'dchkw');
    this._loginService.setIsLogged(true);
    this._route.navigate([routesWeb.HOME]);
  }

  ngOnDestroy() {}
}
