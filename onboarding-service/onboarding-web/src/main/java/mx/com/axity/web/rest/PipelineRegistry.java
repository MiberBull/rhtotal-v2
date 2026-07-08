package mx.com.axity.web.rest;

import mx.com.axity.commons.to.PipelineStageTO;
import mx.com.axity.services.facade.IPipelineFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("pipeline")
public class PipelineRegistry {

    static final Logger LOG = LogManager.getLogger(PipelineRegistry.class);

    @Autowired
    IPipelineFacade pipelineFacade;

    @PutMapping(value = "/{candidateId}/advance", produces = "application/json")
    public ResponseEntity<PipelineStageTO> advanceStage(
            @PathVariable Long candidateId,
            @RequestBody Map<String, String> body) {
        LOG.info("Init advanceStage candidateId={} stage={}", candidateId, body.get("stage"));
        PipelineStageTO result = pipelineFacade.advanceStage(
            candidateId,
            body.get("stage"),
            body.get("notes"),
            body.get("createdBy")
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{candidateId}/history", produces = "application/json")
    public ResponseEntity<List<PipelineStageTO>> getPipelineHistory(@PathVariable Long candidateId) {
        LOG.info("Init getPipelineHistory: {}", candidateId);
        List<PipelineStageTO> result = pipelineFacade.getPipelineHistory(candidateId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
