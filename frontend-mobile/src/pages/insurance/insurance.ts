import { Component } from "@angular/core";
import { NavController, NavParams } from "ionic-angular";
import { FormBuilder, FormGroup, Validators } from "../../../node_modules/@angular/forms";
import { CompensationPackageTO } from "../../models/employee.compl";
import { UsersProvider } from "../../providers/users/users";
import { StorageProvider } from "../../providers/storage/storage";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { UserTO } from "../../models/user.model";
import { KEYS_STORAGE, MSG_DIALOG, VALIDATORS } from "../../environments/environments";
import { MessageGeneral } from "../../iterface/create-account.interface";
import { BrMaskerIonic3 } from "brmasker-ionic-3";

/**
 * Generated class for the InsurancePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: "page-insurance",
  templateUrl: "insurance.html",
  providers: [BrMaskerIonic3]
})
export class InsurancePage {
  private todo: FormGroup;
  userObejct: UserTO;
  msg = new MessageGeneral();
  mesesCoberturaEnable:boolean =true;
  ultimomontoEnable:boolean = true;
  labelUltimomonto:string = '¿Último monto recibido?';
  mesesPorCovertura:string = 'Meses de cobertura por muerte';
  public updatepackage :Array<CompensationPackageTO> = null;
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private formBuilder: FormBuilder,
    public users: UsersProvider,
    public storage: StorageProvider,
    public loading:EventsManagerProvider
  ) {
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      Seguro_GM_Mayores: [null],
      Seguro_GM_Menores: [null],
      Seguro_de_vida: [null],
      Meses_de_Cobertura_por_Muerte: ['',[Validators.maxLength(3),Validators.minLength(1)]],
      Reparto_de_utilidades: [null],
      Ultimo_monto_recibido: ['',[Validators.pattern(VALIDATORS.DECIMAL), Validators.maxLength(12),Validators.minLength(1)]],
      Plan_de_pensiones: [null],
      Otra_prestacion: ['',[Validators.maxLength(50),Validators.minLength(1)]]
    });
  }

  ionViewWillEnter(){
    
    this.getpackage();
  }

  saveInsurance() {

    if(this.updatepackage != null){
      this.updatepackage.forEach(element => {
        for (const key in this.todo.controls) {
          if(element.dsName === key){
            let value = this.todo.get(key).value;
            element.valor  = this.getValueNumber(value);
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
    this.saveOrUpdate(savePackages);
    
  }

  saveOrUpdate(update :Array<CompensationPackageTO>){
    if(this.todo.valid){
      this.loading.setIsLoadingEvent(true);

      this.users.saveConpesationPackage(update).subscribe(data=>{
        if(data){
          this.msg.title = MSG_DIALOG.OK;
          this.msg.msg = MSG_DIALOG.MSG_SAVE;
          this.loading.setGeneralNotificationMessage(this.msg);
          this.loading.setIsLoadingEvent(false);
          setTimeout(()=>{
            this.navCtrl.parent.select(2);
          },2100);
        }else{
          this.loading.setIsLoadingEvent(false);
          this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
        }
      },error=>{
        console.log(error);
        this.loading.setIsLoadingEvent(false);
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      });
      return;
    }
    this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR);
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

  getpackage(){
    this.loading.setIsLoadingEvent(true);
    this.users.getCompensationPackage(this.userObejct.id).subscribe((data:Array<CompensationPackageTO>)=>{
      this.updatepackage = new Array<CompensationPackageTO>();
      data.forEach(element => {
          for (const key in this.todo.controls) {
            if(element.dsName === key){
            this.updatepackage.push(element);
            this.todo.controls[key].setValue(this.setValueNumber(element.valor));
            this.updateEvents(key,this.setValueNumber(element.valor));
            }
          }

        });
        this.loading.setIsLoadingEvent(false);
        this.verifyInfoEnable();  
        this.updatepackage = this.updatepackage.length > 0 ?this.updatepackage : null;

      },error=>{
        console.log(error);
        this.loading.setIsLoadingEvent(false);
      });
  }


  repartoUtilidades(event: any) {
    if (event) {
      this.ultimomontoEnable = false;
      this.labelUltimomonto = '¿Último monto recibido?*'
      this.todo.controls["Ultimo_monto_recibido"].setValidators(Validators.required);
      this.todo.controls["Ultimo_monto_recibido"].updateValueAndValidity();

      return;
    }
    this.todo.controls["Ultimo_monto_recibido"].setValidators([]);
    this.todo.controls["Ultimo_monto_recibido"].updateValueAndValidity();
    this.labelUltimomonto = '¿Último monto recibido?'
    this.ultimomontoEnable = true;
    this.todo.controls['Ultimo_monto_recibido'].setValue('');
  }


  seguroVida(event:any){
    if (event) {
      this.mesesCoberturaEnable = false;
      this.mesesPorCovertura = 'Meses de cobertura por muerte*';
      this.todo.controls["Meses_de_Cobertura_por_Muerte"].setValidators(Validators.required);
    this.todo.controls["Meses_de_Cobertura_por_Muerte"].updateValueAndValidity();
      return;
    }
    this.todo.controls["Meses_de_Cobertura_por_Muerte"].setValidators([]);
    this.todo.controls["Meses_de_Cobertura_por_Muerte"].updateValueAndValidity();
    this.mesesPorCovertura = 'Meses de cobertura por muerte';
    this.mesesCoberturaEnable = true;
    this.todo.controls['Meses_de_Cobertura_por_Muerte'].setValue('');
  }


  updateEvents(name,event:any){

    if(name === 'Reparto_de_utilidades'){
     this.repartoUtilidades(event);
     return;
    }
    if(name === 'Seguro_de_vida'){
     this.seguroVida(event);
     return;
    }
  }




  verifyInfoEnable(){

    if(this.todo.get('Seguro_de_vida').value ){
      this.ultimomontoEnable = false;
      this.labelUltimomonto = '¿Último monto recibido?*';
    }else{
      this.labelUltimomonto = '¿Último monto recibido?';
      this.ultimomontoEnable = true;
    }

    if(this.todo.get('Reparto_de_utilidades').value ){
      this.mesesCoberturaEnable = false;
      this.mesesPorCovertura = 'Meses de cobertura por muerte*';
    }else{
      this.mesesPorCovertura = 'Meses de cobertura por muerte';
      this.mesesCoberturaEnable = true;
    }
 //   this.ultimomontoEnable = this.todo.get('Seguro_de_vida').value ? false : true;
 //   this.mesesCoberturaEnable = this.todo.get('Reparto_de_utilidades').value ? false : true;
    }
}
