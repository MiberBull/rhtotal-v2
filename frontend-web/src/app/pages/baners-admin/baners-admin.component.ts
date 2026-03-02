import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2, DebugElement } from '@angular/core';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BannerService } from '../../services/banner/banner.service';
import { BannerTO } from '../../models/banner.model';
import { Location } from '@angular/common'

import { TreeService } from '../../services/tree/tree.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { BREADCRUMB, environment, EXPRESSION } from '../../../environments/environment';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { Information } from '../../util/date';
import { ImageTO } from '../../models/image.model';
import { routesWeb, MSG } from '../../../environments/environment.prod';
import { Router, Data } from '@angular/router';
import { DataService } from '../../services/data.service';
import { MatDialogConfig, MatDialog, MatSnackBar } from '@angular/material';
import { DialogConfirmComponent } from '../../components/dialog-confirm/dialog-confirm.component';
import { DialogLoadingComponent } from '../../components/dialog-loading/dialog-loading.component';

const dialogConfig = new MatDialogConfig();

@Component({
  selector: 'app-baners-admin',
  templateUrl: './baners-admin.component.html',
  styleUrls: ['./baners-admin.component.css','../../../assets/custom.css']
})
export class BanersAdminComponent implements OnInit,OnDestroy {

  @ViewChild('inputFile_1') inputFile_1: ElementRef<any>;
  @ViewChild('inputFile_2') inputFile_2: ElementRef<any>;
  @ViewChild('inputFile_3') inputFile_3: ElementRef<any>;
  @ViewChild('inputFile_4') inputFile_4: ElementRef<any>;
  @ViewChild('inputFile_5') inputFile_5: ElementRef<any>;
  @ViewChild('inputFile_6') inputFile_6: ElementRef<any>;
  image:any;
  base64textString:string[] = []
  nameImages:string[] = [];
  countImages:number[] = [];

  imagesBanner:ImageTO[] = [];
  positionImage:number = 0;
  count:number = 1;

  banner:BannerTO;
  jsonTreeBanner:any={};
  bannersFrom: FormGroup;
  confirm:boolean = false;
  status = [
    { id:'A',value:'Activo' },
    { id:'I',value:'Inactivo' },
  ];

  inputRequired:boolean = false;
  disableTree:boolean;

  editBandera:boolean = true;
  dateCurrent = new Date();
  dateNext:Date;
  dateEndString:string;
  dialogRefLoading:any;

  numImage:number;

  showSaveUpdate: boolean = true;

  constructor(
    private _toolbar: ToolbarFabService,
    private _bannerService: BannerService,
    private _localS: LocalStorageService,
    private _dataService: DataService,
    private location: Location,
    private dialog:MatDialog,
    private router: Router,
    private _breadcrumb: BreadcrumbService,
    private fb:FormBuilder,
    private render:Renderer2,
    private _treeService:TreeService,
    public snackBar: MatSnackBar ) {

    this.showSaveUpdate = this._localS.getRolUserRead() == environment.ROL_USER_READ ? false : true;

    this._dataService.setIsLoadingEvent(true);

    this.bannersFrom = this.fb.group({
      title:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      startDate:['',[Validators.required]],
      endDate:['',[Validators.required]],
      timePublication:[''],
      notificationTime:[''],
      notificationDetail:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      internalComments:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      status:[''],
      creationUser:[''],
      lastUserModifier:['']
    });

    this._toolbar.setVisible( this.router.url.toString() );
    this.banner = this._bannerService.getBanner();
    let idBanner = this.banner ? this.banner.idBanner : '0';
    
    this._treeService.getAllTree(`${idBanner}`,'B').subscribe( treeJson => {
      this.jsonTreeBanner = treeJson;
    },error => this._dataService.setIsLoadingEvent(false),()=>this._dataService.setIsLoadingEvent(false)
    );

  }

  ngOnInit() {

    if ( this.banner ) {
      this.bannersFrom.disable();
      this._breadcrumb.setRouteText({title:`${BREADCRUMB.DETAIL_BANNER}`,arrow:true});
      this._bannerService
          .queryBannerById( this.banner.idBanner )
          .subscribe( (resp:any) => {
            this.setValuesInForm(resp.bannerTO,resp.images);
          });
    } else {
      this._breadcrumb.setRouteText({title:BREADCRUMB.NEW_BANNER,arrow:true});
      this.editBandera= false;
    }

  }

  setValuesInForm( banner:BannerTO,images:any[] ) {
    this.disableTree = true;
    this.banner = banner;
    this.count = images.length;
    this.imagesBanner = images;

    this.nameImages = images.map(item => item.nameImage);

    this.imagesBanner.forEach(image => {
      this.nameImages[Number(image.typeImage)] = image.nameImage;
      this.base64textString[Number(image.typeImage)] = image.base64;
    });

    this.bannersFrom.patchValue( banner );
  }

