package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;
import mx.com.axity.services.facade.IPaysheetSicoFacade;
import mx.com.axity.services.service.IPaysheetSicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaysheetSicoFacade implements IPaysheetSicoFacade {

    @Autowired
    private IPaysheetSicoService paysheetsicoService;


    @Override
    public String search(String email) {
        String dataFromSico = this.paysheetsicoService.getUserDataByEmail(email);

        return dataFromSico;
    }

    @Override
    public String searchEmployee(SicoEmployeeSearchRequestTO searchData) {
        String url = paysheetsicoService.buildSearchUrl(searchData);
        return paysheetsicoService.getUserDataByParams(url);
    }
}
