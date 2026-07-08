package mx.com.axity.web.scheduler;

import mx.com.axity.model.TicketDO;
import mx.com.axity.model.VacationRequestDO;
import mx.com.axity.persistence.TicketDAO;
import mx.com.axity.persistence.VacationRequestDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class HrScheduler {

    static final Logger LOG = LogManager.getLogger(HrScheduler.class);

    @Autowired
    private VacationRequestDAO vacationRequestDAO;

    @Autowired
    private TicketDAO ticketDAO;

    public HrScheduler() {
        LOG.info("INFO HrScheduler inicializado");
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkPendingVacations() {
        LOG.info("checkPendingVacations init: {}", LocalDateTime.now());
        LocalDateTime threshold = LocalDateTime.now().minusHours(72);
        List<VacationRequestDO> pending = vacationRequestDAO.findAll().stream()
                .filter(v -> "PENDIENTE".equals(v.getDsStatus())
                        && v.getDtCreationDate() != null
                        && v.getDtCreationDate().isBefore(threshold))
                .toList();
        for (VacationRequestDO v : pending) {
            LOG.warn("Vacación #{} empleado #{} sin resolución > 72h (creada: {})",
                    v.getIdRequest(), v.getIdEmployee(), v.getDtCreationDate());
        }
        LOG.info("checkPendingVacations fin: {} solicitudes sin resolver > 72h", pending.size());
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkOverdueTickets() {
        LOG.info("checkOverdueTickets init: {}", LocalDateTime.now());
        LocalDateTime threshold = LocalDateTime.now().minusHours(48);
        List<String> openStatuses = Arrays.asList("ABIERTO", "EN_PROCESO");
        List<TicketDO> overdue = ticketDAO.findAll().stream()
                .filter(t -> openStatuses.contains(t.getDsStatus())
                        && t.getDtCreationDate() != null
                        && t.getDtCreationDate().isBefore(threshold))
                .toList();
        for (TicketDO t : overdue) {
            LOG.warn("Ticket #{} sin resolución > 48h (estado: {}, creado: {})",
                    t.getDsNumber(), t.getDsStatus(), t.getDtCreationDate());
        }
        LOG.info("checkOverdueTickets fin: {} tickets sin resolver > 48h", overdue.size());
    }
}
