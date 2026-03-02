package mx.com.axity.services.service;

import java.time.LocalDateTime;

public interface IParameterService {
   String getParameterFromDb(String nameParameter);
   LocalDateTime getLocalDateTime();
}
