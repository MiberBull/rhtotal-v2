import { Component } from "@angular/core";
import { NavController, NavParams, AlertController } from "ionic-angular";
import {
  FormGroup,
  FormBuilder,
  Validators
} from "../../../node_modules/@angular/forms";
import {
  VALIDATORS,
  PRMISION_WORK,
  GENERO,
  KEYS_STORAGE,
  MSG_DIALOG
} from "../../environments/environments";
import { UsersProvider } from "../../providers/users/users";
import { DialogsProvider } from "../../providers/dialogs/dialogs";
import { UserTO } from "../../models/user.model";
import { StorageProvider } from "../../providers/storage/storage";
import { EmployeeComplementaryTO,EmployeeTO, EmployeeDomicileTO, EmployeePersonalTO } from "../../models/employee.compl";
import { EventsManagerProvider } from "../../providers/events-manager/events-manager";
import { MessageGeneral } from "../../iterface/create-account.interface";


@Component({
  selector: "page-personal-information",
  templateUrl: "personal-information.html"
})
export class PersonalInformationPage {
  isUpdate: boolean = false;
  rht: EmployeeComplementaryTO;
  private todo: FormGroup;
  alertRH: any;
  permisions: any[] = PRMISION_WORK;
  generos: any[] = GENERO;
  estadoCivil: any[] = [];
  enables: any[] = [];
  isenabled: boolean = false;
  userObejct: UserTO;
  nacionality: string;
  base64: string;
  checkMexico: boolean = false;
  checkExtranjero: boolean = false;
  updateEmpComplementary: EmployeeComplementaryTO = null;
  existRhTotalParam: boolean = false;
  domicile: EmployeeDomicileTO;
  permisoMexicoEnable:boolean = false;
  msg = new MessageGeneral();
  labelPasaporte:string;
  existeSico:boolean = true;
  labelPermisoMexico:string;
  permisotrabajo:string = 'Permiso de trabajo';
  curpLabel:string;
  tipeNationalityRadio:boolean;
  constructor(
    public loading: EventsManagerProvider,
    public navCtrl: NavController,
    public storage: StorageProvider,
    public alertCtrl: AlertController,
    public dialogs: DialogsProvider,
    public navParams: NavParams,
    private formBuilder: FormBuilder,
    private users: UsersProvider
    ) {

    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
  }

  ionViewWillEnter(){
    this.checkMexico = true;
    this.nacionality = "Mexicano";
    this.blockSocoInput( false, false,false,false,false,false, false,false,false,false,true,false,false);
    this.todo = this.formBuilder.group({
      curp: ["",[Validators.pattern(VALIDATORS.CURP),Validators.minLength(0), Validators.maxLength(18)]],
      numeroPasaporte: ["", Validators.maxLength(20)],
      nombres: ["",[Validators.required,Validators.pattern(VALIDATORS.OWN_NAME), Validators.maxLength(50)]],
      apellidoPaterno: ["",[Validators.required,Validators.pattern(VALIDATORS.OWN_NAME), Validators.maxLength(50)]],
      apellidoMaterno: ["",[Validators.pattern(VALIDATORS.OWN_NAME), Validators.maxLength(50)]],
      fechaNacimiento: ["", Validators.required],

      paisNacimiento: ["", [Validators.required,Validators.maxLength(50)]],
      estadoNacimiento: ["", [Validators.required,,Validators.maxLength(50)]],
      permisoTrabajo: ["", Validators.maxLength(50)],
      rfc: ["",[Validators.pattern(VALIDATORS.RFCHOMO),Validators.maxLength(13),Validators.minLength(10)]],
      nss: ["", [Validators.maxLength(11),Validators.minLength(11)]],
      correoElectronico: [this.userObejct.email, Validators.required],
      telefonoCelular: ["", [Validators.required, Validators.maxLength(10),Validators.minLength(10)]],
      civilStatus: [""],
      valueGender: [""],
      permisWorckMexico: [""]
    });    
    this.users.getCivilStatus().subscribe(data => {
      this.estadoCivil = [];
      data.forEach(element => {
        this.estadoCivil.push({
          id: element.civilCode,
          name: element.statusCivil
        });
      });
    });

    this.validaUSerByID();
  }


