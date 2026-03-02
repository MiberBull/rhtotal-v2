

export class UserTO{
    active: boolean;
    creationDate: Date;
    creationUser: string;
    email: string;
    id: number;
    lastModification: Date;
    lastUserModifier: string;
    level: number;
    password: string;
    userStatus: string;
    userType: string;
    
    constructor(){}

}

export class LoginTO{
    message:string = null;
    block:boolean = false;
    flag:number = -1;
    user:UserTO;   

    constructor(){}
}


export class NewAccountTO {
    user:string;
    password:string;
    passwordConfirmed:string;
    code:string;

    constructor() {}

    getUser() {
        return this.user;
    }

    setUser( user:string ) {
        this.user = user;
    }

    getPassword() {
        return this.password;
    }

    setPassword( password:string ) {
        this.password = password;
    }

    getPasswordConfirmed() {
        return this.passwordConfirmed;
    }

    setPasswordConfirmed( passwordConfirmed:string ) {
        this.passwordConfirmed = passwordConfirmed;
    }

    getCode() {
        return this.code;
    }

    setCode( code:string ) {
        this.code = code;
    }


}


export class EmployeeHitoryTO{

    constructor(){}
    
    idEmployeeHis :number;
    idUser :number;
    entryDate:Date;
    endDate:Date;
    qtSalary :String;
    benefitsLaw :string;
    aditionalBenefits :string;
    dsCompany:string;
    dsEmployeePosition:string;
    dsIndustry :String;
    dsArea :String;
    qtDependets :String;
    lastUserModifier :string;
    lastModification :Date;
    creationUser :string;
    creationDate :Date;
    active:boolean;
}