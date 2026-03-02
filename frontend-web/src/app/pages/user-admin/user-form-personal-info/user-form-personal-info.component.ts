import { Component, OnInit, OnDestroy,Renderer2,ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { DECLARATION,environment,EXPRESSION,MSG } from '../../../../environments/environment';
import { EmployeeComplementaryTO, EmployeeTO ,EmployeeUserTO,CityTO,CivilStatusTO,StateTO} from '../../../models/employee.model';
import { result, data} from '../../../models/sico.model';
import {UserTO} from '../../../models/user.model';
import { DialogNewItemComponent } from '../../../components/dialog-new-item/dialog-new-item.component';
import { MatDialogConfig, MatRadioChange, MatDialog } from '@angular/material';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { DialogConfirmComponent } from '../../../components/dialog-confirm/dialog-confirm.component';
import { Router } from '@angular/router';

const dialogConfig = new MatDialogConfig();
@Component({
  selector: 'app-user-form-pi',
  templateUrl: './user-form-personal-info.component.html',
  styleUrls: ['./user-form-personal-info.component.css','../../../../assets/custom.css']
})

export class UserFormPersonaleInfoComponent implements OnInit {
  @ViewChild('inputFile_1') inputFile_1: ElementRef<any>;
  employeeData:EmployeeComplementaryTO;
  user:UserTO;
  result= new result;
  formInfoData:FormGroup;
  genderOption=[];
  statusOption=[];
  workPermitConfirmOption=[];
  civilStatusOption : CivilStatusTO[];
  dataSico:string;
  idUser:any;
  buttonSave:boolean=false;
  showSaveUpdate: boolean = true;


  maxDate = new Date();
  dateNac:Date;

  showAuthor:boolean = false;

  isDisable:boolean = false;
  isReading:boolean=true;

  constructor( 
    private fb: FormBuilder,
    private _dataService: DataService,
    private dialog:MatDialog,
    private _userService: UserService,
    private _localStorage:LocalStorageService,
    private _router:Router
    ) { 
   
      
this.dataSico="EX";
this._userService.setTabLabel('datos-personales');

this._userService.setActivePhotography(true);
    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._userService
    .getPermission()    
    .subscribe( resp => {
        this.workPermitConfirmOption = resp;

    });

    
    this.result= new result;
    this.employeeData = new EmployeeComplementaryTO(); 
    this.employeeData.employee = new EmployeeTO();
    this.employeeData.employee.user = new EmployeeUserTO();
    this.employeeData.employee.user.userType='A';
    this.employeeData.employee.name="";
    this.employeeData.employee.lastName="";
    this.employeeData.employee.id=0;
    this.employeeData.photography="";
    this.employeeData.workPermitConfirm="";
    this.formInfoData = this.fb.group({      
      curp:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.CURP), Validators.maxLength(18),Validators.minLength(18)]],
      name:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      lastName:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      lastMName:[ environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      gender:[DECLARATION.EMPTY_INPUT],
      birthDate:[DECLARATION.EMPTY_INPUT],
      cvilStatus:[DECLARATION.EMPTY_INPUT],
      rfc:[DECLARATION.EMPTY_INPUT,[Validators.pattern(EXPRESSION.RFC)]],
      nss:[DECLARATION.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER),Validators.minLength(11)]],
      email:[DECLARATION.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.EMAIL)]],
      state:[DECLARATION.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      phone:[DECLARATION.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER),Validators.minLength(7)]],
      birthCountry:[DECLARATION.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      workPermit:[DECLARATION.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      photography:[DECLARATION.EMPTY_INPUT],
      workPermitConfirm:[''],
      passportNum:[DECLARATION.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      userStatus:[DECLARATION.EMPTY_INPUT],
      lastUserModifier:['']     

    });

    this._userService.getFoto().subscribe(photo=> 
      this.employeeData.photography=photo)
    {

    }

    this.formInfoData.get('lastUserModifier').disable();
    this.selectChange('No');
    
this._localStorage
.getItem("answerLogin","user")
.subscribe(user => {
  this.user=user;
});

    this._userService
    .getGenero()    
    .subscribe( gender => {
        this.genderOption = gender;

    });



    this._userService
    .getStatus()    
    .subscribe( status => {
        this.statusOption = status;
    });

    this._userService
    .getCivilStatus()
    .subscribe((cvilStatus:CivilStatusTO[]) => {
      this.civilStatusOption =cvilStatus;
    });
 
    this.idUser = _userService.getIdUserCurrently();
    if(this.idUser>0)
    { 
      this._userService.getTabs(this.idUser)
      .subscribe(resp => {  
       this._userService.setConvertRespToTabIcon(resp);

      });

      this._dataService.setIsLoadingEvent(true);      

    this._userService.getEmployeeComplementaryByIdUser(this.idUser)
    .subscribe((resp:EmployeeComplementaryTO)=> {   
                this.employeeData=resp;
                console.log('DATOS USUARIO SELECT ', this.employeeData);
                                             
                this.showAuthor = true;
                this.formInfoData.get('email').disable();
                
                if(resp.employee.user.userStatus=='I')
                {
                  this._userService.setIsDisable(true);
                  this.formInfoData.disable(); 
                  this._userService.setFormEnable(false);
                }
                else
                {
                  this._userService.setFormEnable(true);
                  this._userService.setIsDisable(false);
                }

                if(resp.workPermit!=null && resp.workPermit.length>0)
                {
                  this.employeeData.workPermitConfirm="Si";
                  
                  resp.employee.user.userStatus == 'I' ? this.formInfoData.disable() : this.selectChange('Si');
                }

                this._dataService.setIsLoadingEvent(false);
                if(this.employeeData.employee.user.userType==='IN')
                {
                  _userService.setDatosSico(true);
                  this.fieldsReadonly();
                }   
                if(this.employeeData==null ||this.employeeData.photography|| this.employeeData.photography=="")
                  {
                    this._userService.setFoto(this.employeeData.photography);
                  }
                if(resp.employee.user.userStatus=='I')
                {
                  this.formInfoData.disable(); 
                  this._userService.setFormEnable(false);
                }
                else
                {
                  this._userService.setFormEnable(true);
                }

    }, err => {
      this._dataService.setIsLoadingEvent(false);
      this._dataService.setGeneralNotificationMessage( err.error.message );
      
    } );
    
  }

  }

  ngOnInit() {

    
  }

  disableButton()
  {
    if(this.employeeData.employee.id!=null && this.employeeData.employee.id>0)
    {
      return;
    }
    this.buttonSave=true;
  }

  findEmployee(){

    if(this.employeeData.employee.id!=null && this.employeeData.employee.id>0)
    {
      return;
    }

      if(this.employeeData.employee.name.length>1 && this.employeeData.employee.lastName.length >1 && this.employeeData.birthDate!=null )
      {
        this._dataService.setIsLoadingEvent(true);
        var fecha:any;
        fecha=this.employeeData.birthDate;
        this._userService.getInternalEmployee(this.employeeData.employee.name,this.employeeData.employee.lastName,fecha)
        .subscribe((resp:EmployeeComplementaryTO)=>{
          this.buttonSave=false;
          this._userService.convertEmail(resp.email).subscribe(resp=>{
            this.employeeData.employee.name="";
            this._dataService.setGeneralNotificationMessage(`Los datos personales que intentas ingresar pertenecen a la cuenta ${resp} `);
          });
      
          this._dataService.setIsLoadingEvent(false);

          if(resp.employee.user.userType==='IN')
          {
            this.fieldsReadonly();
          }
                },error=>{ 
                  this.buttonSave=true;     
                  this._dataService.setIsLoadingEvent(false);
                  this._dataService.setIsLoadingEvent(true);  
                  if(error.error.message=="No value present")
                  {
                    this._userService.getEmployeeSico(this.employeeData.employee.name,this.employeeData.employee.lastName,this.employeeData.birthDate)
                    .subscribe((response:result) => {
                      this.buttonSave=false;  
                      this._dataService.setIsLoadingEvent(false);
                      if(response.total>0)
                      {             
                        dialogConfig.data = { title:`Hola ${response.data[0].personal.nombres} ${response.data[0].personal.apellidoPaterno} ${response.data[0].personal.apellidoMaterno} \n ¿Perteneces a nuestro equipo de trabajo? `,success:'UPDATE' };
                      let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
                      dialogRef.afterClosed().subscribe( (resp:string) => {
                        if( resp == 'UPDATE' ){
                          this.setFormGrup(response);
                          this.fieldsReadonly();
                        }
                      });
                        
                      }
                
                    }, err => {
                      this.buttonSave=false;  
                        this._dataService.setIsLoadingEvent(false);
                        dialogConfig.data = { title:`Ocurrió un error al consultar la información en SICO \n ¿Deseas registrar al empleado como externo? `,success:'UPDATE' };
                        let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
                        dialogRef.afterClosed().subscribe( (resp:string) => {
                          if( resp == 'UPDATE' ){
                            
                          }
                          else
                          {
                            this._dataService.setGeneralNotificationMessage("La comunicación con SICO no fue posible, intenta más tarde");
                            this._router.navigate([`home/usuarios`]);
                          }

                        });
                        console.log(err)
                    });
                  }

                  
                });
        
      }
  }

  save() {

if(this.employeeData.employee.user.userStatus=='I')
{
  dialogConfig.data = { title:`¿Está seguro de inactivar el usuario? \n Al inactivar no podrá volver a Activarlo, la cuenta de correo electrónico no podrá ser usada nuevamente. `,success:'UPDATE' };
  let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
  dialogRef.afterClosed().subscribe( (resp:string) => {
    if( resp != 'UPDATE' ){
      this.employeeData.employee.user.userStatus='A';
      this.isDisable = false;
      return;
    }   
    else{
      this.isDisable = true;
      this.saveOrUpdate()

    }
  });
}
else{
  this.saveOrUpdate()
}
  
  }


  saveOrUpdate()
  {
   
    this._dataService.setIsLoadingEvent(true);
     setTimeout(()=>{
 
      },3000);

  console.log('foto',this.employeeData.photography);
    if(this.employeeData.employee.id<1)
    {
      this.employeeData.employee.creationUser=this.user.email;
      this.employeeData.creationUser=this.user.email;
    
      var user = new EmployeeUserTO();
      user.id=0;
      user.userType=this.dataSico
      user.email=this.employeeData.email
      user.creationUser=this.user.email;
      user.lastUserModifier=this.user.email;
      this.employeeData.employee.user=user;
    }
    this.employeeData.employee.lastUserModifier=this.user.email;
    this.employeeData.employee.lastModification= new Date;
    this.employeeData.employee.active=true;
    this.employeeData.lastUserModifier=this.user.email;

    this._userService.saveOrUpdateEmployeeComplementary(this.employeeData)
    .subscribe( (response:EmployeeTO) => {
      this._dataService.setIsLoadingEvent(false);
      this._userService.setIdEmployee(response.id);
      this._userService.setDatosSico(response.user.userType==='IN'?true:false);
      this._userService.setIdUserEmployee(response.user.id);
      this._userService.setParamSico(this.employeeData.employee.name,this.employeeData.employee.lastName,this.employeeData.birthDate,this.employeeData.curp)
      this._userService.setBaseStatus(false);
        if(response){
                      this.isDisable ? this.formInfoData.disable() : this.formInfoData.enable();
                      this._userService.setIsDisable(this.isDisable);
                     if(this.employeeData.employee.user.userStatus=='I')
                     {
                      this._dataService.setGeneralNotificationMessage('Se ha modificado el estatus del usuario');
                     }
                     else
                     {
                      this._dataService.setGeneralNotificationMessage(MSG.OK);
                     }

                      this._router.navigate([`home/admin-usuario/domicilio`])
                      
        }else {
          this._dataService.setGeneralNotificationMessage("No se realizo ningun registro");
        }
    }, err => {     
      this._dataService.setIsLoadingEvent(false);

      if(err.error.message.indexOf('email')!== -1)
      {
        this._dataService.setGeneralNotificationMessage('El correo electrónico que desea registrar ya existe, favor de validar');
      }
      else
      {
        this._dataService.setGeneralNotificationMessage( err.error.message );
      }
        
        console.log(err);
    });

  }

  setFormGrup(response)
  {
    this.dataSico="IN"
    this._userService.setDatosSico(true);
    this.fieldsReadonly()
    this.employeeData.employee.gender = response.data[0].personal.genero;
    this.employeeData.birthState=response.data[0].personal.estadoNacimiento;
    this.employeeData.birthCountry=response.data[0].personal.paisNacimiento;
    this.employeeData.employee.lastMName=response.data[0].personal.apellidoMaterno;
    this.employeeData.employee.lastName=response.data[0].personal.apellidoPaterno;
    this.employeeData.employee.name=response.data[0].personal.nombres;
    this.employeeData.employee.cvilStatus=response.data[0].personal.estadoCivil;
    this.employeeData.curp=response.data[0].personal.curp;
    this.employeeData.email=response.data[0].personal.emailPersonal;
    this.employeeData.nss=response.data[0].personal.nss;
    this.employeeData.rfc=response.data[0].personal.rfc;
    this.employeeData.phone=response.data[0].personal.numCelular.replace(" ","");
  }

  fieldsReadonly()
  {
    this.formInfoData.get('curp').disable();
    this.formInfoData.get('name').disable();
    this.formInfoData.get('lastName').disable();
    this.formInfoData.get('lastMName').disable();
    this.formInfoData.get('rfc').disable();
    this.formInfoData.get('gender').disable();
    this.formInfoData.get('birthDate').disable();
   // this.formInfoData.get('email').disable();
    this.formInfoData.get('cvilStatus').disable();
    //this.formInfoData.get('nss').disable();
    //this.formInfoData.get('birthCountry').disable();
    //this.formInfoData.get('state').disable(); 
}

selectChange(value:any)
{

  if (value=="Si") { 
    this.formInfoData.controls["workPermit"].setValidators([Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]);
    this.formInfoData.get('workPermit').enable();
  } 
  else {
    this.formInfoData.controls["workPermit"].setValidators(Validators.maxLength(200));
    this.formInfoData.get('workPermit').disable(); 
    this.employeeData.workPermit="";
  }
  this.formInfoData.controls["workPermit"].updateValueAndValidity();
}

  validInputs() {
    let isValidInputs = true;

    isValidInputs = isValidInputs && this.validDateInput();
   
    return isValidInputs;
  }

  validDateInput(){
    let isValidDate = true;
    let fecha = new Date();

    if (!(this.dateNac < fecha)){
      isValidDate=false;
      this._dataService.setGeneralNotificationMessage("");
    }
     

    return isValidDate;
  }

}

