import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-buzon',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatCardModule],
  template: `
    <div style="padding:24px">
      <h1 style="display:flex;align-items:center;gap:8px">
        <mat-icon>lock</mat-icon> Buzón Confidencial
      </h1>
      <mat-card style="text-align:center;padding:48px">
        <mat-icon style="font-size:64px;width:64px;height:64px;color:#9e9e9e">inbox</mat-icon>
        <p style="color:#666;margin-top:16px">Módulo en desarrollo — Sprint 18</p>
      </mat-card>
    </div>
  `,
})
export class BuzonComponent {}
