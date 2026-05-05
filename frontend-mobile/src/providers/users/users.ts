import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from '../../../node_modules/rxjs';
import { PATH_USER } from '../../environments/environments';
import { Camera, CameraOptions } from '@ionic-native/camera';
import {File, FileEntry, IFile} from "@ionic-native/file";
import { 
  EmployeeDomicileTO,
  SocialNetworkTO,
  MycvTO, 
  CompensationPackageTO,
  ContratingDataTO,
  AsignationDataTO,
  EmployeePersonalTO} from '../../models/employee.compl';
  import { IOSFilePicker } from '@ionic-native/file-picker';
  import { UserTO, EmployeeHitoryTO } from '../../models/user.model';
  import { FilePath } from '../../../node_modules/@ionic-native/file-path';
  import { Base64 } from '../../../node_modules/@ionic-native/base64';
  import { AlertController } from 'ionic-angular';
  import { Jobs } from '../../models/job.model';
  import { API_RHTOTAL } from '../../environments/environments';

/*
  Generated class for the UsersProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
  */
  @Injectable()
  export class UsersProvider {
    private tabNav = new Subject<any>();


    constructor(public http: HttpClient,private filePicker: IOSFilePicker,public alertCtrl: AlertController,private base64: Base64,private camera: Camera,private file: File,private filePath: FilePath) {
      console.log('Hello UsersProvider Provider');
    }

    getEmployeeById(id:number){
      let params = new HttpParams()
      .set('id',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_EMPLOYEE_COMPL_BY_ID}`,{params}).catch(this.handleError);
    }


    getCivilStatus(){
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.CIVIL_STATUS}`).catch(this.handleError);
    }


    saveEmployeComplementary(employee:EmployeePersonalTO){
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_EMPLOYE}`,employee).catch(this.handleError);
    }
    getExistUserRhTotal(nombre:string,aPaterno:string,fechaNacimiento:Date){
      let params = new HttpParams()
      .set('name',nombre)
      .set('lastName',aPaterno)
      .set('birthDate',fechaNacimiento.toISOString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.USER_EXIST_RH_TOTAL}`,{params}).catch(this.handleError);
    }

    getExistUserSico(nombre:string,aPaterno:string,fechaNacimiento:string){
      return Observable.of({ total: 0, data: [] });
    }

    handleError(error: any) {
      console.log(error);
      return Observable.throw(error);
    }

    getImage(){

      var options: CameraOptions = {
        quality: 70,
        destinationType: this.camera.DestinationType.DATA_URL,
        sourceType:this.camera.PictureSourceType.PHOTOLIBRARY,
        saveToPhotoAlbum:false,
        allowEdit:true,
        targetHeight:300,
        targetWidth:300
      }
      return this.camera.getPicture(options);
    }  



    getState(){
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_STATE}`).catch(this.handleError);
    }


    getCity(id:number){
      let params = new HttpParams().set('idState',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_CITY}`,{params}).catch(this.handleError);
    }

    saveDireccion(body:EmployeeDomicileTO){
      return this.http.post(` ${PATH_USER.DOMAIN}/${PATH_USER.SAVE_ADDRESS}`,body).catch(this.handleError);
    }

    getAddress(id:number){
      let params = new HttpParams().set('idUser',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_ADDRESS}`,{params}).catch(this.handleError);
    }


    getTabsEvent(){
      return this.tabNav.asObservable();
    }

    setTabsEvent(tabNav: any){
      this.tabNav.next(tabNav);
    }

    getEmployeeByIdUser(id:number){
      let params = new HttpParams().set('idUser',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_EMPLOYEE}`,{params}).catch(this.handleError);
    }

    saveSocialNetwork(body:Array<SocialNetworkTO>){
      return  this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_SOCIAL}`,body).timeout(100000).catch(this.handleError);
    }

    getSocialNetwork(id:number){
      let params = new HttpParams().set('idUser',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_SOCIAL}`,{params}).catch(this.handleError);
    }


    updateUser(body:UserTO){
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_USER}`,body).timeout(20000).catch(this.handleError);
    }

    saveJobs( jobs:Jobs[] ) {
      let URL = `${PATH_USER.DOMAIN}/${PATH_USER.SAVE_JOBS}`;
      return this.http.post( URL,jobs ).timeout(10000).catch(this.handleError);
    }

    getJobsByIdUser( idUser:number ) {
      let URL = `${PATH_USER.DOMAIN}/${PATH_USER.GET_JOBS}?idUser=${idUser}`;
      return this.http.get( URL ).timeout(10000).catch(this.handleError);
    }


    saveMyCv(body:MycvTO){
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_MY_CV}`,body).catch(this.handleError);
    }

    getMyCv(email:string){
      let params = new HttpParams().set('email',email);
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_MY_CV}`,{params}).catch(this.handleError);
    }

    getFileName(): Promise<string> {
      return new Promise((resolve, reject) => {
        (<any>window).OurCodeWorld.Filebrowser.filePicker.single({
          success: function(data){
            if(!data.length){
              reject('');
            }
            resolve(data[0].toString());
          },
          error: function(err){
            console.log(err);
            reject('');
          }
        });
      });
    }

    getFileInfo(): Promise<any> {
      return this.getFileName().then(fileURI => {
        return this.filePath.resolveNativePath(fileURI).then((filePath) => {
          return this.file.resolveLocalFilesystemUrl(filePath).then((fileEntry: FileEntry) => {
            return new Promise((resolve, reject) => {
              fileEntry.file(meta => resolve(meta), error => reject(error));
            });
          }).then((fileMeta: IFile) => {
            return new Promise((resolve, reject) => {
              return this.base64.encodeFile(filePath).then((base64Data) => {
                resolve({
                  fileData: base64Data,
                  fileName: fileMeta.name,
                  fileSize: fileMeta.size,
                  fileType: fileMeta.type
                })
              }).catch((error) => {
                reject(error);
              })
            })
          });
        });
      });
    }

    getFileIOS (): Promise<any>{
      return this.filePicker.pickFile().then((fileURI) => {
        let filePath = 'file://' + fileURI;
        return this.file.resolveLocalFilesystemUrl(filePath).then((fileEntry: FileEntry) => {
          return new Promise((resolve, reject) => {
            fileEntry.file(meta => resolve(meta), error => reject(error));
          });
        }).then((fileMeta: IFile) => {
          return new Promise((resolve, reject) => {
            console.log('filePath', filePath);
            console.log('fileMeta', JSON.stringify(fileMeta));
            return this.file.readAsDataURL(fileMeta.localURL.replace(fileMeta.name,''), fileMeta.name).then((base64Data) => {
              resolve({
                fileData: base64Data,
                fileName: fileMeta.name,
                fileSize: fileMeta.size,
                fileType: fileMeta.type
              })
            }).catch((error) => {
              reject(error);
            })
          })
        });
      });
    }

    showConfirmAlert(title:string,content:string) {
      let alert = this.alertCtrl.create({
        title:`<label>${title}</label>`,
        subTitle:`<h6>${content}</h6>`,
        buttons:[
        {
          text: 'Aceptar',
          handler: () => {

          }
        }],
        cssClass:'alertCustomCss'
      });
      alert.present();
    }


    showConfirmAlertCenter(title:string,content:string) {
      let alert = this.alertCtrl.create({
        title:`<label>${title}</label>`,
        subTitle:`<h6>${content}</h6>`,
        buttons:[
        {
          text: 'Aceptar',
          handler: () => {

          }
        }],
        cssClass:'class-center-alert'
      });
      alert.present();
    }


    saveConpesationPackage(listPackage:Array<CompensationPackageTO>){
      return  this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_COMPENSATION}`,listPackage).catch(this.handleError);
    }

    getCompensationPackage(id:number){
      let params = new HttpParams().set('idUser',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_COMPENSATION}`,{params}).catch(this.handleError);
    }


    getWorkInfoSico(curp,name)
    {
      return Observable.of({ data: { personal: {}, pago: [] } });
    }


    getContratingByIdUser(idUser:number)
    {
      let params = new HttpParams()
      .set( 'idUser', idUser.toString() );
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_CONTRATING_BY_ID_USER}`,{params}).catch(this.handleError); 
    }


    saveOrUpdateEmployeeContrating(data:ContratingDataTO){
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_CONTRATING}`,data).catch(this.handleError);
    }


    saveOrUpdateAsignationData(data:AsignationDataTO){
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.SAVE_UPDATE_ASIGNATION_DATA}`,data).catch(this.handleError);
    }

    getAsignacionDataByIdUser(idUser:number)
    {
      let params = new HttpParams()
      .set( 'idUser', idUser.toString() );
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.GET_ASIGNATION_DATA}`,{params}).catch(this.handleError); 
    }

    saveOrUpdateEmployeeHistory(history:EmployeeHitoryTO)
    {
      return this.http.post(`${PATH_USER.DOMAIN}/${PATH_USER.USER_SAVE_UPDATE_EMPLOYEE_HISTORY}`,history).catch(this.handleError);
    }

    getHistory(idUser:number){
      let params = new HttpParams()
      .set( 'idUser', idUser.toString() );
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_HISTORY_BY_ID_USER}`,{params}).catch(this.handleError); 
    }


    getPonderation(idUser:number){
      let params = new HttpParams()
      .set( 'idUser', idUser.toString() );
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.PONDERATION_DATA}`,{params}).catch(this.handleError); 
    }

    getInfoCredential(id:number){
      let params = new HttpParams()
      .set('idUser',id.toString());
      return this.http.get(`${PATH_USER.DOMAIN}/${PATH_USER.CREDENTIAL_INFO}`,{params}).catch(this.handleError);
    }

    deleteJobsForUser( jobs:any ) {
      console.log( jobs );
      let URL = `${PATH_USER.DOMAIN}/${PATH_USER.DELETE_JOBS}`;
      return this.http.post( URL,jobs ).catch(this.handleError);
    }

  /**
   * @function getBankInfoByEmployeeId
   * @param {any} employee_id
   * @return {array} http.response
   **/
   getBankInfoByEmployeeId(employee_id: any) {
     let apiurl = `${API_RHTOTAL.DOMAIN}:${API_RHTOTAL.PORT}`; // Base URL to consume Api
     let sendParameters = API_RHTOTAL.GET_BANK_INFO_BY_ID_EMPLOYEE;
     sendParameters = sendParameters.replace(':apikey', API_RHTOTAL.KEY); //aplying apikey
     sendParameters = sendParameters.replace(':employee_id', employee_id); // sendto is the name of mail use of senders

     return this.http.get(`${apiurl}${sendParameters}`).timeout(5000).catch(this.handleError); // return the response or error
   }
   /**
    * @function getEmployeeInfoSico (DEPRECATED - SICO service removed)
    * @param {any} curp
    * @param {any} nombres
    * @param {any} fechaNacimiento
    * @param {any} rfc
    **/
    getEmployeeInfoSico(curp: any,nombres: any,fechaNacimiento: any,rfc: any)
    {
      return Observable.of({ data: { personal: {}, pago: [] } });
    }

   /**
    * @function getEmployeeCFDI (DEPRECATED - SICO service removed)
    * @param {any} nombres
    * @param {any} apellidoPAterno
    * @param {any} mesConsulta
    * @param {any} rfc
    **/
    getEmployeeCFDI(nombres: string, apellidoPaterno: string, rfc: string, mesConsulta: any){
      return Observable.of({ data: [] });
    }

  /**
  * @function employeesByProject Servicio para enviar correos sin utilizar un template predefinido en BD
  * @params emailbody: emailTO modelo de envio para correos
  **/
  employeesByProject(project){
     let apiurl = `${API_RHTOTAL.DOMAIN}:${API_RHTOTAL.PORT}`; // Base URL to consume Api
     let sendParameters = API_RHTOTAL.EMPLOYEE_BY_PROJECT;
     sendParameters = sendParameters.replace(':apikey', API_RHTOTAL.KEY); //aplying apikey
     sendParameters = sendParameters.replace(':project', project); // sendto is the name of mail use of senders

     return this.http.get(`${apiurl}${sendParameters}`).timeout(5000).catch(this.handleError); // return the response or error
  }

  }



