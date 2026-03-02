import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MATERIAL_COMPONENTS } from '../app.material';
import { ChartsModule } from 'ng2-charts';
import { FileUploadModule } from 'ng2-file-upload';

import { GenericTableComponent } from './generic-table/generic-table.component';
import { TablePaginatorComponent } from './generic-table/table-paginator/table-paginator.component';
import { InfoTableComponent } from './generic-table/info-table/info-table.component';
import { PieGraphicComponent } from './pie-graphic/pie-graphic.component';
import { BarGraphicComponent } from './bar-graphic/bar-graphic.component';
import { DialogNewItemComponent } from './dialog-new-item/dialog-new-item.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { DialogFormsFilterComponent } from './dialog-forms-filter/dialog-forms-filter.component';
import { TreeComponent } from './tree/tree.component';
import { DialogConfirmComponent } from './dialog-confirm/dialog-confirm.component';
import { TreeClientComponent } from './tree/tree-client/tree-client.component';
import { TreeProyectComponent } from './tree/tree-proyect/tree-proyect.component';
import { TreeEmployeeComponent } from './tree/tree-employee/tree-employee.component';
import { PipeJsonPipe } from '../pipe-json.pipe';
import { DialogLoadingComponent } from './dialog-loading/dialog-loading.component';
import { DialogSnackComponent } from './dialog-snack/dialog-snack.component';
import { PipeTree } from '../pipes/pipe-tree';



@NgModule({
    declarations:[
        GenericTableComponent,
        TablePaginatorComponent,
        InfoTableComponent,
        PieGraphicComponent,
        BarGraphicComponent,
        DialogNewItemComponent,
        UploadFileComponent,
        DialogConfirmComponent,
        DialogFormsFilterComponent,
        TreeComponent,
        TreeClientComponent,
        TreeProyectComponent,
        TreeEmployeeComponent,
        PipeJsonPipe,
        DialogLoadingComponent,
        DialogSnackComponent,
        PipeTree
        
             
    ],
    exports:[
        GenericTableComponent,
        TablePaginatorComponent,
        InfoTableComponent,
        PieGraphicComponent,
        BarGraphicComponent,
        DialogNewItemComponent,
        TreeComponent,        
        DialogConfirmComponent,
        DialogSnackComponent        
    ],
    entryComponents:[ 
        DialogNewItemComponent,
        UploadFileComponent,
        DialogConfirmComponent,
        DialogSnackComponent,
        DialogFormsFilterComponent,
        DialogLoadingComponent        
    ],
    imports:[
        MATERIAL_COMPONENTS,
        BrowserAnimationsModule,
        ChartsModule,
        FormsModule,
        FileUploadModule,
        ReactiveFormsModule
    ]
})

export class ComponentModule {}