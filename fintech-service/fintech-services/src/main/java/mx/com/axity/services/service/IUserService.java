package mx.com.axity.services.service;

import mx.com.axity.commons.to.UserTO;
import mx.com.axity.model.EmployeeDO;
import org.springframework.data.repository.query.Param;

public interface IUserService {

    UserTO getUserById(Long idUser);
    Object[] getDataForQuerySico(Long idUser);
    String buildUrlForUser( Object[] data,int type);
    EmployeeDO getEmployeByIdUser( long idUser );
    String buildUrlForUserWithMonth( Object[] data,int type,String mounth );
    Long getIdEmployeeByIdUser(Long id);

}
