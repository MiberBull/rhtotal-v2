import { Component, OnInit } from '@angular/core';
import { EmployeeDocumentService } from '../../services/document/employee-document.service';
import { EmployeeDocumentTO, DocumentTypeTO } from '../../models/document.model';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB } from '../../../environments/environment';

@Component({
  selector: 'app-documentos',
  templateUrl: './documentos.component.html',
  styleUrls: ['./documentos.component.css'],
})
export class DocumentosComponent implements OnInit {
  selectedTab = 0;

  // Pending queue
  pendingDocs: EmployeeDocumentTO[] = [];
  pendingColumns = ['idEmployee', 'idDocumentType', 'dsFilename', 'dsStatus', 'acciones'];

  // Employee search
  searchEmployeeId: string = '';
  employeeDocs: EmployeeDocumentTO[] = [];
  employeeColumns = ['idDocumentType', 'dsFilename', 'dsStatus', 'dsRejectionReason', 'acciones'];

  // Document types
  docTypes: DocumentTypeTO[] = [];

  // Upload form
  showUploadForm = false;
  newDoc: EmployeeDocumentTO = {
    idEmployee: 0,
    idDocumentType: 0,
    dsFilename: '',
    dsMimeType: 'application/pdf',
  };

  constructor(
    private _docService: EmployeeDocumentService,
    private _breadcrumb: BreadcrumbService
  ) {}

  ngOnInit() {
    this._breadcrumb.setBreadcrumb(BREADCRUMB.DOCUMENTOS);
    this.loadPending();
    this.loadDocTypes();
  }

  loadPending() {
    this._docService.getPending().subscribe(
      (data: any) => {
        this.pendingDocs = data || [];
      },
      () => {}
    );
  }

  loadDocTypes() {
    this._docService.getAllTypes().subscribe(
      (data: any) => {
        this.docTypes = data || [];
      },
      () => {}
    );
  }

  searchEmployee() {
    if (!this.searchEmployeeId) return;
    this._docService.getByEmployee(Number(this.searchEmployeeId)).subscribe(
      (data: any) => {
        this.employeeDocs = data || [];
      },
      () => {}
    );
  }

  validate(doc: EmployeeDocumentTO) {
    this._docService.validate(doc.id, 'admin').subscribe(
      () => {
        this.loadPending();
        if (this.searchEmployeeId) this.searchEmployee();
      },
      () => {}
    );
  }

  reject(doc: EmployeeDocumentTO) {
    const reason = prompt('Motivo de rechazo:');
    if (!reason) return;
    this._docService.reject(doc.id, 'admin', reason).subscribe(
      () => {
        this.loadPending();
        if (this.searchEmployeeId) this.searchEmployee();
      },
      () => {}
    );
  }

  deleteDoc(doc: EmployeeDocumentTO) {
    if (!confirm('¿Eliminar este documento?')) return;
    this._docService.delete(doc.id).subscribe(
      () => {
        if (this.searchEmployeeId) this.searchEmployee();
      },
      () => {}
    );
  }

  uploadDoc() {
    this._docService.upload(this.newDoc).subscribe(
      () => {
        this.showUploadForm = false;
        this.newDoc = {
          idEmployee: 0,
          idDocumentType: 0,
          dsFilename: '',
          dsMimeType: 'application/pdf',
        };
        this.loadPending();
      },
      () => {}
    );
  }

  docStatusClass(status: string): string {
    switch (status) {
      case 'VALIDADO':
        return 'dch-badge-aprobada';
      case 'RECHAZADO':
        return 'dch-badge-rechazada';
      default:
        return 'dch-badge-pendiente';
    }
  }

  docTypeName(id: number): string {
    const t = this.docTypes.find((d) => d.id === id);
    return t ? t.dsName : String(id);
  }
}
