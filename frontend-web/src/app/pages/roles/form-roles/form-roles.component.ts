import { Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common'

import { ToolbarFabService } from './../../../services/toolbar-fab/toolbar-fab.service';
import { RolService } from '../../../services/roles/rol.service';
import { BreadcrumbService } from './../../../services/breadcrumbs/breadcrumbs.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { DataService } from '../../../services/data.service';

import { RolesUserTO, RolCompoundTO } from '../../../models/rol.model';
import { environment,BUTTON, BREADCRUMB, EXPRESSION, MSG, NUMBERS, EMAIL } from './../../../../environments/environment';
import { routesWeb } from '../../../../environments/environment.prod';
import { Information } from '../../../util/date';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DialogConfirmComponent } from '../../../components/dialog-confirm/dialog-confirm.component';
import { CypherService } from '../../../services/cypher/cypher.service';

const dialogConfig = new MatDialogConfig();

@Component({
    selector:'app-roles-form',
    templateUrl:'./form-roles.component.html',
    styleUrls:['./form-roles.component.css','../../../../assets/custom.css']
})

export class FormRolesComponent implements OnInit,OnDestroy{

    userRolAssig:RolCompoundTO<RolesUserTO> = new RolCompoundTO();

    editButton:string = BUTTON.EDIT;
    saveButton:string = BUTTON.SAVE;

    rolesOption=[];
    statusOption=[];
    formRol:FormGroup;
    disableButton:boolean = true;
    disableRol:boolean =true;

    userRol:RolesUserTO;

    showSaveUpdate: boolean = true;

