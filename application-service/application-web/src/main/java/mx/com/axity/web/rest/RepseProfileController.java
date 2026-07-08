package mx.com.axity.web.rest;

import mx.com.axity.commons.to.RepseClientTO;
import mx.com.axity.commons.to.RepseDocumentTO;
import mx.com.axity.commons.to.RepseProfileTO;
import mx.com.axity.services.facade.IRepseProfileFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("repse")
public class RepseProfileController {

    static final Logger LOG = LogManager.getLogger(RepseProfileController.class);

    @Autowired
    IRepseProfileFacade repseProfileFacade;

    // ---- Profile ----

    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<RepseProfileTO> getProfile(
            @RequestHeader(value = "X-Tenant-ID") String tenantId) {
        LOG.info("init getProfile tenant=" + tenantId);
        var result = repseProfileFacade.getProfile(tenantId);
        LOG.info("getProfile finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<RepseProfileTO> saveProfile(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestBody RepseProfileTO to) {
        LOG.info("init saveProfile tenant=" + tenantId);
        var result = repseProfileFacade.saveProfile(to, tenantId);
        LOG.info("saveProfile finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<RepseProfileTO> updateProfile(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestBody RepseProfileTO to) {
        LOG.info("init updateProfile tenant=" + tenantId);
        var result = repseProfileFacade.updateProfile(to, tenantId);
        LOG.info("updateProfile finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ---- Clients ----

    @RequestMapping(value = "/client/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RepseClientTO>> getClients(
            @RequestHeader(value = "X-Tenant-ID") String tenantId) {
        LOG.info("init getClients tenant=" + tenantId);
        var result = repseProfileFacade.getClients(tenantId);
        LOG.info("getClients finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<RepseClientTO> getClient(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable Long id) {
        LOG.info("init getClient id=" + id);
        var result = repseProfileFacade.getClient(id, tenantId);
        LOG.info("getClient finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<RepseClientTO> saveClient(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestBody RepseClientTO to) {
        LOG.info("init saveClient tenant=" + tenantId);
        var result = repseProfileFacade.saveClient(to, tenantId);
        LOG.info("saveClient finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/client", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<RepseClientTO> updateClient(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestBody RepseClientTO to) {
        LOG.info("init updateClient tenant=" + tenantId);
        var result = repseProfileFacade.updateClient(to, tenantId);
        LOG.info("updateClient finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ---- Documents ----

    @RequestMapping(value = "/document/{idRepseClient}/{period}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RepseDocumentTO>> getDocuments(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable Long idRepseClient,
            @PathVariable String period) {
        LOG.info("init getDocuments client=" + idRepseClient + " period=" + period);
        var result = repseProfileFacade.getDocuments(idRepseClient, period, tenantId);
        LOG.info("getDocuments finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/document", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<RepseDocumentTO> uploadDocument(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @RequestBody RepseDocumentTO to) {
        LOG.info("init uploadDocument tenant=" + tenantId);
        var result = repseProfileFacade.uploadDocument(to, tenantId);
        LOG.info("uploadDocument finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/document/{id}/validate", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<RepseDocumentTO> validateDocument(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable Long id,
            @RequestParam(value = "validatedBy") String validatedBy) {
        LOG.info("init validateDocument id=" + id);
        var result = repseProfileFacade.validateDocument(id, validatedBy, tenantId);
        LOG.info("validateDocument finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/document/{id}/reject", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<RepseDocumentTO> rejectDocument(
            @RequestHeader(value = "X-Tenant-ID") String tenantId,
            @PathVariable Long id,
            @RequestParam(value = "rejectionReason") String rejectionReason) {
        LOG.info("init rejectDocument id=" + id);
        var result = repseProfileFacade.rejectDocument(id, rejectionReason, tenantId);
        LOG.info("rejectDocument finalizado correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
