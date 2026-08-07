import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface TicketTO {
  idTicket: number;
  dsSubject: string;
  dsDescription: string;
  dsCategory: string;
  dsStatus: 'ABIERTO' | 'EN_PROGRESO' | 'RESUELTO' | 'CERRADO';
}

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.page.html',
  standalone: false,
})
export class TicketsPage implements OnInit {
  segmento: 'tickets' | 'nuevo' = 'tickets';
  tickets: TicketTO[] = [];
  loading = false;
  enviando = false;

  categorias = ['IT', 'NOMINA', 'BENEFICIOS', 'REPSE', 'GENERAL'];

  form = {
    dsCategory: '',
    dsSubject: '',
    dsDescription: '',
  };

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(event?: any): void {
    this.loading = true;
    const idEmpleado = this.authService.currentUser?.id;
    this.http
      .get<TicketTO[]>(`${environment.gatewayUrl}/api/hr/ticket/employee/${idEmpleado}`)
      .subscribe({
        next: (data) => {
          this.tickets = data || [];
          this.loading = false;
          if (event) event.target.complete();
        },
        error: () => {
          this.loading = false;
          if (event) event.target.complete();
        },
      });
  }

  crearTicket(): void {
    if (!this.form.dsCategory || !this.form.dsSubject || !this.form.dsDescription) {
      this.showToast('Completa todos los campos');
      return;
    }
    this.enviando = true;
    const payload = {
      idEmployee: this.authService.currentUser?.id,
      dsCategory: this.form.dsCategory,
      dsSubject: this.form.dsSubject,
      dsDescription: this.form.dsDescription,
    };
    this.http.post(`${environment.gatewayUrl}/api/hr/ticket`, payload).subscribe({
      next: () => {
        this.enviando = false;
        this.form = { dsCategory: '', dsSubject: '', dsDescription: '' };
        this.segmento = 'tickets';
        this.loadTickets();
        this.showToast('Ticket creado exitosamente');
      },
      error: () => {
        this.enviando = false;
        this.showToast('Error al crear ticket');
      },
    });
  }

  getColor(estatus: string): string {
    const map: Record<string, string> = {
      RESUELTO: 'success',
      CERRADO: 'success',
      EN_PROGRESO: 'warning',
      ABIERTO: 'primary',
    };
    return map[estatus] || 'medium';
  }

  async showToast(msg: string): Promise<void> {
    const t = await this.toastCtrl.create({ message: msg, duration: 2500, position: 'bottom' });
    t.present();
  }
}
