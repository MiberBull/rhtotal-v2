import { Component, OnInit } from '@angular/core';
import { EmployeeCompensationTO,ControlCompensationTO} from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { MSG, environment,EXPRESSION } from '../../../../environments/environment';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { UserTO } from '../../../models/user.model';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Location } from '@angular/common';
@Component({
  selector: 'app-user-form-compen-package',
  templateUrl: './user-form-compen-package.component.html',
  styleUrls: ['./user-form-compen-package.component.css','../../../../assets/custom.css']
})
export class UserFormCompenPackageComponent implements OnInit {
  
 compensations= new  Array<EmployeeCompensationTO>();
 formInfoData: FormGroup;
 controls:ControlCompensationTO;
 idUserEmployee:number=0;
 showSaveUpdate: boolean = true;
 userAdmin:UserTO;
 listControl:any;

 isDisableForm:boolean=false;

  constructor(
    private _data: DataService,
    private location: Location,
    private _userService: UserService,
    private _localStorage: LocalStorageService,
    private router:Router,
    private fb: FormBuilder,
  ) {

    this._userService.setTabLabel('paquete-compensacion');
    
    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;

    this.controls= new ControlCompensationTO();
    this.controls.Sueldo_bruto_mensual;
    this.controls.Automovil=null;
    this.controls.Gastos_Automovil=null;
    this.controls.Opcion_Compra=null;
    this.controls.Bono_Mensual=null;
    this.controls.Cantidad_Bono_Mensual;
    this.controls.Bono_Bimestral=null;
    this.controls.Cantidad_Bono_Bimestral;
    this.controls.Bono_Trimestral=null;
    this.controls.Cantidad_Bono_Trimestral;
    this.controls.Bono_Anual=null;
    this.controls.Cantidad_Bono_Anual;
    this.controls.Metricas_Otorgamiento_Bono;
    this.controls.Fondo_de_Ahorro=null;
    this.controls.Cantidad_Fondo_de_Ahorro;
    this.controls.Vales_de_Despensa=null;
    this.controls.Cantidad_Vales_de_Despensa;
    this.controls.Vales_Restaurante=null;
    this.controls.Cantidad_Vales_Restaurante;
    this.controls.Vales_Gasolina=null;
    this.controls.Cantidad_Vales_Gasolina;
    this.controls.Aguinaldo=null;
    this.controls.Dias_Aguinaldo;
    this.controls.Cuantos_dias_de_vacaciones;
    this.controls.Porcentaje_prima_vacacional;
    this.controls.Seguro_GM_Mayores=null;
    this.controls.Seguro_GM_Menores=null;
    this.controls.Seguro_de_vida=null;
    this.controls.Meses_de_Cobertura_por_Muerte;
    this.controls.Reparto_de_utilidades=null;
    this.controls.Ultimo_monto_recibido;
    this.controls.Plan_de_pensiones=null;
    this.controls.Otra_prestacion="";
    this.controls.Ingreso_mensual_bruto_integrado;
    this.controls.Ingreso_anual_bruto_estimado;

    this.formInfoData = this.fb.group({      
    Sueldo_bruto_mensual:[ environment.EMPTY_INPUT,[Validators.maxLength(12),Validators.minLength(1)]],
    Automovil:[''],
    Gastos_Automovil:[''],
    Opcion_Compra:[''],
    Bono_Mensual:[''],
    Cantidad_Bono_Mensual:['',[]],
    Bono_Bimestral:[''],
    Cantidad_Bono_Bimestral:['',[]],
    Bono_Trimestral:[''],
    Cantidad_Bono_Trimestral:['',[]],
    Bono_Anual:[''],
    Cantidad_Bono_Anual:['',[]],
    Metricas_Otorgamiento_Bono:['',[Validators.pattern(EXPRESSION.DECIMAL)]],
    Fondo_de_Ahorro:[''],
    Cantidad_Fondo_de_Ahorro:['',[]],
    Vales_de_Despensa:[''],
    Cantidad_Vales_de_Despensa:['',[]],
    Vales_Restaurante:[''],
    Cantidad_Vales_Restaurante:['',[]],
    Vales_Gasolina:[''],
    Cantidad_Vales_Gasolina:['',[]],
    Aguinaldo:[''],
    Dias_Aguinaldo:['',[Validators.pattern(EXPRESSION.NUMBER)]],
    Cuantos_dias_de_vacaciones:['',[Validators.pattern(EXPRESSION.NUMBER)]],
    Porcentaje_prima_vacacional:['',[Validators.pattern(EXPRESSION.NUMBER)]],
    Seguro_GM_Mayores:[''],
    Seguro_GM_Menores:[''],
    Seguro_de_vida:[''],
    Meses_de_Cobertura_por_Muerte:['',[Validators.pattern(EXPRESSION.NUMBER)]],
    Reparto_de_utilidades:[''],
    Ultimo_monto_recibido:['',[]],
    Plan_de_pensiones:[''],
    Otra_prestacion:[''],
    Ingreso_mensual_bruto_integrado:['',[]],
    Ingreso_anual_bruto_estimado:['',[]],

    });

    //Desabilitacion de Formulario (Usuario Inactivo)
    this.isDisableForm = this._userService.getIsDisable();
    this.isDisableForm ? this.formInfoData.disable() : this.formInfoData.enable();


    if(!_userService.getFormEnable())
    {
      this.formInfoData.disable();
    }

    this._userService.setTabLabel('paquete-compensacion');
    this._userService.setActivePhotography(false);

    

    this._localStorage
    .getItem("answerLogin","user")
    .subscribe(user => {
      this.userAdmin=user;
    });
    this._data.setIsLoadingEvent(true);

    this.idUserEmployee = _userService.getIdUserCurrently();
    
    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);

    });

    this.listControl= ['Automovil','Gastos_Automovil','Opcion_Compra','Bono_Mensual','Bono_Bimestral','Bono_Trimestral','Bono_Anual','Fondo_de_Ahorro','Vales_de_Despensa','Vales_Restaurante','Vales_Gasolina','Aguinaldo','Seguro_GM_Mayores','Seguro_GM_Menores','Seguro_de_vida','Reparto_de_utilidades','Plan_de_pensiones'];

    if(this.idUserEmployee >0)
    {
    this._userService.getCompensation(this.idUserEmployee )
    .subscribe((resp:EmployeeCompensationTO[])=> { 
      this.compensations=resp;  
      resp.forEach(prop => {
        this._data.setIsLoadingEvent(false);
       if(this.listControl.includes(prop.dsName)) 
       {
        this.controls[prop.dsName] = prop.valor ==='0'?false : prop.valor ==='2'?true:null; 
       }
       else
       {
        this.controls[prop.dsName] = prop.valor;
       }

             });
    }, err => {
      this._data.setIsLoadingEvent(false);
      if(err.error.message !='No value present')
       {
        this._data.setGeneralNotificationMessage( err.error.message );
       }
          
      },()=>this._data.setIsLoadingEvent(false) );



  }
  else
  {
    this._data.setIsLoadingEvent(false);
  }
    
  }

  ngOnInit() {
  }
  save(){

    this._data.setIsLoadingEvent(true);
    var oCompensations = Array<EmployeeCompensationTO>();

      let i=0; 
      for (const prop in this.controls) {
        let compensation=new EmployeeCompensationTO;
        compensation.dsName=prop;
        if(this.listControl.includes(prop)) 
        {
          compensation.valor=this.controls[prop]==true?'2':this.controls[prop]==false?'0':'1';
        }
        else
        {
          compensation.valor=this.controls[prop];
        }           
        compensation.creationUser=this.userAdmin.email;
        compensation.lastUserModifier=this.userAdmin.email;
        compensation.idUser=this.idUserEmployee;


        if (this.compensations.length>1)
        {
          for(var j=0;j<this.compensations.length;j++){

                 if( prop==this.compensations[j].dsName)
                    {
                       compensation.idCompetation=this.compensations[j].idCompetation;
                       compensation.idUser=this.compensations[j].idUser;
                       compensation.creationDate=this.compensations[j].creationDate;
                       compensation.creationUser=this.compensations[j].creationUser;
                       compensation.dsName=this.compensations[j].dsName;
                     }
              }
         }


    oCompensations[i]= compensation;

  i+=1;
  
  
    }
  
this._userService.saveOrUpdateEmployeeCompensation(oCompensations)
    .subscribe( response => {
      this._data.setIsLoadingEvent(false);
        if(response){
           this._data.setGeneralNotificationMessage(MSG.OK);
           this.router.navigate([`home/admin-usuario/datos-asignacion`]);
        }else {
          this._data.setGeneralNotificationMessage("No se realizo ningún registro");
          
        }
       
    }, err => {
        this._data.setIsLoadingEvent(false);
        this._data.setGeneralNotificationMessage( err.error.message );
        console.log(err);
    });


  }

  onBrokenChange(n: string) {
    var num = n.replace(/[$,]/g, "");
    return Number(num);  
  }

}
