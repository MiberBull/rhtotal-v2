import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';
import { SECRETS } from '../../environments/environments';

@Injectable()
export class CypherProvider {

  constructor(public http: HttpClient) {
    console.log('Hello CypherProvider Provider');
  }

  encryptString(word:string) {
    return CryptoJS.AES.encrypt(word, SECRETS.AES_PASSWORD_SECRET).toString();
  }

  decryptString( ciphertext:string ) {
    return CryptoJS.AES.decrypt( ciphertext, SECRETS.AES_PASSWORD_SECRET );
  }

}
