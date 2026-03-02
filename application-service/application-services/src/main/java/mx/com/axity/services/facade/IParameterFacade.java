package mx.com.axity.services.facade;

import java.time.LocalDateTime;
import java.util.Map;

public interface IParameterFacade {

    String getParameterFromDb(String nameParameter);
    Map<String,LocalDateTime> getLocalDateTime();
}
