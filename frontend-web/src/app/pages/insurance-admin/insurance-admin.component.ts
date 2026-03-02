import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB, MSG } from '../../../environments/environment';
import { FormGroup, FormBuilder } from '@angular/forms';
import { InsuranceService } from '../../services/insurance/insurance.service';
import { InsuranceTableTO } from '../../models/insurance-table.model';
import { TreeService } from '../../services/tree/tree.service';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { Subscriber } from 'rxjs';
import { HEADERS, environment, TABLE_ROUTE, routesWeb } from '../../../environments/environment.prod';
import { log } from 'util';
import { PlanCoverageComponent } from './plan-coverage/plan-coverage.component';
import { MatDialog } from '@angular/material';
import { DialogEventualitiesComponent } from './dialog-eventualities/dialog-eventualities.component';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { DataService } from '../../services/data.service';
import { InsuranceParametersTO } from '../../models/insurance-parameters';

@Component({
  selector: 'app-insurance-admin',
  templateUrl: './insurance-admin.component.html',
  styleUrls: ['./insurance-admin.component.css','../../../assets/custom.css']
})
export class InsuranceAdminComponent implements OnInit {

  formInsurance:FormGroup;

  jsonTreeNotification: any = {};

  opcionInsurance: boolean=false;

  insuranceVehicle: boolean=false;;
  insuranceLife: boolean=false;
  insuranceMedical: boolean=false;
  disabledTree: boolean =false;
  optionInsuranceType:string;
  idTypeInsuranceAdd:string;
  
   tableData: any = {};

  insurances: any[] = [
    { value: '1', typeInsurance: 'Seguro vehicular' },
    { value: '2', typeInsurance: 'Seguro de vida' },
    { value: '3', typeInsurance: 'Seguro gastos médicos mayores' },
    { value: '4', typeInsurance: 'Seguro gastos médicos menores' }
  ];

  insuranceTO = new InsuranceTableTO();
  insuranceTableTO = new InsuranceTableTO();

  showSaveUpdate: boolean = true;

  showTable:boolean=true;
  disableTree:boolean;

  titleEventualidad:string;
  showAddEventualy:boolean=false;

  headerTable:string;
  pageTable:string;
  idInsuranceTable:string;
  typeEventualyTable:number;

  countEventualyCoverage:number;
  


  constructor( private _toolbar: ToolbarFabService,
               private _breadcrumb: BreadcrumbService,
               private fb: FormBuilder,
               private router: Router,
               private insuranceService:InsuranceService,
               private _treeService: TreeService,
               public dialog: MatDialog,
               private _localS: LocalStorageService,
               private _dataService: DataService) {

    this.showSaveUpdate = this._localS.getRolUserRead() == environment.ROL_USER_READ ? false : true;                
    this._dataService.setIsLoadingEvent(true);
    
    this._toolbar.setVisible( this.router.url.toString() );
    
    this.insuranceTO = this.insuranceService.getInsuranceSelected();

    this.insuranceTableTO = this.insuranceService.getInsuranceSelected();
    
    if (this.insuranceTO){
        this._breadcrumb.setRouteText({ title: BREADCRUMB.DETAIL_INSURANCE, arrow: true });
        this.formVisible(this.insuranceTO.idInsurance);
        this.disableTree = true;
    }else {
        this._breadcrumb.setRouteText({ title: BREADCRUMB.NEW_INSURANCE, arrow: true });
        this.opcionInsurance = true;
        this.showTable = false;
        this.disableTree = false;
        this._dataService.setIsLoadingEvent(false);
    }
    this.getTree();


  }

  ngOnInit() {
  }

  getLoadEventualyCoverage(page = 0){            
      this.loadEnvetualyCoverage(this.headerTable, page.toString(), this.idInsuranceTable, this.typeEventualyTable);
  }

