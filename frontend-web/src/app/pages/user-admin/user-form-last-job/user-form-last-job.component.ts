import { Component, OnInit } from '@angular/core';
import { EmployeeHitoryTO, EmployeeTO } from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { MSG, environment,EXPRESSION } from '../../../../environments/environment';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { UserTO } from '../../../models/user.model';
import { Router } from '@angular/router';
import { Location } from '@angular/common'
import { Information } from '../../../util/date';
@Component({
  selector: 'app-user-form-last-job',
  templateUrl: './user-form-last-job.component.html',
  styleUrls: ['./user-form-last-job.component.css','../../../../assets/custom.css']
})
export class UserFormLastJobComponent implements OnInit {
  employeeHistory:EmployeeHitoryTO;
  showSaveUpdate:boolean=true;
  formInfoData: FormGroup;
  userAdmin:UserTO;
  idUserEmployee:number;

  isDisableForm:boolean=false;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private _data: DataService,
    private _userService: UserService,
    private _localStorage: LocalStorageService,
    private router:Router
  ) { 

    this._userService.setTabLabel('ultimo-empleo');
    this._userService.setActivePhotography(false);
    
    let fDate= new Date;
    this.employeeHistory= new  EmployeeHitoryTO();
    this.employeeHistory.endDate=fDate;
    this.employeeHistory.entryDate=fDate;
    this.employeeHistory.qtSalary="";
    this.employeeHistory.dsCompany="";
    this.employeeHistory.dsArea="";
    this.formInfoData = this.fb.group({
      qtSalary :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.DECIMAL),Validators.maxLength(12)]],
      dsCompany :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      dsEmployeePosition:[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      dsIndustry  :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      qtDependets :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER),Validators.maxLength(4)]],
      dsArea  :[environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      entryDate:[''],
      endDate:[''],
      benefitsLawBool:[''],
      aditionalBenefitsBool:['']

        });

        //Desabilitacion de Formulario (Usuario Inactivo)
        this.isDisableForm = this._userService.getIsDisable();
        this.isDisableForm ? this.formInfoData.disable() : this.formInfoData.enable();

        this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;
        this.idUserEmployee = _userService.getIdUserCurrently();

        if(!_userService.getFormEnable())
        {
          this.formInfoData.disable();
        }
    this._localStorage
    .getItem("answerLogin","user")
    .subscribe(user => {
      this.userAdmin=user;
    });

    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);

    });
    this._userService.getHistory(this.idUserEmployee)
    .subscribe((resp:EmployeeHitoryTO)=> {   
      this.employeeHistory=resp;
      this.employeeHistory.aditionalBenefitsBool=this.convertStringToBool(resp.aditionalBenefits); 
      this.employeeHistory.benefitsLawBool=this.convertStringToBool(resp.benefitsLaw); 
      this._data.setIsLoadingEvent(false);
    }, err => {
      this._data.setIsLoadingEvent(false);
      if(err.error.message !='No value present')
       {
        this._data.setGeneralNotificationMessage( err.error.message );
       }
        });

 
  }

  ngOnInit() {
  }
  save()
  {
    this._data.setIsLoadingEvent(true);
      this.employeeHistory.idUser=this.idUserEmployee;
      this.employeeHistory.benefitsLaw=this.convertBoolToString(this.employeeHistory.benefitsLawBool);
      this.employeeHistory.aditionalBenefits=this.convertBoolToString(this.employeeHistory.aditionalBenefitsBool) ;
    if(this.employeeHistory.idEmployeeHis<1)
    {
      this.employeeHistory.creationUser=this.userAdmin.email;
    }
    this.employeeHistory.lastUserModifier=this.userAdmin.email;

    if(this.validInput()){
      this._userService.saveOrUpdateEmployeeHistory(this.employeeHistory)
        .subscribe(response => {
          this._data.setIsLoadingEvent(false);

          this._data.setGeneralNotificationMessage(MSG.OK);
          this.router.navigate([`home/admin-usuario/paquete-compensacion`]);
        }, err => {
          this._data.setIsLoadingEvent(false);
          this._data.setGeneralNotificationMessage(err.error.message);
          console.log(err);

        });        
    }else {
      this._data.setIsLoadingEvent(false);
    }  


  }

convertStringToBool(value:String)
{

return value ==='0'?false : value ==='2'?true:null; 
}

convertBoolToString(value:boolean)
{
  return value===true?'2':value===false?'0':'1';
}

  validInput(){
    let isValidInputs:boolean = true;

    isValidInputs = isValidInputs && this.validDateStartEnd();

    return isValidInputs;
  }

  validDateStartEnd(){

    let isValidDate:boolean = true;
    
    if (this.employeeHistory.endDate && this.employeeHistory.entryDate && Information.dateUsers(this.employeeHistory.entryDate.toString(), this.employeeHistory.endDate.toString())){

      this._data.setGeneralNotificationMessage(MSG.ERROR_DATE_USERS);
      isValidDate = false;

    }

    if (this.employeeHistory.endDate && !(this.employeeHistory.entryDate)){
      this._data.setGeneralNotificationMessage(MSG.NOTIFICATION_START_END_USERS);
      isValidDate = false;
    }

    return isValidDate;
  }

}
