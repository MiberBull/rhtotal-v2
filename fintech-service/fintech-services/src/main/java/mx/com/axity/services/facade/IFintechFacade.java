package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.FintechMyAdvanceDO;

import java.time.LocalDateTime;
import java.util.List;

public interface IFintechFacade {

    //List<UserTO> getAllUsers();

    List<FintechTableTO> getPagedFintechMyAdvance(int page, String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate);

    List<FintechTableTO> getPagedFintechVeloCash(int page, String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate);

    //Consultas Unicas
    FintechTableTO getOneFintechMyAdvance(int id,String status);

    FintechTableTO getOneFintechVeloCash(int id,String status);

    //Actualizacion de status
    ResponseSwapTO updateStatuChangeMyAdvance(int idFintech,String statusFintech, String lastUserModifier, String lastModification,String reasonRejection);

    ResponseSwapTO updateStatuChangeVeloCash(int idFintech,String statusFintech, String lastUserModifier, String lastModification,String reasonRejection);
    UserTO getUserById(Long idUser);


    CountRowTO getNumberRowAdvance(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate);

    CountRowTO getNumberRowVeloCash(String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate);

    void setClickedInfoSwap( CountClickTO click );

    void saveFintechMyAdvanced(FintechMyAdvanceDO fintech );

    List<Long> findByPeriod(long idEmployee);

    List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod);

}
