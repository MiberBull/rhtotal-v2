import { TestBed,async, ComponentFixture } from "@angular/core/testing";
import { LoginPage } from "./login";
import { IonicModule, NavController, NavParams } from "ionic-angular";
import { NavParamsMock } from 'ionic-mocks';
import { LoginProvider } from "../../providers/login/login";
import { HttpClientModule } from "@angular/common/http";

xdescribe('Login Component',() => {
    
    let fixture: ComponentFixture<LoginPage>  ;
    let component: LoginPage;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations:[LoginPage],
            imports:[
                IonicModule.forRoot( LoginPage ),
                HttpClientModule
            ],
            providers:[
                { provide:NavParams, useFactory: () => NavParamsMock.instance() },
                NavController,
                LoginProvider
            ]
        });
    }));


    beforeEach( () => {
        fixture = TestBed.createComponent( LoginPage );
        component = fixture.componentInstance;
    });

    it('should be created', () => {
        expect( component instanceof LoginPage).toBe(true);
    });

});

