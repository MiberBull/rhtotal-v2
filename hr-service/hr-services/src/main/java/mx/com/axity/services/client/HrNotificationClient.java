package mx.com.axity.services.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class HrNotificationClient {

    static final Logger LOG = LogManager.getLogger(HrNotificationClient.class);

    private static final String NOTIFICATION_URL = "http://application-service/notification/hr-event";

    @Autowired
    private RestTemplate restTemplate;

    public void send(Long idElement, String type, String title, String description) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("idElement", idElement);
            body.put("type", type);
            body.put("title", title);
            body.put("description", description);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.exchange(NOTIFICATION_URL, HttpMethod.POST,
                    new HttpEntity<>(body, headers), Void.class);
            LOG.info("HrNotificationClient: notificación enviada type={} idElement={}", type, idElement);
        } catch (Exception e) {
            LOG.warn("HrNotificationClient: no se pudo enviar notificación type={} — {}", type, e.getMessage());
        }
    }
}
