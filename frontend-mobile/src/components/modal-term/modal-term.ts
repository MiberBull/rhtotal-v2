import { Component } from '@angular/core';
import { Platform, NavParams, ViewController } from 'ionic-angular';

/**
 * Generated class for the ModalTermComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'modal-term',
  templateUrl: 'modal-term.html'
})
export class ModalTermComponent {

  text: string;
  character;

  characters = [
    {
      name: 'Gollum',
      quote: 'Sneaky little hobbitses!',
      image: '',
      items: [
        { title: 'Race', note: 'Hobbit' },
        { title: 'Culture', note: 'River Folk' },
        { title: 'Alter Ego', note: 'Smeagol' }
      ]
    },
    {
      name: 'Frodo',
      quote: 'Go back, Sam! I\'m going to Mordor alone!',
      image: 'assets/img/avatar-frodo.jpg',
      items: [
        { title: 'Race', note: 'Hobbit' },
        { title: 'Culture', note: 'Shire Folk' },
        { title: 'Weapon', note: 'Sting' }
      ]
    },
    {
      name: 'Samwise Gamgee',
      quote: 'What we need is a few good taters.',
      image: 'assets/img/avatar-samwise.jpg',
      items: [
        { title: 'Race', note: 'Hobbit' },
        { title: 'Culture', note: 'Shire Folk' },
        { title: 'Nickname', note: 'Sam' }
      ]
    }
  ];
  

  constructor(
    public platform: Platform,
    public params: NavParams,
    public viewCtrl: ViewController
  ) {
    this.text = 'Hello World';
    this.character = this.characters[0];
  }

  dismiss() {
    this.viewCtrl.dismiss();
  }

}
