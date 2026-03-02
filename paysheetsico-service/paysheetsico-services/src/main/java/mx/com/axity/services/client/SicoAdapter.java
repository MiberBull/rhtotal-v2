package mx.com.axity.services.client;

import mx.com.axity.commons.to.AnswerSICOTO;
import mx.com.axity.commons.to.NotificaTransferenciaTO;

public interface SicoAdapter {
    String get(String format);

    AnswerSICOTO employeeNotification(NotificaTransferenciaTO notificationTranferen);

}
