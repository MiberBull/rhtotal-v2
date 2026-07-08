import { Component, OnInit } from '@angular/core';
import { RepseProfileService } from '../../services/repse/repse-profile.service';
import { RepseProfileTO, RepseClientTO, RepseDocumentTO } from '../../models/repse.model';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { BREADCRUMB } from '../../../environments/environment';

const REPSE_DOC_TYPES = [
  'ISR_MENSUAL',
  'IMSS_SUA',
  'INFONAVIT',
  'CFDI_NOMINA',
  'DECLARACION_ANUAL',
  'OPINION_CUMPLIMIENTO',
];

@Component({
  selector: 'app-repse-perfil',
  templateUrl: './repse-perfil.component.html',
  styleUrls: ['./repse-perfil.component.css'],
})
export class RepsePerfilComponent implements OnInit {
  selectedTab = 0;

  // Profile
  profile: RepseProfileTO = {
    dsRazonSocial: '',
    dsRfc: '',
    dsNumeroRepse: '',
    dtVigencia: '',
    dsStatus: 'ACTIVO',
  };
  profileExists = false;
  editingProfile = false;

  // Clients
  clients: RepseClientTO[] = [];
  clientColumns = ['dsNombreCliente', 'dsRfcCliente', 'nuDocumentosRequeridos', 'acciones'];
  showClientForm = false;
  newClient: RepseClientTO = {
    idRepseProfile: 0,
    dsNombreCliente: '',
    dsRfcCliente: '',
    nuDocumentosRequeridos: 6,
  };

  // Documents
  selectedClient: RepseClientTO = null;
  docPeriod: string = this.currentPeriod();
  clientDocs: RepseDocumentTO[] = [];
  docColumns = ['dsType', 'dsFilename', 'dsStatus', 'dsNotes', 'acciones'];
  showDocForm = false;
  newDoc: RepseDocumentTO = {
    idRepseClient: 0,
    dsPeriod: '',
    dsType: '',
    dsFilename: '',
    dsContent: '',
  };
  docTypes = REPSE_DOC_TYPES;

  constructor(
    private _profile: RepseProfileService,
    private _breadcrumb: BreadcrumbService
  ) {}

  ngOnInit() {
    this._breadcrumb.setBreadcrumb(BREADCRUMB.REPSE_PERFIL);
    this.loadProfile();
    this.loadClients();
  }

  loadProfile() {
    this._profile.getProfile().subscribe(
      (data: any) => {
        if (data) {
          this.profile = data;
          this.profileExists = true;
        }
      },
      () => {}
    );
  }

  saveProfile() {
    const obs = this.profileExists
      ? this._profile.updateProfile(this.profile)
      : this._profile.saveProfile(this.profile);
    obs.subscribe(
      (data: any) => {
        this.profile = data;
        this.profileExists = true;
        this.editingProfile = false;
      },
      () => {}
    );
  }

  loadClients() {
    this._profile.getAllClients().subscribe(
      (data: any) => {
        this.clients = data || [];
      },
      () => {}
    );
  }

  saveClient() {
    if (this.profile && this.profile.id) {
      this.newClient.idRepseProfile = this.profile.id;
    }
    this._profile.saveClient(this.newClient).subscribe(
      () => {
        this.showClientForm = false;
        this.newClient = {
          idRepseProfile: 0,
          dsNombreCliente: '',
          dsRfcCliente: '',
          nuDocumentosRequeridos: 6,
        };
        this.loadClients();
      },
      () => {}
    );
  }

  selectClient(client: RepseClientTO) {
    this.selectedClient = client;
    this.newDoc.idRepseClient = client.id;
    this.loadClientDocs();
  }

  loadClientDocs() {
    if (!this.selectedClient) return;
    this._profile.getDocuments(this.selectedClient.id, this.docPeriod).subscribe(
      (data: any) => {
        this.clientDocs = data || [];
      },
      () => {}
    );
  }

  saveDoc() {
    this.newDoc.dsPeriod = this.docPeriod;
    this._profile.uploadDocument(this.newDoc).subscribe(
      () => {
        this.showDocForm = false;
        this.newDoc = {
          idRepseClient: this.selectedClient ? this.selectedClient.id : 0,
          dsPeriod: '',
          dsType: '',
          dsFilename: '',
          dsContent: '',
        };
        this.loadClientDocs();
      },
      () => {}
    );
  }

  validateDoc(doc: RepseDocumentTO) {
    this._profile.validateDocument(doc.id, 'admin').subscribe(
      () => this.loadClientDocs(),
      () => {}
    );
  }

  rejectDoc(doc: RepseDocumentTO) {
    const reason = prompt('Motivo de rechazo:');
    if (!reason) return;
    this._profile.rejectDocument(doc.id, reason).subscribe(
      () => this.loadClientDocs(),
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

  private currentPeriod(): string {
    const now = new Date();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    return `${now.getFullYear()}-${month}`;
  }
}
