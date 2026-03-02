import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { mergeMap, map, debounce } from "rxjs/operators";

import { LocalStorageService } from "../local-sotorage/localstorage.service";

import { CompoundCustomerTO, CustomerTO } from "../../models/clientmodel";
import { PATH_CLIENT } from "../../../environments/environment";
import { PATH_APPLICATION } from "../../../environments/environment.prod";

@Injectable({
  providedIn: "root"
})

export class ClientService {
  
  private customerId:CustomerTO;
  private idClient:number;
  
  constructor(
    private http: HttpClient,
    private _localStorage: LocalStorageService ) {}

  /**
   * 
   * @param compoundCustomer 
   */
  saveOrUpdateClient(compoundCustomer: CompoundCustomerTO) {
    let URL = `${PATH_APPLICATION.DOMAIN}/client/saveOrUpdateClient`
    return this.http.post( URL,compoundCustomer );

  }

  getClientById(customerId:number){
    let URL = `${PATH_CLIENT.DOMAIN}/${PATH_CLIENT.SEARCH_BY_ID}`;
    let params = new HttpParams()
                 .set( 'idCustormer', customerId.toString() );
    return this.http.get( URL, {params} );
  }


/**
   * Servicio para Obtener el total de registros
   * @param  
   */
  queryPaginator( ) {
    let URL = `${PATH_CLIENT.DOMAIN}/${PATH_CLIENT.PAGINATOR}`;
    return this.http.get( URL );
                    
  }



  /**
   * Serviio para obtener la informacion para 
   * la tabla
   *  
   * @param page 
   * @param nameProject 
   * @param nameClient 
   */
  getInfoData(page=0,nameProject='',nameClient='') {
    let URL = `${PATH_APPLICATION.DOMAIN}/client/getPagedClient`;
    let params = new HttpParams()
                 .set( 'page',page.toString() )
                 .set( 'nameProject',nameProject )
                 .set( 'nameClient',nameClient );
    return this.http.get( URL,{params} )
                    .pipe(
                      map( (resp:any) => {

                        console.log('datos de base',resp);
                          return resp.map( item =>{
                            let sum = { empleados:0,sueldos:0 };
                            item.projects.forEach( project => {
                                project.active = project.status == 'A' ? 'Activo' : 'Inactivo';
                                sum.empleados = project.employee.numberEmployees + sum.empleados;
                                sum.sueldos = project.employee.sueldoBruto + sum.sueldos;
                            });
                            item.employes = sum.empleados;
                            item.sueldos = sum.sueldos;
                            item.status=item.status == 'A' ? 'Activo' : 'Inactivo';
                            return item;
                          });
                        })
                      );
  }

  /**
   * Servicio para verificar si se puede 
   * desactivar un cliente
   * @param idCustomer 
   */
  queryCustomerForInvalid( idCustomer:number ) {
    let URL = `${PATH_APPLICATION.DOMAIN}/notificationassignment/getProjects?id=${ idCustomer }`;
    return this.http.get( URL )
                    .pipe(
                      map( (resp:any) => {
                        return resp.length > 0;
                      })
                    );
  }

  /**
   * Servicio para verificar si se puede
   * desactivar un proyecto
   * @param idProject 
   */
  queryProjectForInvalid( idProject:number ) {
    let URL = `${PATH_APPLICATION.DOMAIN}/notificationassignment/getEmployee?id=${idProject}`;
    return this.http.get( URL )
                    .pipe(
                      map( (resp:any) => {
                        console.log( resp.length );
                        return resp.length > 0;
                      })
                    )
  }

  query

  /**
   * Coloca el id del Cliente
   * @param customerId 
   */
  setCustomerId(customerId:CustomerTO){
    this.customerId = customerId;
  }

  /**
   * Obtener el id del Cliente
   */
  getCustomerId(){
    return this.customerId;
  }

  /**
   * Coloca el numero de cliente
   * @param idClient 
   */
  setIdClient( idClient:number ) {
    this.idClient = idClient;
  }

  /**
   * Retorna el id del cliente
   */
  getIdClient() {
    return this.idClient;
  }

  


}
