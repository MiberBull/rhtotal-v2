import { LocalStorageService } from './../local-sotorage/localstorage.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EmployeeComplementaryTO,EmployeeDomicileTO,EmployeeTO ,EmployeeCompensationTO,EmployeeContratingTO,EmployeeDataAssignmentTO,EmployeeSocialNetworkTO,EmployeeHitoryTO} from './../../models/employee.model';
import { Injectable } from "@angular/core";
import { of, Subject, throwError, Observable } from "rxjs";
import { map, catchError, mergeMap } from 'rxjs/operators';
import {tabUser}from '../../models/tapsUser'
import {  STORAGE, PATH_USER,GENERO,STATUS,PERMISSION,TABLE_ROUTE_USERS  } from '../../../environments/environment';

@Injectable({
    providedIn:'root'
})

export class UserService{
    
    private employeeData:any;
    private idUser:number = 0;
    private DatosSico:boolean=false;
    private idEmployee:number=0;
    private tabLabel= new Subject<any>();
    private paramFindSico:any;
    private photography= new Subject<any>();
    private windows= new Subject<any>();
    private windowsIcon = new Subject<any>();
    private tabsStatus= new tabUser();
    private iconStatus= new tabUser();
    private emailUser:string;
    private formEnable:boolean=true;
    private activePhotography= new Subject<any>();
    private isDiableForm:boolean=false;

    private rootHeaderActivate = new Subject<string>();

    constructor(
        private http: HttpClient,
        private _localStorage: LocalStorageService
        
    ){
        this.tabsStatus.asignationStatus=true;
        this.tabsStatus.compensationStatus=true;
        this.tabsStatus.complementaryStatus=true;
        this.tabsStatus.contratingStatus=true;
        this.tabsStatus.domicileStatus=true;
        this.tabsStatus.lastJobStatus=true;
        this.tabsStatus.socialNetworksStatus=true;
        this.iconStatus.asignationStatus=false;
        this.iconStatus.compensationStatus=false;
        this.iconStatus.complementaryStatus=false;
        this.iconStatus.contratingStatus=false;
        this.iconStatus.domicileStatus=false;
        this.iconStatus.lastJobStatus=false;
        this.iconStatus.socialNetworksStatus=false;
    }
    
   

