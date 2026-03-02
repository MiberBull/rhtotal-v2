import { Injectable } from "@angular/core";
import { Subject, of } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map,mergeMap } from 'rxjs/operators';
import { PATH_APPLICATION } from "../../../environments/environment";
import { PATH_FINTECH } from "../../../environments/environment.prod";
import {params} from "../../models/parameters.model"

@Injectable({
    providedIn:'root'
})

export class GenericTableService {

    private columnsGroups = new Subject<any>();
    private headersInactive = new Subject<any>();
    private headersActive = new Subject<any>();
    private itemId:any;

    private cadena:string=`?`;
    private cadenaFilters:string=``;

    constructor( private http: HttpClient ) {


        
    }

    /**
     * Coloca las columnas
     * @param columns
     */
    setColumnsGroups(columns:any) {
        this.columnsGroups.next( columns );
    }

    /**
     * Obtiene el grupo de columnas
     */
    getColumnsGroups() {
        return this.columnsGroups.asObservable();
    }
   
    /**
     * Obtiene la informacion de la tabla
     * y construye el objeto para la tabla
     * @param secction
     * @param page
     */
    getInfoTable( sectionURL:string,header:string,page:string,numberTab = 0) {
        if (header == 'headerFintechWait' || header == 'headerFintechWaitAdvances' || header == 'headerFintechApprovedAdvance' 
           || header == 'headerFintechRejectedAdvance'
            || header == 'headerFintechApprovedVeloCash' || header == 'headerFintechRejectedVeloCash'){
            this.cadena += `page=${page}&nametab=${fintech[numberTab]}`;
        }else {
            //if(header != 'headersUsers')
            //{
                this.cadena += `page=${page}&nametab=${t[numberTab]}`;
            //}
            
        }

        
        this.cadena += this.cadenaFilters;

        let params = this.cadena;
        this.setCadena();
        let data = { infoTable: {} };
        return this.getTableHeaders(header)
                   .pipe(
                       mergeMap( (headers:any) => {
                            data.infoTable['headers'] = headers.value.columns;
                            data.infoTable['titles'] = headers.value.titles;
                            data.infoTable['tabs'] = headers.value.tabs;
                            console.log('peticion a ',sectionURL+params);
                            return this.http.get( sectionURL+params )
                                            .pipe(
                                                map ( dataTable => {
                                                    data.infoTable['infoData'] = dataTable;
                                                    return data;
                                            })
                                        );
                       })
                   )
    }

    /**
     * Metodo que solo obtiene el cuerpo de la
     * tabla
     * @param sectionURL
     * @param header
     * @param page
     * @param numberTab
     */
    getRowsTable( sectionURL:string,header:string,page:string,numberTab? ) {
        let params = new HttpParams()
                     .set( 'page',page )
                     .set( 'nametab',t[numberTab] );
        return this.http.get( sectionURL,{params} );
    }

    /**
     * Retorna las cabeceras en Arreglos (Columns,Titles,Tabs)
     * @param section
     */
    getTableHeaders( header:string ) {
        let URL:string='';
        if (header == 'headerFintechWait' || header == 'headerFintechWaitAdvances' || header == 'headerFintechApprovedAdvance' || header == 'headerFintechRejectedAdvance'
            || header == 'headerFintechApprovedVeloCash' || header == 'headerFintechRejectedVeloCash'){
            URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.HEADERS}`;
        }else {
            URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.HEADERS}`;
        }
        
