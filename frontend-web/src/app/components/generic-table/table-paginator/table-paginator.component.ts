import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-table-paginator',
  templateUrl: './table-paginator.component.html',
  styleUrls: ['./table-paginator.component.css']
})
export class TablePaginatorComponent implements OnInit {

  @Input() total:number;


  
  @Output() goPrev = new EventEmitter<number>();
  @Output() goNext = new EventEmitter<number>();
  
  index:number = 1;
  count:number = 10;
  totalAnterior:number=0;
  

  constructor(){
 
    this.index = 1;
    this.count = 10;
  }

  ngOnInit(

  ){}

  getMin(): number {
    
    return  ((this.index - 1 )*this.count)+1;
  }

  getMax(): number {

    
    
    if(this.total && this.totalAnterior!=this.total)
    {    
      this.index = 1;
      this.count = this.total>10?10:this.total;
      this.totalAnterior=this.total;
    }
    if( ( this.count * this.index ) > this.total ) {
      let x = (this.total % this.count) ;
      return ( ( this.count * (this.index - 1) ) + x );
    }
    return ( this.count * this.index )
  }

  onPrev(): void {
   
    this.index -= 1;
    this.goPrev.emit( (this.index - 1) );
  }

  onNext(): void {

    this.index += 1;
    this.goNext.emit( (this.index - 1) );
  } 

}
