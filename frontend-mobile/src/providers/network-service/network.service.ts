import { Injectable } from '@angular/core';
import { AlertController} from 'ionic-angular';
import { Subscription } from 'rxjs/Subscription';
import { Network } from '@ionic-native/network';
import { Toast } from '@ionic-native/toast';

export enum ConnectionStatusEnum {
    Online,
    Offline
}

@Injectable()
export class NetworkService {

    public connected: Subscription;
    public disconnected: Subscription;
    private change: Subscription;

    public previousStatus: any;
    public connectionType: any;
    
    private isConnected: Boolean;
    private intermittentRank;

    constructor( public network: Network, private alertCtrl: AlertController, private toast: Toast ) {
        this.previousStatus = ConnectionStatusEnum.Online;
        this.isConnected = true;
        this.intermittentRank = 0;
    }

    /**
     * @function startNetworkService
     * 
     **/
     startNetworkService(){
         try{
             this.connected = this.network.onConnect().subscribe(data => {                
                 if(this.previousStatus === ConnectionStatusEnum.Offline){
                     console.log('Connected');
                     this.toast.showWithOptions(
                     {
                         message: 'Conexión Reestablecida: ' + this.network.type,
                         position: 'top',
                         duration: 2000,
                         styling: {backgroundColor:'#68a581', textColor:'#ffffff'}
                     }).subscribe(toast => {});
                     
                 }
                 this.isConnected = true;
                 this.previousStatus = ConnectionStatusEnum.Online;
                 this.connectionType = this.network.type;               
             }, error => console.error('Error connect: ',error));

             this.disconnected = this.network.onDisconnect().subscribe(data => {
                 if(this.previousStatus === ConnectionStatusEnum.Online){
                     console.log('Disconnected');

                     this.toast.showWithOptions(
                     {
                         message: 'Conexión Perdida.',
                         position: 'top',
                         duration: 2000,
                         styling: {backgroundColor:'#e43d39', textColor:'#ffffff'}
                     }).subscribe(toast => {});
                     
                 }
                 this.isConnected = false;
                 this.previousStatus = ConnectionStatusEnum.Offline;
             }, error => console.error('Error disconnect: ',error));

             this.change = this.network.onchange().subscribe( data => {
                 console.log('Change connection detected');

                 if(this.intermittentRank >= 0 && this.intermittentRank < 5){
                     this.intermittentRank++;
                 } else if(this.intermittentRank === 5){
                     this.intermittentRank = 0;
                     this.toast.showWithOptions(
                     {
                         message: 'Conexión Intermitente.',
                         position: 'top',
                         duration: 2000,
                         styling: {backgroundColor:'#ffc107', textColor:'#ffffff'}
                     }).subscribe(toast => {});
                 }

                 //#ffc107
             }, error => console.error('Error change: ', error));

         } catch(e){
             console.log('ERROR TRY START', e);
             this.showALert('Error', e);
         }

     }

     stopNetworkService(){
         try {
             this.connected.unsubscribe();
             this.disconnected.unsubscribe();
             this.change.unsubscribe();
         } catch(e){
             console.log('ERROR TRY STOP', e);
         }

     }

     getIsConnected(){
         return this.isConnected;
     }

     private showALert(title,subTitle) {
         let alert = this.alertCtrl.create({
             title,
             subTitle,
             buttons:[
             {
                 text: 'ACEPTAR',
                 handler: () => {
                     alert.dismiss();
                 }
             }],
             cssClass:'alertonfirmCustomCss'
         });
         alert.present();
     }
 }