import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TreeNotificationService {

  constructor( private http: HttpClient ) {

  }

  getTree(){
    return this.http.get('/assets/data/tree.json')
                             .pipe(
                                 map ( treeJson => {
                                    let arbol = treeJson;
                                    console.log(arbol);

                                    return arbol;
                                 })
                             );
  }

}
