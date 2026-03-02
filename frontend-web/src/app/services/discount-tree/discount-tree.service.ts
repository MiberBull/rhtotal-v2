import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DiscountTreeService {

  arbol1=[
    {'empresa':'pepsi','proyecto':'Razon Social','empleado':'Piter'},
    {'empresa':'pepsi','proyecto':'Razon Social','empleado':'Sonia'},
    {'empresa':'pepsi','proyecto':'Razon Social','empleado':'Nancy','status':''},
  ];

  constructor() { }
}
