import { Directive, HostListener, Input } from '@angular/core';

/**
 * Generated class for the MaxLimitDirective directive.
 *
 * See https://angular.io/api/core/Directive for more info on Angular
 * Directives.
 */
@Directive({
  selector: '[max-limit]' 
})
export class MaxLimitDirective {
  @Input('max-limit') maxLimit: number;

  constructor() {
  }

  @HostListener('keyup', ['$event'])
  keyDown(event) {
    if(event.target.value.length >= (this.maxLimit) + 1) {
      event.target.value = event.target.value.slice(0, (this.maxLimit - 1));
    }
  }

  @HostListener("blur" , ['$event'])
  onBlur(event){
    if(event.target.value.length >= (this.maxLimit) + 1) {
      event.target.value = event.target.value.slice(0, (this.maxLimit - 1));
    }
  }

  @HostListener("focus" , ['$event'])
    onFocus(event) {
      if(event.target.value.length >= (this.maxLimit) + 1) {
        event.target.value = event.target.value.slice(0, (this.maxLimit - 1));
      }
    }
}
