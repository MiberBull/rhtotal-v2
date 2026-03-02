import { Component, OnInit, Input, Output, EventEmitter, OnDestroy, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { InsuranceTableTO } from '../../../models/insurance-table.model';
import { NotificationService } from '../../../services/notification/notification.service';
import { InsuranceService } from '../../../services/insurance/insurance.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { Information } from '../../../util/date';
import { DECLARATION, EXPRESSION, MSG, routesWeb } from '../../../../environments/environment.prod';
import { DataService } from '../../../services/data.service';
import { NULL_EXPR } from '@angular/compiler/src/output/output_ast';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { InsuranceParametersTO } from '../../../models/insurance-parameters';



@Component({
  selector: 'app-vehicle-insurance',
  templateUrl: './vehicle-insurance.component.html',
  styleUrls: ['./vehicle-insurance.component.css']
})
export class VehicleInsuranceComponent implements OnInit {

  @ViewChild('inputFile') inputFilePDF: ElementRef<any>;

  @Input('insurance-table') insuranceTable: InsuranceTableTO;

  @Output('set-insurance') setInsuranceTO = new EventEmitter<InsuranceParametersTO>();
  @Output('enable-tree') enableTree = new EventEmitter<boolean>();

  insuranceFrom: FormGroup;

  insuranceTableTO = new InsuranceTableTO();
  insuranceParametersTO = new InsuranceParametersTO();

  nameFilePDF:string;
  base64FilePDF:string;
  numberFile:number=0;
  showStatus:boolean = false;
  status: any[] = [
    { value: 'A', title: 'Activo' },
    { value: 'I', title: 'Inactivo' }
  ];

  showSave:boolean=false;
  showEdit:boolean=false;
  showEstado:boolean=true;

  msgConfir:string;

  minDate = new Date();

  constructor(private _fb: FormBuilder,
    private _insuranceS: InsuranceService,
    private _localStorage: LocalStorageService,
    private render: Renderer2,
    private _dataService: DataService,
    private _router: Router,
    public dialog: MatDialog ) {

    this._dataService.setIsLoadingEvent(true);

    this.insuranceFrom = this._fb.group({
      policy: [DECLARATION.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      coverage: [DECLARATION.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      urlInsuranceCarrier: [DECLARATION.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.URL_VALID)]],
      insuranceCarrier: [DECLARATION.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      phoneInsuranceCarrier: ['', [Validators.required, Validators.pattern(EXPRESSION.NUMBER)]],
      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]],
      sum: ['',[Validators.required, Validators.pattern(EXPRESSION.DECIMAL)]],
      insurancePolicyPdf: [DECLARATION.EMPTY_INPUT, [Validators.required]],
      timePublication: ['',[Validators.required]],
      notificationTime: ['', [Validators.required]],
      notificationTitle: ['', [Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      notificationDetail: [DECLARATION.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      status:[DECLARATION.EMPTY_INPUT],
      //vehicleDescription:[DECLARATION.EMPTY_INPUT, [Validators.required]],
      typeVehicle: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      serialNumber: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      plates: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      year: ['',[Validators.pattern(EXPRESSION.NUMBER)]],
      marca: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      model: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      service: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      use: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      description: ['', [Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]]
    });

    this._insuranceS.getShowStatus().subscribe( resp => {
      this.changeStatus();
    });

    this._insuranceS.getEnabledForm().subscribe( resp => {
      this.enabledForm();
    });


   }

  ngOnInit() {
    if (this.insuranceTable){
      
      this._insuranceS.selectOneInsurance(this.insuranceTable.idInsurance).subscribe((resp: InsuranceTableTO) => {
          this.insuranceFrom.patchValue(resp);
          this.insuranceTable = resp;
          this.showStatus = true;
          this.showEstado = true;
          this.insuranceFrom.disable();
          console.log("INFO RECIBIDA ", this.insuranceTable);

          this.insuranceFrom.controls['insurancePolicyPdf'].setValue(resp.fileName);
          this.base64FilePDF = resp.insurancePolicyPdf;
          this._dataService.setIsLoadingEvent(false);
      });
          this.showSave = false;
          this.showEdit = true;
    }else {
      
      this.showSave = true;
      this.showEdit = false;
      this.showEstado = false;
      this._dataService.setIsLoadingEvent(false);
    }

  }

   saveUpdateInsurance(){
     this._dataService.setIsLoadingEvent(true);
     let isAcept: boolean = true;
     if(this.insuranceTable){
       this.insuranceTableTO.idInsurance = this.insuranceTable.idInsurance;
       this.insuranceTableTO.status = this.insuranceFrom.get('status').value;
     }else {
       this.insuranceTableTO.status = 'A';
     }
     this.insuranceTableTO.policy = this.insuranceFrom.get('policy').value;
     this.insuranceTableTO.coverage = this.insuranceFrom.get('coverage').value;
     this.insuranceTableTO.urlInsuranceCarrier = this.insuranceFrom.get('urlInsuranceCarrier').value;
     this.insuranceTableTO.insuranceCarrier = this.insuranceFrom.get('insuranceCarrier').value;
     this.insuranceTableTO.phoneInsuranceCarrier = this.insuranceFrom.get('phoneInsuranceCarrier').value;
     this.insuranceTableTO.startDate = this.insuranceFrom.get('startDate').value;
     this.insuranceTableTO.endDate = this.insuranceFrom.get('endDate').value;
     this.insuranceTableTO.sum = this.insuranceFrom.get('sum').value;
     this.insuranceTableTO.insurancePolicyPdf = this.base64FilePDF;
     this.insuranceTableTO.fileName = this.insuranceFrom.get('insurancePolicyPdf').value;
     this.insuranceTableTO.timePublication = this.insuranceFrom.get('timePublication').value;
     this.insuranceTableTO.notificationTime = this.insuranceFrom.get('notificationTime').value;
     this.insuranceTableTO.notificationTitle = this.insuranceFrom.get('notificationTitle').value;
     this.insuranceTableTO.notificationDetail = this.insuranceFrom.get('notificationDetail').value;
     //this.insuranceTableTO.vehicleDescription = this.insuranceFrom.get('vehicleDescription').value;
     this.insuranceTableTO.typeVehicle = this.insuranceFrom.get('typeVehicle').value;
     this.insuranceTableTO.serialNumber = this.insuranceFrom.get('serialNumber').value;
     this.insuranceTableTO.plates = this.insuranceFrom.get('plates').value;
     this.insuranceTableTO.year = this.insuranceFrom.get('year').value;
     this.insuranceTableTO.marca = this.insuranceFrom.get('marca').value;
     this.insuranceTableTO.model = this.insuranceFrom.get('model').value;
     this.insuranceTableTO.service = this.insuranceFrom.get('service').value;
     this.insuranceTableTO.use = this.insuranceFrom.get('use').value;
     this.insuranceTableTO.description = this.insuranceFrom.get('description').value;
     this.insuranceTableTO.idTypeInsurance = Number(this._insuranceS.getIdInsuranceTypeSelected());
     this.insuranceTableTO.lastUserModifier = this._localStorage.getUser();
     this.insuranceTableTO.lastModifier = Information.getCurrentDate();
     this.insuranceTableTO.creationDate = Information.getCurrentDate();

     if (this.insuranceTable) {
       if (this.insuranceFrom.get('status').value !== this.insuranceTable.status) {

         let estado = this.insuranceFrom.get('status').value == 'A' ? 'Activar' :
           (this.insuranceFrom.get('status').value == 'I' ? 'Inactivar' : '');

         const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
           disableClose: true,
           width: '350px',
           height: '200px',
           data: { status: estado, title: 'el Seguro' }
         });

         dialogRef.afterClosed().subscribe(resp => {
           console.log("idInsurance ", resp.confirStatus);
           isAcept = resp.confirStatus;
           if (isAcept) {
             this.msgConfir = MSG.MSG_CONFIR_STATUS_INSURANCE;
             this.saveConfirm();             
           }
         });
       } else {
         this.msgConfir = MSG.MSG_SAVE_UPDATE;
         this.saveConfirm();              
       }
     } else {
       this.msgConfir = MSG.MSG_SAVE_UPDATE;
       this.saveConfirm();           
     }       
   }

  saveConfirm(){
      if (this.validateInput()) {                       
        this.insuranceParametersTO.insuranceTable = this.insuranceTableTO;
        this.insuranceParametersTO.confirmationMessage = this.msgConfir;
        this.setInsuranceTO.emit(this.insuranceParametersTO);        
      }
  }


  showFileChosser(){

    if (this.insuranceFrom.disabled) {
      return;
    }

    this.render.selectRootElement(this.inputFilePDF.nativeElement).click();
  }

  getFilePDF(file:File){
    
    if (!(/\.(pdf)$/i).test(file.name)) {
      this.numberFile = 0;
      this._dataService.setGeneralNotificationMessage(MSG.FILE_PDF);
    }else {
      if (file && (file.size <= 2097152)){
         this.numberFile = 1;
         this.nameFilePDF = file.name;
         this.insuranceFrom.controls['insurancePolicyPdf'].setValue(this.nameFilePDF);
         const reader = new FileReader();
         reader.onload = this.handleReaderLoaded.bind(this);
         reader.readAsBinaryString(file);
       }else {
        this._dataService.setGeneralNotificationMessage(MSG.MSG_SIZE_FILE);
       }
    }
  }

  handleReaderLoaded(e) {
    this.base64FilePDF = ('data:application/pdf;base64,' + btoa(e.target.result));
  }

  validateInput(){
      let valitInput:boolean = true;

      valitInput = valitInput && this.validDateVigencia();
      valitInput = valitInput && this.validInputRequired();
      valitInput = valitInput && this.validHourPublication();
      valitInput = valitInput && this.validHourNotification();

      return valitInput;
  }
  valitFile(){
    let valitFIle:boolean = true;

    if (this.numberFile > 0){
      valitFIle = true;
    }else {
      valitFIle = false;
      this._dataService.setGeneralNotificationMessage(MSG.MESSAGE_PDF_VALID);
      this._dataService.setIsLoadingEvent(false);
    }
    return valitFIle;
  }

  validDateVigencia(){
    let validDate = true;
    let startDate = this.insuranceFrom.get('startDate').value;
    let endDate = this.insuranceFrom.get('endDate').value;
    
    if (endDate < startDate){
      this._dataService.setGeneralNotificationMessage('Ingrese un rango de fechas válido');
      validDate = false;
      this._dataService.setIsLoadingEvent(false);
    }

    return validDate;
  }

  validInputRequired(){
    let isValid:boolean = true;

    if (!this.insuranceFrom.valid){
      isValid = false;
      this._dataService.setGeneralNotificationMessage('Faltan campos requeridos');
      this._dataService.setIsLoadingEvent(false);
    }

    return isValid;
  }

  validHourPublication(){
    let isValid:boolean = true;
    if (Information.validateCurrentHour(this.insuranceFrom.get('startDate').value, this.insuranceFrom.get('timePublication').value)){
      isValid = false;
      this._dataService.setGeneralNotificationMessage('Hora de publicación no válida');
      this._dataService.setIsLoadingEvent(false);
    }
    return isValid;
  }

  validHourNotification(){
    let isValid:boolean = true;
    if (Information.validateCurrentHour(this.insuranceFrom.get('startDate').value, this.insuranceFrom.get('notificationTime').value)){
      isValid = false;
      this._dataService.setGeneralNotificationMessage('Hora de notificación no válida');
      this._dataService.setIsLoadingEvent(false);
    }

    return isValid;
  }

  showEditOrSave(){
    this.showSave = this.showSave ? false : true;
    this.showEdit = this.showEdit ? false : true;
    this.insuranceFrom.enable();
    this.enableTree.emit(false);
  }

  cancelSaveUpdate(){
    this._router.navigate([`${routesWeb.HOME}/${routesWeb.INSURANCE}`]);
  }

  changeStatus(){
    this.showEstado = true;
    this.insuranceFrom.controls['status'].setValue('A');
  }

  enabledForm(){
    this._dataService.setIsLoadingEvent(false);
    this.insuranceFrom.disable();
    this.showSave = false;
    this.showEdit = true;    
  }

}
