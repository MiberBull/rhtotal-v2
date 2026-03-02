import { BreadcrumbsComponent } from './breadcrumbs.component';
import { TestBed, async, ComponentFixture } from "@angular/core/testing";
import { MATERIAL_COMPONENTS } from '../../app.material';
import { RouterTestingModule } from '@angular/router/testing';


describe('BreadcrumbsComponent',() => {

    let fixture: ComponentFixture<BreadcrumbsComponent>;
    let component: BreadcrumbsComponent;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [ 
            BreadcrumbsComponent
          ],
          imports:[
            RouterTestingModule.withRoutes([]),
           MATERIAL_COMPONENTS
          ],
          providers:[
          ]
        })
        .compileComponents();
      }));

      beforeEach(() => {
        fixture = TestBed.createComponent(BreadcrumbsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

      it('should create', () => {
        expect(component).toBeTruthy();
      });

      it('should render title in a span title', async(() => {
        let debugElement = fixture.debugElement.nativeElement;
        component.textTitle='BreadCrumb';
        fixture.detectChanges();
        expect(debugElement.querySelector('.breadcrumbs span').textContent).toContain('BreadCrumb');
      }));
     

});