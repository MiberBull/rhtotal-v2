import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PATH_APLICATION,ARRAYPARAM,INSERT_DB_RHTOTAL} from '../../environments/environments';
import { Observable } from '../../../node_modules/rxjs';
import { DbStorageRhtotalProvider } from '../db-storage-rhtotal/db-storage-rhtotal';
/*
  Generated class for the ConsumeSaveInfoProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ConsumeSaveInfoProvider {

  constructor(public http: HttpClient,public dbRhtotal:DbStorageRhtotalProvider) {}
  
 getParameter(parameter:string){
    return this.http.get(`${PATH_APLICATION.DOMAIN}${PATH_APLICATION.PARAMETER}${parameter}`)
    .catch(this.handleError);
 }

saveDataRhtotal(){
for (let index = 0; index < ARRAYPARAM.length; index++) {
    const element = ARRAYPARAM[index];
    this.getParameter(element).subscribe(data =>{
    this.dbRhtotal.saveOrUpdate(INSERT_DB_RHTOTAL.INSERT_COMPANY_INFORMATION,[data.idCompanyInformation,element,data.dsValue]).then(db =>{
        console.log('okas')
    }).catch(error => console.log('error guardado'));
  });
}  
}

handleError(error: any) {
  console.log(error);
  return Observable.throw(error);
}

}
