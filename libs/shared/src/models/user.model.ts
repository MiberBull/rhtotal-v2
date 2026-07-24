export type DchRole = 'DCH_SUPER_ADMIN' | 'DCH_ADMIN' | 'DCH_RRHH' | 'DCH_VIEWER';

export interface UserTO {
  id: number;
  idAccessPrivilege: number;
  email: string;
  userType: 'IN' | 'EX';
  userStatus: string;
  password: string;
  lastUserModifier: string;
  lastModification: Date;
  creationDate: Date;
  creationUser: string;
  level?: number;
  active?: boolean;
  nameRole?: DchRole;
  nameRol?: DchRole;
  role?: DchRole;
  tenantId?: string;
}

export interface LoginRequestTO {
  user: { email: string; password: string };
  block?: boolean;
}

export interface LoginResponseTO {
  message: string;
  block: boolean;
  flag: LoginFlag;
  user: UserTO;
  menu?: MenuItem[];
  token?: string;
  tenantId?: string;
}

export enum LoginFlag {
  OK = 0,
  INVALID_USER = 1,
  WRONG_PASSWORD = 2,
  BLOCKED = 3,
  NEW_USER = 4,
}

export interface MenuItem {
  id: number;
  name: string;
  icon: string;
  url: string;
  children?: MenuItem[];
}

export interface NewAccountTO {
  user: string;
  password: string;
  passwordConfirmed: string;
  code: string;
}

export interface ResetPasswordRequestTO {
  email: string;
  app: 'web' | 'mobile';
}

export interface ResetPasswordConfirmationTO {
  token: string;
  password: string;
  passwordConfirmed: string;
}
