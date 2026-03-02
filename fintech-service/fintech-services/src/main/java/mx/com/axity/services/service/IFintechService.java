package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.FintechMyAdvanceDO;

import java.time.LocalDateTime;
import java.util.List;

public interface IFintechService {

    //List<UserTO> getUsers();
    List<FintechTableTO> getPagedFintechMyAdvance(int page, String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate);
    List<FintechTableTO> getPagedFintechVeloCash(int page, String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate);

    FintechTableTO getOneFintechMyAdvance(int fintechOne,String status);
    FintechTableTO getOneFintechVeloCash(int fintechOneVeloCash,String status);

    //Service Update Status Fintech
    ResponseSwapTO updateStatuChangeMyAdvance(int idFintech, String statusFintech, String lastUserModifier, String lastModification,String reasonRejection);
    ResponseSwapTO updateStatuChangeVeloCash(int idFintech, String statusFintech, String lastUserModifier, String lastModification,String reasonRejection);
    UserTO getUserById(Long idUser);


    //Service Count ROW

    CountRowTO getNumberRowAdvance(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate);

    CountRowTO getNumberRowVeloCash(String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate);

    void setClickedInfoSwap( CountClickTO click );

    void saveFintechMyAdvanced ( FintechMyAdvanceDO fintech );

    List<Long> findByPeriod(long idEmployee);

    List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod);

}
