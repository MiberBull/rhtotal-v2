import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadingController } from '@ionic/angular';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';

interface CredentialData {
  name: string;
  lastName: string;
  middleName: string;
  email: string;
  rfc: string;
  employeeId: string;
  company: string;
  photoUrl: string;
}

@Component({
  selector: 'app-credential',
  templateUrl: './credential.page.html',
  styleUrls: ['./credential.page.scss'],
  standalone: false,
})
export class CredentialPage implements OnInit {
  credential: CredentialData = {
    name: '',
    lastName: '',
    middleName: '',
    email: '',
    rfc: '',
    employeeId: '',
    company: 'DCH Know Who',
    photoUrl: '',
  };

  get fullName(): string {
    return `${this.credential.name} ${this.credential.lastName} ${this.credential.middleName}`.trim();
  }

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loadingCtrl: LoadingController
  ) {}

  ngOnInit(): void {
    this.loadCredential();
  }

  async loadCredential(): Promise<void> {
    const loading = await this.loadingCtrl.create({ message: 'Cargando credencial...' });
    await loading.present();

    const userId = this.authService.currentUser?.id;
    const url = `${environment.gatewayUrl}/api/user/user/getEmployeeComplementary/${userId}`;

    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.credential = {
          name: data.name || '',
          lastName: data.lastName || '',
          middleName: data.middleName || '',
          email: data.email || this.authService.currentUser?.email || '',
          rfc: data.rfc || '',
          employeeId: data.employeeNumber || String(userId),
          company: data.companyName || 'DCH Know Who',
          photoUrl: data.photoUrl || '',
        };
        loading.dismiss();
      },
      error: () => {
        this.credential.email = this.authService.currentUser?.email || '';
        this.credential.employeeId = String(userId);
        loading.dismiss();
      },
    });
  }
}
