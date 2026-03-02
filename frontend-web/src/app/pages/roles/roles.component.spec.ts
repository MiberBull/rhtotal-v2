import { RouterTestingModule } from '@angular/router/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RolesComponent } from './roles.component';
import { GenericTableComponent } from '../../components/generic-table/generic-table.component';
import { InfoTableComponent } from '../../components/generic-table/info-table/info-table.component';
import { TablePaginatorComponent } from '../../components/generic-table/table-paginator/table-paginator.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { HttpClientModule } from '@angular/common/http';

describe('RolesComponent', () => {
  let component: RolesComponent;
  let fixture: ComponentFixture<RolesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        RolesComponent,
        GenericTableComponent,
        InfoTableComponent,
        TablePaginatorComponent
      ],
      imports:[
        RouterTestingModule.withRoutes([]),
        MATERIAL_COMPONENTS,
        HttpClientModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RolesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
