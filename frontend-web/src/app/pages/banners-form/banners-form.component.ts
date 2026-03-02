import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BannerTO } from '../../models/banner.model';
import { BannerService } from '../../services/banner/banner.service';

@Component({
  selector: 'app-banners-form',
  templateUrl: './banners-form.component.html',
  styleUrls: ['./banners-form.component.css','../../../assets/custom.css']
})
export class BannersFormComponent implements OnInit,OnChanges {

  @Input() banner:BannerTO;
  @Input() jsonTree:any;
  bannersFrom: FormGroup;

  constructor( private fb: FormBuilder, private _bannerService: BannerService  ) {
    this.bannersFrom = this.fb.group({
      title:[''],
      startDate:[''],
      endDate:[''],
      timePublication:[''],
      timeNotification:[''],
      detailNotification:[''],
      image:[''],
      status:[''],
      internalComments:[''],
      author:['']
    })
  }

  ngOnInit() {}

  save(){
    
  }

  ngOnChanges( change:SimpleChanges ) {
  }

}