  saveImage() {
    this.isUpdate = true; // Previene Cambio de pantalla para llenar domicilio
    this.users.getImage().then(data => {
      this.base64 = `data:image/jpeg;base64,${data}`;      
      this.saveInfoRHTotal();// Actualizar los datos
    });
  }

  validaSico() {

    let name:string = this.todo.get("nombres").value;
    let fecha = new Date(this.todo.get("fechaNacimiento").value);
    let aPaterno:string = this.todo.get("apellidoPaterno").value;
    let fechaConpuesta: string = this.getDateString(fecha);
    if (name != null && name != "" && fecha != null && aPaterno != null && aPaterno != "") {
      this.existRHTotal(name, aPaterno, fecha).subscribe(data => {
        console.log(JSON.stringify(data));
        if (!data) {
          this.alertRH = this.alertExistRhTotal(this.rht.email);
          this.alertRH.present();
          this.alertRH.onDidDismiss((data: boolean) => {
            if (data) {
              this.todo.controls["fechaNacimiento"].setValue("");
            }
          });
        }
      },
      error => {
        let nameCompouse = name.trim().split(" ").join("+");
        let aPaternoCompouse = aPaterno.trim().split(" ").join('+');

        this.users.getExistUserSico(nameCompouse, aPaternoCompouse, fechaConpuesta).subscribe(data => {

          if(data.total > 0 ){
            let alertSico = this.alertExistSico(name);
            alertSico.present();
            alertSico.onDidDismiss((data: boolean) => {
              if (data) {
                console.log('este es un user interno');
                this.existSico(nameCompouse, aPaternoCompouse, fechaConpuesta);
                let user = this.userObejct;
                user.userType = "IN";
                this.storage.saveItem(KEYS_STORAGE.USER, user);
                this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
                this.saveInfoRHTotal();
              }
            });
          }
        },
        () => {
          console.log("no existe en sico ni en rht total usuario externo");
        }
        );
        console.log(error.message, "no existe en rhtotal");
      }
      );
      this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    }
  }

  getDateString(date: Date): string {
    var day =
    date.getDate() < 10 ? "0" + date.getDate() + 1 : date.getDate() + 1;
    var month =
    date.getMonth() < 9 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    return `${day}/${month}/${date.getFullYear()}`;
  }

  blockSocoInput(
    curp: boolean,
    nombres: boolean,
    gener: boolean,
    civilStatus: boolean,
    apellidoPaterno: boolean,
    apellidoMaterno: boolean,
    fechaNacimiento: boolean,
    estadoNacimiento: boolean,
    rfc: boolean,
    nss: boolean,
    correoElectronico: boolean,
    telefonoCelular: boolean,
    paisNacimiento: boolean
    ) {
    this.enables = [];
    this.enables.push({
      curp: curp,
      nombres: nombres,
      apellidoPaterno: apellidoPaterno,
      apellidoMaterno: apellidoMaterno,
      fechaNacimiento: fechaNacimiento,
      estadoNacimiento: estadoNacimiento,
      gener: gener,
      civilStatus: civilStatus,
      rfc: rfc,
      nss: nss,
      paisNacimiento: paisNacimiento,
      correoElectronico: correoElectronico,
      telefonoCelular: telefonoCelular
    });
  }

  existRHTotal(name: string, aPaterno: string, fecha: Date) {
    return this.users.getExistUserRhTotal(name, aPaterno, fecha).map(
      (data: EmployeeComplementaryTO) => {
        this.rht = data;
        if (this.rht.employee.user.id === this.userObejct.id) {
          this.updateEmpComplementary = this.rht;
          this.loadInfoForm(this.rht);
          this.blockSocoInput(
            true, // curp
            true, // nombre
            true, // gener
            false, // civil
            true, // apellido p
            true, // apellido m
            true, // fecha nacimiento
            true, // estado nacimiento
            true, //rfc
            true, // nss
            true, // correo e
            true, // celular
            true); // pais nacimiento
          return true;
        }
        return false;
      },
      error => {
        console.error(error.message);
      }
      );
  }

