import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface Ticket {
  idTicket: number;
  asunto: string;
  descripcion: string;
  categoria: string;
  estatus: 'ABIERTO' | 'EN_PROCESO' | 'CERRADO';
  fechaCreacion: string;
  fechaActualizacion?: string;
  respuesta?: string;
}

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.page.html',
  standalone: false,
})
export class TicketsPage implements OnInit {
  segmento: 'tickets' | 'nuevo' = 'tickets';
  tickets: Ticket[] = [];
  loading = false;
  enviando = false;

  categorias = ['IT', 'NOMINA', 'BENEFICIOS', 'REPSE', 'GENERAL'];

  form = {
    categoria: '',
    asunto: '',
    descripcion: '',
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
      .get<Ticket[]>(`${environment.gatewayUrl}/api/hr/tickets/byEmpleado/${idEmpleado}`)
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
    if (!this.form.categoria || !this.form.asunto || !this.form.descripcion) {
      this.showToast('Completa todos los campos');
      return;
    }
    this.enviando = true;
    const payload = {
      idEmpleado: this.authService.currentUser?.id,
      categoria: this.form.categoria,
      asunto: this.form.asunto,
      descripcion: this.form.descripcion,
    };
    this.http.post(`${environment.gatewayUrl}/api/hr/tickets/crear`, payload).subscribe({
      next: () => {
        this.enviando = false;
        this.form = { categoria: '', asunto: '', descripcion: '' };
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
      CERRADO: 'success',
      EN_PROCESO: 'warning',
      ABIERTO: 'primary',
    };
    return map[estatus] || 'medium';
  }

  async showToast(msg: string): Promise<void> {
    const t = await this.toastCtrl.create({ message: msg, duration: 2500, position: 'bottom' });
    t.present();
  }
}
