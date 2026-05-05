import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageProvider } from '../storage/storage';
import { Observable } from 'rxjs';
import { PATH_USER, KEYS_STORAGE } from '../../environments/environments';

@Injectable()
export class RhProvider {
  constructor(
    public http: HttpClient,
    private storage_provider: StorageProvider
  ) {}

  getInfoActualPosition() {
    const idUser = this.storage_provider.getItem(KEYS_STORAGE.USER).id;
    const URL = `${PATH_USER.DOMAIN}/${PATH_USER.ACTUAL_POSITION}?idUser=${idUser}`;
    return this.http.get(URL).timeout(10000).catch(this.handleError);
  }

  getListCfdiByMount(mount: string, year: string) {
    const idUser = this.storage_provider.getItem(KEYS_STORAGE.USER).id;
    const URL = `${PATH_USER.DOMAIN}/${PATH_USER.CFDI}?idUser=${idUser}&mounth=${mount}&year=${year}`;
    return this.http.get(URL).timeout(5000).catch(this.handleError);
  }

  handleError(error: any) {
    return Observable.throw(error);
  }
}
