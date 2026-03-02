import { Router } from '@angular/router'; 

import { routesWeb } from '../../environments/environment';
import { GenericTableService } from '../services/generic-table/generic-table.service';

export class SelectItemTable {
    
    constructor( private routeNavigate:Router, private _genericTable:GenericTableService ){}

    selectItem( section:string,item:any ){
        this._genericTable.setItem( this.option[section].getId(item) );
        this.navigateAdmin( this.option[section].phat );
    }

    navigateAdmin( section ) {
        this.routeNavigate.navigate([`${routesWeb.HOME}/${section}`]);
    }

    option = {
        descuentos:{
            getId: ( item ) => {
                return item.idDiscount;
            },
            phat:routesWeb.ADMIN_DISCOUNT
        },
        banners:{
            getId: ( item ) => {
                return item.idBanner;
            },
            phat:routesWeb.ADMIN_BANNERS
        }
    }

}