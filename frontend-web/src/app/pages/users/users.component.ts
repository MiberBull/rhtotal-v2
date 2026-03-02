import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import {params} from '../../models/parameters.model';
import {tabAndIcon} from '../../models/tapsUser'
import { EmployeeComplementaryTO, EmployeeTO } from '../../models/employee.model'
import { Subscription } from 'rxjs';
import { BREADCRUMB, routesWeb, HEADERS, PATH_USER } from '../../../environments/environment';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { environment } from '../../../environments/environment.prod';
import {UserService} from '../../services/user/user.service';
import { DataService } from '../../services/data.service';




@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})

export class UsersComponent implements OnInit,OnDestroy {

  public tableData:any = {};
  public filtros:any;
  public objetParams: Array<params> = new Array<params>();
  private filtroCurp:params=new params();
  private filtroClient:params=new params();
  private filtroProject: params=new params();
  private eventObserver:Subscription;
  private eventoFiltrado:Subscription;
  private bandera:boolean=true;

  JsonLocalStorage:any={};

  showSaveUpdate: boolean = true;

  countUsers:number;

  cliente:string;
  curp:string;
  correoelectronico:string;
  proyecto:string;

  removable:boolean = true;

  constructor(
    private _toolbar: ToolbarFabService,
    private _dialogFilterService:DialogFormFilterService,
    private _breadcrumb:BreadcrumbService,
    private _localStorageService:LocalStorageService,
    private _router: Router,
    private _table: GenericTableService,
    private _userSerice:UserService,
    private _dataService: DataService,) {

      this._userSerice.setEmployee({"idUser":"0","userType":"EX"})
    this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbar.setRolUserRead(this.showSaveUpdate);

    this.filtroCurp.name="curp";
    this.filtroClient.name='client';
    this.filtroProject.name='project';
    this.filtroCurp.value="";
    this.filtroClient.value='';
    this.filtroProject.value='';
    this.objetParams.push(this.filtroCurp);
    this.objetParams.push(this.filtroClient);
    this.objetParams.push(this.filtroProject);
    this._table.setParams(this.objetParams);
    this._userSerice.setBaseStatus(true);
    this._breadcrumb.setRouteText({title:BREADCRUMB.USER,arrow:false});
    this.eventObserver = this._toolbar.getAddEvent().subscribe( () => this.goAdminUser() );
    this._toolbar.setVisible( this._router.url.toString() );
    
    let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
    this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
    
    this._table.getColumnsGroups().subscribe( columns => this.changeColumnsUser(columns) );
    

    if(this.JsonLocalStorage == null){

        this._table.getTableHeadersJSON( HEADERS.USERS )
            .subscribe( headers => {
                let arrayHeader:any=[];
                this._table.setHeadersActive( headers );
                this._table.setHeadersInactive( arrayHeader );
                this.loadInfoUsers();
        });
    }else{
        this._table.setHeadersActive( this.JsonLocalStorage.actives );
        this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
        this.loadInfoUsers();
    }

    
    this.eventoFiltrado = this._dialogFilterService.getFiltersDialog().subscribe( (filtros:any) => {
      this.filtros = filtros;     
      
      this.curp = filtros.curp == "" || filtros.curp == null ? null : filtros.curp;
      this.cliente = filtros.cliente == "" || filtros.cliente == null ? null : filtros.cliente;
      this.correoelectronico = filtros.correoelectronico == "" || filtros.correoelectronico == null ? null : filtros.correoelectronico;
      this.proyecto = filtros.proyecto == "" || filtros.proyecto == null ? null : filtros.proyecto;
      
      console.log('FILTROS USUARIOS ', this.filtros);
       
      this._table.setParams(this.filtros);

      this.bandera = false;
      this.loadInfoUsers();
     
    });

  }

  ngOnInit() {
  }

