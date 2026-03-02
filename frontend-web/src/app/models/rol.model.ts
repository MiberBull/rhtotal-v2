import { environment } from '../../environments/environment';
export abstract class InfoModificaition{
    
    lastUserModifier:string = '';
    lastModification:Date;
    creationUser:string;
    creationDate:Date;

    constructor() {
    }

}

export class RolesUserTO extends InfoModificaition{
   
    idRolAssig:number;
    idRol:CatalogRolTO;
    nameRol:string;
    name:string;
    lastName:string;
    mLastName:string;
    phone:string;
    email:string;
    status:string;
    password:string;
    active:boolean = true;

    constructor(){
        super();
    }

}

export class CatalogRolTO extends InfoModificaition{
    
    idRol:number;
    descriptionRol:string;
    permission:string;

    constructor(){
        super();
    }

}

export class RolCompoundTO<T> {

    roleList:Array<T> = [];
    imageRole:string;
    htmlRole:string;
    token:string;
    
    constructor(){
    }

    setRoleList(userRole:T){
        this.roleList.push(userRole);
    }

    setToken(token:string){
        this.token = token;
    }

    getRoleList() {
        return this.roleList;
    }

    setImageRole( img:string ) {
        this.imageRole = img;
    }

    getImageRole() {
        return this.imageRole;
    }

    setHtlmRole( html:string ) {
        this.htmlRole = html;
    }

    getHtmlRole() {
        return this.htmlRole;
    }
    

}

