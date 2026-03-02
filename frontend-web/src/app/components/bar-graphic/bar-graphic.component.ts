import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-bar-graphic',
  templateUrl: './bar-graphic.component.html'
})
export class BarGraphicComponent implements OnInit {

  @Input() type:any ;

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartLabels:string[] = ['2006', '2007'];
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;
 
  public barChartData:any[] = [
    {data: [65, 59], label: 'Series A'},
    {data: [28, 48], label: 'Series B'}
  ];

  constructor() {
  }

  ngOnInit() {
  }

}
