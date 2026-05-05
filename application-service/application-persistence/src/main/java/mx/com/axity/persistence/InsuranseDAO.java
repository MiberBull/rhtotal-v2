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
    @Query("SELECT i " +
            "FROM InsuranceDO i JOIN i.insurangeType it " +
            "WHERE i.idInsurance > 0 " +
            "AND (:insuranceCarrier IS NULL OR (Upper(i.insuranceCarrier) LIKE %:insuranceCarrier%)) " +
            "AND (:startDate IS NULL OR i.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR i.startDate < :endDate) " +
            "AND (:author IS NULL OR (Upper(i.policy) LIKE %:author%)) " +
            "ORDER BY i.lastModification DESC")
    Page<InsuranceDO> findAllInsuranse(Pageable pageable,
                                       @Param("insuranceCarrier") String insuranceCarrier,
                                       @Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("author") String author);


    @Query("SELECT i " +
            "FROM InsuranceDO i JOIN i.insurangeType it " +
            "WHERE i.idInsurance > 0 " +
            "ORDER BY i.lastModification DESC")
    List<InsuranceDO> findAllInsuranceExcel();


    @Query("SELECT count(i) " +
            "FROM InsuranceDO i JOIN i.insurangeType it " +
            "WHERE i.idInsurance > 0 " +
            "AND (:insuranceCarrier IS NULL OR (Upper(i.insuranceCarrier) LIKE %:insuranceCarrier%)) " +
            "AND (:startDate IS NULL OR i.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR i.startDate < :endDate) " +
            "AND (:author IS NULL OR (Upper(i.policy) LIKE %:author%))")
    Long getNumberRow(@Param("insuranceCarrier") String insuranceCarrier,
                     @Param("startDate") LocalDateTime startDate,
                     @Param("endDate") LocalDateTime endDate,
                     @Param("author") String author);

    @Query("select " +
            "p.insurangeType.insurangeType, " +
            "p.policy, " +
            "p.insuranceCarrier, " +
            "p.idInsurance, " +
            "p.insurangeType.idInsurangeType " +
            "from InsuranceDO p " +
            "where p.status = 'A' " +
            "and p.idInsurance in(:idInsurances) " +
            "and (cast(concat(cast(p.startDate as java.time.LocalDate), ' ', p.notificationTime) as java.time.LocalDateTime) <= :timeday) " +
            "and (cast(p.endDate as java.time.LocalDate) >= :dateday)")
    List<Object[]> geAllInsurangeUser(@Param("idInsurances")List<Long> idUsers, @Param("timeday") LocalDateTime timeday, @Param("dateday")LocalDate dateday);

    @Query("SELECT p.idInsurance, p.insurangeType.insurangeType, p.policy, p.insuranceCarrier, p.url, p.phones, p.coverage, p.endDate, p.contractPdf, p.certificateNumber, p.typePolicy, p.startDate FROM InsuranceDO p WHERE p.idInsurance = :idInsurance")
    List<Object[]> geInsurangeByInsurance(@Param("idInsurance")Long idUser);

}
