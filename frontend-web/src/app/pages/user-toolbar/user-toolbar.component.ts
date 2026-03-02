import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {tabUser}from '../../models/tapsUser'
import { from } from 'rxjs';
import { UserService } from '../../services/user/user.service';
import { timeout } from 'q';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB } from '../../../environments/environment.prod';


@Component({
  selector: 'app-user-toolbar',
  templateUrl: './user-toolbar.component.html',
  styleUrls: ['./user-toolbar.component.css']
})
export class UserToolbarComponent implements OnInit {



   tabsUser:tabUser;
   tabsIcon:tabUser;
  tabsLabel:string;
  private idUser:number;
  constructor(private route:Router,
    private _userService:UserService ,
    private _breadcrumb:BreadcrumbService) { 

    this._userService.getTabLabel()
      .subscribe(tab => this.tabsLabel = tab);

    this._userService.getChangeTabIcon().subscribe(res => {
        this.tabsIcon = res;
    });      

     this.tabsUser= new tabUser();
     this.tabsIcon= new tabUser();
     this.tabsLabel="datos-personales";
     this.tabsUser.asignationStatus=true;
     this.tabsUser.compensationStatus=true;
     this.tabsUser.complementaryStatus=true;
     this.tabsUser.contratingStatus=true;
     this.tabsUser.domicileStatus=true;
     this.tabsUser.lastJobStatus=true;
     this.tabsUser.socialNetworksStatus=true;
     this.tabsIcon.complementaryStatus = false;
     this.tabsIcon.domicileStatus = false;
     this.tabsIcon.socialNetworksStatus = false;
     this.tabsIcon.contratingStatus = false;
     this.tabsIcon.lastJobStatus = false;
     this.tabsIcon.compensationStatus = false;
     this.tabsIcon.asignationStatus = false; 
     this.tabsUser= _userService.getStatusTabs(); 
     this.idUser = _userService.getIdUserCurrently();

    if(this.idUser>0)
    {
      this._breadcrumb.setRouteText( {title:BREADCRUMB.DETAIL_USER,arrow:true} );
      this._userService.getTabs(this.idUser)
      .subscribe(resp => {  
       this._userService.setConvertRespToTabIcon(resp);

      });

    

     // setTimeout(()=>{
     //   this.tabsIcon=_userService.getStatusIcon();
      //},1000);


    }
    else{       
      this._breadcrumb.setRouteText( {title:BREADCRUMB.NEW_USER,arrow:true} );
  }
    }

  ngOnInit() {
  }

  changeForm( form:string ){
    this.route.navigate([`home/admin-usuario/${ form }`])
    this.tabsLabel=form;
  }

  navigate = {
    personalInfo:'datos-personales',
    domicile:'domicilio',
    socialNetworks:'redes-sociales',
    contractingData:'datos-contratacion',
    lastJob:'ultimo-empleo',
    compenPackage:'paquete-compensacion',
    assignmmentData:'datos-asignacion'
  }

  FunctionColor()
  {

  }



}
