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

  pushLogin:any;

  slides = [
    { title:'Explora', paragraph:'Una nueva forma de relacionarte con el mundo laboral.',next:'Saltar introducción'},
    { title:'Construye', paragraph:'Tu perfil y DNA profesional que te catapultará a las mejores oportunidades profesionales.',next:'Saltar introducción'},
    { title:'Consulta', paragraph:'Tu información de ingresos y recursos humanos.',next:'Saltar introducción'},
    { title:'Descubre', paragraph:'Los beneficios que hemos personalizado para cada perfil profesional.',next:'Saltar introducción'},
    { title:'Accesa', paragraph:'A los servicios y beneficios que tu empresa tiene para ti.',next:'Saltar introducción'},
  ];

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    private storage_provider: StorageProvider,
    private events_manager: EventsManagerProvider) {
    this.pushLogin = LoginPage;
  }

  ionViewDidLoad() {
    this.events_manager.setIsLogged(false);
  }

  goLogin() {
    this.storage_provider.saveOnboarding();
    this.navCtrl.setRoot( LoginPage );
  }

  slideChanged() {
    if(this.slideElement.isEnd()) {
      this.goLogin();
    }
  }

}
