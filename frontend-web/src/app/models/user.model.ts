
export class UserTO{
    idUser:number;
    idAccessPrivilege:number;
    email:string;
    userType:string;
    statusUser:string;
    password:string;
    lastUserModifier:string;
    lastModification:Date;
    creationDate:Date;
    creationUser:string;
}

export class LoginTO{
    message:string = null;
    block:boolean = false;
    device:number;
    flag:number = -1;
    user:UserTO;   
}

