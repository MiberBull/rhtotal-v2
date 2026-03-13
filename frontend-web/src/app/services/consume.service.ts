import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ConsumeService {

  private _url: string = 'http://localhost:8000/api/application/project/project/find';
  constructor(private _http: HttpClient) { }

  getUser():Observable<any> {
    return this._http
        .get(this._url, {responseType: 'json'})
        .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
      console.log(error);
      return Observable.throw(error.json() || 'Server error');
  }
}
