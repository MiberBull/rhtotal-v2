import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PlanCoverageTO } from '../../../models/plan-coverage.model';
import { InsuranceService } from '../../../services/insurance/insurance.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { Information } from '../../../util/date';
import { DECLARATION, EXPRESSION, MSG } from '../../../../environments/environment.prod';
import { DataService } from '../../../services/data.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-plan-coverage',
  templateUrl: './plan-coverage.component.html',
  styleUrls: ['./plan-coverage.component.css']
})
export class PlanCoverageComponent implements OnInit {

  status: any[] = [
    { value: 'A', title: 'Activo' },
    { value: 'I', title: 'Inactivo' }
  ];  

  planCoverageFrom:FormGroup;

  planCoverage = new PlanCoverageTO();
  title:string;
  coverageTO = new PlanCoverageTO();
  idInsurance:string;

  showEstado:boolean = false

  msgValidEventualy:string;

  constructor(
          public dialogRef: MatDialogRef<PlanCoverageComponent>,
          @Inject(MAT_DIALOG_DATA) public data: any,
          private _fb: FormBuilder,
          private _insuranceService: InsuranceService,
          private _localStorage: LocalStorageService,
          private _dataService: DataService,
          public dialog: MatDialog) {

       this.planCoverageFrom = this._fb.group({
         titleEventualy: [DECLARATION.EMPTY_INPUT, [Validators.required]],
         description: [DECLARATION.EMPTY_INPUT, [Validators.required]],
         level: [DECLARATION.EMPTY_INPUT, [Validators.required]],
         sumAssured: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_NINE)]],
         deductibles: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_TWO)]],
         coInsurance: ['', [Validators.required, Validators.pattern(EXPRESSION.DECIMAL_ONE_TWO)]],
         status:['']
       });

       this.planCoverage = data.eventualy;
       this.title = data.title;
       this.idInsurance = data.idInsurance ? data.idInsurance : null;
  }

  ngOnInit() {

    console.log("DATOS COVERAGE ", this.planCoverage);
    
    if (this.planCoverage){

      this._insuranceService.getOnePlanCoverage(this.planCoverage.idCobertura.toString()).subscribe((resp: PlanCoverageTO) => {
          this.planCoverage = resp;
          this.planCoverageFrom.patchValue(resp);
          this.planCoverageFrom.controls['status'].setValue(resp.status);
          this.showEstado = true;
      });
      
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }

  saveUpdateCoverage(){

    let isAcept: boolean = true;

    if (this.planCoverage){
      this.coverageTO.idCobertura = this.planCoverage.idCobertura;
      this.coverageTO.status = this.planCoverageFrom.get('status').value;
    }else{
      this.coverageTO.status = 'A';       
    }
    
    this.coverageTO.idInsurance = this.idInsurance == null ? this.planCoverage.idInsurance : Number(this.idInsurance);
    this.coverageTO.titleEventualy = this.planCoverageFrom.get('titleEventualy').value;
    this.coverageTO.description = this.planCoverageFrom.get('description').value;
    this.coverageTO.level = this.planCoverageFrom.get('level').value;
    this.coverageTO.sumAssured = this.planCoverageFrom.get('sumAssured').value;
    this.coverageTO.deductibles = this.planCoverageFrom.get('deductibles').value;
    this.coverageTO.coInsurance = this.planCoverageFrom.get('coInsurance').value;    
    this.coverageTO.lastUserModifier = this._localStorage.getUser();
    this.coverageTO.lastModification = Information.getCurrentDate();
    this.coverageTO.creationDate = Information.getCurrentDate();
    this.coverageTO.fgActive = true;


    if (this.planCoverage) {
        if (this.planCoverageFrom.get('status').value !== this.planCoverage.status){
          let estado = this.planCoverageFrom.get('status').value == 'A' ? 'Activar' :
                       (this.planCoverageFrom.get('status').value == 'I' ? 'Inactivar' : '');
          const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
            disableClose: true,
            width: '350px',
            height: '200px',
            data: { status: estado, title: 'la Eventualidad' }
          });

          dialogRef.afterClosed().subscribe(resp => {
            console.log("status ", resp.confirStatus);
            isAcept = resp.confirStatus;
            if (isAcept) {
              this.msgValidEventualy = MSG.MSG_CONFIR_STATUS_INSURANCE;
              this.saveConfirm();              
            }
          });                      
        }else{
          this.msgValidEventualy = MSG.MSG_SAVE_UPDATE;
          this.saveConfirm();          
        }
    }else {
      this.msgValidEventualy = MSG.MSG_SAVE_UPDATE;
      this.saveConfirm();      
    }

  }

  saveConfirm(){
      this._insuranceService.saveUpdatePlanCoverage(this.coverageTO).subscribe(resp => {
        console.log("saveUpdate ", resp);
        this._dataService.setIsLoadingEvent(true);
        //this._dataService.setGeneralNotificationMessage(this.msgValidEventualy);
        this.dialogRef.close(
          {
            idInsurance: this.coverageTO.idInsurance,
            msgEventualy: this.msgValidEventualy
          }
        );
      },
      error => {
        this._dataService.setIsLoadingEvent(false);
      });
  }

}
