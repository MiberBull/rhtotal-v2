package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.DocumentTypeTO;
import mx.com.axity.services.facade.impl.DocumentTypeFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("document-type")
public class DocumentTypeRegistry {

    @Autowired private DocumentTypeFacadeImpl documentTypeFacade;

    @PostMapping
    public ResponseEntity<DocumentTypeTO> create(@RequestBody DocumentTypeTO documentTypeTO) {
        return ResponseEntity.ok(documentTypeFacade.create(documentTypeTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentTypeFacade.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypeTO>> getAll() {
        return ResponseEntity.ok(documentTypeFacade.getAll(TenantContext.getCurrentTenant()));
    }

    @GetMapping("/required")
    public ResponseEntity<List<DocumentTypeTO>> getRequired() {
        return ResponseEntity.ok(documentTypeFacade.getRequired(TenantContext.getCurrentTenant()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentTypeFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
