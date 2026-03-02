import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { environment } from '../../../../environments/environment.prod';
import { EmployeeSocialNetworkTO,SocialNetworksTO} from '../../../models/employee.model';
import { UserService } from '../../../services/user/user.service';
import { DataService } from '../../../services/data.service';
import { MSG } from '../../../../environments/environment';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import {UserTO} from '../../../models/user.model';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-form-social-networks',
  templateUrl: './user-form-social-networks.component.html',
  styleUrls: ['./user-form-social-networks.component.css']
})
export class UserFormSocialNetworksComponent implements OnInit {

  socialNetwork= new  SocialNetworksTO();

  showSaveUpdate: boolean = true;
  listSocialNetwork = Array<EmployeeSocialNetworkTO>();
  selectState:boolean=true;
  selectEnabled:boolean=false;
  Facebook:boolean=true;
  userAdmin:UserTO;
  idUserEmployee:number;
  
  disableControl:boolean = false;
  

  constructor(
    private fb:FormBuilder,
    private _data: DataService,
    private _userService: UserService,
    private _localStorage: LocalStorageService,
    private ref: ChangeDetectorRef,
    private router :Router,
    private location: Location,
  ) {

    this.disableControl = this._userService.getIsDisable();
    

    this._data.setIsLoadingEvent(true);
    this._userService.setTabLabel('redes-sociales');
    this._userService.setActivePhotography(false);

    this.showSaveUpdate = this._localStorage.getRolUserRead() == environment.ROL_USER_READ ? false : true;

    this.socialNetwork.Facebook=null;
    this.socialNetwork.Twitter=null;
    this.socialNetwork.LinkedIn=null;
    this.socialNetwork.Google=null;
    this.socialNetwork.Instagram=null;
    this.socialNetwork.Snapchat=null;
    this.socialNetwork.Spotify=null;

    this.idUserEmployee = _userService.getIdUserCurrently();
    this._userService.getTabs(this.idUserEmployee)
    .subscribe(resp => {  
     this._userService.setConvertRespToTabIcon(resp);

    });

    if(!_userService.getFormEnable())
    {
      this.showSaveUpdate=false;
    }
    this._localStorage
    .getItem("answerLogin","user")
    .subscribe(user => {
      this.userAdmin=user;
    });

    if(this.idUserEmployee)
    {
    this._userService.getSocialNetworkByIdUser(this.idUserEmployee)
    .subscribe((resp:EmployeeSocialNetworkTO[])=> { 
      this.listSocialNetwork=resp;

      resp.forEach(prop => {
        this.socialNetwork[prop.nameRedSocial] = prop.value ==='0'?false : prop.value ==='2'?true:null; 
             });
     this._data.setIsLoadingEvent(false);
    }, err => {
      if(err.error.message =='No value present')
      {

      }
      else{
        this._data.setGeneralNotificationMessage( err.error.message );
      }

      this._data.setIsLoadingEvent(false);
    });
    

  }

}


  ngOnInit() {
 
  }
  changeState(event){

  }
  save()  {
    this._data.setIsLoadingEvent(true);
    var oSocialNetwork = Array<EmployeeSocialNetworkTO>();
    if (this.listSocialNetwork.length>1)
    {
      this.listSocialNetwork.forEach(prop => { 
        prop.value=this.socialNetwork[prop.nameRedSocial]==true?'2':this.socialNetwork[prop.nameRedSocial]==false?'0':'1';
        prop.lastUserModifier=this.userAdmin.email;
      });

      oSocialNetwork=this.listSocialNetwork;
    }
    else
    {
      let i=0;
      for (const prop in this.socialNetwork) {
        let socialNetwork=new EmployeeSocialNetworkTO;
        socialNetwork.nameRedSocial=prop;
        socialNetwork.value=this.socialNetwork[prop]==true?'2':this.socialNetwork[prop]==false?'0':'1';
        socialNetwork.parameterDescription="";
        socialNetwork.creationUser=this.userAdmin.email;
        socialNetwork.lastUserModifier=this.userAdmin.email;
        socialNetwork.idUSer=this.idUserEmployee;
        oSocialNetwork[i]= socialNetwork;
  i+=1;
    }

    

}
this._userService.saveOrUpdateEmployeeSocialNetwork(oSocialNetwork)
    .subscribe( response => {
        this._data.setIsLoadingEvent(false);
        if(response){
                      this._data.setGeneralNotificationMessage(MSG.OK);
                      this.router.navigate([`home/admin-usuario/datos-contratacion`]);
        }else {
          this._data.setGeneralNotificationMessage("No se realizo ningún registro");
        }
    }, err => {
        this._data.setIsLoadingEvent(false);
        this._data.setGeneralNotificationMessage( err.error.message );
    });

}

}
