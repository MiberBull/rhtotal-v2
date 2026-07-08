package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.CandidateTO;
import mx.com.axity.services.facade.ICandidateFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("candidate")
public class CandidateRegistry {

    static final Logger LOG = LogManager.getLogger(CandidateRegistry.class);

    @Autowired
    ICandidateFacade candidateFacade;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CandidateTO> createCandidate(@RequestBody CandidateTO candidateTO) {
        LOG.info("Init createCandidate: {}", candidateTO.getDsEmail());
        CandidateTO result = candidateFacade.createCandidate(candidateTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<CandidateTO> updateCandidate(@RequestBody CandidateTO candidateTO) {
        LOG.info("Init updateCandidate: {}", candidateTO.getIdCandidate());
        CandidateTO result = candidateFacade.updateCandidate(candidateTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CandidateTO> getCandidate(@PathVariable Long id) {
        LOG.info("Init getCandidate: {}", id);
        CandidateTO result = candidateFacade.getCandidate(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<CandidateTO>> getAllCandidates() {
        LOG.info("Init getAllCandidates");
        String tenantId = TenantContext.getCurrentTenant();
        List<CandidateTO> result = candidateFacade.getAllCandidates(tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/stage/{stage}", produces = "application/json")
    public ResponseEntity<List<CandidateTO>> getCandidatesByStage(@PathVariable String stage) {
        LOG.info("Init getCandidatesByStage: {}", stage);
        String tenantId = TenantContext.getCurrentTenant();
        List<CandidateTO> result = candidateFacade.getCandidatesByStage(tenantId, stage);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
