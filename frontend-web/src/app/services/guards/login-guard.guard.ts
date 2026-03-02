import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginService } from '../login/login.service';
import { routesWeb } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(
    private _loginService: LoginService,
    private route: Router
  ){}

  /**
   * Validata la sesion
   */
  canActivate(): boolean {
    if( !this._loginService.isLogged ){
      this.route.navigateByUrl(routesWeb.ROOT);
      return false;
    }
    return true;        
  }
}
