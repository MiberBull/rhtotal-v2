import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersComponent } from './users.component';
import { GenericTableComponent } from '../../components/generic-table/generic-table.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { InfoTableComponent } from '../../components/generic-table/info-table/info-table.component';
import { TablePaginatorComponent } from '../../components/generic-table/table-paginator/table-paginator.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersComponent,GenericTableComponent,InfoTableComponent,TablePaginatorComponent ],
      imports:[ MATERIAL_COMPONENTS,RouterTestingModule.withRoutes([]),HttpClientModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
