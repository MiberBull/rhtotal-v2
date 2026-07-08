import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { SurveyService } from '../../services/survey/survey.service';
import { DataService } from '../../services/data.service';
import { BREADCRUMB } from '../../../environments/environment';
import { SurveyTO } from '../../models/hr.model';

@Component({
  selector: 'app-encuestas',
  templateUrl: './encuestas.component.html',
  styleUrls: ['./encuestas.component.css'],
})
export class EncuestasComponent implements OnInit {
  surveys: SurveyTO[] = [];
  showCreateForm = false;
  selectedSurveyResults: any[] = [];
  showResults = false;
  selectedSurveyTitle = '';

  newSurvey: SurveyTO = this.emptyForm();

  readonly SURVEY_TYPES = ['CLIMA_LABORAL', 'SATISFACCION', 'DESEMPENO', 'GENERAL'];

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _router: Router,
    private _survey: SurveyService,
    private _data: DataService
  ) {
    this._breadcrumb.setRouteText({ title: BREADCRUMB.ENCUESTAS, arrow: false });
    this._toolbar.setVisible(this._router.url.toString());
  }

  ngOnInit() {
    this.loadSurveys();
  }

  private emptyForm(): SurveyTO {
    return {
      dsTitle: '',
      dsDescription: '',
      dsType: '',
      dtStartDate: '',
      dtEndDate: '',
      fgAnonymous: false,
    };
  }

  loadSurveys() {
    this._data.setIsLoadingEvent(true);
    this._survey.getAll().subscribe(
      (resp: SurveyTO[]) => {
        this._data.setIsLoadingEvent(false);
        this.surveys = resp || [];
      },
      () => this._data.setIsLoadingEvent(false)
    );
  }

  openCreateForm() {
    this.newSurvey = this.emptyForm();
    this.showCreateForm = true;
    this.showResults = false;
  }

  cancelCreate() {
    this.showCreateForm = false;
  }

  saveSurvey() {
    if (
      !this.newSurvey.dsTitle ||
      !this.newSurvey.dsType ||
      !this.newSurvey.dtStartDate ||
      !this.newSurvey.dtEndDate
    ) {
      this._data.setGeneralNotificationMessage('Favor de validar campos requeridos(*)');
      return;
    }
    this._survey.save(this.newSurvey).subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Encuesta creada correctamente');
        this.showCreateForm = false;
        this.loadSurveys();
      },
      (err) => console.error('Error al guardar encuesta', err)
    );
  }

  publish(survey: SurveyTO) {
    this._survey.publish(survey.id).subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Encuesta publicada');
        this.loadSurveys();
      },
      (err) => console.error('Error al publicar encuesta', err)
    );
  }

  close(survey: SurveyTO) {
    this._survey.close(survey.id).subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Encuesta cerrada');
        this.loadSurveys();
      },
      (err) => console.error('Error al cerrar encuesta', err)
    );
  }

  viewResults(survey: SurveyTO) {
    this.showCreateForm = false;
    this.selectedSurveyTitle = survey.dsTitle;
    this._survey.getResults(survey.id).subscribe(
      (resp: any[]) => {
        this.selectedSurveyResults = resp || [];
        this.showResults = true;
      },
      (err) => console.error('Error cargando resultados', err)
    );
  }

  closeResults() {
    this.showResults = false;
    this.selectedSurveyResults = [];
  }

  statusClass(status: string): string {
    const map = {
      BORRADOR: 'dch-badge-cerrado',
      PUBLICADA: 'dch-badge-aprobada',
      CERRADA: 'dch-badge-rechazada',
    };
    return map[status] || '';
  }
}
