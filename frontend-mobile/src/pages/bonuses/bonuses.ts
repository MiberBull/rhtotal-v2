import { Component } from "@angular/core";
import { NavController, NavParams } from "ionic-angular";
import { FormGroup, FormBuilder, Validators } from "../../../node_modules/@angular/forms";
import { CompensationPackageTO } from "../../models/employee.compl";
import { UserTO } from "../../models/user.model";
import { MessageGeneral } from "../../iterface/create-account.interface";
import { UsersProvider } from "../../providers/users/users";
import { StorageProvider } from "../../providers/storage/storage";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { MSG_DIALOG, KEYS_STORAGE, VALIDATORS } from "../../environments/environments";
import { BrMaskModel, BrMaskerIonic3 } from "brmasker-ionic-3";

/**
 * Generated class for the BonusesPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: "page-bonuses",
  templateUrl: "bonuses.html",
  providers: [BrMaskerIonic3]
})
export class BonusesPage {
  private todo: FormGroup;
  public updatepackage :Array<CompensationPackageTO> = null;
  userObejct: UserTO;
  msg = new MessageGeneral();
  bonoMensualEnable:boolean = true;
  bonoBimestralEnable:boolean = true;
  bonoTrimestralEnable:boolean = true;
  bonoAnualEnable:boolean = true;

  labelCantidadBono:string ='Cantidad bono mensual';
  labelBonoBimestral:string = 'Cantidad bono bimestral';
  labelBonoTrimestral :string= 'Cantidad bono trimestral';
  labelBonoAnual ='Cantidad bono anual';
  
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private formBuilder: FormBuilder,
    public users: UsersProvider,
    public storage: StorageProvider,
    public loading:EventsManagerProvider,
    private brMaskerIonic3: BrMaskerIonic3
  ) {
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      Bono_Mensual: [null],
      Cantidad_Bono_Mensual: ["",[Validators.pattern(VALIDATORS.DECIMAL),Validators.maxLength(12),Validators.min(1)]],
      Bono_Bimestral: [null],
      Cantidad_Bono_Bimestral: ["",[Validators.pattern(VALIDATORS.DECIMAL),Validators.maxLength(12),Validators.min(1)]],
      Bono_Trimestral: [null],
      Cantidad_Bono_Trimestral: ["",[Validators.pattern(VALIDATORS.DECIMAL),Validators.maxLength(12),Validators.min(1)]],
      Bono_Anual: [null],
      Cantidad_Bono_Anual: ["",[Validators.pattern(VALIDATORS.DECIMAL),Validators.maxLength(12),Validators.min(1)]],
      Metricas_Otorgamiento_Bono: ["",[Validators.pattern(VALIDATORS.DECIMAL),Validators.maxLength(12),Validators.min(1)]]
    });
  }
  ionViewWillEnter(){
  
    this.getpackage();
  }
  saveBonues() {
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
    console.log(savePackages);
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
          this.navCtrl.parent.select(3);
        },2100);
      }else{
        this.loading.setIsLoadingEvent(false);
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      }
    },error=>{
      console.log(error);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
      this.loading.setIsLoadingEvent(false);
    });
    return;
  }
  this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.DATA_ERROR);
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
        this.verifyInfoEnable();
        this.loading.setIsLoadingEvent(false);
        this.updatepackage = this.updatepackage.length > 0 ?this.updatepackage : null;
      },error=>{
        console.log(error);
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



  bonoMensual(event:any){
     if(event){
      this.bonoMensualEnable = false;
      this.labelCantidadBono ='Cantidad bono mensual*';
      this.todo.controls["Cantidad_Bono_Mensual"].setValidators(Validators.required);
     this.todo.controls["Cantidad_Bono_Mensual"].updateValueAndValidity();
     
     console.log("Funcion createFormattMoney()" + this.createFormattMoney()); // Revisando contenido temporal
     
      return;
      
     }
     this.todo.controls["Cantidad_Bono_Mensual"].setValidators([]);
    this.todo.controls["Cantidad_Bono_Mensual"].updateValueAndValidity();
     this.labelCantidadBono ='Cantidad bono mensual';
      this.bonoMensualEnable = true;
      this.todo.controls['Cantidad_Bono_Mensual'].setValue('');
  }
      
  bonoBimestral(event:any){
    if(event){
      this.bonoBimestralEnable= false;
      this.labelBonoBimestral = 'Cantidad bono bimestral*';
      this.todo.controls["Cantidad_Bono_Bimestral"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Bono_Bimestral"].updateValueAndValidity();
      return;
    }
    this.labelBonoBimestral = 'Cantidad bono bimestral';
      this.bonoBimestralEnable = true;
      this.todo.controls["Cantidad_Bono_Bimestral"].setValidators([]);
      this.todo.controls["Cantidad_Bono_Bimestral"].updateValueAndValidity();
      this.todo.controls['Cantidad_Bono_Bimestral'].setValue('');
     
  }

  bonoTrimestral(event:any){
    if(event){
      this.bonoTrimestralEnable = false;
      this.labelBonoTrimestral = 'Cantidad bono trimestral*';
      this.todo.controls["Cantidad_Bono_Trimestral"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Bono_Trimestral"].updateValueAndValidity();
      return;
    }
    this.labelBonoTrimestral = 'Cantidad bono trimestral';
    this.bonoTrimestralEnable = true;
    this.todo.controls["Cantidad_Bono_Trimestral"].setValidators([]);
    this.todo.controls["Cantidad_Bono_Trimestral"].updateValueAndValidity();
    this.todo.controls['Cantidad_Bono_Trimestral'].setValue('');
  }

  BonoAnual(event:any){
    if(event){
      this.labelBonoAnual ='Cantidad bono anual*';
      this.bonoAnualEnable = false;
      this.todo.controls["Cantidad_Bono_Anual"].setValidators(Validators.required);
      this.todo.controls["Cantidad_Bono_Anual"].updateValueAndValidity();
      return;
    }
    this.labelBonoAnual ='Cantidad bono anual';
    this.bonoAnualEnable = true;
    this.todo.controls["Cantidad_Bono_Anual"].setValidators([]);
    this.todo.controls["Cantidad_Bono_Anual"].updateValueAndValidity();
    this.todo.controls['Cantidad_Bono_Anual'].setValue('');
  }



  updateEvents(name,event:any) {

    if(name === 'Bono_Mensual') {
     this.bonoMensual(event);
     return;
    }

    if(name === 'Bono_Bimestral') {
     this.bonoBimestral(event);
     return;
    }

    if(name === 'Bono_Trimestral') {
      this.bonoTrimestral(event);
      return;
     }

     if(name === 'Bono_Anual') {
      this.BonoAnual(event);
      return;
     }

  }

  verifyInfoEnable(){
    if(this.todo.get('Bono_Mensual').value ){
      this.bonoMensualEnable = false;
      this.labelCantidadBono ='Cantidad bono mensual*';
    }else{
      this.labelCantidadBono ='Cantidad bono mensual';
      this.bonoMensualEnable = true;
    }


    if(this.todo.get('Bono_Bimestral').value ){
      this.bonoBimestralEnable = false;
      this.labelBonoBimestral = 'Cantidad bono bimestral*';
    }else{
      this.labelBonoBimestral = 'Cantidad bono bimestral';
      this.bonoBimestralEnable = true;
    }

    if(this.todo.get('Bono_Trimestral').value ){
      this.bonoTrimestralEnable = false;
      this.labelBonoTrimestral = 'Cantidad bono trimestral*';
    }else{
      this.labelBonoTrimestral = 'Cantidad bono trimestral';
      this.bonoTrimestralEnable = true;
    }

    if(this.todo.get('Bono_Anual').value ){
      this.bonoAnualEnable = false;
      this.labelBonoAnual = 'Cantidad bono anual*';
    }else{
      this.labelBonoAnual = 'Cantidad bono anual';
      this.bonoAnualEnable = true;
    }
    
    // this.bonoMensualEnable = this.todo.get('Bono_Mensual').value ? false : true;
   //  this.bonoBimestralEnable = this.todo.get('Bono_Bimestral').value ? false : true;
   // this.bonoTrimestralEnable = this.todo.get('Bono_Trimestral').value ? false : true;
  //  this.bonoAnualEnable = this.todo.get('Bono_Anual').value ? false : true;
    }

    onBrokenChange(n: string) {
      var num = n.replace(/[$,]/g, "");
      var val =Number(num);
      this.todo.controls["Cantidad_Bono_Mensual"].setValue = val.toString;
      
    }

    private createFormattMoney(): string {
      const config: BrMaskModel = new BrMaskModel();
      config.money = true;
      config.decimal = 2;
      return this.brMaskerIonic3.writeCreateValue('', config);
    }

}
