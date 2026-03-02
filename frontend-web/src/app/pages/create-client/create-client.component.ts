import { Component, OnInit, Input, ViewChild, OnChanges, SimpleChanges, Output,EventEmitter } from "@angular/core";
import { FormArray, Form } from "@angular/forms";


@Component({
  selector: "app-create-client",
  templateUrl: "./create-client.component.html",
  styleUrls: ["./create-client.component.css","../../../assets/custom.css"]
})

export class CreateClientComponent implements OnInit,OnChanges {
  projectAuthor:boolean=true;
  typeForm: boolean;
  @ViewChild("formContainer")
  formContainer;
  @Input("form-info")
  formInfo: FormArray;
  @Input("form-group")
  formGroup;
  @Input("status")
  status:boolean;
  @Input('new-item')
  newItem:boolean;
  @Output("change-status")
  changeStatus = new EventEmitter<any>();

  constructor() {
  }
  
  ngOnInit() {
    this.typeForm = this.formInfo.controls["typeForm"] || false;
  }

  ngOnChanges(changes:SimpleChanges){
  
  }

  changeSelectStatus( formInfo:any ) {
    this.changeStatus.emit( formInfo );
  }

}

