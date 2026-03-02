import { StorageProvider } from "../providers/storage/storage";

export class Jobs {

    idJobHistory:null;
    employeePosition:string;
    company:string;
    bossName:string;
    bossEmail:string;
    bossTelephone:string;
    assigmentDtartDate:Date;
    assigmentEndDate:Date;
    qtSalary:number;
    assignmentEmail:string;
    professionalResume:string;
    lastUserModifier:string;
    creationUser:string;
    idUser:number;

    constructor( private storage_provider:StorageProvider ) {

        var user = this.storage_provider.getUser();

        this.lastUserModifier = user.email;
        this.creationUser = user.email;
        this.idUser = user.idUser;

    }
    
}

