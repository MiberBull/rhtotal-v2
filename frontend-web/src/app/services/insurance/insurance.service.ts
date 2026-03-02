import { Injectable } from '@angular/core';
import { InsuranceTableTO } from '../../models/insurance-table.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { TABLE_ROUTE, PATH_APPLICATION } from '../../../environments/environment.prod';
import { map, mergeMap } from 'rxjs/operators';
import { Subject, of } from 'rxjs';
import { EventualyTO } from '../../models/eventualy.model';
import { PlanCoverageTO } from '../../models/plan-coverage.model';


@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

  insuranceSelected: InsuranceTableTO;
  idInsuranceTypeSelected:string;
  idInsurance:string;
  private showStatus = new Subject<boolean>();
  private enableForm = new Subject<boolean>();

  constructor(private http: HttpClient) { }

  setInsuranceSelected(itemSelected: InsuranceTableTO){
    this.insuranceSelected = itemSelected;
  }

  getInsuranceSelected(){
    return this.insuranceSelected;
  }

  getIdInsuranceTypeSelected(){
       return this.idInsuranceTypeSelected;
  }

  setIdInsuranceTypeSelected(idInsurance:string){
     this.idInsuranceTypeSelected = idInsurance;
  }

  getIdInsuranceType(){
     return this.idInsurance;
  }
  setIdInsuranceType(idInsuren:string){
    this.idInsurance = idInsuren;
  }

  selectOneInsurance(idInsurance:string){
      let params = new HttpParams()
                          .set('insurance', idInsurance);

      let URL = TABLE_ROUTE.SELECTED_ONE_INSURANCE;

      return this.http.get(URL, { params });
  }


  getInfoTableInsurance(header: string, page: string, idInsurance:string,typeEventualy:number){
    let params = new HttpParams()
      .set('page', page)
      .set('idInsurance', idInsurance);

    let URL = '';
    if (typeEventualy == 1 || typeEventualy == 2){
        URL = TABLE_ROUTE.SHOW_ALL_EVENTUALY;   
    } else if (typeEventualy == 3){
        URL = TABLE_ROUTE.SHOW_ALL_COVERAGE;
    }
    let data = { infoTable: {} };
    return this.getTableHeaders(header)
                     .pipe(
                        mergeMap( (headers:any) => {
                            data.infoTable['headers'] = headers.value.columns;
                            data.infoTable['titles'] = headers.value.titles;
                            data.infoTable['tabs'] = headers.value.tabs;
                            
                            return this.http.get(URL, { params })
                                              .pipe(
                                                map(dataTable => {
                                                  data.infoTable['infoData'] = dataTable;
                                                  return data;
                                                })
                                              );
                        })
                     );     
    

  }

  getTableHeaders(header: string) {

    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.HEADERS}`;
  
    let params = new HttpParams()
      .set('section', header);


    return this.http.get(URL, { params })
      .pipe(
        map((resp: any) => {
          console.log(resp);

          let allHeader = JSON.parse(resp.headers);
          let tabs = allHeader.tabs;
          let columns = allHeader.headers.map(x => x.id);
          let titles = allHeader.headers.map(x => x.title);
          let allColumns = { columns, titles, tabs };
          return of(allColumns);
        })
      );
  }  

  saveUpdateInsurance(insuranceTableTO:InsuranceTableTO,treeJSON?){

      let obj = {
        insuranceTO: insuranceTableTO,
        benefitsNotificationsTO: treeJSON
      };

      let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.SAVE_UPDATE_INSURANCE}`;

      return this.http.post(URL, obj);
  }

  saveUpdateEventualy(eventualy: EventualyTO){
      
      let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.SAVE_UPDATE_EVENTUALY}`;
      
      return this.http.post(URL, eventualy);

  }

  saveUpdatePlanCoverage(coverage: PlanCoverageTO){

    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.SAVE_UPDATE_COVERAGE}`;

    return this.http.post(URL, coverage);

  }

  getOneEventualy(idInsurance:string){
    let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_ONE_EVENTUALY}`;

    let params = new HttpParams()
      .set('id', idInsurance);


    return this.http.get(URL, { params })
  }

  getOnePlanCoverage(idInsurance:string){
      let URL = `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_ONE_PLAN_COVERAGE}`;

      let params = new HttpParams()
                      .set('id', idInsurance); 

      return this.http.get(URL, { params });

  }

  getCountRecords(filters = {}){
    let URL = TABLE_ROUTE.SHOW_COUNT_INSURANCE;

    let params = new HttpParams()
                          .set('insuranceCarrier', filters['aseguradora'] == null ? '' : filters['aseguradora'].toUpperCase())
                          .set('startDate', filters['fechavigenciainiciodesde'] == null ? '' : filters['fechavigenciainiciodesde'])
                          .set('endDate', filters['fechavigenciainiciohasta'] == null ? '' : filters['fechavigenciainiciohasta'])
                          .set('author', filters['numerodepoliza'] == null ? '' : filters['numerodepoliza'].toUpperCase());

    return this.http.get(URL, {params});
  }

  getCountEventualy(URL:string,idInsurance:string){

      let params = new HttpParams()
              .set('idInsurance', idInsurance);

      return this.http.get(URL, { params });

  }

  getCountCoverage(URL: string, idInsurance: string){

      let params = new HttpParams()
                        .set('idInsurance', idInsurance);
      return this.http.get(URL, { params });                       
  }

  getShowStatus() {
    return this.showStatus.asObservable();
  }

  setShowStatus(show: boolean) {
    this.showStatus.next(show);
  }

  getEnabledForm() {
    return this.enableForm.asObservable();
  }

  setEnabledForm(show: boolean) {
    this.enableForm.next(show);
  }  


}