  existSico(name: string, aPaterno: string, fechaConpuesta: string) {
    this.users.getExistUserSico(name, aPaterno, fechaConpuesta).subscribe(sico => {

      if(sico.total > 0 ){
        this.todo.controls["curp"].setValue(sico.data[0].personal.curp);
        this.todo.controls["nombres"].setValue(sico.data[0].personal.nombres);
        this.todo.controls["apellidoPaterno"].setValue(sico.data[0].personal.apellidoPaterno);
        this.todo.controls["apellidoMaterno"].setValue(sico.data[0].personal.apellidoMaterno);
        this.todo.controls["paisNacimiento"].setValue(sico.data[0].personal.paisNacimiento);
        this.todo.controls["estadoNacimiento"].setValue(sico.data[0].personal.estadoNacimiento);
        this.todo.controls["rfc"].setValue(sico.data[0].personal.rfc);
        this.todo.controls["nss"].setValue(sico.data[0].personal.nss);
        this.todo.controls["correoElectronico"].setValue(this.userObejct.email);
        this.todo.controls["telefonoCelular"].setValue(sico.data[0].personal.numCelular);
        this.todo.controls["civilStatus"].setValue(sico.data[0].personal.estadoCivil);
        this.todo.controls["valueGender"].setValue(sico.data[0].personal.genero);

        this.domicile = new EmployeeDomicileTO();
        this.domicile.id = null;
        this.domicile.interiorNumber = sico.data[0].direccion.numeroInterior;
        this.domicile.outDoorNumber = sico.data[0].direccion.numeroExterior;
        this.domicile.state = sico.data[0].direccion.estado;
        this.domicile.city = sico.data[0].direccion.delegacionMunicipio;
        this.domicile.postalCode = sico.data[0].direccion.cp;
        this.domicile.colony = sico.data[0].direccion.colonia;
        this.domicile.street = sico.data[0].direccion.calle;
        this.domicile.lastUserModifier = this.userObejct.email;
        this.domicile.lastModification = null;
        this.domicile.creationUser = this.userObejct.email;
        this.domicile.creationDate = null;
        this.domicile.active = true;
        this.blockSocoInput(
          true, // curp
          true, // nombre
          true, // gener
          false, // civil
          true, // apellido p
          true, // apellido m
          true, // fecha nacimiento
          false, // estado nacimiento
          true, //rfc
          true, // nss
          true, // correo e
          true, // celular
          true); // pais nacimiento
        //this.blockSocoInput(true,true,true,true,true,true,true,false,true,true,true,true,false);
        this.existeSico =  false;
      }
      
    },
    error => {
      console.log(error.message, "error2");
    }
    );
  }

