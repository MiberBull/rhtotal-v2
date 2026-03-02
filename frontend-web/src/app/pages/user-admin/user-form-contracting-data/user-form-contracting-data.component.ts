import { Component, OnInit,ViewChild,ElementRef,Renderer2 } from '@angular/core';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { environment, MSG ,EXPRESSION} from '../../../../environments/environment.prod';
import { EmployeeContratingTO, EmployeeTO ,EmployeeUserTO} from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import {UserTO} from '../../../models/user.model';
import { Router } from '@angular/router';
import { resultJob, data} from '../../../models/sico.model';
import { Location } from '@angular/common';
@Component({
  selector: 'app-user-form-contracting-data',
  templateUrl: './user-form-contracting-data.component.html',
  styleUrls: ['./user-form-contracting-data.component.css','../../../../assets/custom.css']
})
export class UserFormContractingDataComponent implements OnInit {
 
  employeeContrating:EmployeeContratingTO;
  formInfoData: FormGroup;
  userAdmin:UserTO;
  idUserEmployee:number;
  showSaveUpdate: boolean = true;
  datosSico:boolean=false;
  @ViewChild('inputFile_1') inputFile_1: ElementRef<any>;

  isDisableForm:boolean=false;

  constructor(
         private fb: FormBuilder,
         private _data: DataService,
         private _userService: UserService,
        private _localStorage: LocalStorageService,
        private render:Renderer2,
        private location: Location,
        private router:Router) { 
          
    this._userService.setTabLabel('datos-contratacion');
    this._userService.setActivePhotography(false);
    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    
      this.datosSico=_userService.getDatosSico();

    
    this.employeeContrating= new EmployeeContratingTO();
    this.formInfoData = this.fb.group({

      qtSalary :['',[Validators.pattern(EXPRESSION.DECIMAL)]],
      dsArea :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      job:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      skill:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER)]],
      endOfContract:[''],
      cv:['']
      
    });

    
  

    //Desabilitacion de Formulario (Usuario Inactivo)
    this.isDisableForm = this._userService.getIsDisable();
    this.isDisableForm ? this.formInfoData.disable() : this.formInfoData.enable();    

    if(!_userService.getFormEnable())
    {
      this.formInfoData.disable();
    }

    this.idUserEmployee = _userService.getIdUserCurrently();
    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);

    });

    this._localStorage
    .getItem("answerLogin","user")
    .subscribe(user => {
      this.userAdmin=user;
    });

    this._data.setIsLoadingEvent(true);
    this._userService.getContratingByIdUser(this.idUserEmployee)
    .subscribe((resp:EmployeeContratingTO)=> {  

      if(this.datosSico==true)
      {
        this.fieldsReadonly();
      }

      
      
      this.employeeContrating=resp;
      this._data.setIsLoadingEvent(false);
    }, err => {
      this._data.setIsLoadingEvent(false);

      if(err.error.message =='No value present')
      {

        if(this.datosSico)
        {
          this._data.setIsLoadingEvent(true);
          this.fieldsReadonly();
          var params=_userService.getParamSico();
          this._userService.getWorkInfoSico(params.curp,params.name)
                  .subscribe((resp:resultJob)=> {
                    this._data.setIsLoadingEvent(false);
                    if(resp.total>0)
                    {
                       this.employeeContrating.job=resp.data.personal.puesto;
                      // let fecha = resp.data.pago[0].fechaContratacion.split('%2F');
                       //this.employeeContrating.endOfContract=new Date(parseInt(fecha[2]),parseInt(fecha[1]),parseInt(fecha[0]));
                       this.employeeContrating.qtSalary=resp.data.pago[0].salarioRealPercibidoMes;
                    }
                    
                  },err=> this._data.setIsLoadingEvent(false));
        }

      }
      else
      {
        this._data.setGeneralNotificationMessage( err.error.message );
      }
    
       });
  }

  ngOnInit() {
  }

  save(){
    this._data.setIsLoadingEvent(true);
    this.employeeContrating.idUser=this.idUserEmployee;


    if(this.employeeContrating.idContrating==null || this.employeeContrating.idContrating<1)
    {
      this.employeeContrating.creationUser=this.userAdmin.email;
    }
    this.employeeContrating.dsLastUserModifier=this.userAdmin.email;
    this._userService.saveOrUpdateEmployeeContrating(this.employeeContrating)
    .subscribe( response => {
        this._data.setIsLoadingEvent(false);
        if(response){
            this._data.setGeneralNotificationMessage(MSG.OK);
            this.router.navigate([`home/admin-usuario/ultimo-empleo`]);
        }else {
          this._data.setGeneralNotificationMessage("No se realizo ningún registro");
        }
    }, err => {
        this._data.setIsLoadingEvent(false);
        this._data.setGeneralNotificationMessage( err.error.message );
        console.log(err);
    });
  }
  test()
  {}
  fieldsReadonly()
  {
    this.formInfoData.get('qtSalary').disable();
   // this.formInfoData.get('dsArea').disable();
    this.formInfoData.get('job').disable();
   // this.formInfoData.get('endOfContract').disable();
}

showFileChosser() {
  this.render.selectRootElement( this.inputFile_1.nativeElement ).click();
}

getPdf(file:File) {
  console.log('size',file.size)
  if (file.size > 1048576 ) {
    this._data.setGeneralNotificationMessage("El archivo a cargar no cumple con el formato requerido, cargar un archivo en PDF y menor a 1 Mb'");
    return;
  }
  if (file) {
    const reader = new FileReader();
    this.employeeContrating.cv= file.name;
    reader.onload = this.handleReaderLoaded.bind(this);
    reader.readAsBinaryString(file);
  }    
}
handleReaderLoaded(e) {

  this.employeeContrating.cvPdf = "data:image/png;base64," + btoa(e.target.result);
}

  onBrokenChange(n: string) {
    var num = n.replace(/[$,]/g, "");
    return Number(num.toString);  
  }
}
