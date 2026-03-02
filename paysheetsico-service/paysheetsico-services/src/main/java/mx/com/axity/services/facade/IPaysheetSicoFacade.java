package mx.com.axity.services.facade;


import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;

public interface IPaysheetSicoFacade {

    String search(String email);

    String searchEmployee(SicoEmployeeSearchRequestTO searchData);

}
