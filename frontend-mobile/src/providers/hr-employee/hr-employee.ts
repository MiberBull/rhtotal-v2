import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PATH_HR } from '../../environments/environments';

@Injectable()
export class HrEmployeeProvider {
  constructor(public http: HttpClient) {}

  // ── Vacaciones ────────────────────────────────────────────────────────────
  getBalance(employeeId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_BALANCE}/${employeeId}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  getMyRequests(employeeId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_MY_REQUESTS}/${employeeId}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  requestVacation(body: any) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.VACATION_REQUEST}`;
    return this.http.post(URL, body).timeout(15000).catch(this.handleError);
  }

  // ── Tickets ───────────────────────────────────────────────────────────────
  getMyTickets(employeeId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_MY_TICKETS}/${employeeId}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  createTicket(ticket: any) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_SAVE}`;
    return this.http.post(URL, ticket).timeout(15000).catch(this.handleError);
  }

  getComments(ticketId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_COMMENTS}/${ticketId}/comments`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  addComment(comment: any) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.TICKET_COMMENT_ADD}`;
    return this.http.post(URL, comment).timeout(15000).catch(this.handleError);
  }

  // ── Encuestas ─────────────────────────────────────────────────────────────
  getSurveys() {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_LIST}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  getQuestions(surveyId: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_QUESTIONS}/${surveyId}/questions`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  respondSurvey(response: any) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_RESPONSE}`;
    return this.http.post(URL, response).timeout(15000).catch(this.handleError);
  }

  handleError(error: any) {
    return Observable.throw(error);
  }
}