  saveInfoRHTotal() {
    this.loading.setIsLoadingEvent(true);
    let personalInformation = new EmployeePersonalTO();
    if (this.updateEmpComplementary != null) {
      this.updateEmpComplementary.birthCountry = this.todo.get("paisNacimiento").value;
      this.updateEmpComplementary.birthDate = new Date(this.todo.get("fechaNacimiento").value);
      this.updateEmpComplementary.birthState = this.todo.get("estadoNacimiento" ).value;
      this.updateEmpComplementary.creationUser = this.userObejct.email;
      this.updateEmpComplementary.curp = this.todo.get("curp").value;
      this.updateEmpComplementary.email = this.todo.get( "correoElectronico").value;
      this.updateEmpComplementary.lastUserModifier = this.userObejct.email;
      this.updateEmpComplementary.nss = this.todo.get("nss").value;
      this.updateEmpComplementary.passportNumber = this.todo.get("numeroPasaporte").value;
      this.updateEmpComplementary.phone = this.todo.get("telefonoCelular").value;
      this.updateEmpComplementary.photography = this.base64;
      this.updateEmpComplementary.rfc = this.todo.get("rfc").value;
      this.updateEmpComplementary.workPermit = this.todo.get("permisoTrabajo").value;
      this.updateEmpComplementary.nationality = this.nacionality;
      this.updateEmpComplementary.workPermitConfirm = this.todo.get("permisWorckMexico").value;

      this.updateEmpComplementary.employee.creationUser = this.userObejct.email;
      this.updateEmpComplementary.employee.cvilStatus = this.todo.get("civilStatus").value;
      this.updateEmpComplementary.employee.gender = this.todo.get("valueGender").value;
      this.updateEmpComplementary.employee.lastMName = this.todo.get("apellidoMaterno").value;
      this.updateEmpComplementary.employee.lastName = this.todo.get("apellidoPaterno").value;
      this.updateEmpComplementary.employee.lastUserModifier = this.userObejct.email;
      this.updateEmpComplementary.employee.name = this.todo.get("nombres").value;
      personalInformation.complementary = this.updateEmpComplementary;
      personalInformation.addressTO = null;
      this.saveEmployeePerson(personalInformation);
      return;
    }

    let employeeComplementary = new EmployeeComplementaryTO();
    let employee = new EmployeeTO();
    employee.active = true;
    employee.creationDate = null;
    employee.creationUser = this.userObejct.email;
    employee.cvilStatus = this.todo.get("civilStatus").value;
    employee.gender = this.todo.get("valueGender").value;
    employee.id = null;
    employee.lastMName = this.todo.get("apellidoMaterno").value;
    employee.lastModification = null;
    employee.lastName = this.todo.get("apellidoPaterno").value;
    employee.lastUserModifier = this.userObejct.email;
    employee.name = this.todo.get("nombres").value;
    employee.user = this.userObejct;

    employeeComplementary.active = true;
    employeeComplementary.birthCountry = this.todo.get("paisNacimiento").value;
    employeeComplementary.birthDate = new Date(this.todo.get("fechaNacimiento").value);
    employeeComplementary.birthState = this.todo.get("estadoNacimiento").value;
    employeeComplementary.creationDate = null;
    employeeComplementary.creationUser = this.userObejct.email;
    employeeComplementary.curp = this.todo.get("curp").value;
    employeeComplementary.email = this.todo.get("correoElectronico").value;
    employeeComplementary.emailClient = null;
    employeeComplementary.employee = employee;
    employeeComplementary.id = null;
    employeeComplementary.idSwap = null;
    employeeComplementary.lastModification = null;
    employeeComplementary.lastUserModifier = this.userObejct.email;
    employeeComplementary.nss = this.todo.get("nss").value;
    employeeComplementary.passportNumber = this.todo.get("numeroPasaporte").value;
    employeeComplementary.workPermitConfirm = this.todo.get("permisWorckMexico").value;
    employeeComplementary.phone = this.todo.get("telefonoCelular").value;
    employeeComplementary.photography = this.base64;
    employeeComplementary.rfc = this.todo.get("rfc").value;
    employeeComplementary.workPermit = this.todo.get("permisoTrabajo").value;
    employeeComplementary.nationality = this.nacionality;
    personalInformation.complementary = employeeComplementary;

    if (this.domicile != null) {
      personalInformation.addressTO = this.domicile;
    }else{
      personalInformation.addressTO = null;
    }
    this.saveEmployeePerson(personalInformation);
  }

  alertExistRhTotal(email: string) {
    let array = email.split("@");
    let str = array[0].substr(-3);
    let content = `Los datos personales que intentas ingresar pertenecen a la cuenta ******${str}@${
      array[1]
    } Verifica la información que ingresaste`;
    let alert = this.alertCtrl.create({
      title: `<label></label>`,
      subTitle: `<h6>${content}</h6>`,
      enableBackdropDismiss: false,
      buttons: [
      {
        text: "Aceptar",
        handler: () => {
          alert.dismiss(true);
          return false;
        }
      }
      ],
      cssClass: "alertCustomCss"
    });

    return alert;
  }

  alertExistSico(user: string) {
    let content = `Hola ${user}
    ¿Perteneces a nuestro equipo de trabajo?`;

    let alert = this.alertCtrl.create({
      title: `<label></label>`,
      subTitle: `<h6>${content}</h6>`,
      enableBackdropDismiss: false,
      buttons: [
      {
        text: "Si",
        handler: () => {
          alert.dismiss(true);
          return false;
        }
      },
      {
        text: "No",
        handler: () => {
          alert.dismiss(false);
          return false;
        }
      }
      ],
      cssClass: "alertCustomCss"
    });

    return alert;
  }

