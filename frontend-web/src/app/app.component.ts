import { Component } from '@angular/core';
import { Routes, Router  } from '@angular/router';
import { DataService } from './services/data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClientComponent } from './pages/client/client.component';
import { LoginComponent } from './login/login.component';
import { PagesComponent } from './pages/pages.component';
import { LoginGuard } from './services/guards/login-guard.guard';
import { MatDialog } from '@angular/material';
import { DialogSnackComponent } from './components/dialog-snack/dialog-snack.component';
import { DialogLoadingComponent } from './components/dialog-loading/dialog-loading.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'app';
  isLoading = false;
  isLogged = false;
  dialogRef:any;

  showLoading:boolean=false;

  constructor(private _dataService: DataService, public dialog: MatDialog, private router: Router){
    
    this.isLogged = this._dataService.isLogged;

    this._dataService
        .getIsLoadingEvent()
        .subscribe(isLoad => {         
          //this.isLoading = isLoad;

          if(isLoad)
          {
            this.openLoading();

          }else{
            this.closeLoading();
            //this.dialogRef.close();
          }
          });

    this._dataService
        .getIsLogged()
        .subscribe(isLogged => this.isLogged = isLogged);

    this._dataService
        .getGeneralNotificationMessage()
        .subscribe(msg => {
          const dialogRef = this.dialog.open(DialogSnackComponent, {
            height: '50px',
            maxWidth: '60%',
            position: {
              bottom: '0px',
            },
            panelClass: 'snack-container',
            data: { message: msg },
            disableClose: true,
            hasBackdrop: false
          });
          setTimeout(() => {
            dialogRef.close();
          }, 3000);
        });
  }
  openLoading(){
    this.showLoading=true;
    /*
    this.dialogRef = this.dialog.open(DialogLoadingComponent, {
         disableClose: true,                        
         width: '200px',          
         data: {message:'Cargando Archivo'}
     });          
     */
 }
 closeLoading(){

   this.showLoading = false;

 }
}


export const routes: Routes = [
  { 
    path: '', 
    component: LoginComponent 
  },
  {
    path: 'client',
    component: ClientComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: PagesComponent
  }
];
