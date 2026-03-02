import { Component, OnInit, AfterContentInit } from '@angular/core';
import { Router } from '@angular/router';

import { DataService } from '../services/data.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls:['./pages.component.css']
})
export class PagesComponent implements OnInit {

  isLoading:boolean = false;
  isDownload:boolean = false;

  constructor( private _data: DataService, private route: Router ) { 

    this._data.getIsLoadingEvent().subscribe( isLoading => this.isLoading = isLoading );
    this._data.getIsDownload().subscribe( isDownload => this.isDownload = isDownload );

  }

  ngOnInit() {
    this.route.navigate(['home/dashboard']);
  }

}
