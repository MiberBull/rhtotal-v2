import { Component, OnInit, Input, Output,EventEmitter } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'app-info-table-paysheet',
  templateUrl: './info-table-paysheet.component.html',
  styleUrls: ['./info-table-paysheet.component.css']
})
export class InfoTablePaysheetComponent implements OnInit {

  @Input('columns') displayedColumns: string[];
  @Input('info') dataSourceTable :any[];
  @Input('titles') displayTitles: string[]; 
  
  @Output('selected-item') item = new EventEmitter<any>();

  selectState:any=null;
  selectEnabled:boolean=false;

  constructor() { }

  ngOnInit() {
  }

  selectedItem(event){
    this.item.emit( event );
  }

  changeState(event){
       console.log('EVENTO ',event);
       
  }


}
