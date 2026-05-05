import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface MenuItem {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-my-data',
  templateUrl: './my-data.page.html',
  styleUrls: ['./my-data.page.scss'],
  standalone: false,
})
export class MyDataPage {
  menuItems: MenuItem[] = [
    { icon: 'person-outline', label: 'Informacion Personal', route: '/my-data/personal-info' },
    { icon: 'home-outline', label: 'Domicilio', route: '/my-data/address' },
    { icon: 'globe-outline', label: 'Redes Sociales', route: '/my-data/social-networks' },
  ];

  constructor(private router: Router) {}

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
