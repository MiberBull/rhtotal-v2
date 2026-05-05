import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import CryptoJS from 'crypto-js';
import { environment } from '../../../environments/environment';

export interface UserTO {
  id: number;
  email: string;
  userType: 'IN' | 'EX';
  userStatus: string;
  level?: number;
}

export interface LoginResponse {
  message: string;
  block: boolean;
  flag: number; // 0=ok, 1=invalid user, 2=wrong pass, 3=blocked, 4=new
  user: UserTO;
  menu?: any[];
  token?: string;
}

const STORAGE_KEY = 'dch_mobile_auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isAuthenticated$ = new BehaviorSubject<boolean>(false);
  private _currentUser$ = new BehaviorSubject<UserTO | null>(null);
  private _token = '';

  isAuthenticated$ = this._isAuthenticated$.asObservable();
  currentUser$ = this._currentUser$.asObservable();

  get isAuthenticated(): boolean {
    return this._isAuthenticated$.value;
  }

  get currentUser(): UserTO | null {
    return this._currentUser$.value;
  }

  get token(): string {
    return this._token;
  }

  constructor(private http: HttpClient, private router: Router) {
    this.loadFromStorage();
  }

  encrypt(text: string): string {
    return CryptoJS.AES.encrypt(text, environment.aesSecret).toString();
  }

  async login(email: string, password: string): Promise<LoginResponse> {
    const encryptedPassword = this.encrypt(password);
    const url = `${environment.gatewayUrl}/api/security/login/loginMobile`;

    const response = await firstValueFrom(
      this.http.post<LoginResponse>(url, { user: email, password: encryptedPassword })
    );

    if (response.flag === 0) {
      this._currentUser$.next(response.user);
      this._isAuthenticated$.next(true);
      this._token = response.token ?? '';
      this.saveToStorage();
    }

    return response;
  }

  logout(): void {
    this._currentUser$.next(null);
    this._isAuthenticated$.next(false);
    this._token = '';
    localStorage.removeItem(STORAGE_KEY);
    this.router.navigate(['/login'], { replaceUrl: true });
  }

  private saveToStorage(): void {
    const data = {
      user: this._currentUser$.value,
      token: this._token,
    };
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  }

  private loadFromStorage(): void {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return;
    try {
      const data = JSON.parse(raw);
      if (data.user) {
        this._currentUser$.next(data.user);
        this._isAuthenticated$.next(true);
        this._token = data.token ?? '';
      }
    } catch {
      localStorage.removeItem(STORAGE_KEY);
    }
  }
}
