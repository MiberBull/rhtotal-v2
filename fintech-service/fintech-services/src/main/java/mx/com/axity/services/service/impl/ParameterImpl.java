package mx.com.axity.services.service.impl;

import mx.com.axity.model.ParameterDO;
import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.service.IParameterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterImpl implements IParameterService {

    @Autowired
    ParameterDAO parameterDAO;

    @Override
    public String getValueParameterByName(String name) {
        return this.parameterDAO.getParameterFromDb(name);
    }
}