    /**
     * Se crea una empleado
     * @param EmployeeComplementaryTO 
     */
    saveOrUpdateEmployeeComplementary(EmployeeComplementaryTO){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDTE_EMPLOYEE}`;
        return this.http.post(URL,EmployeeComplementaryTO);
    }

/**
     * Se informacion complementaria de usuario
     * @param EmployeeComplementaryTO 
     */
    saveOrUpdateEmployeeDomicile(EmployeeDomicileTO){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_ADDRESS}`;
        return this.http.post(URL,EmployeeDomicileTO);
    }

    /**
     * Se informacion complementaria de usuario
     * @param EmployeeComplementaryTO 
     */
    saveOrUpdateEmployeeCompensation(lista:EmployeeCompensationTO[]){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_COMPESATION}`;
        return this.http.post(URL,lista);
    }

    /**
     * Se informacion complementaria de usuario
     * @param EmployeeContratingTO 
     */
    saveOrUpdateEmployeeContrating(EmployeeContratingTO){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_CONTRATING}`;
        return this.http.post(URL,EmployeeContratingTO);
    }

    /**
     * Se informacion complementaria de usuario
     * @param EmployeeDataAssignmentTO
     */
    saveOrUpdateEmployeeAssignment(EmployeeDataAssignmentTO){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_ASIGNATION}`;
        return this.http.post(URL,EmployeeDataAssignmentTO);
    }

      /**
     * redes sociales de los usuarios
     * @param EmployeeSocialNetworkTO
     */
    saveOrUpdateEmployeeSocialNetwork(list:EmployeeSocialNetworkTO[]){
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_SOCIAL_NETWORK}`;
        return this.http.post(URL,list);
    }

    saveOrUpdateEmployeeHistory(history:EmployeeHitoryTO)
    {
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_HISTORY}`;
        return this.http.post(URL,history);
    }

      /**
     * Obtiene los generos
     */
    getGenero(){
        return of(GENERO);
    }

    getPermission()
    {
        return of(PERMISSION);
    }

    getStatus()
{
    return of(STATUS);
}

    getCivilStatus()
    {
         let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_CIVIL_STATUS}`;
        return this.http.get(URL);
    }

    getState()
    {
         let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_STATE}`;
        return this.http.get(URL);
    }

    getCity(idState=0 )
    {
        let params = new HttpParams()
        .set( 'idState', idState.toString() );
         let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_CITY}`;
        return this.http.get(URL,{params});
    }

    getEmployeeSico(nombre:string,apellidoPaterno:string,fechaNacimiento:Date)
    {
        // SICO integration removed - return empty observable
        return of({total: 0, data: []});
    }

    getWorkInfoSico(curp,name)
    {
        // SICO integration removed - return empty observable
        return of({total: 0, data: null});
    }


    getInternalEmployee(name:string,lastName:string,birthDate:Date)
    {        
        let params = new HttpParams()
            .set('name', name.toUpperCase())
            .set('lastName',lastName.toUpperCase())
            .set('birthDate',birthDate.toISOString());           
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_BY_NAME}`;
        return this.http.get(URL,{params});
    }

    getEmployeeAll()
    {
        let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_ALL_EMPLOYEES}`;
        return this.http.get(URL); 
    }

    convertEmail(email:string)
    {
        let array= email.split("@");
        let str=  array[0].substr(-3);             
        return of(`******${str}@${array[1]}`);
    }

  getEmployeeData(){
      return this.employeeData;
  }

  getIdUserCurrently(){
     return this.idUser;
  }

  getEmailUser()
  {
      return this.emailUser;
  }

  getIdEmployee()
  {
return this.idEmployee;
  }

  setIdEmployee(idEmployee:number)
  {
      this.idEmployee = idEmployee;
  }

  setEmailUser(emailUser:string)
  {
      this.emailUser=emailUser
  }

  /**
   * Coloca un objeto de tipo empployeeComplementary
   * @param discount 
   */
  setEmployee( item: any ) {
    this.idUser=item.idUser;
    this.DatosSico=item.userType==='IN'?true:false;
    
    this.employeeData=item;
  }

  setIdUserEmployee(OIdUser:number)
  {
    this.idUser=OIdUser;
  }

  getEmployeeComplementaryByIdUser(idUser:number)
  {
    let params = new HttpParams()
    .set( 'id', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_COMPLEMENTARY_BY_ID_USER}`;
    return this.http.get(URL,{params});  
  }
  getEmployeeDomicileByIdUser(idUser:number)
  {
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_DOMICILE_BY_ID_USER}`;
    return this.http.get(URL,{params});  
  }

  getSocialNetworkByIdUser(idUser:number)
  {
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_SOCIAL_NETWORK_BY_ID_USER}`;
    return this.http.get(URL,{params});
  }

  getContratingByIdUser(idUser:number)
  {
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_CONTRATING_BY_ID_USER}`;
    return this.http.get(URL,{params}); 
  }

  getCompensation(idUser:number)
  {
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_COMPENSATION_BY_ID_USER}`;
    return this.http.get(URL,{params});
  }

  getHistory(idUser:number){
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_HISTORY_BY_ID_USER}`;
    return this.http.get(URL,{params}); 
  }

  getAsignation(idUser:number)
  {
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_ASIGNATION_BY_ID_USER}`;
    return this.http.get(URL,{params});   
  }

getEmployee(idUser:number)
{
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_BY_ID_USER}`;
    return this.http.get(URL,{params}); 
}

