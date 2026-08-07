package mx.com.axity.services.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    private static final Logger LOG = LogManager.getLogger(FirebaseConfig.class);

    @Value("${firebase.serviceAccount:}")
    private String serviceAccountBase64;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        if (serviceAccountBase64 == null || serviceAccountBase64.isBlank()) {
            LOG.warn("Firebase no configurado (FIREBASE_SERVICE_ACCOUNT_JSON ausente) — push notifications desactivadas");
            return null;
        }
        byte[] decoded = Base64.getDecoder().decode(serviceAccountBase64);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(decoded)))
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        LOG.info("FirebaseApp inicializado correctamente (FCM v1)");
        return FirebaseMessaging.getInstance();
    }
}
