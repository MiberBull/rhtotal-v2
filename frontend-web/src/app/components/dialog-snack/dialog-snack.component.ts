import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-snack',
  templateUrl: './dialog-snack.component.html',
  styleUrls: ['./dialog-snack.component.css']
})
export class DialogSnackComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DialogSnackComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {

  }

  close() {
    this.dialogRef.close();
  }

}
