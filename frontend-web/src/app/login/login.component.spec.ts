import { DataService } from './../services/data.service';
import { LocalStorageService } from './../services/local-sotorage/localstorage.service';
import { RecaptchaModule } from 'ng-recaptcha';
import { ForgetPasswordModal } from './forget-password/forget-password.component';
import { LoginTO } from './../models/user.model';
import { MATERIAL_COMPONENTS } from './../app.material';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { LoginService } from './../services/login/login.service';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { LoginComponent } from './login.component';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginMockService } from '../mock-services/login.service.mock';
import { MatDialog } from '../../../node_modules/@angular/material';


describe('LoginComponent', () => {
  
  const email = 'urbano@axity.com';
  const access = '12345678';
  
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        LoginComponent,
        ForgetPasswordModal
      ],
      imports:[
        ReactiveFormsModule,
        MATERIAL_COMPONENTS,
        BrowserAnimationsModule,
        RouterTestingModule.withRoutes([]),
        RecaptchaModule.forRoot()
      ],
      providers:[
        { provide:LoginService, useClass: LoginMockService},
        HttpClient,
        HttpHandler,
        FormBuilder,
        LocalStorageService,
        DataService,
        MatDialog
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a form', () => {
    expect( component.formLogin.contains('email') ).toBeTruthy();
    expect( component.formLogin.contains('password') ).toBeTruthy();
  });

  it('should validate a email', () => {
    let result = component.validateEmail(email);
    expect(result).toBe(true); 
  });
  
  it('should validate a bad email', () => {
    let result = component.validateEmail(email);
    expect(result).toBe(false); 
  });

  it('shoul variable the intents', () => {
    component.intents = 3;
    let intentIncrement = component.decreaseIntents();
    expect(intentIncrement).toEqual(2);
  });

  it('shoul call the function onLogin()', () => {
    component.formLogin.setValue({email:email,password:access});

    let nativeSpy = spyOn( component, 'onLogin' );
    component.onSubmit();

    expect(nativeSpy).toHaveBeenCalled();
    
  });

  it('should subtract an attempt ', () => {
    component.intents = 5;
    let response:LoginTO = new LoginTO();
    response.flag = 2;
    component.optionsResponse(response);
    expect(component.intents).toBe(4);
  });

});
 