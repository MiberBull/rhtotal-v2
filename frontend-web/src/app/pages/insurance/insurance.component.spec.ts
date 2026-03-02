import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceComponent } from './insurance.component';
import { GenericTableComponent } from '../../components/generic-table/generic-table.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { CommonModule } from '@angular/common';
import { InfoTableComponent } from '../../components/generic-table/info-table/info-table.component';
import { TablePaginatorComponent } from '../../components/generic-table/table-paginator/table-paginator.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

describe('InsuranceComponent', () => {
  let component: InsuranceComponent;
  let fixture: ComponentFixture<InsuranceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceComponent,GenericTableComponent,InfoTableComponent,TablePaginatorComponent],
      imports:[ MATERIAL_COMPONENTS,CommonModule,HttpClientModule,RouterTestingModule.withRoutes([]) ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
