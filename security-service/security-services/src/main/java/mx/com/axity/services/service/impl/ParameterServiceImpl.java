package mx.com.axity.services.service.impl;

import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParameterServiceImpl implements IParameterService {

    @Autowired
    ParameterDAO parameterDAO;

   @Override
   public String getParameter(String parameter) {
       Optional.ofNullable(parameter).orElseThrow();
       return this.parameterDAO.findByNameParameter(parameter);
   }

}
