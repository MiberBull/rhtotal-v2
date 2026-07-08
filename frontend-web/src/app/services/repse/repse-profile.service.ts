import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PATH_REPSE } from '../../../environments/environment';
import { RepseProfileTO, RepseClientTO, RepseDocumentTO } from '../../models/repse.model';

@Injectable({
  providedIn: 'root',
})
export class RepseProfileService {
  constructor(private http: HttpClient) {}

  // Profile
  getProfile() {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.PROFILE}`;
    return this.http.get(URL);
  }

  saveProfile(profile: RepseProfileTO) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.PROFILE}`;
    return this.http.post(URL, profile);
  }

  updateProfile(profile: RepseProfileTO) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.PROFILE}`;
    return this.http.put(URL, profile);
  }

  // Clients
  getAllClients() {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.CLIENT_ALL}`;
    return this.http.get(URL);
  }

  getClient(id: number) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.CLIENT}/${id}`;
    return this.http.get(URL);
  }

  saveClient(client: RepseClientTO) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.CLIENT}`;
    return this.http.post(URL, client);
  }

  updateClient(client: RepseClientTO) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.CLIENT}`;
    return this.http.put(URL, client);
  }

  // Documents
  getDocuments(idRepseClient: number, period: string) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.DOCUMENT}/${idRepseClient}/${period}`;
    return this.http.get(URL);
  }

  uploadDocument(doc: RepseDocumentTO) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.DOCUMENT}`;
    return this.http.post(URL, doc);
  }

  validateDocument(id: number, validatedBy: string) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.DOCUMENT}/${id}/validate?validatedBy=${encodeURIComponent(validatedBy)}`;
    return this.http.put(URL, null);
  }

  rejectDocument(id: number, rejectionReason: string) {
    const URL = `${PATH_REPSE.DOMAIN}/${PATH_REPSE.DOCUMENT}/${id}/reject?rejectionReason=${encodeURIComponent(rejectionReason)}`;
    return this.http.put(URL, null);
  }
}
