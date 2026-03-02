import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription } from 'rxjs';
import { FormControl, Validators } from '@angular/forms';
import { RolService } from '../../services/roles/rol.service';
import { DataService } from '../../services/data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { routesWeb, parameters } from '../../../environments/environment';
import { MatDialog } from '@angular/material';


@Component({
    selector:'app-new-user',
    templateUrl:'./new-user.component.html',
    styleUrls:['./new-user.component.css']
})

export class NewUserComponent implements OnInit{
     
  isCorrectResponse: boolean = false
  isCorrectCaptcha: boolean = false
  serviceSubiscribe: Subscription
  dialogRef:any;
  emailAst:string ="";
  routerSubscription: Subscription;
  website:string;
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor( private _resetService: RolService,  
               private _dataService: DataService, 
               private router:Router,
               private _router: ActivatedRoute) {

    this.routerSubscription = this._router.queryParams.subscribe(
      (response:any) => {
        this.website = response.website;
      },
      (error) => {
        this._dataService.setIsLoadingEvent(false);
      },
      () => {
        this.routerSubscription.unsubscribe()
        this._dataService.setIsLoadingEvent(false);
      }
    )

  }

  ngOnInit() {

  }

  send = (event) => {
    if (this.isCorrectCaptcha) {
      if (!this.emailFormControl.hasError('email') &&
          !this.emailFormControl.hasError('required')) {
           this._dataService.setIsLoadingEvent(true);
           this.emailAst = this.emailFormControl.value;
           var cadena = this.emailAst.substring(1,this.emailAst.indexOf("@")-2);
           var reempl ="";
           for (var i = 0; i < cadena.length; i++) {
              reempl=reempl +"*";             
           }
           this.emailAst = this.emailAst.replace(cadena,reempl);
           this.serviceSubiscribe = this._resetService.requestResetCode(this.emailFormControl.value,this.website)
          .subscribe((response) => {
            this.isCorrectResponse = true
          }, (error) => {
            this._dataService.setIsLoadingEvent(false);
            this.isCorrectResponse = false
            if( this.website === parameters.WEB ){
              this._dataService.setGeneralNotificationMessage('El correo no se encuentra dentro de nuestros registros');
            } else {
              this._dataService.setGeneralNotificationMessage(`Correo electrónico no encontrado`);
            }
          }, () => {
            this._dataService.setIsLoadingEvent(false);
            this.serviceSubiscribe.unsubscribe()
          }
          )
      }

    }
  }



  resolvedCaptcha = (captchaResponse: string) => {
    if (captchaResponse.length > 0 && this.emailFormControl) {
      this.isCorrectCaptcha = true
    }
  }

  computeButtonState = () => {
    if (this.isCorrectCaptcha && this.emailFormControl.valid) {
      return true
    }
    return false
  }

  cancelar =() =>{
    this.router.navigate([`${routesWeb.LOGIN}`]);
  }

}
