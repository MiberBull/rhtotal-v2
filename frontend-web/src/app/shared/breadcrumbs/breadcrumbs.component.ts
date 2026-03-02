import { OnInit,Component, OnDestroy } from '@angular/core';
import { BreadcrumbService } from './../../services/breadcrumbs/breadcrumbs.service';
import { Location } from '@angular/common';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { DialogFormsFilterComponent } from '../../components/dialog-forms-filter/dialog-forms-filter.component';
import {MatDialog} from '@angular/material';
import { Router } from '@angular/router';
import { SavedRoutesService } from '../../services/routing/saved-routes.service';
import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';
import { Subscription } from 'rxjs';
import { BREADCRUMB } from '../../../environments/environment.prod';

@Component({
    selector:'app-breadcrumbs',
    templateUrl:'./breadcrumbs.component.html',
    styleUrls:['./breadcrumbs.component.css']
})

export class BreadcrumbsComponent implements OnInit,OnDestroy{

    textTitle:string='';
    arrow:boolean = true;
    dialogFilter:Subscription;
    itemsPage:string;
    itemsPageTitles:any[]=[];
    itemMyAdvance:string;
    itemVeloCash:string;
    visible:boolean=false;
    subMenu:string='';
    urlSub:string='';

    constructor(
        private _breadcrumb: BreadcrumbService,
        private _toolbar: ToolbarFabService,
        private dialog: MatDialog,
        private _savedAppi: SavedRoutesService,
        private _dialogService:DialogFormFilterService,
        private location: Location,
        private _router:Router, ){
        this._breadcrumb
            .getRouteText()
            .subscribe( routeText => {
                this.textTitle = routeText.title;
                this.itemsPageTitles = routeText.items;
                this.arrow = routeText.arrow;

                this.visible = routeText.visible;
                this.subMenu = routeText.subMenu;
                this.urlSub = routeText.urlSub;
                
                this.itemsPage = BREADCRUMB.PAYSHEET;

                this.itemMyAdvance = BREADCRUMB.MY_ADVANCE;
                this.itemVeloCash = BREADCRUMB.VELO_CASH;
                
            });

        this._savedAppi.getSection().subscribe( (root) => {
            this._savedAppi.setSectionSelected(root);
        });

        this.dialogFilter = this._toolbar.getClickSearch().subscribe( button => this.dialogSearch(button) );
    }

    ngOnInit(){}

    dialogSearch(event){
        let filterDialog:string='';

        filterDialog = this._dialogService.getFiltro(this.textTitle);

        this._savedAppi.setAppi(filterDialog);        
        let dialogRef = this.dialog.open(DialogFormsFilterComponent, {
              disableClose: true,
              width: '500px',
              data: { filter: filterDialog }
        });
        /*
        dialogRef.afterClosed().subscribe( () => {
             console.log('Dialogo Cerrado');

        });
        */
    }

    goBack(){

     let srt=this._router.url.toString();
    if(srt.indexOf('admin-usuario') !== -1)
    {
        this._router.navigate([`home/usuarios`]);
    }
    else
    {
        this.location.back();
    }

    }

    ngOnDestroy(): void {
        this.dialogFilter.unsubscribe();
    }

}
