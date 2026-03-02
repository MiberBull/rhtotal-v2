import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BREADCRUMB } from '../../../environments/environment';
import { Router } from '@angular/router';
import { routesWeb } from '../../../environments/environment.prod';
import { PaysheetService } from '../../services/paysheet/paysheet.service';

@Component({
  selector: 'app-paysheet',
  templateUrl: './paysheet.component.html',
  styleUrls: ['./paysheet.component.css']
})
export class PaysheetComponent implements OnInit {

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private router:Router,
    private _paysheetService: PaysheetService) {
      
      this._breadcrumb.setRouteText({title:BREADCRUMB.PAYSHEET,items:BREADCRUMB.PAYSHEET_ITEMS,arrow:false});

      let rootSelected = this._paysheetService.getRootSelected();
      '/home/adelantos/my-advance'
      this._toolbar.setVisible( this.router.url.toString() );
      this.router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_VELOCASH}`]);

      if (rootSelected != ''){
        this.router.navigate([rootSelected]);
      } 
  }

  ngOnInit() {
  }

  test() {
    this.router.navigate(['/home/adelantos/my-advance'])
  }

}
