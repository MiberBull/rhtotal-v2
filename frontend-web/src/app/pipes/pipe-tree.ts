import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: 'reducer' })
export class PipeTree implements PipeTransform{
    transform(cadena: any, size=20) {
      let cadenaSub='';
      if (cadena != null){
        cadenaSub = cadena.length > size ? cadena.substr(0, size) + '...' : cadena;
      }
      
      return cadenaSub;
    }
}