import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginTO, NewAccountTO } from '../../models/user.model';
import { Observable } from 'rxjs';
import { PATH_SECURITY, PATH_USER } from '../../environments/environments';
import { of } from 'rxjs/observable/of';
import { CypherProvider } from '../cypher/cypher';
import { resetPasswordTO } from '../../models/employee.compl';
import { TenantProvider } from '../tenant/tenant';

@Injectable()
export class LoginProvider {
  slides = [
    {
      title: 'Explora',
      paragraph: 'Una nueva forma de relacionarte con el mundo laboral.',
      next: 'Saltar introducción',
    },
    {
      title: 'Construye',
      paragraph:
        'Tu perfil y DNA profesional que te catapultará a las mejores oportunidades profesionales.',
      next: 'Saltar introducción',
    },
    {
      title: 'Consulta',
      paragraph: 'Tu información de ingresos y recursos humanos.',
      next: 'Saltar introducción',
    },
    {
      title: 'Descubre',
      paragraph: 'Los beneficios que hemos personalizado para cada perfil profesional.',
      next: 'Saltar introducción',
    },
    {
      title: 'Accesa',
      paragraph: 'A los servicios y beneficios que tu empresa tiene para ti.',
      next: 'Saltar introducción',
    },
  ];

  outputPayload = {
    title: 'Código de confirmación',
    description:
      'Un código de confirmación ha sido enviado a tu Email. Ingrésalo para crear tu cuenta exitosamente.',
  };

  private numberIntent: number;

  constructor(
    public http: HttpClient,
    private crypt: CypherProvider,
    private tenant: TenantProvider
  ) {
    console.log('Hello LoginProvider Provider');
  }

  /**
   * Login
   * @param user
   */
  login(loginTO: LoginTO) {
    loginTO.user.password = this.crypt.encryptString(loginTO.user.password);
    const URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.LOGIN}`;
    const headers = new HttpHeaders(this.tenant.getTenantHeaders());
    return this.http.post(URL, loginTO, { headers }).timeout(15000).catch(this.handleError);
  }

  persistTenantId(tenantId: string): void {
    localStorage.setItem('tenantId', tenantId || 'dchkw');
  }

  /**
   * Obtiene los parametros
   * @param paramter
   */
  getParameter(paramter: string) {
    const URL = `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.PARAMETER}?name=${paramter}`;
    return this.http.post(URL, null).catch(this.handleError);
  }

  /**
   * Bloqueo de usuario por pasar los intentos
   * @param userBlock
   */
  blockingUserByIntents(userBlock: LoginTO) {
    return this.http
      .post(`${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.LOGIN}`, userBlock)
      .catch(this.handleError);
  }

  /**
   * Regresa el numero de intentos
   */
  getNumberIntent() {
    return this.numberIntent;
  }

  /**
   * Crea una nueva cuenta
   * @param newAccount
   */
  createAccount(newAccount: NewAccountTO) {
    return this.http
      .post(`${PATH_USER.DOMAIN}/${PATH_USER.CREATE}`, newAccount)
      .catch(this.handleError);
  }
  //

  confirmCode(code: NewAccountTO) {
    return this.http
      .post(`${PATH_USER.DOMAIN}/${PATH_USER.CODE}`, { code: code.code, user: code.user })
      .catch(this.handleError);
  }
  //

  //
  /**
   * Coloca el numero de intentos
   * @param num
   */
  setNumberIntent(num: number) {
    this.numberIntent = num;
  }

  /**
   * Regresa el titulo y la descripcion
   * para cada slide
   */
  getInfoOnboarding() {
    return of(this.slides);
  }

  /**
   * Cachar los errores
   * @param error
   */
  handleError(error: any) {
    console.log(error);
    return Observable.throw(error);
  }

  getUserResetPass(body: resetPasswordTO) {
    return this.http
      .post(`${PATH_SECURITY.DOMAIN}/${PATH_USER.GET_USER}`, body)
      .catch(this.handleError);
  }
}