  loadEnvetualyCoverage(header:string, page:string, idInsurance:string, typeEventualy:number,msgSaveUpdate = ''){
      let URL:string;

      this.insuranceService.getInfoTableInsurance(header, page, idInsurance, typeEventualy)
                                  .subscribe( (resp:any) => {                                      
                                        this.tableData = resp.infoTable;
                                        
                                        this.tableData.infoData.map( (resp:any) => {
                                            resp.status = resp.status === 'A' ? 'Activo' :
                                                (resp.status === 'I' ? 'Inactivo' : '');    
                                                
                                            return resp
                                        });

                                      if (typeEventualy.toString() == environment.TYPE_INSURE_V || typeEventualy.toString() == environment.TYPE_INSURE_SV) {
                                            URL = TABLE_ROUTE.SHOW_COUNT_EVENTUALY;
                                            this.insuranceService.getCountEventualy(URL, idInsurance).subscribe( (countEventualy:any) => {
                                                this.countEventualyCoverage = countEventualy.filas;     
                                                this._dataService.setIsLoadingEvent(false);
                                                if(msgSaveUpdate !== ''){
                                                    this._dataService.setGeneralNotificationMessage(msgSaveUpdate);
                                                }   
                                            },
                                            error => {                                                
                                                this._dataService.setIsLoadingEvent(false);
                                            });
                                      } else if (typeEventualy.toString() == environment.TYPE_INSURE_GMA) {
                                            URL = TABLE_ROUTE.SHOW_COUNT_COVERAGE;
                                            this.insuranceService.getCountCoverage(URL, idInsurance).subscribe( (countCoverage:any) => {
                                                this.countEventualyCoverage = countCoverage.filas;
                                                this._dataService.setIsLoadingEvent(false);
                                                if (msgSaveUpdate !== '') {
                                                    this._dataService.setGeneralNotificationMessage(msgSaveUpdate);
                                                } 
                                            },
                                            error => {
                                                this._dataService.setIsLoadingEvent(false);
                                            });
                                      }
                                                                              
                                  },
                                  error => {
                                      this._dataService.setIsLoadingEvent(false);
                                  });
  }

    setInsuranceSave(insuranceModelTO: InsuranceParametersTO){
      
      this.insuranceService.saveUpdateInsurance(insuranceModelTO.insuranceTable, this.jsonTreeNotification).subscribe((resp: InsuranceTableTO) => {          
          
          if (!this.insuranceTO){
              this.insuranceService.setShowStatus(true);
          }        
          this.mainRedirection(resp.idTypeInsurance.toString());  
          this._dataService.setGeneralNotificationMessage(insuranceModelTO.confirmationMessage);
          this.insuranceService.setEnabledForm(true);
          this.showAddEventualy = false;
          this.insuranceTO = resp;
          this.insuranceTableTO = resp; 
          this.disableTree = true;       
          
          this.mainRedirection(resp.idTypeInsurance.toString());
          this._dataService.setIsLoadingEvent(false);
      }, (error:any) => { 
          this._dataService.setIsLoadingEvent(false);                  
          this._dataService.setGeneralNotificationMessage(error.error.message);
      });  
    
  }

