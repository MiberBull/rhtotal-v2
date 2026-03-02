import { HeaderComponent } from '../shared/header/header.component';
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { MATERIAL_COMPONENTS } from '../app.material'
import { NgModule } from "@angular/core";
import { BreadcrumbsComponent } from './breadcrumbs/breadcrumbs.component';
import { ToolbarFabComponent } from './toolbar-fab/toolbar-fab.component';
import { SelectorColumnsComponent } from './selector-columns/selector-columns.component';
import { DragulaModule } from 'ng2-dragula';

@NgModule({
    imports:[
        RouterModule,
        MATERIAL_COMPONENTS,
        CommonModule,
        DragulaModule
    ],
    declarations:[
        HeaderComponent,
        BreadcrumbsComponent,
        ToolbarFabComponent,
        SelectorColumnsComponent
    ],
    exports:[
        HeaderComponent,
        BreadcrumbsComponent,
        ToolbarFabComponent,
        SelectorColumnsComponent
    ],entryComponents:[
        SelectorColumnsComponent
    ]
})

export class SharedModule { }