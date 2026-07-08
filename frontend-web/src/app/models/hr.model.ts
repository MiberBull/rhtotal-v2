export interface VacationRequestTO {
  id: number;
  idEmployee: number;
  dtStartDate: string;
  dtEndDate: string;
  nuDays: number;
  dsNotes: string;
  dsStatus: string; // PENDIENTE | APROBADA | RECHAZADA
  approvedBy?: string;
  dsRejectionReason?: string;
}

export interface VacationBalanceTO {
  idEmployee: number;
  nuEarnedDays: number;
  nuUsedDays: number;
  nuAvailableDays: number;
  nuYear: number;
}

export interface IncidentTO {
  id?: number;
  idEmployee: number;
  dsType: string; // FALTA | RETARDO | PERMISO | INCAPACIDAD | CAMBIO_TURNO
  dtIncidentDate: string;
  dtEndDate?: string;
  dsNotes?: string;
  dsStatus?: string;
  idTenant?: number;
}

export interface TicketTO {
  id?: number;
  idEmployee: number;
  dsSubject: string;
  dsCategory: string;
  dsPriority: string; // BAJA | MEDIA | ALTA | CRITICA
  dsStatus: string; // ABIERTO | EN_PROCESO | CERRADO
  assignedTo?: string;
  idTenant?: number;
}

export interface TicketCommentTO {
  id?: number;
  idTicket: number;
  dsAuthor: string;
  dsContent: string;
  dtCreated?: string;
}

export interface SurveyTO {
  id?: number;
  dsTitle: string;
  dsDescription?: string;
  dsType: string;
  dtStartDate: string;
  dtEndDate: string;
  fgAnonymous: boolean;
  dsStatus?: string; // BORRADOR | PUBLICADA | CERRADA
  idTenant?: number;
}
