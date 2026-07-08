import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_HR } from '../../../environments/environment';
import { TicketCommentTO } from '../../models/hr.model';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  constructor(private http: HttpClient) {}

  getByStatus(status: string) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_BY_STATUS}/${status}`;
    return this.http.get(URL);
  }

  updateStatus(id: number, newStatus: string, assignedTo: string) {
    const params = new HttpParams().set('newStatus', newStatus).set('assignedTo', assignedTo);
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_STATUS}/${id}/status`;
    return this.http.put(URL, null, { params });
  }

  getComments(ticketId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_COMMENTS}/${ticketId}/comments`;
    return this.http.get(URL);
  }

  addComment(comment: TicketCommentTO) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_COMMENT_ADD}`;
    return this.http.post(URL, comment);
  }
}
