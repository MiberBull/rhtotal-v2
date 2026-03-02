import { Component, OnInit,Inject } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { DialogFormFilterService } from "../../services/filter/dialog-form-filter.service";
import { DataService } from '../../services/data.service';
import { MSG } from '../../../environments/environment.prod';


@Component({
  selector: 'app-dialog-forms-filter',
  templateUrl: './dialog-forms-filter.component.html',
  styleUrls: ['./dialog-forms-filter.component.css'],

})
export class DialogFormsFilterComponent implements OnInit {

  filter:string;
  inputs:any[] = [];
  idsInputs:any[]=[];
  
  constructor(
    private _dialogFormFilterService:DialogFormFilterService,
    private _dataService: DataService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DialogFormsFilterComponent>) {
      this.filter=data.filter;             
  }

  ngOnInit() {      
      this.inputs = this._dialogFormFilterService.getInputs(this.filter);
  }

  cancelDialog()
  {
    this.inputs.forEach( (element) => 
    {                     
      element.value = null;
    });    
    
    this.dialogRef.close();
  }

  findUsers()
  {
    console.log("findUsers");
    let filters:any = {};
    var rangoValido = true;
    this.inputs.forEach((element) => 
    {
      let control:string = element.name.toLowerCase();
      let acontrolArray = control.split(' ');
      control = acontrolArray.join('');
      control = control.replace(/í/gi,"i");
      control = control.replace(/ú/gi, "u");
      control = control.replace(/ó/gi, "o");
      filters[control] = element.value;
    });
     
    let banderFilter = filters.fechainicio || filters.fechafin ? 'containFilterDate' : null;
    let notificationFilter = filters.fechadeenvioinicio || filters.fechadeenviofin ? 'containFilterDateNorification' : null;
    let insuranceFilter = filters.fechavigenciainiciodesde || filters.fechavigenciainiciohasta ? 'insuranceFilter' : null;
     
    if ((banderFilter && filters.fechafin < filters.fechainicio) ||
        (notificationFilter && filters.fechadeenviofin < filters.fechadeenvioinicio) ||
        (insuranceFilter && (filters.fechavigenciainiciohasta < filters.fechavigenciainiciodesde)))
    {
      rangoValido = false;
    }

    if (rangoValido == false)
    {
      this._dataService.setGeneralNotificationMessage('Ingrese un rango de fechas válido');
      return;
    }

    if ((banderFilter && (!filters.fechainicio || !filters.fechafin)) ||
        (notificationFilter && (!filters.fechadeenvioinicio || !filters.fechadeenviofin)) ||
        (insuranceFilter && (!filters.fechavigenciainiciodesde || !filters.fechavigenciainiciohasta))) 
    {
      this._dataService.setGeneralNotificationMessage('Ingrese Fecha inicio y Fecha fin');
      return;
    }
     
    let filtersDialog:any={};

    if (notificationFilter != null) 
    {
      filtersDialog.dateStart = filters.fechadeenvioinicio ? filters.fechadeenvioinicio : null;
      filtersDialog.dateEnd = filters.fechadeenviofin ? filters.fechadeenviofin : null;   
      
      if(!(this.validDialogFilter(filtersDialog)))
      {
        return;
      }
      else
      {
        this.inputs.forEach(element => {
          element.value = null;
        });
      }
    }
    else if (banderFilter != null) 
    {
      filtersDialog.dateStart = filters.fechainicio ? filters.fechainicio : null;
      filtersDialog.dateEnd = filters.fechafin ? filters.fechafin : null;

      if (!(this.validDialogFilter(filtersDialog))) 
      {
        return;
      }
      else 
      {
        this.inputs.forEach(element => 
        {
          element.value = null;
        });
      }
    }
    else 
    {
      this.inputs.forEach(element => 
      {
        element.value = null;
      });
    }
    console.log("Filtros: ",filters);

    this._dialogFormFilterService.setfiltersDialog(filters);

    /*
     this.dialogRef.afterClosed().subscribe(result=>{          
      
    })
*/
    this.dialogRef.close();
  }

  
  validDialogFilter(filters:any){     
    let banderaFilters:boolean = true;
    banderaFilters = banderaFilters && this.validDateStartEnd(filters.dateStart,filters.dateEnd);
    
    return banderaFilters;
  }  

  validDateStartEnd(dateStart:string,dateEnd:string){  
    let isValue:boolean = true;
    isValue = dateStart != null && dateEnd != null ? true : false;       
    !(isValue) ? this._dataService.setGeneralNotificationMessage(MSG.NOTIFICATION_START_END) : '';        
    return isValue;
  }
  
}
