import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';

import { environment } from '../../../environments/environment';

@Injectable({
    providedIn:'root'
})

export class LocalStorageService{
    
    constructor(){ }
    
    /**
     * Agregar un elemento al LocalStorage
     * @param key 
     * @param value 
     */
    addItem(key:string,value:any){
        return localStorage.setItem( key, JSON.stringify(value));
    }

    /**
     * Obtener un elemento del LocalStorage 
     * por su llave y un elemento en especifico
     * @param key 
     * @param value 
     */
    getItem(key:string,value:string){
        let localItem = JSON.parse( localStorage.getItem(key) );
        return of(localItem[value]);
    }

    /**
     * Obtener un todo el elemento del LocalStorage
     * @param key 
     */
    getAllItems(key:string){
        let localItem = JSON.parse( localStorage.getItem(key) );
        return localItem;
    }

    /**
     * Obtener el menu que puede visualizar el usuario
     */
    getMenu(){
        let menu = JSON.parse( localStorage.getItem(environment.ANSWER_LOGIN) );
        return JSON.parse( menu.user.idRol.permission );
    }
    
    /**
     * Obtiene el token y la informacion que 
     * viene dentro del token
     */
    getToken(){
        let token = JSON.parse( localStorage.getItem(environment.ANSWER_LOGIN) ).token;
        let payload = JSON.parse( atob(token.split(environment.POINT)[environment.SLICE]));
        return {token,payload};
    }

    getUser(){
        return  JSON.parse( localStorage.getItem(environment.ANSWER_LOGIN) ).user.email;
    }

    getUserLogin(){
        return JSON.parse(localStorage.getItem(environment.ANSWER_LOGIN)).user.idRol.idRol;
    }

    getRolUserRead(){
        return JSON.parse(localStorage.getItem(environment.ANSWER_LOGIN)).user.idRol.idRol;
    }

    /**
     * Retorna el header con el token
     */
    getHttpHeaders(){
        let httOptions = {
            headers: new HttpHeaders({
                Authorization: `Bearer<${this.getToken().token}>` 
            })
        }
        return of(httOptions);
    }

    getVarLocalStorage(roota){
        let nameVarJSON:any={};
        let nameVar = '';
        nameVarJSON = RUTAS.filter( url => {
             return url.root == roota;    
        });

        if (!(nameVarJSON[0])){
            nameVar = null;
        }else {
            nameVar = nameVarJSON[0].name;
        }
                
        return nameVar;  
    }


}

export const RUTAS = [
    {"name":"bannerLocal","root":"/home/banners"},
    {"name":"notificationLocal","root":"/home/notificaciones"},
    {"name":"discountLocal","root":"/home/descuentos"},
    {"name":"insuranceLocal","root":"/home/seguros"},
    {"name":"clientLocal","root":"/home/clientes"},
    {"name":"rolesLocal","root":"/home/roles"},
    {"name":"userLocal","root":"/home/usuarios"}    
  ];