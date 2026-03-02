import { Component 
      //  ChangeDetectorRef 
      } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { RhProvider } from '../../providers/rh/rh';
import { MSG_DIALOG } from '../../environments/environments';
import { FileTransferProvider } from '../../providers/file-transfer/file-transfer';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-cfdi',
  templateUrl: 'cfdi.html',
})
export class CfdiPage {

  dateService: Date;
  fristArrayMounths:any[] = [];
  secondArrayMounths:any[] = [];
  disableSelector:boolean = true;
  count:number = 0;

  listCfdi:File[] = [];
  listCfdiView:File[] = [];
  listCfdiConfirm:any[] = [];
  startDate:string = '';
  endDate:string = '';

  months = ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'];

  endArray:any[] = [];


  constructor(
    public navCtrl: NavController, 
    public navParams: NavParams,
    private rh_provider: RhProvider,
    private events_provider: EventsManagerProvider,
    private file_provider: FileTransferProvider,
    //private ref: ChangeDetectorRef
    ) {

    this.dateService = new Date(this.navParams.get("dateTime"));
    this.generateFristArray( this.dateService );

  }

  ionViewDidLoad() {
  }

  searchCfdiMount() {

    if( this.startDate.length > 0 && this.endDate.length > 0 ) {
      this.events_provider.setIsLoadingEvent(true);
      this.listCfdiConfirm = [];
      this.listCfdiView = [];
      this.listCfdi = [];
      this.endArray = this.getEndArray();
      this.requestService();
    } 
  

  }

 //TODO:Cambiar por un merge    
 requestService() {
      this.rh_provider.getListCfdiByMount(this.endArray[this.count].idMonth,this.endArray[this.count].year.toString())
          .subscribe( (resp:any[]) => {
            resp.forEach( item => {
              this.listCfdi.push( item );
            });
            this.count += 1;
            if( this.count < this.endArray.length) {
              this.requestService();
            } else {
              if( this.listCfdi.length === 0 ) {
                let message: MessageGeneral = new MessageGeneral();
                message.title = MSG_DIALOG.ERROR_TITLE;
                message.msg = MSG_DIALOG.RESPONSE_EMPTY;
                this.events_provider.setGeneralNotificationMessage( message );
              }
              this.listCfdiView = this.listCfdi;
              this.count = 0;
              this.events_provider.setIsLoadingEvent(false);
            }
          },error => {
            this.count += 1;
            if( this.count < this.endArray.length) {
              this.requestService();
            } else {
              if( this.listCfdi.length === 0 ) {
                let message: MessageGeneral = new MessageGeneral();
                message.title = MSG_DIALOG.ERROR_TITLE;
                message.msg = MSG_DIALOG.RESPONSE_EMPTY;
                this.events_provider.setGeneralNotificationMessage( message );
              }
              this.listCfdiView = this.listCfdi;
              this.count = 0;
              this.events_provider.setIsLoadingEvent(false);
            }
          });
  }

  downloadFiles() {
    
    if( this.listCfdiConfirm.length > 0 ) {
      this.events_provider.setIsLoadingEvent(true);
      this.file_provider.download(this.listCfdiConfirm);
    }

  }

  backDiscount() {
    this.navCtrl.pop();
  }

  onChangeCheckbox(file,checked,index) {

    if( checked ) {
      this.listCfdiConfirm.push( file );
    } else {
      this.listCfdiConfirm = this.listCfdiConfirm.filter( item => item != file );
    }

  }

  selectedFirstArray($event) {
    this.listCfdiView = [];
    this.endDate = '';
    this.disableSelector = false;
    let objSelectedMonth:any = this.fristArrayMounths.find( item => item.description === $event );
    this.secondArrayMounths = this.fristArrayMounths.filter( item => item.id >= objSelectedMonth.id ).reverse();
  }

  selectedSecondtArray($event) {
    this.listCfdiView = [];
  }

  generateFristArray(date:Date) {
    for (let index = 12; index >= 0; index--) {
      let obj = {
        id:index,
        idMonth: (date.getMonth()+1).toString().length === 1 ? `0${(date.getMonth()+1).toString()}` : `${(date.getMonth()+1).toString()}`,
        year:date.getFullYear(),
        description:`${this.months[date.getMonth()]} - ${date.getFullYear()}`
      };
      this.fristArrayMounths.push(obj);
      date.setMonth( (date.getMonth() - 1 ) );
    }
  }

  getEndArray() {
    try {
      let firstPosition = this.fristArrayMounths.find( item => item.description === this.startDate );
      let endPosition = this.fristArrayMounths.find( item => item.description === this.endDate );
      return this.fristArrayMounths.filter( item => {
        if( firstPosition.id <= item.id && endPosition.id >= item.id  ){
          return item;
        } 
      });
    } catch (error) {
      console.log(error);
      this.events_provider.setIsLoadingEvent( false );
    } 
  }
}


interface File {
  periodo:string;
  urlPdf:string;
}