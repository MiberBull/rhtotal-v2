import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { DiscountService } from '../../services/discount/discount.service';
import { BREADCRUMB, routesWeb,STATUS, environment, EXPRESSION } from '../../../environments/environment';
import { DiscountTO, BenefitsDiscountTreeTO } from '../../models/discount.model';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatDialogConfig, MatRadioChange, MatDialog } from '@angular/material';
import { DialogNewItemComponent } from '../../components/dialog-new-item/dialog-new-item.component';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { TreeService } from '../../services/tree/tree.service';
import { ImageDiscountTO } from '../../models/image.model';
import { Subscription } from 'rxjs';
import { Location } from '@angular/common'
import { Router } from '@angular/router';
import { DataService } from '../../services/data.service';
import { MSG } from '../../../environments/environment';
import { CategoryTO, SubcategoryTO } from '../../models/category_sub.model';
import { DialogConfirmComponent } from '../../components/dialog-confirm/dialog-confirm.component';
import { Information } from '../../util/date';
import { ValueTransformer } from '@angular/compiler/src/util';
import { flatten } from '@angular/router/src/utils/collection';

const dialogConfig = new MatDialogConfig();

@Component({
  selector: 'app-discount-admin',
  templateUrl: './discount-admin.component.html',
  styleUrls: ['./discount-admin.component.css']
})
export class DiscountAdminComponent implements OnInit,OnDestroy {

  @ViewChild('primaryImg') primaryImg: ElementRef<any>;
  @ViewChild('secondImg') secondImg: ElementRef<any>;
  @ViewChild('thirdImg') thirdImg: ElementRef<any>;
  @ViewChild('fourImg') fourImg: ElementRef<any>;


  subscription: Subscription;
  minDate = new Date();

  base64textString:string[] = []
  nameImages:string[] = ['',''];
  positionImage:number = 0;
  discount:DiscountTO;
  imagesDiscount:ImageDiscountTO[] = []; 

  costBenefitView:boolean = false ;
  cost:boolean = null;
  checkNot:boolean = false;
  typeDiscount:string = '';
  requiredInfo:string;
  showButtonSave:boolean = true;

  foods: Food[] = [
    {value: 1, viewValue: 'Oro'},
    {value: 2, viewValue: 'Plata'},
    {value: 3, viewValue: 'Bronce'}
  ];

  formDiscount: FormGroup;
  categories: any[];
  subcategories: any[];
  disableCheks:boolean = false;
  disableCheksCost:boolean=false;
  disableBtnCategory:boolean=true;
  disableBtnSubcategory:boolean=true;
  status:any[];
  count:number = 2;
  
  jsonTreeDiscount:any;
  disabledTree:boolean;
  discountOption:number;

  banderaImageSecundary:boolean = false;

  showSaveUpdate: boolean = true;

