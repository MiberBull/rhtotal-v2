import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';


@Injectable({
    providedIn:'root'
})

export class BreadcrumbService{

    private routeText = new Subject<any>();

    
    constructor(){}

    /**
     * Coloca el titulo para el breadcrumb
     * @param routeText 
     */
    setRouteText(routeText:any){
        this.routeText.next(routeText);
    }

    /**
     * Retorna el titulo para el breadcrumb
     */
    getRouteText(){
        return this.routeText.asObservable();
    }

}