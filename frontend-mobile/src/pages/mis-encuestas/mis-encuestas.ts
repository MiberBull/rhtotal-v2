import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { HrEmployeeProvider } from '../../providers/hr-employee/hr-employee';
import { StorageProvider } from '../../providers/storage/storage';
import { KEYS_STORAGE, MSG_DIALOG } from '../../environments/environments';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-mis-encuestas',
  templateUrl: 'mis-encuestas.html',
})
export class MisEncuestasPage {
  surveys: any[] = [];
  selectedSurvey: any = null;
  questions: any[] = [];
  answers: { [key: number]: string } = {};
  employeeId: number;
  userEmail: string;
  submitted = false;

  constructor(
    public navCtrl: NavController,
    private hr: HrEmployeeProvider,
    private storage: StorageProvider,
    private events: EventsManagerProvider
  ) {
    const user = this.storage.getItem(KEYS_STORAGE.USER);
    this.employeeId = user ? user.idEmployee || user.id : null;
    this.userEmail = user ? user.email : 'employee';
  }

  ionViewDidLoad() {
    this.loadSurveys();
  }

  loadSurveys() {
    this.events.setIsLoadingEvent(true);
    this.hr.getSurveys().subscribe(
      (data: any) => {
        this.surveys = Array.isArray(data) ? data : data.data || [];
        this.events.setIsLoadingEvent(false);
      },
      () => this.events.setIsLoadingEvent(false)
    );
  }

  openSurvey(survey: any) {
    this.selectedSurvey = survey;
    this.answers = {};
    this.submitted = false;
    this.events.setIsLoadingEvent(true);
    this.hr.getQuestions(survey.id).subscribe(
      (data: any) => {
        this.questions = Array.isArray(data) ? data : data.data || [];
        this.events.setIsLoadingEvent(false);
      },
      () => this.events.setIsLoadingEvent(false)
    );
  }

  submit() {
    const unanswered = this.questions.filter((q) => !this.answers[q.id]);
    if (unanswered.length > 0) {
      this.showAlert('', 'Por favor responde todas las preguntas.');
      return;
    }
    const body = {
      idSurvey: this.selectedSurvey.id,
      idEmployee: this.employeeId,
      dsRespondedBy: this.selectedSurvey.fgAnonymous ? 'ANONIMO' : this.userEmail,
      answers: Object.entries(this.answers).map(([idQuestion, dsAnswer]) => ({
        idQuestion: Number(idQuestion),
        dsAnswer,
      })),
    };
    this.events.setIsLoadingEvent(true);
    this.hr.respondSurvey(body).subscribe(
      () => {
        this.submitted = true;
        this.events.setIsLoadingEvent(false);
      },
      () => {
        this.events.setIsLoadingEvent(false);
        this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
      }
    );
  }

  showAlert(title: string, msg: string) {
    const objMessage: MessageGeneral = { msg, title };
    this.events.setGeneralNotificationMessage(objMessage);
  }

  back() {
    if (this.selectedSurvey) {
      this.selectedSurvey = null;
      return;
    }
    this.navCtrl.pop();
  }
}
