export interface PagedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  page: number;
  size: number;
}

export interface PagedRequest {
  page: number;
  size: number;
  search?: string;
  sort?: string;
  direction?: 'ASC' | 'DESC';
}

export interface ApiResponse<T> {
  data: T;
  message?: string;
  status?: number;
}

export interface CountRowResponse {
  count: number;
}

export interface StatusOption {
  id: 'A' | 'I';
  value: string;
}

export interface GenderOption {
  id: 'M' | 'F';
  value: string;
}

export const STATUS_OPTIONS: StatusOption[] = [
  { id: 'A', value: 'Activo' },
  { id: 'I', value: 'Inactivo' },
];

export const GENDER_OPTIONS: GenderOption[] = [
  { id: 'M', value: 'Masculino' },
  { id: 'F', value: 'Femenino' },
];
