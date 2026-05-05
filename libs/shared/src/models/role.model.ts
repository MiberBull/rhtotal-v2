export interface RoleTO {
  idRole: number;
  name: string;
  description: string;
  permissions: RolePermission[];
  status: 'A' | 'I';
  lastUserModifier: string;
  lastModification: Date;
  creationUser: string;
  creationDate: Date;
}

export interface RolePermission {
  id: number;
  name: string;
  icon: string;
  url: string;
  read: boolean;
  write: boolean;
  children?: RolePermission[];
}
