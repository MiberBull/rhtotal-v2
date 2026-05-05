import { UserTO } from './user.model';

export interface EmployeeTO {
  id: number;
  idClient: number;
  idProject: number;
  user: UserTO;
  name: string;
  cvilStatus: string;
  lastName: string;
  lastMName: string;
  gender: 'M' | 'F';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  status: boolean;
  active: boolean;
  workPermitConfirm?: string;
}

export interface EmployeeComplementaryTO {
  id: number;
  employee: EmployeeTO;
  idSwap?: number;
  rfc: string;
  curp: string;
  nss: string;
  emailClient: string;
  email: string;
  phone: string;
  workPermitConfirm: string;
  workPermit: string;
  birthDate: Date;
  birthState: string;
  birthCountry: string;
  photography: string;
  passportNumber: string;
  nationality?: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
}

export interface EmployeeDomicileTO {
  id: number;
  employee: EmployeeTO;
  street: string;
  interiorNumber: string;
  outDoorNumber: string;
  colony: string;
  postalCode: string;
  city: string;
  state: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
}

export interface EmployeeCompensationTO {
  idCompensationPackage: number;
  idCompetation: number;
  idUser: number;
  dsName: string;
  valor: string;
  email: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active?: boolean;
}

export interface EmployeeContratingTO {
  idContrating: number;
  idUser: number;
  skill: string;
  qtSalary: string;
  dsArea: string;
  cv: string;
  cvPdf: string;
  job: string;
  contract: string;
  endOfContract: Date;
  dsLastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
}

export interface EmployeeHistoryTO {
  idEmployeeHis: number;
  idUser: number;
  entryDate: Date;
  endDate: Date;
  qtSalary: string;
  benefitsLaw: string;
  aditionalBenefits: string;
  benefitsLawBool: boolean;
  aditionalBenefitsBool: boolean;
  dsCompany: string;
  dsEmployeePosition: string;
  dsIndustry: string;
  dsArea: string;
  qtDependets: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
}

export interface EmployeeDataAssignmentTO {
  idDataAssigment: number;
  idUser: number;
  employeePosition: string;
  client: string;
  idClient: number;
  idProject: number;
  project: string;
  manager: string;
  state: string;
  city: string;
  emailDirectBoss: string;
  telephoneDirectBoss: string;
  startAssigment: Date;
  endAllocation: Date;
  allocationEmail: string;
  allocationSalary: number;
  assignmentContract: string;
  confidentialyContract: string;
  evaluation: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  active: boolean;
}

export interface EmployeeSocialNetworkTO {
  idSocialNet: number;
  idUser: number;
  nameRedSocial: string;
  value: string;
  parameterDescription: string;
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
}

export interface ControlCompensationTO {
  idUser: number;
  Sueldo_bruto_mensual: number;
  Automovil: number;
  Bono_Mensual: number;
  Aguinaldo: number;
  Dias_Aguinaldo: number;
  Cuantos_dias_de_vacaciones: number;
  Seguro_GM_Mayores: number;
  Seguro_de_vida: number;
  Plan_de_pensiones: number;
  Fondo_de_ahorro: number;
  Vales_de_despensa: number;
  [key: string]: number;
}
