import { Component, OnInit, Output, EventEmitter, OnChanges, SimpleChanges, Input } from '@angular/core';
import { TreeNotificationService } from '../../mock-services/tree.service.mock';
import { TreeService } from '../../services/tree/tree.service';
import { SavedRoutesService } from '../../services/routing/saved-routes.service';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';



@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit,OnChanges {

  @Input() jsonTree:any={};
  @Output('tre-json') json:EventEmitter<any> = new EventEmitter() ;
  @Input('confirm') confirm:boolean;
  @Input('disable') disable:boolean;

  formTree:FormGroup;

  constructor() {                              

  }

  ngOnInit() {
  }

  enableCheck( enable:boolean ) {
    this.disable = enable;
  }


  ngOnChanges(changes: SimpleChanges): void {
  }


}
