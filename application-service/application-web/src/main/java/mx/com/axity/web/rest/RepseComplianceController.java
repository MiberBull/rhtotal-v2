package mx.com.axity.web.rest;

import mx.com.axity.commons.to.RepseComplianceTO;
import mx.com.axity.commons.to.RepseProfileTO;
import mx.com.axity.services.facade.IRepseComplianceFacade;
import mx.com.axity.services.facade.IRepseExportFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("repse/compliance")
public class RepseComplianceController {

    static final Logger LOG = LogManager.getLogger(RepseComplianceController.class);

    @Autowired
    IRepseComplianceFacade repseComplianceFacade;

    @Autowired
    IRepseExportFacade repseExportFacade;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RepseComplianceTO>> getDashboard(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestParam(value = "period") String period) {
        LOG.info("init getDashboard tenant=" + tenantId + " period=" + period);
        var result = repseComplianceFacade.getDashboard(tenantId, period);
        LOG.info("getDashboard finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/semaforo/{semaforo}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RepseComplianceTO>> getBySemaforo(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable String semaforo) {
        LOG.info("init getBySemaforo semaforo=" + semaforo);
        var result = repseComplianceFacade.getBySemaforo(tenantId, semaforo);
        LOG.info("getBySemaforo finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/recalculate/{idRepseClient}/{period}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<RepseComplianceTO> recalculate(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable Long idRepseClient,
            @PathVariable String period) {
        LOG.info("init recalculate client=" + idRepseClient + " period=" + period);
        var result = repseComplianceFacade.recalculate(idRepseClient, period, tenantId);
        LOG.info("recalculate finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/expiring", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RepseProfileTO>> getExpiringProfiles(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestParam(value = "daysAhead", defaultValue = "90") int daysAhead) {
        LOG.info("init getExpiringProfiles daysAhead=" + daysAhead);
        var result = repseComplianceFacade.getExpiringProfiles(daysAhead);
        LOG.info("getExpiringProfiles finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportExcel(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestParam(value = "period") String period) {
        LOG.info("init exportExcel tenant={} period={}", tenantId, period);
        byte[] excelBytes = repseExportFacade.exportComplianceExcel(tenantId, period);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "repse-" + period + ".xlsx");
        LOG.info("exportExcel finalizado correctamente");
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
