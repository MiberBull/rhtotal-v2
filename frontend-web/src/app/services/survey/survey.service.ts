import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PATH_HR } from '../../../environments/environment';
import { SurveyTO } from '../../models/hr.model';

@Injectable({
  providedIn: 'root',
})
export class SurveyService {
  constructor(private http: HttpClient) {}

  getAll() {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_LIST}`;
    return this.http.get(URL);
  }

  save(survey: SurveyTO) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_SAVE}`;
    return this.http.post(URL, survey);
  }

  publish(id: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_PUBLISH}/${id}/publish`;
    return this.http.put(URL, null);
  }

  close(id: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_CLOSE}/${id}/close`;
    return this.http.put(URL, null);
  }

  getResults(id: number) {
    const URL = `${PATH_HR.DOMAIN}/${PATH_HR.SURVEY_RESULTS}/${id}/results`;
    return this.http.get(URL);
  }
}
