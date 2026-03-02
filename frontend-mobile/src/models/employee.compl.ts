import { UserTO } from '../models/user.model';


export class EmployeePersonalTO  {
   complementary:EmployeeComplementaryTO;
   addressTO:EmployeeDomicileTO;
}

export class EmployeeComplementaryTO{
        active: boolean;
        birthCountry: string;
        birthDate: Date;
        birthState: string;
        creationDate: Date;
        creationUser: string;
        curp: string;
        email: string;
        emailClient: string;
        employee: EmployeeTO;
        id: number;
        idSwap: string;
        lastModification: Date;
        lastUserModifier: string;
        nss: string;
        passportNumber: string;
        phone: string;
        photography: string;
        rfc: string;
        workPermit: string;
        workPermitConfirm:string;
        nationality:string;
      }
      
      export class EmployeeTO {
        active: boolean;
        creationDate: Date;
        creationUser: string;
        cvilStatus: string;
        gender: string;
        id: number;
        lastMName: string;
        lastModification: Date;
        lastName: string;
        lastUserModifier: string;
        name: string;
        user: UserTO;
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

        export class SocialNetworkTO{
          idSocialNet:number;
          idUSer:number;
          nameRedSocial:string;
          value:string;
          lastUserModifier:string;
          lastModification:Date;
          creationUser:string;
          creationDate:Date;
          active:boolean;
        }

        
      export class resetPasswordTO {
        idUser:number;
        passNew:string;
        passOld:string;
      }

      export class MycvTO {
        idMycv:number;
        idUser:number;
        nameCv:string;
        value:string;
        creationUser:string;
        lastUserModifier:string;
        lastModification:Date;
        creationDate:Date;
        active:boolean;
      }
     
      
     export class CompensationPackageTO {
        idCompetation:number;
        idUser:number;
        dsName:string;
        valor:string;
        dsEmail:string;
        lastUserModifier:string;
        lastModification:Date;
        creationUser:string;
        creationDate:Date;
        active:boolean;
     }


     export class ContratingDataTO {
      idContrating:number;
      idUser:number;
      skill:number;
      qtSalary:number;
      dsArea:string;
      cv:string;
      job:string;
      contract:string;
      endOfContract:Date;
      dsLastUserModifier:string;
      lastModification:Date;
      creationUser:string;
      creationDate:Date;
      active:boolean;
     }

export class AsignationDataTO {
  idDataAssigment: number;
  idUser: Number;
  employeePosition:string;
  idClient: number;
  idProject: number;
  manager: string;
  state: string;
  city: string;
  emailDirectBoss: string;
  telephoneDirectBoss: string;
  startAssigment: Date;
  endAllocation: Date;
  allocationEmail: string;
  allocationSalary: number;
  evaluation: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
  client: string;
  project: string;
}
export class PonderationMobileTO {

  nameNivel:string;
  totalUser:number;
  total:number;
  porcentaje:number;
  porcentajeGrafica:number;
  promedioFinal:number;
  promedioFinalGrafica:number;
}
export class InsuranceTO{
  insurangeType:string;
  idInsurance:string;
  insuranceCarrier:string;
  policy:string;
  idInsurangeType:string;
}

export class InsuranceDetailTO{
  idInsurance:string;
  insurangeType:string;
  policy:string;
  insuranceCarrier:string;
  url:string;
  phones:string;
  coverage:string;
  endDate:string;
  contractPdf:string;
  individualCertificate:string
  typePolicy:string;
  startDate:string;
}

export class CoverageTO{
  sumAssured:string;
  titleDeductibles:string
  level:string;
  titleCoInsurance:string;
  titleDescription:string;
  titleLevel:string;
  description:string;
  titleSumAssured:string;
  deductibles:string;
  coInsurance:string;
}