package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.LogsResponseSicoTO;
import mx.com.axity.model.LogsResponseSicoDO;
import mx.com.axity.persistence.LogsResponseSicoDAO;
import mx.com.axity.services.service.ILogsResponseSicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogsResponseSicoImpl implements ILogsResponseSicoService {

    @Autowired
    LogsResponseSicoDAO logsResponseDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void saveLogsResponseSicoTO(LogsResponseSicoTO responseSico) {
        responseSico.setCreationDate(LocalDateTime.now());
        responseSico.setLastModification(LocalDateTime.now());
        responseSico.setActive(true);
        responseSico.setCreationUser(responseSico.getDsUser());
        responseSico.setLastUserModifier(responseSico.getDsUser());
        this.logsResponseDAO.save(this.modelMapper.map(responseSico,LogsResponseSicoDO.class));
    }
}
