import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IncidentService } from '../../../services/incident/incident.service';
import { IncidentTO } from '../../../models/hr.model';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-dialog-create-incident',
  templateUrl: './dialog-create-incident.component.html',
  styleUrls: ['./dialog-create-incident.component.css'],
})
export class DialogCreateIncidentComponent {
  incident: IncidentTO = {
    idEmployee: null,
    dsType: '',
    dtIncidentDate: '',
    dtEndDate: '',
    dsNotes: '',
  };

  readonly INCIDENT_TYPES = [
    { value: 'FALTA', label: 'Falta' },
    { value: 'RETARDO', label: 'Retardo' },
    { value: 'PERMISO', label: 'Permiso' },
    { value: 'INCAPACIDAD', label: 'Incapacidad' },
    { value: 'CAMBIO_TURNO', label: 'Cambio de turno' },
  ];

  constructor(
    public dialogRef: MatDialogRef<DialogCreateIncidentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _incident: IncidentService,
    private _data: DataService
  ) {}

  save() {
    if (!this.incident.idEmployee || !this.incident.dsType || !this.incident.dtIncidentDate) {
      this._data.setGeneralNotificationMessage('Favor de validar campos requeridos(*)');
      return;
    }
    this._incident.save(this.incident).subscribe(
      () => {
        this._data.setGeneralNotificationMessage('Incidencia registrada correctamente');
        this.dialogRef.close(true);
      },
      (err) => {
        console.error('Error al guardar incidencia', err);
        this._data.setGeneralNotificationMessage('Error al guardar incidencia');
      }
    );
  }

  cancel() {
    this.dialogRef.close(false);
  }
}
