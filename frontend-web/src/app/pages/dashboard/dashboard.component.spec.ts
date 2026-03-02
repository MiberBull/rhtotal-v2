import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { PieGraphicComponent } from '../../components/pie-graphic/pie-graphic.component';
import { BarGraphicComponent } from '../../components/bar-graphic/bar-graphic.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChartsModule } from 'ng2-charts';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardComponent,PieGraphicComponent,BarGraphicComponent ],
      imports:[ MATERIAL_COMPONENTS,BrowserAnimationsModule,ChartsModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