    constructor(
        private formBuilder: FormBuilder,
        private _breadcrumb: BreadcrumbService,
        private _rolService: RolService,
        private _toolbarFab: ToolbarFabService,
        private location: Location,
        private _data: DataService,
        private route:Router,
        private dialog:MatDialog,
        private _localService: LocalStorageService,
        private cypher:CypherService) {

        this.showSaveUpdate = this._localService.getRolUserRead() == environment.ROL_USER_READ ? false : true;

        this._data.setIsLoadingEvent(true);

        this.formRol = this.formBuilder.group({
            name:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
            lastName:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)] ],
            mLastName:[ environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)] ],
            phone:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.NUMBER), Validators.maxLength(10),Validators.minLength(1)] ],
            idRol:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.NUMBER)] ],
            email:[ environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.EMAIL)] ],
            status:[ environment.EMPTY_INPUT,[Validators.pattern(EXPRESSION.INPUT_TEXT)] ]
        });

        this._toolbarFab.setVisible( this.route.url.toString() );
        this._rolService
            .getRoles()
            .subscribe( roles => {
                this._breadcrumb.setRouteText( {title:BREADCRUMB.NEW_ROL,arrow:true} );
                this.rolesOption = roles;
                this.userRol = this._rolService.getUserRol();
                if( this.userRol ){
                    this.getUserRoleById(this.userRol);
                    this.formRol.disable();
                    this._breadcrumb.setRouteText( {title:BREADCRUMB.DETAIL_ROLE,arrow:true} );
                }
                console.log('INFO ROL ', this.userRol);
                
                if(!this.userRol){
                    this.disableRol= false;
                } else if (this.userRol.status === 'Nuevo'){
                    this.disableRol = false;
                }
                this._data.setIsLoadingEvent(false);
            }, error => {
                this._data.setIsLoadingEvent(false)
                console.log( error );
            }, () => this._data.setIsLoadingEvent(false));
            
            
        this._rolService
            .getStatus()
            .subscribe( status => {
                this.statusOption = status;
            });
    }

    ngOnInit() {


    }

    getUserRoleById(userId:RolesUserTO) {
        this._data.setIsLoadingEvent(true);
        this._rolService
            .getRoleUserById( userId )
            .subscribe( (userInfo:any) => {
                this.userRolAssig = userInfo;
                this.setValueForm(this.userRolAssig);
                this._data.setIsLoadingEvent(false);
            }, err => this._data.setIsLoadingEvent(false),()=>this._data.setIsLoadingEvent(false));

    }

    setValueForm(userInfo:RolCompoundTO<RolesUserTO>) {
        let user:RolesUserTO = userInfo.roleList[NUMBERS.CERO];
        this.formRol.setValue({
            name:user.name,
            lastName:user.lastName,
            mLastName: user.mLastName,
            phone: user.phone,
            idRol: user.idRol.idRol,
            email: user.email,
            status: user.status
        })
    }

    enableForm(){
        this.formRol.enable();
        this.formRol.get('email').disable();
    }

    onSubmit() {

        this._data.setIsLoadingEvent(true);

        this.disableButton = false;
      
        if(!this.formRol.valid){
            this.disableButton=true;
            this._data.setIsLoadingEvent(false);
            this._data.setGeneralNotificationMessage(MSG.INFORMATION_IS_MISSING);
            return;
        }

        let assigRolUser: RolesUserTO = new RolesUserTO();
        let role = this.rolesOption.find( role => role.idRol == this.formRol.value.idRol );
        let status = this.statusOption.find( status => status.id == this.formRol.value.status );

        if(this.userRolAssig.roleList.length <= 0 && this.formRol.valid){

            assigRolUser = this.formRol.getRawValue();
            assigRolUser.active = true;
            assigRolUser.creationDate = Information.getCurrentDate();
            assigRolUser.idRol = role;
            assigRolUser.idRolAssig = null;
            assigRolUser.lastModification = Information.getCurrentDate();
            assigRolUser.lastUserModifier = this._localService.getUser();
            assigRolUser.creationUser = this._localService.getUser();
            assigRolUser.nameRol = role.descriptionRol;
            assigRolUser.password = this.cypher.encryptString(assigRolUser.email);
            assigRolUser.status = 'N';
            let assigRol: RolCompoundTO<RolesUserTO> = new RolCompoundTO();
            assigRol.setRoleList( assigRolUser );
            assigRol.setImageRole(EMAIL.NEW_USER);
            assigRol.setHtlmRole(EMAIL.NEW_USER);
            this.saveOrUpdate( assigRol );

        }else{

            dialogConfig.disableClose = true;
            dialogConfig.data = { title:'¿Desea modificar la información de este Administrador?',success:'UPDATE' };
            dialogConfig.width = '250px';
            const dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
            this._data.setIsLoadingEvent(false);
            dialogRef.afterClosed().subscribe( resp => {
                if( resp == 'UPDATE' ){
                let data:RolesUserTO = this.userRolAssig.roleList[0];
                assigRolUser = this.formRol.getRawValue();
                assigRolUser.active = data.active
                assigRolUser.creationDate = new Date(data.creationDate);
                assigRolUser.idRol = role;
                assigRolUser.idRolAssig = data.idRolAssig;
                assigRolUser.lastModification = Information.getCurrentDate();
                assigRolUser.lastUserModifier = this._localService.getUser();
                assigRolUser.nameRol = role.descriptionRol;
                assigRolUser.password = data.password;
                assigRolUser.status = assigRolUser.status ==='N'?assigRolUser.status:status.id;

                let assigRol: RolCompoundTO<RolesUserTO> = new RolCompoundTO();
                assigRol.setRoleList( assigRolUser );

                this.saveOrUpdate( assigRol );
                }

                this.disableButton = true;

            });

        }
    }

    saveOrUpdate(assigRol:RolCompoundTO<RolesUserTO>){

        this._data.setIsLoadingEvent(true);

        this._rolService.assignRole(assigRol)
        .subscribe( response => {
            if(response){
                this._data.setIsLoadingEvent(false);
                this._data.setGeneralNotificationMessage(MSG.OK);

                setTimeout(() => {
                    this.route.navigate([`${routesWeb.HOME}${routesWeb.ROLES}`]);
                }, 3900);                                 
            }else {
                this._data.setGeneralNotificationMessage( MSG.USE_EMAIL);
            }
            this.disableButton=true;
            this._data.setIsLoadingEvent(false);
        }, err => {
            this._data.setIsLoadingEvent(false);
            this.disableButton=true;
            this._data.setGeneralNotificationMessage( err.error.message );
            console.log(err);
        },()=>this._data.setIsLoadingEvent(false));
    }

    goBackRoles(){
        this.location.back();
    }

    ngOnDestroy(){
        this._rolService.setUserRol(null);
    }



}
