import { Injectable } from '@angular/core';
import { KEYS_STORAGE, CLEAN_LOCAL_STORAGE } from '../../environments/environments';

@Injectable()
export class StorageProvider {

  constructor() {
  }

  saveItem( key:string,value:any ) {
    localStorage.setItem( key,JSON.stringify( value ) )
  }

  getItem( key:string ) {
    if( localStorage.getItem( key ) ){
      return JSON.parse( localStorage.getItem( key ) );
    }
  }

  saveOnboarding() {
    let viewOnboarding = { view:true };
    localStorage.setItem( "viewOnboarding",JSON.stringify(viewOnboarding) );
  }

  firtsView() {
    return new Promise( (resolve,reject) => {
      if( localStorage.getItem("viewOnboarding") ) {
        let visit = JSON.parse(localStorage.getItem("viewOnboarding"));
        if( visit.view ){
          return resolve(true);
        }else{
          return resolve(false);
        }
      }else {
        return resolve(false);
      }
    });
  }

  clearStorage() {
    localStorage.clear();
  }

  getUser() {
    try {
      let user = JSON.parse(localStorage.getItem( KEYS_STORAGE.USER ));
      user.idUser = user.id;
      delete user.id;
      return user;
    } catch {
    } 
  }

  clearCustomStorage() {
    try {
      let KEY = CLEAN_LOCAL_STORAGE;
      KEY.forEach( item => {
        localStorage.removeItem(item);
      });
    } catch (error) {
      console.log(JSON.stringify(error));
    }
  }

}
