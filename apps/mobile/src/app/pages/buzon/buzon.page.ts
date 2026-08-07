import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface BuzonReporte {
  idReporte?: number;
  dsCategoria: string;
  dsDescripcion: string;
  dsEstatus?: string;
  dtFecha?: string;
}

@Component({
  selector: 'app-buzon',
  templateUrl: './buzon.page.html',
  standalone: false,
})
export class BuzonPage implements OnInit {
  segmento: 'mis-reportes' | 'nuevo' = 'mis-reportes';
  reportes: BuzonReporte[] = [];
  loading = false;
  enviando = false;

  categorias = ['QUEJA', 'DENUNCIA', 'SUGERENCIA', 'MENSAJE_DIRECCION'];

  form = {
    dsCategoria: '',
    dsDescripcion: '',
    fgAnonimo: true,
    dsNombreReportante: '',
  };

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadReportes();
  }

  loadReportes(event?: any): void {
    this.loading = true;
    this.http.get<BuzonReporte[]>(`${environment.gatewayUrl}/api/hr/buzon/list`).subscribe({
      next: (data) => {
        this.reportes = data || [];
        this.loading = false;
        if (event) event.target.complete();
      },
      error: () => {
        this.loading = false;
        this.reportes = [];
        if (event) event.target.complete();
      },
    });
  }

  enviarReporte(): void {
    if (!this.form.dsCategoria || !this.form.dsDescripcion.trim()) {
      this.showToast('Completa la categoría y la descripción');
      return;
    }
    this.enviando = true;

    const payload: any = {
      idUsuario: this.authService.currentUser?.id,
      dsCategoria: this.form.dsCategoria,
      dsDescripcion: this.form.dsDescripcion,
      fgAnonimo: this.form.fgAnonimo,
    };

    if (!this.form.fgAnonimo && this.form.dsNombreReportante.trim()) {
      payload['dsNombreReportante'] = this.form.dsNombreReportante;
    }

    this.http.post(`${environment.gatewayUrl}/api/hr/buzon/submit`, payload).subscribe({
      next: () => {
        this.enviando = false;
        this.form = { dsCategoria: '', dsDescripcion: '', fgAnonimo: true, dsNombreReportante: '' };
        this.segmento = 'mis-reportes';
        this.loadReportes();
        this.showToast('Reporte enviado correctamente');
      },
      error: () => {
        this.enviando = false;
        this.showToast('Error al enviar el reporte');
      },
    });
  }

  getColorEstatus(estatus?: string): string {
    const map: Record<string, string> = {
      ABIERTO: 'warning',
      EN_PROCESO: 'primary',
      RESUELTO: 'success',
      CERRADO: 'medium',
    };
    return map[estatus ?? ''] || 'medium';
  }

  async showToast(msg: string): Promise<void> {
    const t = await this.toastCtrl.create({ message: msg, duration: 2500, position: 'bottom' });
    t.present();
  }
}
