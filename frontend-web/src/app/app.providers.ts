import { LoginMockService } from './mock-services/login.service.mock';
import { BreadcrumbService } from './services/breadcrumbs/breadcrumbs.service';
import { LocalStorageService } from './services/local-sotorage/localstorage.service';
import { LoginService } from './services/login/login.service';
import { DataService } from './services/data.service';
import { ConsumeService } from "./services/consume.service";
import { MatDialog, MAT_CHIPS_DEFAULT_OPTIONS } from '../../node_modules/@angular/material';
import { RolService } from './services/roles/rol.service';
import { ToolbarFabService } from './services/toolbar-fab/toolbar-fab.service';
import { DownloadService } from './services/download/download.service';
import { DiscountService } from './services/discount/discount.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';



import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter,  MAT_DATE_LOCALE, ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from '@angular/material/core';
import { MAT_DATE_FORMATS } from '@angular/material'; 
import { WindowScrollService } from './services/window-scroll.service';
export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput:'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};
export const APP_PROVIDERS = [
    DataService,
    ConsumeService,
    LoginService,   
    LocalStorageService,
    MatDialog,
    BreadcrumbService,
    RolService,
    ToolbarFabService,
    DownloadService,
    DiscountService,
    {
        provide: MAT_CHIPS_DEFAULT_OPTIONS,
        useValue: {
          separatorKeyCodes: [ENTER, COMMA]
        }
      },
    {   provide: DateAdapter,
         useClass: MomentDateAdapter, 
         deps: [MAT_DATE_LOCALE]
    },
    {   provide: MAT_DATE_FORMATS, 
        useValue: MY_FORMATS
    },
    WindowScrollService,
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
];

