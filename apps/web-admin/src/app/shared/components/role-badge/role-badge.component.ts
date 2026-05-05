import { Component, Input, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DchRole } from '@dch/shared';

const ROLE_DISPLAY: Record<DchRole, { label: string; color: string }> = {
  DCH_SUPER_ADMIN: { label: 'Super Admin', color: '#7c3aed' },
  DCH_ADMIN: { label: 'Admin', color: '#2563eb' },
  DCH_RRHH: { label: 'RRHH', color: '#16a34a' },
  DCH_VIEWER: { label: 'Viewer', color: '#6b7280' },
};

@Component({
  selector: 'app-role-badge',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span
      class="role-badge"
      [style.background-color]="badgeColor"
      [style.color]="'#ffffff'"
    >
      {{ badgeLabel }}
    </span>
  `,
  styles: [`
    .role-badge {
      display: inline-flex;
      align-items: center;
      padding: 2px 10px;
      border-radius: 12px;
      font-size: 11px;
      font-weight: 600;
      letter-spacing: 0.3px;
      text-transform: uppercase;
      white-space: nowrap;
      line-height: 20px;
    }
  `],
})
export class RoleBadgeComponent {
  @Input() role: DchRole = 'DCH_VIEWER';

  get badgeLabel(): string {
    return ROLE_DISPLAY[this.role]?.label ?? 'Viewer';
  }

  get badgeColor(): string {
    return ROLE_DISPLAY[this.role]?.color ?? '#6b7280';
  }
}
