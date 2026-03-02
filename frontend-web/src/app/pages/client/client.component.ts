
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators, FormArray, FormGroup } from "@angular/forms";
import { BreadcrumbService } from "../../services/breadcrumbs/breadcrumbs.service";
import { environment, EXPRESSION } from "../../../environments/environment";
import { ToolbarFabService } from "../../services/toolbar-fab/toolbar-fab.service";
import { Location } from '@angular/common';

import { DataService } from "../../services/data.service";
import { ClientService } from "../../services/client/client.service";
import { Router } from '@angular/router';
import { MatDialogConfig, MatRadioChange, MatDialog } from '@angular/material';
import { CompoundCustomerTO, ProjectTO } from '../../models/clientmodel';
import { CustomerTO } from "../../models/clientmodel";
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { routesWeb } from '../../../environments/environment.prod';
import { DialogConfirmComponent } from '../../components/dialog-confirm/dialog-confirm.component';
const dialogConfig = new MatDialogConfig();

@Component({
  selector: "app-client",
  templateUrl: "./client.component.html",
  styleUrls: ["./client.component.css","../../../assets/custom.css"]
})
export class ClientComponent implements OnInit, OnDestroy {

  compoundCustomerTO:CompoundCustomerTO;
  projectAuthor:boolean=true;
  buttonState:boolean = false;

  enableButtonSave:boolean=false;
  enableButtonAdd:boolean=false;
  visibleButtonSave:boolean=false;
  classButton:string = 'controls-wrapper';

  status:any[] = [];
  newItem:boolean = false;
  idCustomer:number;
  formInfo: FormArray;
  CountStatus:number=0;
  profileForm = this._fb.group({
    formInfoGroups: this._fb.array([])
  });  

  formCustomer: FormGroup;

  titleProyect = 'Proyecto detalle';

