import { Component, inject, input, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserApiService, EmployeeSocialNetworkTO } from '@dch/shared';

@Component({
  selector: 'app-social-networks',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './social-networks.component.html',
  styleUrl: './social-networks.component.scss',
})
export class SocialNetworksComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();

  saving = signal(false);

  form = this.fb.nonNullable.group({
    facebook: [''],
    twitter: [''],
    linkedin: [''],
    instagram: [''],
    github: [''],
    website: [''],
    skype: [''],
  });

  private networkMap: Record<string, string> = {
    facebook: 'Facebook',
    twitter: 'Twitter',
    linkedin: 'LinkedIn',
    instagram: 'Instagram',
    github: 'GitHub',
    website: 'Website',
    skype: 'Skype',
  };

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.userApi.getSocialNetworks(this.idUser()).subscribe({
      next: (data) => {
        if (data && data.length) {
          const formValues: Record<string, string> = {};
          data.forEach((item) => {
            const key = Object.keys(this.networkMap).find(
              (k) => this.networkMap[k].toLowerCase() === item.nameRedSocial?.toLowerCase()
            );
            if (key) {
              formValues[key] = item.value ?? '';
            }
          });
          this.form.patchValue(formValues);
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar redes sociales', 'Cerrar', { duration: 3000 });
      },
    });
  }

  onSave(): void {
    this.saving.set(true);
    const values = this.form.getRawValue();

    const entries = Object.entries(values).filter(([_, v]) => !!v);
    let completed = 0;
    let hasError = false;

    if (entries.length === 0) {
      this.saving.set(false);
      this.snackBar.open('No hay datos para guardar', 'Cerrar', { duration: 3000 });
      return;
    }

    entries.forEach(([key, value]) => {
      const payload: Partial<EmployeeSocialNetworkTO> = {
        idUser: this.idUser(),
        nameRedSocial: this.networkMap[key],
        value: value,
        parameterDescription: this.networkMap[key],
        lastUserModifier: 'admin',
        lastModification: new Date(),
      };

      this.userApi.saveSocialNetwork(payload as EmployeeSocialNetworkTO).subscribe({
        next: () => {
          completed++;
          if (completed === entries.length) {
            this.saving.set(false);
            if (!hasError) {
              this.snackBar.open('Redes sociales guardadas correctamente', 'Cerrar', { duration: 3000 });
            }
          }
        },
        error: () => {
          hasError = true;
          completed++;
          if (completed === entries.length) {
            this.saving.set(false);
            this.snackBar.open('Error al guardar algunas redes sociales', 'Cerrar', { duration: 3000 });
          }
        },
      });
    });
  }
}