  constructor( 
    private _toolbar: ToolbarFabService,
    private fb: FormBuilder, 
    private _localService:LocalStorageService,
    private _treeService:TreeService,
    private dialog:MatDialog,
    private render: Renderer2,
    private location: Location,
    private router:Router,
    private _dataService: DataService,
    private _discountService:DiscountService,
    private _breadcrumb: BreadcrumbService,
    private _discount: DiscountService ) {
    this._dataService.setIsLoadingEvent(true);

    this.showSaveUpdate = this._localService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbar.setRolUserRead(this.showSaveUpdate);
    
    this.status = STATUS;
    this._toolbar.setVisible( this.router.url.toString() );
   
    this._discountService.getCategories().subscribe( (category:any) => this.categories = category );
    
    this.formDiscount = this.fb.group({
      category:[''],
      subCategory:[''],
      supplier:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      title:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      startDate:[''],
      endDate:[''],
      state: [''],
      status: [''],
      description: [environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],  
      linkUrl: [environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.PAGE_URL)]],
      termsConditions: [environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      descriptionPreview: [environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      lastUserModifier: [''],
      lastModification: [''],
      creationUser: [''],
      creationDate: [''],
      active: [''],
      viewCount: [''],
      notificationTime:[''],
      notificationDetail:[environment.EMPTY_INPUT,[Validators.required,Validators.pattern(EXPRESSION.ALPHANUMERIC_CODE)]],
      publicationTime:[''],
      typeDescount:[''],
      cost:[''],
      level:['', [Validators.required]]
    });

    if( this._discount.getDiscount() ) {
       this._breadcrumb.setRouteText( {title:BREADCRUMB.DETAIL_DISCOUNT,arrow:true} );
       this.subscription = this._discount.queryDiscountById( this._discount.getDiscount() )
                  .subscribe( (resp:any) => {
                    this.formDiscount.disable();
                    this.showButtonSave =false;

                    this.discount = resp.discount;
                    this.imagesDiscount = resp.imageDiscountTO;
                    this.count=0;
                    this.disableCheks = true;
                    this.setValuesFormDiscount( resp.discount );
                  },
                  err => {
                    this._dataService.setGeneralNotificationMessage( err.error.message);
                  },
                  () => {
                    this._dataService.setIsLoadingEvent(true);
                  }
                  );
    }else{
      this._breadcrumb.setRouteText( {title:BREADCRUMB.NEW_DISCOUNT,arrow:false} );
      this.disabledTree = false;
      this._dataService.setIsLoadingEvent(false);
    }
    
    let idDiscount = this._discount.getDiscount() != null ? this._discount.getDiscount().idDiscount : '0';
    this._treeService.getAllTree( idDiscount.toString(),'D').subscribe( treeJson => {
      this._dataService.setIsLoadingEvent(true);
      this.jsonTreeDiscount = treeJson;
      this._dataService.setIsLoadingEvent(false);
      
    });     
    
   }

  ngOnInit() {
    this.formDiscount.disable();    
  }

  setValuesFormDiscount( discountValue:DiscountTO ) {
    let countImage:number = 0;
    this.disabledTree = true;
    this.discountOption =  discountValue.levelRh;
    this.formDiscount.controls['level'].setValue(discountValue.levelRh);
    this.imagesDiscount.forEach( (image,index)  => {
      this.nameImages[index] = image.nameImage;
      this.base64textString[index] = image.value;

      countImage += 1;
      this.count +=1;
      console.log(this.count);
    });

    this._discount
        .getSubcategories( discountValue.category.idCategory )
        .subscribe( (subcategories:any) => {
          this.subcategories = subcategories;
          this.typeDiscount = discountValue.typeDiscount;
          this.cost = discountValue.cost;
          let cat = discountValue.category;
          discountValue.category = cat.idCategory;
          let sub = discountValue.subCategory;
          discountValue.subCategory = sub.idSubCategory;
          this.formDiscount.patchValue( discountValue );
        });
  }

  onSubmit() {
    let values = this.formDiscount.getRawValue();

    if( !Information.validateTwoDate( values.startDate,values.endDate ) ) {
      this._dataService.setGeneralNotificationMessage(MSG.ERROR_TWO_DATE);
      return;
    }

    if ( Information.validateCurrentHour( values.startDate,values.publicationTime ) ) {
      this._dataService.setGeneralNotificationMessage('Hora de publicación no válida');
      return;
    }

    if (Information.validateCurrentHour( values.startDate,values.notificationTime ) ) {
      this._dataService.setGeneralNotificationMessage('Hora de notificación no válida');
      return;
    }

    if( !this.discount ) {
      this.saveDiscount( values );
    }else {
      this.updateDiscount( values );
    }
    
  }

