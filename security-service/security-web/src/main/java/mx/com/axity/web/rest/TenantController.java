package mx.com.axity.web.rest;

import mx.com.axity.model.TenantDO;
import mx.com.axity.persistence.TenantDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tenant")
public class TenantController {

    static final Logger LOG = LogManager.getLogger(TenantController.class);

    @Autowired
    TenantDAO tenantDAO;

    @GetMapping("/all")
    public ResponseEntity<List<TenantDO>> getAll() {
        LOG.info("GET /tenant/all");
        return new ResponseEntity<>(tenantDAO.findAllByActiveTrue(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TenantDO> create(@RequestBody TenantDO tenant) {
        LOG.info("POST /tenant/create id={}", tenant.getId());
        tenant.setActive(true);
        tenant.setCreationDate(LocalDateTime.now());
        return new ResponseEntity<>(tenantDAO.save(tenant), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<TenantDO> toggle(@PathVariable("id") String id) {
        LOG.info("PUT /tenant/{}/toggle", id);
        return tenantDAO.findById(id)
                .map(t -> {
                    t.setActive(!Boolean.TRUE.equals(t.getActive()));
                    return new ResponseEntity<>(tenantDAO.save(t), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