  goAdminUser(){
    this._router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_USER}`]);
  }

  loadInfoUsers( page = 0 ) {

    if(this.bandera){
      this._table.setParamsUsersNull();
    }
    this._dataService.setIsLoadingEvent(true);
    this._table.getInfoTable( `${PATH_USER.DOMAIN}/${PATH_USER.USER_GET_EMPLOYEE_BY_CURP}`,HEADERS.USERS,page.toString())
               .subscribe( (resp:any) => {
                this._dataService.setIsLoadingEvent(false);
                 this.tableData=resp.infoTable;
                                  
                 if(this.tableData.infoData!=null)
                 {
                   this.tableData.infoData=this.tableData.infoData.map(function(x){               
                    x.gender = (x.gender=='M'?'Masculino':'Femenino');
                    x.qtSalary="$"+x.qtSalary;
                    x.allocationSalary="$"+ x.allocationSalary;
                    x.sueldo_bruto_mensual=  x.sueldo_bruto_mensual==null?"$ 0":"$ "+ x.sueldo_bruto_mensual.toString();
                    x.cantidad_Bono_Mensual  =  x.cantidad_Bono_Mensual==null?"$ 0":"$ "+ x.cantidad_Bono_Mensual.toString();              
                    x.cantidad_Bono_Bimestral =  x.cantidad_Bono_Bimestral==null?"$ 0":"$ "+ x.cantidad_Bono_Bimestral.toString();               
                    x.cantidad_Bono_Trimestral      =  x.cantidad_Bono_Trimestral==null?"$ 0":"$ "+ x.cantidad_Bono_Trimestral.toString();          
                    x.cantidad_Bono_Anual    =  x.cantidad_Bono_Anual==null?"$ 0":"$ "+ x.cantidad_Bono_Anual.toString();            
                    x.cantidad_Fondo_de_Ahorro  =  x.cantidad_Fondo_de_Ahorro==null?"$ 0":"$ "+ x.cantidad_Fondo_de_Ahorro.toString();              
                    x.cantidad_Vales_de_Despensa =  x.cantidad_Vales_de_Despensa==null?"$ 0":"$ "+ x.cantidad_Vales_de_Despensa.toString();               
                    x.cantidad_Vales_Restaurante  =  x.cantidad_Vales_Restaurante==null?"$ 0":"$ "+ x.cantidad_Vales_Restaurante.toString() ;             
                    x.cantidad_Vales_Gasolina   =  x.cantidad_Vales_Gasolina==null?"$ 0":"$ "+ x.cantidad_Vales_Gasolina.toString();             
                    x.ultimo_monto_recibido   =  x.ultimo_monto_recibido==null?"$ 0":"$ "+ x.ultimo_monto_recibido.toString();             
                    x.ingreso_mensual_bruto_integrado    =  x.ingreso_mensual_bruto_integrado==null?"$ 0":"$ "+ x.ingreso_mensual_bruto_integrado.toString();            
                    x.ingreso_anual_bruto_estimado=  x.ingreso_anual_bruto_estimado==null?"$ 0":"$ "+ x.ingreso_anual_bruto_estimado.toString();
                                       return x;});
                 }
  
                 this.changeColumnsUser(this.JsonLocalStorage);

                 this._userSerice.getCountRow(this.filtros).subscribe( (count:any) => {
                   this.countUsers = count.filas;
                 });
                           },
                           error => {
                            this._dataService.setIsLoadingEvent(false);
                             console.log( 'contiene error',error.error );

                          });
  }

  selectItemUser( item ){
    this._userSerice.setIdEmployee(item.idEmployee);
    this._userSerice.setEmailUser(item.email);
    this._userSerice.setEmployee(item);
    this._userSerice.setParamSico(item.name,item.lastName,item.birthDate,item.curp);  
    this._userSerice.setBaseStatus(false);
    this._router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_USER}`]);
    


  }

  changeColumnsUser( columns:any ) {
    if(columns==null)
    {
      let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
      columns = JSON.parse(localStorage.getItem(nameStorage));
    }
    if(columns){
      this.tableData.titles  = columns.actives.map(x => x.title);
      this.tableData.headers = columns.actives.map(x => x.id);
      this._table.setHeadersActive( columns.actives );
      this._table.setHeadersInactive( columns.inactives );
    }
  }
      

  remove(filter:any){
      this.filtros[filter] = null;
      if (filter == 'curp') this.curp = null; 
      if (filter == 'cliente') this.cliente = null;
      if (filter == 'correoelectronico') this.correoelectronico = null;
      if (filter == 'proyecto') this.proyecto = null;

      this._table.setParams(this.filtros);

      this.loadInfoUsers();
  }

  ngOnDestroy() {

    this.eventoFiltrado.unsubscribe();

  }

  
 
}
