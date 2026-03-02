import { Injectable } from '@angular/core';
import { FileTransfer, FileTransferObject 
} from '@ionic-native/file-transfer';
import { File } from '@ionic-native/file';
import { AndroidPermissions } from '@ionic-native/android-permissions';
import { DialogsProvider } from '../dialogs/dialogs';
import { MSG_DIALOG } from '../../environments/environments';
import { EventsManagerProvider } from '../events-manager/events-manager';
import { MessageGeneral } from '../../iterface/create-account.interface';
import { Platform ,AlertController} from 'ionic-angular';
import { DocumentViewer, DocumentViewerOptions } from '@ionic-native/document-viewer';
import { FileOpener } from '@ionic-native/file-opener';


@Injectable()
export class FileTransferProvider {

  count:number = 0;
  fileTransfer: FileTransferObject;
  message: MessageGeneral = new MessageGeneral();
   
  counterFiles: number = 0;
  files:any[] = [];

  constructor(
    private transfer: FileTransfer, 
    private file: File,
    private androidPermissions: AndroidPermissions,
    private dialog_provider: DialogsProvider,
    private events_manager: EventsManagerProvider,
    private platform: Platform,
    private document: DocumentViewer,
    private alertCtrl: AlertController,
    private fileOpener: FileOpener) {

      this.message.title = MSG_DIALOG.OK;
      this.message.msg = MSG_DIALOG.MSG_SAVE;
      this.fileTransfer = this.transfer.create();

      this.counterFiles = 0;
      this.files = [];
  }

  download( file:any[] ) {
    this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.WRITE_EXTERNAL_STORAGE).then(
      result => {
        if( result.hasPermission ) {
          if(this.platform.is('ios')){
            this.counterFiles = 0;
            this.files = file;
            this.openFile();
          }else{
            file.forEach( element => {
              this.downloadConfirmFile( element,file.length );
            });
          }
        }else {
          this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.WRITE_EXTERNAL_STORAGE).then( resolve => {
            if(this.platform.is('ios')){
              this.counterFiles = 0;
              this.files = file;
              this.openFile();
            }else{
              file.forEach( element => {
                this.downloadConfirmFile( element,file.length );
              });
            }
          }).catch( error => {
          });
        }
    },err => {
        this.events_manager.setIsLoadingEvent(false);        
        console.log(JSON.stringify(err));
      }
    );
  }

  openFile(){
    if(this.files.length > this.counterFiles){
      console.log('this.counterFiles', this.counterFiles);
      console.log('this.files.length', this.files.length);
      this.downloadConfirmFile( this.files[this.counterFiles], this.counterFiles ).then(() => {
        this.counterFiles++;
        this.openFile();
      });
    }else {
      this.events_manager.setIsLoadingEvent(false);
      //this.events_manager.setGeneralNotificationMessage( this.message );
      this.showALert(MSG_DIALOG.ERROR_TITLE,`${this.message.msg}.`);
    }
  }

  downloadConfirmFile(file,size?): Promise<any>{

    return new Promise((resolve, reject) => {
      let nameFile = `${file.periodo}.pdf`;
      let route = this.platform.is('ios') ? 
                `${this.file.documentsDirectory}/Documents${nameFile}` :
                `${this.file.externalRootDirectory}/Download/${nameFile}`;
      this.fileTransfer.download(encodeURI(file.urlPdf), route ).then((entry) => {
        console.log('entry', JSON.stringify(entry));
  
        if(this.platform.is('ios')){
          const options: DocumentViewerOptions = {
            title: nameFile,
            openWith: {enabled: true},
            print: {enabled: true}
          }
          this.document.viewDocument(entry.nativeURL, 'application/pdf', options, () => {}, () => {
              resolve();
          });
        } else if(this.platform.is('android')){ // Se agrego metodo para android
          this.fileOpener.open(entry.toURL(), 'application/pdf')
                  .then(() => console.log('File is opened'))
                  .catch(e => console.log('Error opening file', e));
        }
  
        this.count += 1;
        if( this.count == size ) {
          this.count = 0; 
          this.events_manager.setIsLoadingEvent(false);
          //this.events_manager.setGeneralNotificationMessage( this.message );
          this.showALert(MSG_DIALOG.ERROR_TITLE,`${this.message.msg}.`);
          resolve();
        }
      }, (error) => {
        this.count += 1;
        if( error.code == 1) {
          this.events_manager.setIsLoadingEvent(false);
          this.dialog_provider.viewDialog(MSG_DIALOG.ERROR_TITLE,`La aplicación no tiene permisos para guardar en la memoria del teléfono.`);
        }
        if( this.count == size ) {
          this.count = 0; 
          this.events_manager.setIsLoadingEvent(false);
          //this.events_manager.setGeneralNotificationMessage( this.message );
          this.showALert(MSG_DIALOG.ERROR_TITLE,`${this.message.msg}.`);
        }
        console.log( 'download error:', JSON.stringify( error ) );
        reject(error);
      });
    });
    
  }

  showALert(title,subTitle) {
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
