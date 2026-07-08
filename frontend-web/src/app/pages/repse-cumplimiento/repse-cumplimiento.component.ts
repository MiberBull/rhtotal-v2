import { Component, OnInit } from '@angular/core';
import { RepseComplianceService } from '../../services/repse/repse-compliance.service';
import { RepseComplianceTO, RepseExpiringTO } from '../../models/repse.model';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB } from '../../../environments/environment';

@Component({
  selector: 'app-repse-cumplimiento',
  templateUrl: './repse-cumplimiento.component.html',
  styleUrls: ['./repse-cumplimiento.component.css'],
})
export class RepseCumplimientoComponent implements OnInit {
  period: string = this.currentPeriod();
  compliance: RepseComplianceTO[] = [];
  expiring: RepseExpiringTO[] = [];
  isLoading = false;
  selectedTab = 0;

  displayedColumns = [
    'dsNombreCliente',
    'nuRequired',
    'nuSubmitted',
    'nuValidated',
    'nuRejected',
    'dsSemaforo',
    'acciones',
  ];
  expiringColumns = ['dsRazonSocial', 'dsNumeroRepse', 'dtVigencia', 'nuDaysToExpiry'];

  get verdeCount() {
    return this.compliance.filter((c) => c.dsSemaforo === 'VERDE').length;
  }
  get amarilloCount() {
    return this.compliance.filter((c) => c.dsSemaforo === 'AMARILLO').length;
  }
  get rojoCount() {
    return this.compliance.filter((c) => c.dsSemaforo === 'ROJO').length;
  }

  constructor(
    private _compliance: RepseComplianceService,
    private _breadcrumb: BreadcrumbService
  ) {}

  ngOnInit() {
    this._breadcrumb.setBreadcrumb(BREADCRUMB.REPSE_CUMPLIMIENTO);
    this.load();
    this.loadExpiring();
  }

  load() {
    this.isLoading = true;
    this._compliance.getDashboard(this.period).subscribe(
      (data: any) => {
        this.compliance = data || [];
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  loadExpiring() {
    this._compliance.getExpiring(90).subscribe(
      (data: any) => {
        this.expiring = data || [];
      },
      () => {}
    );
  }

  recalculate(item: RepseComplianceTO) {
    this._compliance.recalculate(item.idRepseClient, this.period).subscribe(
      () => this.load(),
      () => {}
    );
  }

  exportExcel() {
    this._compliance.exportExcel(this.period).subscribe(
      (blob: Blob) => this.downloadBlob(blob, `repse-cumplimiento-${this.period}.xlsx`),
      () => {}
    );
  }

  exportPdf() {
    this._compliance.exportPdf(this.period).subscribe(
      (blob: Blob) => this.downloadBlob(blob, `repse-cumplimiento-${this.period}.pdf`),
      () => {}
    );
  }

  private downloadBlob(blob: Blob, filename: string) {
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = filename;
    link.click();
    URL.revokeObjectURL(url);
  }

  semaforoClass(semaforo: string): string {
    switch (semaforo) {
      case 'VERDE':
        return 'dch-badge-aprobada';
      case 'AMARILLO':
        return 'dch-badge-pendiente';
      case 'ROJO':
        return 'dch-badge-rechazada';
      default:
        return '';
    }
  }

  expiryClass(days: number): string {
    if (days <= 30) return 'dch-badge-rechazada';
    if (days <= 90) return 'dch-badge-pendiente';
    return 'dch-badge-aprobada';
  }

  private currentPeriod(): string {
    const now = new Date();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    return `${now.getFullYear()}-${month}`;
  }
}
