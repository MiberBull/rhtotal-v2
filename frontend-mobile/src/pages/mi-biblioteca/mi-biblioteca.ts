import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EventsManagerProvider } from '../../providers/events-manager/events-manager';
import { StorageProvider } from '../../providers/storage/storage';
import { DialogsProvider } from '../../providers/dialogs/dialogs';
import { KEYS_STORAGE, MSG_DIALOG, PATH_BIBLIOTECA } from '../../environments/environments';
import { MessageGeneral } from '../../iterface/create-account.interface';

@Component({
  selector: 'page-mi-biblioteca',
  templateUrl: 'mi-biblioteca.html',
})
export class MiBibliotecaPage {
  categories: any[] = [];
  documents: any[] = [];
  selectedCategory: number | null = null;
  selectedDoc: any = null;
  employeeId: number;
  tenantId: string;
  idClient: number | null = null;
  ackInProgress = false;

  constructor(
    public navCtrl: NavController,
    private http: HttpClient,
    private storage: StorageProvider,
    private events: EventsManagerProvider,
    private dialogs: DialogsProvider
  ) {
    const user = this.storage.getItem(KEYS_STORAGE.USER);
    this.employeeId = user ? user.idEmployee || user.id : null;
    this.tenantId = user ? user.tenantId || 'demo' : 'demo';
    this.idClient = user ? user.idClient || null : null;
  }

  ionViewDidLoad() {
    this.loadCategories();
    this.loadDocuments();
  }

  loadCategories() {
    const params = new HttpParams().set('tenantId', this.tenantId);
    const url = `${PATH_BIBLIOTECA.DOMAIN}/${PATH_BIBLIOTECA.CATEGORIES}`;
    this.http
      .get(url, { params })
      .timeout(10000)
      .subscribe(
        (data: any) => {
          this.categories = Array.isArray(data) ? data : [];
        },
        () => {}
      );
  }

  loadDocuments() {
    if (!this.tenantId) return;
    this.events.setIsLoadingEvent(true);
    let params = new HttpParams().set('tenantId', this.tenantId);
    if (this.idClient) params = params.set('idClient', String(this.idClient));
    const url = `${PATH_BIBLIOTECA.DOMAIN}/${PATH_BIBLIOTECA.DOCUMENTS_VISIBLE}`;
    this.http
      .get(url, { params })
      .timeout(15000)
      .subscribe(
        (data: any) => {
          const all: any[] = Array.isArray(data) ? data : [];
          this.documents = this.selectedCategory
            ? all.filter((d) => d.idCategory === this.selectedCategory)
            : all;
          this.events.setIsLoadingEvent(false);
        },
        () => this.events.setIsLoadingEvent(false)
      );
  }

  filterByCategory(idCategory: number | null) {
    this.selectedCategory = idCategory;
    this.loadDocuments();
  }

  selectDoc(doc: any) {
    this.selectedDoc = doc;
  }

  acknowledgeDoc() {
    if (!this.selectedDoc || !this.employeeId) return;
    this.ackInProgress = true;
    const url = `${PATH_BIBLIOTECA.DOMAIN}/${PATH_BIBLIOTECA.DOCUMENT_ACK}/${this.selectedDoc.idDocument}/ack`;
    const body = {
      tenantId: this.tenantId,
      idEmployee: this.employeeId,
    };
    this.http
      .post(url, body, { headers: { 'X-Employee-Id': String(this.employeeId) } })
      .timeout(10000)
      .subscribe(
        () => {
          this.ackInProgress = false;
          this.selectedDoc = { ...this.selectedDoc, _acked: true };
          this.showAlert('', '¡Lectura confirmada correctamente!');
        },
        (err: any) => {
          this.ackInProgress = false;
          if (err && err.status === 409) {
            this.showAlert('', 'Ya confirmaste la lectura de este documento.');
          } else {
            this.showAlert(MSG_DIALOG.ERROR_TITLE, MSG_DIALOG.ERROR_SERVICE);
          }
        }
      );
  }

  getMimeIcon(mimeType: string): string {
    if (!mimeType) return 'document';
    if (mimeType.includes('pdf')) return 'document';
    if (mimeType.includes('sheet') || mimeType.includes('excel')) return 'grid';
    if (mimeType.includes('word')) return 'document-text';
    if (mimeType.includes('presentation')) return 'easel';
    return 'document';
  }

  showAlert(title: string, msg: string) {
    const obj: MessageGeneral = { msg, title };
    this.events.setGeneralNotificationMessage(obj);
  }

  back() {
    if (this.selectedDoc) {
      this.selectedDoc = null;
      return;
    }
    this.navCtrl.pop();
  }
}
