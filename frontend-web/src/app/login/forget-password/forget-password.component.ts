import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { MatDialogRef } from "@angular/material";

import { LoginService } from "../../services/login/login.service";
import { parameters } from './../../../environments/environment';
import { environment } from "../../../environments/environment";

@Component({
    selector:'forget-password',
    templateUrl:'./forget-password.component.html',
    styleUrls:['./forget-password.component.css']
})

export class ForgetPasswordModal implements OnInit{
  
    form:FormGroup;
    validator:boolean;
    userValid:boolean = true;
    showForm:boolean = true;

  
    constructor( 
        public _modal:MatDialogRef<ForgetPasswordModal>,
        private _formBuilder: FormBuilder,
        private _loginService: LoginService ){
        this.validator = false;
    }

    ngOnInit(){
        this.form = this._formBuilder.group({
            email:[ environment.EMPTY_INPUT ,[ Validators.required,
                                               Validators.email,
                                               Validators.maxLength(environment.LONG_EMAIL)]]
        });
    }

    resolved( captchaResponse:string ) {
        if( captchaResponse ) this.validator = !this.validator;        
    }

    onSubmit(){
        if( this.validator && this.form.valid ){
            this.validator = false;
            this._loginService
                .sendEmailWithTemplate( this.form.value.email,parameters.FORGET_PASS )
                .subscribe( resp => {
                    if(resp){
                        this.showForm = false;
                    }else{
                        this.userValid = false;
                        this.validator = true;
                    }
                },error => {
                    console.log(error);
                });
        }
    }

    onClose(){
        this._modal.close();
    }

    changeEmail() {
        this.userValid = true;
    }

}