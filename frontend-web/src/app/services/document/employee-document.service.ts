import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PATH_DOCUMENT } from '../../../environments/environment';
import { EmployeeDocumentTO, DocumentTypeTO } from '../../models/document.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeDocumentService {
  constructor(private http: HttpClient) {}

  upload(doc: EmployeeDocumentTO) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_UPLOAD}`;
    return this.http.post(URL, doc);
  }

  getById(id: number) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_VALIDATE}/${id}`;
    return this.http.get(URL);
  }

  getByEmployee(employeeId: number) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_BY_EMPLOYEE}/${employeeId}`;
    return this.http.get(URL);
  }

  getPending() {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_PENDING}`;
    return this.http.get(URL);
  }

  validate(id: number, validatedBy: string) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_VALIDATE}/${id}/validate?validatedBy=${encodeURIComponent(validatedBy)}`;
    return this.http.put(URL, null);
  }

  reject(id: number, validatedBy: string, reason: string) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_REJECT}/${id}/reject?validatedBy=${encodeURIComponent(validatedBy)}&reason=${encodeURIComponent(reason)}`;
    return this.http.put(URL, null);
  }

  delete(id: number) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_VALIDATE}/${id}`;
    return this.http.delete(URL);
  }

  getAllTypes() {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_TYPE_ALL}`;
    return this.http.get(URL);
  }

  getRequiredTypes() {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_TYPE_REQUIRED}`;
    return this.http.get(URL);
  }

  saveType(type: DocumentTypeTO) {
    const URL = `${PATH_DOCUMENT.DOMAIN}/${PATH_DOCUMENT.DOC_TYPE_ALL}`;
    return this.http.post(URL, type);
  }
}