  onSubmit() {

        let infoForm = this.bannersFrom.getRawValue();

        if( Information.validateCurrentHour( infoForm.startDate,infoForm.timePublication ) ) {
          this._dataService.setGeneralNotificationMessage( 'La hora de publicación no es válida ' );
          return;
        }

        if( Information.validateCurrentHour( infoForm.startDate,infoForm.notificationTime ) ) {
          this._dataService.setGeneralNotificationMessage( 'La hora de notificación no es válida' );
          return;
        }

        if( infoForm.endDate ) {
          this.inputRequired = true;
        }

        if( this.banner ) {
          if (this.banner.status == infoForm.status ) {
            this.update(infoForm);
            return;
          }
          let label = infoForm.status == 'A' ? 'activar' : 'inactivar';
          dialogConfig.data = {title:`¿Está seguro de ${label} el Banner?`,success:'UPDATE'};
            dialogConfig.width = '250px;';
            let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
            dialogRef.afterClosed().subscribe( (resp:any) => {
              if( resp == 'UPDATE' ){
                this.update( infoForm,true );
              }
            });
        }else {
          this.save( infoForm );
        }
  }

  enableForm() {
    this.disableTree = false;
    this.bannersFrom.enable();
    this.bannersFrom.get('lastUserModifier').disable();
  }


  save( infoForm ){
    this._dataService.setIsLoadingEvent(true);
    let banner = new BannerTO();
    let imagesTO:ImageTO[] = [];
    this.numImage=0;
    this.nameImages.forEach( (item,index) => {
      let img1 = new ImageTO();
      img1.base64 = this.base64textString[index];
      img1.nameImage = this.nameImages[index];
      img1.idBanner = null;
      img1.typeImage = index.toString();
      img1.lastUserModiffier = this._localS.getUser();
      img1.lastModification = Information.getCurrentDate();
      img1.creationUser = this._localS.getUser();
      img1.creationDate = Information.getCurrentDate();
      img1.active = true;
      imagesTO.push( img1 );
      this.numImage += 1;
    });

    banner.title = infoForm.title;
    banner.startDate = infoForm.startDate;
    banner.endDate = infoForm.endDate;
    banner.timePublication = infoForm.timePublication;
    banner.notificationTime = infoForm.notificationTime;
    banner.notificationDetail = infoForm.notificationDetail;
    banner.internalComments = infoForm.internalComments;
    banner.status = 'A';
    banner.lastModification = Information.getCurrentDate();
    banner.lastUserModifier = this._localS.getUser();
    banner.creationDate = Information.getCurrentDate();
    banner.creationUser = this._localS.getUser();
    banner.active = true;

    if(this.validate()){
      this._bannerService.newBanner( banner,imagesTO,this.jsonTreeBanner )
      .subscribe( () => {
       this._dataService.setIsLoadingEvent(false);
       this._dataService.setGeneralNotificationMessage(MSG.OK);

        setTimeout(() => {
          this.router.navigate([`${routesWeb.HOME}/${routesWeb.BANNERS}`]);
        }, 3900);
        
       
        
      }, error => {
        this._dataService.setIsLoadingEvent(false);
        this._dataService.setGeneralNotificationMessage( error.message );
      },()=> this._dataService.setIsLoadingEvent(false)

      );
    }
  }

  validate():boolean{
    let isValid=true;
    if(this.numImage < 1){
        isValid = isValid && false;
        this.snackBar.open(MSG.IMAGE_EXIST, 'OK', {
          duration: 8000,
        });
    }else {
      isValid = isValid && true;
    }
    isValid = isValid && this.validateFormatImages();
    isValid = isValid && this.validateDate();
    return isValid;
  }

  validateFormatImages():boolean{
     let acumulador:number=0;
     let isValidImages:boolean=true;
     this.nameImages.forEach( (item,index) => {
        if(!(/\.(jpg|png|gif)$/i).test(this.nameImages[index])){
          acumulador += 1;
        }
     });
     isValidImages = acumulador>1 ? false : true;
     acumulador > 1 ? this._dataService.setGeneralNotificationMessage(MSG.IMAGE_FORMAT_VALIT) : '';
     return isValidImages;
  }

  validateDate():boolean{
    let banderaDate:boolean=true;
    let values = this.bannersFrom.getRawValue();
    if( !Information.validateTwoDate( values.startDate,values.endDate ) ){
         this._dataService.setGeneralNotificationMessage(MSG.ERROR_TWO_DATE);
         banderaDate = false;
    }
    return banderaDate;
  }

  message(msj){
    this.snackBar.open(msj, 'OK', {
      duration: 8000,
    });
  }

