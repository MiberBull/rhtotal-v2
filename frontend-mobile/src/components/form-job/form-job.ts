import { Component, Input, Output, EventEmitter } from '@angular/core';
//import { Keyboard } from '@ionic-native/keyboard';
import { FormArray } from '@angular/forms';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { BrMaskerIonic3 } from 'brmasker-ionic-3';

@Component({
  selector: 'form-job',
  templateUrl: 'form-job.html',
  providers: [BrMaskerIonic3]
})
export class FormJobComponent {

  @Input("form-job")
  formInfo: FormArray;
  @Input("form-group")
  formGroup;
  @Input("show-divider")
  showDivider:boolean = false;
  @Input('date-now')
  dateNow:string;
  @Input('disabled-second-date')
  disableSelector:boolean = true;
  @Input('min-date')
  minDate:any;

  @Output("delete-from")
  deleteForm = new EventEmitter<any>();
  

  messages:any = {
    employeePosition:{
      error:null
    },
    company:{
      error:null
    },
    bossName:{
      error:null
    },
    bossEmail:{
      error:null
    },
    bossTelephone:{
      error:null
    },
    assigmentDtartDate:{
      error:null
    },
    assigmentEndDate:{
      error:null
    },
    qtSalary:{
      error:null
    },
    assignmentEmail:{
      error:null
    },
    professionalResume:{
      error:null
    }
  };

  checkDelete:boolean;

  constructor( private events_manager: EventsManagerProvider,
              //private keyboard: Keyboard 
              ) {

    this.events_manager.getIsRequired().subscribe( data => {
      if(data) {
        this.hashErrorRequireds();
      }
    });

  }

  selectedFirstDate($event) {
    this.minDate = this.formInfo.controls["assigmentDtartDate"].value;  
    this.disableSelector = false;
    this.messages['assigmentDtartDate'].error = '';
    this.formInfo.controls["assigmentEndDate"].setValue("");
  }

  validateControl(control:string) {
    if(this.formInfo.controls[control].hasError('required')) {
      this.messages[control].error = 'La información es requerida';
      return;
    }
    if(!this.formInfo.controls[control].valid) {
      this.messages[control].error = 'bossTelephone' != control ? 'Formato incorrecto' : 'Teléfono a 10 dígitos';
    } else {
      this.messages[control].error = '';
    }
  }



  hashErrorRequireds() {
    if(this.formInfo.controls['employeePosition'].hasError('required')) {
      this.messages['employeePosition'].error = 'La información es requerida';
    }
    if(this.formInfo.controls['company'].hasError('required')) {
      this.messages['company'].error = 'La información es requerida';
    }
    if(this.formInfo.controls['assigmentDtartDate'].hasError('required')) {
      this.messages['assigmentDtartDate'].error = 'La información es requerida';
    }
    if(this.formInfo.controls['assigmentEndDate'].hasError('required')) {
      this.messages['assigmentEndDate'].error = 'La información es requerida';
    }
  }

  checkDeleteForm() {
    this.deleteForm.emit( { form:this.formInfo,check:this.checkDelete } );
  } 

}

