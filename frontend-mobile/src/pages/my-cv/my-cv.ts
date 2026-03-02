import { Component } from '@angular/core';
import { NavController, NavParams, Platform } from 'ionic-angular';
import { CreatePdfProvider } from '../../providers/create-pdf/create-pdf';
import { SafeResourceUrl } from '@angular/platform-browser';
import { HttpClient } from '../../../node_modules/@angular/common/http';
import { MycvTO } from '../../models/employee.compl';
import { UsersProvider } from '../../providers/users/users';
import { UserTO } from '../../models/user.model';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG, VARIABLES_PAGE_DISCOUNT } from '../../environments/environments';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { MessageGeneral } from '../../iterface/create-account.interface';


@Component({
  selector: 'page-my-cv',
  templateUrl: 'my-cv.html',
})
export class MyCvPage {
  pdfLink: SafeResourceUrl;
  pdfSrc: any;
  userObejct: UserTO;
  mycvUpdate: MycvTO;
  base64Save:string;
  mess = new MessageGeneral();
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public platform: Platform,
    private cretePdf: CreatePdfProvider,
    public storage: StorageProvider,
    public http: HttpClient,
    public users: UsersProvider, public loading: EventsManagerProvider) {
    this.userObejct = this.storage.getItem(KEYS_STORAGE.USER);
    this.getMycvRhtotal();
  }


  getBase64CV(phat: string, size: number, typeFile: string) {

    if ( typeFile != 'application/pdf') {
      this.loading.setIsLoadingEvent(false);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.MSG_SIZE_PDF);
      return;
    }

    if(size > VARIABLES_PAGE_DISCOUNT.TAMANO){
      this.loading.setIsLoadingEvent(false);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE,MSG_DIALOG.MSG_SIZE_PDF);
      return;
    }

    this.http.get(phat, { responseType: 'arraybuffer' })
      .subscribe((file: ArrayBuffer) => {
        this.pdfSrc = new Uint8Array(file);
      },error =>{
        this.loading.setIsLoadingEvent(false);
       console.log(error);
      },()=>{
        if(size > 0){
          this.saveMycvBase64(phat);
        }
      });
  }

  saveMycvBase64(base64: string) {
    if (this.mycvUpdate != null) {
      this.mycvUpdate.value = base64;
      this.users.saveMyCv(this.mycvUpdate).subscribe((data: MycvTO) => {
        this.cretePdf.savePdfDb(data.idMycv, data.idUser, data.nameCv, data.value, data.creationUser, data.lastUserModifier, data.lastModification, data.creationDate, data.active, this.userObejct.email);
        this.mess.title = MSG_DIALOG.OK;
        this.mess.msg = MSG_DIALOG.MSG_SAVE;
        setTimeout(() => {
          this.loading.setGeneralNotificationMessage(this.mess);
        }, 1000);
      }, error => {
        console.log(error);
        this.loading.setIsLoadingEvent(false);
      });
      return;
    }

      let mycv = new MycvTO();
      mycv.idMycv = null;
      mycv.idUser = this.userObejct.id;
      mycv.nameCv = this.userObejct.email;
      mycv.value = base64;
      mycv.creationUser = this.userObejct.email;
      mycv.lastUserModifier = this.userObejct.email;
      mycv.lastModification = null;
      mycv.creationDate = null;
      mycv.active = null;
  
      this.users.saveMyCv(mycv).subscribe((data: MycvTO) => {
        this.mycvUpdate = data;
        this.cretePdf.savePdfDb(data.idMycv, data.idUser, data.nameCv, data.value, data.creationUser, data.lastUserModifier, data.lastModification, data.creationDate, data.active, this.userObejct.email).then(() => {
        this.mess.title = MSG_DIALOG.OK;
        this.mess.msg = MSG_DIALOG.MSG_SAVE;
        setTimeout(() => {
          this.loading.setGeneralNotificationMessage(this.mess);
        }, 1000);      }).catch(() => {
        console.error('error al guardar');
        this.loading.setIsLoadingEvent(false);
      });
    
    }, error => {
      console.log(error);
      this.loading.setIsLoadingEvent(false);
    });
  
  }

  getMycvRhtotal() {
    this.users.getMyCv(this.userObejct.email).subscribe((data: MycvTO) => {
      this.loading.setIsLoadingEvent(true);
      this.mycvUpdate = data;
      this.cretePdf.savePdfDb(data.idMycv, data.idUser, data.nameCv, data.value, data.creationUser, data.lastUserModifier, data.lastModification, data.creationDate, data.active, this.userObejct.email).then(() => {
        this.getBase64CV(this.mycvUpdate.value, 0, 'application/pdf');
      }).catch(() => {
        this.loading.setIsLoadingEvent(false);
      });
    }, error => {
      console.log(error);
      this.users.showConfirmAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.MSG_SIN_RESULTADOS);
    });
  }

  openFilePdf() {
    if(this.platform.is('ios')){
      this.loading.setIsLoadingEvent(true);
      this.users.getFileIOS().then(data => {
        console.log('data', JSON.stringify(data));
        let fileData = data.fileData.replace('data:image/png;base64', 'data:image/*;charset=utf-8;base64');
        console.log('fileData', JSON.stringify(fileData));
        this.getBase64CV(fileData, data.fileSize, data.fileType);
      })
      .catch(error => {
        console.log('error', JSON.stringify(error));
        this.loading.setIsLoadingEvent(false);
      });
    }else {
      this.loading.setIsLoadingEvent(true);
      this.users.getFileInfo().then(data => {
          this.getBase64CV(data.fileData, data.fileSize, data.fileType);
      }).catch(error=>{
        console.log(error);
       this.loading.setIsLoadingEvent(false);
      });
    }
  }

  callBackFn(event:any){
    setTimeout(() => {
      this.loading.setIsLoadingEvent(false);
    }, 3000);
  }

}
