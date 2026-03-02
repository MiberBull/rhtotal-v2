import { CreateAccountPage } from "./create-account";
import { HttpClientModule } from "@angular/common/http";
import { IonicModule, NavParams, NavController } from "ionic-angular";
import { async, TestBed, ComponentFixture } from "@angular/core/testing";
import { NavParamsMock } from "ionic-mocks";
import { LoginProvider } from "../../providers/login/login";


fdescribe('Create account component ', () => {
    
    let fixture: ComponentFixture<CreateAccountPage>;
    let component: CreateAccountPage;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations:[ CreateAccountPage ],
            imports:[
                IonicModule.forRoot( CreateAccountPage ),
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
        fixture = TestBed.createComponent( CreateAccountPage );
        component = fixture.componentInstance;
    });

    it('should be created', () => {
        expect( component instanceof CreateAccountPage).toBe(true);
    });

    it('should create a form', () => {
        expect( component.accountForm.contains('email') ).toBeTruthy();
        expect( component.accountForm.contains('access') ).toBeTruthy();
        expect( component.accountForm.contains('accessConfirmed') ).toBeTruthy();
      });
    

    it('should validate email', () => {
        let validEmail = 'urbano@axity.com',badEmail = 'urboun.axity.com';
        expect( component.valitedateEmail( validEmail ) ).toBeTruthy();
        expect( component.valitedateEmail( badEmail ) ).toBeFalsy();
    });

    it('should validate email', () => {
        let validEmail = 'urbano@axity.com',badEmail = 'urboun.axity.com';
        expect( component.valitedateEmail( validEmail ) ).toBeTruthy();
        expect( component.valitedateEmail( badEmail ) ).toBeFalsy();
    });

    it('should compare the two passwords', () => {
        let acces_I = 'a$cc0Mexc', acces_II = 'a$cc0Mexc',bad_accces = 'a5cc0Mexc';
        let resp_1 = component.valdiatePasswords(acces_I,acces_II);
        let resp_2 = component.valdiatePasswords(acces_I,bad_accces);
        expect( resp_1.ok ).toBeTruthy();
        expect( resp_2.ok ).toBeFalsy();
    });

    it('should validate format password', () => {
        
        let acces_I = 'a$cc0Mexc', acces_II = 'a$cc0Mexc';  
        let bad_acces_1 = '1234qwer', bad_acces_2 = 'tacosdesuadero',bad_acces_3 = 'axity';
        let resp_1 = component.valdiatePasswords(acces_I,acces_II);
        let resp_2 = component.valdiatePasswords(bad_acces_1,bad_acces_1);
        let resp_3 = component.valdiatePasswords(bad_acces_2,bad_acces_2);
        let resp_4 = component.valdiatePasswords(bad_acces_3,bad_acces_3);

        expect( resp_1.ok ).toBeTruthy();
        expect( resp_2.ok ).toBeFalsy();
        expect( resp_3.ok ).toBeFalsy();
        expect( resp_4.ok ).toBeFalsy();
    });

    xit('should validate alert email invalid', () => {
        let badEmail = 'urbound.axity.com';

        let spy = spyOn( component,'showAlert' );
        component.accountForm.setValue({email:badEmail,acces:'',accesConfirmed:''});
        fixture.detectChanges()

        expect( spy ).toHaveBeenCalled();

    });

    it('shoul show input code',async( () =>  {
        component.accountForm.setValue({
            email:'urbano@axity.com',
            access:'Pa$$w0rd',
            accessConfirmed:'Pa$$w0rd'
        });
        component.onSubmit();
        expect( component.showInputCode ).toBeTruthy();

    }));


    
    
});