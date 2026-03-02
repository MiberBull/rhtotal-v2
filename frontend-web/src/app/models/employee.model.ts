import { getLocaleExtraDayPeriodRules } from "@angular/common";

export class EmployeeUserTO{
	id:number;
    idAccessPrivilege:number;
    email:string;
    userType:string;
	userStatus:string;
    password:string;
    lastUserModifier:string;
    lastModification:Date;
    creationDate:Date;
    creationUser:string;
}
export class EmployeeTO{
	id :number;
	idClient :number;
	idProject :number;
	user :EmployeeUserTO;
	name :string;
	cvilStatus :string;
	lastName :string;
	lastMName :string;
	gender :string;
	lastUserModifier :string;
	lastModification :Date
	creationUser :string;
	creationDate :Date;
	status :boolean;
		active :boolean;
	workPermitConfirm:any;	
}

export class EmployeeComplementaryTO{

	
	 id: number;
    employee:EmployeeTO;
    idSwap :number;
    rfc:string;
    curp:string;
    nss:string;
    emailClient:string;
    email:string;
	phone:string;
	workPermitConfirm:string;
    workPermit:string;
    birthDate:Date;
    birthState:string;
    birthCountry:string;
    photography:string;
    passportNumber:string;
    lastUserModifier:string;
    lastModification :Date;
    creationUser:string;
    creationDate :Date;
    active:boolean;

}


export class EmployeeDomicileTO{
id:number;
employee:EmployeeTO;
street:string;
interiorNumber:string;
outDoorNumber:string;
colony:string;
postalCode:string;
city:string;
state:string;
lastUserModifier:string;
lastModification:Date;
creationUser:string;
creationDate:Date;
active:boolean;
}

export class ControlCompensationTO{

	Sueldo_bruto_mensual:number;
	Automovil:number;
	Gastos_Automovil:number;
	Opcion_Compra:number;
	Bono_Mensual:number;
	Cantidad_Bono_Mensual:number;
	Bono_Bimestral:number;
	Cantidad_Bono_Bimestral:number;
	Bono_Trimestral:number;
	Cantidad_Bono_Trimestral:number;
	Bono_Anual:number;
	Cantidad_Bono_Anual:number;
	Metricas_Otorgamiento_Bono:number;
	Fondo_de_Ahorro:number;
	Cantidad_Fondo_de_Ahorro:number;
	Vales_de_Despensa:number;
	Cantidad_Vales_de_Despensa:number;
	Vales_Restaurante:number;
	Cantidad_Vales_Restaurante:number;
	Vales_Gasolina:number;
	Cantidad_Vales_Gasolina:number;
	Aguinaldo:number;
	Dias_Aguinaldo:number;
	Cuantos_dias_de_vacaciones:number;
	Porcentaje_prima_vacacional:number;
	Seguro_GM_Mayores:number;
	Seguro_GM_Menores:number;
	Seguro_de_vida:number;
	Meses_de_Cobertura_por_Muerte:number;
	Reparto_de_utilidades:number;
	Ultimo_monto_recibido:number;
	Plan_de_pensiones:number;
	Otra_prestacion:string;
	Ingreso_mensual_bruto_integrado:number;
	Ingreso_anual_bruto_estimado:number;
	


}

export class EmployeeCompensationTO{
	idCompensationPackage:number ;
	idCompetation:number;
	idUser:number;
	dsName:string;
	valor:string;
	email:string
	lastUserModifier:string;
	lastModification:Date;
	creationUser:string;
	creationDate:Date;
}

export class EmployeeSocialNetworkTO{
    idSocialNet:number;
	idUSer:number;
	nameRedSocial:string;
	value :string;
	parameterDescription :string;
	lastUserModifier :string;
	lastModification: Date;
	creationUser: string;
	creationDate :Date;
}
export class EmployeeContratingTO{
    idContrating:number;
	idUser :number;
	skill :string;
	qtSalary :string;
	dsArea :string;
	cv :string;
	cvPdf:string;
	job:string;
	contract :string;
	endOfContract :Date;
	dsLastUserModifier :string;
	lastModification :Date;
	creationUser :string;
	creationDate :Date;
	active:boolean;
	}

export class EmployeeHitoryTO{
    idEmployeeHis :number;
	idUser :number;
	entryDate:Date;
	endDate:Date;
	qtSalary :String;
	benefitsLaw :String;
	aditionalBenefits :String;
	benefitsLawBool :boolean;
	aditionalBenefitsBool :boolean;
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

export class EmployeeDataAssignmentTO{
    idDataAssigment:number;
	idUser :number;
	employeePosition :string;
	client :string;
	idClient :number;
	idProject:number;
	project :string;
	manager :string;
	state :string;
	city :string;
	emailDirectBoss :string;
	telephoneDirectBoss :string;
	startAssigment:Date;
	endAllocation :Date;
	allocationEmail :string;
	allocationSalary :number;
	assignmentContract :string;
	confidentialyContract :string;
	evaluation :string;
	lastUserModifier :string;
	lastModification :Date;
	creationUser :string;
	creationDate :Date;
    active:boolean;
}

export class SocialNetworksTO{
Facebook:boolean;
Twitter:boolean;
LinkedIn:boolean;
Google:boolean;
Instagram:boolean;
Snapchat:boolean;
Spotify:boolean;
}

export class CivilStatusTO{
	idCivilStatus:number;
	statusCivil:String;
	civilCode:String;
} 

export class StateTO{
	state:String;
    idState:number;

}
export class CityTO{
	city:String;
    idCity:number;
}

export class ClientTO{
	name:String;
	idClient:number;
}

export class ProjectTO{
	name:String;
	idProject:number;
}