getTabs(idUser:number)
{
    let params = new HttpParams()
    .set( 'idUser', idUser.toString() );
     let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_PROPORTION_TABS_BY_ID_USER}`;
    return this.http.get(URL,{params}); 
}

  getDatosSico()
  {
    return this.DatosSico;
  }

  setDatosSico(flag:boolean)
  {
    this.DatosSico=flag;
  }

  getFoto()
  {
   return this.photography.asObservable();
  }

  setFoto(photography:any)
  {

  this.photography.next(photography);
  }

  



  getParamSico()
  {
     return this.paramFindSico;
  }

  setParamSico(oName:String,oLastName:String,oBirthDate:Date,oCurp:String)
  {
    var param = {name:oName,lastName:oLastName,birthDate:oBirthDate,curp:oCurp}

   this.paramFindSico=param;
  }


  getChangeTab()
  {
    return this.windows.asObservable();
  }

  setChangeTab(tabs:any)
  {
    this.tabsStatus[tabs.tab]=tabs.value;
    this.windows.next(this.tabsStatus);
  }



  getTabLabel()
  {
    return this.tabLabel.asObservable();
  }



  setTabLabel(tab:any)
  {
    this.tabLabel.next(tab);
  }

  getChangeTabIcon()
  {
    return this.windowsIcon.asObservable();
  }

  setChangeTabIcon(tabs:any)
  {
    this.iconStatus[tabs.tab]=tabs.value;
    this.windowsIcon.next(this.iconStatus);
  }

  getActivePhotography()
  {
   return this.activePhotography.asObservable();
  }
  setActivePhotography(value:boolean)
  {
    this.activePhotography.next(value);
  }

  setConvertRespToTabIcon(resp:any)
  {
      var percentageEmployeeComplementary=0;
      console.log(resp.length);
     for(var i=0;i<resp.length;i++)
     {
        switch (resp[i].numTab)
            {
             case 1:
             //employee complementary
                percentageEmployeeComplementary += resp[i].percentageTab;
                break;
                 case 2:
                 //employee
                 percentageEmployeeComplementary += resp[i].percentageTab;
                 break;
                 case 3:
                 //employee address
                 this.iconStatus.domicileStatus = resp[i].percentageTab>99;
                 break;
                 case 4:
                 //social network
                 this.iconStatus.socialNetworksStatus = resp[i].percentageTab>99;
                 break;
                 case 5:
                 //contrating data
                 this.iconStatus.contratingStatus = resp[i].percentageTab>99;
                 break;
                 case 6:
                 //employment history
                 this.iconStatus.lastJobStatus = resp[i].percentageTab>99;
                 break;
                 case 7:
                 //data assignment
                 this.iconStatus.asignationStatus = resp[i].percentageTab>99;
                 break;
                 case 8:
                 //compensation package
                 this.iconStatus.compensationStatus = resp[i].percentageTab>99;
                 break;
            default: 
       alert('Pestaña sin identificar');
       break;
     }
     this.iconStatus.complementaryStatus=(percentageEmployeeComplementary/2)>99;
     }
     this.windowsIcon.next(this.iconStatus);
  }

  getStatusTabs()
  {
  return this.tabsStatus;
  }
  setStatusTabs(tabs:any)
  {
     this.tabsStatus=tabs;
  }

  getStatusIcon()
  {
  return this.iconStatus
  }
  setStatusIcon(tabsIcon:any)
  {
     this.iconStatus=tabsIcon;
  }

  setBaseStatus(value:boolean)
  {
    for (const prop in this.tabsStatus)
    {
        this.tabsStatus[prop]=value;
   }
  }

  getFormEnable()
  {
    return this.formEnable;
  }
  setFormEnable(value:boolean)
  {
   this.formEnable=value;
  }

  getCountRow(filters={}){

      let URL = TABLE_ROUTE_USERS.GET_COUNT_ROW_USERS;

      let params = new HttpParams()
          .set('email', filters['correoelectronico'] == null ? '' : filters['correoelectronico'])
          .set('curp', filters['curp'] == null ? '' : filters['curp'])
          .set('client', filters['cliente'] == null ? '' : filters['cliente'])
          .set('project', filters['proyecto'] == null ? '' : filters['proyecto']);
        

      return this.http.get(URL, { params });

  }

  getAllClient()
  {
       let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_ALL_CLIENT}`;
      return this.http.get(URL);
  }

  getProyectByIdClient(idClient:number)
  {
    let params = new HttpParams()
    .set( 'idClient', idClient.toString() );
       let URL = `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_PROJECT_BY_ID_CLIENT}`;
      return this.http.get(URL,{params});
  }

  getIsDisable(){
      return this.isDiableForm;
  }

  setIsDisable(disable:boolean){
      this.isDiableForm = disable;
  }

  getRootHeaderActivate(){
      return this.rootHeaderActivate.asObservable();
  }

  setRootHeaderActivate(root:string){
      this.rootHeaderActivate.next(root);
  }

}

