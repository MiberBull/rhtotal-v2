package mx.com.axity.services.schedule;

import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.model.RepseProfileDO;
import mx.com.axity.persistence.RepseProfileDAO;
import mx.com.axity.services.service.INotificationRepositoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RepseScheduler {

    static final Logger LOG = LogManager.getLogger(RepseScheduler.class);

    @Autowired
    private RepseProfileDAO repseProfileDAO;

    @Autowired
    private INotificationRepositoryService notificationRepositoryService;

    public RepseScheduler() {
        LOG.info("INFO RepseScheduler inicializado");
    }

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void checkRepseExpiry() {
        LOG.info("checkRepseExpiry init: {}", LocalDateTime.now());
        Iterable<RepseProfileDO> profiles = repseProfileDAO.findAll();
        int critico = 0;
        int preventivo = 0;
        for (RepseProfileDO profile : profiles) {
            if (!Boolean.TRUE.equals(profile.getActive()) || profile.getVigencia() == null) {
                continue;
            }
            long dias = ChronoUnit.DAYS.between(LocalDate.now(), profile.getVigencia());
            if (dias <= 30) {
                LOG.warn("REPSE CRITICO: perfil={} tenant={} vigencia={} dias_restantes={}",
                        profile.getIdRepseProfile(), profile.getTenantId(), profile.getVigencia(), dias);
                NotificationRepositoryDO notif = buildNotification(
                        profile.getIdRepseProfile(),
                        "REPSE_CRITICO",
                        "Renovación REPSE urgente",
                        "Certificación REPSE vence en " + dias + " días para " + profile.getRazonSocial(),
                        "E");
                notificationRepositoryService.registerNotification(notif);
                profile.setStatus("EN_RENOVACION");
                repseProfileDAO.save(profile);
                critico++;
            } else if (dias <= 90) {
                LOG.info("REPSE PREVENTIVO: perfil={} tenant={} vigencia={} dias_restantes={}",
                        profile.getIdRepseProfile(), profile.getTenantId(), profile.getVigencia(), dias);
                NotificationRepositoryDO notif = buildNotification(
                        profile.getIdRepseProfile(),
                        "REPSE_PREVENTIVO",
                        "Renovación REPSE próxima",
                        "Certificación REPSE vence en " + dias + " días para " + profile.getRazonSocial(),
                        "E");
                notificationRepositoryService.registerNotification(notif);
                preventivo++;
            }
        }
        LOG.info("checkRepseExpiry fin: critico={} preventivo={}", critico, preventivo);
    }

    @Scheduled(cron = "0 0 2 1 * *")
    public void triggerMonthlyReminder() {
        LOG.info("triggerMonthlyReminder: recordatorio mensual de carga de documentos REPSE — {}", LocalDateTime.now());
    }

    private NotificationRepositoryDO buildNotification(Long idElement, String type,
                                                         String title, String description, String status) {
        NotificationRepositoryDO notif = new NotificationRepositoryDO();
        notif.setIdElement(idElement);
        notif.setType(type);
        notif.setTitle(title);
        notif.setDescription(description);
        notif.setDescriptionSmall(description.length() > 100 ? description.substring(0, 100) : description);
        notif.setStatus(status);
        notif.setDateNotification(LocalDateTime.now());
        notif.setCreationDate(LocalDateTime.now());
        notif.setCreationUser("SCHEDULER");
        notif.setFgActive(true);
        return notif;
    }
}
