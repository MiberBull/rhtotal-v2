import { QUERY_DB_RHTOTAL} from '../../environments/environments';
import { Injectable } from '@angular/core';
import { DbStorageRhtotalProvider} from '../db-storage-rhtotal/db-storage-rhtotal';
/*
  Generated class for the ConsumeHelpProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ConsumeHelpProvider {

  constructor(public dbRhtotal:DbStorageRhtotalProvider) {
    console.log('Hello ConsumeHelpProvider Provider');
  }

  getParameter(parameter:string){
     return this.dbRhtotal.getAllByParameter(QUERY_DB_RHTOTAL.QUERY_COMPANY_INFORMATION,[parameter]);

  }
}
