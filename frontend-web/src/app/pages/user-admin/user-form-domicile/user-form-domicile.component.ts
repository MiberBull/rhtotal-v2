import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder ,Validators} from '@angular/forms';
import { EmployeeDomicileTO,EmployeeTO,EmployeeUserTO } from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { UserTO } from '../../../models/user.model';
import { Router } from '@angular/router';
import { result, data} from '../../../models/sico.model';
import { Location } from '@angular/common'
import { environment,EXPRESSION,MSG } from '../../../../environments/environment';
@Component({
  selector: 'app-user-form-d',
  templateUrl: './user-form-domicile.component.html',
  styleUrls: ['./user-form-domicile.component.css']
})

export class UserFormDomicileComponent implements OnInit {

  showSaveUpdate:boolean=true;
  userAdmin:UserTO;
  idUserEmployee:number;
  idEmployee:number;

  datosSico:boolean=false;
  employeeDomicile:EmployeeDomicileTO;
  formInfoData:FormGroup;

  isDisableForm:boolean;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private _dataService: DataService,
    private _userService: UserService,
    private _localStorage: LocalStorageService,
    private router:Router
    ) { 

      this._userService.setTabLabel('domicilio');
      this._userService.setActivePhotography(false);
      this._dataService.setIsLoadingEvent(true);
    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this.idUserEmployee = _userService.getIdUserCurrently();
    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);

    });
    this.idEmployee=_userService.getIdEmployee();



    this._localStorage
.getItem("answerLogin","user")
.subscribe(user => {
  this.userAdmin=user;
});


    this.employeeDomicile= new EmployeeDomicileTO();
    this.employeeDomicile.street="";
    this.employeeDomicile.interiorNumber="";
    this.employeeDomicile.outDoorNumber="";
    this.employeeDomicile.city="";
    this.employeeDomicile.colony="";
    this.employeeDomicile.state="";
    this.employeeDomicile.postalCode="";

    this.formInfoData = this.fb.group({
      street:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      interiorNumber:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      outDoorNumber:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      colony:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      postalCode:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER)]],
      city:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      state:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      creationUser:[environment.EMPTY_INPUT],
      
    });

    this.isDisableForm = this._userService.getIsDisable();

    this.isDisableForm ? this.formInfoData.disable() : this.formInfoData.enable();


    if(!_userService.getFormEnable())
    {
      this.formInfoData.disable();
    }

    this.datosSico=_userService.getDatosSico();

    if(this.datosSico==true)
    {
      this.fieldsReadonly();
    }


    this._userService.getEmployeeDomicileByIdUser(this.idUserEmployee)
    .subscribe((resp:EmployeeDomicileTO)=> {   
      this.employeeDomicile=resp;
      this._dataService.setIsLoadingEvent(false);
    }, err => {   
       this._dataService.setIsLoadingEvent(false);

       if(err.error.message =='No value present')
       {
         this._userService.getEmployee(this.idUserEmployee)
         .subscribe((resp:EmployeeTO)=> {
           this.employeeDomicile.employee=resp;          

         });

      if(this.datosSico)
      {
      
        var params=_userService.getParamSico();
        this._userService.getEmployeeSico(params.name,params.lastName,params.birthDate)
                .subscribe((resp:result)=> {
                  if(resp.total>0)
                  {

                    this.employeeDomicile.interiorNumber=resp.data[0].direccion.numeroInterior;
                    this.employeeDomicile.outDoorNumber=resp.data[0].direccion.numeroExterior;
                    this.employeeDomicile.city=resp.data[0].direccion.delegacionMunicipio;
                    this.employeeDomicile.colony=resp.data[0].direccion.colonia;
                    this.employeeDomicile.postalCode=resp.data[0].direccion.cp;
                    this.employeeDomicile.street=resp.data[0].direccion.calle;
                    this.employeeDomicile.state=resp.data[0].direccion.estado;
                    this.fieldsReadonly();
                  }
                  
                },error=> console.log(error))
      }

       }
       else
       {
        this._dataService.setGeneralNotificationMessage( err.error.message );
       }

      } );
  
  }

  ngOnInit() {
  }

  save() {

    this._dataService.setIsLoadingEvent(true);

    this.employeeDomicile.lastUserModifier=this.userAdmin.email;
    if(this.employeeDomicile.id==null || this.employeeDomicile.id<1)
    {
      this.employeeDomicile.creationUser=this.userAdmin.email;
    }
        this._userService.saveOrUpdateEmployeeDomicile(this.employeeDomicile)
        .subscribe( response => {
          
            this._dataService.setIsLoadingEvent(false);
            if(response){
                          this._dataService.setGeneralNotificationMessage(MSG.OK);
                          this.router.navigate([`home/admin-usuario/redes-sociales`])
            }else {
              this._dataService.setGeneralNotificationMessage("No se realizo ningun registro");
            }
        }, err => {
            this._dataService.setIsLoadingEvent(false);
            this._dataService.setGeneralNotificationMessage( err.error.message );
            
        });
      }

      fieldsReadonly()
  {
    this.formInfoData.get('street').disable();
    this.formInfoData.get('outDoorNumber').disable();
    this.formInfoData.get('interiorNumber').disable();
    this.formInfoData.get('colony').disable();
    this.formInfoData.get('city').disable();
    this.formInfoData.get('state').disable();
    this.formInfoData.get('postalCode').disable();
}

}
