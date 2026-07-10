import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PATH_APLICATION, KEYS_STORAGE } from '../../environments/environments';
import { StorageProvider } from '../storage/storage';
import { UserTO } from '../../models/user.model';
import { of } from 'rxjs/observable/of';

/*
  Generated class for the ImagesApiProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ImagesApiProvider {
  constructor(
    public http: HttpClient,
    private storageProvider: StorageProvider
  ) {}

  getImagesForHome() {
    const user: UserTO = this.storageProvider.getItem(KEYS_STORAGE.USER);

    if (!user) {
      user.id = 0;
    }
    if (user.userType == 'EX') {
      user.id = 0;
    }

    const URL = `${PATH_APLICATION.DOMAIN}banner/showbanners?user=${user.id}`;
    return this.http
      .get(URL)
      .map((resp: string[]) => {
        if (resp && resp.length > 0) {
          return resp;
        } else {
          return ['assets/imgs/dch-total-slogan.svg'];
        }
      })
      .timeout(20000);
  }

  getImagesForHomeReload() {
    const user: UserTO = this.storageProvider.getItem(KEYS_STORAGE.USER);
    const URL = `${PATH_APLICATION.DOMAIN}banner/showbanners?user=${user.id}`;
    return this.http.get(URL).map((resp: string[]) => {
      if (resp && resp.length > 0) {
        return resp;
      } else {
        return ['assets/imgs/dch-total-slogan.svg'];
      }
    });
  }
}
