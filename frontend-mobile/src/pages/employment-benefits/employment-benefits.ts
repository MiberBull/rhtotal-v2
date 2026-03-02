import { Component } from "@angular/core";
import { NavController, NavParams } from "ionic-angular";
import {
  FormGroup,
  FormBuilder,
  Validators
} from "../../../node_modules/@angular/forms";
import { UsersProvider } from "../../providers/users/users";
import { CompensationPackageTO } from "../../models/employee.compl";
import { UserTO } from "../../models/user.model";
import { StorageProvider } from "../../providers/storage/storage";
import { KEYS_STORAGE, MSG_DIALOG, VALIDATORS } from "../../environments/environments";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { MessageGeneral } from "../../iterface/create-account.interface";
import { BrMaskerIonic3 } from "brmasker-ionic-3";

/**
 * Generated class for the EmploymentBenefitsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: "page-employment-benefits",
  templateUrl: "employment-benefits.html",
  providers: [BrMaskerIonic3]
})
export class EmploymentBenefitsPage {
  private todo: FormGroup;
  public updatepackage: Array<CompensationPackageTO> = null;
  userObejct: UserTO;
  msg = new MessageGeneral();
  fondoAhorroEnable: boolean = true;
  valeDespensaEnable: boolean = true;
  valeRestauranteEnable: boolean = true;
  valeGasolinaEnable: boolean = true;
  valeAginaldoEnable: boolean = true;

  labelDiasAginaldos:string = 'Días de aguinaldo';
  labelCantidadAhorro:string = 'Cantidad fondo de ahorro';
  labelValesDespensa:string = 'Cantidad vales de despensa';
  labelValesRestaurantes:string = 'Cantidad vales restaurante';
  labelValesGasolina:string = 'Cantidad vales gasolina';

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private formBuilder: FormBuilder,
    public users: UsersProvider,
    public storage: StorageProvider,
    public loading: EventsManagerProvider
  ) {
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      Automovil: [null],
      Opcion_Compra: [null],
      Gastos_Automovil: [null],
      Fondo_de_Ahorro: [null],
      Cantidad_Fondo_de_Ahorro: ["", [Validators.pattern(VALIDATORS.DECIMAL), Validators.maxLength(12), Validators.minLength(1)]],
      Vales_de_Despensa: [null],
      Cantidad_Vales_de_Despensa: ["", [Validators.pattern(VALIDATORS.DECIMAL), Validators.maxLength(12), Validators.minLength(1)]],
      Vales_Restaurante: [null],
      Cantidad_Vales_Restaurante: ["", [Validators.pattern(VALIDATORS.DECIMAL), Validators.maxLength(12), Validators.minLength(1)]],
      Vales_Gasolina: [null],
      Cantidad_Vales_Gasolina: ["", [Validators.pattern(VALIDATORS.DECIMAL), Validators.maxLength(12), Validators.minLength(1)]],
      Aguinaldo: [null],
      Dias_Aguinaldo: ["", [Validators.maxLength(3), Validators.min(1)]],
      Cuantos_dias_de_vacaciones: ["", [Validators.maxLength(3), Validators.min(1)]],
      Porcentaje_prima_vacacional: ["", [Validators.maxLength(3), Validators.min(1)]]
    });
  }

  ionViewWillEnter(){
    this.getpackage();  
  }


  saveEmployment() {

    if (this.updatepackage != null) {
      this.updatepackage.forEach(element => {
        for (const key in this.todo.controls) {
          if (element.dsName === key) {
            let value = this.todo.get(key).value;
            element.valor = this.getValueNumber(value);
            element.lastUserModifier = this.userObejct.email;
          }
        }
      });
      this.saveOrUpdate(this.updatepackage);
      return;
    }




    let savePackages = new Array<CompensationPackageTO>();
    for (const key in this.todo.controls) {
      let compensation = new CompensationPackageTO();
      compensation.idCompetation = null;
      compensation.idUser = this.userObejct.id;
      compensation.dsName = key;
      let value = this.todo.get(key).value;
      compensation.valor = this.getValueNumber(value);
      compensation.dsEmail = this.userObejct.email;
      compensation.lastUserModifier = this.userObejct.email;
      compensation.lastModification = null;
      compensation.creationUser = this.userObejct.email;
      compensation.creationDate = null;
      compensation.active = true;
      savePackages.push(compensation);

    }
    console.log(savePackages);
    this.saveOrUpdate(savePackages);
  }


  saveOrUpdate(update: Array<CompensationPackageTO>) {
    if (this.todo.valid) {
      this.loading.setIsLoadingEvent(true);
      this.users.saveConpesationPackage(update).subscribe(data => {
        if (data) {
          this.msg.title = MSG_DIALOG.OK;
          this.msg.msg = MSG_DIALOG.MSG_SAVE;
          this.loading.setGeneralNotificationMessage(this.msg);
          
          this.loading.setIsLoadingEvent(false);
          setTimeout(()=>{
            this.navCtrl.parent.select(1);
          },2100);
        } else {
          this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
          this.loading.setIsLoadingEvent(false);
        }
      }, error => {
        console.log(error);
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
        this.loading.setIsLoadingEvent(false);
      });
      return;
    }
    this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.DATA_ERROR);

  }


  getpackage() {
    this.loading.setIsLoadingEvent(true);
    this.users.getCompensationPackage(this.userObejct.id).subscribe((data: Array<CompensationPackageTO>) => {
      this.updatepackage = new Array<CompensationPackageTO>();
      data.forEach(element => {
        for (const key in this.todo.controls) {
          if (element.dsName === key) {
            this.updatepackage.push(element);
            this.todo.controls[key].setValue(this.setValueNumber(element.valor));
            this.updateEvents(key,this.setValueNumber(element.valor));
          }
        }
      });
      this.verifyInfoEnable();
      this.loading.setIsLoadingEvent(false);
    }, error => {
      console.error(error);
      this.loading.setIsLoadingEvent(false);
    });
  }

  getValueNumber(value): string {
    if (value === null) {
      return "1";
    }
    let element = value.toString();
    if (element === "false") {
      return "0";
    }

    if (element === "true") {
      return "2";
    }
    return element;
  }

  setValueNumber(value: any): any {
    if (value === '0') {
      return false;
    }
    if (value === '1') {
      return null;
    }
    if (value === '2') {
      return true;
    }
    return value;
  }


  fondoAhorro(event: any) {
    if (event) {
      this.fondoAhorroEnable = false;
      this.todo.controls["Cantidad_Fondo_de_Ahorro"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Fondo_de_Ahorro"].updateValueAndValidity();
      this.labelCantidadAhorro ='Cantidad fondo de ahorro*';
      return;
    }
    this.todo.controls["Cantidad_Fondo_de_Ahorro"].setValidators([]);
    this.todo.controls["Cantidad_Fondo_de_Ahorro"].updateValueAndValidity();
    this.fondoAhorroEnable = true;
    this.todo.controls['Cantidad_Fondo_de_Ahorro'].setValue('');
    this.labelCantidadAhorro ='Cantidad fondo de ahorro';
  }

  valeDespensa(event: any) {
    if (event) {
      this.valeDespensaEnable = false;
      this.labelValesDespensa ='Cantidad vales de despensa*';
      this.todo.controls["Cantidad_Vales_de_Despensa"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Vales_de_Despensa"].updateValueAndValidity();
      
      return;
    }
    this.valeDespensaEnable = true;
    this.labelValesDespensa ='Cantidad vales de despensa';
    this.todo.controls["Cantidad_Vales_de_Despensa"].setValidators([]);
    this.todo.controls["Cantidad_Vales_de_Despensa"].updateValueAndValidity();
    this.todo.controls['Cantidad_Vales_de_Despensa'].setValue('');
    
  }

  valeRestaurante(event: any) {
    if (event) {
      this.valeRestauranteEnable = false;
      this.labelValesRestaurantes ='Cantidad vales restaurante*';
      this.todo.controls["Cantidad_Vales_Restaurante"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Vales_Restaurante"].updateValueAndValidity();
      return;
    }
    this.valeRestauranteEnable = true;
    this.labelValesRestaurantes ='Cantidad vales restaurante';
    this.todo.controls["Cantidad_Vales_Restaurante"].setValidators([]);
    this.todo.controls["Cantidad_Vales_Restaurante"].updateValueAndValidity();
    this.todo.controls['Cantidad_Vales_Restaurante'].setValue('');
    
  }

  valesGasolina(event: any) {
    if (event) {
      this.labelValesGasolina = 'Cantidad vales gasolina*';
      this.todo.controls["Cantidad_Vales_Gasolina"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Vales_Gasolina"].updateValueAndValidity();
      this.valeGasolinaEnable = false;
      return;
    }
    this.valeGasolinaEnable = true;
    this.labelValesGasolina = 'Cantidad vales gasolina';
    this.todo.controls["Cantidad_Vales_Gasolina"].setValidators([]);
    this.todo.controls["Cantidad_Vales_Gasolina"].updateValueAndValidity();
    this.todo.controls['Cantidad_Vales_Gasolina'].setValue('');
    
  }

  valesAginaldo(event: any) {
    if (event) {
      this.labelDiasAginaldos = 'Días de aguinaldo*';
      this.todo.controls["Dias_Aguinaldo"].setValidators(Validators.required);
      this.todo.controls["Dias_Aguinaldo"].updateValueAndValidity();
      this.valeAginaldoEnable = false;
      return;
    }
    this.valeAginaldoEnable = true;
    this.labelDiasAginaldos = 'Días de aguinaldo';
    this.todo.controls["Dias_Aguinaldo"].setValidators([]);
    this.todo.controls["Dias_Aguinaldo"].updateValueAndValidity();
    this.todo.controls['Dias_Aguinaldo'].setValue('');
    
  }

updateEvents(name,event:any){

  if(name === 'Fondo_de_Ahorro'){
   this.fondoAhorro(event);
   return;
  }
  if(name === 'Vales_de_Despensa'){
   this.valeDespensa(event);
   return;
  }
  if(name === 'Vales_Restaurante'){
   this.valeRestaurante(event);
   return;
  }
  if(name === 'Vales_Gasolina'){
   this.valesGasolina(event);
   return;
  }
  if(name === 'Aguinaldo'){
   this.valesAginaldo(event);
   return;
  }
}



  verifyInfoEnable() {
    
    
    if(this.todo.get('Fondo_de_Ahorro').value ){
      this.fondoAhorroEnable = false;
      this.labelCantidadAhorro = 'Cantidad fondo de ahorro*';
    }else{
      this.labelCantidadAhorro = 'Cantidad fondo de ahorro';
      this.fondoAhorroEnable = true;
    }


    if(this.todo.get('Vales_de_Despensa').value ){
      this.valeDespensaEnable = false;
      this.labelValesDespensa = 'Cantidad vales de despensa*';

    }else{
      this.valeDespensaEnable = true;
      this.labelValesDespensa = 'Cantidad vales de despensa';

    }

    if(this.todo.get('Vales_Restaurante').value ){
      this.valeRestauranteEnable = false;
      this.labelValesRestaurantes = 'Cantidad vales restaurante*';

    }else{
      this.valeRestauranteEnable = true;
      this.labelValesRestaurantes = 'Cantidad vales restaurante';

    }

    if(this.todo.get('Vales_Gasolina').value ){
      this.valeGasolinaEnable = false;
      this.labelValesGasolina = 'Cantidad vales gasolina*';

    }else{
      this.valeGasolinaEnable = true;
      this.labelValesGasolina = 'Cantidad vales gasolina';

    }

    if(this.todo.get('Aguinaldo').value ){
      this.valeAginaldoEnable = false;
      this.labelDiasAginaldos = 'Días de aguinaldo*';
    }else{
      this.labelDiasAginaldos = 'Días de aguinaldo';
      this.valeAginaldoEnable = true;
    }

  //  this.fondoAhorroEnable = this.todo.get('Fondo_de_Ahorro').value ?  : true;
  //  this.valeDespensaEnable = this.todo.get('Vales_de_Despensa').value ? false : true;
  //  this.valeRestauranteEnable = this.todo.get('Vales_Restaurante').value ? false : true;
  //  this.valeGasolinaEnable = this.todo.get('Vales_Gasolina').value ? false : true;
 //   this.valeAginaldoEnable = this.todo.get('Aguinaldo').value ? false : true;
  }

}
