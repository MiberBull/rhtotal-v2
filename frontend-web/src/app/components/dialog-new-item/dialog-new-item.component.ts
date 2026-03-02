import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-new-item',
  templateUrl: './dialog-new-item.component.html',
  styleUrls: ['./dialog-new-item.component.css']
})
export class DialogNewItemComponent implements OnInit {

  item: string;
  title: string;
  placeholder: string;

  constructor( @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DialogNewItemComponent>) {
    this.title = data.title;
    this.placeholder = data.placeholder;
  }

  ngOnInit() {
  }

  saveItem(){
    this.dialogRef.close(this.item);
  }

}
