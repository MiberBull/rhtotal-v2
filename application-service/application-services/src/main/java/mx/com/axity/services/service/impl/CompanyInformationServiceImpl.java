package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.CompanyInformationTO;
import mx.com.axity.persistence.CompanyInformationDAO;
import mx.com.axity.services.service.ICompanyInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyInformationServiceImpl implements ICompanyInformationService {

    @Autowired
    CompanyInformationDAO companyInformationDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CompanyInformationTO getCompanyInformation(String nameCompanyInformation){
        Optional.of(nameCompanyInformation).orElseThrow();
        return this.modelMapper.map(this.companyInformationDAO.getCompanyInformation(nameCompanyInformation),CompanyInformationTO.class);
    }
}
