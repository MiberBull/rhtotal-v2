package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.BuzonConfidencialTO;
import mx.com.axity.model.BuzonConfidencialDO;
import mx.com.axity.services.impl.BuzonConfidencialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuzonConfidencialFacadeImpl {

    @Autowired private BuzonConfidencialServiceImpl buzonService;

    public BuzonConfidencialTO submit(BuzonConfidencialTO to, String tenantId) {
        BuzonConfidencialDO d = toDO(to);
        d.setTenantId(tenantId);
        return toTO(buzonService.save(d));
    }

    public List<BuzonConfidencialTO> findByTenant(String tenantId) {
        return buzonService.findByTenant(tenantId).stream().map(this::toTO).collect(Collectors.toList());
    }

    public BuzonConfidencialTO updateEstatus(Long id, String estatus, String comentario) {
        return toTO(buzonService.updateEstatus(id, estatus, comentario));
    }

    private BuzonConfidencialDO toDO(BuzonConfidencialTO to) {
        BuzonConfidencialDO d = new BuzonConfidencialDO();
        d.setDsCategoria(to.getDsCategoria());
        d.setDsDescripcion(to.getDsDescripcion());
        d.setFgAnonimo(to.getFgAnonimo() != null ? to.getFgAnonimo() : true);
        d.setIdUsuario(to.getIdUsuario());
        d.setDsNombreReportante(to.getDsNombreReportante());
        return d;
    }

    private BuzonConfidencialTO toTO(BuzonConfidencialDO d) {
        BuzonConfidencialTO to = new BuzonConfidencialTO();
        to.setIdBuzon(d.getIdBuzon());
        to.setTenantId(d.getTenantId());
        to.setDsCategoria(d.getDsCategoria());
        to.setDsDescripcion(d.getDsDescripcion());
        to.setFgAnonimo(d.getFgAnonimo());
        to.setIdUsuario(d.getIdUsuario());
        to.setDsNombreReportante(d.getDsNombreReportante());
        to.setDsEstatus(d.getDsEstatus());
        to.setDsComentarioRh(d.getDsComentarioRh());
        to.setDtCreacion(d.getDtCreacion());
        to.setDtActualizacion(d.getDtActualizacion());
        return to;
    }
}
