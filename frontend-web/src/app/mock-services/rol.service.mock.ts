import { RolesUserTO } from './../models/rol.model';
import { of, Observable } from 'rxjs';
import { Injectable } from "@angular/core";


@Injectable({
    providedIn:'root'
})

export class RolMockService{
    
    private userId: RolesUserTO;

    constructor(){}

    getRoles(){
        return of([{idRol:1,descriptionRol:'Admin'},{idRol:2,descriptionRol:'Web'}]);
    }

    getStatus(){
        return of([{id:'activo',status:'activo'},{id:2,status:'desactivo'}]);
    }

    assignRole(user:RolesUserTO){
        return of({status:'ok'});
    }

    getUserRoles(user:RolesUserTO){
        return of({email:"ale.pedroza@gmail.com",lastName:"Barrera",mlastName:"Pedroza",name:"Alejandra",phone:"5569795191",idRol:2,status:2});
    }

    setUserId(userId:RolesUserTO){
        this.userId = userId;
    }

    getUserId(){
        return this.userId;
    }

    testArray(){
        var arr = Object.keys(allColumns).map( x => allColumns[x]);
        // console.log( arr );
        var arrayString = 'Cliente,Proyecto,Empleado,Sueldo bruto mensual,Estatus';
        var arrayStringw:string[] = arrayString.split(',');
        console.log( arrayStringw ); 

    }

}
export const allColumns = [{"title":"Cliente"},{"title":"Proyecto",},{"title":"Empleado"},{"title":"Sueldo bruto Mensual"},{"title":"Estatus"}];