  addCategory(){
    dialogConfig.disableClose = true;
    dialogConfig.data = { title:'Agregar categoría',placeholder:'Nueva Categoría' };
    let dialogRef = this.dialog.open( DialogNewItemComponent,dialogConfig );

    dialogRef.afterClosed().subscribe( (resp:string) => {

      let addNewCategory = resp;
      
      if( resp.length <= 0 ){
        return;
      }

      this._dataService.setIsLoadingEvent(true);
      this.disableBtnCategory=false;
      let category = new CategoryTO(this._localService);
      category.category = resp;
      this._discountService.newCategory( category ).subscribe( () => {        
        this._dataService.setGeneralNotificationMessage( 'Se agregó correctamente la categoría' );
        this._discountService.getCategories().subscribe( (resp:any) => {
            this.categories = resp;   
            
            this.categories.forEach( (element:any) => {
              if (element.category === addNewCategory){
                this.formDiscount.controls['category'].setValue(element.idCategory);
                this.subcategories = [];                
              }
            });                   
          
        });      

      },err => {this._dataService.setGeneralNotificationMessage( err.error.message );
        this.disableBtnCategory=true;
        this._dataService.setIsLoadingEvent(false);
      }
      ,()=> {this.disableBtnCategory=true
        this._dataService.setIsLoadingEvent(false);
      } );
    });
  }

  addSubcategory(){
    if( !this.formDiscount.get('category').value ) {
      this._dataService.setGeneralNotificationMessage( 'Debes de elegir una categoría' );
      return;
    }
    
    dialogConfig.disableClose = true;
    dialogConfig.data = { title:'Agregar subcategoría',placeholder:'Nueva Subcategoría' };
    let dialogRef = this.dialog.open( DialogNewItemComponent,dialogConfig );


    dialogRef.afterClosed().subscribe( (resp:string) => {

      let addNewSubCategory = resp;
      
      if( resp.length <= 0 ){
        return;
      }
      this._dataService.setIsLoadingEvent(true);
      this.disableBtnSubcategory=false;
      let subcategory = new SubcategoryTO(this._localService);
      subcategory.subcategory = resp;
      subcategory.category =  this.categories.find( category => this.formDiscount.get('category').value == category.idCategory );
      subcategory.idSubCategory = null;

      this._discountService.newSubcategory( subcategory ).subscribe( () => {
        this._dataService.setGeneralNotificationMessage( 'Se agregó correctamente la subcategoría' );
        this._discountService.getSubcategories( this.formDiscount.get('category').value ).subscribe( (subcategory:any) => {
          this.subcategories = subcategory; 
          console.log('ARREGLO DE SUBCATEGORIAS ', this.subcategories);          
          
          this.subcategories.forEach( (element:any) => {
            if (element.subcategory === addNewSubCategory){
               this.formDiscount.controls['subCategory'].setValue(element.idSubCategory);
             }
          });          
        });
      },err => {this._dataService.setGeneralNotificationMessage( err.error.message )
        this.disableBtnSubcategory=true;
        this._dataService.setIsLoadingEvent(false);
      }
      ,() => {this.disableBtnSubcategory=true
        this._dataService.setIsLoadingEvent(false);
      })
    });
  }

