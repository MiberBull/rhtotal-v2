import { Injectable } from '@angular/core';
import { jsonFormFilter } from '../../util/formsFilter';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DialogFormFilterService {
  formsFilter = jsonFormFilter;

  private filterEvent = new Subject<string>();
  private iconSearch = new Subject<boolean>();

  constructor() { }

  getInputs(nameFilter:string){
        
    let inputs = this.formsFilter.filter((element,index) => {
      return element.filter == nameFilter;
    });

    return inputs;
  }

  /**
   *
   *
   */
  setfiltersDialog(filters:any){
      this.filterEvent.next(filters);
  }

  getFiltersDialog(){
      return this.filterEvent.asObservable();
  }

  getFiltro(textTitle:string){
    return textTitle === 'Usuario' ? 'AdminUser' :
          (textTitle === 'Banners' ? 'AdminBanners' :
          (textTitle === 'Beneficios y Descuentos') ? 'AdminDescu' :
          (textTitle === 'Notificaciones') ? 'AdminNotificaciones' :
          (textTitle === 'Seguros') ? 'AdminSeguros' :
          (textTitle === 'Clientes') ? 'AdminCliente' : '') ;
  }

  getIconSearch(){
      return this.iconSearch.asObservable();
  }

  setIconSearch(url:string){
     let visible:boolean=true;
     if(url == '/home/roles'){
        visible = false;
     }
     this.iconSearch.next(visible);
  }

}

