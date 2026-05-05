import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

interface SocialField {
  key: string;
  label: string;
  icon: string;
}

@Component({
  selector: 'app-social-networks',
  templateUrl: './social-networks.page.html',
  styleUrls: ['./social-networks.page.scss'],
  standalone: false,
})
export class SocialNetworksPage implements OnInit {
  form!: FormGroup;

  socialFields: SocialField[] = [
    { key: 'facebook', label: 'Facebook', icon: 'logo-facebook' },
    { key: 'twitter', label: 'Twitter', icon: 'logo-twitter' },
    { key: 'linkedin', label: 'LinkedIn', icon: 'logo-linkedin' },
    { key: 'instagram', label: 'Instagram', icon: 'logo-instagram' },
    { key: 'github', label: 'GitHub', icon: 'logo-github' },
    { key: 'website', label: 'Sitio Web', icon: 'globe-outline' },
    { key: 'skype', label: 'Skype', icon: 'logo-skype' },
  ];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      facebook: [''],
      twitter: [''],
      linkedin: [''],
      instagram: [''],
      github: [''],
      website: [''],
      skype: [''],
    });

    this.loadData();
  }

  async loadData(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getSocialNetwork/${userId}`;

    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.form.patchValue({
          facebook: data.facebook || '',
          twitter: data.twitter || '',
          linkedin: data.linkedin || '',
          instagram: data.instagram || '',
          github: data.github || '',
          website: data.website || '',
          skype: data.skype || '',
        });
        loading.dismiss();
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al cargar redes sociales', 'danger');
      },
    });
  }

  async save(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Guardando...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/saveOrUpdateSocialNetwork`;
    const payload = { ...this.form.value, idUser: userId };

    this.http.post<any>(url, payload).subscribe({
      next: () => {
        loading.dismiss();
        this.showToast('Redes sociales guardadas correctamente', 'success');
      },
      error: () => {
        loading.dismiss();
        this.showToast('Error al guardar redes sociales', 'danger');
      },
    });
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
