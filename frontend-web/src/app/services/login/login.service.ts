import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,Subject } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { parameters, PATH_SECURITY } from './../../../environments/environment';

import { LoginTO } from './../../models/user.model';
import { CypherService } from '../cypher/cypher.service';

@Injectable({
    providedIn:'root'
})


export class LoginService{

    user:string;
    isLogged:boolean;
    isLoggedEvent = new Subject<boolean>();

    constructor( private http: HttpClient,private cypher:CypherService ){}
    
    /**
     * Obtencion de parametros
     * ejm: Intentos de login
     * @param parameter 
     */
    getParameters(parameter:string):Observable<any>{
        return this.http.post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.GET_PARAMETERS}?${parameters.name}=${parameter}`,null)
                        .pipe(catchError(this.handleError));
    }

    /**
     * Inicio de sesión
     * @param objectTO 
     */
    login(objectTO:LoginTO):Observable<any>{
        objectTO.user.password = this.cypher.encryptString( objectTO.user.password );
        return this.http.post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.LOGIN}`,objectTO)
                        .pipe(catchError(this.handleError));
    }
    
    /**
     * Bloqueo de usuario por pasar los intentos
     * @param userBlock 
     */
    blockingUserByIntents(userBlock:LoginTO){
        return this.http.post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.LOGIN}`,userBlock)
                        .pipe(catchError(this.handleError));
    }

    /**
     * Envio de email, indicando la plantilla
     * @param email 
     * @param template 
     */
    sendEmailWithTemplate( email:string,template:string ){
        let URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.SEND_EMAIL}?${parameters.EMAIL}=${email}&&${parameters.TEMPLATE}=${template}`
        console.log( URL );
        return this.http
                   .get(URL)
                   .pipe(catchError(this.handleError));
    }

    /**
     * Cerrar sesión
     */
    getIsLogged() {
        return this.isLoggedEvent.asObservable();
    }
    
    /**
     * Inicio de sesión
     * @param isLogged 
     */
    setIsLogged(isLogged: boolean) {
        this.isLogged = isLogged;
        this.isLoggedEvent.next(isLogged);
    }

    /**
     * Obtener Error
     * @param error 
     */
    handleError(error: any) {
        return Observable.throw(error || 'Server error');
    }

    cryptPassword( pwd:string ) {

    }

}