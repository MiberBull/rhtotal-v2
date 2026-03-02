import { Component, OnInit, Input, Output, EventEmitter, ViewChild, AfterContentInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { tap } from 'rxjs/operators';
import { GenericTableService } from '../../../services/generic-table/generic-table.service';
import { Router } from '@angular/router';
import { ROOT_FRONTEND_WEB } from '../../../../environments/environment.prod';

//TODO: Adaptar el scrollbar a la clase principal


@Component({
  selector: 'app-info-table',
  templateUrl: './info-table.component.html',
  styleUrls: ['./info-table.component.css']
})



export class InfoTableComponent implements OnInit,AfterContentInit {

  @Input('columns') displayedColumns: string[];
  @Input('info') dataSourceTable :any[];
  @Input('titles') displayTitles: string[];

  @Output('selected-item') item = new EventEmitter<any>();


  rootTable:string;
  rootUsers:string;

  constructor( private route: ActivatedRoute,
               private _router: Router,  ) {

      this.rootTable = this._router.url.toString();
      this.rootUsers = ROOT_FRONTEND_WEB.USERS; 
      
  }

  ngOnInit() {
  }

  ngAfterContentInit(){}

  selectedItem(event){
    this.item.emit( event );
  }

}
