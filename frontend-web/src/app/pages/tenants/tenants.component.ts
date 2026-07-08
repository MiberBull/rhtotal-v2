import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { TenantService, Tenant } from '../../services/tenant/tenant.service';

@Component({
  selector: 'app-tenants',
  templateUrl: './tenants.component.html',
  styleUrls: ['./tenants.component.css'],
})
export class TenantsComponent implements OnInit {
  tenants: Tenant[] = [];
  displayedColumns = ['id', 'name', 'domain', 'active', 'actions'];
  form: FormGroup;
  loading = false;
  showForm = false;

  constructor(
    private tenantService: TenantService,
    private fb: FormBuilder,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      id: ['', [Validators.required, Validators.pattern(/^[a-z0-9-]+$/)]],
      name: ['', Validators.required],
      domain: [''],
    });
    this.load();
  }

  load() {
    this.loading = true;
    this.tenantService.getAll().subscribe(
      (data) => {
        this.tenants = data;
        this.loading = false;
      },
      () => {
        this.loading = false;
        this.snack.open('Error al cargar tenants', 'OK', { duration: 3000 });
      }
    );
  }

  save() {
    if (this.form.invalid) {
      return;
    }
    this.tenantService.create(this.form.value).subscribe(
      () => {
        this.snack.open('Tenant creado correctamente', 'OK', { duration: 3000 });
        this.form.reset();
        this.showForm = false;
        this.load();
      },
      () => this.snack.open('Error al crear tenant', 'OK', { duration: 3000 })
    );
  }

  toggle(tenant: Tenant) {
    this.tenantService.toggle(tenant.id).subscribe(
      (updated) => {
        tenant.active = updated.active;
        this.snack.open(`Tenant ${updated.active ? 'activado' : 'desactivado'}`, 'OK', {
          duration: 2000,
        });
      },
      () => this.snack.open('Error al cambiar estado', 'OK', { duration: 3000 })
    );
  }
}
