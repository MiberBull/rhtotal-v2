package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.VacationBalanceTO;
import mx.com.axity.commons.to.VacationRequestTO;
import mx.com.axity.model.VacationBalanceDO;
import mx.com.axity.model.VacationRequestDO;
import mx.com.axity.services.client.HrNotificationClient;
import mx.com.axity.services.impl.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VacationFacadeImpl {

    @Autowired private VacationServiceImpl vacationService;
    @Autowired private HrNotificationClient hrNotificationClient;

    public VacationBalanceTO initBalance(Long idEmployee, String tenantId, int yearsOfService,
                                         LocalDate periodStart, LocalDate periodEnd) {
        return toBalanceTO(vacationService.initBalance(idEmployee, tenantId, yearsOfService, periodStart, periodEnd));
    }

    public VacationBalanceTO getCurrentBalance(Long idEmployee, String tenantId) {
        return toBalanceTO(vacationService.getCurrentBalance(idEmployee, tenantId));
    }

    public VacationRequestTO createRequest(VacationRequestTO requestTO, String tenantId) {
        VacationRequestDO d = toRequestDO(requestTO);
        d.setTenantId(tenantId);
        return toRequestTO(vacationService.createRequest(d));
    }

    public VacationRequestTO approveRequest(Long id, String approvedBy) {
        VacationRequestTO result = toRequestTO(vacationService.approveRequest(id, approvedBy));
        hrNotificationClient.send(result.getIdEmployee(), "VAC_APROBADA",
                "Vacaciones aprobadas",
                "Tu solicitud de vacaciones del " + result.getDtStartDate() + " al " + result.getDtEndDate() + " fue aprobada.");
        return result;
    }

    public VacationRequestTO rejectRequest(Long id, String approvedBy, String reason) {
        VacationRequestTO result = toRequestTO(vacationService.rejectRequest(id, approvedBy, reason));
        hrNotificationClient.send(result.getIdEmployee(), "VAC_RECHAZADA",
                "Vacaciones rechazadas",
                "Tu solicitud de vacaciones fue rechazada. Motivo: " + reason);
        return result;
    }

    public List<VacationRequestTO> getEmployeeRequests(Long idEmployee, String tenantId) {
        return vacationService.getEmployeeRequests(idEmployee, tenantId).stream()
            .map(this::toRequestTO).collect(Collectors.toList());
    }

    public List<VacationRequestTO> getPendingRequests(String tenantId) {
        return vacationService.getPendingRequests(tenantId).stream()
            .map(this::toRequestTO).collect(Collectors.toList());
    }

    public List<VacationRequestTO> getRequestsByStatus(String tenantId, String status) {
        return vacationService.getRequestsByStatus(tenantId, status).stream()
            .map(this::toRequestTO).collect(Collectors.toList());
    }

    private VacationBalanceTO toBalanceTO(VacationBalanceDO d) {
        VacationBalanceTO to = new VacationBalanceTO();
        to.setIdBalance(d.getIdBalance()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setNbYearOfService(d.getNbYearOfService());
        to.setNbDaysEntitled(d.getNbDaysEntitled()); to.setNbDaysTaken(d.getNbDaysTaken());
        to.setNbDaysPending(d.getNbDaysPending()); to.setDtPeriodStart(d.getDtPeriodStart());
        to.setDtPeriodEnd(d.getDtPeriodEnd()); to.setFgActive(d.getFgActive());
        return to;
    }

    private VacationRequestTO toRequestTO(VacationRequestDO d) {
        VacationRequestTO to = new VacationRequestTO();
        to.setIdRequest(d.getIdRequest()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setDtStartDate(d.getDtStartDate());
        to.setDtEndDate(d.getDtEndDate()); to.setNbDaysRequested(d.getNbDaysRequested());
        to.setDsStatus(d.getDsStatus()); to.setDsNotes(d.getDsNotes());
        to.setDsRejectionReason(d.getDsRejectionReason()); to.setDsApprovedBy(d.getDsApprovedBy());
        to.setDtApprovedDate(d.getDtApprovedDate()); to.setFgActive(d.getFgActive());
        return to;
    }

    private VacationRequestDO toRequestDO(VacationRequestTO to) {
        VacationRequestDO d = new VacationRequestDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDtStartDate(to.getDtStartDate());
        d.setDtEndDate(to.getDtEndDate()); d.setNbDaysRequested(to.getNbDaysRequested());
        d.setDsNotes(to.getDsNotes());
        return d;
    }
}
