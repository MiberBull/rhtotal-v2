package mx.com.axity.services.service.impl;

import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParameterServiceImpl implements IParameterService {
    @Autowired
    ParameterDAO parameterDAO;

    @Override
    public String getParameterFromDb(String nameParameter) {
        return this.parameterDAO.getParameterFromDb(nameParameter);
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
}
