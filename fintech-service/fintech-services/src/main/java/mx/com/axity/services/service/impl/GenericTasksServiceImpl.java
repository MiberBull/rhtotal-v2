package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.HeadersGenericTO;
import mx.com.axity.persistence.HeaderDisplayDAO;
import mx.com.axity.services.service.IGenericTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericTasksServiceImpl implements IGenericTasksService {

    @Autowired
    HeaderDisplayDAO headerDisplayDAO;

    @Override
    public HeadersGenericTO getHeader(String nameHeader) {
        var headerRole = this.headerDisplayDAO.getNameHeader(nameHeader);
        return new HeadersGenericTO(headerRole);
    }
}
