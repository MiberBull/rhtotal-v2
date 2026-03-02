import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

/*
  Generated class for the ViewEventProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ViewEventProvider {

  menu = new Subject<string>();

  constructor(public http: HttpClient) {
    console.log('Hello ViewEventProvider Provider');
  }

  menuOpen( side:string ) {
    this.menu.next( side );
  }

  menuEvent() {
    return this.menu.asObservable();
  }

}
