package mx.com.axity.persistence;

import mx.com.axity.model.NotificationAssignmentDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationAssignmentDAO extends CrudRepository<NotificationAssignmentDO, Long> {



}
