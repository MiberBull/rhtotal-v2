import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-quote-safe',
  templateUrl: './quote-safe.page.html',
  styleUrls: ['./quote-safe.page.scss'],
  standalone: false,
})
export class QuoteSafePage implements OnInit {
  quoteForm!: FormGroup;
  submitted = false;
  success = false;

  insuranceTypes = [
    { value: 'auto', label: 'Auto' },
    { value: 'vida', label: 'Vida' },
    { value: 'gastos', label: 'Gastos Medicos' },
  ];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.currentUser;
    this.quoteForm = this.fb.group({
      type: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: [user?.email || '', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      message: [''],
    });
  }

  async onSubmit(): Promise<void> {
    this.submitted = true;
    if (this.quoteForm.invalid) return;

    const loading = await this.loadingCtrl.create({ message: 'Enviando solicitud...' });
    await loading.present();

    const url = `${environment.gatewayUrl}/api/application/insurance/requestQuote`;
    const payload = {
      ...this.quoteForm.value,
      idUser: this.authService.currentUser?.id,
    };

    this.http.post(url, payload).subscribe({
      next: () => {
        loading.dismiss();
        this.success = true;
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al enviar la solicitud. Intenta de nuevo.',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
