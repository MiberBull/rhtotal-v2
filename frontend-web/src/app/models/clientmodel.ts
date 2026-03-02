import { CreationInformation } from "./creation-information.model";
import { LocalStorageService } from "../services/local-sotorage/localstorage.service";

export class CompoundCustomerTO {
  customer: CustomerTO;
  projectTOList : Array<ProjectTO> = new Array<ProjectTO>();
}

export class CustomerTO extends CreationInformation {
  idCliente: number;
  name: string;
  address: string;
  contact: string;
  phone: string;
  extension: string;
  email: string;
  additionalInformation: string;
  status: string;

  constructor( private localService:LocalStorageService ) {
    super( localService );
    this.idCliente = null;
  }

}

export class ProjectTO extends CreationInformation{
  idProject: number;
  idClient: CustomerTO;
  name: string;
  rfc: string;
  businessName: string;
  address: string;
  contact: string;
  phone: string;
  extension: string;
  email: string;
  additionalInformation: string;
  status: string;

  constructor( private localService:LocalStorageService ) {
    super( localService );
    this.idProject = null;
  }

}
