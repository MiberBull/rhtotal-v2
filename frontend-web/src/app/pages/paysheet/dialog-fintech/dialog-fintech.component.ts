import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { environment, routesWeb } from '../../../../environments/environment.prod';
import { PaysheetService } from '../../../services/paysheet/paysheet.service';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { DataService } from '../../../services/data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog-fintech',
  templateUrl: './dialog-fintech.component.html',
  styleUrls: ['./dialog-fintech.component.css']
})
export class DialogFintechComponent implements OnInit {

  idFintech:number;
  valueNotification:string;
  enabledComment:boolean=false;
  enabledNotification:boolean=true;
  reasonRejection:string;
  typeFintech:string;
  status:boolean;

  typeFintechMessage:string;

  constructor(private _paySheetService: PaysheetService,
    private localStorageService: LocalStorageService,
    private _dataS: DataService,
    private router: Router,
    public dialogRef: MatDialogRef<DialogFintechComponent>,
    @Inject(MAT_DIALOG_DATA) public data:any) {
         this.valueNotification=data.notification;
         this.idFintech = data.idFintech;
         this.typeFintech = data.typeFintech;
         this.typeFintechMessage = this.typeFintech == 'MyAdvance' ? 'Mi adelanto' : this.typeFintech;
         this.status = data.status;
    console.log("USER ", this.localStorageService.getUser());
  }

  ngOnInit() {
  }

  updateComment(){
    this.typeFintech == environment.TYPE_FINTECH_ADVANCE ? this.updateFintechAdvance() :
      (this.typeFintech == environment.TYPE_FINTECH_VELOCASH ? this.updateFintechVeloCash() : '');

  }

  updateStatusFintech(){    
    
    if (this.valueNotification == 'Rechazar'){
        this.enabledComment = true;
        this.enabledNotification = false;
        this.status = false;
    } else if (this.valueNotification == 'Aprobar'){
      
        this.typeFintech == environment.TYPE_FINTECH_ADVANCE ? this.updateFintechAdvance() :
          (this.typeFintech == environment.TYPE_FINTECH_VELOCASH ? this.updateFintechVeloCash() : '');  
 
        this.status = true;
     }
  }

  updateFintechAdvance(){
        this._dataS.setIsLoadingEvent(true);
        console.log("USER ", this.localStorageService.getUser());
        let messageFintech:string = '';
        let statusFintech:string='';
        if (this.status){
          statusFintech='AP';
          messageFintech = 'Aprobada';
        }else{
          statusFintech = 'R';
          messageFintech = 'Rechazada';
        }
        
        this.dialogRef.close(); 

        let lastModification = new Date().toISOString();
        this._paySheetService.updateStatusFintechAdvance(this.idFintech, statusFintech, 
                                                        this.localStorageService.getUser(),lastModification, 
                                                        statusFintech == 'R' ? this.reasonRejection : '')
                                              .subscribe( (x:any) => {

                                                          
                                                    console.log('RESPUESTA DE FINTECH ESTADO MY ADVANCE',x);
                                                    
                                                    this._dataS.setIsLoadingEvent(false);

                                                    if (x.messageError == 'Ocurrio un error'){
                                                       this._dataS.setGeneralNotificationMessage(`${x.messageError}`);                                                    
                                                    }else {
                                                       this._dataS.setGeneralNotificationMessage(`La solicitud fue ${x.message}`);
                                                    }                                                       

                                                    setTimeout(() => {
                                                      this.router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_ADVANCE}`]); 
                                                    }, 3900);
    
                                              }, error => {   
                                                    console.log('ERRORR  ', error);                                                                                                   
                                                    this._dataS.setIsLoadingEvent(false);
                                                    this.dialogRef.close();
                                                    this._dataS.setGeneralNotificationMessage(`Ocurrio un error`);                                              
                                                    
                                                    setTimeout(() => {
                                                       this.router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_ADVANCE}`]);
                                                    }, 3900);                                                    

                                              });      
 
  }

  updateFintechVeloCash(){
         this._dataS.setIsLoadingEvent(true);
        let messageFintech: string = '';
        let statusFintech: string = '';
        if (this.status) {
          statusFintech = 'AP';
          messageFintech = 'Aprobada';
        } else {
          statusFintech = 'R';
          messageFintech = 'Rechazada';
        }

        this.dialogRef.close();
        console.log("::: VELOCASH :::")

        let lastModification = new Date().toISOString();
        this._paySheetService.updateVelocashStatus(statusFintech, this.localStorageService.getUser(), this.idFintech, this.reasonRejection)
                                      .subscribe((x:any) => {
                                            console.log('RESPUESTA DE FINTECH ESTADO VELOCASH', x);  
 
                                            this._dataS.setIsLoadingEvent(false);
                                            
                                            if (x.messageError == 'Ocurrio un error') {
                                              x.messageError = "Ocurrio un error";
                                              this._dataS.setGeneralNotificationMessage(`${x.messageError}`);
                                            } else {
                                              this._dataS.setGeneralNotificationMessage(`La solicitud fue ${x.message}`);
                                            }
                                            
                                            this._dataS.setGeneralNotificationMessage(`La solicitud fue procesada`);
                                            


                                            setTimeout(() => {
                                              this.router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_VELOCASH}`]);
                                              console.log("Ruta Fintech Velocash:", `${routesWeb.HOME}/${routesWeb.FINTECH_VELOCASH}`);
                                            }, 3900);                                             

                                            
                                       }, error => {
                                            console.log('ERRORR  ', error);
                                            this._dataS.setIsLoadingEvent(false);
                                            this.dialogRef.close();
                                            this._dataS.setGeneralNotificationMessage(`Processing...`);  
                                            
                                            setTimeout(() => {
                                                this.router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_VELOCASH}`]);   
                                            }, 3900);
                                       });
  }

  closeDialog(){
       this._paySheetService.setStatusNull("ES");
       this.dialogRef.close();
  }
}
