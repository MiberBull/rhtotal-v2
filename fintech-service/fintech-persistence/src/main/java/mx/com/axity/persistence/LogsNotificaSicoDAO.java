package mx.com.axity.persistence;

import mx.com.axity.model.LogConfirmationSicoDO;
import org.springframework.data.repository.CrudRepository;

public interface LogsNotificaSicoDAO extends CrudRepository<LogConfirmationSicoDO,Long> {
}
