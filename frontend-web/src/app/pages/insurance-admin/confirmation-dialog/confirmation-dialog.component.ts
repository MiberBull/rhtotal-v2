import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent implements OnInit {

  message:string;
  title:string;

  constructor(
    public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

      this.message = data.status;
      this.title = data.title;

  }

  ngOnInit() {
  }

  notAccept(){

    this.dialogRef.close(
      {
        confirStatus: false
      }
    );

  }

  acceptStatus(){

    this.dialogRef.close(
      {
        confirStatus: true
      }
    );

  }

}
