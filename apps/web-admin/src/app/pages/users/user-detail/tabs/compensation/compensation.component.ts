import { Component, inject, input, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { UserApiService, ControlCompensationTO } from '@dch/shared';

@Component({
  selector: 'app-compensation',
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
  templateUrl: './compensation.component.html',
  styleUrl: './compensation.component.scss',
})
export class CompensationComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userApi = inject(UserApiService);
  private snackBar = inject(MatSnackBar);

  idUser = input.required<number>();

  saving = signal(false);

  form = this.fb.nonNullable.group({
    sueldoBrutoMensual: [0],
    automovil: [0],
    bonoMensual: [0],
    aguinaldo: [0],
    diasAguinaldo: [0],
    diasVacaciones: [0],
    seguroGMMayores: [0],
    seguroVida: [0],
    planPensiones: [0],
    fondoAhorro: [0],
    valesDespensa: [0],
  });

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.userApi.getCompensation(this.idUser()).subscribe({
      next: (data) => {
        if (data && data.length) {
          // Map array of key-value pairs to form fields
          const map: Record<string, number> = {};
          data.forEach((item) => {
            map[item.dsName] = parseFloat(item.valor) || 0;
          });

          this.form.patchValue({
            sueldoBrutoMensual: map['Sueldo_bruto_mensual'] ?? 0,
            automovil: map['Automovil'] ?? 0,
            bonoMensual: map['Bono_Mensual'] ?? 0,
            aguinaldo: map['Aguinaldo'] ?? 0,
            diasAguinaldo: map['Dias_Aguinaldo'] ?? 0,
            diasVacaciones: map['Cuantos_dias_de_vacaciones'] ?? 0,
            seguroGMMayores: map['Seguro_GM_Mayores'] ?? 0,
            seguroVida: map['Seguro_de_vida'] ?? 0,
            planPensiones: map['Plan_de_pensiones'] ?? 0,
            fondoAhorro: map['Fondo_de_ahorro'] ?? 0,
            valesDespensa: map['Vales_de_despensa'] ?? 0,
          });
        }
      },
      error: () => {
        this.snackBar.open('Error al cargar datos de compensacion', 'Cerrar', { duration: 3000 });
      },
    });
  }

  onSave(): void {
    this.saving.set(true);
    const values = this.form.getRawValue();

    const payload: ControlCompensationTO = {
      idUser: this.idUser(),
      Sueldo_bruto_mensual: values.sueldoBrutoMensual,
      Automovil: values.automovil,
      Bono_Mensual: values.bonoMensual,
      Aguinaldo: values.aguinaldo,
      Dias_Aguinaldo: values.diasAguinaldo,
      Cuantos_dias_de_vacaciones: values.diasVacaciones,
      Seguro_GM_Mayores: values.seguroGMMayores,
      Seguro_de_vida: values.seguroVida,
      Plan_de_pensiones: values.planPensiones,
      Fondo_de_ahorro: values.fondoAhorro,
      Vales_de_despensa: values.valesDespensa,
    };

    this.userApi.saveCompensation(payload).subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open('Compensacion guardada correctamente', 'Cerrar', { duration: 3000 });
      },
      error: () => {
        this.saving.set(false);
        this.snackBar.open('Error al guardar compensacion', 'Cerrar', { duration: 3000 });
      },
    });
  }
}
