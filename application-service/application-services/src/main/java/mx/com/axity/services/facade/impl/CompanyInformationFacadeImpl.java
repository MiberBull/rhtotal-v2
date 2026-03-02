package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.CompanyInformationTO;
import mx.com.axity.services.facade.ICompanyInformationFacade;
import mx.com.axity.services.service.ICompanyInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyInformationFacadeImpl implements ICompanyInformationFacade {

    @Autowired
    ICompanyInformationService companyInformationService;

    @Override
    public CompanyInformationTO getCompanyInformation(String nameCompanyInformation) {
        try {
            Optional.of(nameCompanyInformation).orElseThrow();
            return this.companyInformationService.getCompanyInformation(nameCompanyInformation);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
