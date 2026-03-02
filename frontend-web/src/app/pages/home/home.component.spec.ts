import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { PieGraphicComponent } from '../../components/pie-graphic/pie-graphic.component';
import { BarGraphicComponent } from '../../components/bar-graphic/bar-graphic.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ChartsModule } from 'ng2-charts';
import { MATERIAL_COMPONENTS } from '../../app.material';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeComponent,PieGraphicComponent,BarGraphicComponent,DashboardComponent ],
      imports:[ ChartsModule,MATERIAL_COMPONENTS ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
