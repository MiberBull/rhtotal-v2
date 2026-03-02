package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.FintechMyAdvanceDO;
import mx.com.axity.model.FintechVeloCashDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleAdvancedPAyshetDAO extends CrudRepository<FintechMyAdvanceDO,Long> {

    @Query("Select c from FintechMyAdvanceDO c where c.statusRequisition = 'ES' and c.creationDate < :nameParemeter ")
    List<FintechMyAdvanceDO> getAdvancedPayshetDAO(@Param("nameParemeter")LocalDateTime parameter);

    @Query("Select c from FintechVeloCashDO c where c.statusRequisition = 'ES' and c.creationDate < :nameParemeter ")
    List<FintechVeloCashDO> getVelocashDAO(@Param("nameParemeter")LocalDateTime parameter);

    @Transactional
    @Modifying
    @Query("UPDATE FintechMyAdvanceDO SET statusRequisition = 'R' , reasonReject = :nameParemeter , " +
            "entity = 'SWAP', dateResponseSwap = CURRENT_DATE , timeResponseSwap = CURRENT_TIMESTAMP, " +
            "lastUserModifier = :userModifier " +
            " WHERE requisitionFolio = :folio")
    void saveOrUpdateRejectFintechAdvanced(@Param("nameParemeter")String param,
                                           @Param("folio")String folio,
                                           @Param("userModifier")String userM);

    @Transactional
    @Modifying
    @Query("UPDATE FintechMyAdvanceDO SET statusRequisition = 'AP' , reasonReject = 'N/A' , " +
            " entity = 'SWAP', dateResponseSwap = CURRENT_DATE , timeResponseSwap = CURRENT_TIMESTAMP," +
            " folioSwap = :folioSwap,descriptionApprobation = :nameParemeter, dateApprobation = CURRENT_TIMESTAMP, " +
            " timeApprobation = CURRENT_TIMESTAMP, lastUserModifier = :userModifier, " +
            " resultApprobation = 'PAID' " +
            " WHERE requisitionFolio = :folio")
    void saveOrUpdateAprovedFintechAdvanced(@Param("nameParemeter")String param,
                                              @Param("folio")String folio,
                                              @Param("folioSwap")String folioSwap,
                                              @Param("userModifier")String userM);


    @Query("SELECT c FROM EmployeeComplementaryDO c where c.idEmployee.idEmployee =:idEmployee")
    EmployeeComplementaryDO getFolioSwapEmployee(@Param("idEmployee")Long idEmployee);

    @Transactional
    @Modifying
    @Query("UPDATE EmployeeComplementaryDO c SET c.idSwap =:idSwap where c.idEmployeeComplementary = :idEmployee")
    void setSaveFolioSwapEmployee(@Param("idSwap")String idSwap,
                                  @Param("idEmployee")Long idEmployee);

    @Transactional
    @Modifying
    @Query("UPDATE FintechMyAdvanceDO v SET v.folioConfirmationSico=:respSico where v.requisitionFolio= :requisitionFolio")
    void saveNotificaSicoAdvanced(@Param("respSico")String resp,
                          @Param("requisitionFolio")String requisitionFolio);
}
