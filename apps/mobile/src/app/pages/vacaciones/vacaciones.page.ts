import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface VacationRequestTO {
  idRequest: number;
  dtStartDate: string;
  dtEndDate: string;
  nbDaysRequested: number;
  dsNotes: string;
  dsStatus: 'PENDIENTE' | 'APROBADA' | 'RECHAZADA' | 'CANCELADA';
  dsRejectionReason?: string;
}

@Component({
  selector: 'app-vacaciones',
  templateUrl: './vacaciones.page.html',
  standalone: false,
})
export class VacacionesPage implements OnInit {
  segmento: 'solicitudes' | 'nueva' = 'solicitudes';
  solicitudes: VacationRequestTO[] = [];
  loading = false;
  enviando = false;

  form = {
    fechaInicio: '',
    fechaFin: '',
    motivo: '',
  };

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadSolicitudes();
  }

  loadSolicitudes(event?: any): void {
    this.loading = true;
    const idEmpleado = this.authService.currentUser?.id;
    this.http
      .get<
        VacationRequestTO[]
      >(`${environment.gatewayUrl}/api/hr/vacation/request/employee/${idEmpleado}`)
      .subscribe({
        next: (data) => {
          this.solicitudes = data || [];
          this.loading = false;
          if (event) event.target.complete();
        },
        error: () => {
          this.loading = false;
          if (event) event.target.complete();
        },
      });
  }

  enviarSolicitud(): void {
    if (!this.form.fechaInicio || !this.form.fechaFin) {
      this.showToast('Selecciona las fechas');
      return;
    }
    this.enviando = true;
    const payload = {
      idEmployee: this.authService.currentUser?.id,
      dtStartDate: this.form.fechaInicio,
      dtEndDate: this.form.fechaFin,
      dsNotes: this.form.motivo,
    };
    this.http.post(`${environment.gatewayUrl}/api/hr/vacation/request`, payload).subscribe({
      next: () => {
        this.enviando = false;
        this.form = { fechaInicio: '', fechaFin: '', motivo: '' };
        this.segmento = 'solicitudes';
        this.loadSolicitudes();
        this.showToast('Solicitud enviada');
      },
      error: () => {
        this.enviando = false;
        this.showToast('Error al enviar solicitud');
      },
    });
  }

  getColor(estatus: string): string {
    const map: Record<string, string> = {
      APROBADA: 'success',
      RECHAZADA: 'danger',
      CANCELADA: 'danger',
      PENDIENTE: 'warning',
    };
    return map[estatus] || 'medium';
  }

  async showToast(msg: string): Promise<void> {
    const t = await this.toastCtrl.create({ message: msg, duration: 2500, position: 'bottom' });
    t.present();
  }
}
