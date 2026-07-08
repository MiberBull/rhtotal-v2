import { Component, OnInit } from '@angular/core';
import { CfdiService } from '../../services/document/cfdi.service';
import { CfdiTO } from '../../models/document.model';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB } from '../../../environments/environment';

@Component({
  selector: 'app-cfdi',
  templateUrl: './cfdi.component.html',
  styleUrls: ['./cfdi.component.css'],
})
export class CfdiComponent implements OnInit {
  searchEmployeeId: string = '';
  searchPeriod: string = '';
  cfdis: CfdiTO[] = [];
  displayedColumns = [
    'dsPeriod',
    'dsType',
    'dsUuid',
    'dsRfcEmisor',
    'dsRfcReceptor',
    'nbTotal',
    'acciones',
  ];

  // Import form
  showImportForm = false;
  newCfdi: CfdiTO = { idEmployee: 0, dsPeriod: '', dsType: 'NOMINA' };

  cfdiTypes = ['NOMINA', 'INGRESO', 'EGRESO', 'TRASLADO'];

  constructor(
    private _cfdi: CfdiService,
    private _breadcrumb: BreadcrumbService
  ) {}

  ngOnInit() {
    this._breadcrumb.setBreadcrumb(BREADCRUMB.CFDI);
  }

  search() {
    if (!this.searchEmployeeId) return;
    if (this.searchPeriod) {
      this._cfdi.getByEmployeeAndPeriod(Number(this.searchEmployeeId), this.searchPeriod).subscribe(
        (data: any) => {
          this.cfdis = Array.isArray(data) ? data : data ? [data] : [];
        },
        () => {}
      );
    } else {
      this._cfdi.getByEmployee(Number(this.searchEmployeeId)).subscribe(
        (data: any) => {
          this.cfdis = data || [];
        },
        () => {}
      );
    }
  }

  importCfdi() {
    this._cfdi.importCfdi(this.newCfdi).subscribe(
      () => {
        this.showImportForm = false;
        this.newCfdi = { idEmployee: 0, dsPeriod: '', dsType: 'NOMINA' };
        this.search();
      },
      () => {}
    );
  }

  downloadXml(cfdi: CfdiTO) {
    if (!cfdi.dsXmlContent) return;
    const blob = new Blob([cfdi.dsXmlContent], { type: 'application/xml' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `${cfdi.dsUuid || 'cfdi'}.xml`;
    link.click();
    URL.revokeObjectURL(url);
  }
}
