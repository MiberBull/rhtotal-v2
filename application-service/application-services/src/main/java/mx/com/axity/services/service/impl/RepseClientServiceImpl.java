package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.RepseClientTO;
import mx.com.axity.model.RepseClientDO;
import mx.com.axity.persistence.RepseClientDAO;
import mx.com.axity.services.service.IRepseClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepseClientServiceImpl implements IRepseClientService {

    @Autowired
    RepseClientDAO repseClientDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RepseClientTO> findAllByTenant(String tenantId) {
        return (List<RepseClientTO>) modelMapper.map(
                repseClientDAO.findAllByTenantIdAndActiveTrue(tenantId),
                new TypeToken<List<RepseClientTO>>(){}.getType()
        );
    }

    @Override
    public Optional<RepseClientTO> findById(Long id, String tenantId) {
        return repseClientDAO.findById(id)
                .filter(c -> tenantId.equals(c.getTenantId()))
                .map(c -> modelMapper.map(c, RepseClientTO.class));
    }

    @Override
    public RepseClientTO save(RepseClientTO to, String tenantId) {
        to.setTenantId(tenantId);
        to.setActive(Boolean.TRUE);
        if (to.getStatus() == null) {
            to.setStatus("ACTIVO");
        }
        var saved = repseClientDAO.save(modelMapper.map(to, RepseClientDO.class));
        return modelMapper.map(saved, RepseClientTO.class);
    }

    @Override
    public RepseClientTO update(RepseClientTO to, String tenantId) {
        to.setTenantId(tenantId);
        var saved = repseClientDAO.save(modelMapper.map(to, RepseClientDO.class));
        return modelMapper.map(saved, RepseClientTO.class);
    }
}
