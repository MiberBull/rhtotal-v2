package mx.com.axity.persistence;

import mx.com.axity.model.FintechMyAdvanceDO;
import mx.com.axity.model.FintechVeloCashDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulePaysheetNowDAO extends CrudRepository<FintechVeloCashDO,Long> {

    @Query("select c FROM FintechVeloCashDO c where c.statusRequisition = 'ES' and c.creationDate < :nameParemeter")
    List<FintechVeloCashDO> getPaysehetNowTime(@Param("nameParemeter")LocalDateTime parameter);


    @Transactional
    @Modifying
    @Query("UPDATE FintechVeloCashDO SET statusRequisition = 'R' , reasonReject = :nameParemeter , " +
            "entity = 'SWAP', dateResponseSwap = CURRENT_DATE , timeResponseSwap = CURRENT_TIMESTAMP, " +
            " lastUserModifier = :userModifier " +
            " WHERE requisitionFolio = :folio")
    void saveOrUpdateRejectFintechVeloCashDO(@Param("nameParemeter")String param,
                                             @Param("folio")String folio,
                                             @Param("userModifier")String userM);

    @Transactional
    @Modifying
    @Query("UPDATE FintechVeloCashDO SET statusRequisition = 'AP' , reasonReject = 'N/A' , " +
            " entity = 'SWAP', dateResponseSwap = CURRENT_DATE , timeResponseSwap = CURRENT_TIMESTAMP," +
            " folioSwap = :folioSwap, descriptionApprobation = :nameParemeter , "+
            " lastUserModifier = :userModifier, dateApprobation =  CURRENT_TIMESTAMP, timeApprobation = CURRENT_TIMESTAMP, " +
            " resultApprobation = 'PAID' " +
            " WHERE requisitionFolio = :folio")
    void saveOrUpdateAprovedFintechVeloCashDO(@Param("nameParemeter")String param,
                                              @Param("folio")String folio,
                                              @Param("folioSwap")String folioSwap,
                                              @Param("userModifier")String userM);
}
