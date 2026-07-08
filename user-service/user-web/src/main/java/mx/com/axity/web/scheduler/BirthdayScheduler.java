package mx.com.axity.web.scheduler;

import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.persistence.EmployeeComplementaryDAO;
import mx.com.axity.persistence.EmployeeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BirthdayScheduler {

    static final Logger LOG = LogManager.getLogger(BirthdayScheduler.class);

    private static final String NOTIFICATION_URL = "http://application-service/notification/hr-event";

    @Autowired private EmployeeComplementaryDAO employeeComplementaryDAO;
    @Autowired private EmployeeDAO employeeDAO;
    @Autowired private RestTemplate restTemplate;

    public BirthdayScheduler() {
        LOG.info("INFO BirthdayScheduler inicializado");
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkBirthdays() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        List<EmployeeComplementaryDO> birthdays = employeeComplementaryDAO.findBirthdaysToday(month, day);
        if (birthdays.isEmpty()) {
            LOG.info("BirthdayScheduler.checkBirthdays: sin cumpleaños hoy ({}/{})", month, day);
            return;
        }
        for (EmployeeComplementaryDO ec : birthdays) {
            Long idEmployee = ec.getEmployee() != null ? ec.getEmployee().getId() : null;
            String nombre = ec.getEmployee() != null
                    ? ec.getEmployee().getName() + " " + ec.getEmployee().getLastName() : "Colaborador";
            LOG.info("BirthdayScheduler: cumpleaños hoy — empleado #{} {}", idEmployee, nombre);
            sendNotification(idEmployee, "CUMPLEANOS",
                    "¡Feliz Cumpleaños!",
                    "DCH te desea un maravilloso cumpleaños, " + nombre + ". ¡Que lo disfrutes!");
        }
        LOG.info("BirthdayScheduler.checkBirthdays: {} notificaciones de cumpleaños enviadas", birthdays.size());
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkAnniversaries() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        List<EmployeeDO> anniversaries = employeeDAO.findAnniversariesToday(month, day);
        if (anniversaries.isEmpty()) {
            LOG.info("BirthdayScheduler.checkAnniversaries: sin aniversarios hoy ({}/{})", month, day);
            return;
        }
        for (EmployeeDO emp : anniversaries) {
            int years = today.getYear() - (emp.getCreationDate() != null ? emp.getCreationDate().getYear() : today.getYear());
            String nombre = emp.getName() + " " + emp.getLastName();
            LOG.info("BirthdayScheduler: aniversario {} años — empleado #{} {}", years, emp.getId(), nombre);
            if (years > 0) {
                sendNotification(emp.getId(), "ANIVERSARIO",
                        "¡Feliz Aniversario!",
                        "Hoy cumples " + years + " año" + (years == 1 ? "" : "s") + " con nosotros, "
                                + nombre + ". ¡Gracias por tu dedicación!");
            }
        }
        LOG.info("BirthdayScheduler.checkAnniversaries: {} notificaciones de aniversario enviadas", anniversaries.size());
    }

    private void sendNotification(Long idEmployee, String type, String title, String description) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("idElement", idEmployee);
            body.put("type", type);
            body.put("title", title);
            body.put("description", description);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.exchange(NOTIFICATION_URL, HttpMethod.POST,
                    new HttpEntity<>(body, headers), Void.class);
        } catch (Exception e) {
            LOG.warn("BirthdayScheduler: no se pudo enviar notificación type={} idEmployee={} — {}", type, idEmployee, e.getMessage());
        }
    }
}
