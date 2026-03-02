import { Component } from '@angular/core'
import { ResetService } from '../services/reset-password/reset.service'
import { Subscription } from 'rxjs'
import { FormControl, Validators, FormGroup, FormBuilder, FormGroupDirective, NgForm, AbstractControl } from '@angular/forms'
import CryptoJS from 'crypto-js'
import { ErrorStateMatcher } from '@angular/material'
import { ActivatedRoute } from '@angular/router'
import { SECRETS, EXPRESSION } from '../../environments/environment';
import { DataService } from '../services/data.service';

class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty)
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty)
    return (invalidCtrl || invalidParent)
  }
}


@Component({
  selector: 'app-reset-confirmation',
  templateUrl: './reset.confirmation.component.html',
  styleUrls: ['./reset.confirmation.component.css', '../../assets/custom.css']
})

export class ResetConfirmationComponent {

  isCorrectResponse: boolean = false
  isLoading: boolean = false
  isAnError: boolean = false
  isPetitionInvalid: boolean = false
  resetSubscription: Subscription
  routerSubscription: Subscription
  myForm: FormGroup
  matcher = new MyErrorStateMatcher()
  queryParams
  errorMessage: string = ''
  correctMessage: string = ''
  type:number;

  constructor(
    private _resetService: ResetService, 
    private _formBuilder: FormBuilder,
    private _dataService:DataService,
    private _router: ActivatedRoute) {
    
    this.routerSubscription = this._router.queryParams.subscribe(
      (response) => {
        this.queryParams = response
        if (!this.queryParams.hasOwnProperty('token') || !this.queryParams.hasOwnProperty('user')) {
          this.isPetitionInvalid = true
        }

        if(this.queryParams.token === '' || this.queryParams.user === '') this.isPetitionInvalid = true
      },
      (error) => {
        this._dataService.setIsLoadingEvent(false);
      },
      () => {
        this.routerSubscription.unsubscribe()
        this._dataService.setIsLoadingEvent(false);
      }
    )
    this.myForm = this._formBuilder.group({
      passwordFormControlName: [
        '',
        [Validators.required, Validators.minLength(8), Validators.maxLength(50),Validators.pattern(EXPRESSION.PASSWORD)]
      ],                                                                                        
      passwordConfirmedFormControlName: [
        '',
        [Validators.required, Validators.minLength(8), Validators.maxLength(50)]
      ]
    }, { validator: [this.checkPasswords] }
  ,)
  }



  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    const pass = group.controls.passwordFormControlName.value
    const confirmPass = group.controls.passwordConfirmedFormControlName.value
    
    return pass === confirmPass ? null : confirmPass===''? { notSame: true }:{notEquals:true}
  }

  submit = (event) => {
    this._dataService.setIsLoadingEvent(true);
    this.errorMessage = ''
    this.isAnError = false
   // this.isLoading = true
    const values = this.myForm.getRawValue()
    const newPasswordEncrypted = CryptoJS.AES.encrypt(
      values.passwordFormControlName, SECRETS.AES_PASSWORD_SECRET
    ).toString()

    const newPasswordConfirmedEncrypted = CryptoJS.AES.encrypt(
      values.passwordConfirmedFormControlName, SECRETS.AES_PASSWORD_SECRET
    ).toString()  
    
    this.resetSubscription = this._resetService.sendResetPassword(
      this.queryParams.token,
      this.queryParams.user,
      newPasswordEncrypted,
      newPasswordConfirmedEncrypted,
      this.queryParams.type
    ).subscribe(
      (response) => {
        this.isCorrectResponse = true
        this._dataService.setIsLoadingEvent(false);
        //this.correctMessage = 'La contraseña ha sido actualizada correctamente'
        this._dataService.setGeneralNotificationMessage('La contraseña ha sido actualizada correctamente');
      },
      (error) => {
        this._dataService.setIsLoadingEvent(false);
        this.isAnError = true
        this._dataService.setGeneralNotificationMessage('Ocurrió un error, por favor vuelve a intentar.');
        //this.errorMessage = 'Ocurrió un error, por favor vuelve a intentar.'
      },
      () => {
        this._dataService.setIsLoadingEvent(false);
        this.resetSubscription.unsubscribe()
      }
    )


  }

}
