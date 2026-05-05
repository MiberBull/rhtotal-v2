import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-confirm-delete-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatIconModule],
  template: `
    <div class="confirm-dialog">
      <div class="dialog-icon">
        <mat-icon>warning</mat-icon>
      </div>
      <h2 mat-dialog-title>Confirmar eliminacion</h2>
      <mat-dialog-content>
        <p>
          Estas seguro de que deseas eliminar al empleado
          <strong>{{ data.name }}</strong>?
        </p>
        <p class="warning-text">Esta accion no se puede deshacer.</p>
      </mat-dialog-content>
      <mat-dialog-actions align="end">
        <button mat-button (click)="onCancel()">Cancelar</button>
        <button mat-flat-button color="warn" (click)="onConfirm()">
          <mat-icon>delete</mat-icon>
          Eliminar
        </button>
      </mat-dialog-actions>
    </div>
  `,
  styles: [
    `
      .confirm-dialog {
        padding: 8px;
        text-align: center;
      }

      .dialog-icon {
        margin: 0 auto 8px;
        width: 56px;
        height: 56px;
        border-radius: 50%;
        background: #fff3e0;
        display: flex;
        align-items: center;
        justify-content: center;

        mat-icon {
          font-size: 28px;
          width: 28px;
          height: 28px;
          color: #e65100;
        }
      }

      h2 {
        margin: 0 0 8px;
        font-size: 1.25rem;
        font-weight: 600;
        color: #1a1a2e;
      }

      p {
        margin: 0 0 8px;
        font-size: 0.875rem;
        color: #616161;
      }

      .warning-text {
        font-size: 0.8125rem;
        color: #9e9e9e;
        font-style: italic;
      }

      mat-dialog-actions {
        margin-top: 16px;
        padding: 0;

        button {
          border-radius: 8px;
          text-transform: none;
          font-weight: 500;

          mat-icon {
            font-size: 18px;
            width: 18px;
            height: 18px;
            margin-right: 4px;
          }
        }
      }
    `,
  ],
})
export class ConfirmDeleteDialogComponent {
  private dialogRef = inject(MatDialogRef<ConfirmDeleteDialogComponent>);
  data: { name: string } = inject(MAT_DIALOG_DATA);

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }
}
