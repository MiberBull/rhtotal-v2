import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BannersComponent } from './banners.component';
import { GenericTableComponent } from '../../components/generic-table/generic-table.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { InfoTableComponent } from '../../components/generic-table/info-table/info-table.component';
import { TablePaginatorComponent } from '../../components/generic-table/table-paginator/table-paginator.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

describe('BannersComponent', () => {
  let component: BannersComponent;
  let fixture: ComponentFixture<BannersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BannersComponent,GenericTableComponent,InfoTableComponent,TablePaginatorComponent ],
      imports:[ MATERIAL_COMPONENTS,RouterTestingModule.withRoutes([]),HttpClientModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BannersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
