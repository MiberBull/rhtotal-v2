import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, Slides } from 'ionic-angular';
import { LoginPage } from '../login/login';
import { StorageProvider } from '../../providers/storage/storage';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';

/**
 * Generated class for the OnboardingPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@Component({
  selector: 'page-onboarding',
  templateUrl: 'onboarding.html',
})
export class OnboardingPage {
  @ViewChild(Slides) slideElement: Slides;

  pushLogin: any;

  slides = [
    {
      title: 'Bienvenido',
      paragraph: 'DCH Total es tu plataforma integral de gestión de talento.',
      next: 'Saltar introducción',
    },
    {
      title: 'Tu expediente',
      paragraph:
        'Consulta y gestiona tu información laboral, contratos y documentos en un solo lugar.',
      next: 'Saltar introducción',
    },
    {
      title: 'Asistencia',
      paragraph: 'Registra tu entrada y salida con geolocalización desde cualquier lugar.',
      next: 'Saltar introducción',
    },
    {
      title: 'Beneficios',
      paragraph: 'Accede a los descuentos y beneficios exclusivos que DCH tiene para ti.',
      next: 'Saltar introducción',
    },
    {
      title: 'Soporte',
      paragraph: 'Abre tickets de ayuda y da seguimiento a tus solicitudes en tiempo real.',
      next: 'Comenzar',
    },
  ];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private storage_provider: StorageProvider,
    private events_manager: EventsManagerProvider
  ) {
    this.pushLogin = LoginPage;
  }

  ionViewDidLoad() {
    this.events_manager.setIsLogged(false);
  }

  goLogin() {
    this.storage_provider.saveOnboarding();
    this.navCtrl.setRoot(LoginPage);
  }

  slideChanged() {
    if (this.slideElement.isEnd()) {
      this.goLogin();
    }
  }
}
