import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface Comunicado {
  idComunicado: number;
  titulo: string;
  contenido: string;
  fechaPublicacion: string;
  estado: string;
  leido: boolean;
  fechaLectura?: string;
}

@Component({
  selector: 'app-comunicados',
  templateUrl: './comunicados.page.html',
  standalone: false,
})
export class ComunicadosPage implements OnInit {
  comunicados: Comunicado[] = [];
  selected: Comunicado | null = null;
  loading = false;
  marking = false;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.load();
  }

  async load(event?: any): Promise<void> {
    const loader = await this.loadingCtrl.create({ message: 'Cargando...' });
    if (!event) await loader.present();

    const userId = this.authService.currentUser?.id;
    this.http
      .get<
        Comunicado[]
      >(`${environment.gatewayUrl}/api/application/comunicado/list?idUser=${userId}`)
      .subscribe({
        next: (data) => {
          this.comunicados = data || [];
          loader.dismiss();
          if (event) event.target.complete();
        },
        error: () => {
          loader.dismiss();
          if (event) event.target.complete();
          this.showToast('Error al cargar comunicados');
        },
      });
  }

  verDetalle(c: Comunicado): void {
    this.selected = c;
    if (!c.leido) {
      this.marcarLeido(c, false);
    }
  }

  volver(): void {
    this.selected = null;
  }

  marcarLeido(c: Comunicado, showToast = true): void {
    if (this.marking) return;
    this.marking = true;
    const userId = this.authService.currentUser?.id;
    this.http
      .post(`${environment.gatewayUrl}/api/application/comunicado/${c.idComunicado}/leer`, {
        idUsuario: userId,
      })
      .subscribe({
        next: () => {
          c.leido = true;
          this.marking = false;
          if (showToast) this.showToast('Marcado como leído');
        },
        error: () => {
          this.marking = false;
        },
      });
  }

  async showToast(msg: string): Promise<void> {
    const toast = await this.toastCtrl.create({ message: msg, duration: 2000, position: 'bottom' });
    toast.present();
  }
}
