import { Injectable } from '@angular/core';

import { jsonFormFintech } from '../../util/formsFintech';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_APPLICATION, PATH_FINTECH, API_FINTECH } from '../../../environments/environment.prod';
import { Subject, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class PaysheetService {

  formsFintech = jsonFormFintech;

  fintechJSON:any={};

  tebSelectedJSON:any={};

  private tabFintech = new Subject<any>();
  private statusNulo = new Subject<string>();

  rootSelected:string='';

  constructor(private http: HttpClient) { }

  getInputsFintech(nameForm:string){
    let arrayInputs = this.formsFintech.filter( element => {
      return element.formFintech == nameForm;
    });

    return arrayInputs;
  }

  getCountItemsAdvance(tab = 0, filters = {}) {
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.COUNT_ADVANCE}`;
    let params = new HttpParams()
    .set('nametab', TABS[tab])
    .set('firstName', filters['nombre'] == null ? '' : filters['nombre'])
    .set('lastName', filters['apellidopaterno'] == null ? '' : filters['apellidopaterno'])
    .set('secondSurname', filters['apellidomaterno'] == null ? '' : filters['apellidomaterno'])
    .set('folioRequest', filters['foliosolicitud'] == null ? '' : filters['foliosolicitud'])
    .set('requestDate', filters['fechasolicitud'] == null ? '' : filters['fechasolicitud']);
    return this.http.get(URL, { params });
  }
  
  getCountItemsVeloCash(tab = 0, filters = {}) {
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.COUNT_VELOCASH}`;
    let params = new HttpParams()
    .set('nametab', TABS[tab])
    .set('firstName', filters['nombre'] == null ? '' : filters['nombre'])
    .set('lastName', filters['apellidopaterno'] == null ? '' : filters['apellidopaterno'])
    .set('secondSurname', filters['apellidomaterno'] == null ? '' : filters['apellidomaterno'])
    .set('folioRequest', filters['foliosolicitud'] == null ? '' : filters['foliosolicitud'])
    .set('requestDate', filters['fechasolicitud'] == null ? '' : filters['fechasolicitud']);
    return this.http.get(URL, { params });
  }

  setFintechJSON(idFintech: number, status: string, typeFintech: string, headers:any, titles,tabSelected){
    this.fintechJSON = {
      "id": idFintech,
      "status":status,
      "typeFintech":typeFintech,
      "headers": headers,
      "titles": titles,
      "tab": tabSelected
    };
  }

  getFintechJSON(){
    return this.fintechJSON;
  }

  getFintechAdvance(idFintech, status){
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.ONE_FINTECH_ADVANCE}`;
    let cadenaParams = `?id=${idFintech}&status=${status}`;
    
    return this.http.get(URL + cadenaParams)
    .pipe(
      map( infoFintech => {
        console.log("CONSULTA UNICA ", infoFintech);
        return infoFintech;                                       
      })
      );

  }

  getFintechVeloCash(idFintech, status) {
    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.ONE_FINTECH_VELOCASH}`;
    let cadenaParams = `?id=${idFintech}&status=${status}`;

    return this.http.get(URL + cadenaParams)
    .pipe(
      map(infoFintech => {
        console.log("CONSULTA UNICA ", infoFintech);
        return infoFintech;
      })
      );
  }

  updateStatusFintechAdvance(idFintech, statusFintech, lastUserModifier, lastModification,reasonRejection){

    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.UPDATE_STATUS_ADVANCE}`;

    let params =  new HttpParams()
    .set('idFintech', `${idFintech}`)
    .set('statusFintech', `${statusFintech}`)
    .set('lastUserModifier', `${lastUserModifier}`)
    .set('lastModification', `${lastModification}`)
    .set('reasonRejection', `${reasonRejection}`);

    //let cadenaParam = `?idFintech=${idFintech}&statusFintech=${statusFintech}&lastUserModifier=${lastUserModifier}&lastModification=$/////{lastModification}&reasonRejection=${reasonRejection}`;

    return this.http.get(URL, { params } )
    .pipe(
      map( info => {                             
        return info;
      })
      );

  }

  updateStatusFintechVeloCash(idFintech, statusFintech, lastUserModifier, lastModification, reasonRejection){

    let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.UPDATE_STATUS_VELOCASH}`;
    console.log(":::"+ URL + ":::")

    let params = new HttpParams()
    .set('idFintech', `${idFintech}`)
    .set('statusFintech', `${statusFintech}`)
    .set('lastUserModifier', `${lastUserModifier}`)
    .set('lastModification', `${lastModification}`)
    .set('reasonRejection', `${reasonRejection}`);  
    console.log(params);  

    //let cadenaParam = `?idFintech=${idFintech}&statusFintech=${statusFintech}&lastUserModifier=${lastUserModifier}&lastModification=${lastModification}&reasonRejection=${reasonRejection}`;

    return this.http.get(URL, {params} )
    .pipe(
      map(info => {
        return info;
      })
      );
  }

  getTabFintech(){
    return this.tabFintech.asObservable();
  }

  setTabFintech(tab:any){
    this.tabFintech.next(tab);
  }

  getTabFintechSelection(){
    return this.tebSelectedJSON;
  }
  setTabFintechSelection(tabSelected:any){
    this.tebSelectedJSON = tabSelected;
  }

  getStatusNull() {
    return this.statusNulo.asObservable();
  }

  setStatusNull(tab: string) {
    this.statusNulo.next(tab);
  }

  getRootSelected(){
    return this.rootSelected;
  }

  setRootSelected(root:string){
    this.rootSelected = root;    
  }

  // Method to update status with new api
  /**
   * @function updateVelocashStatus
   * @param {string} statysFintech
   * @param {string} lastUserModifier
   * @param {number} idFintech
   * @param {string} reasonRejection
   * @return {array} info
   **/
  updateVelocashStatus(statusFintech, lastUserModifier, idFintech,  reasonRejection){

    let URL = `${API_FINTECH.DOMAIN}:${API_FINTECH.PORT}/${API_FINTECH.UPDATE_STATUS_VELOCASH}/${API_FINTECH.KEY}`;

    let params = new HttpParams()
    .set('folio', `${idFintech}`)
    .set('status', `${statusFintech}`)
    .set('lastUserModifier', `${lastUserModifier}`)
    .set('reasonRejection', `${reasonRejection}`);  

    console.log(":::"+ URL + ":::")
    console.log(params);
    console.log("::: __________ :::")


    return this.http.put(URL, 
    {
      folio: `${idFintech}`,
      status: `${statusFintech}`,
      lastUserModifier: `${lastUserModifier}`,
      reasonRejection: `${reasonRejection}`
    },
    { responseType: 'text'})
    .pipe(
      map(info => {
        return info;
      })
      );
  }


}

export const TABS = {
  0: 'ES',
  1: 'AP',
  2: 'R'
}