  initialForm = {
    idProject:[],
    name: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    rfc: [environment.EMPTY_INPUT, [Validators.required,Validators.pattern(EXPRESSION.RFC), Validators.maxLength(13),Validators.minLength(10)]],
    businessName:[environment.EMPTY_INPUT,[Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    email: [environment.EMPTY_INPUT, [Validators.pattern(EXPRESSION.EMAIL)]],
    address: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    contact: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    phone: [environment.EMPTY_INPUT, [Validators.required,Validators.pattern(EXPRESSION.NUMBER), Validators.maxLength(10),Validators.minLength(1)]],
    extension: [environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.NUMBER), Validators.maxLength(5),Validators.minLength(1)]],
    status: [""],
    additionalInformation: [environment.EMPTY_INPUT,[ Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
    creationUser: []
  };

  showSaveUpdate: boolean = true;

  constructor(
    private location: Location,
    private _toolbarFab: ToolbarFabService,
    private _clientService: ClientService,
    private _router:Router,
    private _fb: FormBuilder,
    private _localService: LocalStorageService,
    private _dataService: DataService,
    private dialog:MatDialog,
    private _breadcrumb: BreadcrumbService) {
      this.visibleButtonSave=true;
    this.showSaveUpdate = this._localService.getRolUserRead() == environment.ROL_USER_READ ? false : true;


    this._dataService.setIsLoadingEvent(true);

      this.formCustomer = _fb.group({
        name: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
        email: [environment.EMPTY_INPUT, [ Validators.pattern(EXPRESSION.EMAIL)]],
        address: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
        contact: [environment.EMPTY_INPUT, [Validators.required, Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
        phone: [environment.EMPTY_INPUT, [Validators.required,Validators.pattern(EXPRESSION.NUMBER), Validators.maxLength(10),Validators.minLength(1)]],
        extension: [environment.EMPTY_INPUT, [Validators.pattern(EXPRESSION.NUMBER), Validators.maxLength(5),Validators.minLength(1)]],
        status: [environment.EMPTY_INPUT],
        additionalInformation: [environment.EMPTY_INPUT, [ Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
        creationUser:[]
      });

    this.CountStatus=0;
    this._toolbarFab.setVisible( this._router.url.toString() );
    this.idCustomer = this._clientService.getIdClient();
    if ( this.idCustomer > 0 ) {
      this.visibleButtonSave = false;
      this.enableButtonSave=false;
      this.newItem = true;
      this._breadcrumb.setRouteText({title:'Cliente detalle',arrow:true});
      this.getClientAndProjects( this._clientService.getIdClient() )
    }
    else {
      this._breadcrumb.setRouteText({title:'Cliente nuevo',arrow:true});
      this.visibleButtonSave = true;
      this.status.push({value:false})
      this.agregar();
    }
        
  } 

  ngOnInit() {
    window.addEventListener( 'scroll', (event) => {
      this.scroll(event)
    } );
    this._dataService.setIsLoadingEvent(false);
  }

  scroll( event ) {
    if(  event.currentTarget.scrollY > 300 ) {
        this.buttonState = true;
    }else{
      this.buttonState = false;
    }
  }


  getClientAndProjects( idClient ){
    this._dataService.setIsLoadingEvent(false);
    this._clientService
        .getClientById( idClient )    
        .subscribe( (resp:any) => {
          this.setValuesForm( resp );
        }, err => {
          this._dataService.setIsLoadingEvent(false);
        },()=>this._dataService.setIsLoadingEvent(false));
  }

  setValuesForm(values){
    this.compoundCustomerTO = values;
    this.formCustomer.patchValue( values.customer );
    this.formCustomer.disable();
    values.projectTOList.forEach( (item,index) => {
      this.status.push({value:true})
      this.formInfoGroups.push(this._fb.group(this.initialForm));
      this.formInfoGroups.controls[index].patchValue( item );
      this.formInfoGroups.controls[index].disable();
    });
  }



  get formInfoGroups() {
    return this.profileForm.get("formInfoGroups") as FormArray;
  }

  onSubmit() {
    if(this.CountStatus>0 )
    {
         dialogConfig.disableClose = true;
        let Singular =this.CountStatus==1?' al elemento':'a los elementos' ;
        dialogConfig.data = { title:`¿Está seguro de cambiar el estatus ${Singular}?`,success:'UPDATE' };
        let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
        dialogRef.afterClosed().subscribe( (resp:string) => {
          if( resp == 'UPDATE' ){
            let customer = this.formCustomer.getRawValue();
            let projects = this.profileForm.getRawValue();
        
            if( this.idCustomer > 0 ) {
              this.updateParameters( customer,projects.formInfoGroups );
            }else{
              this.saveParameters( customer,projects );
            }

          }
       
      });
    }
    else
    {
            let customer = this.formCustomer.getRawValue();
            let projects = this.profileForm.getRawValue();
        
            if( this.idCustomer > 0 ) {
              this.updateParameters( customer,projects.formInfoGroups );
            }else{
              this.saveParameters( customer,projects );
            }
    }
  
  }

  

  agregar() {
    this.visibleButtonSave=true;
    this.enableButtonSave=true;
    if( this.profileForm.valid ) {
      this.status.push({value:false});
      if( this.idCustomer > 0 ){
        let project = new ProjectTO(this._localService);
        this.compoundCustomerTO.projectTOList.push( project );
      }
      this.formInfoGroups.push(this._fb.group(this.initialForm));
    }

  }

  enabledForms() {
    let status = this.formCustomer.get('status').value;
    this.enableButtonAdd = status=='A';
    this.enableButtonSave = true;
    this.visibleButtonSave=true;
    this.formCustomer.enable();
    this.formCustomer.get('creationUser').disable();

    this.formInfoGroups.controls.forEach( (item:any,index) => {
         this.formInfoGroups.controls[index].enable();
         item.controls['creationUser'].disable();
              });    
  
  }

  saveParameters( customer,projects ) {

    let customerTO = this.setCustomer( customer );    
    let project = projects.formInfoGroups;
    let projectsTO: ProjectTO[] = this.setProjects( project,customerTO );
    
    let finalobjet = new CompoundCustomerTO();
    finalobjet.customer = customerTO;
    finalobjet.projectTOList = projectsTO;
    this._clientService.saveOrUpdateClient(finalobjet)
                       .subscribe( () => {
                         this._dataService.setGeneralNotificationMessage(environment.DATOS_CORRECTOS);

                         setTimeout(() => {
                            this._router.navigate([`${routesWeb.HOME}/${routesWeb.CUSTOMER}`])
                         }, 3900);
                        }, error => {
                          this._dataService.setGeneralNotificationMessage(error.error.message);
                        } );
  }

  updateParameters( customer,projects ) {
    this.compoundCustomerTO.customer.name = customer.name;
    this.compoundCustomerTO.customer.address = customer.address;
    this.compoundCustomerTO.customer.contact = customer.contact;
    this.compoundCustomerTO.customer.phone = customer.phone;
    this.compoundCustomerTO.customer.extension = customer.extension;
    this.compoundCustomerTO.customer.email = customer.email;
    this.compoundCustomerTO.customer.additionalInformation = customer.additionalInformation;
    this.compoundCustomerTO.customer.status=customer.status;

    this.compoundCustomerTO.projectTOList.forEach( (project,index) => {
      project.idClient = this.compoundCustomerTO.customer;
      project.name = projects[index].name;
      project.rfc = projects[index].rfc.toUpperCase();
      project.businessName = projects[index].businessName;
      project.address = projects[index].address;
      project.contact = projects[index].contact;
      project.phone = projects[index].phone;
      project.extension = projects[index].extension;
      project.email = projects[index].email;
      project.additionalInformation = projects[index].additionalInformation;
      project.status = projects[index].status;
      this.compoundCustomerTO.projectTOList[index].status = this.compoundCustomerTO.projectTOList[index].idProject<1?"A":projects[index].status;
    });
    this.saveOrUpdateService( this.compoundCustomerTO );
    this.CountStatus=0;
  }

  validateEmail(email: string) {
    let emailRegex = environment.EMAIL_REGEX;
    return emailRegex.test(email);
  }

  saveOrUpdateService( compoundCustomer: CompoundCustomerTO ) {
    this._dataService.setIsLoadingEvent(true);
    this._clientService.saveOrUpdateClient(compoundCustomer)
                       .subscribe( () => {
                          this._dataService.setIsLoadingEvent(false);

                         this._dataService.setGeneralNotificationMessage(environment.DATOS_CORRECTOS);

                         setTimeout(() => {
                           this._router.navigate([`${routesWeb.HOME}/${routesWeb.CUSTOMER}`]);
                         }, 3900); 

                        }, error => {
                          this._dataService.setIsLoadingEvent(false);
                          this._dataService.setGeneralNotificationMessage(error.error.message);
                        },()=>this._dataService.setIsLoadingEvent(false));
  }

  setCustomer( customerForm ) {
    let customer:CustomerTO = new CustomerTO(this._localService);
    customer.name = customerForm.name;
    customer.address = customerForm.address;
    customer.contact = customerForm.contact;
    customer.phone = customerForm.phone;
    customer.extension = customerForm.extension;
    customer.email = customerForm.email;
    customer.additionalInformation = customerForm.additionalInformation;
    customer.status = 'A';
    return customer;
  }

  setProjects( projects,customer ) {
    let projectsTO:ProjectTO[] = [];
    projects.forEach( item => {
      let project = new ProjectTO( this._localService );
      project.idClient = customer;
      project.name = item.name;
      project.rfc = item.rfc.toUpperCase();
      project.businessName = item.businessName;
      project.address = item.address;
      project.contact = item.contact;
      project.phone = item.phone;
      project.extension = item.extension;
      project.email = item.email;
      project.additionalInformation = item.additionalInformation;
      project.status = 'A';
      projectsTO.push( project );
    });
    return projectsTO;
  }

  checkCustomer() {
    let status = this.formCustomer.get('status').value;
    if( status == 'I' ){
      this.enableButtonAdd = false;
      let activeFormsProject = this.formInfoGroups.controls.filter( (item:any) => item.controls.status.value === 'A' );
      if( activeFormsProject.length > 0 ) {
        this._dataService.setGeneralNotificationMessage('No se puede inactivar el cliente, aún cuenta con proyectos activos');
        this.formCustomer.controls.status.setValue('A');
        this.enableButtonAdd = true;
      }
      else
      {
        this.CountStatus+=1;
      }
    }
    else
    {
      this.enableButtonAdd  = true;
    }
  }
      
  changeStatusForProject( formInfo:any ) {
    let status = this.formCustomer.get('status').value;
    if(status=='I')
    {
      this._dataService.setGeneralNotificationMessage("No es posible activar el proyecto, debido a que el cliente está inactivo");
      formInfo.controls.status.setValue('I');   
      return;
    }

    this._clientService
        .queryProjectForInvalid( formInfo.controls.idProject.value )
        .subscribe( (resp) => {
          if( resp ) {
            
            this._dataService.setGeneralNotificationMessage('No se puede inactivar el proyecto, aún cuenta con empleados activos');
            formInfo.controls.status.setValue('A');   
          }
          else
          {
            this.CountStatus +=1;
          }
          
        }, error => console.log( error ));    
  }

  cancel() {
    this.location.back();
  }

  enabledButtonSave() {    

    return (this.formCustomer.valid && this.profileForm.valid && this.enableButtonSave)
  }

  ngOnDestroy(){
    this._clientService.setIdClient(null);
  }

}