  saveDiscount( values ){
    this._dataService.setIsLoadingEvent(true);
    let discountTO:BenefitsDiscountTreeTO = new BenefitsDiscountTreeTO();
    let images:ImageDiscountTO[] = [];
    let discount:DiscountTO = new DiscountTO(this._localService);
    discount.category  = this.categories.find( category => values.category == category.idCategory );
    let sub = this.subcategories.find( subcategory => values.subCategory == subcategory.idSubCategory );
    sub.category = this.categories.find( category => values.category == category.idCategory );
    discount.subCategory = sub;
    discount.supplier = values.supplier;
    discount.title = values.title;
    discount.startDate = values.startDate != null ?  values.startDate :  '';
    discount.endDate = values.endDate != null ?  values.endDate :  '';
    discount.notificationTime = values.notificationTime != null ?  values.notificationTime :  '';;
    discount.notificationDetail = values.notificationDetail != null ?  values.notificationDetail :  '';
    discount.publicationTime = values.publicationTime != null ?  values.publicationTime :  '';
    discount.status = 'A';
    discount.description = values.description;
    discount.linkUrl = values.linkUrl;
    discount.termsConditions = values.termsConditions;
    discount.descriptionPreview = values.descriptionPreview;
    discount.viewCount = 0;
    discount.typeDiscount = this.typeDiscount;
    discount.cost = this.cost;
    discount.levelRh =  this.discountOption;

    this.nameImages.forEach( (value,index) => {
      let image:ImageDiscountTO = new ImageDiscountTO(this._localService);
      image.idImage = null;
      image.idDiscount = null;
      image.nameImage = value;
      image.value = this.base64textString[index];
      image.typeImage = index == 0 ? 'P' : 'S' ;
      if(image.value!=null)
      {
        images.push( image );
      }
    
    });

    discountTO.discount = discount;
    discountTO.images = images;
    discountTO.benefitsNotificationsTO = this.jsonTreeDiscount;

    this._discountService
        .newOrUpdateDiscount( discountTO )
        .subscribe( resp => {
          if( resp ) {
            this._dataService.setIsLoadingEvent(false);
            this._dataService.setGeneralNotificationMessage(`${MSG.OK}`);

            setTimeout(() => {
              this.router.navigate([`${routesWeb.HOME}/${routesWeb.DISCOUNT}`]);
            }, 3900);
          }
        }, (error:any) => {this._dataService.setGeneralNotificationMessage(error.message)
          this._dataService.setIsLoadingEvent(false);
        },()=> this._dataService.setIsLoadingEvent(false));
  }


  updateDiscount( values ) {
    
    if( values.status != this.discount.status ) {
      dialogConfig.disableClose = true;
      let status = values.status == 'A' ? 'Activar' : 'Inactivar';
      let type = this.typeDiscount == 'D' ? 'Descuento' : 'Beneficio';
      dialogConfig.data = { title:`¿Está seguro de ${status} el ${type}?`,success:'UPDATE' };
      let dialogRef = this.dialog.open( DialogConfirmComponent,dialogConfig );
      dialogRef.afterClosed().subscribe( (resp:string) => {
        if( resp == 'UPDATE' ){
          this.updateDiscuntConfirm( values,true );
        }
      });
    }else {
      this.updateDiscuntConfirm( values,false );
    }
        
  }

  updateDiscuntConfirm( values, updateStatus:boolean ) {
    this._dataService.setIsLoadingEvent(true);
    let discountTO:BenefitsDiscountTreeTO = new BenefitsDiscountTreeTO();
    let images:ImageDiscountTO[] = [];

    this.discount.category  = this.categories.find( category => values.category == category.idCategory );
    let sub = this.subcategories.find( subcategory => values.subCategory == subcategory.idSubCategory );
    sub.category = this.categories.find( category => values.category == category.idCategory );
    this.discount.subCategory = sub;
    this.discount.supplier = values.supplier;
    this.discount.title = values.title;
    this.discount.startDate = values.startDate;
    this.discount.endDate = values.endDate;
    this.discount.status = values.status;
    this.discount.description = values.  description;
    this.discount.linkUrl = values.linkUrl;
    this.discount.termsConditions = values.termsConditions;
    this.discount.descriptionPreview = values.descriptionPreview;
    this.discount.viewCount = 0;
    this.discount.notificationTime = values.notificationTime;
    this.discount.notificationDetail = values.notificationDetail;
    this.discount.lastUserModifier;
    this.discount.publicationTime = values.publicationTime;
    this.discount.typeDiscount = this.typeDiscount;
    this.discount.cost = this.cost;
    this.discount.levelRh =  this.discountOption;
    this.discount.lastUserModifier= this._localService.getUser();
    
    this.nameImages.forEach( (values,index) => {
      let image:ImageDiscountTO = new ImageDiscountTO(this._localService);
      image.idImage = index < this.imagesDiscount.length? this.imagesDiscount[index].idImage:null;
      image.nameImage = this.nameImages[index];
      image.value = this.base64textString[index];
      image.typeImage = index == 0 ? 'P' : 'S' ;
      if(image.value!=null)
      {
        images.push( image );
      }
      
    });

    discountTO.discount = this.discount;
    discountTO.images = images;
    discountTO.benefitsNotificationsTO = this.jsonTreeDiscount;
    this._discountService
        .newOrUpdateDiscount( discountTO )
        .subscribe( resp => {
          if( resp ) {
            this._dataService.setIsLoadingEvent(false);
            if(!updateStatus){
            this._dataService.setGeneralNotificationMessage(`${MSG.OK}`);
            }
            else
            {
              let type = this.typeDiscount == 'D' ? 'Descuento' : 'Beneficio';
              this._dataService.setGeneralNotificationMessage('Se ha modificado el estatus del ' +` ${type} `);
            }

            setTimeout(() => {
               this.router.navigate([`${routesWeb.HOME}/${routesWeb.DISCOUNT}`]);
            }, 3900);            
          }
        }, error => {
          this._dataService.setGeneralNotificationMessage( error.error.message );     
          this._dataService.setIsLoadingEvent(false); 
        },()=>  this._dataService.setIsLoadingEvent(false));
    
  }


