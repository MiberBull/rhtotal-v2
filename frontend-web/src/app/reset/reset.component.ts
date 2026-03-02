import { Component, OnInit, OnDestroy } from '@angular/core';
import { ResetService } from '../services/reset-password/reset.service';
import { Subscription } from 'rxjs';
import { FormControl, Validators } from '@angular/forms';
import { DataService } from '../services/data.service';
import { MatDialog } from '@angular/material';
import { DialogLoadingComponent } from '../components/dialog-loading/dialog-loading.component';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css', '../../assets/custom.css']
})

export class ResetComponent implements OnInit {

  isCorrectResponse: boolean = false
  isCorrectCaptcha: boolean = false
  serviceSubiscribe: Subscription
  dialogRef:any;

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor( private _resetService: ResetService,private dialog: MatDialog,private _dataService:DataService) {
   
  }

  ngOnInit() {

  }

  send = (event) => {
    
    if (this.isCorrectCaptcha) {
      if (!this.emailFormControl.hasError('email') &&
          !this.emailFormControl.hasError('required')) {
            this._dataService.setIsLoadingEvent(true);
          this.serviceSubiscribe = this._resetService.requestResetCode(this.emailFormControl.value)
          .subscribe((response) => {
            this.isCorrectResponse = true
          }, (error) => {
            this._dataService.setIsLoadingEvent(false);
            this.isCorrectResponse = false
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

}
