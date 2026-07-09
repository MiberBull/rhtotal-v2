import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { FormBuilder, FormGroup, Validators } from '../../../node_modules/@angular/forms';
import { EXPRESSION, KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { UsersProvider } from '../../providers/users/users';
import { AsignationDataTO, EmployeeComplementaryTO } from '../../models/employee.compl';
import { StorageProvider } from '../../providers/storage/storage';
import { UserTO } from '../../models/user.model';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MyAccountPage } from '../my-account/my-account';

/**
 * Generated class for the AsignationDataPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-asignation-data',
  templateUrl: 'asignation-data.html',
})
export class AsignationDataPage {
  todo: FormGroup;
  employeePositionEnable: boolean = false;
  //private companyEnable:boolean = false;
  //private projectEnable:boolean = false;
  enableAsignation: boolean = true;
  minDate: any;
  msg = new MessageGeneral();
  asignacion: AsignationDataTO = null;
  userObejct: UserTO;
  client: string;
  project: string;
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public loading: EventsManagerProvider,
    public storage: StorageProvider,
    public users: UsersProvider,
    private formBuilder: FormBuilder
  ) {
    this.loading.setIsLoadingEvent(true);
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.todo = this.formBuilder.group({
      employeePosition: ['', Validators.maxLength(150)],
      manager: ['', Validators.maxLength(50)],
      state: ['', Validators.maxLength(50)],
      city: ['', Validators.maxLength(50)],
      emailDirectBoss: ['', [Validators.maxLength(50), Validators.pattern(EXPRESSION.EMAIL)]],
      telephoneDirectBoss: ['', Validators.maxLength(10)],
      startAssigment: [''],
      endAllocation: [''],
      allocationEmail: ['', [Validators.maxLength(50), Validators.pattern(EXPRESSION.EMAIL)]],
      allocationSalary: ['', [Validators.minLength(1)]],
      evaluation: ['', [Validators.maxLength(3), Validators.min(1)]],
    });
    this.validateTypeUSers();
    this.getAsignationDataRHTotal();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AsignationDataPage');
  }

  getAsignationDataRHTotal() {
    this.users.getAsignacionDataByIdUser(this.userObejct.id).subscribe(
      (data: AsignationDataTO) => {
        this.asignacion = new AsignationDataTO();
        this.loadFormInput(data);
        this.asignacion = data;
      },
      (error) => {
        this.getEployeName();
        this.loading.setIsLoadingEvent(false);
        console.log(error);
      }
    );
  }

  getEployeName() {
    this.users.getEmployeeById(this.userObejct.id).subscribe(
      (data: EmployeeComplementaryTO) => {
        this.getSicoAsignacionData(data.curp, data.employee.name);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getSicoAsignacionData(curp: string, name: string) {
    const nameCompouse = name.trim().replace(' ', '+');
    this.users.getWorkInfoSico(curp, nameCompouse).subscribe(
      (data) => {
        if (data && data.data && data.data.pago && data.data.pago.length > 0) {
          const asignacionData = new AsignationDataTO();
          this.client = data.data.pago[0].nombreCliente;
          this.project = data.data.pago[0].nombreProyecto;
          asignacionData.employeePosition = (data.data.personal as any).puesto;
          this.loadFormInput(asignacionData);
        } else {
          this.loading.setIsLoadingEvent(false);
        }
      },
      (error) => {
        console.log(error);
        this.loading.setIsLoadingEvent(false);
      }
    );
  }

  validateTypeUSers() {
    if (this.userObejct.userType === 'IN') {
      this.employeePositionEnable = true;
    }
  }

  getInfoInit() {
    if (this.asignacion != null) {
      this.asignacion.employeePosition = this.todo.get('employeePosition').value;
      this.asignacion.manager = this.todo.get('manager').value;
      this.asignacion.state = this.todo.get('state').value;
      this.asignacion.city = this.todo.get('city').value;
      this.asignacion.idClient = 0;
      this.asignacion.idProject = 0;
      this.asignacion.emailDirectBoss = this.todo.get('emailDirectBoss').value;
      this.asignacion.telephoneDirectBoss = this.todo.get('telephoneDirectBoss').value;
      this.asignacion.startAssigment = new Date(this.todo.get('startAssigment').value);
      this.asignacion.endAllocation = new Date(this.todo.get('endAllocation').value);
      this.asignacion.allocationEmail = this.todo.get('allocationEmail').value;
      this.asignacion.allocationSalary = this.todo.get('allocationSalary').value;
      this.asignacion.evaluation = this.todo.get('evaluation').value;
      this.saveSignationData(this.asignacion);
      return;
    }

    const contract = new AsignationDataTO();
    contract.employeePosition = this.todo.get('employeePosition').value;
    contract.manager = this.todo.get('manager').value;
    contract.state = this.todo.get('state').value;
    contract.city = this.todo.get('city').value;
    contract.emailDirectBoss = this.todo.get('emailDirectBoss').value;
    contract.telephoneDirectBoss = this.todo.get('telephoneDirectBoss').value;
    contract.startAssigment = new Date(this.todo.get('startAssigment').value);
    contract.endAllocation = new Date(this.todo.get('endAllocation').value);
    contract.idClient = 0;
    contract.idProject = 0;
    contract.project = this.project;
    contract.client = this.client;
    contract.allocationEmail = this.todo.get('allocationEmail').value;
    contract.allocationSalary = this.todo.get('allocationSalary').value;
    contract.evaluation = this.todo.get('evaluation').value;
    contract.idDataAssigment = 0;
    contract.idUser = this.userObejct.id;
    contract.lastUserModifier = this.userObejct.email;
    contract.lastModification = null;
    contract.creationUser = this.userObejct.email;
    contract.creationDate = null;
    contract.active = true;

    console.log(contract);
    this.saveSignationData(contract);
  }

  loadFormInput(contract: AsignationDataTO) {
    for (const key in this.todo.controls) {
      for (const prop in contract) {
        if (prop === 'allocationSalary' && key === 'allocationSalary') {
          const value: any = contract[prop];
          this.todo.controls[key].setValue(value === 0 ? '' : contract[prop]);
        } else {
          if (key === prop) {
            this.todo.controls[key].setValue(contract[prop]);
          }
        }
      }
    }
    this.disableInitDate(this.todo.get('startAssigment').value);
    this.loading.setIsLoadingEvent(false);
  }

  saveSignationData(asignation: AsignationDataTO) {
    this.loading.setIsLoadingEvent(true);

    this.users.saveOrUpdateAsignationData(asignation).subscribe(
      (data) => {
        if (data) {
          this.msg.title = MSG_DIALOG.OK;
          this.msg.msg = MSG_DIALOG.MSG_SAVE;
          this.loading.setGeneralNotificationMessage(this.msg);

          this.loading.setIsLoadingEvent(false);

          setTimeout(() => {
            this.navCtrl.push(MyAccountPage);
          }, 2100);
        } else {
          this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
          this.loading.setIsLoadingEvent(false);
        }
      },
      (error) => {
        console.log(error);
        this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
        this.loading.setIsLoadingEvent(false);
      }
    );
  }

  back() {
    this.navCtrl.pop();
  }

  onChange() {
    this.minDate = new Date(this.todo.get('startAssigment').value).toISOString();
    this.enableAsignation = false;
    console.log(this.minDate); // Revisión de valor
  }

  disableInitDate(dateInit: string) {
    if (dateInit != '' && dateInit != null) {
      this.minDate = new Date(dateInit).toISOString();
      this.enableAsignation = false;
      console.log(this.minDate); // Revisión de valor
    }
  }
}
