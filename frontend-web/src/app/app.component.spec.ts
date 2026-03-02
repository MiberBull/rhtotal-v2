import { TestBed, async } from '@angular/core/testing';

import { APP_BASE_HREF } from "@angular/common";
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing'
        
import { APP_PROVIDERS } from './app.providers';
        
import { MATERIAL_COMPONENTS } from "./app.material";

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ClientComponent } from './pages/client/client.component';
import { PagesComponent } from './pages/pages.component';
import { CreateClientComponent } from './pages/create-client/create-client.component';
import { HeaderComponent } from './shared/header/header.component';
import { By } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { BreadcrumbsComponent } from './shared/breadcrumbs/breadcrumbs.component';
import { ToolbarFabComponent } from './shared/toolbar-fab/toolbar-fab.component';

describe('AppComponent', () => {

  let fixture;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        LoginComponent,
        ClientComponent,
        PagesComponent,
        CreateClientComponent,
        HeaderComponent,
        BreadcrumbsComponent,
        ToolbarFabComponent
      ],
      imports: [
        HttpClientModule,
        BrowserAnimationsModule,
        MATERIAL_COMPONENTS,
        ReactiveFormsModule,
        RouterTestingModule.withRoutes([])
      ],
      providers: [APP_PROVIDERS, {provide: APP_BASE_HREF, useValue: '/'}]
    }).compileComponents();
  }));

  beforeEach(() =>{
    fixture = TestBed.createComponent(AppComponent);
  });
  
  it('should create the app', async(() => {
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  
  it(`should have as title 'app'`, async(() => {
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app');
  }));

  it('should have a router-oulet element', () => {
    const debugElement = fixture.debugElement.query( By.directive( RouterOutlet ) );
    expect(debugElement).not.toBeNull();
  });

});
