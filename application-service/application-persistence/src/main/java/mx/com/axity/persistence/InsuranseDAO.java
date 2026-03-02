package mx.com.axity.persistence;

import mx.com.axity.model.InsuranceDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface InsuranseDAO extends CrudRepository<InsuranceDO,Long> {
    @Query("SELECT i\n" +
            "FROM InsuranceDO i INNER JOIN InsuranceTypeDO it ON i.insurangeType = it.idInsurangeType\n" +
            "WHERE i.idInsurance > 0\n" +
            "AND (:insuranceCarrier IS NULL OR (Upper(i.insuranceCarrier) LIKE %:insuranceCarrier%))\n" +
            "AND (CAST(:startDate AS java.time.LocalDate) IS NULL OR i.startDate >= :startDate)\n"+
            "AND (CAST(:endDate AS java.time.LocalDate) IS NULL OR i.startDate < :endDate)\n"+
            "AND (:author IS NULL OR (Upper(i.policy) LIKE %:author%))\n"+
            "ORDER BY i.lastModification DESC")
    Page<InsuranceDO> findAllInsuranse(Pageable pageable,
                                       @Param("insuranceCarrier") String insuranceCarrier,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("author") String author);


    @Query("SELECT i\n" +
            "FROM InsuranceDO i INNER JOIN InsuranceTypeDO it ON i.insurangeType = it.idInsurangeType\n" +
            "WHERE i.idInsurance > 0 \n" +
            "ORDER BY i.lastModification DESC")
    List<InsuranceDO> findAllInsuranceExcel();


    @Query("SELECT count(i)\n" +
            "FROM InsuranceDO i INNER JOIN InsuranceTypeDO it ON i.insurangeType = it.idInsurangeType\n" +
            "WHERE i.idInsurance > 0\n" +
            "AND (:insuranceCarrier IS NULL OR (Upper(i.insuranceCarrier) LIKE %:insuranceCarrier%))\n" +
            "AND (CAST(:startDate AS java.time.LocalDate) IS NULL OR i.startDate >= :startDate)\n"+
            "AND (CAST(:endDate AS java.time.LocalDate) IS NULL OR i.startDate < :endDate)\n"+
            "AND (:author IS NULL OR (Upper(i.policy) LIKE %:author%))")
    Long getNumberRow(@Param("insuranceCarrier") String insuranceCarrier,
                     @Param("startDate") LocalDateTime startDate,
                     @Param("endDate") LocalDateTime endDate,
                     @Param("author") String author);

    @Query("select\n" +
            "\tp.insurangeType.insurangeType ,\n" +
            "\tp.policy ,\n" +
            "\tp.insuranceCarrier,\n" +
            "\tp.idInsurance,\n" +
            "\tp.insurangeType.idInsurangeType\n" +
            "from\n" +
            "\tInsuranceDO p\n" +
            "where\n" +
            "\tp.status = 'A'\n" +
            "\tand p.idInsurance in(:idInsurances )\n" +
            "\tand ( cast(( concat( cast( p.startDate as java.time.LocalDate ), ' ', p.notificationTime )) as java.time.LocalDateTime) <= :timeday)\n" +
            "\tand ( cast( p.endDate as java.time.LocalDate ) >= :dateday )")
    List<Object[]> geAllInsurangeUser(@Param("idInsurances")List<Long> idUsers, @Param("timeday") LocalDateTime timeday, @Param("dateday")LocalDate dateday);

    @Query("SELECT   p.idInsurance, p.insurangeType.insurangeType , p.policy , p.insuranceCarrier, p.url, p.phones , p.coverage, p.endDate,p.contractPdf,p.certificateNumber,p.typePolicy,p.startDate  FROM  InsuranceDO p WHERE p.idInsurance = :idInsurance")
    List<Object[]> geInsurangeByInsurance(@Param("idInsurance")Long idUser);

}
