import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;
 
  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
    console.log( e );
  }
 
  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
    console.log( e );
  }

  constructor() { }

  ngOnInit() {
  }

}
