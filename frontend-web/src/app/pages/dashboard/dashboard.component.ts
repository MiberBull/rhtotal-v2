import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor( private _breadcrumb: BreadcrumbService,
               private _toolbar: ToolbarFabService,
             private _router:Router, ) {
    this._breadcrumb.setRouteText({title:null,arrow:false});
    this._toolbar.setVisible( this._router.url.toString() );
   }

  ngOnInit() {
  }



}
