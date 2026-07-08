package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.FaqTO;
import mx.com.axity.services.facade.impl.FaqFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("faq")
public class FaqRegistry {

    @Autowired private FaqFacadeImpl faqFacade;

    @PostMapping
    public ResponseEntity<FaqTO> create(@RequestBody FaqTO faqTO) {
        return ResponseEntity.ok(faqFacade.create(faqTO, TenantContext.getTenantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(faqFacade.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FaqTO>> getAll() {
        return ResponseEntity.ok(faqFacade.getAll(TenantContext.getTenantId()));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FaqTO>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(faqFacade.getByCategory(TenantContext.getTenantId(), category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        faqFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
