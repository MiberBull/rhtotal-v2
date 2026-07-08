export interface RepseProfileTO {
  id?: number;
  dsRazonSocial: string;
  dsRfc: string;
  dsNumeroRepse: string;
  dtVigencia: string;
  dsStatus: string;
  idTenant?: number;
}

export interface RepseClientTO {
  id?: number;
  idRepseProfile: number;
  dsNombreCliente: string;
  dsRfcCliente: string;
  nuDocumentosRequeridos: number;
  idTenant?: number;
}

export interface RepseDocumentTO {
  id?: number;
  idRepseClient: number;
  dsPeriod: string;
  dsType: string;
  dsStatus?: string;
  dsFilename: string;
  dsContent?: string;
  dsNotes?: string;
  dsValidatedBy?: string;
  dsRejectionReason?: string;
  idTenant?: number;
}

export interface RepseComplianceTO {
  idRepseClient: number;
  dsNombreCliente: string;
  dsPeriod: string;
  nuRequired: number;
  nuSubmitted: number;
  nuValidated: number;
  nuRejected: number;
  dsSemaforo: string;
}

export interface RepseExpiringTO {
  idRepseProfile: number;
  dsRazonSocial: string;
  dsNumeroRepse: string;
  dtVigencia: string;
  nuDaysToExpiry: number;
}
