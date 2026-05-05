import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController, ToastController } from '@ionic/angular';
import { environment } from '../../../environments/environment';

interface CompanyInfo {
  idCompanyInformation?: number;
  name?: string;
  phone?: string;
  email?: string;
  address?: string;
  website?: string;
  mission?: string;
  vision?: string;
}

interface FaqItem {
  question: string;
  answer: string;
}

@Component({
  selector: 'app-help',
  templateUrl: './help.page.html',
  styleUrls: ['./help.page.scss'],
  standalone: false,
})
export class HelpPage implements OnInit {
  companyInfo: CompanyInfo | null = null;

  faqs: FaqItem[] = [
    {
      question: 'Como puedo ver mis beneficios?',
      answer: 'Desde la pantalla principal, toca el icono de "Beneficios" para ver todas las categorias y descuentos disponibles para ti.',
    },
    {
      question: 'Como solicito una cotizacion de seguro?',
      answer: 'Ve a la seccion de Seguros, selecciona el tipo de seguro de tu interes y toca "Solicitar cotizacion". Un asesor se comunicara contigo.',
    },
    {
      question: 'Como actualizo mis datos personales?',
      answer: 'Accede a "Mis Datos" desde el menu lateral. Ahi podras editar tu informacion personal, direccion y redes sociales.',
    },
    {
      question: 'No puedo iniciar sesion, que hago?',
      answer: 'Verifica que tu correo y contrasena sean correctos. Si olvidaste tu contrasena, usa la opcion "Recuperar contrasena" en la pantalla de login.',
    },
    {
      question: 'Como contacto a soporte?',
      answer: 'Puedes contactarnos por telefono o correo electronico. Encontraras nuestra informacion de contacto al final de esta pagina.',
    },
  ];

  constructor(
    private http: HttpClient,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController
  ) {}

  ngOnInit(): void {
    this.loadCompanyInfo();
  }

  callSupport(): void {
    if (this.companyInfo?.phone) {
      window.open(`tel:${this.companyInfo.phone}`, '_system');
    }
  }

  emailSupport(): void {
    if (this.companyInfo?.email) {
      window.open(`mailto:${this.companyInfo.email}`, '_system');
    }
  }

  private async loadCompanyInfo(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando...' });
    await loading.present();

    const url = `${environment.gatewayUrl}/api/application/companyInformation/getCompanyInformation`;

    this.http.get<CompanyInfo>(url).subscribe({
      next: (data) => {
        this.companyInfo = data;
        loading.dismiss();
      },
      error: async () => {
        loading.dismiss();
        const toast = await this.toastCtrl.create({
          message: 'Error al cargar informacion',
          duration: 3000,
          color: 'danger',
        });
        toast.present();
      },
    });
  }
}
