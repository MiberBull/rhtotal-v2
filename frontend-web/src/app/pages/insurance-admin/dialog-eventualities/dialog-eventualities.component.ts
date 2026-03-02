import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EventualyTO } from '../../../models/eventualy.model';
import { InsuranceService } from '../../../services/insurance/insurance.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { Information } from '../../../util/date';
import { DECLARATION, EXPRESSION, MSG } from '../../../../environments/environment.prod';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { DataService } from '../../../services/data.service';


@Component({
  selector: 'app-dialog-eventualities',
  templateUrl: './dialog-eventualities.component.html',
  styleUrls: ['./dialog-eventualities.component.css']
})
export class DialogEventualitiesComponent implements OnInit {

  status: any[] = [
    { value: 'A', title: 'Activo' },
    { value: 'I', title: 'Inactivo' }    
  ];

  eventualyFrom:FormGroup;

  eventualy = new EventualyTO();
  title:string;
  eventualyTO = new EventualyTO();
  idInsurance: string;

  showEstado:boolean = false;

  msgCoverage:string;

  constructor(
          public dialogRef: MatDialogRef<DialogEventualitiesComponent>,
          @Inject(MAT_DIALOG_DATA) public data: any,
          private _fb: FormBuilder,
          private _insuranceService:InsuranceService,
          private _localStorage: LocalStorageService,
          public dialog: MatDialog,
         private _dataService: DataService) { 

       this.eventualyFrom = this._fb.group({
         titleEventualy: [DECLARATION.EMPTY_INPUT, [Validators.required]],
         description: [DECLARATION.EMPTY_INPUT, [Validators.required]],
         sumAssured: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_NINE)]],
         securedPremium: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_NINE)]],
         deductibles: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_TWO)]],
         status:['']
       });
       this._dataService.setIsLoadingEvent(true);

       this.eventualy = data.eventualy;
       this.title = data.title;
       this.idInsurance = data.idInsurance ? data.idInsurance : null;

    }

  ngOnInit() {
    console.log("DATOS DIALOGO EVENTUALY ", this.eventualy);

    if (this.eventualy){
      this._insuranceService.getOneEventualy(this.eventualy.idEventualy.toString()).subscribe((resp: EventualyTO) => {
          this.eventualy = resp;
          this.eventualyFrom.patchValue(resp);
          this.eventualyFrom.controls['status'].setValue(resp.status);
          this.showEstado = true;
          this._dataService.setIsLoadingEvent(false);
          console.log(resp);
          
      });
    }
    this._dataService.setIsLoadingEvent(false);    
  }

  closeDialog(){
    

    this.dialogRef.close();
  }

  saveUpdateEventualy(){
    
    let isAcept:boolean=true;

    if (this.eventualy){
      this.eventualyTO.idEventualy = this.eventualy.idEventualy;
      this.eventualyTO.status = this.eventualyFrom.get('status').value;
    }else{
      this.eventualyTO.status = 'A';
    }
    
    this.eventualyTO.idInsurance = this.idInsurance == null ? this.eventualy.idInsurance : Number(this.idInsurance);
    this.eventualyTO.titleEventualy = this.eventualyFrom.get('titleEventualy').value;
    this.eventualyTO.description = this.eventualyFrom.get('description').value;
    this.eventualyTO.sumAssured = this.eventualyFrom.get('sumAssured').value;
    this.eventualyTO.securedPremium = this.eventualyFrom.get('securedPremium').value;
    this.eventualyTO.deductibles = this.eventualyFrom.get('deductibles').value;    
    this.eventualyTO.lastUserModifier = this._localStorage.getUser();
    this.eventualyTO.lastModification = Information.getCurrentDate();
    this.eventualyTO.creationDate = Information.getCurrentDate();
    this.eventualyTO.fgActive = true;
    
    if (this.eventualy){
      if (this.eventualyFrom.get('status').value !== this.eventualy.status){
        let estado = this.eventualyFrom.get('status').value == 'A' ? 'Activar' : 
                     (this.eventualyFrom.get('status').value == 'I' ? 'Inactivar' : '');
                     
        const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
            disableClose: true,
            width: '350px',
            height: '200px',
            data: { status: estado, title: 'la Eventualidad' }
          });

        dialogRef.afterClosed().subscribe(resp => {
          console.log("idInsurance ", resp.confirStatus);
          isAcept = resp.confirStatus;  
          if(isAcept){
             this.msgCoverage = MSG.MSG_CONFIR_STATUS_INSURANCE;
             this.saveConfirm();             
          }        
        }); 
      }else{
        this.msgCoverage = MSG.MSG_SAVE_UPDATE;
        this.saveConfirm();        
      }
    }else {
      this.msgCoverage = MSG.MSG_SAVE_UPDATE;
      this.saveConfirm();      
    }
  }

  saveConfirm(){
      this._insuranceService.saveUpdateEventualy(this.eventualyTO).subscribe(resp => {
        console.log("save ", resp);
        this._dataService.setIsLoadingEvent(true);
        //this._dataService.setGeneralNotificationMessage(this.msgCoverage);
        this.dialogRef.close(
          {
            idInsurance: this.eventualyTO.idInsurance,
            msgEventualy: this.msgCoverage
          }
        );
      },
      error => {
        this._dataService.setIsLoadingEvent(false);
      });
  }

}

