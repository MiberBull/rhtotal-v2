import { Injectable } from '@angular/core';
import { DiscountTO, BenefitsDiscountTreeTO } from '../../models/discount.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_APPLICATION } from '../../../environments/environment';
import { CategoryTO, SubcategoryTO } from '../../models/category_sub.model';

@Injectable({
  providedIn: 'root'
})
export class DiscountService {

  private discount: DiscountTO;

  constructor( private http: HttpClient) { }

  /**
   * Crear nuevo descuento
   * @param discount 
   */
  newOrUpdateDiscount( discount: BenefitsDiscountTreeTO ){
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/saveOrUpdateDiscount`;
    return this.http.post( URL,discount );
  }

  /**
   * Consulta por id para descuento
   */
  queryDiscountById( discount:DiscountTO ){
    let params = new HttpParams()
                     .set( 'id', discount.idDiscount.toString() );
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/getDiscount`;
    return this.http.get( URL,{params} );
  }

  /**
   * Retornar las categorias
   */
  getCategories(){
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/getCategory`;
    return this.http.get( URL );
  }

  /**
   * Retornar las subcategorias
   */
  getSubcategories( idCategory = 0 ) {
    let params = new HttpParams()
                     .set( 'idCategory', idCategory.toString() );
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/getSubCategory`;
    return this.http.get( URL,{ params } );
  }

  newCategory( category:CategoryTO ) {
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/saveCategory`
    return this.http.post( URL,category );
  }

  newSubcategory( subcategory:SubcategoryTO ) {
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/saveSubCategory`
    return this.http.post( URL,subcategory );
  }

  /**
   * 
   */
  getCountItems( filters = {} ){
    let URL = `${PATH_APPLICATION.DOMAIN}/discount/getNumberRow`;
    let params = new HttpParams()
                .set('supplier',filters['proveedor'] == null ? '':filters['proveedor'])
                .set('autor',filters['autor'] == null ? '' : filters['autor'])
                .set('startDate',filters['fechainicio'] == null ? '' : filters['fechainicio'])
                .set('enddate',filters['fechafin'] == null ? '' : filters['fechafin']);
    return this.http.get( URL,{params} );
  }

  /**
   * Retorna un objeto de descuento
   */
  getDiscount() {
    return this.discount;
  }

  /**
   * Coloca un objeto de tipo discount
   * @param discount 
   */
  setDiscount( discount: DiscountTO ) {
    this.discount = discount;
  }

  

}
