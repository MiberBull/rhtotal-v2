import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DialogNewItemComponent } from '../../components/dialog-new-item/dialog-new-item.component';
import { UploadFileComponent } from '../../components/upload-file/upload-file.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DiscountService } from '../../services/discount/discount.service';
import { CategoryTO, SubcategoryTO } from '../../models/category_sub.model';
import { mergeMap } from 'rxjs/operators';
import { DiscountTO } from '../../models/discount.model';

const dialogConfig = new MatDialogConfig();

@Component({
  selector: 'app-create-discount',
  templateUrl: './create-discount.component.html',
  styleUrls: ['./create-discount.component.css','../../../assets/custom.css']
})
export class CreateDiscountComponent implements OnInit,OnChanges {

  @Input() discount:DiscountTO;

  formDiscount: FormGroup;
  categories: any[];
  subcategories: any[];
  disableBtnSubcategory:boolean = false;

  constructor( private dialog: MatDialog,
    private fb: FormBuilder,
    private _discountService: DiscountService) {
  }

  ngOnInit() {
 
  }

  ngOnChanges( change:SimpleChanges ) {
    this.formDiscount.patchValue( change.discount.currentValue );
  }


}
