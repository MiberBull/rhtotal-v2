package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.RepseProfileTO;
import mx.com.axity.model.RepseProfileDO;
import mx.com.axity.persistence.RepseProfileDAO;
import mx.com.axity.services.service.IRepseProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RepseProfileServiceImpl implements IRepseProfileService {

    @Autowired
    RepseProfileDAO repseProfileDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Optional<RepseProfileTO> findByTenantId(String tenantId) {
        return repseProfileDAO.findByTenantId(tenantId)
                .map(do_ -> {
                    var to = modelMapper.map(do_, RepseProfileTO.class);
                    if (do_.getVigencia() != null) {
                        to.setDiasParaVencimiento(ChronoUnit.DAYS.between(LocalDate.now(), do_.getVigencia()));
                    }
                    return to;
                });
    }

    @Override
    public RepseProfileTO save(RepseProfileTO to, String tenantId) {
        to.setTenantId(tenantId);
        to.setActive(Boolean.TRUE);
        var saved = repseProfileDAO.save(modelMapper.map(to, RepseProfileDO.class));
        var result = modelMapper.map(saved, RepseProfileTO.class);
        if (saved.getVigencia() != null) {
            result.setDiasParaVencimiento(ChronoUnit.DAYS.between(LocalDate.now(), saved.getVigencia()));
        }
        return result;
    }

    @Override
    public RepseProfileTO update(RepseProfileTO to, String tenantId) {
        to.setTenantId(tenantId);
        var saved = repseProfileDAO.save(modelMapper.map(to, RepseProfileDO.class));
        var result = modelMapper.map(saved, RepseProfileTO.class);
        if (saved.getVigencia() != null) {
            result.setDiasParaVencimiento(ChronoUnit.DAYS.between(LocalDate.now(), saved.getVigencia()));
        }
        return result;
    }

    @Override
    public List<RepseProfileTO> getExpiringProfiles(int daysAhead) {
        LocalDate threshold = LocalDate.now().plusDays(daysAhead);
        return StreamSupport.stream(repseProfileDAO.findAll().spliterator(), false)
                .filter(p -> p.getVigencia() != null
                        && !p.getVigencia().isAfter(threshold)
                        && Boolean.TRUE.equals(p.getActive()))
                .map(do_ -> {
                    var to = modelMapper.map(do_, RepseProfileTO.class);
                    to.setDiasParaVencimiento(ChronoUnit.DAYS.between(LocalDate.now(), do_.getVigencia()));
                    return to;
                })
                .collect(Collectors.toList());
    }
}
