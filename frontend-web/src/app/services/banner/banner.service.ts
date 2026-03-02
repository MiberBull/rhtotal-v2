import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BannerTO } from '../../models/banner.model';
import { PATH_APPLICATION } from '../../../environments/environment';
import { ImageTO } from '../../models/image.model';

@Injectable({
  providedIn: 'root'
})
export class BannerService {

  private banner:BannerTO;

  constructor( private http:HttpClient ) { }

  /**
   * Recive un objeto de tipo para
   * despues crearlo
   * @param banner
   */
  newBanner( banner:BannerTO,images:ImageTO[],jsonTree? ){
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.NEW_BANNER}`
    let obj = {
      images:images,
      notificationTO:banner,
      benefitsNotificationsTO:jsonTree
    };
    return this.http.post( URL,obj );
  }

  /**
   * Consulta un banner por id
   * @param id
   */
  queryBannerById( id:any ) {
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_BANNER}`;
    let params = new HttpParams()
                 .set( 'id', id );
    return this.http.get( URL,{ params } );
  }

  /**
   * Actualiza un banner
   * @param banner
   */
  updateBanner( banner:BannerTO,images:ImageTO[],jsonTree? ){
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.NEW_BANNER}`
    let obj = {
      images:images,
      notificationTO:banner,
      benefitsNotificationsTO:jsonTree
    };
    return this.http.post( URL,obj );
  }

  /**
   * 
   */
  getCountItems( filters = {} ){
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.COUNT_BANNER}`;
    let params = new HttpParams()
                .set('title',filters['titulo'] == null ? '':filters['titulo'])
                .set('autor',filters['autor'] == null ? '' : filters['autor'])
                .set('startDate',filters['fechainicio'] == null ? '' : filters['fechainicio'])
                .set('enddate',filters['fechafin'] == null ? '' : filters['fechafin']);
    return this.http.get( URL,{params} );
  }

  /**
   * Retorna un banner
   */
  getBanner() {
    return this.banner;
  }

  /**
   * Coloca un objeto de tipo banners
   * @param banner
   */
  setBanner( banner:BannerTO ) {
    this.banner = banner;
  }

}
