import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface MenuItem {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-rh',
  templateUrl: './rh.page.html',
  styleUrls: ['./rh.page.scss'],
  standalone: false,
})
export class RhPage {
  menuItems: MenuItem[] = [
    { icon: 'business-outline', label: 'Posicion Actual', route: '/rh/position' },
    { icon: 'cash-outline', label: 'Pagos', route: '/rh/payments' },
    { icon: 'document-text-outline', label: 'Recibos de Nomina', route: '/rh/payroll' },
    { icon: 'ribbon-outline', label: 'Constancias', route: '/rh/constancias' },
    { icon: 'create-outline', label: 'Solicitar Cambio de Datos', route: '/rh/change-request' },
  ];

  constructor(private router: Router) {}

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