  update( infoForm,changeStatus = false ) {


    this._dataService.setIsLoadingEvent(true);
    this.banner.lastUserModifier = this._localS.getUser();
    this.banner.lastModification = Information.getCurrentDate();
    this.banner.notificationDetail = infoForm.notificationDetail;
    this.banner.title = infoForm.title;
    this.banner.startDate = infoForm.startDate;
    this.banner.endDate = infoForm.endDate;
    this.banner.timePublication = infoForm.timePublication;
    this.banner.notificationTime = infoForm.notificationTime;
    this.banner.notificationDetail = infoForm.notificationDetail;
    this.banner.internalComments = infoForm.internalComments;
    this.banner.status = infoForm.status;
    this._bannerService.updateBanner( this.banner,this.imagesBanner,this.jsonTreeBanner )
                       .subscribe( () => {
                        let msg = changeStatus ? 'Se ha modificado el estatus del Banner' : MSG.OK;
                        this._dataService.setIsLoadingEvent(false);
                        this._dataService.setGeneralNotificationMessage(msg);
                         
                         setTimeout(() => {
                           this.router.navigate([`${routesWeb.HOME}/${routesWeb.BANNERS}`]);  
                         }, 3900);                                               
                       }, error => {
                        this._dataService.setIsLoadingEvent(false);
                        this._dataService.setGeneralNotificationMessage(error.error.message);
                       },()=>this._dataService.setIsLoadingEvent(false) );

  }

  cancelForm() {
    this.location.back();
  }

  getImageUpdate(files:File){

    if(!(/\.(jpg|png)$/i).test(files.name)){
      this._dataService.setGeneralNotificationMessage(MSG.IMAGE_FORMAT_VALIT);
    }else{
      if (files) {

        let img1:any = {};

        if(!this.imagesBanner[this.positionImage]){
            img1.base64 = this.base64textString[this.positionImage];
            img1.nameImage = this.nameImages[this.positionImage];
            img1.idBanner = this.banner;
            img1.typeImage = this.positionImage.toString();
            img1.lastUserModiffier = this._localS.getUser();
            img1.lastModification = Information.getCurrentDate();
            img1.creationUser = this._localS.getUser();
            img1.creationDate = Information.getCurrentDate();
            img1.active = true;
            this.imagesBanner[this.positionImage] = img1;
        }else if(this.imagesBanner[this.positionImage].idBanner != null){
            this.imagesBanner[this.positionImage].base64 = null;
            this.imagesBanner[this.positionImage].base64 = this.base64textString[this.positionImage];
            this.imagesBanner[this.positionImage].nameImage = this.nameImages[this.positionImage];
            this.imagesBanner[this.positionImage].lastUserModiffier = this._localS.getUser();
            this.imagesBanner[this.positionImage].lastModification = Information.getCurrentDate();
        }else if(this.imagesBanner[this.positionImage].idBanner == null ){
            img1.base64 = this.base64textString[this.positionImage];
            img1.nameImage = this.nameImages[this.positionImage];
            img1.idBanner = this.banner;
            img1.typeImage = this.positionImage.toString();
            img1.lastUserModiffier = this._localS.getUser();
            img1.lastModification = Information.getCurrentDate();
            img1.creationUser = this._localS.getUser();
            img1.creationDate = Information.getCurrentDate();
            img1.active = true;
            this.imagesBanner[this.positionImage] = img1;
        }
        
      }
    }
  }

  getImage(file:File,item:number) {

    this.positionImage = item;

    if( !file ) {
      return;
    }
    console.log('NOMBRE DEL ARCHIVO ', file.name.split('.'));
    
    if(!(/\.(jpg|png)$/i).test(file.name)){
      this._dataService.setGeneralNotificationMessage(MSG.IMAGE_FORMAT_VALIT);
      return;
    }else{
        if (file) {
          this.nameImages[item] = file.name;
          const reader = new FileReader();
          reader.onload = this.handleReaderLoaded.bind(this);
          reader.readAsBinaryString(file);
        }

        setTimeout(() => {
          if (this.banner) {
            this.getImageUpdate(file);
          }
        }, 1000);
    }
    

      
  }

  handleReaderLoaded(e) {
    this.base64textString[this.positionImage] = ('data:image/png;base64,' + btoa(e.target.result));
  }

  showFileChosser() {
    this.render.selectRootElement( this.inputFile_1.nativeElement ).click();
  }
  showFileChosser_2() {
    this.render.selectRootElement( this.inputFile_2.nativeElement ).click();
  }
  showFileChosser_3() {
    this.render.selectRootElement( this.inputFile_3.nativeElement ).click();
  }
  showFileChosser_4() {
    this.render.selectRootElement( this.inputFile_4.nativeElement ).click();
  }
  showFileChosser_5() {
    this.render.selectRootElement( this.inputFile_5.nativeElement ).click();
  }
  showFileChosser_6() {
    this.render.selectRootElement( this.inputFile_6.nativeElement ).click();
  }

  addIputImage() {
    if((this.nameImages.length+1) > this.count ) {
      this.count += 1;
    }
  }

  validateHours() {

  }

  ngOnDestroy() {
    this._bannerService.setBanner( null );
  }

}
