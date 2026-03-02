import { Component, OnInit, Inject } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-dialog-loading',
  templateUrl: './dialog-loading.component.html',
  styleUrls: ['./dialog-loading.component.css']
})
export class DialogLoadingComponent implements OnInit {
  message:string;
  constructor(public dialogRef: MatDialogRef<DialogLoadingComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {

          this.message=data.message;

 }

  ngOnInit() {
  }

}