  selectedBenefit( event:MatRadioChange ) {
    if( 'D' == event.value ){
      this.costBenefitView = false;
      this.cost = false;
      this.formDiscount.enable();
     }
    if( 'B' == event.value ){
      this.formDiscount.enable();
      this.costBenefitView = true;
    }

    this.typeDiscount = 'D' == event.value ? 'D' : 'B';
  }

  selectedCost( event:MatRadioChange ) {
    this.formDiscount.enable();
    this.cost = 'Y' == event.value; 
  }

  enableForm() {
    this.showButtonSave = true;
    this.formDiscount.enable();
    this.disabledTree = false;
    this.formDiscount.get('lastUserModifier').disable();
  }

  enableButtonSave() {

    let primaryImg = this.nameImages[0].length > 0;
    let secondImg = this.nameImages[1].length > 0;
    let res=!(this.formDiscount.valid && (secondImg && primaryImg));
    return res;
  } 
   showFileChosserImage() {
    if (this.formDiscount.disabled) {
      return;
    }
    this.render.selectRootElement( this.primaryImg.nativeElement ).click();
  }

  showFileChosserImageTwo() {
    if (this.formDiscount.disabled) {
      return;
    }
    this.render.selectRootElement( this.secondImg.nativeElement ).click();
  }
  showFileChosserImageThird(){
      if (this.formDiscount.disabled) {
        return;
      }
      this.render.selectRootElement( this.thirdImg.nativeElement ).click();    
  }
  showFileChosserImageFour(){
    if (this.formDiscount.disabled) {
      return;
    }
    this.render.selectRootElement( this.fourImg.nativeElement ).click();    
}

  getImage(file:File,item:number) {
    if(!(/\.(jpg|png)$/i).test(file.name)){
      this._dataService.setGeneralNotificationMessage(MSG.IMAGE_FORMAT_VALIT);
      return;
    }
    else
    {
      this.positionImage = item;
      this.nameImages[item] = file.name;
      if (file) {
        const reader = new FileReader();
        reader.onload = this.handleReaderLoaded.bind(this);
        reader.readAsBinaryString(file);
      }
    }

  }

  handleReaderLoaded(e) {
    this.base64textString[this.positionImage] = ('data:image/png;base64,' + btoa(e.target.result));
  }

  loadSubcategories() {
    let idCategory = this.formDiscount.get('category').value;
    this._discount.getSubcategories( idCategory ).subscribe( (subcategories:any) => this.subcategories = subcategories );
  }

  cancelButton() {
    this.location.back();
  }

  addIputImage() {
    this.count += 1;
  }

  ngOnDestroy() {
    this._discount.setDiscount( null );
  }
}

export interface Food {
  value: number;
  viewValue: string;
}