        let params = new HttpParams()
                     .set( 'section',header);
        return this.http.get( URL,{params} )
                        .pipe(
                            map( (resp:any) => {          

                                                      
                                let allHeader = JSON.parse(resp.headers);
                                let tabs = allHeader.tabs;
                                let columns = allHeader.headers.map(x => x.id);
                                let titles = allHeader.headers.map(x => x.title);
                                let allColumns = { columns, titles,tabs } ;
                                return of(allColumns);
                            })
                        );
    }

    /**
     * Retorna las cabeceras en JSON
     * @param section
     */
    getTableHeadersJSON( header:string ) {
        let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.HEADERS}`;
        let params = new HttpParams()
                         .set( 'section', header);
        return this.http.get( URL, {params} )
                        .pipe(
                            map( (resp:any) => {
                                return JSON.parse(resp.headers).headers;
                            })
                        );
    }

    getTableHeaderFintech(header: string){
        let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.HEADERS}`;
        let params = new HttpParams()
                         .set('section', header);
        return this.http.get(URL, { params })
                    .pipe(
                        map((resp: any) => {
                            console.log('HEADERS DOS ', resp);
                                
                            return JSON.parse(resp.headers);
                        })
                    );
    }

    getTableHeaderFintechColumns(header: string){
        let URL = `${PATH_FINTECH.DOMAIN}/${PATH_FINTECH.HEADERS}`;
        let params = new HttpParams()
            .set('section', header);
        return this.http.get(URL, { params })
            .pipe(
                map((resp: any) => {
                    console.log('HEADERS DOS ', resp);

                    return JSON.parse(resp.headers).headers;
                })
            );        
    }

    /**
     * Coloca las cabeceras Inactivas
     * @param headersInactive
     */
    setHeadersInactive( headersInactive:Array<string> ){
        this.headersInactive.next( headersInactive );
    }

    /**
     * Retorna las cabeceras Inactivas
     */
    getHeadersInactive(){
        return this.headersInactive.asObservable();
    }

    /**
     * Coloca las cabeceras Activas
     * @param headerActive
     */
    setHeadersActive( headerActive: any ){
        this.headersActive.next( headerActive );
    }

    /**
     * Retorna las cabeceras Activas
     */
    getHeadersActive(){
        return this.headersActive.asObservable();
    }

    /**
     * Lanzar el evento para la fila
     * selecionada de la tabla
     * @param item
     */
    setItem( item:any ){
        this.itemId = item ;
    }

    /**
     * Obtener la fila seleccionanda
     */
    getItem(){
        return this.itemId;
    }
    /////****NOTIFICACION***/////
    setParamsNotification(filters:any){

        let parametro = new HttpParams()
                .set('title',filters['titulo'] == null ? '':filters['titulo'].toUpperCase())
                .set('autor',filters['autor'] == null ? '' : filters['autor'].toUpperCase())
                .set('startDate',filters['fechadeenvioinicio'] == null ? '' : filters['fechadeenvioinicio'])
                .set('enddate',filters['fechadeenviofin'] == null ? '' : filters['fechadeenviofin']);

        this.cadenaFilters = `&${parametro.toString()}`;

    }

    setParamsNotificationNull(){
        this.cadenaFilters = `&title=&autor=&startDate=&enddate=`;
    }

    setParamsDiscount(filters:any){
        let parametros = new HttpParams()
                .set('supplier',filters['proveedor'] == null ? '': filters['proveedor'] )
                .set('autor',filters['autor'] ==null ? '': filters['autor'] )                
                .set('startdate',filters['fechainiciodesde'] ==null ? '': filters['fechainiciodesde'] )
                .set('enddate',filters['fechainiciohasta'] ==null ? '': filters['fechainiciohasta']);


        this.cadenaFilters = `&${parametros.toString()}`;
    }

    setParamsDiscountNull(){
        this.cadenaFilters = `&supplier=&autor=&startdate=&enddate=`;
    }

    /**
     * Coloca los filtros para Banners
     * @param filters 
     */
    setParamsBanners(filters:any){
        let parametro = new HttpParams()
        .set('title',filters['titulo'] == null ? '':filters['titulo'])
        .set('autor',filters['autor'] == null ? '' : filters['autor'])
        .set('startdate',filters['fechainiciodesde'] == null ? '' : filters['fechainiciodesde'])
        .set('enddate',filters['fechainiciohasta'] == null ? '' : filters['fechainiciohasta']);

        this.cadenaFilters = `&${parametro.toString()}`;
    }

    setParamsBannersNull(){
        this.cadenaFilters = `&title=&autor=&startDate=&enddate=`;      
    }

    /**
     * Coloca filtros para Fintech
     * */ 
    setParamsFintech(filters:any){
        let parameters = new HttpParams()
            .set('firstName', filters['nombre'] == null ? '' : filters['nombre'].toUpperCase())
            .set('lastName', filters['apellidopaterno'] == null ? '' : filters['apellidopaterno'].toUpperCase())
            .set('secondSurname', filters['apellidomaterno'] == null ? '' : filters['apellidomaterno'].toUpperCase())
            .set('folioRequest', filters['foliosolicitud'] == null ? '' : filters['foliosolicitud'].toUpperCase())
            .set('requestDate', filters['fechasolicitud'] == null ? '' : filters['fechasolicitud']);

        this.cadenaFilters = `&${parameters.toString()}`;

    }

    setParamsFintechNull(){
        this.cadenaFilters = `&firstName=&lastName=&secondSurname=&folioRequest=&requestDate=`;
    }
    
    //////**Seguros**////
    setParamsInsurance(filters:any){

        let parametros = new HttpParams()            
                    .set('insuranceCarrier', filters['aseguradora'] == null ? '' : filters['aseguradora'].toUpperCase())
                    .set('startDate', filters['fechavigenciainiciodesde'] == null ? '' : filters['fechavigenciainiciodesde'])
                    .set('endDate', filters['fechavigenciainiciohasta'] == null ? '' : filters['fechavigenciainiciohasta'])
                    .set('author', filters['numerodepoliza'] == null ? '' : filters['numerodepoliza'].toUpperCase());

        this.cadenaFilters = `&${parametros.toString()}`;
    }



    setParamsInsuranceNull(){
        this.cadenaFilters = `&insuranceCarrier=&startDate=&endDate=&author=`;
    }

    /////*****USERS*******//////

    setParams(filtros:any) {

        let parameters = new HttpParams()
            .set('email', filtros['correoelectronico'] == null ? '' : filtros['correoelectronico'])
            .set('curp', filtros['curp'] == null ? '' : filtros['curp'])
            .set('client', filtros['cliente'] == null ? '' : filtros['cliente'])
            .set('project', filtros['proyecto'] == null ? '' : filtros['proyecto']);

        this.cadenaFilters = `&${parameters.toString()}`;

    }  

    setParamsUsersNull(){
        this.cadenaFilters = `&email=&curp=&client=&project=`;
    }



    setCadena(){
        this.cadena=`?`;
    }

}

export const t = {
    0:'A,I',
    1:'E',
    2:'AP',
    3:'ES',
    4:'R'
}

export const fintech = {
    0:'ES',
    1:'AP',
    2:'R'
}
