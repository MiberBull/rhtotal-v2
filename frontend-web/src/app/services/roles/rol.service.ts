import { LocalStorageService } from './../local-sotorage/localstorage.service';
import { HttpClient } from '@angular/common/http';
import { RolesUserTO, RolCompoundTO } from './../../models/rol.model';
import { Injectable } from "@angular/core";
import { of, Subject, throwError, Observable } from "rxjs";
import { map, catchError, mergeMap } from 'rxjs/operators';
import { PATH_SECURITY, STORAGE, STATUS_ROLES, PATH_USER } from '../../../environments/environment';
import { parameters } from '../../../environments/environment';


@Injectable({
    providedIn:'root'
})

export class RolService{
    
    private userRol:RolesUserTO;

    constructor(
        private http: HttpClient,
        private _localStorage: LocalStorageService
        
    ){}
    
    /**
     * Obtiene los roles para la pantalla de ROLES
     */
    getRoles(){
        let URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.ROLES_CATALOGUE}`;
        return this.http.get(URL)
                        .pipe(
                            map( (roles:any) => {
                                roles.roleList=  roles.roleList.filter(
                                    x=> x.descriptionRol !="Administrador Master");                                 
                                return roles.roleList;
                            }),
                            catchError( error => {
                                console.log( error );
                                return of({error});
                            })
                        );
    }

    /**
     * Consulta particular por ID 
     * para el detalle de asignacion de ROL
     * @param userId 
     */
    getRoleUserById(userId:RolesUserTO){
        let URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.USER_ROLE_INFO}`;
        let userRole: RolCompoundTO<RolesUserTO> = new RolCompoundTO();
        userRole.setRoleList(userId);
        return this._localStorage.getItem(STORAGE.ANSWER_LOGIN,STORAGE.TOKEN)
                                .pipe(
                                    mergeMap( token => {
                                        userRole.setToken(token);
                                        return this.http.post(URL,userRole);
                                    }),
                                    catchError( err => {
                                        console.log("Error al consultar usuario");
                                        return throwError(err); 
                                    })
                                );
    }

    /**
     * Retorna los status que llenan el select de la 
     * pantalla de ROLES
     */
    getStatus(){
        return of(STATUS_ROLES);
    }

    /**
     * Se crea una asignacion de ROL para el usuario
     * @param user 
     */
    assignRole(user:any){
        let URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.SAVE_USER_ROLE}`;
        return this.http.post(URL,user);
    }

    getCountItems(){
        let URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.COUNT_ROLE}`;
        return this.http.get( URL );
    }

    /**
     * Metodo para colocar el objeto 
     * de userRol
     * @param userRol 
     */
    setUserRol(userRol:any){
        this.userRol = userRol;
    }

    /**
     * Metoto que retorna el objeto
     * de UserRol
     */
    getUserRol(){
        return this.userRol;
    }

    /**
     * Resetea el password del usuario
     * @param token 
     * @param email 
     * @param password 
     * @param confirmedPassword 
     */
    sendResetPassword(token: string , email: string, password: string, confirmedPassword: string): Observable<any> {
        return this.http.post(
          `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.RESET_CONFIRMATION_ENDPOINT}`,
          {
            token: token,
            user: email,
            newPassword: password,
            newPasswordConfirmed: confirmedPassword
          }
        ).pipe(catchError(this.handleError))
      }


/**
 * Genera Code para reset
 */      
  requestResetCode(email: string,website:string) : Observable<any> {

    let URL:string;
    if( website === parameters.WEB ) URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.RESET_REQUEST_ENDPOINT}`;
    if( website === parameters.APP ) URL = `${PATH_USER.DOMAIN}/${PATH_USER.RESET_REQUEST_ENDPOINT}`;
    return this.http.post(
    `${URL}`,
      {
        user: email.toLocaleLowerCase()
      }
    ).pipe(catchError(this.handleError))
  }


       /**
     * Obtener Error
     * @param error 
     */
      handleError(error: any) {
        return Observable.throw(error || 'Server error');
     }

}
