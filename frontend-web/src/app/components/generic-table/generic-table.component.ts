import { Component, OnInit, Input, Output,EventEmitter } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';

import { GenericTableService } from '../../services/generic-table/generic-table.service';


@Component({
  selector: 'app-generic-table',
  templateUrl: './generic-table.component.html',
  styleUrls: ['./generic-table.component.css']
})

export class GenericTableComponent implements OnInit {

@Input() tabs:string[];
@Input() headers:string[] = [];
@Input('info-data') infoData:any[] = []
@Input() titles:string[] = [];
@Input('total-items') totalItems:number;
@Input() headersJSON:any;

@Output('tag-change') tagChangeEvent = new EventEmitter<MatTabChangeEvent>() 
@Output('selected-item') selectedItem = new EventEmitter<any>();

@Output('go-next') next = new EventEmitter<number>();
@Output('go-prev') prev = new EventEmitter<number>();

tab:boolean = false;

constructor() {}

  ngOnInit() {} 
  
  tagChange( tagChange: MatTabChangeEvent ) {
    this.tagChangeEvent.emit( tagChange );
  }

  selectItem( item ) {
    this.selectedItem.emit( item );
  }

  goPrev( page ) {
    this.prev.emit( page );
  }

  goNext( page ) {
    this.next.emit( page );
  }

}
