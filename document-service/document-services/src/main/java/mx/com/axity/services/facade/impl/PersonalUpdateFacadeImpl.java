package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.PersonalUpdateRequestTO;
import mx.com.axity.model.PersonalUpdateRequestDO;
import mx.com.axity.services.impl.PersonalUpdateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonalUpdateFacadeImpl {

    @Autowired private PersonalUpdateServiceImpl personalUpdateService;

    public PersonalUpdateRequestTO request(PersonalUpdateRequestTO requestTO, String tenantId) {
        PersonalUpdateRequestDO d = toDO(requestTO);
        d.setTenantId(tenantId);
        return toTO(personalUpdateService.request(d));
    }

    public PersonalUpdateRequestTO getById(Long id) {
        return toTO(personalUpdateService.findById(id));
    }

    public List<PersonalUpdateRequestTO> getByEmployee(Long idEmployee, String tenantId) {
        return personalUpdateService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<PersonalUpdateRequestTO> getPending(String tenantId) {
        return personalUpdateService.findPending(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public PersonalUpdateRequestTO approve(Long id, String approvedBy) {
        return toTO(personalUpdateService.approve(id, approvedBy));
    }

    public PersonalUpdateRequestTO reject(Long id, String approvedBy, String reason) {
        return toTO(personalUpdateService.reject(id, approvedBy, reason));
    }

    private PersonalUpdateRequestTO toTO(PersonalUpdateRequestDO d) {
        PersonalUpdateRequestTO to = new PersonalUpdateRequestTO();
        to.setIdUpdateRequest(d.getIdUpdateRequest()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setDsFieldName(d.getDsFieldName());
        to.setDsCurrentValue(d.getDsCurrentValue()); to.setDsNewValue(d.getDsNewValue());
        to.setDsStatus(d.getDsStatus()); to.setDsApprovedBy(d.getDsApprovedBy());
        to.setDtApprovedDate(d.getDtApprovedDate()); to.setDsRejectionReason(d.getDsRejectionReason());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private PersonalUpdateRequestDO toDO(PersonalUpdateRequestTO to) {
        PersonalUpdateRequestDO d = new PersonalUpdateRequestDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDsFieldName(to.getDsFieldName());
        d.setDsCurrentValue(to.getDsCurrentValue()); d.setDsNewValue(to.getDsNewValue());
        return d;
    }
}
