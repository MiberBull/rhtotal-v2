import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface CvInfo {
  fileName: string;
  fileUrl: string;
  uploadDate: string;
}

@Component({
  selector: 'app-my-cv',
  templateUrl: './my-cv.page.html',
  styleUrls: ['./my-cv.page.scss'],
  standalone: false,
})
export class MyCvPage implements OnInit {
  cvInfo: CvInfo | null = null;
  isLoading = false;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadCv();
  }

  async loadCv(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getMyCv/${userId}`;

    this.http.get<any>(url).subscribe({
      next: (data) => {
        if (data && data.fileName) {
          this.cvInfo = {
            fileName: data.fileName,
            fileUrl: data.fileUrl || '',
            uploadDate: data.uploadDate || '',
          };
        } else {
          this.cvInfo = null;
        }
        loading.dismiss();
      },
      error: () => {
        this.cvInfo = null;
        loading.dismiss();
      },
    });
  }

  async onFileSelected(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    if (file.type !== 'application/pdf') {
      this.showToast('Solo se permiten archivos PDF', 'warning');
      return;
    }

    const loading = await this.loadingCtrl.create({ message: 'Subiendo CV...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/uploadCv`;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('idUser', String(userId));

    this.http.post<any>(url, formData).subscribe({
      next: () => {
        loading.dismiss();
        this.showToast('CV subido correctamente', 'success');
        this.loadCv();
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al subir el CV', 'danger');
      },
    });
  }

  async deleteCv(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Eliminando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/deleteCv/${userId}`;

    this.http.delete<any>(url).subscribe({
      next: () => {
        this.cvInfo = null;
        loading.dismiss();
        this.showToast('CV eliminado', 'success');
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al eliminar el CV', 'danger');
      },
    });
  }

  downloadCv(): void {
    if (this.cvInfo?.fileUrl) {
      window.open(this.cvInfo.fileUrl, '_blank');
    }
  }

  private async showToast(message: string, color: string): Promise<void> {
    const toast = await this.toastCtrl.create({
      message,
      color,
      duration: 2500,
      position: 'bottom',
    });
    await toast.present();
  }
}