  selectItemInsuranceEventualy(eventualy:any){
        if (this.showSaveUpdate){
            console.log('EVENTUALIDA ', eventualy);

            if (this.insuranceService.getIdInsuranceType() == environment.TYPE_INSURE_V
                || this.insuranceService.getIdInsuranceType() == environment.TYPE_INSURE_SV) {

                const dialogRef = this.dialog.open(DialogEventualitiesComponent, {
                    width: '500px',
                    data: { eventualy: eventualy, title: 'Eventualidad Amparada' }
                });

                dialogRef.afterClosed().subscribe(resp => {
                    console.log("idInsurance ", resp.idInsurance);
                    this.setParametersTableEventualy(HEADERS.EVENTUALY_HEADER, '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()));
                    this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER,
                        '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance,
                        Number(this.insuranceService.getIdInsuranceType()), resp.msgEventualy);
                });

            } else if (this.insuranceService.getIdInsuranceType() == environment.TYPE_INSURE_GMA) {

                const dialogRef = this.dialog.open(PlanCoverageComponent, {
                    width: '500px',
                    height: '700px',
                    data: { eventualy: eventualy, title: 'Cobertura plan' }
                });

                dialogRef.afterClosed().subscribe(resp => {
                    console.log("idInsurance ", resp.idInsurance);
                    this.setParametersTableEventualy(HEADERS.COVERAGE_HEADER, '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()));
                    this.loadEnvetualyCoverage(HEADERS.COVERAGE_HEADER,
                        '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance,
                        Number(this.insuranceService.getIdInsuranceType()), resp.msgEventualy);
                });
            }
        //this.insuranceService.setIdInsuranceType('0');
        }        
    }
 

  formVisible(idInsurance:string){
      this.insuranceService.selectOneInsurance(idInsurance).subscribe((resp: InsuranceTableTO) => {
          this.idTypeInsuranceAdd = resp.idTypeInsurance.toString();
          this.insuranceTableTO = resp;
          console.log("INFORMACION SEGUROS ", this.insuranceTableTO);

          this._treeService.getAllTree(this.insuranceTableTO.idInsurance.toString(), 'I')
              .subscribe(treeJson => {
                  this.jsonTreeNotification = treeJson;
              });

          if (resp.idTypeInsurance == 1) {              
              this.insuranceVehicle = true;
              this.titleEventualidad = MSG.TITLE_EVENTUALIDAD;   
              this.setParametersTableEventualy(HEADERS.EVENTUALY_HEADER, '0', this.insuranceTableTO.idInsurance, Number(environment.TYPE_INSURE_V));        
              this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER, '0', this.insuranceTableTO.idInsurance, 1);
              this.insuranceService.setIdInsuranceTypeSelected(environment.TYPE_INSURE_V);
              this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_V);
              
          } else if (resp.idTypeInsurance == 2) {
              this.insuranceLife = true;              
              this.titleEventualidad = MSG.TITLE_EVENTUALIDAD;
              this.setParametersTableEventualy(HEADERS.EVENTUALY_HEADER, '0', this.insuranceTableTO.idInsurance, Number(environment.TYPE_INSURE_SV));
              this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER, '0', this.insuranceTableTO.idInsurance, 2);            
              this.insuranceService.setIdInsuranceTypeSelected(environment.TYPE_INSURE_SV);
              this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_SV);
              
          } else if (resp.idTypeInsurance == 3) {
              this.insuranceMedical = true;              
              this.titleEventualidad = MSG.TITLE_COBERTURA;
              this.setParametersTableEventualy(HEADERS.EVENTUALY_HEADER, '0', this.insuranceTableTO.idInsurance, Number(environment.TYPE_INSURE_GMA));
              this.loadEnvetualyCoverage(HEADERS.COVERAGE_HEADER, '0', this.insuranceTableTO.idInsurance, 3);
              this.insuranceService.setIdInsuranceTypeSelected(environment.TYPE_INSURE_GMA);
              this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_GMA);
              
          } else if (resp.idTypeInsurance == 4) {
              this.insuranceMedical = true;
              this.showTable = false;
              this.insuranceService.setIdInsuranceTypeSelected(environment.TYPE_INSURE_GME);   
              this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_GME);  
              this._dataService.setIsLoadingEvent(false);            
          }
          //this.showTable = resp.idTypeInsurance == 1 || resp.idTypeInsurance == 2 || resp.idTypeInsurance == 3 ? true :
            //               (resp.idTypeInsurance == 4 ? false : null);
          this.insuranceService.setInsuranceSelected(null);
      });   
      
  }

  showTypeInsurance(){
      console.log("TIPO SELECCIONADO ", this.optionInsuranceType);
      this._dataService.setIsLoadingEvent(true);
      this.idTypeInsuranceAdd = this.optionInsuranceType;
      this.insuranceService.setIdInsuranceTypeSelected(this.optionInsuranceType);
      this.opcionInsurance = false;
      this.showAddEventualy = true;
      if (this.optionInsuranceType == environment.TYPE_INSURE_V) {
          this.insuranceVehicle = true;  
          this.titleEventualidad = MSG.TITLE_EVENTUALIDAD;
          this.showTable = true;
          this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_V);
          this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER, '0', environment.DEFAULT_VALUE_EVENTUALIDAD, 1);                 
          this.getTree();          
      } else if (this.optionInsuranceType == environment.TYPE_INSURE_SV) {
          this.insuranceLife = true;  
          this.titleEventualidad = MSG.TITLE_EVENTUALIDAD;
          this.showTable = true;
          this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_SV); 
          this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER, '0', environment.DEFAULT_VALUE_EVENTUALIDAD, 2);       
          this.getTree();
          
      } else if (this.optionInsuranceType == environment.TYPE_INSURE_GMA) {
          this.insuranceMedical = true;          
          this.titleEventualidad = MSG.TITLE_COBERTURA;
          this.showTable = true;
          this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_GMA);
          this.loadEnvetualyCoverage(HEADERS.COVERAGE_HEADER, '0', environment.DEFAULT_VALUE_EVENTUALIDAD, 3);
          this.getTree();
          
      } else if (this.optionInsuranceType == environment.TYPE_INSURE_GME) {
          this.insuranceMedical = true;
          this.showTable = false;
          this.insuranceService.setIdInsuranceType(environment.TYPE_INSURE_GME);
          this.getTree();         

      }      
  }

  getTree(){
      this._treeService.getAllTree('0', 'I')
          .subscribe(treeJson => {
              this.jsonTreeNotification = treeJson;
              this._dataService.setIsLoadingEvent(false);
          },
          error => {
              this._dataService.setIsLoadingEvent(false);
          });
  }


  addEventualy() {
         
      if (this.idTypeInsuranceAdd == environment.TYPE_INSURE_V || this.idTypeInsuranceAdd == environment.TYPE_INSURE_SV){
          const dialogRef = this.dialog.open(DialogEventualitiesComponent, {
              width: '500px',
              data: { eventualy: null, title: 'Eventualidad Amparada', idInsurance: this.insuranceTO.idInsurance }
          });

          dialogRef.afterClosed().subscribe(resup => {
              console.log("idInsurance ", resup.idInsurance);   
              this.setParametersTableEventualy(HEADERS.EVENTUALY_HEADER, '0', resup.idInsurance ? resup.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()));
              this.loadEnvetualyCoverage(HEADERS.EVENTUALY_HEADER,
                  '0', resup.idInsurance ? resup.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()), resup.msgEventualy);
          });
      }else if (this.idTypeInsuranceAdd == environment.TYPE_INSURE_GMA){
          const dialogRef = this.dialog.open(PlanCoverageComponent, {
              width: '500px',
              data: { eventualy: null, title: 'Cobertura plan', idInsurance: this.insuranceTO.idInsurance }
          });

          dialogRef.afterClosed().subscribe(resp => {
              console.log("idInsurance ", resp.idInsurance);
              this.setParametersTableEventualy(HEADERS.COVERAGE_HEADER, '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()));
              this.loadEnvetualyCoverage(HEADERS.COVERAGE_HEADER,
                  '0', resp.idInsurance ? resp.idInsurance : this.insuranceTableTO.idInsurance, Number(this.insuranceService.getIdInsuranceType()), resp.msgEventualy);
          });
       }
  }

  setEnableTree(enableTree){
      this.disableTree = enableTree;
  }
  setParametersTableEventualy(header: string, page: string, idInsurance: string, typeEventualy: number){
        this.headerTable = header;
        this.pageTable = page;
        this.idInsuranceTable = idInsurance;
        this.typeEventualyTable = typeEventualy;
  }

  mainRedirection(idTypeEventualy:string){
        if (idTypeEventualy === environment.TYPE_INSURE_GME){

            setTimeout(() => {
                this.router.navigate([`${routesWeb.HOME}/${routesWeb.INSURANCE}`]);
            }, 3900);                
            return;
        }
  }

}