  validaUSerByID() {

    this.users.getEmployeeById(this.userObejct.id).subscribe(
      (data: EmployeeComplementaryTO) => {
        this.loading.setIsLoadingEvent(true);
        if(data != null && data.employee != null && data.employee.user != null){
          this.updateEmpComplementary = data;
          
          if (data.employee.user.userType === "IN") {
            this.loadInfoForm(data);
            this.blockSocoInput(
              true, // curp
              true, // nombre
              true, // gener
              false, // civil
              true, // apellido p
              true, // apellido m
              true, // fecha nacimiento
              false, // estado nacimiento
              true, //rfc
              true, // nss
              true, // correo e
              true, // celular
              true); // pais nacimiento
            //this.blockSocoInput(true,true,true,true,true,true, true,false, true,true,true,true,false);
            this.loading.setIsLoadingEvent(false);
          } else {
            this.loadInfoForm(data);
            this.blockSocoInput(false, false,false,false,false, false, false,false, false,false,true,false, false);
            this.loading.setIsLoadingEvent(false);
          }
          this.users.setTabsEvent({tab1: true,tab2: true});
          this.loading.setIsLoadingEvent(false);
        }else{
          this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.BUSQUEDA);
          this.loading.setIsLoadingEvent(false);
        }

      },
      () => {
        this.mcqAnswer(true);
        this.users.setTabsEvent({tab1: false, tab2: false });
        this.loading.setIsLoadingEvent(false);
      }
      );
  }



  loadInfoForm(form: EmployeeComplementaryTO) {
    this.base64 = form.photography != null ? form.photography : "";
    this.todo.controls["curp"].setValue(form.curp);
    this.todo.controls["nombres"].setValue(form.employee.name);
    this.todo.controls["apellidoPaterno"].setValue(form.employee.lastName);
    this.todo.controls["apellidoMaterno"].setValue(form.employee.lastMName);
    this.todo.controls["paisNacimiento"].setValue(form.birthCountry);
    this.todo.controls["estadoNacimiento"].setValue(form.birthState);
    this.todo.controls["fechaNacimiento"].setValue(form.birthDate);
    this.todo.controls["rfc"].setValue(form.rfc);
    this.todo.controls["nss"].setValue(form.nss);
    this.todo.controls["correoElectronico"].setValue(this.userObejct.email);
    this.todo.controls["telefonoCelular"].setValue(form.phone);
    this.todo.controls["civilStatus"].setValue(form.employee.cvilStatus);
    this.todo.controls["valueGender"].setValue(form.employee.gender);
    this.todo.controls["numeroPasaporte"].setValue(form.passportNumber);
    this.todo.controls["permisoTrabajo"].setValue(form.workPermit);
    this.todo.controls["permisWorckMexico"].setValue(form.workPermitConfirm);
    this.typeNacionality(form.nationality);
    this.onChange(form.workPermitConfirm);

  }

  typeNacionality(nacionalidad: string) {
    if (nacionalidad === "Extranjero") {
      this.checkMexico = false;
      this.checkExtranjero = true;
      this.labelPasaporte = 'Número de pasaporte*';
      this.labelPermisoMexico = 'Permiso para trabajar en México*';
      this.mcqAnswer(false);
    } else {
      this.mcqAnswer(true);
      this.checkMexico = true;
      this.checkExtranjero = false;
      this.labelPermisoMexico = 'Permiso para trabajar en México';
      this.labelPasaporte = 'Número de pasaporte';
    }
  }

  
  mcqAnswer(nacionalidad: boolean) {
    if (!nacionalidad) {
      this.todo.controls["numeroPasaporte"].setValidators([Validators.required,Validators.maxLength(20)]);
      this.todo.controls["permisWorckMexico"].setValidators(Validators.required);
      this.todo.controls["permisWorckMexico"].updateValueAndValidity();
      this.todo.controls["numeroPasaporte"].updateValueAndValidity();
      this.nacionality = "Extranjero";
      this.labelPasaporte = 'Número de pasaporte*';
      this.labelPermisoMexico = 'Permiso para trabajar en México*';
      this.tipeNationalityRadio = false; 
      this.curpLabel = 'CURP';
      this.todo.controls["curp"].setValidators([Validators.pattern(VALIDATORS.CURP),Validators.minLength(18), Validators.maxLength(18)]);
      this.todo.controls["curp"].updateValueAndValidity();

    } else {
      this.todo.controls["numeroPasaporte"].setValidators(Validators.maxLength(20));
      this.todo.controls["permisWorckMexico"].setValidators([]);
      this.todo.controls["permisWorckMexico"].updateValueAndValidity();
      this.todo.controls["numeroPasaporte"].updateValueAndValidity();
      this.nacionality = "Mexicano";
      this.labelPermisoMexico = 'Permiso para trabajar en México';
      this.labelPasaporte = 'Número de pasaporte';
      this.tipeNationalityRadio = true; 
      this.curpLabel = 'CURP';
      this.todo.controls["curp"].setValidators([Validators.pattern(VALIDATORS.CURP),Validators.minLength(0), Validators.maxLength(18)]);
      this.todo.controls["curp"].updateValueAndValidity();
    }
  }

  onChange(event:string){
    if(event === 'Si' && this.typeNacionality){
      this.todo.controls["permisoTrabajo"].setValidators(Validators.required);
      this.todo.controls["permisoTrabajo"].updateValueAndValidity();
      this.permisotrabajo = 'Permiso de trabajo*';
      this.permisoMexicoEnable = false;
      return;
    }

    if(event === 'No'){
      this.permisotrabajo = 'Permiso de trabajo';
      this.todo.controls["permisoTrabajo"].setValue('');
      this.todo.controls["permisoTrabajo"].setValidators([]);
      this.todo.controls["permisoTrabajo"].updateValueAndValidity();
      this.permisoMexicoEnable = true;
      return;
    }
    this.permisotrabajo = 'Permiso de trabajo';
  }


  updatePerfil(perfilNew :EmployeeComplementaryTO){
    let perfil =  this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL);
    perfil.name  = perfilNew.employee.name;
    perfil.lastName = perfilNew.employee.lastName;
    perfil.mLastName = perfilNew.employee.lastMName;
    perfil.imageBase64 = perfilNew.photography;
    this.storage.saveItem(KEYS_STORAGE.INFO_CRENDENTIAL,perfil);
  }



  saveEmployeePerson(employeePersonal:EmployeePersonalTO){

    this.users.saveEmployeComplementary(employeePersonal).subscribe(()=>{
      this.updatePerfil(employeePersonal.complementary);
      this.loading.setIsLoadingEvent(false);

      // Cambia la pantalla normalmente si se actualiza todo
      if(!this.isUpdate){
        this.users.setTabsEvent({tab1: true,tab2: true});
        this.msg.title = MSG_DIALOG.OK;
        this.msg.msg = MSG_DIALOG.MSG_SAVE;
        this.loading.setGeneralNotificationMessage(this.msg);

        setTimeout(()=>{
          this.navCtrl.parent.select(1);
        },2100);
      }
      this.isUpdate = false; // Prepara para dar botton guardar y funcione normal

    },error =>{
      this.loading.setIsLoadingEvent(false);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.ERROR_SERVICE);
    });
  }

   /**
    * @function actualizarDatos Revisa si la imagen ha cambiado fuera de personal data para actualizarla
    **/
    actualizarDatos(){
      this.isUpdate = true; // Previene Cambio de pantalla para llenar domicilio
      let perfil =  this.storage.getItem(KEYS_STORAGE.INFO_CRENDENTIAL);

      if(perfil.imageBase64 === this.base64) {
        console.log("No hay cambio de imagen no se hace nada");
      } else {
        this.base64 = perfil.imageBase64; // Se toma la imagen que cambio en el store para actualizar
        this.saveInfoRHTotal();// Actualizar los datos
      }   
    }

  }