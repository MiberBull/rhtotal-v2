import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Information } from '../../util/date';


@Injectable({
    providedIn:'root'
})

export class DownloadService {

    constructor( private http: HttpClient ){}

    downloadExcel(URL,section = 0){
        let params = new HttpParams()
                     .set('section',section.toString());
        return this.http.get( URL,{params} );
    }

    getFileExcel(ruta:string){
       let date = new Date();
       let fecha:string = Information.getDateString(date);

       return ruta === '/home/roles' ? `Roles_${fecha}.xlsx` :
              (ruta === '/home/notificaciones' ? `Notificaciones_${fecha}.xlsx`:
              (ruta === '/home/descuentos' ? `Beneficios y Descuentos_${fecha}.xlsx`:
              (ruta === '/home/banners' ? `Banners_${fecha}.xlsx`:
              (ruta === '/home/clientes' ? `Clientes_${fecha}.xlsx`:
              (ruta === '/home/seguros' ? `Seguros_${fecha}.xlsx` :
              (ruta === '/home/usuarios' ? `Usuarios_${fecha}.xlsx` : ''))))));
    }

}
