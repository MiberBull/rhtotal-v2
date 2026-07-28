export interface BannerTO {
  idBanner: number;
  name: string;
  description: string;
  image: string;
  url: string;
  startDate: Date;
  endDate: Date;
  status: 'A' | 'I';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
}

export interface NotificationTO {
  idRepository: number;
  idNotification: number;
  type: string;
  subCategory: string;
  description: string;
  title: string;
  unRead: boolean;
  startDate?: Date;
  endDate?: Date;
  status?: 'A' | 'I';
  lastUserModifier?: string;
  lastModification?: Date;
  creationUser?: string;
  creationDate?: Date;
}

export interface DiscountTO {
  idDiscount: number;
  name: string;
  description: string;
  category: string;
  subCategory: string;
  image: string;
  provider: string;
  url: string;
  status: 'A' | 'I';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
}

export interface CategorySubTO {
  id: number;
  name: string;
  description: string;
  parentId?: number;
  children?: CategorySubTO[];
}

export interface InsuranceTO {
  idInsurance: number;
  insuranceType: string;
  idInsuranceType: string;
  insuranceCarrier: string;
  policy: string;
  url: string;
  phones: string;
  coverage: string;
  startDate: Date;
  endDate: Date;
  contractPdf: string;
  individualCertificate: string;
  typePolicy: string;
  status: 'A' | 'I';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
}

export interface InsuranceEventualityTO {
  idEventualy: number;
  idInsurance: number;
  description: string;
  status: 'A' | 'I';
}

export interface InsurancePlanCoverageTO {
  idPlanCoverage: number;
  idInsurance: number;
  description: string;
  value: string;
  status: 'A' | 'I';
}

export interface ClientTO {
  idClient: number;
  name: string;
  rfc: string;
  contact: string;
  email: string;
  phone: string;
  status: 'A' | 'I';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
  dsLogo?: string;
}

export interface CompanyInformationTO {
  id: number;
  name: string;
  description: string;
  phone: string;
  email: string;
  address: string;
  website: string;
}
