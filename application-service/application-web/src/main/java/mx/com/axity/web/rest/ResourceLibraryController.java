package mx.com.axity.web.rest;

import mx.com.axity.commons.to.ResourceAckTO;
import mx.com.axity.commons.to.ResourceCategoryTO;
import mx.com.axity.commons.to.ResourceDocumentTO;
import mx.com.axity.services.facade.IResourceLibraryFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("library")
public class ResourceLibraryController {

    private static final Logger LOG = LogManager.getLogger(ResourceLibraryController.class);

    @Autowired
    private IResourceLibraryFacade facade;

    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ResourceCategoryTO>> getCategories(
            @RequestParam(name = "tenantId") String tenantId) {
        LOG.info("init getCategories tenantId={}", tenantId);
        return new ResponseEntity<>(facade.getCategories(tenantId), HttpStatus.OK);
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResourceCategoryTO> createCategory(@RequestBody ResourceCategoryTO category) {
        LOG.info("init createCategory name={}", category.getName());
        return new ResponseEntity<>(facade.createCategory(category), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ResourceDocumentTO>> getAllDocuments(
            @RequestParam(name = "tenantId") String tenantId) {
        LOG.info("init getAllDocuments tenantId={}", tenantId);
        return new ResponseEntity<>(facade.getAllDocuments(tenantId), HttpStatus.OK);
    }

    @RequestMapping(value = "/documents/visible", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ResourceDocumentTO>> getVisibleDocuments(
            @RequestParam(name = "tenantId") String tenantId,
            @RequestParam(name = "idClient", required = false) Long idClient) {
        LOG.info("init getVisibleDocuments tenantId={} idClient={}", tenantId, idClient);
        return new ResponseEntity<>(facade.getVisibleDocuments(tenantId, idClient), HttpStatus.OK);
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResourceDocumentTO> getDocumentById(@PathVariable("id") Long id) {
        LOG.info("init getDocumentById id={}", id);
        return new ResponseEntity<>(facade.getDocumentById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/document", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResourceDocumentTO> createDocument(@RequestBody ResourceDocumentTO document) {
        LOG.info("init createDocument title={}", document.getTitle());
        return new ResponseEntity<>(facade.createDocument(document), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<ResourceDocumentTO> updateDocument(
            @PathVariable("id") Long id,
            @RequestBody ResourceDocumentTO document) {
        LOG.info("init updateDocument id={}", id);
        return new ResponseEntity<>(facade.updateDocument(id, document), HttpStatus.OK);
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> deleteDocument(@PathVariable("id") Long id) {
        LOG.info("init deleteDocument id={}", id);
        facade.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/document/{id}/ack", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResourceAckTO> acknowledgeDocument(
            @PathVariable("id") Long id,
            @RequestHeader(name = "X-Employee-Id", required = false) Long employeeIdHeader,
            @RequestBody ResourceAckTO ack) {
        if (employeeIdHeader == null && (ack.getIdEmployee() == null || ack.getIdEmployee() <= 0)) {
            LOG.warn("acknowledgeDocument rejected: missing employee identification");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (employeeIdHeader != null) {
            ack.setIdEmployee(employeeIdHeader);
        }
        LOG.info("init acknowledgeDocument id={} employee={}", id, ack.getIdEmployee());
        return new ResponseEntity<>(facade.acknowledgeDocument(id, ack), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/document/{id}/acks", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ResourceAckTO>> getAcknowledgements(@PathVariable("id") Long id) {
        LOG.info("init getAcknowledgements id={}", id);
        return new ResponseEntity<>(facade.getAcknowledgements(id), HttpStatus.OK);
    }
}
