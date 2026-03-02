import { RolMockService } from './../../../mock-services/rol.service.mock';
import { RolService } from './../../../services/roles/rol.service';
import { RouterTestingModule } from '@angular/router/testing';
import { FormRolesComponent } from './form-roles.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { RouterModule } from '@angular/router';
import { RolesComponent } from '../roles.component';
import { BrowserAnimationsModule } from '../../../../../node_modules/@angular/platform-browser/animations';

describe('FormRolesComponent', () => {
  let component: FormRolesComponent;
  let fixture: ComponentFixture<FormRolesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        FormRolesComponent
      ],
      imports:[
        BrowserAnimationsModule,
        ReactiveFormsModule,
        MATERIAL_COMPONENTS,
        RouterTestingModule.withRoutes([]),
      ],
      providers:[
        {
          provide: RolService,
          useClass: RolMockService
        }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent( FormRolesComponent );
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should create a form', () => {
    expect( component.formRol.contains('name') ).toBeTruthy();
    expect( component.formRol.contains('lastName') ).toBeTruthy();
    expect( component.formRol.contains('mLastName') ).toBeTruthy();
    expect( component.formRol.contains('phone') ).toBeTruthy();
    expect( component.formRol.contains('idRol') ).toBeTruthy();
    expect( component.formRol.contains('email') ).toBeTruthy();
    expect( component.formRol.contains('status') ).toBeTruthy();
  });

  it('should create a form II', async(() => {
    fixture.detectChanges();
    expect(component.rolesOption).not.toBeNull();    
  }));

  it('should contain at least one record', async(()=>{
    fixture.detectChanges();
    expect((component.rolesOption.length > 1)).toBeTruthy();
  }));

  it('should be different from null', async(()=>{
    fixture.detectChanges();
    expect((component.rolesOption.length != null)).toBeTruthy();
  }));

  it('should validate the form with information valid',() => {
    component.formRol.patchValue({
      name:'Urbano',
      lastName:'Ceron',
      mLastName:'Santillan',
      phone:5569795191,
      idRol:1,
      email:'urbano@axity.com',
      status:'activo'
    });
    expect(component.formRol.valid).toBeTruthy();
  });

  it('should validate the form with information invalid',() => {
    component.formRol.patchValue({
      name:'Select * from',
      lastName:'Cer"$',
      mLastName:'Santill0#$',
      phone:'5569795191',
      idRol:1,
      email:'urbano@axity',
      status:'activo'
    });
    expect(component.formRol.valid).toBeFalsy();
  });


});
