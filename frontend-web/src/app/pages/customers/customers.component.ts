import { Component, OnInit } from "@angular/core";
import { ToolbarFabService } from "../../services/toolbar-fab/toolbar-fab.service";
import { Router } from "@angular/router";
import { BreadcrumbService } from "./../../services/breadcrumbs/breadcrumbs.service";
import { ClientService } from '../../services/client/client.service';
import { routesWeb, environment } from "../../../environments/environment";
import { DataService } from "../../services/data.service";
import { error } from "@angular/compiler/src/util";
import { LocalStorageService } from "../../services/local-sotorage/localstorage.service";
@Component({
  selector: "app-customers",
  templateUrl: "./customers.component.html",
  styleUrls: ["./customers.component.css"]
})
export class CustomersComponent implements OnInit {
  
  titles:string[] = ['Cliente','Proyecto','Empleados','Ingreso total mensual','Estatus']
  
  infoData:any;
  panelOpenState:false;
  rowNum:number=2;

  showSaveUpdate: boolean = true;


  constructor(
    private _toolbarFab: ToolbarFabService,
    private router: Router,
    private _dataService:DataService,
    private _breadcrumb: BreadcrumbService,
    private _customer: ClientService,
    private _localStorageService: LocalStorageService) {

      this._dataService.setIsLoadingEvent(true);


    this._customer.queryPaginator().subscribe((c:any)  => this.rowNum = c.filas );

    this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbarFab.setRolUserRead(this.showSaveUpdate);

    this._customer.getInfoData().subscribe( data => {
      this.infoData = data;
      this._dataService.setIsLoadingEvent(false);

    },error=>     this._dataService.setIsLoadingEvent(false),
    ()=>     this._dataService.setIsLoadingEvent(false)
    );

    this._toolbarFab.setVisible( this.router.url.toString() );
    this._toolbarFab.getAddEvent().subscribe(() => {
      this.options();
    });
    this._breadcrumb.setRouteText({title:'Clientes',arrow:false});
    this._toolbarFab.setVisible( this.router.url.toString() );
  }

  ngOnInit() {}

  options() {
    this.router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_CUSTOMER}`]);
  }

  goToDetailsClients( item ) {
    this._customer.setIdClient( item.idCustomer );
    this.router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_CUSTOMER}`]);
  }

  loadData( page ) {
    this._customer.getInfoData(page).subscribe( data => {
      this.infoData = data;
      console.log('500');
      
    });    
  }

}
