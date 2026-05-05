import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';

@Injectable({ providedIn: 'root' })
export class CryptoService {
  private secretKey = '';

  configure(secret: string): void {
    this.secretKey = secret;
  }

  encrypt(plainText: string): string {
    if (!this.secretKey) {
      throw new Error('CryptoService: secret key not configured. Call configure() first.');
    }
    return CryptoJS.AES.encrypt(plainText, this.secretKey).toString();
  }

  decrypt(cipherText: string): string {
    if (!this.secretKey) {
      throw new Error('CryptoService: secret key not configured. Call configure() first.');
    }
    const bytes = CryptoJS.AES.decrypt(cipherText, this.secretKey);
    return bytes.toString(CryptoJS.enc.Utf8);
  }
}
