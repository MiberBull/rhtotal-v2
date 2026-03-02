import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-pie-graphic',
  templateUrl: './pie-graphic.component.html',
  styles: []
})
export class PieGraphicComponent implements OnInit {

  @Input() type:any;

  public pieChartLabels:string[] = ['Download Sales', 'In-Store Sales', 'Mail Sales'];
  public pieChartData:number[] = [300, 500, 100];
  public pieChartType:string = 'pie';
  public options = { title: { display: true, text: 'Custom Chart Title'} };

  constructor() {  
  }

  ngOnInit() {
  }

  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }

}
