
export class personal {
    rfc: string;
    paisNacimiento: string;
    numCelular: string;
    nss: string;
    nombres: string;
    genero: string;
    fechaNacimiento: string;
    estadoNacimiento: string;
    estadoCivil: string;
    emailPersonal: string;
    curp: string;
    apellidoPaterno: string;
    apellidoMaterno: string;
}

 export class direccion{
    numeroInterior: string;
    numeroExterior: string;
    estado: string;
    delegacionMunicipio: string;
    cp: string;
    colonia: string;
    calle: string;
  }

  export class data{
    personal:personal;
    direccion:direccion;
}

  export class result{
    total: number;
  to: number;
  prev_page_url: string;
  per_page:number;
  next_page_url:string
  last_page:number;
  from: number;
  data:data[];
  current_page:number;
}

export class dataJob{
 personal: personalJob;
 pago:puestoJob[];

}

export class personalJob{
  idEmpleado:string;
  rfc:string;
  puesto:string;
  salarioRealCapturado:string;
  urlContAsignacion:string;
  urlContConfidencialidad:string;

}

export class puestoJob{
  fechaContratacion:string;
  periodoPago:string;
  nombreCliente:string;
  nombreProyecto:string;
  salarioRealAsegurado:string;
  salarioRealPercibidoMes:string;
}

export class resultJob{
  total: number;
  to: number;
  prev_page_url: string;
  per_page:number;
  next_page_url:string
  last_page:number;
  from: number;
  data:dataJob;
  current_page:number; 
}