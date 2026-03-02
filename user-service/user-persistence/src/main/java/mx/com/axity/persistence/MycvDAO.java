package mx.com.axity.persistence;

import mx.com.axity.model.MycvDO;
import org.springframework.data.repository.CrudRepository;

public interface MycvDAO extends CrudRepository<MycvDO,Long> {
   MycvDO findBynameCv(String email);

   MycvDO findByIdUser(Long idUser);
}


