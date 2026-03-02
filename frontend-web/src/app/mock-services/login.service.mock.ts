import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';

import { UserTO, LoginTO } from './../models/user.model';

@Injectable({
    providedIn:'root'
})

export class LoginMockService{

    user:LoginTO;
    isLogged:boolean;
    isLoggedEvent = new Subject<boolean>();

    constructor( private http: HttpClient ){
    }

    getParameters():Observable<any>{
        return of({
                        "error": true,
                        "message":null,
                        "value":2
                    }
                );
    }

    login(objectTO:LoginTO):Observable<any>{

        if( objectTO.user.email == 'urbano@axity.com' && objectTO.user.password == '12345678' ){
            return this.http.get('/assets/data/login.json')
                            .pipe( catchError(this.handleError) );
        }

        if( objectTO.user.email == 'urbano@axity.com' ){
            return of({"flag":2,"message":"Contraseña incorrecta" });
        }
        
        return of({"flag":1,"message":"El usuario que ingreso no es válido"});
    }

    blockingUserByIntents(user:LoginTO){
        return of({"message":"Usuario Bloquedo"});
    }

    getMenuForUser(userToken:LoginTO){
        return this.http.get('/assets/data/menu.json')
                        .pipe( catchError(this.handleError) );
    }

    sendEmailToRecoverPassword(userEmail: UserTO){
        return of({ response: "ok" });
    }

    getIsLogged() {
        return this.isLoggedEvent.asObservable();
    }
    
    setIsLogged(isLogged: boolean) {
        this.isLogged = isLogged;
        this.isLoggedEvent.next(isLogged);
    }

    handleError(error: any) {
        return Observable.throw( new Error(`${error}`) );
    }


}