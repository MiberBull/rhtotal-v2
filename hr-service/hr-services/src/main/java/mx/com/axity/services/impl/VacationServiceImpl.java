package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.VacationBalanceTO;
import mx.com.axity.commons.to.VacationRequestTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.LFTVacationUtils;
import mx.com.axity.model.VacationBalanceDO;
import mx.com.axity.model.VacationRequestDO;
import mx.com.axity.persistence.VacationBalanceDAO;
import mx.com.axity.persistence.VacationRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationServiceImpl {

    @Autowired private VacationBalanceDAO balanceDAO;
    @Autowired private VacationRequestDAO requestDAO;

    @Transactional
    public VacationBalanceDO initBalance(Long idEmployee, String tenantId, int yearsOfService,
                                         LocalDate periodStart, LocalDate periodEnd) {
        VacationBalanceDO balance = new VacationBalanceDO();
        balance.setTenantId(tenantId);
        balance.setIdEmployee(idEmployee);
        balance.setNbYearOfService(yearsOfService);
        balance.setNbDaysEntitled(LFTVacationUtils.getDaysEntitled(yearsOfService));
        balance.setNbDaysTaken(0);
        balance.setNbDaysPending(0);
        balance.setDtPeriodStart(periodStart);
        balance.setDtPeriodEnd(periodEnd);
        return balanceDAO.save(balance);
    }

    public VacationBalanceDO getCurrentBalance(Long idEmployee, String tenantId) {
        return balanceDAO.findTopByIdEmployeeAndTenantIdOrderByDtPeriodStartDesc(idEmployee, tenantId)
            .orElseThrow(() -> new BusinessException(404, "Saldo de vacaciones no encontrado para empleado: " + idEmployee));
    }

    @Transactional
    public VacationRequestDO createRequest(VacationRequestDO request) {
        VacationBalanceDO balance = getCurrentBalance(request.getIdEmployee(), request.getTenantId());
        int available = balance.getNbDaysEntitled() - balance.getNbDaysTaken() - balance.getNbDaysPending();
        if (request.getNbDaysRequested() > available) {
            throw new BusinessException(400, "Días insuficientes. Disponibles: " + available);
        }
        VacationRequestDO saved = requestDAO.save(request);
        balance.setNbDaysPending(balance.getNbDaysPending() + request.getNbDaysRequested());
        balanceDAO.save(balance);
        return saved;
    }

    @Transactional
    public VacationRequestDO approveRequest(Long idRequest, String approvedBy) {
        VacationRequestDO req = findRequestById(idRequest);
        if (!Constants.VAC_PENDIENTE.equals(req.getDsStatus())) {
            throw new BusinessException(400, "La solicitud no está en estado PENDIENTE");
        }
        req.setDsStatus(Constants.VAC_APROBADA);
        req.setDsApprovedBy(approvedBy);
        req.setDtApprovedDate(LocalDateTime.now());

        VacationBalanceDO balance = getCurrentBalance(req.getIdEmployee(), req.getTenantId());
        balance.setNbDaysPending(balance.getNbDaysPending() - req.getNbDaysRequested());
        balance.setNbDaysTaken(balance.getNbDaysTaken() + req.getNbDaysRequested());
        balanceDAO.save(balance);
        return requestDAO.save(req);
    }

    @Transactional
    public VacationRequestDO rejectRequest(Long idRequest, String approvedBy, String reason) {
        VacationRequestDO req = findRequestById(idRequest);
        if (!Constants.VAC_PENDIENTE.equals(req.getDsStatus())) {
            throw new BusinessException(400, "La solicitud no está en estado PENDIENTE");
        }
        req.setDsStatus(Constants.VAC_RECHAZADA);
        req.setDsApprovedBy(approvedBy);
        req.setDsRejectionReason(reason);
        req.setDtApprovedDate(LocalDateTime.now());

        VacationBalanceDO balance = getCurrentBalance(req.getIdEmployee(), req.getTenantId());
        balance.setNbDaysPending(balance.getNbDaysPending() - req.getNbDaysRequested());
        balanceDAO.save(balance);
        return requestDAO.save(req);
    }

    public List<VacationRequestDO> getEmployeeRequests(Long idEmployee, String tenantId) {
        return requestDAO.findAllByIdEmployeeAndTenantId(idEmployee, tenantId);
    }

    public List<VacationRequestDO> getPendingRequests(String tenantId) {
        return requestDAO.findAllByTenantIdAndDsStatus(tenantId, Constants.VAC_PENDIENTE);
    }

    public List<VacationRequestDO> getRequestsByStatus(String tenantId, String status) {
        return requestDAO.findAllByTenantIdAndDsStatus(tenantId, status);
    }

    private VacationRequestDO findRequestById(Long id) {
        return requestDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Solicitud no encontrada: " + id));
    }
}
