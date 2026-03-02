import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ClientComponent } from "./client.component";
import { ReactiveFormsModule } from "@angular/forms";
import { CreateClientComponent } from "../create-client/create-client.component";
import { MATERIAL_COMPONENTS } from "../../app.material";
import { HttpClientModule } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

describe('ClientComponent', () => {

  let component: ClientComponent;
  let fixture: ComponentFixture<ClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ClientComponent,CreateClientComponent],
      imports:[ ReactiveFormsModule,MATERIAL_COMPONENTS,HttpClientModule,BrowserAnimationsModule ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("Wait for the data to be saved successfully", () => {
  });
});
