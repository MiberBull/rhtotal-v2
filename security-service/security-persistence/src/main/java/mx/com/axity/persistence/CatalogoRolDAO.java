package mx.com.axity.persistence;

import mx.com.axity.model.CatalogoRolDO;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CatalogoRolDAO extends CrudRepository<CatalogoRolDO,Long>  {


}
