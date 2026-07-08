export interface CfdiTO {
  id?: number;
  idEmployee: number;
  dsPeriod: string;
  dsType: string;
  dsUuid?: string;
  dsRfcEmisor?: string;
  dsRfcReceptor?: string;
  nbTotal?: number;
  dsXmlContent?: string;
  dsXmlS3Key?: string;
  dsPdfS3Key?: string;
}

export interface EmployeeDocumentTO {
  id?: number;
  idEmployee: number;
  idDocumentType: number;
  dsFilename: string;
  dsMimeType: string;
  dsContent?: string;
  dsS3Key?: string;
  dsStatus?: string;
  dsRejectionReason?: string;
  dsValidatedBy?: string;
}

export interface DocumentTypeTO {
  id?: number;
  dsCode: string;
  dsName: string;
  fgRequiredOnboarding: boolean;
  fgEmployeeUploadable: boolean;
}
