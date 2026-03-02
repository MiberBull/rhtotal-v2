import { ComponentFixture, async, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { RecaptchaModule } from 'ng-recaptcha';

import { ForgetPasswordModal } from './forget-password.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { LoginMockService } from '../../mock-services/login.service.mock';
import { LoginService } from '../../services/login/login.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('ForgetPasswordComponent', () => {
    
    let component: ForgetPasswordModal;
    let fixture: ComponentFixture<ForgetPasswordModal>;
  
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ 
          ForgetPasswordModal
        ],
        imports:[
          ReactiveFormsModule,
          MATERIAL_COMPONENTS,
          RecaptchaModule.forRoot(),
          MatDialogModule,
          HttpClientModule,
          BrowserAnimationsModule,
          RouterTestingModule.withRoutes([])
        ],
        providers:[
            { provide: MatDialogRef, useValue: {} },
            { provide:LoginService, useClass:LoginMockService }
        ]
      })
      .compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(ForgetPasswordModal);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should create a form', () => {
      expect( component.form.contains('email') ).toBeTruthy();
    });

    it('should validate the recaptcha flag', () => {
      component.resolved('CAPCHA');   
      let validateCaptcha = component.validator; 
      expect( validateCaptcha ).toBeTruthy();
    });
  
  });