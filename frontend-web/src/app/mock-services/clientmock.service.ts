import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { of } from "rxjs";
import { Observable } from "rxjs";
import { CompoundCustomerTO, CustomerTO, ProjectTO } from '../models/clientmodel';

@Injectable({
  providedIn: "root"
})
export class ClientmockService {

  private customerId:CustomerTO;

  constructor(private _http: HttpClient) {}

  setParametros(clientmodel: Array<CompoundCustomerTO>): Observable<any> {
    if (clientmodel.length) return of({ status: "ok" });
    return of({ status: "nook" });
  }

  getClientById(customerId:CustomerTO){
    console.log('Mock');
    return of( {name:'Urbano',address:'Ceron',contact:'Arthur',phone:'123123',
              extension:'123123',email:'u@gmail.com',additionalInformation:'Ceron Santillan Urbano',status:'activo'} );
  }

  setCustomerId(customerId:CustomerTO){
    this.customerId = customerId;
  }

  getCustomerId(){
    return this.customerId;
  }
}
